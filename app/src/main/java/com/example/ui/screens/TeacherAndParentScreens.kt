package com.example.ui.screens

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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.example.ui.theme.CanvasCream
import com.example.ui.theme.OdishaMagenta
import com.example.ui.theme.PureWhite
import com.example.ui.theme.TextDark
import com.example.ui.viewmodel.EduLockerViewModel
import java.io.File

// =========================================================================
// SCREEN 40: Teacher / Faculty Profile (Exact Replica of 40.png)
// =========================================================================
@Composable
fun TeacherProfileScreen(
    viewModel: EduLockerViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val customAssets by viewModel.customProfileAssets.collectAsState()

    var showPhotoDialog by remember { mutableStateOf(false) }
    var showBannerDialog by remember { mutableStateOf(false) }

    val teacherPhotoUri = customAssets["avatar_teacher"]
    val teacherBannerUri = customAssets["banner_teacher"]

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            viewModel.uploadProfileAsset("avatar_teacher", uri)
            Toast.makeText(context, "Teacher photo updated!", Toast.LENGTH_SHORT).show()
        }
    }

    val bannerPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            viewModel.uploadProfileAsset("banner_teacher", uri)
            Toast.makeText(context, "Teacher banner updated!", Toast.LENGTH_SHORT).show()
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(CanvasCream)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // 1. TOP BLACKBOARD BANNER WITH PHYSICS / MATH FORMULAS (40.png)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                if (!teacherBannerUri.isNullOrEmpty() && File(teacherBannerUri).exists()) {
                    AsyncImage(
                        model = File(teacherBannerUri),
                        contentDescription = "Teacher Banner",
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable { showBannerDialog = true },
                        contentScale = ContentScale.Crop
                    )
                } else {
                    // Chalkboard with authentic chalk writings (sin 300°, cos 30°, P_N/pi*r^2, formulas)
                    BlackboardCanvas(
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable { showBannerDialog = true }
                    )
                }

                // Overlapping Circular Teacher Photo on bottom-left (40.png)
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(start = 20.dp, bottom = 0.dp)
                        .offset(y = 36.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(86.dp)
                            .clip(CircleShape)
                            .border(3.5.dp, PureWhite, CircleShape)
                            .background(Color(0xFF1E293B))
                            .clickable { showPhotoDialog = true },
                        contentAlignment = Alignment.Center
                    ) {
                        if (!teacherPhotoUri.isNullOrEmpty() && File(teacherPhotoUri).exists()) {
                            AsyncImage(
                                model = File(teacherPhotoUri),
                                contentDescription = "Teacher Photo",
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(CircleShape),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            // Default Teacher Portrait (matching 40.png)
                            TeacherAvatarCanvas(modifier = Modifier.fillMaxSize())
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(44.dp))

            // 2. TEACHER INFO (Exact Typography & Layout from 40.png)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 22.dp)
            ) {
                // Name & Verified Badge & Teacher Label
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "Sushree Adyasha Mohanty",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF0F172A),
                        fontSize = 21.sp
                    )
                    Box(
                        modifier = Modifier
                            .size(18.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF475569)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = "Verified",
                            tint = PureWhite,
                            modifier = Modifier.size(12.dp)
                        )
                    }
                    Text(
                        text = "Teacher",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(0xFF64748B),
                        fontSize = 12.5.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                // School Name & Magenta AOR Badge
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "Govt. Higher Secondary School",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF334155),
                        fontSize = 13.5.sp
                    )
                    Box(
                        modifier = Modifier
                            .size(16.dp)
                            .clip(CircleShape)
                            .background(OdishaMagenta),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = "Verified",
                            tint = PureWhite,
                            modifier = Modifier.size(10.dp)
                        )
                    }
                    Text(
                        text = "AOR 56",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF334155),
                        fontSize = 11.5.sp
                    )
                }

                // Location with Red Pin Icon
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.padding(top = 2.dp)
                ) {
                    Text(text = "📍", fontSize = 12.sp)
                    Text(
                        text = "Phulbani, Kandhamal, Odisha",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF64748B),
                        fontSize = 12.sp
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Identifiers (40.png)
                Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
                    Text(
                        text = "UDYAP ID: ODIST-KMDPLBM-SABO1",
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF475569),
                        fontSize = 13.sp
                    )
                    Text(
                        text = "User ID: 445629881",
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF475569),
                        fontSize = 13.sp
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Designation Row with School Crest
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    SchoolMiniCrest(size = 20.dp)
                    Text(
                        text = "PGT Physics | Govt. Higher Secondary School",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF475569),
                        fontSize = 12.5.sp
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Action Buttons Row: "Get in Touch" & Share Button (40.png)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        modifier = Modifier
                            .weight(1f)
                            .height(42.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .clickable {
                                viewModel.activeChatContact.value = "Sushree Adyasha Mohanty"
                                viewModel.navigateToPage(13)
                            },
                        shape = RoundedCornerShape(12.dp),
                        color = OdishaMagenta
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = "Get in Touch",
                                color = PureWhite,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(0xFFFCE7F3))
                            .clickable {
                                Toast.makeText(context, "Teacher profile link copied", Toast.LENGTH_SHORT).show()
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Share,
                            contentDescription = "Share",
                            tint = Color(0xFF1E293B),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Socials Section (40.png)
                Text(
                    text = "Socials",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E293B),
                    fontSize = 16.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    SocialCircleBadge("in")
                    SocialCircleBadge("🦉")
                    SocialCircleBadge("un")
                }

                Spacer(modifier = Modifier.height(56.dp))

                // Lock graphic & exact prompt message (40.png)
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    BigLockGraphic()
                    Spacer(modifier = Modifier.height(14.dp))
                    Text(
                        text = "Cant see any information until\nyoure in touch..",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF64748B),
                        fontSize = 13.5.sp,
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.height(84.dp))
            }
        }

        // Photo Dialog
        if (showPhotoDialog) {
            AssetChooserDialog(
                title = "Teacher Profile Photo",
                subtitle = "Upload or change teacher's photo from gallery",
                onUpload = {
                    showPhotoDialog = false
                    photoPickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                },
                onRemove = if (!teacherPhotoUri.isNullOrEmpty()) {
                    {
                        viewModel.removeProfileAsset("avatar_teacher")
                        showPhotoDialog = false
                    }
                } else null,
                onDismiss = { showPhotoDialog = false }
            )
        }

        // Banner Dialog
        if (showBannerDialog) {
            AssetChooserDialog(
                title = "Teacher Profile Banner",
                subtitle = "Upload or change chalkboard banner from gallery",
                onUpload = {
                    showBannerDialog = false
                    bannerPickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                },
                onRemove = if (!teacherBannerUri.isNullOrEmpty()) {
                    {
                        viewModel.removeProfileAsset("banner_teacher")
                        showBannerDialog = false
                    }
                } else null,
                onDismiss = { showBannerDialog = false }
            )
        }
    }
}

