package ie.tudublin

import java.io.File
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    if (args.isEmpty()) {
        println("Usage: sudoku <input-file>")
        exitProcess(1)
    }

    val file = File(args[0])
    if (!file.exists()) {
        println("Error: file not found: ${args[0]}")
        exitProcess(1)
    }

    val board = parseBoard(file)
    if (board == null) {
        println("Error: invalid board format. Expected 9 lines of 9 digits (0 = empty).")
        exitProcess(1)
    }

    println("Input board:")
    printBoard(board)
    println()

    val sudoku = Sudoku(board)
    val solution = sudoku.solve()

    if (solution == null) {
        println("Could not find a solution")
        exitProcess(1)
    }

    println("Solution:")
    printBoard(solution)
    println("\nSolved in ${sudoku.getIterations()} iterations.")
}

private fun parseBoard(file: File): Array<IntArray>? {
    val lines = file.readLines().map { it.trim() }.filter { it.isNotEmpty() }
    if (lines.size != 9) return null
    val board = Array(9) { IntArray(9) }
    for (i in 0 until 9) {
        val digits = lines[i].replace(" ", "")
        if (digits.length != 9) return null
        for (j in 0 until 9) {
            val ch = digits[j]
            if (!ch.isDigit()) return null
            board[i][j] = ch - '0'
        }
    }
    return board
}

private fun printBoard(board: Array<IntArray>) {
    val line = "+-------+-------+-------+"
    println(line)
    for ((i, row) in board.withIndex()) {
        if (i > 0 && i % 3 == 0) println(line)
        val sb = StringBuilder()
        for ((j, cell) in row.withIndex()) {
            if (j % 3 == 0) sb.append("| ")
            sb.append(if (cell == 0) "." else cell.toString()).append(" ")
        }
        sb.append("|")
        println(sb)
    }
    println(line)
}