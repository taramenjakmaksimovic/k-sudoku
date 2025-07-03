package com.example.k_sudoku

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.k_sudoku.data.repository.SudokuRepositoryImpl
import com.example.k_sudoku.domain.usecase.GenerateSudokuUseCase
import com.example.k_sudoku.presentation.screen.SudokuScreen
import com.example.k_sudoku.presentation.viewmodel.SudokuViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val sudokuRepository = SudokuRepositoryImpl()
            val generateSudokuUseCase = GenerateSudokuUseCase(sudokuRepository)
            val viewModel = SudokuViewModel(generateSudokuUseCase)
            SudokuScreen(viewModel)
        }
    }
}

