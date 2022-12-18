package commons

class Node<T>(value: T) {

    val neighbours = mutableListOf<Node<T>>()
}