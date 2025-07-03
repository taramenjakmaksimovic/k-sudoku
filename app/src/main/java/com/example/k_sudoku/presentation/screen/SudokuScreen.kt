package com.example.k_sudoku.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.k_sudoku.presentation.viewmodel.SudokuViewModel



@Composable
fun SudokuScreen(viewModel: SudokuViewModel) {
    val board by viewModel.boardState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(32.dp))

        for (row in 0..8) {
            Row {
                for (col in 0..8) {
                    val value = board.cells[row][col]
                    val isInitial = board.initial[row][col]

                    var text by remember {
                        mutableStateOf(
                            if (value != 0) value.toString() else ""
                        )
                    }

                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .drawBehind {
                                val strokeWidthThin = 1.dp.toPx()
                                val strokeWidthThick = 3.dp.toPx()
                                val colorThin = Color.Gray
                                val colorThick = Color.Black


                                val strokeWidthStart =
                                    if (col % 3 == 0) strokeWidthThick else strokeWidthThin
                                drawLine(
                                    color = if (col % 3 == 0) colorThick else colorThin,
                                    start = Offset(0f, 0f),
                                    end = Offset(0f, size.height),
                                    strokeWidth = strokeWidthStart,
                                    cap = StrokeCap.Square
                                )


                                val strokeWidthTop =
                                    if (row % 3 == 0) strokeWidthThick else strokeWidthThin
                                drawLine(
                                    color = if (row % 3 == 0) colorThick else colorThin,
                                    start = Offset(0f, 0f),
                                    end = Offset(size.width, 0f),
                                    strokeWidth = strokeWidthTop,
                                    cap = StrokeCap.Square
                                )


                                if (col == 8) {
                                    drawLine(
                                        color = colorThick,
                                        start = Offset(size.width, 0f),
                                        end = Offset(size.width, size.height),
                                        strokeWidth = strokeWidthThick,
                                        cap = StrokeCap.Square
                                    )
                                }


                                if (row == 8) {
                                    drawLine(
                                        color = colorThick,
                                        start = Offset(0f, size.height),
                                        end = Offset(size.width, size.height),
                                        strokeWidth = strokeWidthThick,
                                        cap = StrokeCap.Square
                                    )
                                }
                            }
                    ) {
                        BasicTextField(
                            value = text,
                            onValueChange = { newText ->
                                if (newText.isEmpty()) {
                                    text = ""
                                    viewModel.onCellChanged(row, col, 0)
                                } else if (newText.length == 1 && newText[0] in '1'..'9') {
                                    text = newText
                                    viewModel.onCellChanged(row, col, newText.toInt())
                                }
                            },
                            readOnly = isInitial,
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Transparent),
                            textStyle = TextStyle(
                                color = if (isInitial) Color.Black else Color.Blue,
                                textAlign = TextAlign.Center,
                                fontWeight = if (isInitial) FontWeight.Bold else FontWeight.Normal,
                                fontSize = 20.sp,
                                lineHeight = 20.sp
                            ),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Done
                            ),
                            singleLine = true,
                            decorationBox = { innerTextField ->
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    innerTextField()
                                }
                            }
                        )
                    }
                }
            }
        }
        Spacer(Modifier.height(32.dp))
        Button(
            onClick = {
                viewModel.resetGame()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Reset game")
        }
    }
}
