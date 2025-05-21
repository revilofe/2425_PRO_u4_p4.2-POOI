package org.iesra

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotSame
import kotlin.test.assertNull
import kotlin.test.assertSame
import kotlin.test.assertTrue

class TiempoTest {

    // --- Pruebas del Constructor (Éxito y Normalización) ---
    @Test
    fun `constructor con hora, minuto y segundo validos`() {
        val t = Tiempo(10, 30, 15)
        assertEquals(10, t.hora)
        assertEquals(30, t.minuto)
        assertEquals(15, t.segundo)
    }

    @Test
    fun `constructor con hora y minuto validos (segundo por defecto)`() {
        val t = Tiempo(10, 30)
        assertEquals(10, t.hora)
        assertEquals(30, t.minuto)
        assertEquals(0, t.segundo)
    }

    @Test
    fun `constructor con hora valida (minuto y segundo por defecto)`() {
        val t = Tiempo(10)
        assertEquals(10, t.hora)
        assertEquals(0, t.minuto)
        assertEquals(0, t.segundo)
    }

    @Test
    fun `constructor normalizacion de segundos`() {
        val t = Tiempo(10, 15, 90) // 90s = 1m 30s
        assertEquals(10, t.hora)
        assertEquals(16, t.minuto)
        assertEquals(30, t.segundo)
    }

    @Test
    fun `constructor normalizacion de minutos`() {
        val t = Tiempo(10, 70, 15) // 70m = 1h 10m
        assertEquals(11, t.hora)
        assertEquals(10, t.minuto)
        assertEquals(15, t.segundo)
    }

    @Test
    fun `constructor normalizacion combinada`() {
        val t = Tiempo(1, 70, 90) // 90s = 1m 30s; 70m+1m = 71m = 1h 11m; 1h+1h = 2h
        assertEquals(2, t.hora)
        assertEquals(11, t.minuto)
        assertEquals(30, t.segundo)
    }
    
    @Test
    fun `constructor normalizacion que lleva a hora limite`() {
        val t = Tiempo(22, 118, 120) // 120s = 2m; 118m+2m = 120m = 2h; 22h+2h = 24h -> debe fallar
        assertThrows<IllegalArgumentException> {
             Tiempo(22, 118, 120)
        }
    }

    @Test
    fun `constructor con tiempo maximo valido`() {
        val t = Tiempo(23, 59, 59)
        assertEquals(23, t.hora)
        assertEquals(59, t.minuto)
        assertEquals(59, t.segundo)
    }

    // --- Pruebas del Constructor (Fallos - IllegalArgumentException) ---
    @Test
    fun `fallo constructor hora mayor o igual a 24 (despues de normalizacion)`() {
        val exception1 = assertThrows<IllegalArgumentException> {
            Tiempo(24, 0, 0)
        }
        assertEquals("La hora no puede ser >= 24 (después de ajustes).", exception1.message)

        val exception2 = assertThrows<IllegalArgumentException> {
            Tiempo(23, 60, 0) // Normaliza a 24h
        }
        assertEquals("La hora no puede ser >= 24 (después de ajustes).", exception2.message)

        val exception3 = assertThrows<IllegalArgumentException> {
            Tiempo(23, 59, 60) // Normaliza a 24h
        }
        assertEquals("La hora no puede ser >= 24 (después de ajustes).", exception3.message)
    }

    @Test
    fun `fallo constructor hora negativa`() {
        val exception = assertThrows<IllegalArgumentException> {
            Tiempo(-1, 0, 0)
        }
        assertEquals("La hora no puede ser negativa.", exception.message)
    }

    @Test
    fun `fallo constructor minuto negativo`() {
        val exception = assertThrows<IllegalArgumentException> {
            Tiempo(10, -5, 0)
        }
        assertEquals("El minuto no puede ser negativo.", exception.message)
    }

    @Test
    fun `fallo constructor segundo negativo`() {
        val exception = assertThrows<IllegalArgumentException> {
            Tiempo(10, 0, -5)
        }
        assertEquals("El segundo no puede ser negativo.", exception.message)
    }

    // --- Prueba de toString() ---
    @Test
    fun `toString formato correcto`() {
        val t1 = Tiempo(8, 5, 30)
        assertEquals("08h 05m 30s", t1.toString())

        val t2 = Tiempo(0, 0, 0)
        assertEquals("00h 00m 00s", t2.toString())

        val t3 = Tiempo(23, 59, 59)
        assertEquals("23h 59m 59s", t3.toString())
    }

    // --- Pruebas de Métodos de Manipulación ---

    // incrementar(t: Tiempo)
    @Test
    fun `incrementar valido`() {
        val t1 = Tiempo(10, 30, 0)
        val tInc = Tiempo(1, 15, 30)
        assertTrue(t1.incrementar(tInc))
        assertEquals(11, t1.hora)
        assertEquals(45, t1.minuto)
        assertEquals(30, t1.segundo)
    }

    @Test
    fun `incrementar a limite 23 59 59`() {
        val t1 = Tiempo(23, 59, 58)
        val tInc = Tiempo(0, 0, 1)
        assertTrue(t1.incrementar(tInc))
        assertEquals(23, t1.hora)
        assertEquals(59, t1.minuto)
        assertEquals(59, t1.segundo)
    }

    @Test
    fun `incrementar excede limite (no cambia)`() {
        val t1 = Tiempo(23, 59, 58)
        val tInc = Tiempo(0, 0, 2) // Esto llevaría a 24:00:00
        val originalH = t1.hora
        val originalM = t1.minuto
        val originalS = t1.segundo

        assertFalse(t1.incrementar(tInc))
        assertEquals(originalH, t1.hora, "La hora no debería cambiar si el incremento falla")
        assertEquals(originalM, t1.minuto, "El minuto no debería cambiar si el incremento falla")
        assertEquals(originalS, t1.segundo, "El segundo no debería cambiar si el incremento falla")
    }

