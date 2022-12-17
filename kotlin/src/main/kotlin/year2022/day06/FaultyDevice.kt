package year2022.day06

import commons.Day

fun main() = println(FaultyDevice())

class FaultyDevice : Day(true) {

    override fun a() = content.first().toList().findFistDistinctSequence(4)

    override fun b() = content.first().toList().findFistDistinctSequence(14)

    private fun List<Char>.findFistDistinctSequence(size: Int): Int {
        val history = mutableListOf<Char>()

        forEachIndexed { i, c ->
            history.add(c)
            if (history.takeLast(size).toSet().size == size) return i + 1
        }

        return -1
    }
}