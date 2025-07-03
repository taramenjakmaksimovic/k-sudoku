package com.example.k_sudoku.domain.usecase

import com.example.k_sudoku.domain.model.SudokuBoard
import com.example.k_sudoku.domain.model.SudokuDifficulty
import com.example.k_sudoku.domain.repository.SudokuRepository

class GenerateSudokuUseCase(private val repo: SudokuRepository) {
    operator fun invoke(difficulty: SudokuDifficulty): SudokuBoard{
        return when(difficulty){
            SudokuDifficulty.EASY -> repo.generateEasyBoard()
            SudokuDifficulty.INTERMEDIATE -> repo.generateIntermediateBoard()
            SudokuDifficulty.HARD -> repo.generateHardBoard()
        }
    }
}