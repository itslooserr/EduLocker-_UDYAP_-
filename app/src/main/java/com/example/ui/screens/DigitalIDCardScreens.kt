package com.example.ui.screens

import android.widget.Toast
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import java.io.File
import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.CanvasCream
import com.example.ui.theme.DarkBorder
import com.example.ui.theme.OdishaMagenta
import com.example.ui.theme.PureWhite
import com.example.ui.theme.TextDark
import com.example.ui.theme.TextMuted
import com.example.ui.viewmodel.EduLockerViewModel

// =========================================================================
// SCREENS 38 & 39: Digital Student ID Card (Exact Replica of 38.png & 39.png)
// =========================================================================
@Composable
fun DigitalIDCardScreen(
    viewModel: EduLockerViewModel,
    modifier: Modifier = Modifier
) {
    val profile by viewModel.studentProfile.collectAsState()
    var isFlipped by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val flipAngle by animateFloatAsState(
        targetValue = if (isFlipped) 180f else 0f,
        animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing),
        label = "cardFlip"
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(CanvasCream)
            .padding(horizontal = 22.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(48.dp))

            // 3D FLIPPABLE ID CARD
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(520.dp)
                    .graphicsLayer {
                        rotationY = flipAngle
                        cameraDistance = 14f * density
                    },
                contentAlignment = Alignment.Center
            ) {
                if (flipAngle <= 90f) {
                    // SCREEN 38: FRONT SIDE (38.png)
                    IdCardFrontView(
                        profileName = profile?.fullName ?: "Laxmikant Nayak",
                        eduLockerId = profile?.eduLockerId ?: "ODIST-KMD-PLBM-LNO01",
                        userId = profile?.userId ?: "01009864",
                        parentName = profile?.parentName ?: "Sachidananda Nayak",
                        bloodGroup = profile?.bloodGroup ?: "B +ve",
                        address = profile?.address ?: "Phulbani, Kandhamal, Odisha",
                        pinCode = profile?.pinCode ?: "762001",
                        photoUri = profile?.photoUri
                    )
                } else {
                    // SCREEN 39: BACK SIDE (39.png)
                    Box(
                        modifier = Modifier.graphicsLayer { rotationY = 180f }
                    ) {
                        IdCardBackView()
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // SEE BACK / SEE FRONT TOGGLE BUTTON (Exact from 38.png & 39.png)
            Button(
                onClick = { isFlipped = !isFlipped },
                modifier = Modifier
                    .height(46.dp)
                    .fillMaxWidth(0.48f)
                    .clip(RoundedCornerShape(12.dp)),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = OdishaMagenta)
            ) {
                Text(
                    text = if (isFlipped) "See front" else "See back",
                    color = PureWhite,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.height(84.dp))
        }
    }
}

// -----------------------------------------------------------
// FRONT SIDE OF ID CARD (Exact replica of 38.png)
// -----------------------------------------------------------
@Composable
private fun IdCardFrontView(
    profileName: String,
    eduLockerId: String,
    userId: String,
    parentName: String,
    bloodGroup: String,
    address: String,
    pinCode: String,
    photoUri: String? = null
) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(22.dp)),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFDE8E8)) // Soft pink background
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Top Magenta Wave/Arch Banner
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(170.dp)
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val path = Path().apply {
                        moveTo(0f, 0f)
                        lineTo(size.width, 0f)
                        lineTo(size.width, size.height * 0.72f)
                        cubicTo(
                            size.width * 0.7f, size.height * 0.65f,
                            size.width * 0.3f, size.height * 1.05f,
                            0f, size.height * 0.8f
                        )
                        close()
                    }
                    drawPath(path, color = Color(0xFFD81B60))
                }

                // School Emblem & Name
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, top = 16.dp, end = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    DigitalIdSchoolEmblemLogo(size = 46.dp)

                    Column {
                        Text(
                            text = "Govt. Higher Secondary School",
                            color = PureWhite,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.5.sp
                        )
                        Text(
                            text = "Phulbani, Kandhamal, Odisha",
                            color = PureWhite.copy(alpha = 0.9f),
                            style = MaterialTheme.typography.labelSmall,
                            fontSize = 11.5.sp
                        )
                    }
                }

                // Overlapping circular student avatar
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .size(92.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF1E293B))
                        .border(3.dp, PureWhite, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    if (!photoUri.isNullOrEmpty() && File(photoUri).exists()) {
                        AsyncImage(
                            model = File(photoUri),
                            contentDescription = "Student Photo",
                            modifier = Modifier.fillMaxSize().clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Text("LN", color = PureWhite, fontWeight = FontWeight.ExtraBold, fontSize = 28.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Student Information Block (Exact from 38.png)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 20.dp)
            ) {
                // Background faint emblem watermark
                Canvas(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .size(160.dp)
                ) {
                    drawCircle(
                        color = Color(0xFFF43F5E).copy(alpha = 0.08f),
                        radius = size.width / 2,
                        style = Stroke(width = 2.dp.toPx())
                    )
                }

                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = profileName,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF1E293B),
                            fontSize = 20.sp
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "EduLocker ID: $eduLockerId",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFF64748B),
                            fontSize = 13.sp
                        )
                        Text(
                            text = "User ID: $userId",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFF64748B),
                            fontSize = 13.sp
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                            IdFieldText("S/D. of : $parentName")
                            IdFieldText("D.O.B : DD . MM . YYYY")
                            IdFieldText("Blood Group : $bloodGroup")
                            IdFieldText("Address : $address")
                            IdFieldText("Pin Code : $pinCode")
                            IdFieldText("Class : 12ᵗʰ Science")
                            IdFieldText("eKYC Status : Verified")
                        }
                    }

                    // Bottom Row: User ID & EduLocker Watermark
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "User ID: $userId",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color(0xFF64748B),
                            fontSize = 11.5.sp
                        )

                        Text(
                            text = "EduLocker",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF475569),
                            fontSize = 18.sp
                        )
                    }
                }
            }
        }
    }
}

