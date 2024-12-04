package utils.day4

import utils.readInput
import kotlin.system.measureTimeMillis

val vectors = listOf(Pair(0,1), Pair(0,-1), Pair(-1,0),Pair(-1,1), Pair(-1,-1), Pair(1,-1),Pair(1,1), Pair(1,0))
const val XMAS = "XMAS"

fun countXmas(map: Array<CharArray>, i: Int, j: Int): Int {
    var counter = 0
    for(vector in vectors) {
        var distance = 0
        while(distance < XMAS.length && (i + vector.first*distance in map.indices)
            && (j + vector.second*distance in map[0].indices)) {
            val currentLetter = map[i + vector.first*distance][j + vector.second*distance]
            if (currentLetter == XMAS[distance]) {
                distance++
            } else {
                break
            }
        }
        if (distance == XMAS.length) {
            counter++
        }
    }
    return counter
}

fun countCrossMas(map: Array<CharArray>, i: Int, j: Int): Int {
    return if (insideBordersA(map, i, j) && topLeftToBotRight(map, i, j) && botLeftToTopRight(map, i, j)) 1 else 0
}

private fun insideBordersA(map: Array<CharArray>, i: Int, j: Int) =
    map[i][j] == 'A' && i > 0 && i < map.size - 1 && j > 0 && j < map[0].size - 1

private fun topLeftToBotRight(map: Array<CharArray>, i: Int, j: Int) =
    (map[i - 1][j - 1] == 'M' && map[i + 1][j + 1] == 'S') || (map[i - 1][j - 1] == 'S' && map[i + 1][j + 1] == 'M')

private fun botLeftToTopRight(map: Array<CharArray>, i: Int, j: Int) =
    (map[i - 1][j + 1] == 'M' && map[i + 1][j - 1] == 'S') || (map[i - 1][j + 1] == 'S' && map[i + 1][j - 1] == 'M')

fun execute(map: Array<CharArray>, f: (map: Array<CharArray>, i: Int, j: Int) -> Int): Int {
    return map.foldIndexed(0) { i, acc, row ->
        acc + row.foldIndexed(0) { j, accRow, elem ->
            accRow + f(map, i, j)
        }
    }
}

fun part1(map: Array<CharArray>): Int {
    return execute(map, ::countXmas)
}

fun part2(map: Array<CharArray>): Int {
    return execute(map, ::countCrossMas)
}

fun main() {
    val mapTest = readInput("day4_test").map { it.toCharArray() }.toTypedArray<CharArray>()
    val map = readInput("day4").map { it.toCharArray() }.toTypedArray<CharArray>()

    measureTimeMillis {
        val result = part1(mapTest)
        assert(result == 18)
    }

    measureTimeMillis {
        val result = part1(map)
        println(result)
    }

    measureTimeMillis {
        val result = part2(mapTest)
        assert(result == 9)
    }

    measureTimeMillis {
        val result = part2(map)
        println(result)
    }

}
