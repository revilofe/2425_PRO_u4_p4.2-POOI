package org.iesra

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals

class RectanguloTest {

    @Test
    fun `creacion exitosa con base y altura validas`() {
        val base = 5.0
        val altura = 10.0
        val rect = Rectangulo(base, altura)
        // No se necesita assert explícito si el constructor no lanza excepción
        // Podríamos verificar los valores si tuviéramos getters, pero no son parte de la interfaz pública de Rectangulo
        // La simple creación exitosa es la prueba aquí.
        assertEquals(base, rect.area() / altura, "La base debería ser la esperada") // Verificación indirecta
        assertEquals(altura, rect.area() / base, "La altura debería ser la esperada") // Verificación indirecta
    }

    @Test
    fun `fallo creacion con base igual a 0`() {
        val exception = assertThrows<IllegalArgumentException> {
            Rectangulo(0.0, 5.0)
        }
        assertEquals("La base debe ser mayor que 0", exception.message)
    }

    @Test
    fun `fallo creacion con altura igual a 0`() {
        val exception = assertThrows<IllegalArgumentException> {
            Rectangulo(5.0, 0.0)
        }
        assertEquals("La altura debe ser mayor que 0", exception.message)
    }

    @Test
    fun `fallo creacion con base negativa`() {
        val exception = assertThrows<IllegalArgumentException> {
            Rectangulo(-5.0, 10.0)
        }
        assertEquals("La base debe ser mayor que 0", exception.message)
    }

    @Test
    fun `fallo creacion con altura negativa`() {
        val exception = assertThrows<IllegalArgumentException> {
            Rectangulo(10.0, -5.0)
        }
        assertEquals("La altura debe ser mayor que 0", exception.message)
    }

    @Test
    fun `area calculada correctamente`() {
        val rect = Rectangulo(5.0, 10.0)
        assertEquals(50.0, rect.area(), "El área debe ser 50.0")

        val rect2 = Rectangulo(7.5, 3.0)
        assertEquals(22.5, rect2.area(), "El área debe ser 22.5")
    }

    @Test
    fun `perimetro calculado correctamente`() {
        val rect = Rectangulo(5.0, 10.0)
        assertEquals(30.0, rect.perimetro(), "El perímetro debe ser 30.0")

        val rect2 = Rectangulo(7.5, 3.0)
        assertEquals(21.0, rect2.perimetro(), "El perímetro debe ser 21.0")
    }

    @Test
    fun `toString formato correcto`() {
        val rect = Rectangulo(5.0, 10.0)
        assertEquals("Rectangulo(base=5.0, altura=10.0)", rect.toString(), "El formato de toString no es el esperado")

        val rect2 = Rectangulo(7.5, 3.2)
        assertEquals("Rectangulo(base=7.5, altura=3.2)", rect2.toString(), "El formato de toString no es el esperado para rect2")
    }
}
