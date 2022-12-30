package commons

class Validator(private val dayClass: Class<out Day>) {

    fun run(
        type: ValidationType,
        practiceAnswerA: Any,
        practiceAnswerB: Any,
        realAnswerA: Any? = null,
        realAnswerB: Any? = null
    ) {
        println((dayClass.constructors.first().newInstance(false) as Day).name())
        if (type != ValidationType.REAL) run(false, practiceAnswerA, practiceAnswerB)
        if (type != ValidationType.PRACTICE) run(true, realAnswerA, realAnswerB)
    }

    private fun run(realData: Boolean, answerA: Any?, answerB: Any?) {
        val day = dayClass.constructors.first().newInstance(realData) as Day
        val partA = day.a()
        val partB = day.b()

        val resultA = when (answerA.toString()) {
            null -> ""
            partA.toString() -> " <- Correct"
            else -> " <- Should be $answerA"
        }
        val resultB = when (answerB.toString()) {
            null -> ""
            partB.toString() -> " <- Correct"
            else -> " <- Should be $answerB"
        }

        println("Using ${if (realData) "real" else "practice"} data")
        println("a: $partA $resultA")
        println("b: $partB $resultB")
    }

    enum class ValidationType {
        PRACTICE, REAL, BOTH
    }
}