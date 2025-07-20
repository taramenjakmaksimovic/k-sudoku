package com.example.k_sudoku.data.repository

import com.example.k_sudoku.data.datasource.SudokuGenerator
import com.example.k_sudoku.domain.model.SudokuBoard
import com.example.k_sudoku.domain.model.SudokuDifficulty
import com.example.k_sudoku.domain.repository.SudokuRepository

class SudokuRepositoryImpl : SudokuRepository {

    override fun generateEasyBoard(): SudokuBoard {
        val fullBoard = SudokuGenerator.generateFullBoard()
        val (cells,initial) = SudokuGenerator.maskBoard(fullBoard,hints=SudokuDifficulty.EASY.hints)
        return SudokuBoard(cells,initial,  solution = fullBoard.map { it.toList() })
    }

    override fun generateIntermediateBoard(): SudokuBoard {
        val fullBoard = SudokuGenerator.generateFullBoard()
        val (cells,initial) = SudokuGenerator.maskBoard(fullBoard,hints=SudokuDifficulty.INTERMEDIATE.hints)
        return SudokuBoard(cells,initial, solution = fullBoard.map { it.toList() })
    }

    override fun generateHardBoard(): SudokuBoard {
        val fullBoard = SudokuGenerator.generateFullBoard()
        val (cells,initial) = SudokuGenerator.maskBoard(fullBoard,hints=SudokuDifficulty.HARD.hints)
        return SudokuBoard(cells,initial,  solution = fullBoard.map { it.toList() })
    }
}