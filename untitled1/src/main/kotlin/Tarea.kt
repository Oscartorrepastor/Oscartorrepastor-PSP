import java.util.concurrent.Executor
import java.util.concurrent.Executors

fun main() {
    val ejecutar = Executors.newSingleThreadExecutor()
    ejecutar.execute {
        println("Tarea en segundo plano ejecutándose...")
        // Simular una tarea larga
        Thread.sleep(2000)
        println("Tarea en segundo plano completada.")
    }
    ejecutar.shutdown()
}