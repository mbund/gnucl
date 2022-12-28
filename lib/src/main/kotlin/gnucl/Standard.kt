interface Standard<T> {
    fun clear()
    fun newInstance(): T
    fun transferFrom(source: T)
}
