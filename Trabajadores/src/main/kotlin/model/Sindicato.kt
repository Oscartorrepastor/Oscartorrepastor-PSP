package model

interface Sindicato {
    fun bajarSueldo(lista: ArrayList<Trabajador>): Boolean
    fun calcularBeneficios(): Double
}