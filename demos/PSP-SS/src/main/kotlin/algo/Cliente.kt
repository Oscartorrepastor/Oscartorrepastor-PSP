package algo

fun main() {
    try {
        val registro = java.rmi.registry.LocateRegistry.getRegistry("192.168.2.109", 2020)
        val sumaRemota = registro.lookup("SumaRemota") as SumRemota

        val a = 5
        val b = 7
        val resultado = sumaRemota.suma(a, b)

        println("La suma de $a y $b es: $resultado")
    } catch (e: Exception) {
        e.printStackTrace()
    }
}