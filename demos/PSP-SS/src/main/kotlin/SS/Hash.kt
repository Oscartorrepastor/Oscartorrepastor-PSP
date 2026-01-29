package SS

import jdk.jfr.internal.consumer.EventLog.update
import java.security.MessageDigest

fun main() {
     //ESTO PARA OBTENER EL HASTH DE UNA PALABRA
    val palabra = "123"

     val instancia = MessageDigest.getInstance("SHA-256").apply {
          update(palabra.toByteArray())
          val hash = digest()
         println("Resuntado 1")
         println(hash.joinToString("") { "%02x".format(it) })
         println("Fin resultado 1")
      }



    // DESHASEAR CONTRASEÑAS - HASHING INVERSO
    val hashObjetivo = "a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3"

    // Bucle for simple - probando palabras una por una
    for (palabra in listOf("test", "java", "kotlin", "hola", "adios", "123", "password", "qwerty")) {
        // Tu código exacto
        val instancia = MessageDigest.getInstance("SHA-256").apply {
            update(palabra.toByteArray())
            val hash = digest()
            val hashStr = hash.joinToString("") { "%02x".format(it) }

            // Comparar
            if (hashStr == hashObjetivo) {
                println("✅ ¡SÍ! '$palabra' produce el hash objetivo")
            } else {
                println("❌ '$palabra' NO coincide")
            }
        }
    }

}