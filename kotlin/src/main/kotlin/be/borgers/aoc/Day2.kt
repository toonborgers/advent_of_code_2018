package be.borgers.aoc

import be.borgers.aoc.util.getInput

fun main(args: Array<String>) {
    part1()
    part2()
}


private fun part1() {
    var count2 = 0
    var count3 = 0

    getInput(2)
            .map { it.toList() }
            .forEach {
                it.groupingBy { c -> c }.eachCount().map { p -> p.value }.distinct().forEach { c ->
                    if (c == 2) count2++

                    if (c == 3) count3++
                }
            }

    println(count2 * count3)
}

private fun part2() {
    val input = getInput(2)

    println(input
            .map { curr ->
                input.map { curr.differences(it) }.filter { it.first }.map { it.second }
            }
            .first { it.size == 1 }
            [0])
}

fun String.differences(other: String): Pair<Boolean, String> {
    var result = ""
    this.forEachIndexed { i, c ->
        if (other[i] == c) {
            result += c
        }
    }

    return Pair(result.length == this.length - 1, result)
}