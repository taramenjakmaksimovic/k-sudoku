package com.example.k_sudoku.data.datasource

import kotlin.random.Random


object SudokuGenerator {
    fun generateFullBoard(): Array<IntArray> {
        val board = Array(9) { IntArray(9) }
        solve(board, Random.Default)
        return board

    }


    private fun solve(board: Array<IntArray>, random: Random): Boolean {
        for (row in 0..8) {
            for (col in 0..8) {
                if (board[row][col] == 0) {
                    for (num in (1..9).shuffled(random)) {
                        if (isValid(board, row, col, num)) {
                            board[row][col] = num
                            if (solve(board,random))
                                return true
                            board[row][col] = 0
                        }
                    }
                    return false
                }
            }
        }
        return true
    }


    private fun isValid(board: Array<IntArray>, row: Int, col: Int, num: Int): Boolean {
        for (i in 0..8) {
            if (board[row][i] == num || board[i][col] == num)
                return false
        }
        val boxRow = row / 3 * 3
        val boxCol = col / 3 * 3
        for (i in 0..2)
            for (j in 0..2)
                if (board[boxRow + i][boxCol + j] == num)
                    return false
        return true
    }

    fun maskBoard(
        fullBoard: Array<IntArray>,
        hints: Int = 36
    ): Pair<List<MutableList<Int>>, List<MutableList<Boolean>>> {
        val cells = fullBoard.map { it.toMutableList() }.toMutableList()
        val initial = List(9) { MutableList(9) { true } }
        val allPositions = (0..8).flatMap { row -> (0..8).map { col -> row to col } }.shuffled()
        val toRemove = 81 - hints

        for ((row, col) in allPositions.take(toRemove)) {
            cells[row][col] = 0
            initial[row][col] = false
        }
        return Pair(cells, initial)
    }
}



