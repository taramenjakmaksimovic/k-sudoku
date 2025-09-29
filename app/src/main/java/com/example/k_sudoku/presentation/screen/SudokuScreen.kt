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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.k_sudoku.ui.theme.myGreen
import com.example.k_sudoku.ui.theme.myPurple
import kotlinx.coroutines.delay
import java.util.Locale


@Composable
fun SudokuScreen(
    viewModel: SudokuViewModel,
    onBackToHome: () -> Unit,
    onExit: () -> Unit
    ) {
    val board by viewModel.boardState.collectAsState()
    var solvedMessage by remember { mutableStateOf<String?>(null) }
    val scrollState = rememberScrollState()
    var menuExpanded by remember { mutableStateOf(false) }
    val remainingHints by viewModel.remainingHints.collectAsState()
    val elapsedTime by viewModel.elapsedTime.collectAsState()
    val isPaused by viewModel.isPaused.collectAsState()
    val isGeneratedByComputer by viewModel.isGeneratedByComputer.collectAsState()

    val hours = elapsedTime / 3600
    val minutes = (elapsedTime % 3600) / 60
    val seconds = elapsedTime % 60


    LaunchedEffect(solvedMessage) {
        if (solvedMessage!=null){
            delay(3000L)
            solvedMessage=null
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(top = 25.dp, start = 8.dp, end = 8.dp, bottom = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            String.format
                (Locale.getDefault(), "%02d:%02d:%02d",
                hours, minutes, seconds),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding( 4.dp)

        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(Alignment.TopEnd)

        ){
            IconButton(onClick = {menuExpanded=true}) {
                Icon(Icons.Default.MoreVert, contentDescription = "")
            }

            DropdownMenu(
                expanded = menuExpanded,
                onDismissRequest = { menuExpanded = false}
            ) {
                DropdownMenuItem(
                    text = { Text("Generate solution") },
                    onClick = {
                        menuExpanded = false
                        viewModel.solveBoard()
                        viewModel.resetTimer()
                    }
                )
                DropdownMenuItem(
                    text = { Text("Reset game") },
                    onClick = {
                        menuExpanded = false
                        viewModel.resetGame()
                        solvedMessage = null
                    }
                )
                DropdownMenuItem(
                    text = { Text("Go back to Homepage") },
                    onClick = {
                        menuExpanded = false
                        onBackToHome()
                    }
                )
                DropdownMenuItem(
                    text = { Text("Exit") },
                    onClick = {
                        menuExpanded = false
                        onExit()
                    }
                )

            }
        }

        Spacer(Modifier.height(32.dp))

        for (row in 0..8) {
            Row {
                for (col in 0..8) {
                    val value = board.cells[row][col]
                    val isInitial = board.initial[row][col]

                    var text by remember(value) {
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
                            readOnly = isInitial || isGeneratedByComputer,
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Transparent),
                            textStyle = TextStyle(
                                color = if (isInitial) Color.Black else myPurple,
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

        Spacer(Modifier.height(16.dp))

        Box (modifier = Modifier.height(24.dp)) {
            solvedMessage?.let { message ->
                Spacer(Modifier.height(16.dp))
                Text(
                    text = message,
                    color = when {
                        message.contains("Congratulations") -> myGreen
                        message.contains("generated") -> Color.Black
                        else -> Color.Red

                    },
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp
                )
            }
        }

        Button(
            onClick = {
                viewModel.togglePause()
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = myPurple,
                contentColor = Color.White
            ),
            enabled = !isGeneratedByComputer

        ) {
            Text( if(isPaused) "Resume" else "Pause")
        }

        Spacer(Modifier.height(6.dp))

        Button(
            onClick = {
                viewModel.provideHint()
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = myPurple,
                contentColor = Color.White
            ),
            enabled = remainingHints > 0 && !isGeneratedByComputer
        ) {
            Text("Hint")
        }

        Spacer(Modifier.height(6.dp))

        Button(
            onClick = {
                solvedMessage = when {
                    isGeneratedByComputer -> "This solution was generated."
                    viewModel.isSolved() -> "Congratulations! You solved this sudoku board!"
                    else -> "You didn't solve this sudoku board."

            }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = myPurple,
                contentColor = Color.White
            )
        ) {
            Text("Check solution")
        }
    }
}
