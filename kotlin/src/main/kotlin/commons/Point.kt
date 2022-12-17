package commons

import kotlin.math.sqrt

data class Point(val x: Int = 0, val y: Int = 0) {

    fun distance(p: Point): Double {
        val px: Double = (p.x - x).toDouble()
        val py: Double = (p.y - y).toDouble()
        return sqrt(px * px + py * py)
    }

    operator fun plus(p: Point) = Point(x + p.x, y + p.y)

}