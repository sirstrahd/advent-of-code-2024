package utils.day5

import utils.readInput
import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.HashSet
import kotlin.system.measureTimeMillis

fun part1(input: List<String>, rules: MutableMap<Int, MutableSet<Int>>): Int {
    return input.countValidPrints(rules)
}

fun part2(input: List<String>, rules: MutableMap<Int, MutableSet<Int>>): Int {
    return input.fixInvalidPrints(rules)
}

fun List<String>.toRulesMap(): MutableMap<Int, MutableSet<Int>> {
    val definitions = this.subList(0,this.indexOf(""))
    val rules = HashMap<Int, MutableSet<Int>>()
    for (rule in definitions) {
        val parts = rule.split("|")
        val part1 = parts[0].toInt()
        val part2 = parts[1].toInt()
        if (rules.containsKey(part1)) {
            rules[part1]?.add(part2)
        } else {
            val newSet = HashSet<Int>();
            newSet.add(part2)
            rules[part1] = newSet
        }
    }
    return rules
}

fun List<Int>.compliesWith(rules: MutableMap<Int, MutableSet<Int>>): Boolean {
    for(i in this.indices) {
        val value = this[i]
        for(j in i+1..< this.size) {
            if (rules[this[j]]?.contains(this[i]) == true) {
                return false;
            }
        }
    }
    return true;
}

fun List<String>.countValidPrints(rules: MutableMap<Int, MutableSet<Int>>): Int {
    val prints = this.subList(this.indexOf("")+1, this.size).map {it.split(",").map{it.toInt()}}
    return prints.filter { it.compliesWith(rules) }.fold(0) { acc, it -> (acc + it[(it.size/2)])}
}

fun List<Int>.reorder(rulesInput: MutableMap<Int, MutableSet<Int>>): List<Int> {
    val rules = HashMap<Int, MutableSet<Int>>()
    rules.putAll(rulesInput.filter{this.contains(it.key)})
    val input = LinkedList<Int>()
    input.addAll(this)
    val output = LinkedList<Int>()
    while (!input.isEmpty()) {
        val nextElement = input.find { subject ->
            var found = false;
            rules.keys.forEach { key ->
                if (rules[key]?.contains(subject) == true) {
                    found = true
                }
            }
            !found
        }!!
        output.add(nextElement)
        input.remove(nextElement)
        rules.remove(nextElement)
    }
    return output
}

fun List<String>.fixInvalidPrints(rules: MutableMap<Int, MutableSet<Int>>): Int {
    val prints = this.subList(this.indexOf("")+1, this.size).map {it.split(",").map{it.toInt()}}
    return prints.filter { !it.compliesWith(rules) }.fold(0) {
                                                             acc, elems ->
                                                                 acc + elems.reorder(rules)[elems.size/2]

    }
}



fun main() {
    val testInput = readInput("day5_test")
    val rulesTest = testInput.toRulesMap()
    val input = readInput("day5")
    val rules = input.toRulesMap()

    measureTimeMillis {
        val result = part1(testInput, rulesTest)
        println(result) // should be 143
    }

    measureTimeMillis {
        val result = part1(input, rules)
        println(result)
    }

    measureTimeMillis {
        val result = part2(testInput, rulesTest)
        println(result) // should be 123
    }

    measureTimeMillis {
        val result = part2(input, rules)
        println(result)
    }

}
