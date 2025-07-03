package com.example.k_sudoku.domain.repository

import com.example.k_sudoku.domain.model.SudokuBoard

interface SudokuRepository {
    fun generateEasyBoard() : SudokuBoard
}