import model.Asalariado
import model.Autonomo
import model.Directivo
import model.Jefe
import model.Persona
import model.Sindicato
import model.Trabajador

fun main() {
    //var trabajador = Trabajador ("Oscar", "Pastor" , "1234A", 40000, 911111, "oscar@gmail.com", true)
    val asalariado = Asalariado("OscarAs", "Pastor", "1234A", 40000.00, 10, 0.21)
    val autonomo = Autonomo("OscarAU", "Pastor", "1234B", 40000.00, 10, true)
    val jefe = Jefe("OscarJf", "Pastor", "1234C", 40000.00, 5)
    val directivo = Directivo("oscarDir", " Pastor", "1234D", 688785455, "oscar@gmail.com", 45000)

    val trabajadores = arrayListOf<Persona>(autonomo, asalariado, jefe, directivo)

    //asalariado.aumento()
    //asalariado.mostrarDatos()
    //autonomo.pedirDescuento(5.00)
    //jefe.incrementarResponsabilidad()
    //jefe.decrementoResponsabilidad()

    /*trabajadores.forEach {
        it.mostrarDatos()
        if (it is Trabajador){
            println("Calculando salario del trabajador: ${it.calcularSalarioNeto()}")
        }
    }*/


    trabajadores.forEach {
        if (it is Sindicato){
            it.bajarSueldo(trabajadores.filter { it !is Directivo }
                    as ArrayList<Trabajador>)
        }
    }

        do {
            var opcion : Int = readln().toInt()
            when(opcion){
                1 -> {
                    println("1. Registrar trabajo")
                }
                2 -> {
                    println("")
                }
                3 ->{println("Saliendo... ")}
                else -> {
                    println("No se recoge esa opcion")
                }
            }
        }while (opcion!=3)




    // Para poder bajar sueldos un jefe solo bajara los sueldos
    // a los trabajadores que no son jefes
    // la cantidad de salario que puede bajar es del 10%

    // en caso de ser directivo le podre bajar el sueldo a todos los trabajadores
    // incluido a los jefes un 20% a los asalariados/ autonomos
    // un 10% a los jefes
}