package year2022.day21

import commons.Day
import commons.Validator


fun main() = Validator(MonkeyMath::class.java).run(Validator.ValidationType.REAL, 152, 301, 194501589693264, 3887609741189)

class MonkeyMath(realData: Boolean) : Day(realData) {

    override fun a() = content.parse().calculate("root")
    override fun b() = solve(content.parse().toMutableMap())

    private fun List<String>.parse() = map { row -> row.split(':').map { it.trim() } }.map { (name, expr) ->
        if (isInt(expr)) {
            name to listOf(expr)
        } else {
            var data: List<String>? = null
            for (op in listOf("+", "-", "*", "/")) {
                if (expr.contains(op)) {
                    val sp = expr.split(op).map { it.strip() }
                    data = listOf(sp[0], sp[1], op)
                    break
                }
            }
            name to data!!
        }
    }.associateBy { it.first }.mapValues { it.value.second }

    private fun Map<String, List<String>>.calculate(node: String): Long {
        val operation = this[node]!!
        if (operation.size == 1) return operation[0].toLong()
        when (operation[2]) {
            "+" -> return calculate(operation[0]) + calculate(operation[1])
            "-" -> return calculate(operation[0]) - calculate(operation[1])
            "*" -> return calculate(operation[0]) * calculate(operation[1])
            "/" -> return calculate(operation[0]) / calculate(operation[1])
        }
        throw IllegalArgumentException()
    }

    private fun solve(data: MutableMap<String, List<String>>): Long {
        val human = "humn"
        val (left, right) = data["root"]!!.take(2)

        val leftVariations = (0..5).map {
            data[human] = listOf("$it")
            data.calculate(left)
        }.toSet()

        val (variableNode, goal) =
            if (leftVariations.size > 1) left to data.calculate(right) else right to data.calculate(left)

        var low = 0L
        var high = 10e14.toLong()
        while (low < high) {
            val mid = (low + high) / 2L
            data[human] = listOf("$mid")
            val attempt = data.calculate(variableNode)
            if (attempt == goal) {
                return mid
            }
            if (attempt <= goal) high = mid - 1
            else low = mid + 1
        }
        return -1
    }

    private fun isInt(s: String) = s.toIntOrNull() != null

}
