package com.example.k_sudoku.data.solver


class SudokuSolverImpl {
    fun solveSudoku(grid: Array<IntArray>): Boolean {
        for (row in 0..8) {
            for (col in 0..8) {
                if (grid[row][col] == 0) {
                    for (num in (1..9)) {
                        if (isValidSolver(grid, row, col, num)) {
                            grid[row][col] = num
                            if (solveSudoku(grid))
                                return true
                            grid[row][col] = 0
                        }
                    }
                    return false
                }
            }
        }
        return true
    }


    private fun isValidSolver(grid: Array<IntArray>, row: Int, col: Int, num: Int): Boolean {
        for (i in 0..8) {
            if (grid[row][i] == num || grid[i][col] == num)
                return false
        }
        val boxRow = row / 3 * 3
        val boxCol = col / 3 * 3
        for (i in 0..2)
            for (j in 0..2)
                if (grid[boxRow + i][boxCol + j] == num)
                    return false
        return true
    }
}