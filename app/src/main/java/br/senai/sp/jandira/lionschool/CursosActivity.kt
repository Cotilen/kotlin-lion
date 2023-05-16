package br.senai.sp.jandira.lionschool

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.senai.sp.jandira.lionschool.ui.theme.LionSchoolTheme

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
    val switchState = remember { mutableStateOf(false) }

    Column(modifier = Modifier.background(Color(0,76,153))) {
      // Estado para controlar o valor do Switch

      Switch(
          checked = switchState.value,
          onCheckedChange = { isChecked ->
              switchState.value = isChecked
          },
          modifier = Modifier.width(310.dp).height(30.dp),
          colors = SwitchDefaults.colors(
              checkedThumbColor = Color.Blue,
              uncheckedThumbColor = Color.Blue,
              checkedTrackColor = Color.White,
              uncheckedTrackColor = Color.White
          ),
      )
  }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LionSchoolTheme {
        Greeting2()
    }
}