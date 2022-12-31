package year2022.day25

import commons.Day
import commons.Validator

fun main() = Validator(Snafu::class.java).run(
    Validator.ValidationType.BOTH,
    "2=-1=0",
    "Merry Christmas!",
    "20=022=21--=2--12=-2",
    "Merry Christmas!"
)

class Snafu(realData: Boolean) : Day(realData) {

    override fun a() = content.sumOf { it.parseSnafu() }.toSnafu()
    override fun b() = "Merry Christmas!"

    private fun String.parseSnafu(): Long {
        val map = mapOf('1' to 1, '2' to 2, '0' to 0, '-' to -1, '=' to -2)
        return indices.fold(0L) { total, i -> total * 5 + map[this[i]]!! }
    }

    private fun Long.toSnafu(): String {
        val list = listOf(0, 1, 2, -2, -1)
        var number = this
        var snafu = ""
        while (number > 0) {
            val r = (number % 5).toInt()
            val d = list[r]
            snafu = "012=-"[r] + snafu
            number = (number - d) / 5
        }
        return snafu
    }
}