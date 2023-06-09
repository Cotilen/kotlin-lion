package br.senai.sp.jandira.lionschool
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.senai.sp.jandira.lionschool.model.AlunosList
import br.senai.sp.jandira.lionschool.model.ConclusaoList
import br.senai.sp.jandira.lionschool.model.CursosList
import br.senai.sp.jandira.lionschool.service.RetrofitFactory

import br.senai.sp.jandira.lionschool.ui.theme.LionSchoolTheme
import coil.compose.AsyncImage

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class CursosActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LionSchoolTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting2()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Greeting2() {

    val context = LocalContext.current

    val call = RetrofitFactory().getCursosService().getCursos()

    var cursos by remember {
        mutableStateOf(listOf<br.senai.sp.jandira.lionschool.model.Cursos>())
    }

    val callConclusao = RetrofitFactory().getConclusaoService().getConclusao()

    var conclusao by remember {
        mutableStateOf(listOf<br.senai.sp.jandira.lionschool.model.Conclusao>())
    }


    call.enqueue(object : Callback<CursosList> {
        override fun onResponse(
            call: Call<CursosList>,
            response: Response<CursosList>
        ) {
            //Duas exclamações seignificam que pode vir nulo
            cursos = response.body()!!.cursos;
        }

        override fun onFailure(call: Call<CursosList>, t: Throwable) {
            Log.i(
                "ds2m",
                "onFailure: ${t.message}"
            )
        }

    })

    callConclusao.enqueue(object : Callback<ConclusaoList> {
        override fun onResponse(
            call: Call<ConclusaoList>,
            response: Response<ConclusaoList>
        ) {
            //Duas exclamações seignificam que pode vir nulo
            conclusao = response.body()!!.conclusao;
        }

        override fun onFailure(call: Call<ConclusaoList>, t: Throwable) {
            Log.i(
                "ds2m",
                "onFailure: ${t.message}"
            )
        }

    })

    var alunos by remember {
        mutableStateOf(listOf<br.senai.sp.jandira.lionschool.model.Alunos>())
    }


    Column(modifier = Modifier.background(Color(0, 76, 153))) {

        if (cursos.isNotEmpty() && conclusao.isNotEmpty()) {
            Log.d("v", conclusao.toString())
            Log.d("v", alunos.toString())

            Column(modifier = Modifier.fillMaxSize()) {
                val selectedTabIndex = remember { mutableStateOf(0) }

                TabRow(selectedTabIndex = selectedTabIndex.value) {

                    Tab(
                        selected = selectedTabIndex.value == 0,
                        onClick = { selectedTabIndex.value = 0

                            val callAlunos = RetrofitFactory().getAlunosService().getCurso("DS")

                            //Call Alunos
                            callAlunos.enqueue(object : Callback<AlunosList> {
                                override fun onResponse(
                                    call: Call<AlunosList>,
                                    response: Response<AlunosList>
                                ) {
                                    //Duas exclamações seignificam que pode vir nulo
                                    alunos = response.body()!!.alunos;
                                }

                                override fun onFailure(call: Call<AlunosList>, t: Throwable) {
                                    Log.i(
                                        "ds2m",
                                        "onFailure: ${t.message}"
                                    )
                                }
                            })
                        }) {
                        Text(
                            text = cursos[0].nome,
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center
                        )

                    }
                    Tab(
                        selected = selectedTabIndex.value == 1,
                        onClick = { selectedTabIndex.value = 1

                            val callAlunos = RetrofitFactory().getAlunosService().getCurso("RDS")

                            //Call Alunos
                            callAlunos.enqueue(object : Callback<AlunosList> {
                                override fun onResponse(
                                    call: Call<AlunosList>,
                                    response: Response<AlunosList>
                                ) {
                                    //Duas exclamações seignificam que pode vir nulo
                                    alunos = response.body()!!.alunos;
                                }

                                override fun onFailure(call: Call<AlunosList>, t: Throwable) {
                                    Log.i(
                                        "ds2m",
                                        "onFailure: ${t.message}"
                                    )
                                }
                            })

                        }) {
                        Text(
                            text = cursos[1].nome,
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center
                        )
                    }

                }

                //Chips de conclusão
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 15.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                )
                {
                    Chip(
                        onClick = {
                            val callAlunos = RetrofitFactory().getAlunosService()
                                .getStatus("DS", "Finalizado")
                            Log.d("v", "DS Finalizado")

                            //Call Alunos
                            callAlunos.enqueue(object : Callback<AlunosList> {
                                override fun onResponse(
                                    call: Call<AlunosList>,
                                    response: Response<AlunosList>
                                ) {
                                    //Duas exclamações seignificam que pode vir nulo
                                    alunos = response.body()!!.alunos;
                                }

                                override fun onFailure(call: Call<AlunosList>, t: Throwable) {
                                    Log.i(
                                        "ds2m",
                                        "onFailure: ${t.message}"
                                    )
                                }
                            })

                            if (selectedTabIndex.value == 1) {
                                val callAlunos = RetrofitFactory().getAlunosService()
                                    .getStatus("RDS", "Finalizado")

                                //Call Alunos
                                callAlunos.enqueue(object : Callback<AlunosList> {
                                    override fun onResponse(
                                        call: Call<AlunosList>,
                                        response: Response<AlunosList>
                                    ) {
                                        //Duas exclamações seignificam que pode vir nulo
                                        alunos = response.body()!!.alunos;
                                    }

                                    override fun onFailure(call: Call<AlunosList>, t: Throwable) {
                                        Log.i(
                                            "ds2m",
                                            "onFailure: ${t.message}"
                                        )
                                    }
                                })
                            }

                             },
                        border = BorderStroke(2.dp, Color.Black),
                        colors = ChipDefaults.chipColors(Color(241, 185, 69))
                    ) {
                        Text(
                            text = "Finalizado",
                            modifier = Modifier.width(100.dp),
                            textAlign = TextAlign.Center,
                            color = Color(51, 71, 176)
                        )

                    }

                    Chip(
                        onClick = {
                            val callAlunos = RetrofitFactory().getAlunosService()
                                .getStatus("DS", "Cursando")
                            Log.d("v", "DS Cursando")

                            //Call Alunos
                            callAlunos.enqueue(object : Callback<AlunosList> {
                                override fun onResponse(
                                    call: Call<AlunosList>,
                                    response: Response<AlunosList>
                                ) {
                                    //Duas exclamações seignificam que pode vir nulo
                                    alunos = response.body()!!.alunos;
                                }

                                override fun onFailure(call: Call<AlunosList>, t: Throwable) {
                                    Log.i(
                                        "ds2m",
                                        "onFailure: ${t.message}"
                                    )
                                }
                            })

                            if (selectedTabIndex.value == 1) {
                                val callAlunos = RetrofitFactory().getAlunosService()
                                    .getStatus("RDS", "Cursando")

                                //Call Alunos
                                callAlunos.enqueue(object : Callback<AlunosList> {
                                    override fun onResponse(
                                        call: Call<AlunosList>,
                                        response: Response<AlunosList>
                                    ) {
                                        //Duas exclamações seignificam que pode vir nulo
                                        alunos = response.body()!!.alunos;
                                        Log.d("bc", alunos.toString())
                                    }

                                    override fun onFailure(call: Call<AlunosList>, t: Throwable) {
                                        Log.i(
                                            "ds2m",
                                            "onFailure: ${t.message}"
                                        )
                                    }
                                })
                            } },
                        border = BorderStroke(2.dp, Color.Black),
                        colors = ChipDefaults.chipColors(Color(241, 185, 69))
                    ) {
                        Text(
                            text = "Cursando",
                            modifier = Modifier.width(100.dp),
                            textAlign = TextAlign.Center,
                            color = Color(51, 71, 176)
                        )

                    }
                }

                Text(
                    text = "Conclusão",
                    modifier = Modifier.padding(start = 15.dp, top = 15.dp),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                //Chips Conclusão
                Row(
                    modifier = Modifier
                        .padding(top = 15.dp, end = 15.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    LazyRow() {
                        items(conclusao[0].ano) {

                            Log.d("as",selectedTabIndex.value.toString())
                            Chip(
                                onClick = {


                                    if (selectedTabIndex.value == 1){
                                        val callAlunos = RetrofitFactory().getAlunosService()
                                            .getConclusao("RDS","Cursando",it)
                                        Log.d("as","RDS Cursando")

                                        //Call Alunos
                                        callAlunos.enqueue(object : Callback<AlunosList> {
                                            override fun onResponse(
                                                call: Call<AlunosList>,
                                                response: Response<AlunosList>
                                            ) {
                                                //Duas exclamações seignificam que pode vir nulo

                                                if (response.body() != null) {
                                                    alunos = response.body()!!.alunos;
                                                } else {
                                                    Toast.makeText(context, "Não existem alunos  neste ano", Toast.LENGTH_LONG).show()
                                                }
                                            }

                                            override fun onFailure(call: Call<AlunosList>, t: Throwable) {
                                                Log.i(
                                                    "ds2m",
                                                    "onFailure: ${t.message}"
                                                )
                                            }
                                        })

                                    }else{
                                        val callAlunos = RetrofitFactory().getAlunosService()
                                            .getConclusao("DS","Cursando",it)

                                        //Call Alunos
                                        callAlunos.enqueue(object : Callback<AlunosList> {
                                            override fun onResponse(
                                                call: Call<AlunosList>,
                                                response: Response<AlunosList>
                                            ) {
                                                //Duas exclamações seignificam que pode vir nulo

                                                if (response.body() != null) {
                                                    alunos = response.body()!!.alunos;
                                                } else {
                                                    Toast.makeText(context, "Não existem alunos neste ano", Toast.LENGTH_LONG).show()
                                                }
                                            }

                                            override fun onFailure(call: Call<AlunosList>, t: Throwable) {
                                                Log.i(
                                                    "ds2m",
                                                    "onFailure: ${t.message}"
                                                )
                                            }
                                        })
                                    }

                                },
                                modifier = Modifier.padding(5.dp),
                                border = BorderStroke(1.dp, Color.Black),
                                colors = ChipDefaults.chipColors(Color.White)
                            ) {
                                Text(
                                    text = it,
                                    modifier = Modifier.width(35.dp),
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black
                                )

                            }
                        }
                    }
                }

                //Cards de Alunos
                LazyColumn(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                    items(alunos) {
                        Card(
                            modifier = Modifier
                                .width(350.dp)
                                .padding(top = 8.dp),
                            shape = RoundedCornerShape(20.dp),
                            backgroundColor = Color.White,
                            onClick = {
                                val intent = Intent(context, AlunoActivity::class.java)
                                intent.putExtra("matricula",it.matricula)
                                context.startActivity(intent)}
                        ) {
                            Row(modifier = Modifier.padding(8.dp), verticalAlignment =Alignment.CenterVertically ) {
                                Card(shape = CircleShape) {
                                    AsyncImage(
                                        model = it.foto,
                                        contentDescription = "avatar",
                                        modifier = Modifier.size(80.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(16.dp))

                                Column {
                                    Text(text = it.nome.uppercase(),
                                        fontSize = 16.sp,
                                        modifier = Modifier.width(200.dp),
                                        color = Color.Black,
                                        fontWeight = FontWeight.Bold)
                                    Text(text = it.matricula,
                                        fontSize = 15.sp,
                                        color = Color.Black,
                                        fontWeight = FontWeight.Bold)
                                    Text(text = it.status,
                                        fontSize = 10.sp,
                                        color = Color.Black,
                                        fontWeight = FontWeight.Bold)
                                }

                                Icon(painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24), contentDescription = "")

                            }

                        }
                    }
                }

            }
        }
        else {
            Text(text = "Carregando",
                fontSize = 40.sp)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LionSchoolTheme {
        Greeting2()
    }
}