package org.iesra

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.text.DecimalFormat
import kotlin.math.abs
import kotlin.test.*

class PersonaTest {

    private fun Double.assertEquals(other: Double, tolerance: Double = 0.001) {
        assertTrue(abs(this - other) < tolerance, "Expected $other, but got $this (tolerance $tolerance)")
    }

    // --- Pruebas del Constructor Primario ---
    @Test
    fun `constructor primario creacion exitosa`() {
        val persona = Persona(peso = 70.0, alturaInicial = 1.75)
        assertNull(persona.nombre, "El nombre debería ser null")
        assertEquals(70.0, persona.peso)
        assertEquals(1.75, persona.altura)
        (70.0 / (1.75 * 1.75)).assertEquals(persona.imc)
    }

    @Test
    fun `constructor primario fallo con peso igual a 0`() {
        val exception = assertThrows<IllegalArgumentException> {
            Persona(peso = 0.0, alturaInicial = 1.75)
        }
        assertEquals("El peso debe ser mayor que 0", exception.message)
    }

    @Test
    fun `constructor primario fallo con peso negativo`() {
        val exception = assertThrows<IllegalArgumentException> {
            Persona(peso = -70.0, alturaInicial = 1.75)
        }
        assertEquals("El peso debe ser mayor que 0", exception.message)
    }

    @Test
    fun `constructor primario fallo con altura igual a 0`() {
        val exception = assertThrows<IllegalArgumentException> {
            Persona(peso = 70.0, alturaInicial = 0.0)
        }
        assertEquals("La altura debe ser mayor que 0", exception.message)
    }

    @Test
    fun `constructor primario fallo con altura negativa`() {
        val exception = assertThrows<IllegalArgumentException> {
            Persona(peso = 70.0, alturaInicial = -1.75)
        }
        assertEquals("La altura debe ser mayor que 0", exception.message)
    }

    // --- Pruebas del Constructor Secundario ---
    @Test
    fun `constructor secundario creacion exitosa`() {
        val persona = Persona(nombre = "Juan", peso = 80.0, altura = 1.80)
        assertEquals("Juan", persona.nombre)
        assertEquals(80.0, persona.peso)
        assertEquals(1.80, persona.altura)
        (80.0 / (1.80 * 1.80)).assertEquals(persona.imc)
    }

    @Test
    fun `constructor secundario fallo con peso invalido`() {
        val exception = assertThrows<IllegalArgumentException> {
            Persona(nombre = "Ana", peso = 0.0, altura = 1.60)
        }
        assertEquals("El peso debe ser mayor que 0", exception.message)
    }

    @Test
    fun `constructor secundario fallo con altura invalida`() {
        val exception = assertThrows<IllegalArgumentException> {
            Persona(nombre = "Luis", peso = 75.0, altura = -1.70)
        }
        assertEquals("La altura debe ser mayor que 0", exception.message)
    }

    // --- Pruebas de Cálculo de IMC ---
    @Test
    fun `imc calculado correctamente`() {
        val p1 = Persona(70.0, 1.75) // IMC = 22.86
        (22.857).assertEquals(p1.imc, 0.001)

        val p2 = Persona(50.0, 1.60) // IMC = 19.53
        (19.531).assertEquals(p2.imc, 0.001)

        val p3 = Persona(90.0, 1.70) // IMC = 31.14
        (31.141).assertEquals(p3.imc, 0.001)
    }

    @Test
    fun `imc se actualiza al modificar altura`() {
        val persona = Persona(70.0, 1.75)
        val imcInicial = persona.imc
        persona.altura = 1.80
        val imcFinal = persona.imc
        assertNotEquals(imcInicial, imcFinal, "El IMC debería cambiar al modificar la altura")
        (70.0 / (1.80 * 1.80)).assertEquals(imcFinal)
    }

