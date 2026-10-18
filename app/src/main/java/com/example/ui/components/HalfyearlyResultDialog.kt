package com.example.ui.components

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.ui.theme.OdishaMagenta
import com.example.ui.theme.PureWhite

data class SubjectScore(
    val subject: String,
    val theoryMarks: String,
    val practicalMarks: String,
    val totalMarks: String,
    val maxMarks: String,
    val grade: String,
    val gradeColor: Color
)

@Composable
fun HalfyearlyResultDialog(
    onDismiss: () -> Unit
) {
    val context = LocalContext.current

    val subjects = listOf(
        SubjectScore("Physics", "64/70", "29/30", "93", "100", "A1", Color(0xFF059669)),
        SubjectScore("Chemistry", "58/70", "28/30", "86", "100", "A2", Color(0xFF0284C7)),
        SubjectScore("Mathematics", "94/100", "-", "94", "100", "A1", Color(0xFF059669)),
        SubjectScore("Biology / IT", "62/70", "29/30", "91", "100", "A1", Color(0xFF059669)),
        SubjectScore("English", "88/100", "-", "88", "100", "A2", Color(0xFF0284C7)),
        SubjectScore("Odia (MIL)", "94/100", "-", "94", "100", "A1", Color(0xFF059669))
    )

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.94f)
                .clip(RoundedCornerShape(24.dp)),
            shape = RoundedCornerShape(24.dp),
            color = PureWhite
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                // Header with close button
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFCCFBF1)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.School,
                                contentDescription = null,
                                tint = Color(0xFF0F766E),
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Column {
                            Text(
                                text = "Halfyearly Report Card",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF111827)
                            )
                            Text(
                                text = "Academic Session 2025-2026",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFF64748B),
                                fontSize = 11.5.sp
                            )
                        }
                    }
                    IconButton(onClick = onDismiss) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "Close",
                            tint = Color(0xFF64748B)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Student details summary card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF8FAFC))
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text("Student Name", fontSize = 11.sp, color = Color(0xFF64748B))
                                Text("Laxmikant Nayak", fontWeight = FontWeight.Bold, fontSize = 13.5.sp, color = Color(0xFF1E293B))
                            }
                            Column(horizontalAlignment = Alignment.End) {
                                Text("Class & Stream", fontSize = 11.sp, color = Color(0xFF64748B))
                                Text("12th Science", fontWeight = FontWeight.Bold, fontSize = 13.5.sp, color = Color(0xFF1E293B))
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text("Institution", fontSize = 11.sp, color = Color(0xFF64748B))
                                Text("Govt. Higher Sec. School, Phulbani", fontWeight = FontWeight.Medium, fontSize = 12.sp, color = Color(0xFF1E293B))
                            }
                            Column(horizontalAlignment = Alignment.End) {
                                Text("College Rank", fontSize = 11.sp, color = Color(0xFF64748B))
                                Text("Rank 3 (Top 1%)", fontWeight = FontWeight.Bold, fontSize = 12.5.sp, color = OdishaMagenta)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Score Overview Banner
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    color = Color(0xFFF0FDF4)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text("Total Marks Obtained", fontSize = 11.5.sp, color = Color(0xFF15803D))
                            Text("546 / 600", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color(0xFF14532D))
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text("Percentage", fontSize = 11.5.sp, color = Color(0xFF15803D))
                            Text("91.0 % (Grade A1)", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFF15803D))
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Subject breakdown Table Header
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFF1F5F9), RoundedCornerShape(8.dp))
                        .padding(horizontal = 10.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Subject", fontWeight = FontWeight.Bold, fontSize = 11.5.sp, color = Color(0xFF334155), modifier = Modifier.weight(1.3f))
                    Text("Theory", fontWeight = FontWeight.Bold, fontSize = 11.5.sp, color = Color(0xFF334155), modifier = Modifier.weight(0.9f))
                    Text("Total", fontWeight = FontWeight.Bold, fontSize = 11.5.sp, color = Color(0xFF334155), modifier = Modifier.weight(0.8f))
                    Text("Grade", fontWeight = FontWeight.Bold, fontSize = 11.5.sp, color = Color(0xFF334155), modifier = Modifier.weight(0.6f))
                }

                Spacer(modifier = Modifier.height(6.dp))

                // Subject Rows
                subjects.forEach { item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(item.subject, fontWeight = FontWeight.Medium, fontSize = 12.sp, color = Color(0xFF1E293B), modifier = Modifier.weight(1.3f))
                        Text(item.theoryMarks, fontSize = 11.5.sp, color = Color(0xFF64748B), modifier = Modifier.weight(0.9f))
                        Text("${item.totalMarks}/100", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Color(0xFF0F172A), modifier = Modifier.weight(0.8f))
                        Box(
                            modifier = Modifier
                                .weight(0.6f)
                                .clip(RoundedCornerShape(6.dp))
                                .background(item.gradeColor.copy(alpha = 0.12f))
                                .padding(vertical = 2.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(item.grade, fontWeight = FontWeight.Bold, fontSize = 11.5.sp, color = item.gradeColor)
                        }
                    }
                    Divider(color = Color(0xFFF1F5F9), thickness = 1.dp)
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Verification Badge
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFEFF6FF), RoundedCornerShape(10.dp))
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.CheckCircle,
                        contentDescription = null,
                        tint = Color(0xFF2563EB),
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = "Digitally Verified by Council of Higher Secondary Education (CHSE), Odisha",
                        fontSize = 11.sp,
                        color = Color(0xFF1E40AF),
                        lineHeight = 14.sp
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Download & Share Actions
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedButton(
                        onClick = {
                            Toast.makeText(context, "Downloading verified marksheet PDF...", Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(Icons.Filled.Download, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Download PDF", fontSize = 12.sp)
                    }

                    Button(
                        onClick = {
                            Toast.makeText(context, "Report card shared with parents via SMS/WhatsApp", Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = OdishaMagenta)
                    ) {
                        Icon(Icons.Filled.Share, contentDescription = null, modifier = Modifier.size(16.dp), tint = PureWhite)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Share Result", fontSize = 12.sp, color = PureWhite)
                    }
                }
            }
        }
    }
}
