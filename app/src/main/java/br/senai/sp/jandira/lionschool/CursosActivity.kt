package br.senai.sp.jandira.lionschool

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.tooling.preview.Preview
import br.senai.sp.jandira.lionschool.model.CursosList
import br.senai.sp.jandira.lionschool.service.RetrofitFactory

import br.senai.sp.jandira.lionschool.ui.theme.LionSchoolTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.log

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


@Composable
fun Greeting2() {

    val call = RetrofitFactory().getCursosService().getCursos()

    var cursos by remember{
        mutableStateOf(listOf<br.senai.sp.jandira.lionschool.model.Cursos>())
    }

    call.enqueue(object : Callback<CursosList> {
        override fun onResponse(
            call: Call<CursosList>,
            response: Response<CursosList>
        ) {
            //Duas exclamações seignificam que pode vir nulo
            cursos = response.body()!!.cursos

        }

        override fun onFailure(call: Call<CursosList>, t: Throwable) {
            Log.i(
                "ds2m",
                "onFailure: ${t.message}"
            )
        }

    })

    Log.d("v","${cursos}")


    Column(modifier = Modifier.background(Color(0,76,153))) {


        Column(modifier = Modifier.fillMaxSize()) {
            val selectedTabIndex = remember { mutableStateOf(0) }

            TabRow(selectedTabIndex = selectedTabIndex.value) {

                Tab(selected = selectedTabIndex.value == 0, onClick = { selectedTabIndex.value = 0 }) {
                    Text(text = "${cursos}")

                    Icon(painter = painterResource(id = R.drawable.logo_image), contentDescription = "")
                }
                Tab(selected = selectedTabIndex.value == 1, onClick = { selectedTabIndex.value = 1 }) {
                    Text(text = "Tab 2")
                }

            }

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