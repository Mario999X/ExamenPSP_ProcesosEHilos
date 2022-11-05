package ejercicio3.monitor

import ejercicio3.models.Muestra
import java.util.concurrent.locks.Condition
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock
import kotlin.system.exitProcess

class Servidor(
    val maxMuestras: Int = 8
) : MonitorInterface<Muestra> {

    // Lista donde se guardan las muestras
    val listaMuestras = mutableListOf<Muestra>()

    // ReentrantLock + condiciones
    private val lock: ReentrantLock = ReentrantLock()
    val servidorLleno: Condition = lock.newCondition()
    val servidorVacio: Condition = lock.newCondition()

    private var existAppMuestras = 0

    private var permisoEntrada = false

    override fun get(): Muestra {
        lock.withLock {
            while (listaMuestras.size == 0) {
                servidorLleno.await()
            }
            val muestra = listaMuestras.removeFirst()
            permisoEntrada = false

            servidorVacio.signalAll()
            println("Muestras en el servidor: ${listaMuestras.size}")
            return muestra
        }
    }

    override fun put(item: Muestra) {
        lock.withLock {
            while (listaMuestras.size == maxMuestras) {
                servidorVacio.await()
            }
            listaMuestras.add(item)

            existAppMuestras++
            if (existAppMuestras == 15) {
                println("\n ---CERRANDO CONEXION---")
                exitProcess(15)
            }

            permisoEntrada = true

            servidorLleno.signalAll()
            println("Muestras en el servidor: ${listaMuestras.size}")
        }
    }
}