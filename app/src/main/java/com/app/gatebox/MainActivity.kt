package com.app.gatebox

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.gatebox.ui.theme.GateBoxTheme
import kotlin.math.PI
import kotlin.math.sin
import kotlin.random.Random

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      GateBoxTheme {
//        MainWebView()
        Column(
          modifier = Modifier.fillMaxSize(),
          verticalArrangement = Arrangement.Bottom,
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          Screen()
        }
      }
    }
  }
}

const val TWO_PI = 2 * PI

val getX: (Int, Float, Float) -> Float = { y, amplitude, period ->
  (sin(y * TWO_PI / period) * amplitude).toFloat()
}

@Composable
fun LikeAnimation() {
  val height = LocalConfiguration.current.screenHeightDp

  val amplitude = remember {
    Random.nextDouble(10.0, 20.0).toFloat()
  }
  val periode = remember {
    Random.nextDouble(90.0, 100.0).toFloat()
  }


  var alphaState by remember { mutableFloatStateOf(1f) }
  var yState by remember { mutableStateOf(0.dp) }

  LaunchedEffect(Unit) {
    animate(
      initialValue = 1f,
      targetValue = 0f,
      animationSpec = repeatable(
        iterations = 1,
        animation = tween(Random.nextInt(2000, 4000), easing = LinearEasing),
        repeatMode = RepeatMode.Restart
      ),
      block = { alpha, _ ->
        alphaState = alpha
      }
    )
  }

  LaunchedEffect(Unit) {
    animate(
      initialValue = height.toFloat(),
      targetValue = 0.0f,
      animationSpec = repeatable(
        iterations = 1,
        animation = tween(Random.nextInt(2000, 4000), easing = LinearEasing),
        repeatMode = RepeatMode.Restart
      ),
      block = { y, _ ->
        yState = y.dp
      }
    )
  }

  AnimatedVisibility(
    visible = yState.value > 20
  ) {
    Icon(
      modifier = Modifier
        .offset(x = getX(yState.value.toInt(), amplitude, periode).dp, y = yState)
        .alpha(alphaState),
      painter = painterResource(R.drawable.baseline_heart_broken_24),
      contentDescription = null,
      tint = Color.Red
    )
  }
}

@Preview
@Composable
fun Screen() {
  val heartCount = remember { mutableStateOf(0) }
  repeat(heartCount.value) {
    LikeAnimation()
  }
  Column(
    modifier = Modifier.fillMaxSize(),
    verticalArrangement = Arrangement.Bottom,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {

    Button(
      onClick = { heartCount.value++ },
      modifier = Modifier
        .padding(24.dp)
        .wrapContentHeight()
        .wrapContentWidth()
    ) {
      Text(
        text = "Like",
        color = Color.White
      )
    }
  }
}