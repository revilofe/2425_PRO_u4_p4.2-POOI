package org.iesra

import java.util.Scanner

fun ejercicio4_1_Rectangulo() {
    println("--- Ejercicio 4.1: Rectangulo ---")
    val rect1 = Rectangulo(5.0, 10.0)
    val rect2 = Rectangulo(base = 7.5, altura = 3.2)

    println("Rectángulo 1: $rect1")
    println("Área de Rectángulo 1: ${rect1.area()}")
    println("Perímetro de Rectángulo 1: ${rect1.perimetro()}")

    println("Rectángulo 2: $rect2")
    println("Área de Rectángulo 2: ${rect2.area()}")
    println("Perímetro de Rectángulo 2: ${rect2.perimetro()}")
    println()
}

fun ejercicio4_2_Persona() {
    println("--- Ejercicio 4.2: Persona ---")
    val persona1 = Persona(peso = 65.0, alturaInicial = 1.70)
    val persona2 = Persona(nombre = "Ana", peso = 58.0, altura = 1.65)
    val persona3 = Persona(nombre = "Carlos", peso = 80.0, altura = 1.78)

    println("Persona 1 (toString): $persona1")
    println("Persona 2 (toString): $persona2")
    println("Persona 3 (toString): $persona3")
    println()

    // Persona 1
    print("Introduce el nombre para Persona 1: ")
    var nombreP1: String?
    do {
        nombreP1 = readlnOrNull()
        if (nombreP1.isNullOrBlank()) {
            print("El nombre no puede ser vacío. Introdúcelo de nuevo: ")
        }
    } while (nombreP1.isNullOrBlank())
    persona1.nombre = nombreP1
    println("Datos Persona 1 (actualizada): Nombre=${persona1.nombre}, Peso=${persona1.peso}kg, Altura=${persona1.altura}m")
    println()

    // Persona 3
    println("Datos Persona 3: Peso=${persona3.peso}kg, Altura=${persona3.altura}m, IMC=${String.format("%.2f", persona3.imc)}")
    persona3.altura = 1.80
    println("Datos Persona 3 (altura modificada): Peso=${persona3.peso}kg, Altura=${persona3.altura}m, IMC=${String.format("%.2f", persona3.imc)}")
    println()

    // Persona 2 y Persona 3
    persona2.altura = persona3.altura
    println("Persona 2 (altura igualada a Persona 3): $persona2")
    println("Persona 3 (sin cambios recientes): $persona3")
    println("¿Persona 2 es igual a Persona 3? ${persona2.equals(persona3)}") // Debería ser false si los nombres son diferentes
    
    // Para probar equals true, si nombre y peso fueran iguales
    val persona4 = Persona("Carlos", 80.0, 1.80) // Mismos datos que persona3 ahora
    println("¿Persona 3 es igual a una nueva Persona4 con mismos datos (Carlos, 80.0, 1.80)? ${persona3.equals(persona4)}")

    println()
}

fun ejercicio4_3_ActualizacionPersona() {
    println("--- Ejercicio 4.3: Actualización Persona ---")
    val personaEjemplo = Persona("Luisa", 60.0, 1.68)
    println("Demostración con ${personaEjemplo.nombre}:")
    println("Saludo: ${personaEjemplo.saludar()}")
    println("¿Altura encima de la media? ${personaEjemplo.alturaEncimaMedia()}")
    println("¿Peso encima de la media? ${personaEjemplo.pesoEncimaMedia()}")
    println("Descripción completa: ${personaEjemplo.obtenerDesc()}")
    println()

    val listaPersonas = listOf(
        Persona("Pedro", 75.0, 1.82),
        Persona("Sofía", 55.0, 1.60),
        Persona("Miguel", 90.0, 1.75),
        Persona(null, 68.0, 1.72),
        Persona("Elena", 62.0, 1.70)
    )

    println("Recorriendo lista de personas:")
    listaPersonas.forEach { p ->
        println(p.saludar())
        println(p.obtenerDesc())
        println("---")
    }
    println()
}

