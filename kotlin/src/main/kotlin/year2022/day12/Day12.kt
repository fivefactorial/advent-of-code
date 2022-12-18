package year2022.day12

import commons.Day
import commons.Node
import commons.Point

fun main() = println(Day12())

class Day12 : Day(false) {


    override fun a(): Any {

        val data = content.parse()

        return -1
    }


    private fun List<String>.parse() {

        val grid = map { row -> row.toMutableList() }.toMutableList()
        val w = grid.first().size
        val h = grid.size

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

        nodes.forEach { point, node ->
            val neighbours =
                listOf(point.left(), point.right(), point.up(), point.down())
                    .filter { it.within(0, w - 1, 0, h - 1) }
                    .map { nodes[it]!! }
            node.neighbours.addAll(neighbours)
        }

        //return Triple(nodes.values.toList(), nodes)


    }
}