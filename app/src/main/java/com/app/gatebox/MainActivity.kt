package com.app.gatebox

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOut
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.animation.with
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.app.gatebox.component.MainWebView
import com.app.gatebox.ui.theme.GateBoxTheme
import kotlinx.coroutines.delay
import kotlin.math.PI
import kotlin.math.sin
import kotlin.random.Random
import kotlin.random.nextInt

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

  val visibleState = remember {
    mutableStateOf(true)
  }

//  val offsetY by animateDpAsState(
//    targetValue = if (visibleState.value) height.dp else 0.dp,
//    animationSpec = tween(5000)
//  )

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
        .offset(x = getX(yState.value.toInt(), 40f, 80f).dp, y = yState)
        .alpha(alphaState),
      painter = painterResource(R.drawable.baseline_heart_broken_24),
      contentDescription = null,
      tint = Color.Red
    )
  }

  LaunchedEffect(key1 = visibleState) {
    visibleState.value = !visibleState.value
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