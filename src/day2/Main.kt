package day2

import utils.readInput

fun part1(input: List<String>): Int {
    return input.map { it.split("\\s+".toRegex()).map {it.toInt()}
    }.map {
        !checkIfFailurePart1(it)
    }.count {it}
}

private fun checkIfFailurePart1(it: List<Int>): Boolean {
    val ascending = it[1] > it[0]
    var i = 0
    var failure = false;
    while (i < (it.size - 1) && !failure) {
        val currentIncrease = it[i + 1] - it[i]
        failure = ((!ascending || !(currentIncrease in 1..3)) && (ascending || !(currentIncrease  in -3..-1)))
        i++
    }
    return failure
}

fun part2(input: List<String>): Int {
    return input.map { it.split("\\s+".toRegex()).map {it.toInt()}
    }.map {
        val ascending = it[1] > it[0]
        var i = 0
        var failure = false
        while (i<(it.size-1)) {
            val currentIncrease = it[i+1] - it[i]
            if ((!ascending || !(currentIncrease in 1..3)) && (ascending || !(currentIncrease  in -3..-1))) {
                var j = if (i == 0) 0 else i-1;
                var salvageable = false;
                while(j<=i+1) {
                    val list = buildList() {
                        addAll(it.subList(0,j))
                        addAll(it.subList(j+1,it.size))
                    }
                    if (!checkIfFailurePart1(list)) {
                        salvageable = true;
                        break;
                    }
                    j++;
                }
                if (!salvageable) {
                    failure=true;
                }
                break;
            }
            i++
        }
        !failure
    }.count {it}
}

fun main() {
    val testInput = readInput("day2")

    val result1 = part1(testInput)
    println("Result1: ${result1}")

    val result2 = part2(testInput)
    println("Result2: ${result2}")

}