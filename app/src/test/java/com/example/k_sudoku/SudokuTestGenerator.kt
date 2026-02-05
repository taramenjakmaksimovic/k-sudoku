package com.example.k_sudoku
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import com.example.k_sudoku.data.datasource.SudokuGenerator


class SudokuTestGenerator : StringSpec ({
    "Full table should have 9 rows and 9 columns" {
        val board = SudokuGenerator.generateFullBoard()
        board.size shouldBe 9
        board.all { it.size==9 } shouldBe true
    }

    "Every row in full table should have unique numbers from 1 to 9" {
        val board = SudokuGenerator.generateFullBoard()
        board.forEach { row ->
            row.toSet().size shouldBe 9
            row.all { it in 1..9 } shouldBe true
        }
    }

    "Every column in full table should have unique numbers from 1 to 9" {
        val board = SudokuGenerator.generateFullBoard()
        for(col in 0..8) {
            val columnValues = (0..8).map { board[it][col] }
            columnValues.distinct().size shouldBe 9
        }
    }

    })