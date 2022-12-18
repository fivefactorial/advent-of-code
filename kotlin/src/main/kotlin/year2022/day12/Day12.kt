package year2022.day12

import commons.Day
import commons.Node
import commons.Point
import java.util.*

fun main() = println(Day12())

class Day12 : Day(true) {

    override fun a(): Any {
        val (data, start, end) = content.parse()

        println("$start ${start.neighbours}")
        println("$end ${end.neighbours} ${end.neighbours.first().neighbours} ${end.neighbours[0].neighbours[1].neighbours}")

        return distance(start, end)
    }

    private fun distance(from: Node<Char>, to: Node<Char>): Int {
        val distance = mutableMapOf<Node<Char>, Int>()
        val visited = mutableSetOf<Node<Char>>()
        val queue = LinkedList<Node<Char>>()

        distance[from] = 0
        visited.add(from)
        queue.offer(from)

        while (queue.isNotEmpty()) {
            val node = queue.pop()
            val d = distance[node]!!
            if (node == to)
                return d
            node.neighbours.forEach {
                if (visited.add(it)) {
                    queue.offer(it)
                    distance[it] = d + 1
                }
            }
        }
        return -1
    }


    private fun List<String>.parse(): Triple<List<Node<Char>>, Node<Char>, Node<Char>> {
        val grid = map { row -> row.toMutableList() }.toMutableList()
        val w = grid.first().size
        val h = grid.size

        println("w:$w h:$h")

        val nodes = mutableMapOf<Point, Node<Char>>()
        var start: Point? = null
        var end: Point? = null

        (0 until w).forEach { x ->
            (0 until h).forEach { y ->
                val point = Point(x, y)
                val value = when (grid[y][x]) {
                    'S' -> {
                        start = point
                        'a'
                    }
                    'E' -> {
                        end = point
                        'z'
                    }
                    else -> grid[y][x]
                }
                nodes[point] = Node(value)
            }
        }

        nodes.forEach { (point, node) ->
            val neighbours =
                listOf(point.left(), point.right(), point.up(), point.down())
                    .filter { it.within(0, w - 1, 0, h - 1) }
                    .map { nodes[it]!! }
                    .filter { it -> it.value - node.value in (-1..1) }
            node.neighbours.addAll(neighbours)
        }

        return Triple(nodes.values.toList(), nodes[start]!!, nodes[end]!!)
    }
}