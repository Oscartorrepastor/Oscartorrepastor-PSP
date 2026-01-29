package cifrar

import java.nio.file.Files
import java.nio.file.Paths
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.PublicKey
import java.util.Base64

fun generarRSA(): KeyPair = KeyPairGenerator.getInstance("RSA").apply { initialize(2048) }.genKeyPair()

fun main() {
    val parClaves = generarRSA()

    guardar("src/main/resources/publicKey.der", parClaves.public.encoded)
    guardar("src/main/resources/privateKey.der", parClaves.private.encoded)

    val publica = cargarPublica("src/main/resources/publicKey.der")
    val privada = cargarPrivada("src/main/resources/privateKey.der")
    println("Clave Publica: $publica")
    println("Clave Privada: $privada")

    println("----------------------------")

    println(Base64.getEncoder().encodeToString(publica.encoded))
    println(Base64.getEncoder().encodeToString(privada.encoded))
}

fun guardar(file: String, byte: ByteArray) = Files.write(Paths.get(file), byte)

fun cargarPublica(file: String): PublicKey{
    val contenido = Files.readAllBytes(Paths.get(file))
    val keyFactory = java.security.KeyFactory.getInstance("RSA")
    val keySpec = java.security.spec.X509EncodedKeySpec(contenido)
    return keyFactory.generatePublic(keySpec)
}

fun cargarPrivada(file: String): java.security.PrivateKey{
    val contenido = Files.readAllBytes(Paths.get(file))
    val keyFactory = java.security.KeyFactory.getInstance("RSA")
    val keySpec = java.security.spec.PKCS8EncodedKeySpec(contenido)
    return keyFactory.generatePrivate(keySpec)
}