fun ejercicio4_4_Coche() {
    println("--- Ejercicio 4.4: Coche ---")
    val coche1 = Coche("Rojo", "Seat", "Leon", 150, 5, "1234ABC")
    val coche2 = Coche(colorInicial = "Azul", marcaInicial = "renault", modeloInicial = "megane", caballosInicial = 140, puertasInicial = 5, matriculaInicial = "5678DEF")

    println("Coche 1: $coche1")
    println("Coche 2: $coche2") // Verificamos que marca y modelo se capitalizan
    coche2.color = "Verde"
    println("Coche 2 (color modificado): $coche2")
    println()

    println("Demostrando validaciones (captura de excepciones):")

    try {
        Coche(null, "Seat", "Ibiza", 100, 5, "1111BBB")
    } catch (e: IllegalArgumentException) {
        println("Error al crear coche con color nulo: ${e.message}")
    }
    
    try {
        Coche("Rojo", null, "Ibiza", 100, 5, "1111BBB")
    } catch (e: IllegalArgumentException) {
        println("Error al crear coche con marca nula: ${e.message}")
    }
    try {
        Coche("Rojo", "", "Ibiza", 100, 5, "1111BBB")
    } catch (e: IllegalArgumentException) {
        println("Error al crear coche con marca vacía: ${e.message}")
    }

    try {
        Coche("Rojo", "Seat", null, 100, 5, "2222CCC")
    } catch (e: IllegalArgumentException) {
        println("Error al crear coche con modelo nulo: ${e.message}")
    }
    try {
        Coche("Rojo", "Seat", "", 100, 5, "2222CCC")
    } catch (e: IllegalArgumentException) {
        println("Error al crear coche con modelo vacío: ${e.message}")
    }

    try {
        Coche("Rojo", "Seat", "Ibiza", 50, 5, "3333DDD")
    } catch (e: IllegalArgumentException) {
        println("Error al crear coche con caballos < 70: ${e.message}")
    }
    try {
        Coche("Rojo", "Seat", "Ibiza", 800, 5, "3333DDD")
    } catch (e: IllegalArgumentException) {
        println("Error al crear coche con caballos > 700: ${e.message}")
    }
    
    try {
        Coche("Rojo", "Seat", "Ibiza", 100, null, "3333DDD")
    } catch (e: IllegalArgumentException) {
        println("Error al crear coche con puertas nulas: ${e.message}")
    }

    try {
        Coche("Rojo", "Seat", "Ibiza", 100, 2, "4444EEE")
    } catch (e: IllegalArgumentException) {
        println("Error al crear coche con puertas < 3: ${e.message}")
    }
    try {
        Coche("Rojo", "Seat", "Ibiza", 100, 6, "4444EEE")
    } catch (e: IllegalArgumentException) {
        println("Error al crear coche con puertas > 5: ${e.message}")
    }

    try {
        Coche("Rojo", "Seat", "Ibiza", 100, 5, null)
    } catch (e: IllegalArgumentException) {
        println("Error al crear coche con matricula nula: ${e.message}")
    }
    try {
        Coche("Rojo", "Seat", "Ibiza", 100, 5, "123456") // 6 chars
    } catch (e: IllegalArgumentException) {
        println("Error al crear coche con matricula de 6 chars: ${e.message}")
    }
    try {
        Coche("Rojo", "Seat", "Ibiza", 100, 5, "12345678") // 8 chars
    } catch (e: IllegalArgumentException) {
        println("Error al crear coche con matricula de 8 chars: ${e.message}")
    }

    try {
        val cocheValido = Coche("Negro", "Audi", "A4", 190, 5, "7777GGG")
        println("Coche válido creado: $cocheValido")
        // cocheValido.color = null // Esto daría error de compilación porque el setter espera String, no String?
        // Para forzar el error de validación del setter, necesitaríamos reflexión o una función Java.
        // La validación en el setter `require(value != null)` es más una salvaguarda si se
        // interactúa desde Java. En Kotlin puro, el tipo String no nulable ya lo previene.
        // Vamos a simular un intento de asignación inválida indirectamente, aunque la validación de color
        // en el constructor ya cubre la no nulidad inicial.
        // Este caso específico (asignar null a un color ya existente) es difícil de probar directamente
        // en Kotlin puro sin recurrir a trucos, dado que el tipo es `String` y no `String?`.
        // La `require` en el setter de `color` está más para el caso de que `colorInicial` fuera `null`
        // y no se validara antes en el `init`, o para interoperabilidad Java.
        // La validación `require(colorInicial != null)` en el `init` es la que se prueba arriba.
        println("La prueba de asignar 'null' al color de un coche existente no se puede hacer directamente en Kotlin puro si la propiedad es no nulable (String). La validación del setter se activaría principalmente en escenarios de interoperabilidad Java o si la validación del constructor no existiera.")
    } catch (e: IllegalArgumentException) {
        println("Error al modificar color a nulo: ${e.message}") // No se espera llegar aquí con Kotlin puro
    }
    println()
}

fun leerTiempo(scanner: Scanner, mensajePrompt: String): Tiempo? {
    print("$mensajePrompt (formato: H M S, H M, o solo H): ")
    val linea = scanner.nextLine()
    val partes = linea.split(" ").mapNotNull { it.toIntOrNull() }
    return try {
        when (partes.size) {
            1 -> Tiempo(partes[0])
            2 -> Tiempo(partes[0], partes[1])
            3 -> Tiempo(partes[0], partes[1], partes[2])
            else -> {
                println("Entrada inválida.")
                null
            }
        }
    } catch (e: IllegalArgumentException) {
        println("Error al crear tiempo: ${e.message}")
        null
    }
}

