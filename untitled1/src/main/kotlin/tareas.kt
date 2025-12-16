import java.util.concurrent.Executors

fun main() {
    val ejecutar = Executors.newCachedThreadPool()
    val tareas = listOf(
        Tarea("Tarea 1"),
        Tarea("Tarea 2"),
        Tarea("Tarea 3"),
        Tarea("Tarea 4"),
        Tarea("Tarea 5"),
        Tarea("Tarea 6")
    )
    for (tarea in tareas) {
        ejecutar.execute(tarea)
    }
}

class Tarea (private val nombre: String) : Runnable {
    override fun run() {
        println("$nombre iniciada.")
        for (i in 1..5) {
            println("$nombre: paso $i")
            Thread.sleep(500) // Simular trabajo
        }
    }
}