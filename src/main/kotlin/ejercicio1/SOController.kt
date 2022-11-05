package ejercicio1

object SOController {
    fun init(): Boolean {
        var isLinux = false

        val so = System.getProperty("os.name").lowercase()
        if (so.contains("linux") || so.contains("mac")) {
            isLinux = true
        }
        //println(so)
        return isLinux
    }
}