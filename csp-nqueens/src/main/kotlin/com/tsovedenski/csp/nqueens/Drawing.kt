package com.tsovedenski.csp.nqueens

import com.tsovedenski.csp.*
import com.tsovedenski.csp.heuristics.ordering.comparators.BiggestDomainVariable
import com.tsovedenski.csp.heuristics.pruning.schemas.FullLookAhead
import com.tsovedenski.csp.reactor.Backtracking
import processing.core.PApplet
import processing.core.PImage
import reactor.core.publisher.Mono
import reactor.core.publisher.TopicProcessor
import reactor.core.scheduler.Schedulers
import java.time.Duration
import kotlin.concurrent.thread

data class Position(val x: Float, val y: Float)

class ProcessingQueens: PApplet() {
    var assignment: Assignment<Int, Int> = emptyAssignment()

    // hope no copyright laws were violated 0_0
    val queenPath = "https://www.clipartmax.com/png/full/48-485159_white-queen-finish-chess-piece-queen-symbol.png"
    val queen: PImage? = loadImage(queenPath, "png")

    companion object Factory {
        fun run() {
            val sketch = ProcessingQueens()
            sketch.setSize(1000, 1000)
            sketch.runSketch()
        }
    }

    override fun setup() {
        val problem = Queens(12)
        val processor = TopicProcessor.create<Assignment<Int, Int>>()
        val sink = processor.sink()

        processor.concatMap { it -> Mono.just(it).delayElement(Duration.ofMillis(500)) }
                .subscribeOn(Schedulers.parallel())
                .publishOn(Schedulers.parallel())
                .doOnNext{
                    assignment = it
                    printQueensPartial(it) }
                .doOnComplete{
                    processor.shutdown()
                }.subscribe{}

        thread {
            val solution = problem.solve(
                    strategy = Backtracking(
                            pruneSchema = FullLookAhead(),
                            sink = sink
                    )
            )
            solution.print()
            (solution as? Solved)?.let(::printQueens)
        }

    }

    override fun draw() {
        drawQueens(assignment)
    }


    fun drawQueens(assignment: Assignment<Int, Int>) {
        val smallestDimension = if(height <= width) height else width
        val size = assignment.size
        repeat(size) { i ->
            val variables = assignment.variablesOf(size - 1 - i)
            var queenAt = -1
            if (variables.size == 1) {
                queenAt = variables.first()
            }
            drawCellsRow(smallestDimension/size.toFloat(), size, i, queenAt)
        }
    }

    fun drawCellsRow(cellSize: Float, boardSize: Int, rowNum: Int, queenAt: Int) {
        repeat(boardSize) { i ->
            drawCell(Position(i*cellSize, rowNum*cellSize), cellSize, i == queenAt, (rowNum + i) % 2 == 0)
        }
    }

    fun drawCell(position: Position, size: Float, hasQueen: Boolean, isBlack: Boolean) {
        fill(if (isBlack) 20 else 210)
        rect(position.x, position.y, size, size)
        if (hasQueen) {
//            val img = loadImage("data/queen.png")
//            fill(if (isBlack) 255 else 0)
//            textSize(size / 1.5f)
//            textAlign(CENTER, CENTER)
//            text("Q", position.x + size / 2f, position.y + size / 2f)
           tint(if (isBlack) 255 else 40)
            image(queen, position.x, position.y, size, size)
        }
    }
}

fun main(args: Array<String>) {
    ProcessingQueens.run()
}
