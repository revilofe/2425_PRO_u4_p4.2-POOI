// .github/scripts/call-ai-feedback.js

const fs = require('fs').promises;
const { Octokit } = require("@octokit/rest");
const { GoogleGenerativeAI, HarmCategory, HarmBlockThreshold } = require("@google/generative-ai");

// --- Configuración y Constantes ---
const AI_API_KEY = process.env.AI_API_KEY;
const PR_NUMBER = process.env.PR_NUMBER;
const REPO_OWNER = process.env.REPO_OWNER;
const REPO_NAME = process.env.REPO_NAME;
const GITHUB_TOKEN = process.env.GITHUB_TOKEN;

const EXERCISE_DESC_PATH = '.github/exercise-descriptions'; // Relativo a la raíz del repo
const MAX_FILE_CONTENT_LENGTH = 28000; // Límite de caracteres para el contenido de un archivo (Gemini 1.5 Flash tiene ~32k tokens, 1 char ~ 1 token en peor caso)
const MAX_TOTAL_CODE_LENGTH = 60000; // Límite total de caracteres de código a enviar a la IA en una sola llamada (si se hiciera así)

const octokit = new Octokit({ auth: GITHUB_TOKEN });
let genAI, aiModel;

if (AI_API_KEY) {
    try {
        genAI = new GoogleGenerativeAI(AI_API_KEY);
        aiModel = genAI.getGenerativeModel({ model: "gemini-1.5-flash-latest" });
    } catch (e) {
        console.error("Error inicializando GoogleGenerativeAI. ¿Está la API Key configurada correctamente?", e);
        // No se puede continuar sin el modelo de IA si la API key está presente pero falla la inicialización
    }
}

const safetySettings = [
    { category: HarmCategory.HARM_CATEGORY_HARASSMENT, threshold: HarmBlockThreshold.BLOCK_ONLY_HIGH },
    { category: HarmCategory.HARM_CATEGORY_HATE_SPEECH, threshold: HarmBlockThreshold.BLOCK_ONLY_HIGH },
    { category: HarmCategory.HARM_CATEGORY_SEXUALLY_EXPLICIT, threshold: HarmBlockThreshold.BLOCK_ONLY_HIGH },
    { category: HarmCategory.HARM_CATEGORY_DANGEROUS_CONTENT, threshold: HarmBlockThreshold.BLOCK_ONLY_HIGH },
];

async function getModifiedKotlinFiles() {
    if (!PR_NUMBER) {
        console.log("No es un Pull Request válido (PR_NUMBER no está definido), omitiendo obtención de archivos modificados.");
        return [];
    }
    try {
        const { data: files } = await octokit.pulls.listFiles({
            owner: REPO_OWNER,
            repo: REPO_NAME,
            pull_number: PR_NUMBER,
        });

        return files
            .filter(file => file.filename.endsWith('.kt') && (file.status === 'added' || file.status === 'modified'))
            .map(file => ({ filename: file.filename, status: file.status }));
    } catch (error) {
        console.error(`Error obteniendo archivos del PR #${PR_NUMBER}: ${error.message}`);
        throw error;
    }
}

async function getFileContent(filename) {
    try {
        const { data: content } = await octokit.repos.getContent({
            owner: REPO_OWNER,
            repo: REPO_NAME,
            path: filename,
            ref: process.env.GITHUB_HEAD_REF || process.env.GITHUB_SHA,
        });
        const fileContent = Buffer.from(content.content, 'base64').toString('utf-8');
        if (fileContent.length > MAX_FILE_CONTENT_LENGTH) {
            console.warn(`Contenido del archivo ${filename} truncado de ${fileContent.length} a ${MAX_FILE_CONTENT_LENGTH} caracteres.`);
            return fileContent.substring(0, MAX_FILE_CONTENT_LENGTH);
        }
        return fileContent;
    } catch (error) {
        console.error(`Error obteniendo contenido del archivo ${filename}: ${error.message}`);
        return null;
    }
}

function getExerciseNumberFromFilename(filename) {
    const name = filename.toLowerCase();
    if (name.includes('rectangulo')) return '4.1';
    if (name.includes('persona')) return '4.2'; // Asume que 'Persona.kt' es para 4.2; 4.3 podría necesitar un nombre de archivo diferente o lógica aquí
    if (name.includes('coche')) return '4.4';
    if (name.includes('tiempo')) return '4.5';
    // Podríamos necesitar una lógica más específica si los nombres de archivo no son únicos por ejercicio
    // o si un ejercicio modifica varios archivos que no tienen el nombre clave.
    // Por ahora, para el ej 4.3 (Actualización Persona), si el archivo sigue siendo Persona.kt,
    // el prompt deberá ser lo suficientemente claro si la descripción del ejercicio 4.3 se pasa.
    // Si se crea un 'PersonaV2.kt' o similar, se necesitaría añadir esa lógica aquí.
    return null; 
}

