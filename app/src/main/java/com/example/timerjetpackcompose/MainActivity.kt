package com.example.timerjetpackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

        }
    }
}

@Composable
fun Timer(
    totalTime: Long,
    handleColor: Color,
    inactiveBarColor: Color,
    activeBarColor: Color,
    modifier: Modifier = Modifier,
    initialValue: Float = 0f,
    strokeWidth: Dp = 10.dp

) {
    var size by remember {
        mutableStateOf(IntSize.Zero)
    }
    var value by remember {
        mutableStateOf(initialValue)
    }
    var currentTime by remember {
        mutableStateOf(totalTime)
    }
    var isTimerRunning by remember {
        mutableStateOf(false)
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .onSizeChanged {
                size = it
            }
    ){Canvas(modifier = modifier) {
        drawArc(
            color = inactiveBarColor,
            startAngle = -215f,
            sweepAngle = 250f,
            useCenter = false,
            size = Size(size.width.toFloat(), size.height.toFloat()),
            style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
        )
        drawArc(
            color = activeBarColor,
            startAngle = -215f,
            sweepAngle = 250f * value,
            useCenter = false,
            size = Size(size.width.toFloat(), size.height.toFloat()),
            style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
        )
        val center = Offset(size.width / 2f, size.height /2f)
        val beta = (250f * value + 145f ) * (PI / 180f).toFloat()
        val r = size.width / 2f
        val a = cos(beta) * r
        val b = sin(beta) * r
        drawPoints(
            listOf(Offset(center.x + a, center.y + b)),
            pointMode = PointMode.Points,
            color = handleColor,
            strokeWidth = (strokeWidth * 3f).toPx(),
            cap = StrokeCap.Round
        )
    }
        Text(
            text = (currentTime / 1000).toString(),
            fontSize = 44.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier.align(Alignment.BottomCenter)
                .background(
                    color = if (!isTimerRunning || currentTime <= 0L) {
                        Color.Green
                    } else {
                        Color.Red
                    }
                ),
//            colors = ButtonDefaults.buttonColors(
//
//                background = if (!isTimerRunning || currentTime <= 0L) {
//                    Color.Green
//                } else {
//                    Color.Red
//                }
//            )
        ) {
            Text( if(!isTimerRunning && currentTime >= 0L) "START"
            else if(isTimerRunning && currentTime <= 0L )"STOP"
                else "RESTART"
                )
        }

    }
}