package com.example.k_sudoku.domain.usecase

import com.example.k_sudoku.domain.model.SudokuBoard
import com.example.k_sudoku.domain.repository.SudokuRepository

class GenerateSudokuUseCase(private val repo: SudokuRepository) {
    operator fun invoke(): SudokuBoard = repo.generateEasyBoard()
}