// =========================================================================
// SCREEN 41: Parent Profile (Exact Replica of 41.png)
// =========================================================================
@Composable
fun ParentProfileScreen(
    viewModel: EduLockerViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val customAssets by viewModel.customProfileAssets.collectAsState()

    var showPhotoDialog by remember { mutableStateOf(false) }
    var showBannerDialog by remember { mutableStateOf(false) }

    val parentPhotoUri = customAssets["avatar_parent"]
    val parentBannerUri = customAssets["banner_parent"]

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            viewModel.uploadProfileAsset("avatar_parent", uri)
            Toast.makeText(context, "Parent photo updated!", Toast.LENGTH_SHORT).show()
        }
    }

    val bannerPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            viewModel.uploadProfileAsset("banner_parent", uri)
            Toast.makeText(context, "Parent banner updated!", Toast.LENGTH_SHORT).show()
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(CanvasCream)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // 1. TOP SAND DUNES / TEXTURE BANNER (41.png)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                if (!parentBannerUri.isNullOrEmpty() && File(parentBannerUri).exists()) {
                    AsyncImage(
                        model = File(parentBannerUri),
                        contentDescription = "Parent Banner",
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable { showBannerDialog = true },
                        contentScale = ContentScale.Crop
                    )
                } else {
                    SandDunesBannerCanvas(
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable { showBannerDialog = true }
                    )
                }

                // Overlapping Circular Parent Photo on bottom-left (41.png)
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(start = 20.dp, bottom = 0.dp)
                        .offset(y = 36.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(86.dp)
                            .clip(CircleShape)
                            .border(3.5.dp, PureWhite, CircleShape)
                            .background(Color(0xFF6D4C41))
                            .clickable { showPhotoDialog = true },
                        contentAlignment = Alignment.Center
                    ) {
                        if (!parentPhotoUri.isNullOrEmpty() && File(parentPhotoUri).exists()) {
                            AsyncImage(
                                model = File(parentPhotoUri),
                                contentDescription = "Parent Photo",
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(CircleShape),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            ParentAvatarCanvas(modifier = Modifier.fillMaxSize())
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(44.dp))

            // 2. PARENT INFO (Exact Typography & Layout from 41.png)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 22.dp)
            ) {
                // Name & Verified Badge & Parent Label
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "Sachidananda Nayak",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF0F172A),
                        fontSize = 21.sp
                    )
                    Box(
                        modifier = Modifier
                            .size(18.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF475569)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = "Verified",
                            tint = PureWhite,
                            modifier = Modifier.size(12.dp)
                        )
                    }
                    Text(
                        text = "Parent",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(0xFF64748B),
                        fontSize = 12.5.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                // Work & Profession Rows (41.png)
                Text(
                    text = "Kasidevi Treding Co. | Accountant",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF475569),
                    fontSize = 13.5.sp
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "UltraTech | Sales",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF475569),
                        fontSize = 13.5.sp
                    )
                    Text(
                        text = "more..",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF94A3B8),
                        fontSize = 13.sp
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                // UDYAP ID & User ID (41.png)
                Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
                    Text(
                        text = "UDYAP ID: PARENT-ACLN-SHNO24",
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF475569),
                        fontSize = 13.sp
                    )
                    Text(
                        text = "User ID: 488632159",
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF475569),
                        fontSize = 13.sp
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Linked Student (Laxmikant Nayak +1 More - 41.png)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF1E293B)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("LN", color = PureWhite, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    }
                    Text(
                        text = "Laxmikant Nayak",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF334155),
                        fontSize = 13.sp
                    )
                    Text(
                        text = "+1 More",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF64748B),
                        fontSize = 12.sp
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Action Buttons Row: "Contact" & Share Button (41.png)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        modifier = Modifier
                            .weight(1f)
                            .height(42.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .clickable {
                                viewModel.activeChatContact.value = "Sachidananda Nayak"
                                viewModel.navigateToPage(13)
                            },
                        shape = RoundedCornerShape(12.dp),
                        color = OdishaMagenta
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = "Contact",
                                color = PureWhite,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(0xFFFCE7F3))
                            .clickable {
                                Toast.makeText(context, "Parent profile link copied", Toast.LENGTH_SHORT).show()
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Share,
                            contentDescription = "Share",
                            tint = Color(0xFF1E293B),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Socials Section (41.png has "in")
                Text(
                    text = "Socials",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E293B),
                    fontSize = 16.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    SocialCircleBadge("in")
                }

                Spacer(modifier = Modifier.height(56.dp))

                // Lock graphic & exact prompt message (41.png)
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    BigLockGraphic()
                    Spacer(modifier = Modifier.height(14.dp))
                    Text(
                        text = "Cant see any information until\nyoure in touch..",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF64748B),
                        fontSize = 13.5.sp,
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.height(84.dp))
            }
        }

        // Photo Dialog
        if (showPhotoDialog) {
            AssetChooserDialog(
                title = "Parent Profile Photo",
                subtitle = "Upload or change parent's photo from gallery",
                onUpload = {
                    showPhotoDialog = false
                    photoPickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                },
                onRemove = if (!parentPhotoUri.isNullOrEmpty()) {
                    {
                        viewModel.removeProfileAsset("avatar_parent")
                        showPhotoDialog = false
                    }
                } else null,
                onDismiss = { showPhotoDialog = false }
            )
        }

        // Banner Dialog
        if (showBannerDialog) {
            AssetChooserDialog(
                title = "Parent Profile Banner",
                subtitle = "Upload or change sand dune banner from gallery",
                onUpload = {
                    showBannerDialog = false
                    bannerPickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                },
                onRemove = if (!parentBannerUri.isNullOrEmpty()) {
                    {
                        viewModel.removeProfileAsset("banner_parent")
                        showBannerDialog = false
                    }
                } else null,
                onDismiss = { showBannerDialog = false }
            )
        }
    }
}

