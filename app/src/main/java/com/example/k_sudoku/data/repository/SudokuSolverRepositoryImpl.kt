package com.example.k_sudoku.data.repository


import com.example.k_sudoku.data.solver.SudokuSolverImpl
import com.example.k_sudoku.domain.model.SudokuBoard
import com.example.k_sudoku.domain.repository.SudokuSolverRepository

class SudokuSolverRepositoryImpl(
    private val solver : SudokuSolverImpl
) : SudokuSolverRepository {

    override fun solveBoard(board: SudokuBoard) : SudokuBoard?{
        val grid = board.cells.map { it.toIntArray() }.toTypedArray()
        val solved = solver.solveSudoku(grid)
        return if (solved){
            val solvedCells = grid.map { it.toMutableList() }
            SudokuBoard(
                cells = solvedCells,
                initial = board.initial
            )
        } else {
            null
        }
    }

}