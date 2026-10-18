package com.example.ui.components

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.QrCode2
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.local.DocumentItemEntity
import com.example.ui.theme.CanvasCream
import com.example.ui.theme.DarkBorder
import com.example.ui.theme.LightBlushPink
import com.example.ui.theme.OdishaMagenta
import com.example.ui.theme.OdishaMagentaDark
import com.example.ui.theme.OdishaMagentaDeep
import com.example.ui.theme.PureWhite
import com.example.ui.theme.SoftBlushPink
import com.example.ui.theme.SoftCyan
import com.example.ui.theme.SoftYellow
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.SuccessGreenDark
import com.example.ui.theme.TextDark
import com.example.ui.theme.TextMuted

@Composable
fun DocumentPreviewDialog(
    doc: DocumentItemEntity?,
    onDismiss: () -> Unit
) {
    DocumentViewerDialog(doc = doc, onDismiss = onDismiss)
}

@Composable
fun DocumentViewerDialog(
    doc: DocumentItemEntity?,
    onDismiss: () -> Unit
) {
    if (doc == null) return
    val context = LocalContext.current
    var isNumberMasked by remember { mutableStateOf(true) }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0x77000000))
                .clickable { onDismiss() },
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.94f)
                    .padding(vertical = 20.dp)
                    .clickable(enabled = false) {}
                    .border(1.5.dp, DarkBorder, RoundedCornerShape(24.dp)),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = PureWhite),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Header Bar
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            EduLockerDualCircleLogo(size = 36.dp)
                            Column {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Text(
                                        text = "EduLocker Digital Vault",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = TextDark,
                                        fontSize = 15.sp
                                    )
                                    Icon(
                                        imageVector = Icons.Filled.CheckCircle,
                                        contentDescription = "Verified",
                                        tint = SuccessGreenDark,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                                Text(
                                    text = "Govt. of Odisha • Official Citizen Record",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = OdishaMagentaDark,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 11.sp
                                )
                            }
                        }

                        IconButton(
                            onClick = onDismiss,
                            modifier = Modifier
                                .size(34.dp)
                                .clip(CircleShape)
                                .background(SoftBlushPink.copy(alpha = 0.5f))
                                .border(1.dp, DarkBorder.copy(alpha = 0.2f), CircleShape)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Close,
                                contentDescription = "Close",
                                tint = TextDark,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Realistic Document Representation
                    when (doc.id.lowercase()) {
                        "aadhar" -> AadhaarOfficialCardView(
                            doc = doc,
                            isMasked = isNumberMasked,
                            onToggleMask = { isNumberMasked = !isNumberMasked }
                        )
                        "pan" -> PanOfficialCardView(doc = doc)
                        "x_marksheet" -> MarksheetOfficialCardView(
                            title = "High School Certificate (Class X)",
                            boardName = "Board of Secondary Education, Odisha",
                            doc = doc,
                            percentage = "94.8%",
                            grade = "A1 Distinction"
                        )
                        "xi_marksheet" -> MarksheetOfficialCardView(
                            title = "Higher Secondary Progress (Class XI)",
                            boardName = "Council of Higher Secondary Education, Odisha",
                            doc = doc,
                            percentage = "92.4%",
                            grade = "A1 Distinction"
                        )
                        "aapar" -> ApaarOfficialCardView(doc = doc)
                        else -> GenericGovtDocumentCardView(doc = doc)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Security Cryptographic Seal Box
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, DarkBorder.copy(alpha = 0.15f), RoundedCornerShape(14.dp)),
                        color = CanvasCream,
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Security,
                                        contentDescription = null,
                                        tint = OdishaMagentaDark,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Text(
                                        text = "UIDAI / DigiLocker Cryptographic Seal",
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = TextDark,
                                        fontSize = 11.5.sp
                                    )
                                }

                                Surface(
                                    shape = RoundedCornerShape(6.dp),
                                    color = Color(0xFFDCFCE7)
                                ) {
                                    Text(
                                        text = "VALID ✓",
                                        color = Color(0xFF166534),
                                        fontSize = 9.5.sp,
                                        fontWeight = FontWeight.ExtraBold,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "SHA-256: ${doc.sha256Hash}",
                                style = MaterialTheme.typography.labelSmall,
                                fontFamily = FontFamily.Monospace,
                                fontSize = 9.5.sp,
                                color = TextMuted,
                                lineHeight = 13.sp
                            )

                            Spacer(modifier = Modifier.height(6.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Issued: ${doc.issueDate}",
                                    fontSize = 10.5.sp,
                                    color = TextMuted,
                                    fontWeight = FontWeight.Medium
                                )
                                Text(
                                    text = "Size: ${doc.fileSize} (Encrypted)",
                                    fontSize = 10.5.sp,
                                    color = TextMuted,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    // Action Buttons: Share & Download PDF
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        OutlinedButton(
                            onClick = {
                                Toast.makeText(context, "🔗 Verified link for ${doc.title} copied to clipboard!", Toast.LENGTH_SHORT).show()
                            },
                            modifier = Modifier
                                .weight(1f)
                                .height(50.dp)
                                .border(1.5.dp, DarkBorder, RoundedCornerShape(16.dp)),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Share,
                                contentDescription = null,
                                tint = TextDark,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Share", color = TextDark, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        }

                        Button(
                            onClick = {
                                Toast.makeText(context, "📥 Downloaded ${doc.title} PDF to device storage!", Toast.LENGTH_SHORT).show()
                            },
                            modifier = Modifier
                                .weight(1.3f)
                                .height(50.dp)
                                .border(1.5.dp, DarkBorder, RoundedCornerShape(16.dp)),
                            shape = RoundedCornerShape(16.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = OdishaMagenta)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Download,
                                contentDescription = null,
                                tint = PureWhite,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Download PDF", color = PureWhite, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        }
                    }
                }
            }
        }
    }
}

