package com.example.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.EduLockerDualCircleLogo
import com.example.ui.theme.CanvasCream
import com.example.ui.theme.DarkBorder
import com.example.ui.theme.OdishaMagenta
import com.example.ui.theme.PureWhite
import com.example.ui.theme.TextDark
import com.example.ui.theme.TextMuted
import com.example.ui.viewmodel.EduLockerViewModel

// Screen 25: Olympiad Series
@Composable
fun OlympiadSeriesScreen(
    viewModel: EduLockerViewModel,
    onContinue: () -> Unit,
    modifier: Modifier = Modifier
) {
    val rulesAgreed by viewModel.olympiadRulesAgreed.collectAsState()
    val context = LocalContext.current

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(CanvasCream)
            .padding(horizontal = 22.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            EduLockerDualCircleLogo(size = 46.dp)
            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "Olympiad Series",
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.ExtraBold,
                color = TextDark,
                fontSize = 22.sp
            )
            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "The Olympiad Series is a competitive academic platform designed to encourage students to test their knowledge, develop problem solving skills, and showcase their potential through a series of subject based Olympiads.\n\nParticipants will compete across different levels and subjects, with opportunities to earn certificates, recognition, and exciting rewards",
                style = MaterialTheme.typography.bodyMedium,
                color = TextDark.copy(alpha = 0.85f),
                fontSize = 13.sp,
                lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Why Participate?",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = TextDark
            )
            Spacer(modifier = Modifier.height(6.dp))

            val bulletPoints = listOf(
                "Test your knowledge and problem-solving abilities",
                "Compete with students from across the country",
                "Earn a Certificate of Participation",
                "Get recognized for your performance",
                "Win exciting prizes and awards",
                "Build your academic profile"
            )

            bulletPoints.forEach { pt ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Text("• ", color = TextDark, fontWeight = FontWeight.Bold)
                    Text(
                        text = pt,
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextDark.copy(alpha = 0.85f),
                        fontSize = 13.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Participate. Compete. Learn. Achieve.",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                color = TextDark
            )

            Spacer(modifier = Modifier.height(18.dp))

            // Checkbox
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { viewModel.olympiadRulesAgreed.value = !rulesAgreed },
                verticalAlignment = Alignment.Top
            ) {
                RadioButton(
                    selected = rulesAgreed,
                    onClick = { viewModel.olympiadRulesAgreed.value = !rulesAgreed },
                    colors = RadioButtonDefaults.colors(selectedColor = OdishaMagenta)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "I confirm that I voluntarily agree to participate in the Olympiad Series and accept the competition rules and guidelines",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextDark,
                    fontSize = 13.sp,
                    lineHeight = 18.sp
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Rules ang Guidelines",
                color = TextDark.copy(alpha = 0.8f),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .padding(start = 36.dp)
                    .clickable {
                        Toast.makeText(context, "Official Olympiad Guidelines: 90 mins online exam, negative marking 0.25", Toast.LENGTH_LONG).show()
                    }
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Bottom Continue
            Button(
                onClick = onContinue,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth(0.55f)
                    .height(52.dp)
                    .border(2.dp, DarkBorder, RoundedCornerShape(30.dp)),
                shape = RoundedCornerShape(30.dp),
                colors = ButtonDefaults.buttonColors(containerColor = OdishaMagenta)
            ) {
                Text(
                    text = "Continue",
                    color = PureWhite,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

// Screen 26: Olympiad Registration Successful! 🏆
@Composable
fun OlympiadSuccessScreen(
    viewModel: EduLockerViewModel? = null,
    onDone: () -> Unit,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(Unit) {
        viewModel?.registerForOlympiad()
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(CanvasCream)
            .padding(horizontal = 24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 80.dp)
        ) {
            EduLockerDualCircleLogo(size = 46.dp)
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Registration Successful!",
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.ExtraBold,
                color = TextDark,
                fontSize = 22.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Thank you for registering for the Olympiad Series. We'll inform you shortly before the Olympiad begins with all the necessary details.",
                style = MaterialTheme.typography.bodyLarge,
                color = TextDark.copy(alpha = 0.8f),
                fontSize = 14.sp,
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Best of luck! 🏆",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = TextDark,
                fontSize = 16.sp
            )
        }

        // Bottom Done Button
        Button(
            onClick = onDone,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 36.dp)
                .fillMaxWidth(0.55f)
                .height(52.dp)
                .border(2.dp, DarkBorder, RoundedCornerShape(30.dp)),
            shape = RoundedCornerShape(30.dp),
            colors = ButtonDefaults.buttonColors(containerColor = OdishaMagenta)
        ) {
            Text(
                text = "Done",
                color = PureWhite,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
