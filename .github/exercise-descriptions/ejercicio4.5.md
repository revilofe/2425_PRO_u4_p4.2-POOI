# Ejercicio 4.5: Tiempo

Crear una clase `Tiempo`, que refleja las horas de un día, es decír, desde 00:00:00 hasta 23:59:59, con atributos `hora`, `minuto` y `segundo`, que pueda ser construida indicando los tres atributos, sólo hora y minuto o sólo la hora (si no se indica, el valor de minuto o segundo será 0).

Crear el método `toString()` para mostrar el tiempo en formato: XXh XXm XXs.

En el programa principal, debe solicitar por teclado hora, minuto y segundo de forma que se puedan omitir los segundos o los minutos (y segundos, claro) e instancie la clase `Tiempo` mostrando su valor.

A tener en cuenta:
- Si segundos o minutos es mayor que 59 (no 60), se tendrá que hacer las operaciones necesarios para incrementar la magnitud superior, quedándose en segundos o minutos con el resto. Es decir 65 segundos equivale a : 1 minuto y 5 segundos.
- Hora siempre tendrá que ser menor que 24, si no, lanzará una excepción.

Añadir los siguientes métodos (y probarlos en `main`):
- `incrementar(t:Tiempo):Boolean`
- `decrementar(t:Tiempo):Boolean`
- `comparar(t:Tiempo):Int`
- `copiar():Tiempo`
- `copiar(t:Tiempo):Unit` (modifica el objeto actual)
- `sumar(t:Tiempo):Tiempo?`
- `restar(t:Tiempo):Tiempo?`
- `esMayorQue(t:Tiempo):Boolean`
- `esMenorQue(t:Tiempo):Boolean`

**Requisitos Clave a Evaluar (para la IA):**
- Clase `Tiempo` con atributos `hora`, `minuto`, `segundo` (Int).
- Constructores múltiples (h,m,s; h,m; h) con valores por defecto y manejo de desbordamiento (segundos > 59, minutos > 59).
- Validación de hora < 24 en constructores.
- Método `toString()` con formato "XXh XXm XXs".
- Implementación correcta y robusta de todos los métodos: `incrementar`, `decrementar`, `comparar`, `copiar` (2 versiones), `sumar`, `restar`, `esMayorQue`, `esMenorQue`.
- Manejo de casos límite en `incrementar`, `decrementar`, `sumar`, `restar` (ej. no superar 23:59:59, no bajar de 00:00:00, devolver `Boolean` o `Tiempo?` según corresponda).