// -----------------------------------------------------------------------------
// Official Aadhaar Card View
// -----------------------------------------------------------------------------
@Composable
private fun AadhaarOfficialCardView(
    doc: DocumentItemEntity,
    isMasked: Boolean,
    onToggleMask: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.5.dp, DarkBorder, RoundedCornerShape(18.dp)),
        shape = RoundedCornerShape(18.dp),
        color = PureWhite
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            // Aadhaar Top Tricolor Bar with GoI & UIDAI Logos
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.horizontalGradient(
                            listOf(Color(0xFFFF9933), Color(0xFFFFFFFF), Color(0xFF138808))
                        )
                    )
                    .padding(vertical = 4.dp, horizontal = 12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "भारत सरकार",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF0F172A)
                        )
                        Text(
                            text = "Government of India",
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF0F172A)
                        )
                    }

                    // UIDAI Sunburst Emblem Icon
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFEF4444)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "☀️",
                            fontSize = 12.sp
                        )
                    }
                }
            }

            // Aadhaar Body Content
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFFFFBEB))
                    .padding(14.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    // Photo Placeholder
                    Box(
                        modifier = Modifier
                            .size(width = 68.dp, height = 80.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFFE2E8F0))
                            .border(1.dp, Color(0xFF94A3B8), RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "👨‍🎓", fontSize = 28.sp)
                            Text(text = "PHOTO", fontSize = 8.sp, color = TextMuted, fontWeight = FontWeight.Bold)
                        }
                    }

                    // Personal Information
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        Text(
                            text = "ଲକ୍ଷ୍ମୀକାନ୍ତ ନାୟକ",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextDark
                        )
                        Text(
                            text = "Laxmikant Nayak",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = TextDark
                        )
                        Text(
                            text = "ଜନ୍ମ ତାରିଖ / DOB: 14/08/2007",
                            fontSize = 11.sp,
                            color = Color(0xFF334155),
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = "ଲିଙ୍ଗ / Gender: ପୁରୁଷ / Male",
                            fontSize = 11.sp,
                            color = Color(0xFF334155),
                            fontWeight = FontWeight.Medium
                        )
                    }

                    // QR Code
                    Icon(
                        imageVector = Icons.Filled.QrCode2,
                        contentDescription = "QR Code",
                        modifier = Modifier.size(54.dp),
                        tint = TextDark
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))
                HorizontalDivider(color = Color(0xFFCBD5E1), thickness = 1.dp)
                Spacer(modifier = Modifier.height(10.dp))

                // Aadhaar Number with Mask toggle
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (isMasked) "XXXX  XXXX  4829" else "5482  9104  4829",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold,
                        fontFamily = FontFamily.Monospace,
                        color = Color(0xFFDC2626),
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(
                        onClick = onToggleMask,
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            imageVector = if (isMasked) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                            contentDescription = "Toggle Visibility",
                            tint = Color(0xFF475569),
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "ମୋ ଆଧାର, ମୋ ପରିଚୟ  •  मेरा आधार, मेरी पहचान",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E293B),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

