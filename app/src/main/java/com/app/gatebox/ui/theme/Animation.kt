package com.app.gatebox.ui.theme

import androidx.annotation.FloatRange
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.app.gatebox.R
import kotlin.math.PI
import kotlin.math.sin
import kotlin.random.Random

const val TWO_PI = 2 * PI

fun computeX(
  y: Int,
  amplitude: Float,
  periode: Float
) = (sin(y * TWO_PI / periode) * amplitude).toFloat()

data class FloatingAnimationProp(
  var y: Dp = 0.dp,
  @FloatRange(0.0, 1.0) var alpha: Float = 1.0f
)

@Composable
fun LikeAnimation(
  modifier: Modifier = Modifier
) {
  val height = LocalConfiguration.current.screenHeightDp

  val amplitude = remember {
    Random.nextDouble(10.0, 20.0).toFloat()
  }
  val periode = remember {
    Random.nextDouble(90.0, 100.0).toFloat()
  }

  var animationState by remember {
    mutableStateOf(FloatingAnimationProp())
  }

  with(animationState) {
    LaunchedEffect(Unit) {
      animate(
        initialValue = 1f,
        targetValue = 0f,
        animationSpec = repeatable(
          iterations = 1,
          animation = tween(4000, easing = LinearEasing),
          repeatMode = RepeatMode.Restart
        ),
        block = { alpha, _ ->
          animationState = animationState.copy(alpha = alpha)
        }
      )
    }
    LaunchedEffect(Unit) {
      animate(
        initialValue = height.toFloat(),
        targetValue = 0.0f,
        animationSpec = repeatable(
          iterations = 1,
          animation = tween(Random.nextInt(3000, 6000), easing = LinearEasing),
          repeatMode = RepeatMode.Restart
        ),
        block = { y, _ ->
          animationState = animationState.copy(y = y.dp)
        }
      )
    }

    AnimatedVisibility(
      modifier = modifier,
      visible = y.value != 0.0f
    ) {
      Icon(
        modifier = Modifier
          .offset(
            x = computeX(y.value.toInt(), amplitude, periode).dp,
            y = y
          )
          .alpha(alpha),
        painter = painterResource(R.drawable.baseline_heart_broken_24),
        contentDescription = null,
        tint = Color.Red
      )
    }
  }

}

@Preview
@Composable
fun AnimationOverlay() {
  val heartCount = remember { mutableIntStateOf(0) }
  Box(
    modifier = Modifier
      .fillMaxSize()
      .clickable { heartCount.intValue++ }
  ) {
    repeat(heartCount.intValue) {
      LikeAnimation(
        modifier = Modifier
          .align(Alignment.TopCenter)
      )
    }

    Column(
      modifier = Modifier.fillMaxSize(),
      verticalArrangement = Arrangement.Bottom,
      horizontalAlignment = Alignment.CenterHorizontally
    ) {

      Button(
        onClick = { heartCount.intValue++ },
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
}