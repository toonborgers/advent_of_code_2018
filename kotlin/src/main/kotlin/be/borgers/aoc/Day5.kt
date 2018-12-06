package be.borgers.aoc

import be.borgers.aoc.util.getInput

fun main(args: Array<String>) {
    val input = getInput(5)[0]

    part1(input)
    day2(input)
}

private fun part1(input: String) {
    val result = react(input)

    println(result.length)
}

private fun day2(input: String) {
    println('a'.rangeTo('z')
            .map { react(input.replace(it.toString(), "", true)).length }
            .minBy { it })
}

private fun react(input: String): String {
    val resultList = mutableListOf<Char>()
    input.forEach {
        if (resultList.isEmpty() || !sameDifferentCase(it, resultList[resultList.size - 1])) {
            resultList.add(it)
        } else {
            resultList.removeAt(resultList.size - 1)
        }
    }
    val result = resultList.joinToString(separator = "")
    return result
}

fun sameDifferentCase(first: Char, second: Char): Boolean =
        first.toLowerCase() == second.toLowerCase() && first.isLowerCase() != second.isLowerCase()

