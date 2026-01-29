package algo

import java.rmi.registry.LocateRegistry
import java.rmi.registry.Registry

fun main() {
    val sumaRemota = SumaRemotaImpl()

    try {
        val registro: Registry = LocateRegistry.createRegistry(2020)
        registro.bind("SumaRemota", sumaRemota)
        println("Servidor RMI iniciado en el puerto 2020")
    } catch (e: Exception) {
        e.printStackTrace()
    }
}