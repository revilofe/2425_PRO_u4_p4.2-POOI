# Ejercicio 4.1: Rectángulo

Crear una clase `Rectangulo`, con atributos `base` y `altura`. La clase debe disponer del constructor y los métodos para calcular el área y el perímetro. Los atributos no se podrán modificar, aunque sí consultar. Por último, tendrán que ser mayor que 0.

Opcionalmente se puede crear el método `toString()` para mostrar información sobre el rectángulo: `override fun toString() = ""`. (Pulsa Ctrl+o)

En el programa principal, crear varios rectángulos. Mostrarlos y mostrar por pantalla sus áreas y perímetros.

**Requisitos Clave a Evaluar (para la IA):**
- Definición de la clase `Rectangulo`.
- Atributos `base` y `altura` (Double, privados, inmutables/val).
- Constructor que inicializa `base` y `altura`.
- Validación en el constructor: `base` y `altura` deben ser > 0. Lanzar `IllegalArgumentException` si no.
- Método `area(): Double` que calcula `base * altura`.
- Método `perimetro(): Double` que calcula `2 * (base + altura)`.
- (Opcional) Método `toString()` con formato razonable.
