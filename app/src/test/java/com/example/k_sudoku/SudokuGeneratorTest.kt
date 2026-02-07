package com.example.k_sudoku
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import com.example.k_sudoku.data.datasource.SudokuGenerator
import io.kotest.matchers.shouldNotBe


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

    "Every 3x3 block should have unique numbers from 1 to 9" {
        val board = SudokuGenerator.generateFullBoard()
        for(blockRow in 0..2){
            for(blockCol in 0..2){
                val blockValues = mutableListOf<Int>()
                for(row in 0..2){
                    for(col in 0..2){
                        blockValues+=board[blockRow*3 + row][blockCol*3 + col]
                    }
                }
                blockValues.toSet().size shouldBe 9
                blockValues.all { it in 1..9 } shouldBe true
            }
        }
    }

    "Generated boards should differ" {
        val board1 = SudokuGenerator.generateFullBoard()
        val board2 = SudokuGenerator.generateFullBoard()
        board1 shouldNotBe board2
    }

    "Full board should not contain zeros" {
        val board = SudokuGenerator.generateFullBoard()
        board.all { row -> row.all { it != 0 }} shouldBe true
    }

    "All numbers should be between 1 and 9" {
        val board = SudokuGenerator.generateFullBoard()
        board.all { row -> row.all { it in 1..9 }} shouldBe true
    }



    })