// -----------------------------------------------------------
// CANVASES & DRAWABLES FOR TEACHER AND PARENT PROFILES
// -----------------------------------------------------------

@Composable
fun BlackboardCanvas(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        // Dark slate blackboard
        drawRect(color = Color(0xFF1E293B))

        // Chalkboard formulas in subtle white chalk
        val chalkColor = Color.White.copy(alpha = 0.50f)
        val chalkStroke = Stroke(width = 1.8.dp.toPx(), cap = StrokeCap.Round)

        // Geometry arcs & coordinate grid lines
        drawArc(
            color = chalkColor.copy(alpha = 0.3f),
            startAngle = 0f,
            sweepAngle = 180f,
            useCenter = false,
            topLeft = Offset(w * 0.45f, h * 0.10f),
            size = Size(w * 0.35f, h * 0.55f),
            style = chalkStroke
        )
        drawLine(
            color = chalkColor.copy(alpha = 0.35f),
            start = Offset(0f, h * 0.65f),
            end = Offset(w, h * 0.65f),
            strokeWidth = 1.2.dp.toPx()
        )
    }

    // Text formulas overlay
    Box(
        modifier = modifier.padding(horizontal = 16.dp, vertical = 14.dp)
    ) {
        Text(
            text = "sin 300° = -\u221A3/2\nsin(3·90°+30°)\ncos 30° = \u221A3/2\nsin(x+y)",
            color = Color.White.copy(alpha = 0.65f),
            fontSize = 12.5.sp,
            fontWeight = FontWeight.Medium,
            lineHeight = 18.sp,
            modifier = Modifier.align(Alignment.TopStart)
        )
        Text(
            text = "P_N / \u03C0·r²\n\u03B1 / 360°\nt = 2·\u03C0·\u221A(l/g)",
            color = Color.White.copy(alpha = 0.65f),
            fontSize = 12.5.sp,
            fontWeight = FontWeight.Medium,
            lineHeight = 18.sp,
            modifier = Modifier.align(Alignment.TopEnd)
        )
    }
}

