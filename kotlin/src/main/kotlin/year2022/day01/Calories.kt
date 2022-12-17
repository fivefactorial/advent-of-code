package year2022.day01

import commons.Day

fun main() = println(Calories())

class Calories : Day(true) {
    
    private val parsed = content
        .joinToString("\n")
        .split("\n\n")
        .map { it.split("\n").sumOf { item -> item.toInt() } }
        .sortedDescending()

    override fun a() = parsed.first()

    override fun b() = parsed.take(3).sumOf { it }

}