package com.example.k_sudoku

import com.example.k_sudoku.data.solver.isValidBoard
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue

class SudokuValidatorTest : StringSpec({
    "Empty board should be valid"{
        val grid = Array(9) { IntArray(9) {0} }
        isValidBoard(grid).shouldBeTrue()
    }

    "Solver should detect duplicates in a row" {
        val grid = Array(9) {IntArray(9) {0} }

        grid[0][0]=1
        grid[0][1]=1
        isValidBoard(grid).shouldBeFalse()
    }

    "Solver should detect duplicates in a column" {
        val grid = Array(9) {IntArray(9) {0} }

        grid[0][0]=2
        grid[1][0]=2
       isValidBoard(grid).shouldBeFalse()
    }

    "Solver should detect duplicates in a 3x3 block" {
        val grid = Array(9) {IntArray(9) {0} }

        grid[0][0]=3
        grid[1][1]=3
        isValidBoard(grid).shouldBeFalse()
    }

   "Partially filled board should be valid"{
       val grid = arrayOf(
           intArrayOf(5,3,0,0,7,0,0,0,0),
           intArrayOf(6,0,0,1,9,5,0,0,0),
           intArrayOf(0,9,8,0,0,0,0,6,0),
           intArrayOf(8,0,0,0,6,0,0,0,3),
           intArrayOf(4,0,0,8,0,3,0,0,1),
           intArrayOf(7,0,0,0,2,0,0,0,6),
           intArrayOf(0,6,0,0,0,0,2,8,0),
           intArrayOf(0,0,0,4,1,9,0,0,5),
           intArrayOf(0,0,0,0,8,0,0,7,9)
       )
       isValidBoard(grid).shouldBeTrue()
   }


})