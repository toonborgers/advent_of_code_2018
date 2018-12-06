package be.borgers.aoc

import be.borgers.aoc.Util4.DATE_TIME_FORMATTER
import be.borgers.aoc.Util4.guardId
import be.borgers.aoc.util.getInput
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.regex.Pattern

fun main(args: Array<String>) {
    val input: MutableMap<Int, MutableMap<Int, Int>> = parseInput()

//    part1(input)
    part2(input)
}

private fun part1(result: MutableMap<Int, MutableMap<Int, Int>>) {
    val mostId = result.maxBy { it.value.values.sum() }!!.key
    val mostMinute = result[mostId]!!.maxBy { it.value }!!.key
    println(mostId * mostMinute)
}

private fun part2(input: MutableMap<Int, MutableMap<Int, Int>>) {
    val (guardId, entry) = input
            .map { e -> Pair(e.key, e.value.maxBy { it.value }) }
            .filter { it.second != null }
            .maxBy { it.second!!.value }!!

    println(guardId * entry!!.key)
}


private fun parseInput(): MutableMap<Int, MutableMap<Int, Int>> {
    val result: MutableMap<Int, MutableMap<Int, Int>> = mutableMapOf()
    var currentGuardId: Int = -1
    var currentGuardTimes: MutableMap<Int, Int> = mutableMapOf()
    var startCurrentGuard: LocalDateTime = LocalDateTime.now()

    getInput(4)
            .sorted()
            .forEach { line ->
                val dateTime = LocalDateTime.parse(line.substringBefore("]").substring(1), DATE_TIME_FORMATTER)
                val otherPart = line.substringAfter("] ")

                when {
                    otherPart.startsWith("Guard") -> {
                        if (currentGuardId > -1) {
                            result[currentGuardId] = currentGuardTimes
                        }

                        currentGuardId = guardId(otherPart)
                        currentGuardTimes = result.getOrDefault(currentGuardId, mutableMapOf())
                    }
                    otherPart.contains("falls asleep") ->
                        startCurrentGuard = dateTime
                    else ->
                        while (startCurrentGuard.isBefore(dateTime)) {
                            currentGuardTimes[startCurrentGuard.minute] = currentGuardTimes.getOrDefault(startCurrentGuard.minute, 0) + 1
                            startCurrentGuard = startCurrentGuard.plusMinutes(1)
                        }
                }

            }

    return result
}

object Util4 {
    val DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")!!
    private val GUARD_ID_PATTERN = Pattern.compile("(\\d+)")!!

    fun guardId(input: String): Int {
        val matcher = GUARD_ID_PATTERN.matcher(input)
        matcher.find()
        return matcher.group(1).toInt()
    }
}