async function getExerciseDescription(exerciseNumber) {
    if (!exerciseNumber) return "No se pudo determinar el ejercicio específico. Por favor, evalúa el código Kotlin proporcionado en base a principios generales de POO, claridad, eficiencia y correctitud idiomática de Kotlin.";
    // Ajustar la ruta para que sea relativa a la raíz del repositorio donde se ejecuta la Action
    const filePath = `./.github/exercise-descriptions/ejercicio${exerciseNumber}.md`;
    try {
        return await fs.readFile(filePath, 'utf-8');
    } catch (error) {
        console.warn(`Descripción no encontrada para ejercicio ${exerciseNumber} en ${filePath}. Usando descripción genérica.`);
        return "Descripción del ejercicio no disponible. Evalúa basado en principios generales de POO en Kotlin, buscando claridad, eficiencia y correctitud idiomática.";
    }
}

async function getAIFeedbackFromGemini(prompt) {
    if (!aiModel) {
        console.log("Cliente de IA (Gemini) no inicializado (probable falta de AI_API_KEY o error de inicialización). No se puede generar feedback.");
        return "Feedback de IA no disponible: Cliente de IA no inicializado.";
    }
    try {
        // console.log("\n--- PROMPT ENVIADO A GEMINI ---", prompt.substring(0, 500) + "..." , "\n----------------------------"); // Para depuración, cuidado con la longitud
        const result = await aiModel.generateContent({
            contents: [{ role: "user", parts: [{ text: prompt }] }],
            safetySettings,
            generationConfig: {
                // temperature: 0.6, // Un valor más bajo para respuestas más factuales y menos "creativas"
                // maxOutputTokens: 2048, // Límite de tokens en la respuesta
            }
        });
        const response = result.response;
        if (!response || !response.candidates || response.candidates.length === 0 || 
            !response.candidates[0].content || !response.candidates[0].content.parts || 
            response.candidates[0].content.parts.length === 0 || !response.candidates[0].content.parts[0].text) {
            console.warn("Respuesta de IA (Gemini) inesperada o vacía:", JSON.stringify(response, null, 2));
            const finishReason = response?.candidates?.[0]?.finishReason;
            if (finishReason === 'SAFETY') {
                return "El feedback no pudo ser generado debido a las políticas de seguridad del contenido de la IA. El prompt o el código podrían haber activado un filtro.";
            }
            if (finishReason === 'MAX_TOKENS') {
                return "La IA no pudo generar un feedback completo porque se alcanzó el límite máximo de tokens de respuesta. El feedback podría estar incompleto.";
            }
            return "La IA no proporcionó un feedback en el formato esperado (respuesta vacía o malformada).";
        }
        return response.candidates[0].content.parts[0].text;
    } catch (error) {
        console.error("Error llamando a la API de Gemini:", error.message);
        if (error.message && (error.message.toLowerCase().includes('safety') || error.message.toLowerCase().includes('blocked'))) {
             return "El feedback no pudo ser generado debido a políticas de seguridad del contenido de la IA. Intenta reformular o revisar el código enviado.";
        }
        if (error.message && error.message.toLowerCase().includes('quota')) {
            return "Se ha excedido la cuota de la API de IA. Por favor, inténtalo más tarde o revisa tu plan de facturación.";
        }
        return "Error al generar feedback de la IA. Revisa los logs del workflow para más detalles.";
    }
}