fun ejercicio4_5_Tiempo() {
    println("--- Ejercicio 4.5: Tiempo ---")
    val scanner = Scanner(System.`in`)

    println("Introduce el tiempo inicial:")
    val tInicial = leerTiempo(scanner, "Tiempo inicial")
    if (tInicial != null) {
        println("Tiempo inicial ingresado: $tInicial")
    } else {
        println("No se pudo crear el tiempo inicial. Usando uno por defecto.")
        //return // O usar un tiempo por defecto para continuar
    }
    var t1 = tInicial ?: Tiempo(10,30,0) // Usa el ingresado o uno por defecto
    println("Tiempo base para operaciones (t1): $t1")
    println()

    println("--- Pruebas de métodos ---")

    val tIncremento = leerTiempo(scanner, "Introduce tiempo a incrementar (H M S)")
    if (tIncremento != null) {
        println("Intentando incrementar t1 ($t1) en $tIncremento...")
        if (t1.incrementar(tIncremento)) {
            println("t1 incrementado: $t1")
        } else {
            println("Error: Incrementar t1 ($t1) en $tIncremento resultaría en un tiempo inválido (>23:59:59). t1 no ha cambiado.")
        }
    }
    println()

    val tDecremento = leerTiempo(scanner, "Introduce tiempo a decrementar (H M S)")
    if (tDecremento != null) {
        println("Intentando decrementar t1 ($t1) en $tDecremento...")
        if (t1.decrementar(tDecremento)) {
            println("t1 decrementado: $t1")
        } else {
            println("Error: Decrementar t1 ($t1) en $tDecremento resultaría en un tiempo inválido (<00:00:00). t1 no ha cambiado.")
        }
    }
    println()

    val tComparar = leerTiempo(scanner, "Introduce tiempo para comparar con t1 (H M S)")
    if (tComparar != null) {
        val comparacion = t1.comparar(tComparar)
        println("Comparación de t1 ($t1) con $tComparar: $comparacion (0=igual, 1=t1>tComparar, -1=t1<tComparar)")
    }
    println()

    val tCopia = t1.copiar()
    println("Copia de t1 (tCopia): $tCopia. Original t1: $t1")
    // Modificamos tCopia para ver que t1 no cambia
    tCopia.incrementar(Tiempo(1,0,0))
    println("tCopia modificada: $tCopia. Original t1 sin cambios: $t1")
    println()

    val tCopiaDestino = leerTiempo(scanner, "Introduce tiempo para ser el nuevo valor de t1 (copiar a t1)")
    if (tCopiaDestino != null) {
        println("t1 antes de copiar DESDE $tCopiaDestino: $t1")
        t1.copiar(tCopiaDestino) // t1 toma los valores de tCopiaDestino
        println("t1 después de copiar DESDE $tCopiaDestino: $t1")
    }
    println()
    
    // Reset t1 para las siguientes pruebas para que sean más predecibles
    t1 = Tiempo(10, 30, 0)
    println("t1 reseteado a $t1 para las siguientes pruebas.")


    val tSumar = leerTiempo(scanner, "Introduce tiempo para sumar a t1 (H M S)")
    if (tSumar != null) {
        val suma = t1.sumar(tSumar)
        println("Suma de t1 ($t1) + $tSumar: ${suma ?: "null (resultado inválido)"}")
    }
    println()

    val tRestar = leerTiempo(scanner, "Introduce tiempo para restar de t1 (H M S)")
    if (tRestar != null) {
        val resta = t1.restar(tRestar)
        println("Resta de t1 ($t1) - $tRestar: ${resta ?: "null (resultado inválido)"}")
    }
    println()

    val tMayor = leerTiempo(scanner, "Introduce tiempo para ver si t1 es MAYOR que él (H M S)")
    if (tMayor != null) {
        println("¿Es t1 ($t1) MAYOR que $tMayor? ${t1.esMayorQue(tMayor)}")
    }
    println()

    val tMenor = leerTiempo(scanner, "Introduce tiempo para ver si t1 es MENOR que él (H M S)")
    if (tMenor != null) {
        println("¿Es t1 ($t1) MENOR que $tMenor? ${t1.esMenorQue(tMenor)}")
    }
    println()

    scanner.close()
}


fun main() {
    ejercicio4_1_Rectangulo()
    ejercicio4_2_Persona()
    ejercicio4_3_ActualizacionPersona()
    ejercicio4_4_Coche()
    ejercicio4_5_Tiempo()
}
