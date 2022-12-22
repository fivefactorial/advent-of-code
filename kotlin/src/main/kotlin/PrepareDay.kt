import java.io.File
import java.util.*

fun main() {
    val year = input("Year", Calendar.getInstance().get(Calendar.YEAR).toString())?.toInt()
    val day = input("Day", Calendar.getInstance().get(Calendar.DAY_OF_MONTH).toString())?.toInt()

    val src = File("src/main/kotlin")
    val yearFolder = File(src, "year$year").apply { if (!exists()) mkdir() }
    val dayFolder = File(yearFolder, String.format("day%02d", day)).apply { if (!exists()) mkdir() }

    val dayName = String.format("Day%02d", day)
    val dayFile = File(dayFolder, "$dayName.kt")
    if (dayFile.exists()) {
        println("File already exists!")
        return
    }
    println("Creating $dayFile")
    val fileContent = """
package ${yearFolder.name}.${dayFolder.name}

import commons.Day

fun main() = println($dayName(false))

class $dayName(realData: Boolean) : Day(realData) {

    override fun a(): Any {
        val data = content.parse()

        println(data)

        return -1
    }

    private fun List<String>.parse() {

    }

}
    """.trimIndent()

    dayFile.writeText(fileContent)
    println("Done!")
}


private fun input(question: String, default: String? = null): String? {
    print(question)
    default?.run {
        print(" ($default)")
    }
    print(": ")
    Scanner(System.`in`).nextLine().apply {
        return if (isEmpty()) default else this
    }
}