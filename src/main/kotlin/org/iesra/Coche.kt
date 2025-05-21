package org.iesra

class Coche(
    // Parámetros del constructor nulables para validación
    colorInicial: String?,
    marcaInicial: String?,
    modeloInicial: String?,
    caballosInicial: Int?,
    puertasInicial: Int?,
    matriculaInicial: String?
) {

    val marca: String
        get() = field.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }

    val modelo: String
        get() = field.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }

    var color: String
        set(value) {
            require(value != null) { "El color no puede ser nulo." }
            field = value
        }

    val caballos: Int
    val puertas: Int
    val matricula: String

    init {
        require(marcaInicial != null && marcaInicial.isNotBlank()) { "La marca no puede ser nula ni vacía." }
        this.marca = marcaInicial

        require(modeloInicial != null && modeloInicial.isNotBlank()) { "El modelo no puede ser nulo ni vacío." }
        this.modelo = modeloInicial

        require(caballosInicial != null && caballosInicial in 70..700) { "Los caballos deben estar entre 70 y 700." }
        this.caballos = caballosInicial

        require(puertasInicial != null && puertasInicial in 3..5) { "El número de puertas debe estar entre 3 y 5." }
        this.puertas = puertasInicial

        require(matriculaInicial != null && matriculaInicial.length == 7) { "La matrícula debe tener exactamente 7 caracteres." }
        this.matricula = matriculaInicial
        
        require(colorInicial != null) { "El color no puede ser nulo." }
        this.color = colorInicial // Llama al setter personalizado
    }

    override fun toString(): String {
        return "Coche(marca=${this.marca}, modelo=${this.modelo}, color=${this.color}, caballos=${this.caballos}, puertas=${this.puertas}, matricula=${this.matricula})"
    }
}
