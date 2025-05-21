package org.iesra

class Rectangulo(private val base: Double, private val altura: Double) {

    init {
        require(base > 0) { "La base debe ser mayor que 0" }
        require(altura > 0) { "La altura debe ser mayor que 0" }
    }

    fun area(): Double {
        return base * altura
    }

    fun perimetro(): Double {
        return 2 * (base + altura)
    }

    override fun toString(): String {
        return "Rectangulo(base=$base, altura=$altura)"
    }
}
