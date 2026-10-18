package com.example.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.CanvasCream
import com.example.ui.theme.PureWhite

// =========================================================================
// SCREENS 2 to 7: Onboarding Intro Screens (Exact Replicas of 2.png - 7.png)
// Clean layout designed for IIT State Level presentation with zero overlap
// =========================================================================
@Composable
fun OnboardingScreen(
    pageIndex: Int, // 2: Welcome, 3: 01, 4: 02, 5: 03, 6: 04, 7: 05
    onNext: () -> Unit,
    modifier: Modifier = Modifier
) {
    val stepTitle = when (pageIndex) {
        2 -> "Welcome to EduLocker" // 2.png
        3 -> "" // 3.png (01 - description only)
        4 -> "Everything You Need. One Platform." // 4.png (02)
        5 -> "Your Journey. Your Digital Identity." // 5.png (03)
        6 -> "Discover. Learn. Grow." // 6.png (04)
        7 -> "Your Future. Connected." // 7.png (05)
        else -> ""
    }

    val stepDescription = when (pageIndex) {
        2 -> "One platform to learn, grow, and\ndiscover opportunities." // 2.png
        3 -> "EduLocker enables secure digital\naccess to verified documents,\nsubject to applicable laws and\nregulations." // 3.png
        4 -> "EduLocker brings education,\nopportunities, career guidance,\nscholarships, and essential student\nservices together in one digital\necosystem." // 4.png
        5 -> "Keep your academic journey,\nachievements, documents, and\nimportant student records organized\nin one secure place." // 5.png
        6 -> "Find scholarships, competitions, career\nopportunities, educational updates, and\nresources that match your journey." // 6.png
        7 -> "From learning and scholarships to careers,\nskills, and opportunities - EduLocker\nbrings the resources you need to move\nforward, all in one place." // 7.png
        else -> ""
    }

    val buttonLabel = when (pageIndex) {
        2 -> "Get Started" // 2.png
        3, 4, 5, 6 -> "Next" // 3.png, 4.png, 5.png, 6.png
        7 -> "Continue" // 7.png
        else -> "Next"
    }

    val counterText = when (pageIndex) {
        3 -> "01"
        4 -> "02"
        5 -> "03"
        6 -> "04"
        7 -> "05"
        else -> null
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(CanvasCream)
    ) {
        // 1. TOP GRAPHICS SECTION (Fixed top area so content below never overlaps)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        ) {
            // Giant Magenta Curve Arc (Exact Geometry from screenshots 2.png - 7.png)
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            ) {
                val w = size.width
                val h = size.height

                // Smooth arc from top spanning across to the left edge
                val arcPath = Path().apply {
                    moveTo(0f, 0f)
                    lineTo(w * 0.65f, 0f)
                    // Smooth curve down to left edge at y = h * 0.95f
                    cubicTo(
                        w * 0.62f, h * 0.45f,
                        w * 0.35f, h * 0.90f,
                        0f, h * 0.95f
                    )
                    close()
                }
                drawPath(arcPath, color = Color(0xFFD81B60))
            }

            // Overlapping Soft Pink Circle Badge (Exact Position & Styling)
            Box(
                modifier = Modifier
                    .offset(x = 72.dp, y = 175.dp)
                    .size(114.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFF9C1D9)),
                contentAlignment = Alignment.Center
            ) {
                if (counterText != null) {
                    Text(
                        text = counterText,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF374151),
                        fontSize = 29.sp,
                        letterSpacing = (-0.5).sp
                    )
                } else {
                    // Screen 2: Person raising hand silhouette icon (Exact from 2.png)
                    StudentRaisingHandIcon(size = 46.dp)
                }
            }
        }

        // 2. TEXT CONTENT AREA (Starts below the header graphics with clear margin)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 38.dp, end = 38.dp, top = 315.dp)
        ) {
            if (stepTitle.isNotEmpty()) {
                Text(
                    text = stepTitle,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF111827),
                    fontSize = 17.5.sp,
                    lineHeight = 24.sp
                )
                Spacer(modifier = Modifier.height(12.dp))
            } else {
                Spacer(modifier = Modifier.height(4.dp))
            }

            Text(
                text = stepDescription,
                style = MaterialTheme.typography.bodyLarge,
                color = Color(0xFF1E293B),
                lineHeight = 24.sp,
                fontSize = 15.sp,
                fontWeight = FontWeight.Normal
            )
        }

        // 3. BOTTOM-RIGHT ACTION BUTTON: Double-Border Pill (Exact from 2.png - 7.png)
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 22.dp, bottom = 44.dp)
        ) {
            Surface(
                modifier = Modifier
                    .clip(RoundedCornerShape(36.dp))
                    .border(1.5.dp, Color(0xFF1E293B), RoundedCornerShape(36.dp))
                    .padding(3.5.dp)
                    .clickable { onNext() },
                shape = RoundedCornerShape(32.dp),
                color = Color(0xFFD81B60)
            ) {
                Box(
                    modifier = Modifier.padding(
                        horizontal = if (pageIndex == 2 || pageIndex == 7) 28.dp else 36.dp,
                        vertical = 10.5.dp
                    ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = buttonLabel,
                        color = PureWhite,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                }
            }
        }
    }
}

// -----------------------------------------------------------
// Student Raising Hand Silhouette Icon for Welcome Screen (2.png)
// -----------------------------------------------------------
@Composable
private fun StudentRaisingHandIcon(size: androidx.compose.ui.unit.Dp) {
    Canvas(modifier = Modifier.size(size)) {
        val darkColor = Color(0xFF333333)
        val w = this.size.width
        val h = this.size.height

        // Head
        drawCircle(
            color = darkColor,
            radius = w * 0.16f,
            center = Offset(w * 0.60f, h * 0.28f)
        )

        // Torso with arm raised up towards top-left (Exact silhouette from 2.png)
        val bodyPath = Path().apply {
            moveTo(w * 0.44f, h * 0.82f)
            lineTo(w * 0.84f, h * 0.82f)
            lineTo(w * 0.84f, h * 0.68f)
            // Shoulder curve
            cubicTo(
                w * 0.72f, h * 0.60f,
                w * 0.58f, h * 0.56f,
                w * 0.46f, h * 0.58f
            )
            // Arm raising upwards to left
            cubicTo(
                w * 0.38f, h * 0.44f,
                w * 0.28f, h * 0.28f,
                w * 0.25f, h * 0.18f
            )
            // Fingertip curve
            cubicTo(
                w * 0.32f, h * 0.18f,
                w * 0.40f, h * 0.34f,
                w * 0.45f, h * 0.42f
            )
            // Inner arm coming back down to lower torso
            cubicTo(
                w * 0.36f, h * 0.55f,
                w * 0.32f, h * 0.68f,
                w * 0.44f, h * 0.82f
            )
            close()
        }
        drawPath(bodyPath, color = darkColor)
    }
}