    // --- Pruebas de Modificación de Atributos ---
    @Test
    fun `modificar nombre`() {
        val persona = Persona(70.0, 1.75)
        assertNull(persona.nombre)
        persona.nombre = "David"
        assertEquals("David", persona.nombre)
        persona.nombre = "Roberto"
        assertEquals("Roberto", persona.nombre)
        persona.nombre = null
        assertNull(persona.nombre)
    }

    @Test
    fun `modificar altura con valor valido`() {
        val persona = Persona(70.0, 1.75)
        persona.altura = 1.80
        assertEquals(1.80, persona.altura)
        (70.0 / (1.80 * 1.80)).assertEquals(persona.imc)
    }

    @Test
    fun `modificar altura con valor igual a 0`() {
        val persona = Persona(70.0, 1.75)
        val exception = assertThrows<IllegalArgumentException> {
            persona.altura = 0.0
        }
        assertEquals("La altura debe ser mayor que 0", exception.message)
        assertEquals(1.75, persona.altura, "La altura no debería haber cambiado")
    }

    @Test
    fun `modificar altura con valor negativo`() {
        val persona = Persona(70.0, 1.75)
        val exception = assertThrows<IllegalArgumentException> {
            persona.altura = -1.80
        }
        assertEquals("La altura debe ser mayor que 0", exception.message)
        assertEquals(1.75, persona.altura, "La altura no debería haber cambiado")
    }

    // --- Pruebas del Método equals() ---
    @Test
    fun `equals funciona correctamente`() {
        val p1 = Persona("Juan", 70.0, 1.75)
        val p2 = Persona("Juan", 70.0, 1.75)
        val p3 = Persona("Ana", 70.0, 1.75)    // Nombre diferente
        val p4 = Persona("Juan", 75.0, 1.75)    // Peso diferente
        val p5 = Persona("Juan", 70.0, 1.80)    // Altura diferente
        val p6 = Persona(null, 70.0, 1.75)
        val p7 = Persona(null, 70.0, 1.75)

        assertTrue(p1.equals(p2), "p1 debería ser igual a p2")
        assertEquals(p1.hashCode(), p2.hashCode(), "Los hashCodes de p1 y p2 deberían ser iguales")

        assertFalse(p1.equals(p3), "p1 no debería ser igual a p3 (nombre diferente)")
        assertFalse(p1.equals(p4), "p1 no debería ser igual a p4 (peso diferente)")
        assertFalse(p1.equals(p5), "p1 no debería ser igual a p5 (altura diferente)")

        assertFalse(p1.equals(null), "p1 no debería ser igual a null")
        assertFalse(p1.equals(Any()), "p1 no debería ser igual a un objeto de tipo Any")
        
        assertTrue(p6.equals(p7), "p6 debería ser igual a p7 (ambos nombres null)")
        assertEquals(p6.hashCode(), p7.hashCode(), "Los hashCodes de p6 y p7 deberían ser iguales")
        assertFalse(p1.equals(p6), "p1 no debería ser igual a p6 (p1.nombre no es null, p6.nombre es null)")
    }

    // --- Pruebas de Métodos Adicionales ---
    @Test
    fun `saludar funciona correctamente`() {
        val pConNombre = Persona("Ana", 60.0, 1.65)
        assertEquals("Hola, soy Ana.", pConNombre.saludar())

        val pSinNombre = Persona(60.0, 1.65)
        assertEquals("Hola.", pSinNombre.saludar())
    }

    @Test
    fun `alturaEncimaMedia funciona correctamente`() {
        val pAlta = Persona(70.0, 1.80) // > 1.75
        assertTrue(pAlta.alturaEncimaMedia())

        val pMedia = Persona(70.0, 1.75) // == 1.75
        assertTrue(pMedia.alturaEncimaMedia())

        val pBaja = Persona(70.0, 1.70) // < 1.75
        assertFalse(pBaja.alturaEncimaMedia())
    }

