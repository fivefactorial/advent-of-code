package year2022.day08

import commons.Day

fun main() = println(Treehouse())

class Treehouse : Day(true) {

    override fun a(): Any {
        val trees = content.map { row -> row.toList().map { "$it".toInt() } }
        val size = content.first().length

        val ans = mutableListOf<MutableList<Boolean>>()
        (1..size).forEach {
            ans.add(MutableList(size) { false })
        }

        (0 until size).forEach { x ->
            (0 until size).forEach { y ->
                if ((x == 0 || x == size - 1) || (y == 0 || y == size - 1))
                    ans[x][y] = true

            }
        }

        // from left
        (0 until size).forEach { y ->
            var max = trees[0][y]
            (1 until size - 1).forEach { x ->
                if (trees[x][y] > max) {
                    max = trees[x][y]
                    ans[x][y] = true
                }
            }
        }

        //from right
        (0 until size).forEach { y ->
            var max = trees[size - 1][y]
            (1 until size - 1).forEach { x_temp ->
                val x = size - 1 - x_temp
                if (trees[x][y] > max) {
                    max = trees[x][y]
                    ans[x][y] = true
                }
            }
        }

        //from up
        (0 until size).forEach { x ->
            var max = trees[x][0]
            (1 until size - 1).forEach { y ->
                if (trees[x][y] > max) {
                    max = trees[x][y]
                    ans[x][y] = true
                }
            }
        }

        //from down
        (0 until size).forEach { x ->
            var max = trees[x][size - 1]
            (1 until size - 1).forEach { y_temp ->
                val y = size - 1 - y_temp
                if (trees[x][y] > max) {
                    max = trees[x][y]
                    ans[x][y] = true
                }
            }
        }

        return ans.sumOf { row -> row.sumOf { if (it) yes else no } }
    }

    override fun b(): Any {
        val trees = content.map { row -> row.toList().map { "$it".toInt() } }
        val size = content.first().length

        val ans = mutableListOf<MutableList<Int>>()
        (1..size).forEach { _ ->
            ans.add(MutableList(size) { 0 })
        }

        (0 until size).forEach { y ->
            (0 until size).forEach { x ->
                ans[y][x] = score(trees, x, y)
            }
        }

        return ans.maxOf { row -> row.maxOf { it } }
    }

    private fun score(trees: List<List<Int>>, x: Int, y: Int): Int {
        val size = trees.size
        val leftSlice = (0 until x).reversed().toList()
        val rightSlice = (x + 1 until size).toList()
        val upSlice = (0 until y).reversed().toList()
        val downSlice = (y + 1 until size).toList()

        val left = horizontal(trees, leftSlice, x, y)
        val right = horizontal(trees, rightSlice, x, y)
        val up = vertical(trees, upSlice, x, y)
        val down = vertical(trees, downSlice, x, y)

        return left * right * up * down
    }

    private fun horizontal(trees: List<List<Int>>, slice: List<Int>, x: Int, y: Int): Int {
        var steps = 0
        slice.forEach { newX ->
            steps++
            if (trees[y][newX] >= trees[y][x]) return steps
        }
        return steps
    }

    private fun vertical(trees: List<List<Int>>, slice: List<Int>, x: Int, y: Int): Int {
        var steps = 0
        slice.forEach { newY ->
            steps++
            if (trees[newY][x] >= trees[y][x]) return steps
        }
        return steps
    }

}