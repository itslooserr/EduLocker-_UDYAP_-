package com.example.ui.screens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.CanvasCream
import kotlinx.coroutines.delay

// =========================================================================
// SCREEN 1: Splash Screen (Exact Replica of 1.png)
// =========================================================================
@Composable
fun SplashScreen(
    onTimeout: () -> Unit,
    modifier: Modifier = Modifier
) {
    var startAnimation by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        startAnimation = true
        delay(2200)
        onTimeout()
    }

    val alphaAnim by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 800, easing = FastOutSlowInEasing),
        label = "alpha"
    )

    val scaleAnim by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0.9f,
        animationSpec = tween(durationMillis = 800, easing = FastOutSlowInEasing),
        label = "scale"
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(CanvasCream)
            .clickable { onTimeout() }
    ) {
        // Centered Brand: Soft Pink Circle overlapping bold "EduLocker" text (Exact from 1.png)
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .alpha(alphaAnim)
                .scale(scaleAnim),
            contentAlignment = Alignment.CenterStart
        ) {
            // Soft Pink Background Circle behind the "Edu" letters
            Box(
                modifier = Modifier
                    .offset(x = (-14).dp)
                    .size(88.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFFBCFE8).copy(alpha = 0.85f))
            )

            // Bold "EduLocker" Text
            Text(
                text = "EduLocker",
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF111827),
                fontSize = 38.sp,
                letterSpacing = (-0.5).sp,
                modifier = Modifier.padding(start = 4.dp)
            )
        }

        // Bottom: "Issued by Govt. of Odisha" (Exact from 1.png)
        Text(
            text = "Issued by Govt. of Odisha",
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF64748B),
            fontSize = 13.5.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 44.dp)
        )
    }
}