@Composable
fun SandDunesBannerCanvas(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        val sandBrush = Brush.verticalGradient(
            colors = listOf(
                Color(0xFF5D4037),
                Color(0xFF795548),
                Color(0xFF8D6E63),
                Color(0xFFA1887F),
                Color(0xFFD7CCC8)
            )
        )
        drawRect(brush = sandBrush)

        // Diagonal light & shade dunes
        val dunePath = Path().apply {
            moveTo(0f, h * 0.2f)
            cubicTo(w * 0.4f, h * 0.5f, w * 0.7f, h * 0.1f, w, h * 0.35f)
            lineTo(w, h)
            lineTo(0f, h)
            close()
        }
        drawPath(
            path = dunePath,
            brush = Brush.horizontalGradient(
                colors = listOf(
                    Color(0xFF4E342E).copy(alpha = 0.45f),
                    Color(0xFFBCAAA4).copy(alpha = 0.25f)
                )
            )
        )
    }
}

@Composable
fun TeacherAvatarCanvas(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        // Dark background with soft warm glow
        drawRect(color = Color(0xFF1E293B))
        drawCircle(
            color = Color(0xFF38BDF8).copy(alpha = 0.35f),
            radius = w * 0.42f,
            center = Offset(w * 0.5f, h * 0.45f)
        )

        // Head & hair
        drawCircle(color = Color(0xFF0F172A), radius = w * 0.28f, center = Offset(w * 0.5f, h * 0.38f))
        drawCircle(color = Color(0xFFF1F5F9), radius = w * 0.22f, center = Offset(w * 0.5f, h * 0.42f))

        // Professional saree/shawl drape
        val drape = Path().apply {
            moveTo(w * 0.15f, h)
            cubicTo(w * 0.25f, h * 0.65f, w * 0.75f, h * 0.65f, w * 0.85f, h)
            close()
        }
        drawPath(drape, color = Color(0xFF0284C7))
    }
}

