package com.example.k_sudoku.domain.usecase

import com.example.k_sudoku.domain.model.SudokuBoard
import com.example.k_sudoku.domain.repository.SudokuSolverRepository

class SolveSudokuUseCase (
    private val repository: SudokuSolverRepository
    ) {
    operator fun invoke(board: SudokuBoard) : SudokuBoard?{
        return repository.solveBoard(board)
    }
}