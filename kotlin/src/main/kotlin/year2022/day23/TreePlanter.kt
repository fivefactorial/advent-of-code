package year2022.day23

import commons.Day
import commons.Point

fun main() = println(TreePlanter(true))

class TreePlanter(realData: Boolean) : Day(realData) {

    override fun a() = solve(10, false)
    override fun b() = solve(10000, true)

    private fun solve(rounds: Int, breakIfNoMovement: Boolean): Int {
        val elves = content.parse().toMutableSet()
        val directions = mutableListOf(
            setOf(Point().up(), Point().up().left(), Point().up().right()),        // N
            setOf(Point().down(), Point().down().left(), Point().down().right()),  // S
            setOf(Point().left(), Point().left().up(), Point().left().down()),     // W
            setOf(Point().right(), Point().right().up(), Point().right().down())   // E
        )

        (1..rounds).forEach { round ->
            println(round)
            val next = mutableMapOf<Point, MutableList<Point>>()
            elves.forEach { e ->
                if (e.allNeighbours().intersect(elves).isNotEmpty()) {
                    var moved = false
                    directions.forEach { dir ->
                        if (!moved && elves.intersect(dir.map { it + e }.toSet()).isEmpty()) {
                            val step = dir.first() + e
                            if (next.contains(step)) {
                                next[step]!!.add(e)
                            } else {
                                next[step] = mutableListOf(e)
                            }
                            moved = true
                        }
                    }
                }
            }

            directions.rotate()

            var anyMovement = false
            next.forEach { (to, fromList) ->
                if (fromList.size == 1) {
                    anyMovement = true
                    elves.remove(fromList.first())
                    elves.add(to)
                }
            }

            if (!anyMovement && breakIfNoMovement)
                return round
        }

        return elves.freeSpaces()
    }

    private fun <T> MutableList<T>.rotate() {
        add(removeAt(0))
    }

    private fun Set<Point>.freeSpaces(): Int {
        val minX = minOf { it.x }
        val maxX = maxOf { it.x }
        val minY = minOf { it.y }
        val maxY = maxOf { it.y }

        val dx = maxX - minX + 1
        val dy = maxY - minY + 1
        val spaces = dx * dy

        return spaces - size
    }

    private fun List<String>.parse(): Set<Point> {
        val elves = mutableSetOf<Point>()
        forEachIndexed { y, row ->
            row.forEachIndexed { x, c ->
                if (c == '#')
                    elves.add(Point(x, y))
            }
        }
        return elves
    }
}