// -----------------------------------------------------------------------------
// Official PAN Card View
// -----------------------------------------------------------------------------
@Composable
private fun PanOfficialCardView(doc: DocumentItemEntity) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.5.dp, DarkBorder, RoundedCornerShape(18.dp)),
        shape = RoundedCornerShape(18.dp),
        color = Color(0xFFF0FDF4)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Header Bar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF0284C7))
                    .padding(horizontal = 14.dp, vertical = 6.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "आयकर विभाग / INCOME TAX DEPARTMENT",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = PureWhite
                        )
                        Text(
                            text = "भारत सरकार / GOVT. OF INDIA",
                            fontSize = 8.5.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFFBAE6FD)
                        )
                    }
                    Text(text = "🇮🇳", fontSize = 16.sp)
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
                        Text(
                            text = "Permanent Account Number Card",
                            fontSize = 9.sp,
                            color = TextMuted,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "ABCPN8742K",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.ExtraBold,
                            fontFamily = FontFamily.Monospace,
                            color = Color(0xFF0F172A)
                        )
                        Spacer(modifier = Modifier.height(4.dp))

                        Text(text = "Name", fontSize = 9.sp, color = TextMuted, fontWeight = FontWeight.Bold)
                        Text(text = "LAXMIKANT NAYAK", fontSize = 12.5.sp, fontWeight = FontWeight.Bold, color = TextDark)

                        Text(text = "Father's Name", fontSize = 9.sp, color = TextMuted, fontWeight = FontWeight.Bold)
                        Text(text = "SACHIDANANDA NAYAK", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = TextDark)

                        Text(text = "Date of Birth", fontSize = 9.sp, color = TextMuted, fontWeight = FontWeight.Bold)
                        Text(text = "14/08/2007", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextDark)
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(width = 54.dp, height = 64.dp)
                                .clip(RoundedCornerShape(6.dp))
                                .background(Color(0xFFE2E8F0))
                                .border(1.dp, Color(0xFF94A3B8), RoundedCornerShape(6.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "👨‍🎓", fontSize = 24.sp)
                        }

                        // Digital Signature Box
                        Box(
                            modifier = Modifier
                                .width(64.dp)
                                .height(22.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(PureWhite)
                                .border(0.8.dp, Color(0xFF94A3B8), RoundedCornerShape(4.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "L. Nayak",
                                fontSize = 10.sp,
                                fontFamily = FontFamily.Cursive,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF1E293B)
                            )
                        }
                    }
                }
            }
        }
    }
}

// -----------------------------------------------------------------------------
// Official Marksheet Card View (Class X / XI)
// -----------------------------------------------------------------------------
@Composable
private fun MarksheetOfficialCardView(
    title: String,
    boardName: String,
    doc: DocumentItemEntity,
    percentage: String,
    grade: String
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.5.dp, DarkBorder, RoundedCornerShape(18.dp)),
        shape = RoundedCornerShape(18.dp),
        color = PureWhite
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF1E3A8A))
                    .padding(horizontal = 14.dp, vertical = 8.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = boardName.uppercase(),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF93C5FD),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = title,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = PureWhite,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(text = "Candidate Name", fontSize = 9.sp, color = TextMuted, fontWeight = FontWeight.Bold)
                        Text(text = "LAXMIKANT NAYAK", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextDark)
                        Text(text = "School: Govt. Higher Sec School, Phulbani", fontSize = 10.5.sp, color = TextMuted)
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text(text = "Roll Number", fontSize = 9.sp, color = TextMuted, fontWeight = FontWeight.Bold)
                        Text(text = "2408B1098", fontSize = 13.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace, color = TextDark)
                        Text(text = "Year: 2024", fontSize = 10.5.sp, color = TextMuted)
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Subject Marks Summary Table
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    color = Color(0xFFF8FAFC),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0))
                ) {
                    Column(modifier = Modifier.padding(8.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(text = "Subject", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TextMuted)
                            Text(text = "Marks Obtained", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TextMuted)
                        }
                        HorizontalDivider(color = Color(0xFFE2E8F0))
                        MarksRow(subject = "First Language (Odia)", marks = "96 / 100")
                        MarksRow(subject = "Second Language (English)", marks = "92 / 100")
                        MarksRow(subject = "Mathematics", marks = "98 / 100")
                        MarksRow(subject = "Science", marks = "95 / 100")
                        MarksRow(subject = "Social Science", marks = "93 / 100")
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(text = "Result Status", fontSize = 9.sp, color = TextMuted, fontWeight = FontWeight.Bold)
                        Text(text = "PASSED ($grade)", fontSize = 12.sp, fontWeight = FontWeight.ExtraBold, color = SuccessGreenDark)
                    }

                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Color(0xFFEFF6FF)
                    ) {
                        Text(
                            text = "Score: $percentage",
                            color = Color(0xFF1D4ED8),
                            fontSize = 13.sp,
                            fontWeight = FontWeight.ExtraBold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun MarksRow(subject: String, marks: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = subject, fontSize = 10.5.sp, color = TextDark)
        Text(text = marks, fontSize = 10.5.sp, fontWeight = FontWeight.Bold, color = TextDark)
    }
}

