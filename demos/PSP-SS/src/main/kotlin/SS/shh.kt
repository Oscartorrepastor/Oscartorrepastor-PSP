package SS

import net.schmizz.sshj.SSHClient
import net.schmizz.sshj.connection.channel.direct.Session
import java.io.IOException
import java.io.InputStream

fun main() {
    val host = "192.168.2.222"
    val user = "ubuntu"
    val password = "ubuntu"
    val command = "ls -l"

    val ssh = SSHClient()
    ssh.addHostKeyVerifier { _, _, _ -> true }

    try {
        ssh.connect(host)
        ssh.authPassword(user, password)

        val session: Session = ssh.startSession()
        val cmd: Session.Command = session.exec(command)

        val inputStream: InputStream = cmd.inputStream
        val output = inputStream.bufferedReader().readText()

        cmd.join(5, java.util.concurrent.TimeUnit.SECONDS)

        println("Salida del comando:")
        println(output)
    } catch (e: IOException) {
        e.printStackTrace()
    } finally {
        ssh.disconnect()
    }
}