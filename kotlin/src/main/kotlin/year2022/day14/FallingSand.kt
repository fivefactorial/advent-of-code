package year2022.day14

import commons.Day
import commons.Point

fun main() = println(FallingSand())

class FallingSand : Day(true) {

    override fun a(): Any {
        val stones = content.parse()
        val sand = mutableSetOf<Point>()
        sand.addAll(stones)

        var done = false
        var p = Point(500, 0)
        val bottom = stones.maxOf { it.y }

        while (!done) {
            if (p.y > bottom) {
                done = true
            } else if (!sand.contains(p.down())) p = p.down()
            else if (!sand.contains(p.downLeft())) p = p.downLeft()
            else if (!sand.contains(p.downRight())) p = p.downRight()
            else {
                sand.add(p)
                p = Point(500, 0)
            }
        }
        return sand.size - stones.size
    }

    override fun b(): Any {
        val stones = content.parse()
        val sand = mutableSetOf<Point>()
        sand.addAll(stones)

        var done = false
        var p = Point(500, 0)
        val floor = stones.maxOf { it.y } + 1

        while (!done) {
            if (p.y == floor) {
                sand.add(p)
                p = Point(500, 0)
            } else if (!sand.contains(p.down())) p = p.down()
            else if (!sand.contains(p.downLeft())) p = p.downLeft()
            else if (!sand.contains(p.downRight())) p = p.downRight()
            else {
                sand.add(p)
                if (p == Point(500, 0)) done = true
                p = Point(500, 0)
            }
        }
        return sand.size - stones.size
    }

    private fun List<String>.parse(): Set<Point> {
        val points = mutableSetOf<Point>()

        forEach { row ->
            val data = row.split(" -> ").map {
                val parts = it.split(",").map { it.toInt() }
                Point(parts[0], parts[1])
            }
            var prev = data.first()
            data.forEach { curr ->
                points.addAll(line(prev, curr))
                prev = curr
            }
        }

        return points
    }

    private fun line(start: Point, end: Point) =
        (start.x.coerceAtMost(end.x)..start.x.coerceAtLeast(end.x)).map { x ->
            (start.y.coerceAtMost(end.y)..start.y.coerceAtLeast(end.y)).map { y ->
                Point(x, y)
            }.toMutableSet()
        }.fold(mutableSetOf<Point>()) { a, b -> a.addAll(b); a }

    private fun Point.downLeft() = this.down().left()
    private fun Point.downRight() = this.down().right()

}

