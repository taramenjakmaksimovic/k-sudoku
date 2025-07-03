package com.example.k_sudoku.data.repository

import com.example.k_sudoku.data.datasource.SudokuGenerator
import com.example.k_sudoku.domain.model.SudokuBoard
import com.example.k_sudoku.domain.repository.SudokuRepository

class SudokuRepositoryImpl : SudokuRepository {
    override fun generateEasyBoard(): SudokuBoard {
        val fullBoard = SudokuGenerator.generateFullBoard()
        val (cells,initial) = SudokuGenerator.maskBoard(fullBoard,hints=36)
        return SudokuBoard(cells,initial)
    }
}