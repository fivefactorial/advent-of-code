package year2022.day02

import commons.Day

fun main() = println(RockPaperScissors())

class RockPaperScissors : Day(true) {

    private val letters = content.map { row -> Pair(row[0] - 'A', row[2] - 'X') }

    override fun a() = letters.sumOf { (a, b) ->
        (3 + b + 1 - a) % 3 * 3 + b + 1
    }

    override fun b() = letters.sumOf { (a, b) ->
        3 * b + 1 + when (b) {
            0 -> (a + 2) % 3
            1 -> a
            else -> (a + 1) % 3
        }
    }
}