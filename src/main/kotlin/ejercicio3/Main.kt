package ejercicio3

import ejercicio3.models.Androide
import ejercicio3.models.Terminal
import ejercicio3.monitor.Servidor
import java.io.File
import java.nio.file.Paths
import java.util.concurrent.Executors

/* --- ENUNCIADO ---
* No lo recuerdo al 100%, pero puedo realizar una aproximacion de lo que era el enunciado
*
* R2D2 y BB8, recogen muestras de Endor y se envian a un servidor, donde Luke y Leia las recogen,
* esas muestras son escritas en un manifiesto.txt si cumplen cierto requisito.
*
* Realizando un analisis del encunciado sacamos lo siguiente:
*   Androides R2D2 y BB8 (Productores X2)
*   Muestras (porcentaje de pureza) (Producto)
*   Servidor (SC/Monitor)
*   Terminales Luke y Leia (Consumidores x2)
*
* Lo importante a recordar:
*   Las muestras tenian un porcentaje de pureza, que se mueve entre 10 y 80; solo si el numero es mayor a 60 se escribe
* en el txt
*   Los Androides mandan las muestras con un descanso de 1,50 segundos entre ellas
*   Las terminales toman las muestras con un descanso entre 1 y 1,50 segundos de forma aleatoria entre ellas
*
*   Las terminales tienen un max de muestras a tomar, 5
*   El servidor tiene un limite para las muestras almacenadas, 8
* --------------------------------------------------------
*
*   Mi solucion se acerca a la practica famosa de los jamones, pero con ligeras modificaciones.
*   Mi solucion en el examen se acercaba a la correcta, pero cuenta con un par de fallos que no supe corregir en
* el tiempo limite, pero he sabido corregirlos
*
* --- TIPS ---
* Recomiendo seguir un orden al leer el enunciado, es importante analizar quien es el productor, el consumidor,
* el producto y la SC
*
* Luego, este orden es el que aplico una vez analizado el enunciado:
*   Generacion de un main con el metodo main, generacion de modelos de forma basica, luego terminaremos con ellos.
*
*   Generacion del paquete Monitor, con dos cosas, una interfaz y el monitor en cuestion.
*
*   Agregamos a los modelos la informacion en una variable del monitor, implementamos Thread() en Productor y Consumidor
* y sobrescribimos el metodo correspondiente de Thread
*
*   Regresamos al monitor e implementamos la interfaz creada, escribimos el codigo de esta clase en su totalidad
*
*   Regresamos a los modelos y escribimos en el metodo sobreescrito el codigo necesario
*
*   Finalmente, terminamos escribiendo el codigo necesario en el main.
*/
private const val PRODUCCION = 50
private const val MAX_MUESTRAS = 5

private fun main(){
    limpiezaTxt()

    val servidor = Servidor()

    val androide1 = Androide("r2d2", PRODUCCION, servidor)
    val androide2 = Androide("bb8", PRODUCCION, servidor)

    val terminal1 = Terminal("Luke", servidor, MAX_MUESTRAS)
    val terminal2 = Terminal("Leia", servidor, MAX_MUESTRAS)

    val pool = Executors.newFixedThreadPool(4)
    pool.execute(androide1)
    pool.execute(androide2)

    pool.execute(terminal1)
    pool.execute(terminal2)

    pool.shutdown()
}

private fun limpiezaTxt(){
    val userDir = System.getProperty("user.dir")
    val pathFile = Paths.get(userDir + File.separator + "data").toString()
    val file = File(pathFile + File.separator + "manifiesto.txt")

    println("Borrando informacion antigua del archivo: $file")
    file.writeText("")
}