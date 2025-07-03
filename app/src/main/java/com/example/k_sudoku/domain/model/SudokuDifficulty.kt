package com.example.k_sudoku.domain.model

enum class SudokuDifficulty(val hints: Int) {
    EASY(36),
    INTERMEDIATE(28),
    HARD(20)
}