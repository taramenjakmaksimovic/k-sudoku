package com.example.k_sudoku.domain.model

enum class SudokuDifficulty(val hints: Int, val maxHintsAllowed: Int) {
    EASY(36, 1),
    INTERMEDIATE(28, 3),
    HARD(20, 5)
}