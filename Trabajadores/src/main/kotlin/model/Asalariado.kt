package model

import kotlin.random.Random

class Asalariado(nombre: String, apellido: String, dni: String, salario: Double, var nPagas: Int, var irpf: Double) :
    Trabajador(nombre, apellido, dni, salario) {
    override fun calcularSalarioNeto(): Double {
        //Calcula su salirio neto - lo que te quitan de irpf
        return salario - (salario * irpf)
    }

    // un asalariado puede pedir un aumento
    // se genera un aleatorio entre 1-10
    // en caso de generar un numero <5 no se le da
    // en caso de generar >=5 se le da el aumento

    fun aumento() {
        val num: Int = (1..10).random()
        if (num >= 5) {
            salario *= 1.1
        } else
            println("No se te va a subir el salaro")
    }

    override fun mostrarDatos() {
        super.mostrarDatos()
        println("nPagas = ${nPagas}")
        println("IRPF = ${irpf}")
    }

}