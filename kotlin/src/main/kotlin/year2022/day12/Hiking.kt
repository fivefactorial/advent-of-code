package year2022.day12

import commons.Day
import commons.Point
import java.util.*

fun main() = println(Hiking())

class Hiking : Day(true) {

    override fun a(): Any {
        val (nodes, start, end) = content.parse()
        return distance(nodes, start, end)
    }

    override fun b(): Any {
        val (nodes, _, end) = content.parse()
        return nodes.filter { it.height == 'a' }.map { distance(nodes, it, end) }.minOf { it }
    }

    private fun distance(nodes: List<Node>, from: Node, to: Node): Int {
        val visited = mutableSetOf<Node>()
        val queue = LinkedList<Node>()
        val distance = mutableMapOf<Node, Int>()

        visited.add(from)
        queue.add(from)
        distance[from] = 0

        while (queue.isNotEmpty()) {
            val node = queue.pop()
            val d = distance[node]!!
            if (node == to) return d

            nodes.neighbours(node).forEach {
                if (visited.add(it)) {
                    queue.add(it)
                    distance[it] = d + 1
                }
            }
        }
        return Integer.MAX_VALUE
    }

    private fun List<Node>.neighbours(n: Node) =
        n.point.neighbours().mapNotNull { this[it] }.filter { n.height - it.height >= -1 }

    private operator fun List<Node>.get(point: Point) = firstOrNull { point.x == it.x && point.y == it.y }

    private fun List<String>.parse(): Triple<List<Node>, Node, Node> {
        val nodes = mutableListOf<Node>()

        var start: Node? = null
        var end: Node? = null

        forEachIndexed { y, row ->
            row.forEachIndexed { x, c ->
                when (c) {
                    'S' -> {
                        nodes.add(Node(x, y, 'a'))
                        start = nodes.last()
                    }
                    'E' -> {
                        nodes.add(Node(x, y, 'z'))
                        end = nodes.last()
                    }
                    else -> nodes.add(Node(x, y, c))
                }
            }
        }
        return Triple(nodes, start!!, end!!)
    }
}

private data class Node(
    val x: Int,
    val y: Int,
    val height: Char
) {
    val point = Point(x, y)
}