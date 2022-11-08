package ejercicio1

import java.io.BufferedReader
import java.io.File
import java.nio.file.Paths

/*
* El ejercicio se basaba en llamar a un jar usando cmd, powershell o bash
* A este jar se le pasa un numero entero x como parametro de entrada y repite x veces
* su accion, que es devolver/mostrar por consola nombres de personajes de Star Wars
*
* Teniamos dos resultados a conseguir:
*   Mostrar el numero de Siths generados de esa serie de nombres (los que empiezan por Darth)
*   Indicar si en esos Siths generados se encontraba al menos una vez Darth Vader
*
*
* Aqui esta mi solucion a ese ejercicio, funcional tanto en Windows como Linux, y espero que en Mac
* gracias al uso de SOController
*
*/

// Genero dos variables lateInit para evitar generar mas variables que pueden ser reutilizadas
private lateinit var pB: Process
private lateinit var reader: BufferedReader

private fun main() {
    // Como el jar se encuentra en el mismo proyecto, obtenemos la ruta de la siguiente forma
    val userDir = System.getProperty("user.dir")
    val pathJar = Paths.get(userDir + File.separator + "dataJar")

    // Iniciamos el comprobador de SO
    val comprobador = SOController.init()

    // Lo usaremos luego para obtener linea a linea el resultado del jar
    var line: String?

    // ArrayList donde almacenaremos los personajes, ideal para mostrar el numero de forma sencilla
    val listaPersonajes = ArrayList<String>()

    // Un boolean pensado para Darth Vader
    var aviso = false
    // Pedimos un numero entero (que por si acaso se pone otra cosa sea Null) y lo uso en la llamada del jar
    print("Escriba el numero de mensajes ha ser captados: ")
    val numeroMensajes = readln().toIntOrNull()

    pB = if (!comprobador) {
        // Generamos un ProcessBuilder con los datos adecuados
        ProcessBuilder("cmd.exe", "/c", "cd $pathJar & java -jar mensajes.jar $numeroMensajes").start()
    } else {
        ProcessBuilder("bash", "-c", "cd $pathJar && java -jar mensajes.jar $numeroMensajes").start()
    }

    /*
    * Para la lectura linea a linea se realiza el siguiente bloque de codigo
    * En este caso, mi idea fue aplicar el filtrado de nombres con un if de la linea leida
    * De esa forma, luego podia imprimir el size de forma sencilla.
    */
    reader = pB.inputStream.bufferedReader()
    while (reader.readLine().also { line = it } != null) {
        //println("Personaje: $line")
        print(".")
        if (line?.lowercase()?.startsWith("darth") == true) {
            listaPersonajes.add(line.toString())
        }
    }

    /*
    * En este bloque realizo la busqueda de Darth Vader en la lista generada.
    *
    * Hay otra solucion que requeriria realizar un waitFor del proceso, pero en este caso no es necesario
    * debido a que uso la lista, no el proceso, para sacar la informacion.
    */
    for (i in 0 until listaPersonajes.size) {
        if (listaPersonajes[i].lowercase().contains("darth vader"))
            aviso = true
    }

    // Mostramos lo pedido para el ejercicio.
    println("\nSiths existentes: ${listaPersonajes.size}")
    if (aviso) {
        println("Darth Vader detectado")
    } else {
        println("Darth Vader NO detectado")
    }

}