package model

class Autonomo(nombre: String, apellido: String, dni: String, salario: Double, var cuotass: Int) :
    Trabajador(nombre, apellido, dni, salario) {

    constructor(nombre: String, apellido: String, dni: String, salario: Double, cuotass: Int, seguro: Boolean) : this(
        nombre,
        apellido,
        dni,
        salario,
        cuotass
    ) {
        this.seguro = seguro
    }


    override fun calcularSalarioNeto(): Double {
       return salario - (cuotass * 12)
    }

    fun pedirDescuento(porcentage: Double){
        if (seguro) {
            cuotass = (cuotass - cuotass * porcentage).toInt()
        }
    }

    override fun mostrarDatos() {
        super.mostrarDatos()
        println("cuotass = ${cuotass}")
    }
}