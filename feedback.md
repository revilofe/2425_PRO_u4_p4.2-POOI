# Sistema de Feedback y Evaluación Automática

Este repositorio está equipado con un sistema de feedback automático diseñado para ayudar a los estudiantes a medida que desarrollan sus soluciones para los ejercicios de Kotlin POO. El sistema se basa en dos componentes principales: **tests unitarios automatizados** y **análisis de código mediante Inteligencia Artificial (IA)**.

## Visión General del Funcionamiento

Cuando un estudiante envía un Pull Request (PR) con sus soluciones:
1.  **GitHub Actions se activa automáticamente:** Un workflow definido en `.github/workflows/main.yml` se pone en marcha.
2.  **Compilación y Tests Unitarios:** El código del estudiante se compila y se ejecutan una serie de tests unitarios predefinidos. Estos tests verifican la correctitud funcional de las clases implementadas (`Rectangulo`, `Persona`, `Coche`, `Tiempo`).
3.  **Análisis por IA (Google Gemini):** Si los tests se completan (o incluso si fallan, pero el código compila), los archivos Kotlin modificados en el PR se envían a un modelo de IA de Google (actualmente `gemini-1.5-flash-latest` o similar) para un análisis cualitativo.
4.  **Comentario en el PR:** Se publica un comentario consolidado en el PR del estudiante, que incluye:
    *   Un resumen de los resultados de los tests unitarios (cuántos pasaron/fallaron).
    *   Feedback cualitativo basado en los resultados de los tests.
    *   Feedback detallado de la IA sobre cada archivo Kotlin modificado, con aspectos positivos, sugerencias de mejora y un resumen.

## Componentes del Sistema

### 1. Tests Unitarios
*   **Ubicación:** Los tests se encuentran en `src/test/kotlin/org/iesra/`. Hay un archivo de test por cada clase principal del ejercicio (ej. `RectanguloTest.kt`).
*   **Propósito:** Evaluar si el código del alumno cumple con los requisitos funcionales explícitos de cada ejercicio (cálculos correctos, manejo de casos límite, validaciones, etc.).
*   **Feedback:** Proporcionan un feedback cuantitativo (tests pasados/fallidos) y son la principal medida de "correctitud" de la solución.

### 2. Feedback Asistido por IA
*   **Motor de IA:** Se utiliza la API de Google Gemini (configurado para `gemini-1.5-flash-latest` o un modelo similar en el script).
*   **Script de Orquestación:** El script `.github/scripts/call-ai-feedback.js` es responsable de:
    *   Identificar los archivos `.kt` modificados en el PR.
    *   Leer el contenido de estos archivos.
    *   Leer la descripción del ejercicio correspondiente (ver "Configuración de Ejercicios para la IA" más abajo).
    *   Construir un prompt detallado para la IA.
    *   Llamar a la API de Gemini y obtener el feedback.
    *   Pasar este feedback al workflow para que se incluya en el comentario del PR.
*   **Prompt de la IA:** El prompt base está definido dentro de `call-ai-feedback.js`. Está diseñado para que la IA actúe como un tutor de Kotlin, enfocándose en aspectos positivos, áreas de mejora (lógica, eficiencia, estilo idiomático de Kotlin, adherencia a POO) y un resumen.

## Configuración por el Profesor

Para que el sistema de feedback funcione correctamente, especialmente la parte de la IA, el profesor necesita realizar algunas configuraciones:

### A. Configurar la API Key de Google Gemini
El sistema necesita una API key válida para acceder a los modelos de Google Gemini.

