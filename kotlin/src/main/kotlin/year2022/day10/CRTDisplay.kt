package year2022.day10

import commons.Day

fun main() = println(CRTDisplay())

class CRTDisplay : Day(true) {

    override fun a(): Any {
        var x = 1
        val values = mutableListOf<Int>()
        values.add(-1)
        values.add(x)

        content.map { row ->
            val parts = row.split(" ")
            if (parts.size == 1)
                Noop()
            else
                AddX(parts[1].toInt())
        }.forEach { instruction ->
            (1 until instruction.cycles).forEach { _ -> values.add(x) }
            x = instruction.get(x)
            values.add(x)
        }

        return (20..220 step 40).sumOf { it * values[it] }
    }

    override fun b(): Any {
        val values = mutableListOf<Int>()
        var reg = 1
        content.forEach { row ->
            values.add(reg)
            if (row != "noop") {
                values.add(reg)
                reg += row.split(" ")[1].toInt()
            }
        }

        values.forEachIndexed { index, value ->
            val pos = index % 40
            if (pos in listOf(value - 1, value, value + 1))
                print("#")
            else
                print(".")
            if (pos == 39)
                println()
        }

        return "See output"
    }
}

abstract class Instruction(val cycles: Int) {
    open fun get(x: Int): Int {
        return x
    }
}

class AddX(val x: Int) : Instruction(2) {
    override fun get(x: Int): Int {
        return this.x + x
    }
}

class Noop() : Instruction(1)