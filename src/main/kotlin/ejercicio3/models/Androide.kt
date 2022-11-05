package ejercicio3.models

import ejercicio3.monitor.MonitorInterface

class Androide(
    val nombre: String,
    val cantidadMinerales: Int,
    val servidor: MonitorInterface<Muestra>

) : Thread() {
    private var ms = 1500

    override fun run() {
        for (i in 1..cantidadMinerales + 1) {
            val muestra = Muestra(i)
            servidor.put(muestra)
            println(" ||Androide $nombre -> Muestra: $muestra")
            sleep(ms.toLong())
        }
    }
}