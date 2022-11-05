package ejercicio3.models

import ejercicio3.monitor.MonitorInterface
import java.io.File
import java.nio.file.Paths

class Terminal(
    var nombre: String,
    val servidor: MonitorInterface<Muestra>,
    private val maxMuestras: Int,
) : Thread() {

    private var ms: Int = (1000..1500).random()

    private val userDir = System.getProperty("user.dir")
    private val pathFile = Paths.get(userDir + File.separator + "data").toString()
    private val file = File(pathFile + File.separator + "manifiesto.txt")

    private var cerrandoTerminal = false
    private var muestrasTomadas = 0

    override fun run() {

        while (!cerrandoTerminal) {
            val muestra = servidor.get()
            println("La terminal: $nombre ha recogido la muestra: $muestra ")
            println("------------------------------------------------")
            muestrasTomadas++

            sleep(ms.toLong())
            if (muestra.porcentajePureza > 60) {

                println("-La terminal: $nombre escribiendo en fichero...")
                file.appendText("La terminal: $nombre ha recogido la muestra: $muestra \n")

                println("\t --Informacion agregada de la terminal: $nombre")

            } else if (muestrasTomadas == maxMuestras) {
                println("\t \t -La terminal: $nombre ha llegado a su limite.")
                cerrandoTerminal = true
            }

        }

    }
}