package year2022.day15

import commons.Day
import commons.Point
import java.util.regex.Pattern

fun main() = println(Beacons())

class Beacons : Day(true) {

    override fun a(): Any {
        val data = content.parse()

        val blocked = mutableSetOf<Point>()
        data.forEach { (_, b) -> blocked.add(b) }

        val scanY = if (realData) 2000000 else 10

        data.forEach { (s, b) ->
            val distance = s.manhattan(b)
            val x1 = s.x - distance
            val x2 = s.x + distance

            (x1..x2).forEach { x ->
                val p = Point(x, scanY)
                if (s.manhattan(p) <= s.manhattan(b))
                    blocked.add(p)
            }
        }
        val knownBeacons = data.map { (_, b) -> b }.filter { it.y == scanY }.toSet().size
        return blocked.filter { p -> p.y == scanY }.size - knownBeacons
    }

    override fun b(): Any {
        val data = content.parse()

        val scanAreSize = if (realData) 4000000 else 20
        var points = 0

        data.forEach { (s, b) ->
            val distance = s.manhattan(b) + 1
            (0..distance).forEach { d ->
                val x = s.x + d
                val y = s.y + distance - d
                val p = Point(x, y)
                points++
                if (x >= 0 && y >= 0 && x <= scanAreSize && y <= scanAreSize)
                    data.filter { (a, b) -> a.manhattan(b) < a.manhattan(p) }.apply {
                        if (size == data.size) {
                            println(points)
                            return p.x * 4000000L + p.y
                        }
                    }
            }
        }

        return "No solution found."
    }

    private fun List<String>.parse(): List<Pair<Point, Point>> {
        val pattern = Pattern.compile("Sensor at x=(.+), y=(.+): closest beacon is at x=(.+), y=(.+)")

        return map { row ->
            val match = pattern.matcher(row)
            if (!match.find()) throw IllegalArgumentException(row)

            val sx = match.group(1).toInt()
            val sy = match.group(2).toInt()
            val bx = match.group(3).toInt()
            val by = match.group(4).toInt()

            Pair(Point(sx, sy), Point(bx, by))
        }
    }


}