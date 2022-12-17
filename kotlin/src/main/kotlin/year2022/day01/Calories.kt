package year2022.day01

import commons.Day
import commons.group

fun main() = println(Calories())

class Calories : Day(true) {

    private val parsed = content.group("")
        .map { it.sumOf { item -> item.toInt() } }
        .sortedDescending()

    override fun a() = parsed.first()

    override fun b() = parsed.take(3).sumOf { it }

}