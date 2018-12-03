package be.borgers.aoc

import be.borgers.aoc.util.getInputAsInts

fun main(args: Array<String>) {
    part1()
    part2()
}

private fun part1() {
    println(getInputAsInts(1)
            .reduce { sum, element -> sum + element })
}

private fun part2() {
    val history = mutableListOf(0)
    val inputs = getInputAsInts(1)
    var index = 0

    while (true) {
        val curr = (history.last() + inputs[index++ % inputs.size])
        if (history.contains(curr)) {
            println(curr)
            return
        }
        history.add(curr)
    }
}
