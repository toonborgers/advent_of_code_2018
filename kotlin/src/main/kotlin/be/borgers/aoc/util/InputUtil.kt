package be.borgers.aoc.util

import java.io.File

const val INPUTS_DIR = "src/main/resources/inputs/"

fun getInput(day: Int): List<String> {
    return File(File(INPUTS_DIR), "day$day.txt").readLines()
}

fun getInputAsInts(day:Int) = getInput(day).map { it.toInt() }

