package utils.day8

import utils.readInput
import java.util.Objects
import kotlin.system.measureTimeMillis

data class Coordinate(val i: Int, val j: Int)

data class Position(val i: Int, val j: Int, val frequency: Char)

fun part1(map: Array<CharArray>): Int {
    val positions = HashSet<Position>()
    map.forEachIndexed { i, row ->
        row.forEachIndexed { j, elem ->
            if (map[i][j] != '.') {
                positions.add(Position(i,j,map[i][j]))
            }
        }
    }
    return calculateAntinodes(positions, map.size, map[0].size)
}

fun part2(map: Array<CharArray>): Int {
    val positions = HashSet<Position>()
    map.forEachIndexed { i, row ->
        row.forEachIndexed { j, elem ->
            if (map[i][j] != '.') {
                positions.add(Position(i,j,map[i][j]))
            }
        }
    }
    return calculateAntinodes2(positions, map.size, map[0].size)
}

fun calculateAntinodes(
    positions: HashSet<Position>,
    sizeX: Int,
    sizeY: Int
): Int {
    val found = HashSet<Coordinate>()
    for (position in positions) {
       for (otherPosition in positions) {
           if (!position.equals(otherPosition) && position.frequency == otherPosition.frequency) {
               val pos1 = Coordinate((2*position.i - otherPosition.i), (2*position.j - otherPosition.j))
               val pos2 = Coordinate((2*otherPosition.i - position.i), (2*otherPosition.j - position.j))
               if (insideBorders(pos1, sizeX, sizeY)) {
                   found.add(pos1)
               }
               if (insideBorders(pos2, sizeX, sizeY)) {
                   found.add(pos2)
               }
           }
       }
    }
    return found.size
}

fun calculateAntinodes2(
    positions: HashSet<Position>,
    sizeX: Int,
    sizeY: Int
): Int {
    val found = HashSet<Coordinate>()
    for (position in positions) {
        for (otherPosition in positions) {
            if (!position.equals(otherPosition) && position.frequency == otherPosition.frequency) {
                var mul = 0;
                while(true) {
                    var foundAny = false
                    val pos1 = Coordinate(((mul+1)*position.i - mul*otherPosition.i),
                        ((mul+1)*position.j - mul*otherPosition.j))
                    val pos2 = Coordinate(((mul+1)*otherPosition.i - mul*position.i),
                        ((mul+1)*otherPosition.j - mul*position.j))
                    if (insideBorders(pos1, sizeX, sizeY)) {
                        foundAny=true
                        found.add(pos1)
                    }
                    if (insideBorders(pos2, sizeX, sizeY)) {
                        foundAny=true
                        found.add(pos2)
                    }
                    if (!foundAny) {
                        break
                    }
                    mul++
                }
            }
        }
    }
    return found.size
}


private fun insideBorders(pos: Coordinate, sizeX: Int, sizeY: Int) =
    pos.i >= 0 && pos.i < sizeX && pos.j >= 0 && pos.j < sizeY

fun main() {
    var testInput = readInput("day8_test").map { it.toCharArray() }.toTypedArray<CharArray>()
    var testInput2 = readInput("day8_test2").map { it.toCharArray() }.toTypedArray<CharArray>()
    var input = readInput("day8").map { it.toCharArray() }.toTypedArray<CharArray>()
    measureTimeMillis {
        val result = part1(testInput)
        println(result) // should be 14
    }
    measureTimeMillis {
        val result = part1(input)
        println(result)
    }

    measureTimeMillis {
        val result = part2(testInput)
        println(result) // should be 34
    }
    measureTimeMillis {
        val result = part2(input)
        println(result)
    }

}
