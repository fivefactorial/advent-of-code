package year2022.day18

import commons.Day
import commons.Point3
import java.util.*

fun main() = println(SurfaceArea())

class SurfaceArea : Day(true) {

    override fun a() = content.parse().surfaceArea()

    override fun b(): Any {
        val data = content.parse()

        val minX = data.minOf { it.x }
        val maxX = data.maxOf { it.x }
        val minY = data.minOf { it.y }
        val maxY = data.maxOf { it.y }
        val minZ = data.minOf { it.z }
        val maxZ = data.maxOf { it.z }

        val possiblePoints = mutableSetOf<Point3>()
        (minX..maxX).forEach { x ->
            (minY..maxY).forEach { y ->
                (minZ..maxZ).forEach { z ->
                    val p = Point3(x, y, z)
                    if (!data.contains(p))
                        possiblePoints.add(p)
                }
            }
        }

        val points = possiblePoints.filter { !exposed(it, data, minX, maxX, minY, maxY, minZ, maxZ) }.toMutableList()
        points.addAll(data)

        return points.surfaceArea()
    }

    private fun List<Point3>.surfaceArea() = sumOf {
        7 - filter { other -> other.distance(it) <= 1.1 }.size
    }

    private fun exposed(
        startPoint: Point3,
        blocked: List<Point3>,
        x1: Int,
        x2: Int,
        y1: Int,
        y2: Int,
        z1: Int,
        z2: Int
    ): Boolean {
        val visited = mutableSetOf<Point3>()
        val queue = LinkedList<Point3>()

        visited.add(startPoint)
        queue.offer(startPoint)

        while (queue.isNotEmpty()) {
            val p = queue.pop()
            if (!p.within(x1, x2, y1, y2, z1, z2))
                return true
            listOf(p.left(), p.right(), p.up(), p.down(), p.above(), p.below()).forEach {
                if (!blocked.contains(it) && visited.add(it)) queue.offer(it)
            }
        }
        return false
    }

    private fun List<String>.parse(): List<Point3> {
        return map { row ->
            val data = row.split(",").map { it.toInt() }
            Point3(data[0], data[1], data[2])
        }
    }
}