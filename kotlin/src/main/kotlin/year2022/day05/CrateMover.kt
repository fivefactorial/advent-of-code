package year2022.day05

import commons.Day
import commons.group
import java.util.regex.Pattern

fun main() = println(CrateMover())

class CrateMover : Day(true) {

    override fun a(): Any {
        val (crates, instructions) = parse()

        instructions.forEach { (n, from, to) ->
            (1..n).map { _ -> crates[from].removeFirst() }.forEach {
                crates[to].add(0, it)
            }
        }

        return crates.joinToString("") { it.firstOrNull() ?: "" }
    }

    override fun b(): Any {
        val (crates, instructions) = parse()

        instructions.forEach { (n, from, to) ->
            (1..n).map { _ -> crates[from].removeFirst() }.reversed().forEach {
                crates[to].add(0, it)
            }
        }

        return crates.joinToString("") { it.firstOrNull() ?: "" }
    }

    private fun parse(): Pair<List<MutableList<String>>, List<Triple<Int, Int, Int>>> {
        val (crateRows, instructionRows) = content.group("").toPair()

        val stacks = (crateRows.maxOf { it.length } + 1) / 4
        val crates = (0..stacks).map { mutableListOf<String>() }

        val pattern = Pattern.compile(".(.). ")

        crateRows.dropLast(1).forEach { row ->
            val match = pattern.matcher("$row ")
            var i = 1
            while (match.find()) {
                val found = match.group(1)
                if (found != " ") crates[i].add(found)
                i++
            }
        }

        val instructions = instructionRows.map { row ->
            val data = row.split(" ")
            Triple(data[1].toInt(), data[3].toInt(), data[5].toInt())
        }

        return Pair(crates, instructions)
    }

    private fun <E> List<E>.toPair(): Pair<E, E> {
        if (size != 2) throw IllegalArgumentException("List must have 2 elements, has $size")
        return Pair(get(0), get(1))
    }


}