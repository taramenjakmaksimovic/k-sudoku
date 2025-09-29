package com.example.k_sudoku.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.k_sudoku.domain.model.SudokuDifficulty
import com.example.k_sudoku.ui.theme.myPurple


@Composable
fun HomeScreen(
    onNewGame: (SudokuDifficulty) -> Unit,
    onExit: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Sudoku",
            fontSize = 64.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            "Select difficulty",
            fontSize = 48.sp
            )

        Spacer(modifier = Modifier.height(24.dp))

        SudokuDifficulty.entries.forEach { difficulty ->
            Button(
                onClick = { onNewGame(difficulty) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = myPurple,
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .height(64.dp)
                    .padding(vertical = 4.dp)
            ) {
                Text(
                    when (difficulty) {
                        SudokuDifficulty.EASY -> "Easy"
                        SudokuDifficulty.INTERMEDIATE -> "Intermediate"
                        SudokuDifficulty.HARD -> "Hard"
                    },
                    fontSize = 32.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onExit,
            colors = ButtonDefaults.buttonColors(
                containerColor = myPurple,
                contentColor = Color.White
            ),
            modifier = Modifier
                .height(64.dp)
                .padding(vertical = 4.dp)
        ) {
            Text(
                "Exit",
                fontSize = 32.sp
                )
        }
    }
}