package model

abstract class Trabajador(nombre: String, apellido: String, dni: String, var salario: Double) :
    Persona(nombre, apellido, dni) {
    var seguro: Boolean = false

    constructor(
        nombre: String,
        apellido: String,
        dni: String,
        salario: Double,
        telefono: Int,
        correo: String,
        seguro: Boolean
    ) : this(nombre, apellido, dni, salario) {
        this.seguro = seguro
        this.telefono = telefono
        this.correo = correo
    }

    abstract fun calcularSalarioNeto(): Double

    override fun mostrarDatos() {
        super.mostrarDatos()
        println("El salario es $salario")
        println("El Seguro es $seguro")
    }

}