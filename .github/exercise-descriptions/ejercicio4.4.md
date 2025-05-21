# Ejercicio 4.4: Coche

Crear una clase `Coche`, a través de la cual se pueda conocer el color del coche, la marca, el modelo, el número de caballos, el número de puertas y la matrícula. Crear el constructor del coche, así como el método `toString()`.

- Todas sus propiedades tendrán tipos de datos nulables inicialmente en el constructor para validación, pero las propiedades finales de la clase deben reflejar las reglas de nulabilidad.
- `Marca` y `modelo` no podrán ser vacíos, ni nulos. Tampoco podrán modificarse y solo se inicializarán al crear el objeto.
- `Número de caballos`, `número de puertas` y `matrícula` no podrán modificarse, ni podrán ser nulos.
- `Color` podrá modificarse, pero no podrá ser nulo.

Recuerda que Kotlin añade los getters y setters con el comportamiento por defecto, por lo que no es necesario que los implementes, a no ser que tengas que añadir alguna funcionalidad extra.

- El atributo `matricula` no debe permitir un valor que no tenga 7 caracteres.
- Los atributos `modelo` y `marca` siempre se devolverán con la primera letra en mayúscula (getter).
- El `número de caballos` deberá tener un valor entre 70 y 700.
- El `número de puertas` no podrá ser inferior a 3, ni superior a 5.

Ten en cuenta todas estas condiciones a la hora de crear el constructor de la clase.
En el programa principal, instancia varios coches, muéstralos por pantalla (`toString`) y realiza pruebas para capturar excepciones de las validaciones.

**Requisitos Clave a Evaluar (para la IA):**
- Clase `Coche`.
- Atributos:
    - `color: String` (mutable, no nulo).
    - `marca: String` (inmutable, no nulo/vacío).
    - `modelo: String` (inmutable, no nulo/vacío).
    - `caballos: Int` (inmutable, no nulo, rango 70-700).
    - `puertas: Int` (inmutable, no nulo, rango 3-5).
    - `matricula: String` (inmutable, no nulo, 7 caracteres).
- Constructor que valide todos los requisitos de los atributos (lanzar `IllegalArgumentException`).
- Getters personalizados para `marca` y `modelo` (primera letra mayúscula).
- Setter personalizado para `color` (no permitir nulo).
- Método `toString()` implementado.
