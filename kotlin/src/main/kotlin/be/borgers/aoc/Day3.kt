package be.borgers.aoc

import be.borgers.aoc.util.getInput

fun main(args: Array<String>) {
    val fabric = Fabric(getInput(3).map { parseLine(it) })

    part1(fabric)
    part2(fabric)
}

private fun part2(fabric: Fabric) {
    println(fabric.getPart2Result())
}

private fun part1(fabric: Fabric) {
    println(fabric.getPart1Result())
}


private fun parseLine(line: String): Claim {
    val (claimId, _, location, size) = line.split(" ")
    val (x, y) = location.substringBeforeLast(":").split(",")
    val (width, heigth) = size.split("x")

    return Claim(claimId.substring(1).toInt(), Pair(x.toInt(), y.toInt()), Pair(width.toInt(), heigth.toInt()))

}

data class Claim(val id: Int, val location: Pair<Int, Int>, val size: Pair<Int, Int>, var overlaps: Boolean = false) {
    fun getPoints(): List<Pair<Int, Int>> {
        val result = mutableListOf<Pair<Int, Int>>()
        for (x in location.first until location.first + size.first) {
            for (y in location.second until location.second + size.second) {
                result.add(Pair(x, y))
            }
        }

        return result
    }
}

class Fabric(private val claims: List<Claim>) {
    private val result: MutableMap<Pair<Int, Int>, MutableList<Int>> = mutableMapOf()

    init {
        claims.forEach { claim ->
            claim.getPoints().forEach {
                val claimIds = result.getOrDefault(it, mutableListOf())
                if (claimIds.isNotEmpty()) {
                    claim.overlaps = true
                    claimIds.map { claimId -> claims.find { c -> claimId == c.id } }.forEach { c -> c?.overlaps = true }
                }
                claimIds.add(claim.id)
                result[it] = claimIds
            }
        }
    }

    fun getPart1Result(): Int {
        return result.values.filter { it.size > 1 }.count()
    }

    fun getPart2Result() = claims.first { !it.overlaps }.id
}