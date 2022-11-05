package ejercicio2

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.Future

/*
* El ejercicio se basaba en leer una pagina web, obtener su resultado y filtrar las lineas donde
* apareciera la palabra "darth vader"
* Eso si, de forma asincrona, es decir, aplicando futures, cada x ms deberia de aparecer un mensaje mientras se realiza
* la lectura y filtracion de la pagina
*
* En principio, este estaria bien... no estoy seguro.
*/
fun main() {
    val listaString = ArrayList<String>()
    val pool = Executors.newSingleThreadExecutor()

    val task = Callable {
        val url = URL("https://es.wikipedia.org/wiki/Star_Wars")
        BufferedReader(InputStreamReader(url.openStream())).use { br ->
            var line: String?
            while (br.readLine().also { line = it } != null) {
                if (line?.lowercase()?.contains("darth vader") == true) {
                    listaString.add(line.toString())
                }
            }
        }
        println("Numero de veces que aparece Darth Vader: ${listaString.size}")
    }

    val future: Future<Unit> = pool.submit(task)
    while (!future.isDone) {
        println("Esperando...")
        Thread.sleep(250)
    }

    pool.shutdown()
}