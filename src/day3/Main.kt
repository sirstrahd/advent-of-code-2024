package utils.day3

import utils.readInput
import kotlin.system.measureTimeMillis

val mulRegex = "mul\\((\\d+),(\\d+)\\)".toRegex()

val doRegex = "do\\(\\)".toRegex()
val dontRegex = "don't\\(\\)".toRegex()

fun part1(inputs: List<String>): Int {
    return inputs.fold(0) { acc, line ->
        val matchResults = mulRegex.findAll(line)
        acc + matchResults.fold(0) { acc, element ->
            val (first, second) = element.destructured
            acc + (first.toInt() * second.toInt())
        }
    }
}

fun isProgramEnabled(elemIndex: Int, doIndexes: List<Int>, dontIndexes: List<Int>): Boolean {
    val prevDo = doIndexes.findLast {it < elemIndex}
    val prevDont = dontIndexes.findLast {it < elemIndex}
    return prevDont == null || (prevDo != null && prevDo > prevDont)
}

fun part2(inputs: List<String>): Int {
    val input = inputs.joinToString("-");
    val matchResults = mulRegex.findAll(input)
    val doResults = doRegex.findAll(input).map { it.range.first }.toList()
    val dontResults = dontRegex.findAll(input).map { it.range.first }.toList()
    return matchResults.fold(0) { acc, element ->
        if (isProgramEnabled(element.range.first, doResults, dontResults)) {
            val (first, second) = element.destructured
            acc + (first.toInt() * second.toInt())
        } else {
            acc
        }
    }
}

fun main() {
//    val testInput = readInput("day3_test")
//    val testInput2 = readInput("day3_test2")
    val input = readInput("day3")

//    assert(part1(testInput)==161)

    val millis1 =  measureTimeMillis {
        val result1 = part1(input)
        println(result1)
    }

//    assert(part2(testInput2)==48)
    val millis2 =  measureTimeMillis {
        val result2 = part2(input)
        println(result2)
    }

    println("Part 1 took ${millis1} ms, part 2 took ${millis2} ms")

}
