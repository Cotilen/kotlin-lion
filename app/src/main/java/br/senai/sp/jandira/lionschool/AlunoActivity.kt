package br.senai.sp.jandira.lionschool

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.senai.sp.jandira.lionschool.model.AlunoList
import br.senai.sp.jandira.lionschool.service.RetrofitFactory
import br.senai.sp.jandira.lionschool.ui.theme.*
import coil.compose.AsyncImage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import br.senai.sp.jandira.lionschool.model.ChartModel
import kotlin.math.roundToInt

class AlunoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            intent.extras
            val matricula = intent.getStringExtra("matricula")

            val matriculaAluno: String = matricula ?: "0"

            LionSchoolTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                    Greeting3(matriculaAluno)
                }
            }
        }
    }
}

@Composable
fun Greeting3(matricula: String) {
    val itemList = remember { mutableStateListOf<BarchartInput>() }

    val context = LocalContext.current

    val call = RetrofitFactory().getAlunoService().getAluno(matricula)

    var aluno by remember {
        mutableStateOf(listOf<br.senai.sp.jandira.lionschool.model.Aluno>())
    }

    call.enqueue(object : Callback<AlunoList> {
        override fun onResponse(
            call: Call<AlunoList>,
            response: Response<AlunoList>
        ) {
            //Duas exclamações seignificam que pode vir nulo
            aluno = response.body()!!.aluno;
        }

        override fun onFailure(call: Call<AlunoList>, t: Throwable) {
            Log.i(
                "ds2m",
                "onFailure: ${t.message}"
            )
        }

    })

    if (aluno.isNotEmpty()) {
        Log.d("cd", aluno[0].Disciplinas.toString())

        Column(modifier = Modifier
            .fillMaxSize()
            .background(Color(0, 76, 153))) {

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically)
            {

                AsyncImage(
                    model = aluno[0].Foto,
                    contentDescription = "foto",
                    modifier = Modifier.size(50.dp)
                )
                Icon(painter = painterResource(id = R.drawable.baseline_density_medium_24),
                    contentDescription = "")
            }
            
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)) {

                Spacer(modifier = Modifier.height(20.dp))
                
                Text(text =aluno[0].Aluno,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White)

                Text(text = aluno[0].Matricula,
                    modifier = Modifier.padding(top = 5.dp),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White)

                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp),
                    verticalAlignment = Alignment.CenterVertically)
                {

                    if (aluno[0].Status == "Cursando"){
                    Icon(painter = painterResource(id = R.drawable.ampulheta),
                        contentDescription = "",
                        tint = Color.White)
                    }else{
                        Icon(painter = painterResource(id = R.drawable.certo),
                            contentDescription = "",
                            tint = Color.White)
                    }
                    
                    Text(text = aluno[0].Status,
                        modifier = Modifier.padding(start = 5.dp),
                    color = Color.White)

                }

                Spacer(modifier = Modifier.height(15.dp))
                
                Text(text = aluno[0].Curso,
                    fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White)

                Spacer(modifier = Modifier.height(15.dp))

                //Grafico
                aluno[0].Disciplinas.forEach {

                    val words = it.nome.split(" ")
                    val nome = buildAnnotatedString {
                        words.forEach {
                            if(it.length >= 2){
                                append(it.substring(0,2))
                            }else{
                                append(it)
                            }
                        }
                    }
                    if (it.media.toInt() < 50) {
                        itemList.add(BarchartInput(it.media.toInt(), nome.toString(), Color.Red))
                    }else{
                        itemList.add(BarchartInput(it.media.toInt(), nome.toString(), Color.Blue))
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(30.dp)
                    ,                    contentAlignment = Alignment.TopCenter
                ){
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(15.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){

                        BarChart(
                            inputList =itemList,
                            modifier = Modifier
                                .fillMaxWidth(),
                            showDescription = true
                        )
                    }
                }
                    
                }

            }

        }
    }


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
                    .height(200.dp * percentage * inputList.size)
                    .width(30.dp),
                primaryColor = input.color,
                percentage = input.value,
                description = input.description,
                showDescription = showDescription
            )
        }
    }
}


@Composable
fun Bar(
    modifier: Modifier = Modifier,
    primaryColor: Color,
    percentage: Int,
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
                    colors = listOf( primaryColor, Color(189,197,208))
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
                    colors = listOf(primaryColor, Color(189,197,208))
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
                    colors = listOf( primaryColor, Color(189,197,208))
                )
            )
            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    "${percentage} ",
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

@Composable
fun ExtractFirstTwoLetters(sentence: String) {
    val words = sentence.split(" ")

    val annotatedString = buildAnnotatedString {
        words.forEach { word ->
            if (word.length >= 2) {
                withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                    append(word.substring(0, 2))
                }
                append(word.substring(2))
            } else {
                append(word)
            }
            append(" ")
        }
    }
}