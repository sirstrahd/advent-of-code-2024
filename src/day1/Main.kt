package day1

import utils.readInput
import kotlin.math.abs

private fun getEntries(input: List<String>): Pair<List<Int>, List<Int>> {
    val fileEntries = input.fold(Pair(listOf<Int>(), listOf<Int>())) { acc, line ->
        val entries = line.split("\\s+".toRegex())
        Pair(acc.first.plus(entries.get(0).toInt()), acc.second.plus(entries.get(1).toInt()))
    }
    return fileEntries
}

private fun getSortedEntries(input: List<String>): Pair<List<Int>, List<Int>> {
    val fileEntries = getEntries(input)
    val sortedEntries = Pair(fileEntries.first.sorted(), fileEntries.second.sorted())
    return sortedEntries
}

fun part1(input: List<String>): Int {
    val sortedEntries = getSortedEntries(input)
    return sortedEntries.first.foldIndexed(0, { index, acc, element ->
        acc + abs(sortedEntries.second[index] - element)
    })
}

fun part2(input: List<String>): Int {
    val entries = getEntries(input)
    return entries.first.fold(0, { acc, element ->
        acc + (element * entries.second.filter{ it.equals(element)}.count())
    })
}

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    val testInput = readInput("Day01_test")

    val result1 = part1(testInput)
    println("Result1: ${result1}")

    val result2 = part2(testInput)
    println("Result2: ${result2}")

}