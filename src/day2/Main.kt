package day2

import utils.readInput

fun part1(input: List<String>): Int {
    return input.map { it.split("\\s+".toRegex()).map {it.toInt()}
    }.map {
        !checkIfFailurePart1(it)
    }.count {it}
}

fun part2(input: List<String>): Int {
    return input.map { it.split("\\s+".toRegex()).map {it.toInt()}
    }.map {
        val ascending = it[1] > it[0]
        var i = 0
        var failure = false
        while (i<(it.size-1) && !failure) {
            val currentIncrease = it[i+1] - it[i]
            failure = ((!ascending || currentIncrease !in 1..3) && (ascending || currentIncrease !in -3..-1))
            i++
        }
        if (failure) {
            failure = tryWithDampener(i, failure, it)
        }
        !failure
    }.count {it}
}

private fun checkIfFailurePart1(it: List<Int>): Boolean {
    val ascending = it[1] > it[0]
    var i = 0
    var failure = false
    while (i < (it.size - 1) && !failure) {
        val currentIncrease = it[i + 1] - it[i]
        failure = ((!ascending || !(currentIncrease in 1..3)) && (ascending || !(currentIncrease  in -3..-1)))
        i++
    }
    return failure
}

private fun tryWithDampener(i: Int, failure: Boolean, it: List<Int>): Boolean {
    var i1 = i
    var failure1 = failure
    i1--
    var j = if (i1 == 0) 0 else i1 - 1
    while (j <= i1 + 1 && failure1) {
        val list = removeElement(it, j)
        if (!checkIfFailurePart1(list)) {
            failure1 = false
        }
        j++
    }
    return failure1
}

private fun removeElement(it: List<Int>, j: Int): List<Int> {
    return buildList() {
        addAll(it.subList(0, j))
        addAll(it.subList(j + 1, it.size))
    }
}

fun main() {
    val testInput = readInput("day2")

    val result1 = part1(testInput)
    println("Result1: ${result1}")

    val result2 = part2(testInput)
    println("Result2: ${result2}")

}