@Composable
fun ParentAvatarCanvas(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        drawRect(color = Color(0xFF3E2723))
        drawCircle(color = Color(0xFFFFCCBC).copy(alpha = 0.4f), radius = w * 0.40f, center = Offset(w * 0.5f, h * 0.45f))

        // Head & glasses outline
        drawCircle(color = Color(0xFF212121), radius = w * 0.26f, center = Offset(w * 0.5f, h * 0.38f))
        drawCircle(color = Color(0xFFFFCCBC), radius = w * 0.22f, center = Offset(w * 0.5f, h * 0.42f))

        // Red polo/shirt
        val shirt = Path().apply {
            moveTo(w * 0.15f, h)
            cubicTo(w * 0.25f, h * 0.68f, w * 0.75f, h * 0.68f, w * 0.85f, h)
            close()
        }
        drawPath(shirt, color = Color(0xFFE11D48))
    }
}

@Composable
fun SchoolMiniCrest(size: androidx.compose.ui.unit.Dp) {
    Box(
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .background(Color(0xFFFCE7F3))
            .border(1.dp, OdishaMagenta, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Text("🏛️", fontSize = 11.sp)
    }
}

@Composable
fun SocialCircleBadge(label: String) {
    Box(
        modifier = Modifier
            .size(38.dp)
            .clip(CircleShape)
            .background(Color(0xFFFCE7F3)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 14.5.sp,
            color = Color(0xFF1E293B)
        )
    }
}

@Composable
fun BigLockGraphic() {
    Canvas(modifier = Modifier.size(64.dp)) {
        val darkColor = Color(0xFF64748B)
        val w = size.width
        val h = size.height

        // Shackle
        drawArc(
            color = darkColor,
            startAngle = 180f,
            sweepAngle = 180f,
            useCenter = false,
            topLeft = Offset(w * 0.26f, h * 0.10f),
            size = Size(w * 0.48f, h * 0.48f),
            style = Stroke(width = 4.5.dp.toPx())
        )

        // Lock Body
        drawRoundRect(
            color = darkColor,
            topLeft = Offset(w * 0.16f, h * 0.40f),
            size = Size(w * 0.68f, h * 0.52f),
            cornerRadius = CornerRadius(10.dp.toPx(), 10.dp.toPx())
        )

        // Keyhole
        drawCircle(
            color = PureWhite,
            radius = 3.8.dp.toPx(),
            center = Offset(w * 0.50f, h * 0.60f)
        )
        val keyholePath = Path().apply {
            moveTo(w * 0.46f, h * 0.60f)
            lineTo(w * 0.54f, h * 0.60f)
            lineTo(w * 0.52f, h * 0.76f)
            lineTo(w * 0.48f, h * 0.76f)
            close()
        }
        drawPath(keyholePath, color = PureWhite)
    }
}

@Composable
private fun AssetChooserDialog(
    title: String,
    subtitle: String,
    onUpload: () -> Unit,
    onRemove: (() -> Unit)? = null,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp)),
            shape = RoundedCornerShape(20.dp),
            color = PureWhite
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = TextDark, fontSize = 17.sp)
                Text(subtitle, fontSize = 11.5.sp, color = Color(0xFF64748B), modifier = Modifier.padding(top = 4.dp, bottom = 16.dp))

                Button(
                    onClick = onUpload,
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = OdishaMagenta)
                ) {
                    Icon(Icons.Filled.PhotoLibrary, contentDescription = null, tint = PureWhite, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Upload from Photos / Gallery", color = PureWhite, fontWeight = FontWeight.Bold, fontSize = 13.5.sp)
                }

                if (onRemove != null) {
                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedButton(
                        onClick = onRemove,
                        modifier = Modifier.fillMaxWidth().height(44.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(Icons.Filled.Delete, contentDescription = null, tint = Color(0xFFE11D48), modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Remove Custom Image", color = Color(0xFFE11D48), fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))
                OutlinedButton(
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth().height(44.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Cancel", color = TextDark, fontSize = 13.sp)
                }
            }
        }
    }
}
