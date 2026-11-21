package com.example.ui.screens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.theme.PureWhite
import com.example.ui.theme.TextMuted
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onTimeout: () -> Unit,
    modifier: Modifier = Modifier
) {
    var startAnimation by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        startAnimation = true
        delay(2000)
        onTimeout()
    }

    val alphaAnim by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 700, easing = FastOutSlowInEasing),
        label = "alpha"
    )

    val scaleAnim by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0.92f,
        animationSpec = tween(durationMillis = 700, easing = FastOutSlowInEasing),
        label = "scale"
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(PureWhite)
            .clickable { onTimeout() }
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .alpha(alphaAnim)
                .scale(scaleAnim),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.udyap_logo_full),
                contentDescription = "UDYAP Logo",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .width(230.dp)
                    .height(100.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Unified Digital Youth Advancement Platform",
                style = MaterialTheme.typography.bodySmall,
                color = TextMuted,
                fontSize = 12.5.sp,
                fontWeight = FontWeight.Medium,
                letterSpacing = 0.2.sp
            )
        }

        Text(
            text = "Issued by Govt. of Odisha",
            style = MaterialTheme.typography.bodyMedium,
            color = TextMuted,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .navigationBarsPadding()
                .padding(bottom = 32.dp)
        )
    }
}
