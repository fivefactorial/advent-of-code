package commons

import kotlin.math.abs
import kotlin.math.sqrt

data class Point(val x: Int = 0, val y: Int = 0) {

    fun distance(p: Point): Double {
        val px: Double = (p.x - x).toDouble()
        val py: Double = (p.y - y).toDouble()
        return sqrt(px * px + py * py)
    }

    fun manhattan(p: Point): Int = abs(x - p.x) + abs(y - p.y)

    fun left() = Point(x - 1, y)
    fun right() = Point(x + 1, y)
    fun up() = Point(x, y - 1)
    fun down() = Point(x, y + 1)

    fun neighbours() = listOf(left(), right(), up(), down())

    fun within(x1: Int, x2: Int, y1: Int, y2: Int): Boolean {
        if (x1 > x2) throw IllegalArgumentException("x1 > x2")
        if (y1 > y2) throw IllegalArgumentException("y1 > y2")
        return (x in x1..x2) && (y in y1..y2)
    }

    operator fun plus(p: Point) = Point(x + p.x, y + p.y)

}