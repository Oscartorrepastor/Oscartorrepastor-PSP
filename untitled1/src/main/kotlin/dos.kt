fun main() {

    val libre = Thread {
        try {
            println("Libre empezado")
            while (!Thread.currentThread().isInterrupted) {
                for (i in 1..5) {
                    println("Libre en ejecución $i")
                    Thread.sleep(1000)
                }
            }
        }catch (e: InterruptedException) {
            println("Libre muerto de un tiro")
        }
    }
    val tortuga = Thread {
        println("Tortuga empezado")
        for (i in 1..5) {
            println("Tortuga en ejecución $i")
            Thread.sleep(2000)
        }
        println("Tortuga terminado")

    }
    libre.start()
    tortuga.start()
    Thread.sleep(4000)
    libre.interrupt()
    libre.join()
    tortuga.join()
    println("Carrera terminada")
}