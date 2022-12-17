package year2022.day03

import commons.Day

fun main() = println(Day03())

class Day03 : Day(true) {

    override fun a() =
        content.sumOf { it.toList().half().intersect().first().priority() }

    override fun b() =
        content
            .withIndex()
            .groupBy { it.index / 3 }
            .map { (_, v) -> v.map { it.value.toList() } }
            .sumOf { it.intersect().first().priority() }

    private fun <T> List<T>.half(): Pair<List<T>, List<T>> =
        Pair(this.subList(0, size / 2), this.subList(size / 2, size))

    private fun <T> Pair<List<T>, List<T>>.intersect() = first.intersect(second.toSet())

    private fun Char.priority() = 1 + if (this in 'A'..'Z') this - 'A' + 26 else this - 'a'

    private fun <T> List<List<T>>.intersect(): Set<T> {
        var intersect: Set<T> = first().toSet()
        for (i in 1 until size) {
            intersect = intersect.intersect(this[i].toSet())
        }
        return intersect
    }
}