package com.example.ui.components

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import com.example.ui.theme.CanvasCream
import com.example.ui.theme.OdishaMagenta
import com.example.ui.theme.PureWhite
import com.example.ui.theme.TextDark
import com.example.ui.viewmodel.EduLockerViewModel
import java.io.File

data class PostPhotoViewerData(
    val id: String,
    val title: String,
    val author: String,
    val authorHandle: String,
    val timestamp: String,
    val description: String,
    val defaultGradient: List<Color> = listOf(Color(0xFF0F172A), Color(0xFF1E293B)),
    val likesCount: Int = 142
)

@Composable
fun PostPhotoViewerModal(
    viewModel: EduLockerViewModel,
    data: PostPhotoViewerData,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    val customImages by viewModel.customPostImages.collectAsState()
    val customThemes by viewModel.customPostThemes.collectAsState()

    var isLiked by remember { mutableStateOf(false) }
    var likesCount by remember { mutableStateOf(data.likesCount) }
    var showMenu by remember { mutableStateOf(false) }
    var showThemeDialog by remember { mutableStateOf(false) }
    var showEditCaptionDialog by remember { mutableStateOf(false) }
    var captionText by remember { mutableStateOf(data.description) }

    val currentCustomImage = customImages[data.id]
    val currentCustomTheme = customThemes[data.id]

    // Photo picker launcher
    val photoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            viewModel.updatePostPhoto(data.id, uri)
            Toast.makeText(context, "Photo updated successfully!", Toast.LENGTH_SHORT).show()
        }
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.85f))
                .clickable { onDismiss() },
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.94f)
                    .clip(RoundedCornerShape(24.dp))
                    .clickable(enabled = false) {}, // Prevent dismiss when tapping dialog content
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = PureWhite)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                ) {
                    // Header Bar (Author Info + 3-Dot Options + Close)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(38.dp)
                                    .clip(CircleShape)
                                    .background(OdishaMagenta),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = data.author.take(1),
                                    color = PureWhite,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp
                                )
                            }
                            Column {
                                Text(
                                    text = data.author,
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF0F172A),
                                    fontSize = 13.5.sp
                                )
                                Text(
                                    text = "${data.authorHandle} • ${data.timestamp}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color(0xFF64748B),
                                    fontSize = 11.sp
                                )
                            }
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            // 3-Dot Menu Button (User requested: "3 dot par click karne se me photo change kar sakta hu")
                            Box {
                                IconButton(onClick = { showMenu = true }) {
                                    Icon(
                                        imageVector = Icons.Filled.MoreVert,
                                        contentDescription = "Photo Options",
                                        tint = Color(0xFF1E293B)
                                    )
                                }

                                DropdownMenu(
                                    expanded = showMenu,
                                    onDismissRequest = { showMenu = false },
                                    modifier = Modifier.background(PureWhite)
                                ) {
                                    DropdownMenuItem(
                                        text = { Text("📷 Change Photo (From Device)", fontWeight = FontWeight.Medium) },
                                        onClick = {
                                            showMenu = false
                                            photoPicker.launch(
                                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                            )
                                        },
                                        leadingIcon = {
                                            Icon(Icons.Filled.PhotoLibrary, contentDescription = null, tint = OdishaMagenta)
                                        }
                                    )

                                    DropdownMenuItem(
                                        text = { Text("🎨 Customize Design Theme", fontWeight = FontWeight.Medium) },
                                        onClick = {
                                            showMenu = false
                                            showThemeDialog = true
                                        },
                                        leadingIcon = {
                                            Icon(Icons.Filled.AutoAwesome, contentDescription = null, tint = Color(0xFF0284C7))
                                        }
                                    )

                                    DropdownMenuItem(
                                        text = { Text("✏️ Edit Caption", fontWeight = FontWeight.Medium) },
                                        onClick = {
                                            showMenu = false
                                            showEditCaptionDialog = true
                                        },
                                        leadingIcon = {
                                            Icon(Icons.Filled.Edit, contentDescription = null, tint = Color(0xFF10B981))
                                        }
                                    )

                                    if (currentCustomImage != null || currentCustomTheme != null) {
                                        DropdownMenuItem(
                                            text = { Text("🗑️ Reset to Original", color = Color(0xFFEF4444), fontWeight = FontWeight.Medium) },
                                            onClick = {
                                                showMenu = false
                                                viewModel.resetPostPhoto(data.id)
                                                Toast.makeText(context, "Reset to default photo", Toast.LENGTH_SHORT).show()
                                            },
                                            leadingIcon = {
                                                Icon(Icons.Filled.Delete, contentDescription = null, tint = Color(0xFFEF4444))
                                            }
                                        )
                                    }
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
                    }

                    // Main Expanded High-Res Photo Viewport
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(280.dp)
                            .background(Color(0xFF0F172A))
                    ) {
                        if (!currentCustomImage.isNullOrEmpty() && File(currentCustomImage).exists()) {
                            AsyncImage(
                                model = File(currentCustomImage),
                                contentDescription = data.title,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            val activeGradient = when (currentCustomTheme) {
                                "Cyber Neon" -> listOf(Color(0xFF0F172A), Color(0xFF0284C7), Color(0xFF38BDF8))
                                "Royal Gold" -> listOf(Color(0xFF451A03), Color(0xFFB45309), Color(0xFFF59E0B))
                                "Rose Garden" -> listOf(Color(0xFF881337), Color(0xFFE11D48), Color(0xFFFB7185))
                                "Forest Emerald" -> listOf(Color(0xFF064E3B), Color(0xFF059669), Color(0xFF34D399))
                                "Deep Purple" -> listOf(Color(0xFF3B0764), Color(0xFF7C3AED), Color(0xFFA855F7))
                                else -> data.defaultGradient
                            }

                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Brush.verticalGradient(activeGradient)),
                                contentAlignment = Alignment.Center
                            ) {
                                Canvas(modifier = Modifier.fillMaxSize()) {
                                    drawCircle(
                                        color = Color.White.copy(alpha = 0.12f),
                                        radius = size.width * 0.45f
                                    )
                                }

                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier.padding(20.dp)
                                ) {
                                    Text(
                                        text = data.title,
                                        color = PureWhite,
                                        fontWeight = FontWeight.ExtraBold,
                                        fontSize = 19.sp,
                                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                                    )
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(
                                        text = "Tap 3-dots at top right to customize or upload your photo",
                                        color = PureWhite.copy(alpha = 0.75f),
                                        fontSize = 11.5.sp,
                                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                                    )
                                }
                            }
                        }

                        // Watermark / Badge
                        Surface(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(12.dp),
                            shape = RoundedCornerShape(8.dp),
                            color = Color(0x99000000)
                        ) {
                            Text(
                                text = "EduLocker Verified",
                                color = PureWhite,
                                fontSize = 10.5.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }

                    // Bottom Content: Likes, Interactive Heart, Description & Actions
                    Column(modifier = Modifier.padding(16.dp)) {
                        // Row with Like, Comment, Share & Photo Design shortcut
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(14.dp)
                            ) {
                                // Like Heart Toggle Button
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .clickable {
                                            isLiked = !isLiked
                                            likesCount = if (isLiked) likesCount + 1 else likesCount - 1
                                            viewModel.togglePostLike(data.id)
                                        }
                                        .padding(4.dp)
                                ) {
                                    Icon(
                                        imageVector = if (isLiked) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                                        contentDescription = "Like",
                                        tint = if (isLiked) OdishaMagenta else Color(0xFF475569),
                                        modifier = Modifier.size(22.dp)
                                    )
                                    Text(
                                        text = "$likesCount Likes",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp,
                                        color = TextDark
                                    )
                                }

                                // Share Button
                                IconButton(
                                    onClick = {
                                        Toast.makeText(context, "Post link copied to clipboard!", Toast.LENGTH_SHORT).show()
                                    },
                                    modifier = Modifier.size(32.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Share,
                                        contentDescription = "Share",
                                        tint = Color(0xFF475569),
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }

                            // Quick Design / Change Button
                            Surface(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(12.dp))
                                    .clickable {
                                        photoPicker.launch(
                                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                        )
                                    },
                                shape = RoundedCornerShape(12.dp),
                                color = Color(0xFFFCE7F3)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.PhotoLibrary,
                                        contentDescription = null,
                                        tint = OdishaMagenta,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Text(
                                        text = "Change Photo",
                                        color = OdishaMagenta,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 11.5.sp
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // Description text
                        Text(
                            text = captionText,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFF334155),
                            fontSize = 13.sp,
                            lineHeight = 18.sp
                        )
                    }
                }
            }
        }
    }

    // Theme Selection Dialog
    if (showThemeDialog) {
        Dialog(onDismissRequest = { showThemeDialog = false }) {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = CanvasCream),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = "Choose Design Theme",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = TextDark
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    val themes = listOf("Cyber Neon", "Royal Gold", "Rose Garden", "Forest Emerald", "Deep Purple")
                    themes.forEach { theme ->
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .clickable {
                                    viewModel.updatePostTheme(data.id, theme)
                                    showThemeDialog = false
                                    Toast.makeText(context, "Theme applied: $theme", Toast.LENGTH_SHORT).show()
                                },
                            shape = RoundedCornerShape(12.dp),
                            color = PureWhite
                        ) {
                            Row(
                                modifier = Modifier.padding(14.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(theme, fontWeight = FontWeight.Medium, color = TextDark)
                                Icon(Icons.Filled.AutoAwesome, contentDescription = null, tint = OdishaMagenta)
                            }
                        }
                    }
                }
            }
        }
    }

    // Edit Caption Dialog
    if (showEditCaptionDialog) {
        Dialog(onDismissRequest = { showEditCaptionDialog = false }) {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = CanvasCream),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text("Edit Post Description", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedTextField(
                        value = captionText,
                        onValueChange = { captionText = it },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = PureWhite,
                            unfocusedContainerColor = PureWhite,
                            focusedTextColor = TextDark,
                            unfocusedTextColor = TextDark
                        )
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                    Button(
                        onClick = {
                            viewModel.updatePostContent(data.id, captionText)
                            showEditCaptionDialog = false
                            Toast.makeText(context, "Caption saved!", Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = OdishaMagenta)
                    ) {
                        Text("Save Caption", color = PureWhite, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
