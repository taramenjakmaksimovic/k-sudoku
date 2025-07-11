package com.example.k_sudoku

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.k_sudoku.data.repository.SudokuRepositoryImpl
import com.example.k_sudoku.data.repository.SudokuSolverRepositoryImpl
import com.example.k_sudoku.data.solver.SudokuSolverImpl
import com.example.k_sudoku.domain.usecase.GenerateSudokuUseCase
import com.example.k_sudoku.domain.usecase.SolveSudokuUseCase
import com.example.k_sudoku.presentation.screen.SudokuScreen
import com.example.k_sudoku.presentation.viewmodel.SudokuViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val sudokuRepository = SudokuRepositoryImpl()
            val generateSudokuUseCase = GenerateSudokuUseCase(sudokuRepository)

            val solver = SudokuSolverImpl()
            val solverRepository = SudokuSolverRepositoryImpl(solver)
            val solveUseCase = SolveSudokuUseCase(solverRepository)

            val viewModel = SudokuViewModel(generateSudokuUseCase, solveUseCase )
            SudokuScreen(viewModel)
        }
    }
}