1.  **Obtener una API Key:**
    *   Ve a [Google AI Studio](https://aistudio.google.com/) (o la plataforma correspondiente de Google Cloud para APIs de Gemini).
    *   Crea un nuevo proyecto o selecciona uno existente.
    *   Genera una nueva API key. Asegúrate de copiarla y guardarla de forma segura.
2.  **Añadir la API Key a los Secrets de GitHub:**
    *   En tu repositorio de GitHub (el que usarán tus alumnos, basado en este template), ve a `Settings` > `Secrets and variables` > `Actions`.
    *   Crea un nuevo "Repository secret" llamado `AI_API_KEY`.
    *   Pega la API key que obtuviste de Google en el valor de este secret.
    *   **Importante:** Nunca escribas la API key directamente en los archivos del workflow o en el código. Usar secrets es la forma segura. El script `call-ai-feedback.js` está diseñado para leer esta variable de entorno.

### B. Preparar las Descripciones de los Ejercicios
La IA necesita el enunciado del ejercicio para entender el contexto del código del alumno.

1.  **Directorio de Descripciones:** Asegúrate de que el directorio `.github/exercise-descriptions/` existe en la raíz del repositorio. El script de IA buscará los archivos aquí.
2.  **Crear Archivos Markdown:**
    *   Para cada ejercicio que quieras que la IA evalúe, crea un archivo Markdown en este directorio.
    *   **Nomenclatura:** El script `call-ai-feedback.js` intenta encontrar la descripción basándose en el nombre del archivo Kotlin modificado (ej. `Rectangulo.kt` -> `ejercicio4.1.md`). La convención actual implementada en `getExerciseNumberFromFilename` dentro del script es:
        *   `Rectangulo.kt` -> `ejercicio4.1.md`
        *   `Persona.kt` -> `ejercicio4.2.md` (Si necesitas diferenciar el ejercicio 4.3 que también modifica `Persona.kt`, considera nombrar el archivo del alumno para 4.3 de forma distintiva, o ajustar la lógica en `getExerciseNumberFromFilename`).
        *   `Coche.kt` -> `ejercicio4.4.md`
        *   `Tiempo.kt` -> `ejercicio4.5.md`
        *   Ajusta la función `getExerciseNumberFromFilename` en `call-ai-feedback.js` si usas una nomenclatura diferente para los archivos de los alumnos o las descripciones.
    *   **Contenido:**
        *   Copia el enunciado completo y exacto del ejercicio en el archivo Markdown correspondiente.
        *   (Recomendado) Incluye una sección al final del Markdown como `**Requisitos Clave a Evaluar (para la IA):**` y lista los puntos más importantes. Esto está contemplado en el prompt que usa la IA.

### C. Revisar y Personalizar el Prompt de la IA (Opcional Avanzado)
*   El prompt que se envía a la IA está definido en la variable `prompt` dentro del script `.github/scripts/call-ai-feedback.js`.
*   Si tienes conocimientos de "prompt engineering" o quieres que la IA se enfoque en aspectos diferentes, puedes modificar este prompt. Realiza pruebas cuidadosas después de cualquier cambio.

### D. Dependencias del Script de IA
*   El script `call-ai-feedback.js` depende de las librerías `@google/generative-ai` y `@octokit/rest`, cuyas versiones están especificadas en `.github/scripts/package.json`.
*   Estas dependencias se instalan automáticamente por el workflow (`main.yml`) usando `npm install` en el directorio `.github/scripts/`.
*   Si actualizas Node.js o estas librerías, asegúrate de que el workflow sea compatible.

## Interpretación del Feedback (Guía para Profesores y Alumnos)

*   **Tests Unitarios son la Base:** Si los tests fallan, el código tiene problemas funcionales que deben ser corregidos prioritariamente.
*   **IA como un Tutor Asistente:**
    *   Las sugerencias de la IA están para ayudar a aprender y ver diferentes perspectivas.
    *   No todas las sugerencias de la IA serán perfectas o la única forma de hacer las cosas. Anima a los alumnos a pensar críticamente.
    *   La IA puede ayudar a identificar código poco idiomático, posibles mejoras de lógica, o recordar buenas prácticas de POO.
*   **Iteración:** El desarrollo de software es iterativo. Los alumnos deben usar el feedback para mejorar su código y reenviar sus soluciones.

## Limitaciones y Consideraciones

*   **Costos de API:** El uso de la API de Gemini puede tener costos asociados dependiendo del volumen de uso. Monitoriza tu consumo en la consola de Google Cloud.
*   **Rate Limits de API:** Las APIs pueden tener límites de peticiones por minuto. Si tienes muchos alumnos haciendo PRs simultáneamente, podrías alcanzar estos límites. El script actual tiene un manejo básico de errores pero no implementa reintentos sofisticados con backoff exponencial, por ejemplo.
*   **Calidad de la IA:** Aunque los modelos de IA son potentes, no son infalibles. Pueden cometer errores o dar sugerencias que no sean óptimas o que no entiendan completamente el contexto de una solución muy novedosa.
*   **Seguridad del Contenido de la IA:** Se han configurado `safetySettings` básicos en la llamada a Gemini. Revisa y ajusta estos según tus necesidades y políticas. El feedback podría ser bloqueado si el código o el prompt activan estos filtros.
*   **Límites de Tokens:** Los modelos de IA tienen un límite en la cantidad de texto (tokens) que pueden procesar en una sola petición (prompt + respuesta). El script `call-ai-feedback.js` tiene algunas salvaguardas (`MAX_FILE_CONTENT_LENGTH`, `MAX_TOTAL_CODE_LENGTH`) para evitar exceder estos límites, pero si los archivos de los alumnos son extremadamente grandes o numerosos en un solo PR, el feedback podría ser truncado o no procesar todos los archivos.

---

Este sistema de feedback está diseñado para ser una herramienta poderosa tanto para la evaluación formativa como para el aprendizaje del alumno. ¡Esperamos que te sea de gran utilidad!
