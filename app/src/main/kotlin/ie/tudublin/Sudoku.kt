package ie.tudublin

class Sudoku(private val board: Array<IntArray>) {

    companion object {
        const val MAX_ITERATIONS = 2_000_000
    }

    private var iterations = 0

    fun getIterations(): Int = iterations

    /** Returns the solved board, or null if unsolvable / iteration limit exceeded. */
    fun solve(): Array<IntArray>? {
        iterations = 0
        val grid = Array(9) { row -> board[row].clone() }
        return if (solveHelper(grid)) grid else null
    }

    private fun solveHelper(grid: Array<IntArray>): Boolean {
        iterations++
        if (iterations > MAX_ITERATIONS) return false   // give up

        for (row in 0 until 9) {
            for (col in 0 until 9) {
                if (grid[row][col] == 0) {
                    for (num in 1..9) {
                        if (isValid(grid, row, col, num)) {
                            grid[row][col] = num
                            if (solveHelper(grid)) return true
                            grid[row][col] = 0          // backtrack
                        }
                    }
                    return false                        // no valid number here
                }
            }
        }
        return true                                     // board full & valid
    }

    private fun isValid(grid: Array<IntArray>, row: Int, col: Int, num: Int): Boolean {
        for (i in 0 until 9) {
            if (grid[row][i] == num) return false
            if (grid[i][col] == num) return false
        }
        val boxRow = row / 3 * 3
        val boxCol = col / 3 * 3
        for (r in boxRow until boxRow + 3)
            for (c in boxCol until boxCol + 3)
                if (grid[r][c] == num) return false
        return true
    }
}