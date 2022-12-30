package year2022.day17

import commons.Day
import commons.Validator

fun main() =
    Validator(Tetris::class.java).run(Validator.ValidationType.REAL, 3068, 1514285714288, 3092, 1528323699442)

class Tetris(realData: Boolean) : Day(realData) {

    private val width = 7
    private val blocks = 5
    private val scanHeight = 25
    private val moveDown: (Stone) -> Unit = { stone -> stone.move(0, -1) }

    override fun a(): Any {
        val data = content.parse()
        return solution(data, 2022)
    }

    override fun b(): Any {
        val data = content.parse()
        return solution(data, 1000000000000)
    }

    private fun List<String>.parse(): List<(Stone) -> Unit> =
        first().toList().map { if (it == '>') { stone -> stone.move(1, 0) } else { stone -> stone.move(-1, 0) } }

    private fun getBlock(n: Int, y: Int) = when (n.mod(blocks)) {
        0 -> listOf(Stone(3, y), Stone(4, y), Stone(5, y), Stone(6, y))
        1 -> listOf(Stone(4, y), Stone(3, y + 1), Stone(4, y + 1), Stone(5, y + 1), Stone(4, y + 2))
        2 -> listOf(Stone(3, y), Stone(4, y), Stone(5, y), Stone(5, y + 1), Stone(5, y + 2))
        3 -> listOf(Stone(3, y), Stone(3, y + 1), Stone(3, y + 2), Stone(3, y + 3))
        4 -> listOf(Stone(3, y), Stone(4, y), Stone(3, y + 1), Stone(4, y + 1))
        else -> throw IllegalArgumentException()
    }

    private fun Set<Stone>.moveIfAble(block: List<Stone>, instruction: (Stone) -> Unit): Boolean {
        block.copy().onEach(instruction).forEach { stone ->
            if (stone.x < 1 || stone.x > width || contains(stone)) return false
        }
        block.onEach(instruction)
        return true
    }

    private fun createMap() = mutableSetOf<Stone>().apply {
        (1..width).forEach { x ->
            add(Stone(x, 0))
        }
    }

    private fun solution(instructions: List<(Stone) -> Unit>, roundNumber: Long): Long {
        val map = createMap()

        var height = 0
        var step = 0

        val history = mutableMapOf<List<Int>, Int>()
        val heightList = mutableListOf<Int>()

        for (turn in 0 until minOf(Int.MAX_VALUE.toLong(), roundNumber).toInt()) {
            val block = getBlock(turn, height + 4)

            val state = (1..width).map { x ->
                (0 until scanHeight).map { y ->
                    if (map.contains(Stone(x, height - y))) 1 else 0
                }
            }.flatten() + listOf(step.mod(instructions.size), turn.mod(blocks))

            while (true) {
                val instruction = instructions[step.mod(instructions.size)]
                map.moveIfAble(block, instruction)
                step++

                if (!map.moveIfAble(block, moveDown)) {
                    block.forEach { stone ->
                        map.add(stone)
                        height = maxOf(height, stone.y)
                    }
                    break
                }
            }

            heightList.add(height)
            if (state in history) {
                val oldState = history[state]!!
                val heightDiff = (heightList[turn] - heightList[oldState]).toLong()
                val cyclesNumber = (roundNumber - (turn + 1)) / (turn - oldState)
                val diff = ((roundNumber - (turn + 1)).mod((turn - oldState)))
                return height.toLong() + cyclesNumber * heightDiff + heightList[oldState + diff] - heightList[oldState]
            }

            history[state] = turn
        }
        return height.toLong()
    }

    private fun List<Stone>.copy() = map { stone -> stone.copy() }.toList()

    private data class Stone(
        var x: Int,
        var y: Int
    ) {
        fun move(dx: Int, dy: Int) {
            x += dx
            y += dy
        }
    }
}



