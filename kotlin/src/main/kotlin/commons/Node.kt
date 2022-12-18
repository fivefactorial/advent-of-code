package commons

class Node<T>(val value: T) {

    val neighbours = mutableListOf<Node<T>>()

    override fun toString() = "node($value)"
}