// -----------------------------------------------------------------------------
// Official APAAR ID Card View
// -----------------------------------------------------------------------------
@Composable
private fun ApaarOfficialCardView(doc: DocumentItemEntity) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.5.dp, DarkBorder, RoundedCornerShape(18.dp)),
        shape = RoundedCornerShape(18.dp),
        color = Color(0xFFF0F9FF)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF0369A1))
                    .padding(horizontal = 14.dp, vertical = 8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "MINISTRY OF EDUCATION • GOVT. OF INDIA",
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFBAE6FD)
                        )
                        Text(
                            text = "Automated Permanent Academic Account Registry",
                            fontSize = 11.5.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = PureWhite
                        )
                    }
                    Text(text = "🇮🇳", fontSize = 16.sp)
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(text = "APAAR ID (12-Digit One Nation One Student ID)", fontSize = 9.sp, color = TextMuted, fontWeight = FontWeight.Bold)
                        Text(
                            text = "7829-4501-9238",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.ExtraBold,
                            fontFamily = FontFamily.Monospace,
                            color = Color(0xFF0284C7)
                        )
                    }
                    Icon(
                        imageVector = Icons.Filled.QrCode2,
                        contentDescription = "QR Code",
                        modifier = Modifier.size(48.dp),
                        tint = TextDark
                    )
                }

                HorizontalDivider(color = Color(0xFFBAE6FD))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(text = "Student Name", fontSize = 9.sp, color = TextMuted, fontWeight = FontWeight.Bold)
                        Text(text = "LAXMIKANT NAYAK", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextDark)
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text(text = "U-DISE+ School Code", fontSize = 9.sp, color = TextMuted, fontWeight = FontWeight.Bold)
                        Text(text = "21210800101", fontSize = 12.5.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace, color = TextDark)
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(text = "Academic Level", fontSize = 9.sp, color = TextMuted, fontWeight = FontWeight.Bold)
                        Text(text = "Class 12th (Senior Secondary)", fontSize = 11.5.sp, fontWeight = FontWeight.Medium, color = TextDark)
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text(text = "Academic Bank of Credits (ABC)", fontSize = 9.sp, color = TextMuted, fontWeight = FontWeight.Bold)
                        Text(text = "240 Credits Verified ✓", fontSize = 11.5.sp, fontWeight = FontWeight.Bold, color = SuccessGreenDark)
                    }
                }
            }
        }
    }
}

// -----------------------------------------------------------------------------
// Generic Govt. Document View
// -----------------------------------------------------------------------------
@Composable
private fun GenericGovtDocumentCardView(doc: DocumentItemEntity) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.5.dp, DarkBorder, RoundedCornerShape(18.dp)),
        shape = RoundedCornerShape(18.dp),
        color = PureWhite
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = doc.title,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = TextDark
                    )
                    Text(
                        text = doc.subtitle,
                        fontSize = 12.sp,
                        color = TextMuted
                    )
                }
                Icon(
                    imageVector = Icons.Filled.AccountBalance,
                    contentDescription = null,
                    tint = OdishaMagenta,
                    modifier = Modifier.size(28.dp)
                )
            }

            HorizontalDivider(color = Color(0xFFE2E8F0))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(text = "Document Number", fontSize = 9.sp, color = TextMuted, fontWeight = FontWeight.Bold)
                    Text(text = doc.documentNo, fontSize = 13.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace, color = TextDark)
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text(text = "Issued By", fontSize = 9.sp, color = TextMuted, fontWeight = FontWeight.Bold)
                    Text(text = doc.issuedBy, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = TextDark)
                }
            }
        }
    }
}
