package br.senai.sp.jandira.lionschool.model

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.dp
import br.senai.sp.jandira.lionschool.ui.theme.gray
import br.senai.sp.jandira.lionschool.ui.theme.white
import kotlin.math.roundToInt

class ChartModel {
    @Composable
    fun BarChart(
        inputList:List<BarchartInput>,
        modifier: Modifier = Modifier,
        showDescription:Boolean
    ) {
        Row(
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = modifier
        ){
            val listSum by remember {
                mutableStateOf(inputList.sumOf { it.value })
            }
            inputList.forEach { input ->
                val percentage = input.value/listSum.toFloat()
                Bar(
                    modifier = androidx.compose.ui.Modifier
                        .height(120.dp * percentage * inputList.size)
                        .width(40.dp),
                    primaryColor = input.color,
                    percentage = input.value/listSum.toFloat(),
                    description = input.description,
                    showDescription = showDescription
                )
            }
        }
    }
}

@Composable
fun Bar(
    modifier: Modifier = Modifier,
    primaryColor: Color,
    percentage:Float,
    description: String,
    showDescription: Boolean
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ){
        Canvas(
            modifier = Modifier.fillMaxSize()
        ){
            val width = size.width
            val height = size.height
            val barWidth = width / 5 * 3
            val barHeight = height / 8 * 7
            val barHeight3DPart = height - barHeight
            val barWidth3DPart = (width - barWidth)*(height*0.002f)

            var path = Path().apply {
                moveTo(0f,height)
                lineTo(barWidth,height)
                lineTo(barWidth,height-barHeight)
                lineTo(0f,height-barHeight)
                close()
            }
            drawPath(
                path,
                brush = Brush.linearGradient(
                    colors = listOf(gray, primaryColor)
                )
            )
            path = Path().apply {
                moveTo(barWidth,height-barHeight)
                lineTo(barWidth3DPart+barWidth,0f)
                lineTo(barWidth3DPart+barWidth,barHeight)
                lineTo(barWidth,height)
                close()
            }
            drawPath(
                path,
                brush = Brush.linearGradient(
                    colors = listOf(primaryColor, gray)
                )
            )
            path = Path().apply {
                moveTo(0f,barHeight3DPart)
                lineTo(barWidth,barHeight3DPart)
                lineTo(barWidth+barWidth3DPart,0f)
                lineTo(barWidth3DPart,0f)
                close()
            }
            drawPath(
                path,
                brush = Brush.linearGradient(
                    colors = listOf(gray, primaryColor)
                )
            )
            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    "${(percentage*100).roundToInt()} %",
                    barWidth/5f,
                    height + 55f,
                    Paint().apply {
                        this.color = white.toArgb()
                        textSize = 11.dp.toPx()
                        isFakeBoldText = true
                    }
                )
            }
            if(showDescription){
                drawContext.canvas.nativeCanvas.apply {
                    rotate(-50f, pivot = Offset(barWidth3DPart-20,0f)){
                        drawText(
                            description,
                            0f,
                            0f,
                            Paint().apply {
                                this.color = white.toArgb()
                                textSize = 14.dp.toPx()
                                isFakeBoldText = true
                            }
                        )
                    }
                }
            }
        }
    }
}

data class BarchartInput(
    val value:Int,
    val description:String,
    val color:Color
)