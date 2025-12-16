package algo

import java.io.ObjectOutputStream
import java.net.Socket

class Cliente {
    fun cliente(persona: Persona) {
        val cliente = Socket("localhost", 1238)
        val out = ObjectOutputStream(cliente.getOutputStream())
        out.writeObject(persona)
        out.flush()
        out.close()
        cliente.close()
    }
}