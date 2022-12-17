package commons

fun <T> List<T>.group(delimiter: T): List<List<T>> {
    val list = mutableListOf<List<T>>()
    var current = mutableListOf<T>()

    forEach {
        if (it == delimiter) {
            list.add(current)
            current = mutableListOf()
        } else {
            current.add(it)
        }
    }
    list.add(current)
    return list.toList()
}