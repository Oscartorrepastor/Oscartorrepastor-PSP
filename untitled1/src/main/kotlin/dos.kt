fun main() {
    
    val libre = Thread {
        println("Libre empezado")
        for (i in 1..5) {
            println("Libre en ejecución $i")
            Thread.sleep(1000)
        }
        println("Libre terminado")
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
    libre.join()
    tortuga.join()
    println("Carrera terminada")
}