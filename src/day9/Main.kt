package utils.day9

import utils.readInput
import kotlin.arrayOfNulls
import kotlin.system.measureTimeMillis

fun digest(array: Array<Int?>): Long {
    return array.foldIndexed(0L) { idx, acc, elem ->
        if (elem == null) {
            acc
        } else {
            acc + (idx * elem)
        }

    }
}

fun digestPositions(positions: ArrayList<Position>): Long {
    var pos = 0
    var total = 0L
    for(position in positions) {
        if (position.id != null) {
            for(i in 0..(position.size-1)) {
                total+=((pos+i)*position.id)
            }
        }
        pos+=position.size
    }
    return total
}

private fun populateArray(files: String): Array<Int?> {
    val requiredLength = files.toCharArray().fold(0) { acc, elem -> acc + elem.digitToInt() }
    val arrayOfInts = arrayOfNulls<Int>(requiredLength)
    var idx = 0
    var currentFile = 0
    var isFile = true
    files.toCharArray().forEach {
        if (isFile) {
            for (i in 0..<it.digitToInt()) {
                arrayOfInts[idx++] = currentFile
            }
            isFile = false
        } else {
            currentFile++
            idx += it.digitToInt()
            isFile = true
        }
    }
    return arrayOfInts
}

fun part1(files: String): Long {
    val arrayOfInts = populateArray(files)
    var i = 0
    var j = arrayOfInts.size - 1
    while(i<j) {
        while(i < arrayOfInts.size && arrayOfInts[i]!= null) {
            i+=1
        }
        while(j>=0 && arrayOfInts[j]==null) {
            j-=1
        }
        if (i<j) {
            arrayOfInts[i] = arrayOfInts[j]
            arrayOfInts[j] = null
        }
    }
    return digest(arrayOfInts)
}

data class Position(val id: Int?, val size: Int)

fun part2(files: String): Long {
    val positions = ArrayList<Position>()
    var currentId = 0
    var isFile = true
    files.toCharArray().forEach {
        if (isFile) {
            val position = Position(currentId++, it.digitToInt())
            positions.add(position)
            isFile = false
        } else {
            positions.add(Position(null, it.digitToInt()))
            isFile = true
        }
    }

    while(--currentId >=0) {
        val currentElement = positions.find {it.id == currentId} as Position
        val currentElementIdx = positions.indexOf(currentElement)
        val fittingGapIdx = findGap(positions, currentElement)
        if (fittingGapIdx != null) {
            val fittingGap = positions[fittingGapIdx]
            positions[currentElementIdx] = Position(null, currentElement.size)
            positions[fittingGapIdx] = currentElement
            val pendingSize = fittingGap.size - currentElement.size
            if (pendingSize > 0) {
                positions.add(fittingGapIdx + 1, Position(null, pendingSize))
            }
            mergeGaps(positions)
        }
    }
    return digestPositions(positions)
}

fun mergeGaps(positions: ArrayList<Position>) {
    var i = 0
    while(i < positions.size - 1) {
        if (positions[i].id == null && positions[i+1].id == null) {
            positions[i] = Position(null, positions[i].size+positions[i+1].size)
            positions.removeAt(i+1)
        } else {
            i++
        }
    }
}

fun findGap(positions: ArrayList<Position>, movableFile: Position): Int? {
    for (i in positions.indices) {
        val candidate = positions[i]
        if (candidate.equals(movableFile)) {
            break
        }
        if (candidate.id == null && candidate.size >= movableFile.size) {
            return i
        }
    }
    return null
}

fun main() {
    var testInput = readInput("day9_test")[0]
    measureTimeMillis {
        val result = part1(testInput)
        println(result)
    }
    measureTimeMillis {
        val result = part2(testInput)
        println(result)
    }
    var input = readInput("day9")[0]
    measureTimeMillis {
        val result = part1(input)
        println(result)
    }
    measureTimeMillis {
        val result = part2(input)
        println(result)
    }
}
