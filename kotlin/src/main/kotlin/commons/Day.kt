package commons

import java.io.File

abstract class Day(val realData: Boolean) {

    protected val yes: Int = 1
    protected val no: Int = 0

    private val year: String
    private val day: String

    private val realContent: List<String>
    private val practiceContent: List<String>

    protected val content: List<String>
        get() = if (realData) realContent else practiceContent

    init {
        val classPath = this::class.java.toString().split(".")
        year = classPath[0].substring(10)
        day = classPath[1].substring(3)

        val resources = File("resources").apply { if (!exists()) mkdir() }
        val yearFile = File(resources, year).apply { if (!exists()) mkdir() }

        val realData = File(yearFile, "day$day.real.txt").apply { if (!exists()) writeText("") }
        val practiceData = File(yearFile, "day$day.practice.txt").apply { if (!exists()) writeText("") }

        realContent = realData.readLines()
        practiceContent = practiceData.readLines()
    }

    open fun a(): Any = "No Sol."
    open fun b(): Any = "No Sol."

    fun name() = "$year, ${day.toInt()} of december"

    override fun toString() =
        "${name()}\nUsing ${if (realData) "real" else "practice"} data\na: ${a()}\nb: ${b()}"

}