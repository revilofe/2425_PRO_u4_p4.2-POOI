package org.iesra

class Tiempo(horaInicial: Int, minutoInicial: Int = 0, segundoInicial: Int = 0) {

    var hora: Int
    var minuto: Int
    var segundo: Int

    init {
        require(horaInicial >= 0) { "La hora no puede ser negativa." }
        require(minutoInicial >= 0) { "El minuto no puede ser negativo." }
        require(segundoInicial >= 0) { "El segundo no puede ser negativo." }

        var totalSegundos = segundoInicial
        var totalMinutos = minutoInicial + totalSegundos / 60
        this.segundo = totalSegundos % 60

        var totalHoras = horaInicial + totalMinutos / 60
        this.minuto = totalMinutos % 60
        
        require(totalHoras < 24) { "La hora no puede ser >= 24 (después de ajustes)." }
        this.hora = totalHoras
    }

    constructor(hora: Int, minuto: Int) : this(hora, minuto, 0)
    constructor(hora: Int) : this(hora, 0, 0)

    private fun aSegundos(): Int = hora * 3600 + minuto * 60 + segundo

    override fun toString(): String {
        return String.format("%02dh %02dm %02ds", hora, minuto, segundo)
    }

    fun incrementar(t: Tiempo): Boolean {
        val nuevosSegundosTotales = this.aSegundos() + t.aSegundos()
        if (nuevosSegundosTotales >= 24 * 3600) {
            return false // Supera 23:59:59
        }
        
        var tempSegundos = nuevosSegundosTotales
        this.hora = tempSegundos / 3600
        tempSegundos %= 3600
        this.minuto = tempSegundos / 60
        this.segundo = tempSegundos % 60
        return true
    }

    fun decrementar(t: Tiempo): Boolean {
        val nuevosSegundosTotales = this.aSegundos() - t.aSegundos()
        if (nuevosSegundosTotales < 0) {
            return false // Resultado menor que 00:00:00
        }

        var tempSegundos = nuevosSegundosTotales
        this.hora = tempSegundos / 3600
        tempSegundos %= 3600
        this.minuto = tempSegundos / 60
        this.segundo = tempSegundos % 60
        return true
    }

    fun comparar(t: Tiempo): Int {
        return this.aSegundos().compareTo(t.aSegundos())
    }

    fun copiar(): Tiempo {
        return Tiempo(this.hora, this.minuto, this.segundo)
    }

    fun copiar(t: Tiempo) {
        // Re-validación no es estrictamente necesaria si confiamos que 't' es un Tiempo válido
        // pero por seguridad, podríamos hacerlo o simplemente asignar.
        // Optamos por la asignación directa ya que 't' debe ser una instancia válida.
        this.hora = t.hora
        this.minuto = t.minuto
        this.segundo = t.segundo
    }

    fun sumar(t: Tiempo): Tiempo? {
        val totalSegundosSuma = this.aSegundos() + t.aSegundos()
        if (totalSegundosSuma >= 24 * 3600) {
            return null // Supera 23:59:59
        }
        // Convertir segundos totales a h, m, s para el nuevo objeto Tiempo
        // No necesitamos usar el constructor directamente con estos valores porque no requieren ajustes adicionales
        // ya que totalSegundosSuma ya es un valor válido (menor a 24*3600)
        val h = totalSegundosSuma / 3600
        val m = (totalSegundosSuma % 3600) / 60
        val s = totalSegundosSuma % 60
        return Tiempo(h, m, s) // El constructor de Tiempo manejará la asignación
    }

    fun restar(t: Tiempo): Tiempo? {
        val totalSegundosResta = this.aSegundos() - t.aSegundos()
        if (totalSegundosResta < 0) {
            return null // Menor que 00:00:00
        }
        val h = totalSegundosResta / 3600
        val m = (totalSegundosResta % 3600) / 60
        val s = totalSegundosResta % 60
        return Tiempo(h, m, s) // El constructor de Tiempo manejará la asignación
    }
    
    fun esMayorQue(t: Tiempo): Boolean {
        return this.comparar(t) > 0
    }

    fun esMenorQue(t: Tiempo): Boolean {
        return this.comparar(t) < 0
    }
}
