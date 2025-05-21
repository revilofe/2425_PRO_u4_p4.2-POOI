# Práctica de Programación Orientada a Objetos en Kotlin

Este repositorio contiene una serie de ejercicios diseñados para practicar los conceptos fundamentales de la Programación Orientada a Objetos (POO) utilizando el lenguaje Kotlin. Los ejercicios cubren la creación de clases, objetos, constructores, propiedades, métodos, encapsulamiento y el uso de tests unitarios.

El proyecto está configurado con Gradle, lo que facilita la gestión de dependencias, la compilación, la ejecución de la aplicación y las pruebas.

---

# Documentación de la Práctica

## Identificación de la Actividad

- **Módulo:** PROGRAMACIÓN
- **Unidad de Trabajo:** UT4. Programación Orientada a Objetos.
- **Actividad:** Profundizando en POO. Ejercicios en Kotlin.
- **Fecha de Creación:** [Fecha en la que el alumno comienza la actividad]
- **Fecha de Entrega:** [Fecha límite de entrega]
- **Alumno(s):**
  - **Nombre y Apellidos:** [Nombre y Apellidos del alumno o integrantes del grupo]
  - **Correo electrónico:** [Correo electrónico g.educaand.es]
  - **Iniciales del Alumno/Grupo:** [Iniciales del alumno o del grupo]

## Descripción General de la Actividad

[El alumno debe describir aquí brevemente en qué consiste la práctica desde su perspectiva y los objetivos que busca alcanzar con su desarrollo.]

## Estructura del Proyecto

El código fuente del proyecto se organiza de la siguiente manera:

-   **Código Principal:** Se encuentra en `src/main/kotlin/org/iesra/`. Aquí residen las clases de los ejercicios (como `Rectangulo.kt`, `Persona.kt`, `Coche.kt`, `Tiempo.kt`) y el archivo principal de demostración (`Main.kt`).
-   **Pruebas Unitarias:** Los tests para cada clase se encuentran en `src/test/kotlin/org/iesra/`. Por ejemplo, `RectanguloTest.kt`, `PersonaTest.kt`, etc.

## Instrucciones de Compilación y Ejecución

Este proyecto utiliza Gradle como sistema de construcción. Asegúrate de tener permisos de ejecución para el Gradle Wrapper (`gradlew`). Si no los tienes, ejecútalo con `sh ./gradlew` o dale permisos con `chmod +x ./gradlew`.

### Requisitos Previos:
-   JDK 17 o superior.
-   Git.

### Compilar el Código:
Para compilar todo el proyecto y generar los artefactos necesarios:
```bash
./gradlew build
```

### Ejecutar la Aplicación Principal:
Para ejecutar la función `main()` ubicada en `src/main/kotlin/org/iesra/Main.kt`, que demuestra el uso de las clases:
```bash
./gradlew run
```

### Ejecutar Pruebas Unitarias:
Para ejecutar todos los tests unitarios definidos en el proyecto y verificar la correcta implementación de las clases:
```bash
./gradlew test
```
Los informes de los tests se generan en `build/reports/tests/test/index.html`.

## Feedback Automático
Este repositorio está configurado con GitHub Actions. Cuando envíes un Pull Request con tus soluciones (o hagas un push directo a tu repositorio fork), se ejecutarán automáticamente una serie de pruebas unitarias.

Podrás ver los resultados de estas pruebas y un feedback general directamente en tu Pull Request (en la sección de comentarios y en la pestaña "Checks") o en la pestaña "Actions" de tu repositorio si fue un push. ¡Asegúrate de revisar esta información para validar tu solución!

## Desarrollo de la Actividad

[En esta sección, el alumno debe detallar el proceso de desarrollo para cada uno de los ejercicios solicitados. Se recomienda crear subsecciones para cada clase o ejercicio.]

**Ejemplo de estructura:**

### Ejercicio 4.1: Clase Rectangulo

-   **Descripción de la solución:** [Explicación de cómo se ha implementado la clase `Rectangulo`, decisiones de diseño, etc.]
-   **Código Fuente:**
    -   Clase: `src/main/kotlin/org/iesra/Rectangulo.kt`
    -   Tests: `src/test/kotlin/org/iesra/RectanguloTest.kt`
-   **Ejemplos de Uso (desde `Main.kt`):** [Breve descripción o captura si es relevante]

### Ejercicio 4.2: Clase Persona (y 4.3 Actualización)

-   **Descripción de la solución:** [Explicación de la clase `Persona` y sus métodos.]
-   **Código Fuente:**
    -   Clase: `src/main/kotlin/org/iesra/Persona.kt`
    -   Tests: `src/test/kotlin/org/iesra/PersonaTest.kt`
-   **Ejemplos de Uso (desde `Main.kt`):**

### Ejercicio 4.4: Clase Coche

-   **Descripción de la solución:** [Explicación de la clase `Coche`.]
-   **Código Fuente:**
    -   Clase: `src/main/kotlin/org/iesra/Coche.kt`
    -   Tests: `src/test/kotlin/org/iesra/CocheTest.kt`
-   **Ejemplos de Uso (desde `Main.kt`):**

### Ejercicio 4.5: Clase Tiempo

-   **Descripción de la solución:** [Explicación de la clase `Tiempo`.]
-   **Código Fuente:**
    -   Clase: `src/main/kotlin/org/iesra/Tiempo.kt`
    -   Tests: `src/test/kotlin/org/iesra/TiempoTest.kt`
-   **Ejemplos de Uso (desde `Main.kt`):**

### Ejercicio 4.6: Archivo Principal `Main.kt`

-   **Descripción de la solución:** [Explicación de cómo `Main.kt` demuestra el uso de las clases.]
-   **Código Fuente:** `src/main/kotlin/org/iesra/Main.kt`

### Ejercicio 4.7: Pruebas Unitarias

-   **Descripción del desarrollo de tests:** [Comentarios sobre el proceso de creación de los tests para cada clase.]

### Ejercicio 4.8: Workflow de GitHub Actions

-   **Descripción de la configuración:** [Si el alumno ha modificado o entendido el workflow, puede comentarlo aquí.]
-   **Código Fuente:** `.github/workflows/main.yml`

## Resultados de Pruebas

[El alumno debe resumir aquí los resultados de las pruebas unitarias ejecutadas localmente, por ejemplo, indicando si todos los tests pasan. Puede adjuntar una captura de pantalla de la ejecución de `./gradlew test` o del informe HTML.]

## Conclusiones

[El alumno debe resumir aquí las conclusiones alcanzadas al desarrollar la actividad, las lecciones aprendidas, dificultades encontradas y posibles mejoras o extensiones que se podrían implementar.]

## Referencias y Fuentes

[El alumno listará aquí las fuentes consultadas para el desarrollo de la actividad, tales como documentación oficial de Kotlin, artículos, o cualquier recurso externo relevante.]

---

### Notas Adicionales (Para el Alumno):

1.  **Nombres de Archivos y Repositorios:**
    -   Si estás trabajando en un fork o entregando esta práctica, asegúrate de seguir las convenciones de nomenclatura que te haya indicado tu profesor.
2.  **Permisos:**
    -   Verifica que tu profesor tenga los permisos necesarios para acceder a tu repositorio si es privado.
3.  **Claridad:**
    -   Asegúrate de que tu código está bien comentado y que la documentación en este README es clara y concisa.
4.  **Integridad:**
    -   No elimines las secciones de este README que son para tu documentación, a menos que se indique lo contrario. Completa la información solicitada.
