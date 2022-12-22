package year2022.day16

import commons.Day

fun main() = println(Day16(true))

class Day16(realData: Boolean) : Day(realData) {

    override fun a() = content.parse().solve(1, 30)
    override fun b() = content.parse().solve(2, 26)

    private fun Map<String, Valve>.solve(players: Int, totalTime: Int): Int {
        val sorted = values.sortedBy { if (it.name == "AA") -1 else if (it.flowRate > 0) 0 else 1 }

        val nonZeroValves = sorted.count { it.flowRate > 0 } + 1
        val history = MutableList((1 shl nonZeroValves) * size * 64) { -1 }

        val startValve = sorted.first()
        fun solve(valve: Valve, openedValves: Int, remainingTime: Int, players: Int): Int {
            if (players == 0) return 0
            if (remainingTime == 0) return solve(startValve, openedValves, totalTime, players - 1)
            val index = sorted.indexOf(valve)
            val key = openedValves * size * 64 + index * 31 * 2 + remainingTime * 2 + players
            if (history[key] != -1)
                return history[key]
            if (valve.flowRate > 0 && ((1 shl index) and openedValves) == 0) {
                history[key] = (remainingTime - 1) * valve.flowRate +
                        solve(valve, (1 shl index) or openedValves, remainingTime - 1, players)
            }
            valve.neighbours.map { get(it)!! }.forEach { neighbour ->
                history[key] = maxOf(history[key], solve(neighbour, openedValves, remainingTime - 1, players))
            }
            return history[key]
        }
        return solve(startValve, 0, totalTime, players)
    }

    private fun List<String>.parse() = map { row ->
        val data = Regex("[A-Z]{2}|\\d+").findAll(row).map { it.value }.toList()
        Valve(data)
    }.associateBy { it.name }

    private data class Valve(
        val name: String,
        val flowRate: Int,
        val neighbours: List<String>
    ) {
        constructor(list: List<String>) : this(list[0], list[1].toInt(), list.drop(2))
    }
}