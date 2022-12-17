package year2022.day09

import commons.Day
import commons.Point
import commons.group

fun main() = println(RopeBridge())

class RopeBridge : Day(true) {

    private val directions = (-1..1).map { x -> (-1..1).map { y -> Point(x, y) }.toMutableList() }
        .reduce { left, right -> left.addAll(right); left }.toSet()

    override fun a(): Any {
        val visited = mutableSetOf<Point>()

        var head = Point()
        var tail = Point()

        visited.add(tail)

        content.parse().forEach { (dir, distance) ->
            (1..distance).forEach { _ ->
                head += dir
                tail = next(head, tail)
                visited.add(tail)
            }
        }

        return visited.size
    }

    override fun b(): Any {
        val visited = mutableSetOf<Point>()

        var head = Point()
        val tails = (1..9).map { Point() }.toMutableList()

        visited.add(tails.last())

        content.parse().forEach { (dir, distance) ->
            (1..distance).forEach { _ ->
                head += dir
                tails.forEachIndexed { i, tail ->
                    val before = if (i == 0) head else tails[i - 1]
                    tails[i] = next(before, tail)
                }
                visited.add(tails.last())
            }
        }

        return visited.size
    }

    private fun next(head: Point, tail: Point) =
        if (head.distance(tail) > 1.5) {
            val possibilities = directions.associateBy { (tail + it).distance(head) }
            val tailDir = possibilities[possibilities.keys.minOf { it }]
            tail + (tailDir ?: throw IllegalArgumentException())
        } else {
            tail
        }

    private fun List<String>.parse(): List<Pair<Point, Int>> =
        group("").first().map {
            val parts = it.split(" ")
            val dir = when (parts[0]) {
                "R" -> Point(1, 0)
                "U" -> Point(0, 1)
                "D" -> Point(0, -1)
                "L" -> Point(-1, 0)
                else -> throw IllegalArgumentException(parts[0])
            }
            Pair(dir, parts[1].toInt())
        }

    operator fun Point.plus(other: Point) = Point(x + other.x, y + other.y)

}