package ie.tudublin

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class SudokuTest {

    // Classic Wikipedia puzzle
    private val puzzle1 = arrayOf(
        intArrayOf(5,3,0,0,7,0,0,0,0),
        intArrayOf(6,0,0,1,9,5,0,0,0),
        intArrayOf(0,9,8,0,0,0,0,6,0),
        intArrayOf(8,0,0,0,6,0,0,0,3),
        intArrayOf(4,0,0,8,0,3,0,0,1),
        intArrayOf(7,0,0,0,2,0,0,0,6),
        intArrayOf(0,6,0,0,0,0,2,8,0),
        intArrayOf(0,0,0,4,1,9,0,0,5),
        intArrayOf(0,0,0,0,8,0,0,7,9)
    )

    // Second puzzle (different solution)
    private val puzzle2 = arrayOf(
    intArrayOf(0,0,0,2,6,0,7,0,1),
    intArrayOf(6,8,0,0,7,0,0,9,0),
    intArrayOf(1,9,0,0,0,4,5,0,0),
    intArrayOf(8,2,0,1,0,0,0,4,0),
    intArrayOf(0,0,4,6,0,2,9,0,0),
    intArrayOf(0,5,0,0,0,3,0,2,8),
    intArrayOf(0,0,9,3,0,0,0,7,4),
    intArrayOf(0,4,0,0,5,0,0,3,6),
    intArrayOf(7,0,3,0,1,8,0,0,0)
    )

    // Fast-failing unsolvable board: row 0 needs 9 at col 8,
    // but col 8 already contains a 9 in row 1 -> no candidate.
    private val invalidBoard = arrayOf(
        intArrayOf(1,2,3,4,5,6,7,8,0),
        intArrayOf(0,0,0,0,0,0,0,0,9),
        intArrayOf(0,0,0,0,0,0,0,0,0),
        intArrayOf(0,0,0,0,0,0,0,0,0),
        intArrayOf(0,0,0,0,0,0,0,0,0),
        intArrayOf(0,0,0,0,0,0,0,0,0),
        intArrayOf(0,0,0,0,0,0,0,0,0),
        intArrayOf(0,0,0,0,0,0,0,0,0),
        intArrayOf(0,0,0,0,0,0,0,0,0)
    )

    @Test
    fun testSolvePuzzle1() {
        val solution = Sudoku(puzzle1).solve()
        assertNotNull(solution)
        assertTrue(isValidComplete(solution!!))
        assertTrue(preservesClues(puzzle1, solution))
    }

    @Test
    fun testSolvePuzzle2() {
        val solution = Sudoku(puzzle2).solve()
        assertNotNull(solution)
        assertTrue(isValidComplete(solution!!))
        assertTrue(preservesClues(puzzle2, solution))
    }

    @Test
    fun testUnsolvableBoard() {
        val solution = Sudoku(invalidBoard).solve()
        assertNull(solution, "Unsolvable board should return null")
    }

    // --- helpers ---

    private fun isValidComplete(board: Array<IntArray>): Boolean {
        val expected = (1..9).toSet()
        for (i in 0 until 9) {
            if ((0 until 9).map { board[i][it] }.toSet() != expected) return false
            if ((0 until 9).map { board[it][i] }.toSet() != expected) return false
        }
        for (br in 0 until 3) for (bc in 0 until 3) {
            val box = mutableSetOf<Int>()
            for (r in 0 until 3) for (c in 0 until 3)
                box.add(board[br * 3 + r][bc * 3 + c])
            if (box != expected) return false
        }
        return true
    }

    private fun preservesClues(original: Array<IntArray>, solution: Array<IntArray>): Boolean {
        for (i in 0 until 9) for (j in 0 until 9)
            if (original[i][j] != 0 && original[i][j] != solution[i][j]) return false
        return true
    }
}