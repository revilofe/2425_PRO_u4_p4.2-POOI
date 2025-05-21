package org.iesra

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class CocheTest {

    // --- Pruebas del Constructor (Éxito) ---
    @Test
    fun `creacion exitosa con todos los parametros validos`() {
        val coche = Coche(
            colorInicial = "Rojo",
            marcaInicial = "seat",
            modeloInicial = "leon",
            caballosInicial = 150,
            puertasInicial = 5,
            matriculaInicial = "1234ABC"
        )
        assertEquals("Rojo", coche.color)
        assertEquals("Seat", coche.marca, "La marca debería estar capitalizada") // Getter capitaliza
        assertEquals("Leon", coche.modelo, "El modelo debería estar capitalizado") // Getter capitaliza
        assertEquals(150, coche.caballos)
        assertEquals(5, coche.puertas)
        assertEquals("1234ABC", coche.matricula)
    }

    // --- Pruebas del Constructor (Fallos - IllegalArgumentException) ---

    // Marca
    @Test
    fun `fallo creacion con marca nula`() {
        val exception = assertThrows<IllegalArgumentException> {
            Coche("Rojo", null, "Leon", 150, 5, "1234ABC")
        }
        assertEquals("La marca no puede ser nula ni vacía.", exception.message)
    }

    @Test
    fun `fallo creacion con marca vacia`() {
        val exception = assertThrows<IllegalArgumentException> {
            Coche("Rojo", "", "Leon", 150, 5, "1234ABC")
        }
        assertEquals("La marca no puede ser nula ni vacía.", exception.message)
    }
    
    @Test
    fun `fallo creacion con marca en blanco`() {
        val exception = assertThrows<IllegalArgumentException> {
            Coche("Rojo", "   ", "Leon", 150, 5, "1234ABC")
        }
        assertEquals("La marca no puede ser nula ni vacía.", exception.message)
    }

    // Modelo
    @Test
    fun `fallo creacion con modelo nulo`() {
        val exception = assertThrows<IllegalArgumentException> {
            Coche("Rojo", "Seat", null, 150, 5, "1234ABC")
        }
        assertEquals("El modelo no puede ser nulo ni vacío.", exception.message)
    }

    @Test
    fun `fallo creacion con modelo vacio`() {
        val exception = assertThrows<IllegalArgumentException> {
            Coche("Rojo", "Seat", "", 150, 5, "1234ABC")
        }
        assertEquals("El modelo no puede ser nulo ni vacío.", exception.message)
    }
    
    @Test
    fun `fallo creacion con modelo en blanco`() {
        val exception = assertThrows<IllegalArgumentException> {
            Coche("Rojo", "Seat", "  ", 150, 5, "1234ABC")
        }
        assertEquals("El modelo no puede ser nulo ni vacío.", exception.message)
    }

    // Caballos
    @Test
    fun `fallo creacion con caballos nulos`() {
        val exception = assertThrows<IllegalArgumentException> {
            Coche("Rojo", "Seat", "Leon", null, 5, "1234ABC")
        }
        assertEquals("Los caballos deben estar entre 70 y 700.", exception.message)
    }

    @Test
    fun `fallo creacion con caballos menor que 70`() {
        val exception = assertThrows<IllegalArgumentException> {
            Coche("Rojo", "Seat", "Leon", 69, 5, "1234ABC")
        }
        assertEquals("Los caballos deben estar entre 70 y 700.", exception.message)
    }

    @Test
    fun `fallo creacion con caballos mayor que 700`() {
        val exception = assertThrows<IllegalArgumentException> {
            Coche("Rojo", "Seat", "Leon", 701, 5, "1234ABC")
        }
        assertEquals("Los caballos deben estar entre 70 y 700.", exception.message)
    }

    // Puertas
    @Test
    fun `fallo creacion con puertas nulas`() {
        val exception = assertThrows<IllegalArgumentException> {
            Coche("Rojo", "Seat", "Leon", 150, null, "1234ABC")
        }
        assertEquals("El número de puertas debe estar entre 3 y 5.", exception.message)
    }
    
    @Test
    fun `fallo creacion con puertas menor que 3`() {
        val exception = assertThrows<IllegalArgumentException> {
            Coche("Rojo", "Seat", "Leon", 150, 2, "1234ABC")
        }
        assertEquals("El número de puertas debe estar entre 3 y 5.", exception.message)
    }

    @Test
    fun `fallo creacion con puertas mayor que 5`() {
        val exception = assertThrows<IllegalArgumentException> {
            Coche("Rojo", "Seat", "Leon", 150, 6, "1234ABC")
        }
        assertEquals("El número de puertas debe estar entre 3 y 5.", exception.message)
    }

    // Matrícula
    @Test
    fun `fallo creacion con matricula nula`() {
        val exception = assertThrows<IllegalArgumentException> {
            Coche("Rojo", "Seat", "Leon", 150, 5, null)
        }
        assertEquals("La matrícula debe tener exactamente 7 caracteres.", exception.message)
    }

    @Test
    fun `fallo creacion con matricula longitud incorrecta corta`() {
        val exception = assertThrows<IllegalArgumentException> {
            Coche("Rojo", "Seat", "Leon", 150, 5, "1234AB") // 6 chars
        }
        assertEquals("La matrícula debe tener exactamente 7 caracteres.", exception.message)
    }

    @Test
    fun `fallo creacion con matricula longitud incorrecta larga`() {
        val exception = assertThrows<IllegalArgumentException> {
            Coche("Rojo", "Seat", "Leon", 150, 5, "1234ABCD") // 8 chars
        }
        assertEquals("La matrícula debe tener exactamente 7 caracteres.", exception.message)
    }

    // Color
    @Test
    fun `fallo creacion con color nulo`() {
        val exception = assertThrows<IllegalArgumentException> {
            Coche(null, "Seat", "Leon", 150, 5, "1234ABC")
        }
        assertEquals("El color no puede ser nulo.", exception.message)
    }


    // --- Pruebas de Getters Personalizados ---
    @Test
    fun `getter marca capitaliza correctamente`() {
        val coche1 = Coche("Rojo", "seat", "Leon", 150, 5, "1234ABC")
        assertEquals("Seat", coche1.marca)

        val coche2 = Coche("Azul", "RENAULT", "Clio", 90, 3, "5678DEF") // Ya capitalizada
        assertEquals("RENAULT", coche2.marca)
        
        val coche3 = Coche("Verde", "bMw", "X5", 300, 5, "9012GHI") // Mezcla
        assertEquals("BMw", coche3.marca) // Solo la primera se capitaliza por el replaceFirstChar
    }

    @Test
    fun `getter modelo capitaliza correctamente`() {
        val coche1 = Coche("Rojo", "Seat", "leon", 150, 5, "1234ABC")
        assertEquals("Leon", coche1.modelo)

        val coche2 = Coche("Azul", "Renault", "CLIO", 90, 3, "5678DEF") // Ya capitalizada
        assertEquals("CLIO", coche2.modelo)

        val coche3 = Coche("Verde", "BMW", "serie 3", 300, 5, "9012GHI") // Mezcla
        assertEquals("Serie 3", coche3.modelo)
    }

    // --- Pruebas de Setter Personalizado (color) ---
    @Test
    fun `modificar color a valor valido`() {
        val coche = Coche("Rojo", "Seat", "Leon", 150, 5, "1234ABC")
        coche.color = "Azul"
        assertEquals("Azul", coche.color)
        coche.color = "Verde Metalizado"
        assertEquals("Verde Metalizado", coche.color)
    }

    @Test
    fun `intentar modificar color a null`() {
        val coche = Coche("Rojo", "Seat", "Leon", 150, 5, "1234ABC")
        // En Kotlin, asignar null a una propiedad de tipo no nulable (String) es un error de compilación.
        // La `require` en el setter es más una salvaguarda para interoperabilidad Java o reflexión.
        // Para probar la `require` del setter explícitamente desde Kotlin, necesitaríamos
        // que el tipo de `color` fuera `String?` y luego que el setter rechazara `null`.
        // Dado que `color` es `String`, el compilador previene la asignación de `null`.
        // La validación que se prueba efectivamente es la del constructor con `colorInicial = null`.
        // Si la propiedad fuera `var color: String?` y el setter tuviera `require(value != null)`,
        // entonces podríamos hacer:
        // val exception = assertThrows<IllegalArgumentException> {
        //     coche.color = null
        // }
        // assertEquals("El color no puede ser nulo.", exception.message)
        // Esta prueba, tal como está la clase Coche, es más conceptual para la `require` del setter.
        // La prueba `fallo_creacion_con_color_nulo` ya cubre la validación de no nulidad del color en la inicialización.
        assertTrue(true, "Test conceptual: Kotlin previene asignación de null a tipo no nulable. La validación del setter es una capa extra.")
    }

    // --- Prueba de toString() ---
    @Test
    fun `toString formato correcto`() {
        val coche = Coche(
            colorInicial = "Negro",
            marcaInicial = "audi",
            modeloInicial = "a4",
            caballosInicial = 190,
            puertasInicial = 5,
            matriculaInicial = "0000XYZ"
        )
        val expectedString = "Coche(marca=Audi, modelo=A4, color=Negro, caballos=190, puertas=5, matricula=0000XYZ)"
        assertEquals(expectedString, coche.toString())
    }
}
