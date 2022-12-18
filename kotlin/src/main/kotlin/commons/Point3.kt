package commons

import kotlin.math.sqrt

data class Point3(val x: Int = 0, val y: Int = 0, val z: Int = 0) {

    fun distance(p: Point3): Double {
        val px: Double = (p.x - x).toDouble()
        val py: Double = (p.y - y).toDouble()
        val pz: Double = (p.z - z).toDouble()
        return sqrt(px * px + py * py + pz * pz)
    }

    fun left() = Point3(x - 1, y, z)
    fun right() = Point3(x + 1, y, z)
    fun up() = Point3(x, y - 1, z)
    fun down() = Point3(x, y + 1, z)
    fun above() = Point3(x, y, z + 1)
    fun below() = Point3(x, y, z - 1)

    operator fun plus(p: Point3) = Point3(x + p.x, y + p.y, z + p.z)

    fun within(x1: Int, x2: Int, y1: Int, y2: Int, z1: Int, z2: Int): Boolean {
        if (x1 > x2) throw IllegalArgumentException("x1 > x2")
        if (y1 > y2) throw IllegalArgumentException("y1 > y2")
        if (y1 > y2) throw IllegalArgumentException("z1 > z2")
        return (x in x1..x2) && (y in y1..y2) && (z in z1..z2)
    }

    override fun toString() = "($x, $y, $z)"
}