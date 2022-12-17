package year2022.day11

import commons.Day
import commons.group

fun main() = println(MonkeyBusiness())

class MonkeyBusiness : Day(true) {

    override fun a() = monkeyBusiness(20, true)
    override fun b() = monkeyBusiness(10000, false)

    private fun monkeyBusiness(rounds: Int, divide: Boolean): Long {
        val monkeys = content.parse()
        val commonDivisor = monkeys.map { it.divisor }.fold(1L) { a, b -> a * b }
        val stressReduction = if (divide) 3 else 1

        (1..rounds).forEach { _ ->
            monkeys.forEach { monkey ->
                while (monkey.items.isNotEmpty()) {
                    monkey.inspected++
                    val item = monkey.items.removeFirst()
                    val worry = (monkey.operation(item) / stressReduction) % commonDivisor
                    val toMonkey = monkey.throwTo(worry)
                    monkeys[toMonkey].items.add(worry)
                }
            }
        }

        return monkeys.map { it.inspected }.sortedDescending().take(2).fold(1L) { a, b -> a * b }
    }

    private fun List<String>.parse() = group("").map {
        val items = it[1].split(":")[1].split(",").map { it.trim().toLong() }.toMutableList()
        val operationRow = it[2].split(" = ")[1].split(" ").drop(1)

        val operation: (Long) -> Long = if (operationRow[0] == "*" && operationRow[1] == "old") {
            { a -> a * a }
        } else if (operationRow[0] == "*") {
            { a -> a * operationRow[1].toLong() }
        } else if (operationRow[1] == "old") {
            { a -> a + a }
        } else {
            { a -> a + operationRow[1].toLong() }
        }

        val divisor = it[3].split(" ")[5].toLong()
        val monkeyTrue = it[4].split(" ").toList().last().toInt()
        val monkeyFalse = it[5].split(" ").toList().last().toInt()

        Monkey(items, divisor, monkeyTrue, monkeyFalse, operation)
    }

}

class Monkey(
    val items: MutableList<Long>,
    val divisor: Long,
    private val monkeyTrue: Int,
    private val monkeyFalse: Int,
    val operation: (Long) -> Long
) {
    var inspected: Long = 0

    fun throwTo(stress: Long) =
        if (stress % divisor == 0L) monkeyTrue else monkeyFalse
}