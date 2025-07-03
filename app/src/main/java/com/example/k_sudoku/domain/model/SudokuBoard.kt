package com.example.k_sudoku.domain.model

data class SudokuBoard(
    val cells: List<MutableList<Int>>,
    val initial: List<List<Boolean>>
)
