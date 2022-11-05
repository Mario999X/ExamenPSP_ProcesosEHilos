package ejercicio3.monitor

interface MonitorInterface<T> {
    fun get(): T
    fun put(item: T)
}