    @Test
    fun `pesoEncimaMedia funciona correctamente`() {
        val pPesada = Persona(75.0, 1.70) // > 70.0
        assertTrue(pPesada.pesoEncimaMedia())

        val pMedio = Persona(70.0, 1.70) // == 70.0
        assertTrue(pMedio.pesoEncimaMedia())

        val pLigera = Persona(65.0, 1.70) // < 70.0
        assertFalse(pLigera.pesoEncimaMedia())
    }

    @Test
    fun `obtenerDescImc funciona correctamente`() {
        // IMC < 18.5 -> "peso insuficiente"
        val pInsuficiente = Persona(50.0, 1.75) // IMC = 16.33
        assertEquals("peso insuficiente", pInsuficiente.obtenerDescImc())

        // IMC < 25.0 -> "peso saludable"
        val pSaludable = Persona(70.0, 1.75) // IMC = 22.86
        assertEquals("peso saludable", pSaludable.obtenerDescImc())

        // IMC < 30.0 -> "sobrepeso"
        val pSobrepeso = Persona(85.0, 1.75) // IMC = 27.76
        assertEquals("sobrepeso", pSobrepeso.obtenerDescImc())

        // IMC >= 30.0 -> "obesidad"
        val pObesidad = Persona(100.0, 1.75) // IMC = 32.65
        assertEquals("obesidad", pObesidad.obtenerDescImc())
        val pObesidadLimite = Persona(91.87, 1.75) // IMC = 30.00 (aprox)
        assertEquals("obesidad", pObesidadLimite.obtenerDescImc())
    }

    @Test
    fun `obtenerDesc funciona correctamente`() {
        val df = DecimalFormat("#.##")

        val p1 = Persona("Luis", 75.0, 1.82) // Encima media altura, encima media peso, saludable
        // IMC = 75 / (1.82*1.82) = 22.6
        var imcDesc = "saludable"
        var expectedDesc = "Luis con una altura de ${df.format(1.82)}m (Por encima de la media) y un peso ${df.format(75.0)}kg (Por encima de la media) tiene un IMC de ${df.format(p1.imc)} ($imcDesc)."
        assertEquals(expectedDesc, p1.obtenerDesc())

        val p2 = Persona(null, 50.0, 1.60) // Debajo media altura, debajo media peso, saludable
        // IMC = 50 / (1.60*1.60) = 19.53
        imcDesc = "peso saludable"
        expectedDesc = "Una persona con una altura de ${df.format(1.60)}m (Por debajo de la media) y un peso ${df.format(50.0)}kg (Por debajo de la media) tiene un IMC de ${df.format(p2.imc)} ($imcDesc)."
        assertEquals(expectedDesc, p2.obtenerDesc())

        val p3 = Persona("Ana", 90.0, 1.70) // Debajo media altura, encima media peso, obesidad
        // IMC = 90 / (1.70*1.70) = 31.14
        imcDesc = "obesidad"
        expectedDesc = "Ana con una altura de ${df.format(1.70)}m (Por debajo de la media) y un peso ${df.format(90.0)}kg (Por encima de la media) tiene un IMC de ${df.format(p3.imc)} ($imcDesc)."
        assertEquals(expectedDesc, p3.obtenerDesc())
    }

    // --- Prueba de toString() ---
    @Test
    fun `toString formato correcto`() {
        val df = DecimalFormat("#.##")
        val p1 = Persona("Laura", 65.0, 1.72)
        val expectedToString1 = "Persona(nombre=Laura, peso=${df.format(65.0)}, altura=${df.format(1.72)}, imc=${df.format(p1.imc)})"
        assertEquals(expectedToString1, p1.toString())

        val p2 = Persona(null, 80.0, 1.65)
        val expectedToString2 = "Persona(nombre=null, peso=${df.format(80.0)}, altura=${df.format(1.65)}, imc=${df.format(p2.imc)})"
        assertEquals(expectedToString2, p2.toString())
    }
}
