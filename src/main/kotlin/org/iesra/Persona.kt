package org.iesra

import java.text.DecimalFormat

class Persona(val peso: Double, alturaInicial: Double) {

    var nombre: String? = null
    var altura: Double = alturaInicial
        set(value) {
            require(value > 0) { "La altura debe ser mayor que 0" }
            field = value
        }

    val imc: Double
        get() = peso / (altura * altura)

    init {
        require(peso > 0) { "El peso debe ser mayor que 0" }
        require(altura > 0) { "La altura debe ser mayor que 0" }
    }

    constructor(nombre: String?, peso: Double, altura: Double) : this(peso, altura) {
        this.nombre = nombre
    }

    fun saludar(): String {
        return if (nombre != null) "Hola, soy $nombre." else "Hola."
    }

    fun alturaEncimaMedia(): Boolean {
        return altura >= 1.75
    }

    fun pesoEncimaMedia(): Boolean {
        return peso >= 70.0
    }

    private fun obtenerDescImcInterna(): String { // Lo hacemos privado ya que solo se usa en obtenerDesc()
        return when {
            imc < 18.5 -> "peso insuficiente"
            imc < 25.0 -> "peso saludable"
            imc < 30.0 -> "sobrepeso"
            else -> "obesidad"
        }
    }
    
    fun obtenerDescImc(): String { //Expuesto como publico segun requerimientos
        return obtenerDescImcInterna()
    }

    fun obtenerDesc(): String {
        val df = DecimalFormat("#.##")
        val nombreDesc = nombre ?: "Una persona"
        val alturaDesc = if (alturaEncimaMedia()) "(Por encima de la media)" else "(Por debajo de la media)"
        val pesoDesc = if (pesoEncimaMedia()) "(Por encima de la media)" else "(Por debajo de la media)"
        val imcDesc = obtenerDescImcInterna()
        
        return "$nombreDesc con una altura de ${df.format(altura)}m $alturaDesc y un peso ${df.format(peso)}kg $pesoDesc tiene un IMC de ${df.format(imc)} ($imcDesc)."
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Persona) return false

        if (nombre != other.nombre) return false
        if (peso != other.peso) return false
        if (altura != other.altura) return false

        return true
    }

    override fun hashCode(): Int {
        var result = nombre?.hashCode() ?: 0
        result = 31 * result + peso.hashCode()
        result = 31 * result + altura.hashCode()
        return result
    }

    override fun toString(): String {
        val df = DecimalFormat("#.##")
        return "Persona(nombre=$nombre, peso=${df.format(peso)}, altura=${df.format(altura)}, imc=${df.format(imc)})"
    }
}
