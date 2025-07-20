package com.example.k_sudoku.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.k_sudoku.domain.model.SudokuBoard
import com.example.k_sudoku.domain.model.SudokuDifficulty
import com.example.k_sudoku.domain.usecase.GenerateSudokuUseCase
import com.example.k_sudoku.domain.usecase.SolveSudokuUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SudokuViewModel(
    private val generateUseCase: GenerateSudokuUseCase,
    private val solveUseCase: SolveSudokuUseCase
) : ViewModel() {
    private var currentDifficulty: SudokuDifficulty = SudokuDifficulty.EASY
    private val _remainingHints = MutableStateFlow(0)
    val remainingHints: StateFlow<Int> = _remainingHints


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
            _remainingHints.value = currentDifficulty.maxHintsAllowed
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
            _remainingHints.value = currentDifficulty.maxHintsAllowed
        }
    }

  /*  fun setDifficulty(difficulty: SudokuDifficulty) {
        resetGame(difficulty)
    } */

    fun isSolved() : Boolean {
        val board=_boardState.value.cells
        for (i in 0..8){
            val row = mutableSetOf<Int>()
            val col = mutableSetOf<Int>()
            for(j in 0..8){
                val rolVal = board[i][j]
                val colVal = board[j][i]
                if(rolVal !in 1..9 || rolVal in row)
                    return false
                if(colVal !in 1..9 || colVal in col)
                    return false
                row.add(rolVal)
                col.add(colVal)
            }
        }
        for(blockRow in 0..2) {
            for (blockCol in 0..2) {
                val block = mutableSetOf<Int>()
                for(i in 0..2){
                    for (j in 0..2){
                        val value = board[blockRow * 3 + i][blockCol * 3 + j]
                        if (value !in 1..9 || value in block)
                            return false
                        block.add(value)
                    }
                }
            }
        }
        return true
    }

    fun solveBoard(){
        viewModelScope.launch {
            val currentBoard = _boardState.value
            val solved = solveUseCase(currentBoard)
            if(solved != null){
                _boardState.value = solved
            }
            // TODO else
        }
    }

    fun provideHint() {
        if (_remainingHints.value <= 0) return

        val board = _boardState.value
        val newCells = board.cells.map { it.toMutableList() }
        val solution = board.solution

        if (solution.isEmpty() || solution[0].isEmpty()) return

        val emptyCells = mutableListOf<Pair<Int, Int>>()
        for (row in 0..8) {
            for (col in 0..8) {
                if (newCells[row][col] == 0) {
                    emptyCells.add(row to col)
                }
            }
        }

        if (emptyCells.isEmpty()) return

        val (hintRow, hintCol) = emptyCells.random()
        newCells[hintRow][hintCol] = solution[hintRow][hintCol]

        _boardState.value = board.copy(cells = newCells)
        _remainingHints.value -= 1
    }
}