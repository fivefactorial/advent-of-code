package year2022.day13

import com.google.gson.Gson
import commons.Day
import commons.group

fun main() = println(DistressSignal())

class DistressSignal : Day(true) {

    private val gson = Gson()

    override fun a(): Any {
        val data = content.group("").map { Pair(it[0].parse(), it[1].parse()) }

        return data.map { compare(it.first, it.second) }.mapIndexed { i, v -> if (v < 0) i + 1 else 0 }.sum()
    }

    override fun b(): Any {
        val data = content
            .filter { it.isNotEmpty() }
            .map { it.parse() }
            .toMutableList()

        val dp1 = "[[2]]".parse().apply { data.add(this) }
        val dp2 = "[[6]]".parse().apply { data.add(this) }

        data.sortedWith { a, b -> compare(a, b) }.apply {
            return (indexOf(dp1) + 1) * (indexOf(dp2) + 1)
        }
    }

    private fun compare(left: Any, right: Any): Int {
        if (left is Double && right is Double) {
            return if (left == right) 0 else (left - right).toInt()
        }
        if (left is List<*> && right is List<*>) {
            val size = Integer.min(left.size, right.size)

            (0 until size).forEach { i ->
                val comp = compare(left[i]!!, right[i]!!)
                if (comp != 0) return comp
            }

            return left.size - right.size

        }
        val newLeft = if (left is Double) listOf(left) else left
        val newRight = if (right is Double) listOf(right) else right

        return compare(newLeft, newRight)
    }

    private fun String.parse(): List<Any> {
        return gson.fromJson(this, List::class.java) as List<Any>
    }

}