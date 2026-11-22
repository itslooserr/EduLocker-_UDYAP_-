package com.example.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.theme.TextDark
import com.example.ui.theme.TextMuted

@Composable
fun EduLockerDualCircleLogo(
    modifier: Modifier = Modifier,
    size: Dp = 46.dp
) {
    Image(
        painter = painterResource(id = R.drawable.udyap_logo_full),
        contentDescription = "UDYAP Logo",
        contentScale = ContentScale.Fit,
        modifier = modifier
            .height(size)
            .width(size * 2.6f)
    )
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
        Image(
            painter = painterResource(id = R.drawable.udyap_logo_full),
            contentDescription = "UDYAP Official Logo",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .height(44.dp)
                .width(126.dp)
        )
        Spacer(modifier = Modifier.height(14.dp))
        Text(
            text = "Welcome to UDYAP",
            style = MaterialTheme.typography.headlineLarge,
            color = TextDark,
            fontWeight = FontWeight.Bold,
            fontSize = 26.sp
        )
        if (showSubtitle) {
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Unified Digital Youth Advancement Platform.",
                style = MaterialTheme.typography.bodyMedium,
                color = TextMuted,
                fontSize = 14.sp
            )
        }
    }
}
