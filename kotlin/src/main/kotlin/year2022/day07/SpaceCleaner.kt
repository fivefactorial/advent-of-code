package year2022.day07

import commons.Day

fun main() = println(SpaceCleaner())

class SpaceCleaner : Day(true) {

    override fun a(): Any {
        val dirs = parseInput()
        return dirs.filter { it.size() <= 100000 }.sumOf { it.size() }
    }

    override fun b(): Any {
        val dirs = parseInput()
        val root = dirs.first()

        val totalDiskspace = 70000000
        val spaceNeeded = 30000000
        val spaceToFree = spaceNeeded - (totalDiskspace - root.size())

        val candidates = dirs.filter { it.size() >= spaceToFree }
        return candidates.map { it.size() }.minOf { it }
    }

    private fun parseInput(): List<Dir> {
        val dirs = mutableListOf<Dir>()
        val root = Dir("/")
        dirs.add(root)
        var current = root
        content.forEach { row ->
            when {
                row == "$ cd /" -> current = root
                row == "$ ls" -> {}
                row.startsWith("dir") -> {
                    val name = row.subSequence(4, row.length).toString()
                    val dir = Dir(name, current)
                    current.dirs.add(dir)
                    dirs.add(dir)
                }
                row.startsWith("$ cd") -> {
                    val dir = row.split(" ")[2]
                    if (dir == "..") current = current.parent!! else current =
                        current.dirs.filter { it.name == dir }.first()
                }
                else -> {
                    val parts = row.split(" ")
                    val size = parts[0].toInt()
                    current.files.add(size)
                }
            }
        }
        return dirs
    }

    private data class Dir(val name: String, val parent: Dir? = null) {
        val files = mutableListOf<Int>()
        val dirs = mutableListOf<Dir>()

        fun size(): Int = files.sumOf { it } + dirs.sumOf { it.size() }

        override fun toString(): String {
            return "$name (${size()})"
        }
    }

}