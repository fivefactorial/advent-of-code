package year2022.day19

import commons.Day
import commons.Validator
import java.util.*
import kotlin.math.ceil
import kotlin.math.max

fun main() = Validator(GeodeFinder::class.java).run(Validator.ValidationType.BOTH, 33, 3472, 1192, 14725)

class GeodeFinder(realData: Boolean) : Day(realData) {

    override fun a() = content.parse().sumOf { it.id * calculate(it, 24) }

    override fun b() = content.parse().take(3).map { calculate(it, 32) }.reduce { left, right -> left * right }

    private fun List<String>.parse() = map { row ->
        val numbers = Regex("\\d+").findAll(row).map { it.value.toInt() }.toList()
        Blueprint(
            id = numbers[0],
            ore = Robot(cost = Resource(ore = numbers[1]), constructs = Resource(ore = 1)),
            clay = Robot(cost = Resource(ore = numbers[2]), constructs = Resource(clay = 1)),
            obsidian = Robot(cost = Resource(ore = numbers[3], clay = numbers[4]), constructs = Resource(obsidian = 1)),
            geode = Robot(cost = Resource(ore = numbers[5], obsidian = numbers[6]), constructs = Resource(geode = 1))
        )
    }

    private fun calculate(blueprint: Blueprint, minutes: Int): Int {
        val queue = PriorityQueue<State>().apply { add(State(minutes = minutes)) }
        var best = 0
        while (queue.isNotEmpty()) {
            val state = queue.poll()
            if (state.canOutproduce(best)) {
                queue.addAll(state.next(blueprint))
            }
            best = max(best, state.resources.geode + state.robots.geode * (state.minutes - 1))
        }
        return best
    }

    private fun State.next(blueprint: Blueprint) = buildList {
        if (blueprint.maxOre > robots.ore)
            add(nextWith(blueprint.ore))
        if (blueprint.maxClay > robots.clay)
            add(nextWith(blueprint.clay))
        if (robots.clay > 0 && blueprint.maxObsidian > robots.obsidian)
            add(nextWith(blueprint.obsidian))
        if (robots.obsidian > 0)
            add(nextWith(blueprint.geode))
    }.filter { it.minutes > 0 }

    private data class State(
        val minutes: Int,
        val resources: Resource = Resource(ore = 1),
        val robots: Resource = Resource(ore = 1)
    ) : Comparable<State> {

        fun nextWith(robot: Robot): State {
            val minutes = timeToBuild(robot)
            return copy(
                minutes = this.minutes - minutes,
                resources = resources + robots * minutes - robot.cost,
                robots = robots + robot.constructs
            )
        }

        fun canOutproduce(best: Int): Boolean {
            val potentialProduction = (0 until minutes - 1).sumOf { it + robots.geode }
            return resources.geode + potentialProduction > best
        }

        override fun compareTo(other: State) = other.resources.geode.compareTo(resources.geode)

        private fun timeToBuild(robot: Robot): Int {
            val remaining = (robot.cost - resources).coerceAtLeast(0)
            return maxOf(
                ceil(remaining.ore / robots.ore.toFloat()).toInt(),
                ceil(remaining.clay / robots.clay.toFloat()).toInt(),
                ceil(remaining.obsidian / robots.obsidian.toFloat()).toInt(),
            ) + 1
        }
    }

    private data class Blueprint(
        val id: Int,
        val ore: Robot,
        val clay: Robot,
        val obsidian: Robot,
        val geode: Robot
    ) {
        private val robots = listOf(ore, clay, obsidian, geode)
        val maxOre = robots.maxOf { it.cost.ore }
        val maxClay = robots.maxOf { it.cost.clay }
        val maxObsidian = robots.maxOf { it.cost.obsidian }
    }

    private data class Robot(
        val cost: Resource,
        val constructs: Resource
    )

    private data class Resource(
        val ore: Int = 0,
        val clay: Int = 0,
        val obsidian: Int = 0,
        val geode: Int = 0
    ) {

        fun coerceAtLeast(minimumValue: Int) = Resource(
            ore.coerceAtLeast(minimumValue),
            clay.coerceAtLeast(minimumValue),
            obsidian.coerceAtLeast(minimumValue),
            geode.coerceAtLeast(minimumValue)
        )

        operator fun times(n: Int) = Resource(ore * n, clay * n, obsidian * n, geode * n)

        operator fun plus(other: Resource) =
            Resource(ore + other.ore, clay + other.clay, obsidian + other.obsidian, geode + other.geode)

        operator fun minus(other: Resource) =
            Resource(ore - other.ore, clay - other.clay, obsidian - other.obsidian, geode - other.geode)

    }
}
