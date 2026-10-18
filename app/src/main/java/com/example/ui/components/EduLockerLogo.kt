package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.DarkBorder
import com.example.ui.theme.OdishaMagenta
import com.example.ui.theme.OdishaMagentaDark
import com.example.ui.theme.PureWhite
import com.example.ui.theme.SoftBlushPink
import com.example.ui.theme.TextDark

@Composable
fun EduLockerDualCircleLogo(
    modifier: Modifier = Modifier,
    size: Dp = 46.dp
) {
    val circleSize = size * 0.72f
    Box(
        modifier = modifier.size(size),
        contentAlignment = Alignment.Center
    ) {
        // Left circle: Solid vibrant Odisha Magenta
        Box(
            modifier = Modifier
                .size(circleSize)
                .offset(x = (-size * 0.16f))
                .clip(CircleShape)
                .background(OdishaMagenta)
        )
        // Right circle: Soft blush pink with dark outline overlapping
        Box(
            modifier = Modifier
                .size(circleSize)
                .offset(x = (size * 0.16f))
                .clip(CircleShape)
                .background(SoftBlushPink)
                .border(2.dp, DarkBorder, CircleShape)
        )
    }
}

@Composable
fun EduLockerBrandHeader(
    modifier: Modifier = Modifier,
    showSubtitle: Boolean = true
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start
    ) {
        EduLockerDualCircleLogo(size = 46.dp)
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "Welcome to EduLocker",
            style = MaterialTheme.typography.headlineLarge,
            color = TextDark,
            fontWeight = FontWeight.Bold,
            fontSize = 26.sp
        )
        if (showSubtitle) {
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "State-level digital student ecosystem issued by Govt. of Odisha.",
                style = MaterialTheme.typography.bodyMedium,
                color = TextDark.copy(alpha = 0.8f),
                fontSize = 14.sp
            )
        }
    }
}
