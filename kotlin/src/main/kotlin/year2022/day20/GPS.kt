package year2022.day20

import commons.Day
import commons.Validator

fun main() = Validator(GPS::class.java).run(Validator.ValidationType.BOTH, 3, 1623178306, 4066, 6704537992933)

class GPS(realData: Boolean) : Day(realData) {

    override fun a() = content.parse().solve()

    override fun b() = content.parse(key = 811589153).solve(times = 10)

    private fun List<String>.parse(key: Int = 1) = mapIndexed { i, e -> i to e.toLong() * key }

    private fun List<Pair<Int, Long>>.solve(times: Int = 1): Long {
        val data = toMutableList()

        (1..times).forEach { _ ->
            forEach {
                val index = data.indexOf(it)
                data.removeAt(index)
                data.add((index + it.second).mod(data.size), it)
            }
        }

        return listOf(1000, 2000, 3000)
            .sumOf { data[(it + data.indexOfFirst { e -> e.second == 0L }) % data.size].second }
    }

}