async function main() {
    let fullAiFeedbackOutput = "";

    if (!AI_API_KEY) {
        fullAiFeedbackOutput = "Feedback de IA no disponible: La variable AI_API_KEY no está configurada en los secrets del repositorio.";
        process.stdout.write(fullAiFeedbackOutput);
        return;
    }
    if (!PR_NUMBER) {
        fullAiFeedbackOutput = "Feedback de IA no disponible: No se detectó un número de Pull Request (variable PR_NUMBER).";
        process.stdout.write(fullAiFeedbackOutput);
        return;
    }
    if (!genAI || !aiModel) {
        fullAiFeedbackOutput = "Feedback de IA no disponible: Error al inicializar el cliente de Google Gemini. Verifica la API Key y la configuración.";
        process.stdout.write(fullAiFeedbackOutput);
        return;
    }

    try {
        const modifiedFilesInfo = await getModifiedKotlinFiles();
        if (modifiedFilesInfo.length === 0) {
            fullAiFeedbackOutput = "No se encontraron archivos Kotlin (.kt) modificados o añadidos en este Pull Request para analizar.";
            process.stdout.write(fullAiFeedbackOutput);
            return;
        }

        fullAiFeedbackOutput = "### ⭐ Feedback Detallado por IA (Google Gemini)\n\n";
        let totalCodeCharCount = 0;

        for (const fileInfo of modifiedFilesInfo) {
            const studentCode = await getFileContent(fileInfo.filename);
            if (!studentCode) {
                fullAiFeedbackOutput += `\n\n---\n\n#### 📄 Archivo: \`${fileInfo.filename}\`\n\nNo se pudo obtener el contenido del archivo.\n`;
                continue;
            }

            if (totalCodeCharCount + studentCode.length > MAX_TOTAL_CODE_LENGTH && modifiedFilesInfo.length > 1) {
                 console.warn(`Límite total de código (${MAX_TOTAL_CODE_LENGTH} caracteres) excedido. No se analizará ${fileInfo.filename} ni los archivos subsiguientes en esta ejecución para evitar sobrecargar la API.`);
                 fullAiFeedbackOutput += `\n\n---\n\n#### 📄 Archivo: \`${fileInfo.filename}\`\n\nEste archivo (y los siguientes, si los hay) no fueron analizados para no exceder el límite de tokens de la IA en una sola ejecución.\n`;
                 break;
            }
            totalCodeCharCount += studentCode.length;
            
            const exerciseNumber = getExerciseNumberFromFilename(fileInfo.filename);
            const exerciseDescription = await getExerciseDescription(exerciseNumber);
            
            const prompt = \`
Eres un asistente experto en programación Kotlin y un tutor amigable. Tu tarea es revisar el código Kotlin de un estudiante para un ejercicio de Programación Orientada a Objetos y proporcionar feedback constructivo.

**Contexto del Ejercicio (Ejercicio \${exerciseNumber || 'Desconocido'}):**
---
\${exerciseDescription}
---

**Código del Estudiante (Archivo: \${fileInfo.filename}):**
---
\`\`\`kotlin
\${studentCode}
\`\`\`
---

**Instrucciones para tu Feedback:**
Por favor, analiza CUIDADOSAMENTE el "Código del Estudiante" en el contexto del "Contexto del Ejercicio". Estructura tu respuesta utilizando Markdown con las siguientes secciones EXACTAS:

### ✅ Aspectos Positivos
*   Menciona 1-3 aspectos que el estudiante implementó bien o buenas prácticas que utilizó en ESTE ARCHIVO. Sé específico. Si no hay aspectos claramente positivos, indica que el código es una primera aproximación que cumple con X o Y.

### 💡 Áreas de Mejora y Sugerencias
*   Identifica 1-3 áreas CLAVE donde el código de ESTE ARCHIVO podría mejorar. Considera:
    *   Errores lógicos o bugs sutiles.
    *   Incumplimiento de requisitos del ejercicio.
    *   Oportunidades para que el código sea más eficiente, legible o idiomático en Kotlin.
    *   Desviaciones de buenas prácticas de POO (encapsulamiento, cohesión, etc.).
    *   Ofrece sugerencias CONCRETAS y, si es posible, pequeños ejemplos de CÓMO podría mejorarse. No reescribas todo el código, enfócate en fragmentos.

### 📝 Resumen Específico para este Archivo
*   Proporciona un breve resumen (1-2 frases) con tu evaluación general del código de ESTE ARCHIVO y el consejo más importante para el estudiante respecto a este archivo.

**Formato y Tono:**
*   Enfócate EXCLUSIVAMENTE en el código del archivo \`\${fileInfo.filename}\`. NO comentes sobre otros archivos.
*   Sé específico y constructivo. El objetivo es ayudar al estudiante a APRENDER.
*   Usa un tono amigable y alentador.
*   NO inventes información si no estás seguro. Es mejor decir que no puedes evaluar un aspecto que dar información incorrecta.
*   Si el código es muy corto o simple, ajusta la profundidad de tu feedback, pero intenta siempre encontrar algo útil que decir.
*   Si el código parece no tener relación con el ejercicio descrito, indícalo amablemente.
\`;
            // console.log(\`Generando feedback de IA para \${fileInfo.filename}...\`); // Log útil para depuración
            const individualFeedback = await getAIFeedbackFromGemini(prompt);
            fullAiFeedbackOutput += `\n\n---\n\n#### 📄 Archivo: \`${fileInfo.filename}\`\n\n${individualFeedback}`;
        }

    } catch (error) {
        console.error("Error en la función main de call-ai-feedback.js:", error.message);
        fullAiFeedbackOutput = "Ocurrió un error general al procesar los archivos para el feedback de la IA. Revisa los logs del workflow.";
    }

    process.stdout.write(fullAiFeedbackOutput);
}

main();
