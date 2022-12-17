package year2022.day04

import commons.Day

fun main() = println(OverlappingJobs())

class OverlappingJobs : Day(true) {

    private var yes: Int = 1
    private var no: Int = 0

    override fun a() = content.sumOf { row ->
        val (aa, bb) = row.parse()
        val inter = aa.intersect(bb).toSet()
        if (inter == aa || inter == bb) yes else no
    }

    override fun b() = content.sumOf { row ->
        val (aa, bb) = row.parse()
        val inter = aa.intersect(bb)
        if (inter.isEmpty()) no else yes
    }

    private fun String.parse(): Pair<Set<Int>, Set<Int>> {
        val parsed = split(",")
            .map { it.split("-").map { n -> n.toInt() } }
            .map { (it[0]..it[1]).toSet() }
        return Pair(parsed[0], parsed[1])
    }
}