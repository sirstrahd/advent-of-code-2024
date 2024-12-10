package utils.day6

import utils.readInput
import java.util.*
import kotlin.system.measureTimeMillis
// (0,0)                ...   (0, x)
// ...
// (y,0)   ...                (y, x)
fun Char.isGuard(): Boolean {
    return this.equals('>') || this.equals('v') || this.equals('<') || this.equals('^')
}

fun Char.turnRight(): Char {
    return if (this.equals('>')) {
        'v'
    } else
    if (this.equals('<')) {
        '^'
    } else
    if (this.equals('v')) {
        '<'
    } else if (this.equals('^')) {
        '>'
    } else {
        throw Error("Not a Guard position")
    }
}

fun Char.toVector(): Pair<Int, Int> {
    return if (this.equals('>')) {
        Pair(0, 1)
    } else if (this.equals('<')) {
        Pair(0, -1)
    } else
    if (this.equals('v')) {
        Pair(1,0)
    } else    if (this.equals('^')) {
        Pair(-1,0)
    } else {
        throw Error("Not a Guard position")
    }
}

fun Array<Array<Char>>.getStartingPoint(): Triple<Int, Int, Char> {
    for(i in indices) {
        for (j in this[0].indices) {
            if (this[i][j].isGuard()) {
                return Triple(i,j, this[i][j])
            }
        }
    }
    throw Error("Guard not found")
}

fun Array<Array<Char>>.countXs(): Int {
    var counter = 0
    for(i in indices) {
        for (j in this[0].indices) {
            if (this[i][j] == 'X') {
                counter++
            }
        }
    }
    return counter
}



fun Array<Array<Char>>.move(currentPos: Triple<Int, Int, Char>): Triple<Int, Int, Char> {
    val nextPos = Pair(currentPos.first+currentPos.third.toVector().first, currentPos.second+currentPos.third.toVector().second)
    return if (!this.outOfBounds(nextPos) && this[nextPos.first][nextPos.second] == '#') {
        Triple(currentPos.first, currentPos.second, currentPos.third.turnRight())
    } else {
        this[currentPos.first][currentPos.second] = 'X'
        Triple(nextPos.first, nextPos.second, currentPos.third)
    }
}

fun Array<Array<Char>>.outOfBounds(guardPos: Pair<Int, Int>): Boolean {
    return !(guardPos.first in this.indices && guardPos.second in this[0].indices)
}

fun part1(map: Array<Array<Char>>): Int {
    map.populateXs()
    return map.countXs()
}

private fun Array<Array<Char>>.populateXs() {
    var currentPos = getStartingPoint()
    while (!outOfBounds(Pair(currentPos.first, currentPos.second))) {
        currentPos = this.move(currentPos)
    }
}

fun Array<Array<Char>>.getXPositions(): List<Pair<Int, Int>> {
    val result = LinkedList<Pair<Int,Int>>()
    for(i in indices) {
        for (j in this[0].indices) {
            if (this[i][j] == 'X') {
                result.add(Pair(i,j))
            }
        }
    }
    return result
}

fun Array<Array<Char>>.loops(startingPoint: Triple<Int,Int,Char>): Boolean {
    var currentPos = startingPoint
    val visited = LinkedList<Triple<Int,Int, Char>>()
    while (!outOfBounds(Pair(currentPos.first, currentPos.second))
        && !visited.contains(Triple(currentPos.first, currentPos.second, currentPos.third))) {
        val nextPos = this.move(currentPos)
        if (nextPos.first != currentPos.first || nextPos.second != currentPos.second) {
            visited.add(Triple(currentPos.first, currentPos.second, currentPos.third))
        }
        currentPos = nextPos
    }
    return !(outOfBounds(Pair(currentPos.first, currentPos.second)))
}

fun part2(map: Array<Array<Char>>): Int {
    val startingPoint = map.getStartingPoint()
    map.populateXs()
    map[startingPoint.first][startingPoint.second] = startingPoint.third
    val positions = map.getXPositions()
    var counter = 0
    for(position in positions) {
        map[position.first][position.second] = '#'
        if (map.loops(startingPoint)) {
            counter++
        }
        map[position.first][position.second] = '.'
    }
    return counter
}

fun List<String>.transform(): Array<Array<Char>> {
    val response = Array(this.size ) { Array(this[0].length) {'.'} }
    for(i in indices) {
        for(j in this[0].indices) {
            response[i][j] = this[i][j]
        }
    }
    return response
}
fun main() {
    var testInput = readInput("day6_test")
    var input = readInput("day6")

    measureTimeMillis {
        val result = part1(testInput.transform())
        println(result)
    }

    measureTimeMillis {
        val result = part1(input.transform())
        println(result)
    }

    measureTimeMillis {
        val result = part2(testInput.transform())
        println(result)
    }

    measureTimeMillis {
        val result = part2(input.transform())
        println(result)
    }

}
