package com.example.k_sudoku.presentation.viewmodel

import android.telecom.StatusHints
import androidx.lifecycle.ViewModel
import com.example.k_sudoku.data.repository.SudokuRepositoryImpl
import com.example.k_sudoku.domain.model.SudokuBoard
import com.example.k_sudoku.domain.usecase.GenerateSudokuUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SudokuViewModel : ViewModel() {
    val repo = SudokuRepositoryImpl()
    val generateUseCase = GenerateSudokuUseCase(repo)

    private val _boardState = MutableStateFlow(generateUseCase())
    val boardState : StateFlow<SudokuBoard> = _boardState

    fun onCellChanged(row: Int, col: Int, value: Int){
        val board = _boardState.value
        if(!board.initial[row][col]){
            board.cells[row][col] = value
            _boardState.value = board.copy()
        }
    }

    fun resetGame(hints: Int=36){
        _boardState.value=generateUseCase()
    }
}