    // decrementar(t: Tiempo)
    @Test
    fun `decrementar valido`() {
        val t1 = Tiempo(10, 30, 0)
        val tDec = Tiempo(1, 15, 30)
        assertTrue(t1.decrementar(tDec))
        assertEquals(9, t1.hora)
        assertEquals(14, t1.minuto)
        assertEquals(30, t1.segundo)
    }

    @Test
    fun `decrementar a limite 00 00 00`() {
        val t1 = Tiempo(0, 0, 1)
        val tDec = Tiempo(0, 0, 1)
        assertTrue(t1.decrementar(tDec))
        assertEquals(0, t1.hora)
        assertEquals(0, t1.minuto)
        assertEquals(0, t1.segundo)
    }

    @Test
    fun `decrementar excede limite (no cambia)`() {
        val t1 = Tiempo(0, 0, 1)
        val tDec = Tiempo(0, 0, 2) // Esto llevaría a -00:00:01
        val originalH = t1.hora
        val originalM = t1.minuto
        val originalS = t1.segundo

        assertFalse(t1.decrementar(tDec))
        assertEquals(originalH, t1.hora, "La hora no debería cambiar si el decremento falla")
        assertEquals(originalM, t1.minuto, "El minuto no debería cambiar si el decremento falla")
        assertEquals(originalS, t1.segundo, "El segundo no debería cambiar si el decremento falla")
    }

    // sumar(t: Tiempo)
    @Test
    fun `sumar valido`() {
        val t1 = Tiempo(10, 30, 0)
        val t2 = Tiempo(1, 15, 30)
        val resultado = t1.sumar(t2)
        assertNotNull(resultado)
        assertEquals(11, resultado.hora)
        assertEquals(45, resultado.minuto)
        assertEquals(30, resultado.segundo)
    }

    @Test
    fun `sumar excede limite (retorna null)`() {
        val t1 = Tiempo(23, 0, 0)
        val t2 = Tiempo(1, 0, 0) // Suma sería 24:00:00
        val resultado = t1.sumar(t2)
        assertNull(resultado)
    }

    // restar(t: Tiempo)
    @Test
    fun `restar valido`() {
        val t1 = Tiempo(10, 30, 0)
        val t2 = Tiempo(1, 15, 30)
        val resultado = t1.restar(t2)
        assertNotNull(resultado)
        assertEquals(9, resultado.hora)
        assertEquals(14, resultado.minuto)
        assertEquals(30, resultado.segundo)
    }

    @Test
    fun `restar excede limite (retorna null)`() {
        val t1 = Tiempo(1, 0, 0)
        val t2 = Tiempo(1, 0, 1) // Resta sería -00:00:01
        val resultado = t1.restar(t2)
        assertNull(resultado)
    }

    // --- Pruebas de Métodos de Comparación ---

    // comparar(t: Tiempo)
    @Test
    fun `comparar funciona correctamente`() {
        val t1 = Tiempo(10, 0, 0)
        val t2 = Tiempo(12, 0, 0)
        val t3 = Tiempo(10, 0, 0)

        assertEquals(-1, t1.comparar(t2), "t1 debería ser menor que t2")
        assertEquals(1, t2.comparar(t1), "t2 debería ser mayor que t1")
        assertEquals(0, t1.comparar(t3), "t1 debería ser igual que t3")
    }

    // esMayorQue(t: Tiempo)
    @Test
    fun `esMayorQue funciona correctamente`() {
        val tBase = Tiempo(10, 0, 0)
        val tMenor = Tiempo(9, 59, 59)
        val tIgual = Tiempo(10, 0, 0)
        val tMayor = Tiempo(10, 0, 1)

        assertTrue(tBase.esMayorQue(tMenor))
        assertFalse(tBase.esMayorQue(tIgual))
        assertFalse(tBase.esMayorQue(tMayor))
    }

    // esMenorQue(t: Tiempo)
    @Test
    fun `esMenorQue funciona correctamente`() {
        val tBase = Tiempo(10, 0, 0)
        val tMenor = Tiempo(9, 59, 59)
        val tIgual = Tiempo(10, 0, 0)
        val tMayor = Tiempo(10, 0, 1)

        assertFalse(tBase.esMenorQue(tMenor))
        assertFalse(tBase.esMenorQue(tIgual))
        assertTrue(tBase.esMenorQue(tMayor))
    }

    // --- Pruebas de Métodos de Copia ---

    // copiar()
    @Test
    fun `copiar() crea nueva instancia con mismos valores`() {
        val tOriginal = Tiempo(15, 30, 45)
        val tCopia = tOriginal.copiar()

        assertEquals(tOriginal.hora, tCopia.hora)
        assertEquals(tOriginal.minuto, tCopia.minuto)
        assertEquals(tOriginal.segundo, tCopia.segundo)
        assertNotSame(tOriginal, tCopia, "La copia debe ser una instancia diferente")
    }

    // copiar(t: Tiempo)
    @Test
    fun `copiar(t Tiempo) modifica objeto actual`() {
        val tDestino = Tiempo(1, 2, 3)
        val tFuente = Tiempo(10, 20, 30)

        tDestino.copiar(tFuente)

        assertEquals(tFuente.hora, tDestino.hora)
        assertEquals(tFuente.minuto, tDestino.minuto)
        assertEquals(tFuente.segundo, tDestino.segundo)
        // No es necesario que sean la misma instancia, solo que los valores se copien.
    }
}