// -----------------------------------------------------------
// BACK SIDE OF ID CARD (Exact replica of 39.png)
// -----------------------------------------------------------
@Composable
private fun IdCardBackView() {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(22.dp)),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFD81B60)) // Solid Magenta
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Main Content Area with faint globe/hands watermark
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(20.dp)
            ) {
                // Background Watermark Outline
                Canvas(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(240.dp)
                ) {
                    drawCircle(
                        color = PureWhite.copy(alpha = 0.08f),
                        radius = size.width / 2,
                        style = Stroke(width = 3.dp.toPx())
                    )
                }

                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(10.dp))

                    // Large College Emblem
                    DigitalIdSchoolEmblemLogo(size = 76.dp)

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Govt. Higher Secondary\nSchool, Phulbani",
                        color = PureWhite,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "Valid till : 31.May.2027",
                        color = PureWhite,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium,
                        fontSize = 13.5.sp
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Legal / Non-transferable Declaration (Exact copy from 39.png)
                    Text(
                        text = "This Card testified to the status of the holder of being a student of govt. Higher Secondary School, Phulbani, Kandhamal",
                        color = PureWhite.copy(alpha = 0.95f),
                        style = MaterialTheme.typography.bodySmall,
                        fontSize = 11.5.sp,
                        lineHeight = 15.sp,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "It is not Transferable and it should be produced at the time of receipt of library books, monetary helps, examinations and any such other times when required to do so by the Principal",
                        color = PureWhite.copy(alpha = 0.95f),
                        style = MaterialTheme.typography.bodySmall,
                        fontSize = 11.sp,
                        lineHeight = 15.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }

            // Bottom Soft Pink Strip (Exact from 39.png)
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(68.dp),
                color = Color(0xFFFDE8E8)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 18.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "School ID : 3458XX",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF475569),
                        fontSize = 12.sp
                    )

                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = "Pinki Pallavika Biswasi",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1E293B),
                            fontSize = 13.sp
                        )
                        Text(
                            text = "Principal, GHSS Phulbani",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color(0xFF64748B),
                            fontSize = 11.sp
                        )
                    }
                }
            }
        }
    }
}

// -----------------------------------------------------------
// School Emblem Logo Component
// -----------------------------------------------------------
@Composable
private fun DigitalIdSchoolEmblemLogo(size: androidx.compose.ui.unit.Dp, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(PureWhite)
            .border(1.5.dp, Color(0xFFD81B60), CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(color = Color(0xFFFEF3C7), radius = size.toPx() * 0.44f)
            drawCircle(color = Color(0xFFD81B60), radius = size.toPx() * 0.44f, style = Stroke(width = 1.5.dp.toPx()))
            // Inner quadrant colors
            drawArc(
                color = Color(0xFF60A5FA),
                startAngle = 180f,
                sweepAngle = 90f,
                useCenter = true
            )
            drawArc(
                color = Color(0xFFF87171),
                startAngle = 270f,
                sweepAngle = 90f,
                useCenter = true
            )
            drawArc(
                color = Color(0xFF4ADE80),
                startAngle = 90f,
                sweepAngle = 90f,
                useCenter = true
            )
        }
        Text(
            text = "1960",
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.ExtraBold,
            color = Color(0xFF1E293B),
            fontSize = (size.value * 0.18f).sp
        )
    }
}

@Composable
private fun IdFieldText(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodySmall,
        color = Color(0xFF334155),
        fontSize = 12.sp
    )
}
