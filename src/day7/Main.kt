package utils.day7

import utils.readInput
import kotlin.system.measureTimeMillis

val operatorsPart1 = listOf("+","*")

val operatorsPart2 = listOf("+","*","||")


fun part1(input: List<String>): Long {
    return input.sumOf {
        val elements = it.split(": ")
        val total = elements[0].toLong()
        val components = elements[1].split(" ").map { it.toLong() }
        if (recursivelyCheck(operatorsPart1, total, 0, components)) {
            total
        } else {
            0
        }
    }
}

fun part2(input: List<String>): Long {
    return input.sumOf {
        val elements = it.split(": ")
        val total = elements[0].toLong()
        val components = elements[1].split(" ").map { it.toLong() }
        if (recursivelyCheck(operatorsPart2, total, 0, components)) {
            total
        } else {
            0
        }
    }
}

fun recursivelyCheck(operators: List<String>, targetTotal: Long, currentTotal: Long, remainingMembers: List<Long>): Boolean {
    if (remainingMembers.size == 0 && targetTotal == currentTotal) {
        return true
    } else if (remainingMembers.size > 0 && currentTotal <= targetTotal) {
        for(operator in operators) {
            if (operator == "+") {
                if (recursivelyCheck(
                        operators, targetTotal,
                        currentTotal + remainingMembers.first(),
                        remainingMembers.subList(1, remainingMembers.size)
                    )) {
                    return true;
                }
            } else if (operator == "*") {
                if (recursivelyCheck(
                        operators, targetTotal,
                        currentTotal * remainingMembers.first(),
                        remainingMembers.subList(1, remainingMembers.size)
                    )) {
                    return true;
                }
            } else if (operator == "||") {
                if (recursivelyCheck(
                        operators, targetTotal,
                        (currentTotal.toString() +
                                    remainingMembers.first().toString()).toLong(),
                        remainingMembers.subList(1, remainingMembers.size)
                    )) {
                    return true;
                }
            }
        }
    }
    return false
}

fun main() {
    var testInput = readInput("day7_test")
    var input = readInput("day7")
    measureTimeMillis {
        val result = part1(testInput)
        println(result) // should be 3749
    }
    measureTimeMillis {
        val result = part2(testInput)
        println(result) // should be 11387
    }

    measureTimeMillis {
        val result = part1(input)
        println(result)
    }
    measureTimeMillis {
        val result = part2(input)
        println(result)
    }

}
