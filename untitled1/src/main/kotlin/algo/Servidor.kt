package algo

import java.io.ObjectInput
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.net.ServerSocket

class Servidor {
    fun iniciarServer(){
        val servidor = ServerSocket(1238)
        val cliente = servidor.accept()
        val recibir = ObjectInputStream(cliente.getInputStream())
        val persona = recibir.readObject() as Persona
        println("Nombre: ${persona.nombre}, Edad: ${persona.edad}")
        recibir.close()
        cliente.close()
        servidor.close()
    }
}