package com.example.k_sudoku.domain.repository

import com.example.k_sudoku.domain.model.SudokuBoard

interface SudokuSolverRepository {
    fun solveBoard(board: SudokuBoard) : SudokuBoard?
}