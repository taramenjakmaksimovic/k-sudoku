package com.example.k_sudoku.presentation.viewmodel

import android.telecom.StatusHints
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.k_sudoku.data.repository.SudokuRepositoryImpl
import com.example.k_sudoku.domain.model.SudokuBoard
import com.example.k_sudoku.domain.model.SudokuDifficulty
import com.example.k_sudoku.domain.usecase.GenerateSudokuUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SudokuViewModel(
    private val generateUseCase: GenerateSudokuUseCase
) : ViewModel() {
    private var currentDifficulty: SudokuDifficulty = SudokuDifficulty.EASY


    private val _boardState = MutableStateFlow(
        SudokuBoard(
            cells = List(9) { MutableList(9) { 0 } },
            initial = List(9) { MutableList(9) { false } }
        )
    )
    val boardState : StateFlow<SudokuBoard> = _boardState

    init {
        viewModelScope.launch {
            _boardState.value = generateUseCase(currentDifficulty)
        }
    }

    fun onCellChanged(row: Int, col: Int, value: Int){
        val board = _boardState.value
        if(!board.initial[row][col]){
            board.cells[row][col] = value
            _boardState.value = board.copy()
        }
    }

    fun resetGame(difficulty: SudokuDifficulty=currentDifficulty){
        currentDifficulty=difficulty
        viewModelScope.launch {
            _boardState.value = generateUseCase(currentDifficulty)
        }
    }

    fun setDifficulty(difficulty: SudokuDifficulty) {
        resetGame(difficulty)
    }
}