package com.example.k_sudoku.domain.model

data class SudokuBoard(
    val cells: List<MutableList<Int>>,
    val initial: List<MutableList<Boolean>>,
    val solution: List<List<Int>> = List(9) { List(9) {0} }
)
