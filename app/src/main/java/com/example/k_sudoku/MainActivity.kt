package com.example.k_sudoku

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.k_sudoku.data.repository.SudokuRepositoryImpl
import com.example.k_sudoku.data.repository.SudokuSolverRepositoryImpl
import com.example.k_sudoku.data.solver.SudokuSolverImpl
import com.example.k_sudoku.domain.usecase.GenerateSudokuUseCase
import com.example.k_sudoku.domain.usecase.SolveSudokuUseCase
import com.example.k_sudoku.presentation.home.HomeScreen
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

            val viewModel = remember {
                SudokuViewModel(generateSudokuUseCase, solveUseCase)
            }

            var currentScreen by remember { mutableStateOf("home") }

            when (currentScreen) {
                "home" -> HomeScreen(
                    onNewGame = { difficulty ->
                        viewModel.resetGame(difficulty)
                        currentScreen = "game"
                    },
                    onExit = {
                        finish()
                    }
                )

                "game" -> SudokuScreen(
                    viewModel = viewModel,
                    onBackToHome = {
                        currentScreen = "home"
                    }
                )
            }
        }
    }
}

