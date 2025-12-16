package api

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.google.zxing.BarcodeFormat
import com.google.zxing.client.j2se.MatrixToImageWriter
import com.google.zxing.qrcode.QRCodeWriter
import sun.net.www.http.HttpClient
import java.io.ByteArrayOutputStream
import java.net.URLEncoder

fun main() {
    // Inicializamos un cliente HTTP para hacer la petición de audio externa
    //val httpClient = HttpClient(CIO)

    val server = embeddedServer(Netty, port = 8080) {
        routing {

            // 1. Endpoint Original (JSON)
            get("/saludo") {
                val jsonString = "{\"mensaje\": \"¡Hola! Tu API está funcionando.\"}"
                call.respondText(jsonString, ContentType.Application.Json)
            }

            // 2. Endpoint: Generar código QR
            // Uso: http://localhost:8080/qr?texto=HolaMundo
            // Invoke-RestMethod "http://localhost:8080/qr?texto=www.google.com" -OutFile "saludo.png"

            get("/qr") {
                // Obtenemos el texto de la URL, si no hay, usamos uno por defecto

                val texto = call.request.queryParameters["texto"] ?: "Sin texto"

                try {
                    // Generamos el QR en memoria
                    val qrWriter = QRCodeWriter()
                    val bitMatrix = qrWriter.encode(texto, BarcodeFormat.QR_CODE, 300, 300)

                    // Convertimos la matriz a una imagen PNG (bytes)
                    val stream = ByteArrayOutputStream()
                    MatrixToImageWriter.writeToStream(bitMatrix, "PNG", stream)
                    val byteArray = stream.toByteArray()

                    // Respondemos con la imagen
                    call.respondBytes(byteArray, ContentType.Image.PNG)
                } catch (e: Exception) {
                    call.respondText("Error generando QR: ${e.message}", status = HttpStatusCode.InternalServerError)
                }
            }

            // 3. Endpoint: Texto a Audio (TTS)
            // Uso: http://localhost:8080/audio?texto=Hola
            //
            //Invoke-RestMethod "http://localhost:8080/audio?texto=Prueba de audio simplificado" -OutFile "audio.mp3"
            get("/audio") {
                val texto = call.request.queryParameters["texto"] ?: "Hola"

                // Convertimos el texto para que acepte espacios (ej: "Hola mundo" -> "Hola+mundo")
                val encoded = java.net.URLEncoder.encode(texto, "UTF-8")

                // Simplemente redirigimos a la URL
                call.respondRedirect("https://translate.google.com/translate_tts?ie=UTF-8&client=tw-ob&tl=es&q=$encoded")
            }

        }
    }
    server.start(wait = true)
}

/*
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class Saludo(val mensaje: String)

fun main() {
    val server = embeddedServer(Netty, port = 8080) {
        routing {
            get("/saludo") {
                val saludo = Saludo("¡Hola! Este es un mensaje desde tu API en Kotlin.")
                val jsonString = "{\"mensaje\": \"${saludo.mensaje}\"}"
                call.respondText(jsonString, ContentType.Application.Json)
            }
        }
    }
    server.start(wait = true)
}
*/
