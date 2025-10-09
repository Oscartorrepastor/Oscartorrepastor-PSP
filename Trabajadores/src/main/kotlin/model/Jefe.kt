package model

class Jefe(nombre: String, apellido: String, dni: String, salario: Double, var nResponsabilidad: Int) :
    Trabajador(nombre, apellido, dni, salario) , Sindicato{


    override fun calcularSalarioNeto(): Double {
        nResponsabilidad = (1..5).random()
        if (nResponsabilidad >= 3) {
            salario + (salario * 1.03)
        } else if (nResponsabilidad < 3) {
            salario - (salario * 1.03)
        }
        return salario
    }

    fun incrementarResponsabilidad(){
        if (nResponsabilidad<5){nResponsabilidad++}
    }
    fun decrementoResponsabilidad(){
        if (nResponsabilidad>5){nResponsabilidad--}
    }

    override fun mostrarDatos() {
        super.mostrarDatos()
        println("nResponsabilidad = ${nResponsabilidad}")
    }

    override fun bajarSueldo(lista: ArrayList<Trabajador>): Boolean {
        lista.forEach {
            if (it !is Jefe){
                it.salario*=0.9
            }
        }
        return true
    }

    override fun calcularBeneficios(): Double {
        println("Como Jefe vas a calcular los beneficios")
        return 0.0
    }
}