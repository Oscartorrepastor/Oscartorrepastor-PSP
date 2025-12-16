import java.lang.Thread.sleep
import java.util.concurrent.CountDownLatch

fun main() {
   val interruptor = CountDownLatch(3)
    fun tarea(){
        sleep(1000)
        interruptor.countDown()
        println("Termino una tarea")

    }

    val hilo1 = Thread{
        tarea()
        println("Hilo 1 completado")
    }
    val hilo2 = Thread {
        tarea()
        println("Hilo 2 completado")
    }
    val hilo3 = Thread {
        tarea()
        println("Hilo 3 completado")
    }

    hilo1.start()
    hilo2.start()
    hilo3.start()

}