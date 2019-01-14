package com.tsovedenski.csp.sudoku

import com.tsovedenski.csp.*
import com.tsovedenski.csp.heuristics.pruning.schemas.FullLookAhead
import com.tsovedenski.csp.reactor.Backtracking as ReactorBacktracking
import processing.core.PApplet
import reactor.core.publisher.Mono
import reactor.core.publisher.TopicProcessor
import reactor.core.scheduler.Schedulers
import java.lang.StringBuilder
import java.time.Duration
import kotlin.concurrent.thread


data class Position(val x: Float, val y: Float)

class ProcessingSudoku: PApplet() {
    var assignment: Assignment<String, Int> = emptyAssignment()

    companion object Factory {
        fun run() {
            val sketch = ProcessingSudoku()
            sketch.setSize(1000, 1000)
            sketch.runSketch()
        }
    }

    override fun setup() {

        val problem = Sudoku(sudoku1)
        val processor = TopicProcessor.create<Assignment<String, Int>>()
        val sink = processor.sink()

        processor.concatMap { it -> Mono.just(it).delayElement(Duration.ofMillis(200)) }
                .subscribeOn(Schedulers.parallel())
                .publishOn(Schedulers.parallel())
                .doOnNext {
                    assignment = it
                    printSudokuPartial(it)
                }
                .doOnComplete {
                    processor.shutdown()
                }.subscribe {}

        thread {
            val solution = problem.solve(
                    strategy = ReactorBacktracking(
                            pruneSchema = FullLookAhead(),
                            sink = sink
                    )
            )
            solution.print()
            printSudoku(problem.grid, solution)
        }
    }

    override fun draw() {
        drawSudoku(assignment)
    }

    fun drawSudoku(assignment: Assignment<String, Int>) {
        val size = sqrt(assignment.size.toFloat()).toInt()
        val smallestDimension = min(width, height)

        repeat(size) { i ->
            val rowFirstIndex = (size) * i
            val rowLastIndex = (size) * i + size
            val row = assignment
                    .toList()
                    .subList(rowFirstIndex, rowLastIndex)
                    .map { printCell(it.second) }

            drawCellsRow(row, i, smallestDimension/size.toFloat())
        }
    }

    fun printCell(domain: Domain<Int>): String {
        val list = domain.toList()
        return  if (list.toList().size == 1) list.first().toString() else ""
    }


    fun drawCellsRow(row: List<String>, rowNum: Int, cellSize: Float) {
        row.forEachIndexed{ i, num ->
            drawCell(Position(i*cellSize, rowNum*cellSize), cellSize, num)
        }
    }

    fun drawCell(position: Position, size: Float, number: String = "") {
        strokeWeight(4f)
        stroke(4)
        fill(255)
        rect(position.x, position.y, size, size)
        fill(20)
        textSize(size / 1.5f)
        textAlign(CENTER, CENTER)
        text(number, position.x + size / 2f, position.y + size / 2f)
    }
}

fun main(args: Array<String>) {
    ProcessingSudoku.run()
}
