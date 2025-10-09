package model

class Directivo(nombre: String, apellido: String, dni: String, telefono: Int, correo: String, var pAcciones: Int) :
    Persona(nombre, apellido, dni, telefono, correo), Sindicato {


    fun venderAcciones(acciones: Int) {
        if (acciones > pAcciones)
            println("No puedes vender tantas acciones")
        else {
            pAcciones -= acciones
            println(" nª accioenes actualizadas")
        }
    }

    override fun mostrarDatos() {
        super.mostrarDatos()
        println("pAcciones = ${pAcciones}")
    }

    override fun bajarSueldo(lista: ArrayList<Trabajador>): Boolean {
        lista.forEach {
            if (it is Jefe){
                it.salario = it.salario*0.9
            }else{
                it.salario = it.salario*0.8
            }
        }
        return true
    }

    override fun calcularBeneficios(): Double {
        println("Como directivo vas a calcular los beneficios")
        return 0.0
    }
}