package com.example.ui.screens

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Accessibility
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Headphones
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import com.example.ui.theme.CanvasCream
import com.example.ui.theme.DarkBorder
import com.example.ui.theme.OdishaMagenta
import com.example.ui.theme.OdishaMagentaDark
import com.example.ui.theme.PureWhite
import com.example.ui.theme.SoftBlushPink
import com.example.ui.theme.TextDark
import com.example.ui.theme.TextMuted
import com.example.ui.viewmodel.EduLockerViewModel
import com.example.util.NotificationHelper
import java.io.File

// =========================================================================
// SCREEN 36: Student Profile Hub (Exact Replica of 36.png)
// =========================================================================
@Composable
fun ProfileHubScreen(
    viewModel: EduLockerViewModel,
    onNavigateToIdCard: () -> Unit,
    onNavigateToAchievements: () -> Unit,
    modifier: Modifier = Modifier
) {
    val profile by viewModel.studentProfile.collectAsState()
    val context = LocalContext.current
    var activeModal by remember { mutableStateOf<String?>(null) }
    var showPhotoChooserDialog by remember { mutableStateOf(false) }
    var showBannerChooserDialog by remember { mutableStateOf(false) }

    // Photo picker launcher (Zero-permission Android Photo Picker)
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            viewModel.uploadStudentPhoto(uri)
            Toast.makeText(context, "Profile photo updated successfully!", Toast.LENGTH_SHORT).show()
        }
    }

    // Banner picker launcher
    val bannerPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            viewModel.uploadStudentBanner(uri)
            Toast.makeText(context, "Profile banner updated successfully!", Toast.LENGTH_SHORT).show()
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
            // 1. TOP HERO BANNER (Candle in Glass on Wood with Brown Fabric Drapes - Clickable to change)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(210.dp)
            ) {
                val bannerUri = profile?.bannerUri
                if (!bannerUri.isNullOrEmpty() && File(bannerUri).exists()) {
                    AsyncImage(
                        model = File(bannerUri),
                        contentDescription = "Profile Banner",
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable { showBannerChooserDialog = true },
                        contentScale = ContentScale.Crop
                    )
                } else {
                    // High-Fidelity Photorealistic Candle in Glass on Wooden Tray (Exact Replica from 36.png)
                    CandleBannerCanvas(
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable { showBannerChooserDialog = true }
                    )
                }

                // Overlapping circular student photo (Exact position & styling from 36.png)
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(start = 20.dp, bottom = 0.dp)
                        .offset(y = 36.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(88.dp)
                            .clip(CircleShape)
                            .border(3.5.dp, PureWhite, CircleShape)
                            .background(Color(0xFF1E293B))
                            .clickable { showPhotoChooserDialog = true },
                        contentAlignment = Alignment.Center
                    ) {
                        val photoUri = profile?.photoUri
                        if (!photoUri.isNullOrEmpty() && File(photoUri).exists()) {
                            AsyncImage(
                                model = File(photoUri),
                                contentDescription = "Profile Photo",
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(CircleShape),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            // Default Handsome Student Portrait Artwork matching 36.png
                            DefaultStudentAvatarCanvas(modifier = Modifier.fillMaxSize())
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(44.dp))

            // 2. PROFILE DETAILS CONTENT (Exact Layout from 36.png)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 22.dp)
            ) {
                // Name & Verified Badge & Settings Gear
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = profile?.fullName ?: "Laxmikant Nayak",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF0F172A),
                            fontSize = 22.sp
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
                            text = "Verified",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color(0xFF64748B),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    // Settings Button (Opens Screen 37 Main Settings)
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(0xFFFCE7F3))
                            .clickable { onNavigateToAchievements() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Settings,
                            contentDescription = "Settings",
                            tint = Color(0xFF1E293B),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                // School Name & AOR Badge (36.png)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = profile?.schoolName ?: "Govt. Higher Secondary School",
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
                        text = profile?.schoolAor ?: "AOR 56",
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
                        text = profile?.schoolLocation ?: "Phulbani, Kandhamal, Odisha",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF64748B),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Magenta EduLocker Identifiers (Exact 3 lines in Pink from 36.png)
                Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
                    Text(
                        text = "EduLocker ID: ${profile?.eduLockerId ?: "ODIST-KMD-PLBM-LN001"}",
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFFE11D48),
                        fontSize = 13.5.sp
                    )
                    Text(
                        text = "User ID: ${profile?.userId ?: "01009864"}",
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFFE11D48),
                        fontSize = 13.5.sp
                    )
                    Text(
                        text = "Region: ${profile?.region ?: "Kandhamal, Odisha, India"}",
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFFE11D48),
                        fontSize = 13.5.sp
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Personal Details List (Exact 7 lines from 36.png)
                Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
                    DetailTextRow("S/D. of : ${profile?.parentName ?: "Sachidananda Nayak"}")
                    DetailTextRow("D.O.B : DD . MM . YYYY")
                    DetailTextRow("Blood Group : ${profile?.bloodGroup ?: "B +ve"}")
                    DetailTextRow("Address : ${profile?.address ?: "Phulbani, Kandhamal, Odisha"}")
                    DetailTextRow("Pin Code : ${profile?.pinCode ?: "762001"}")
                    DetailTextRow("Class : 12ᵗʰ Science")
                    DetailTextRow("eKYC Status : ${profile?.ekycStatus ?: "Verified"}")
                }

                Spacer(modifier = Modifier.height(18.dp))

                // Socials Section (Exact 5 circular buttons from 36.png)
                Text(
                    text = "Socials",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E293B),
                    fontSize = 16.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // 1. GitHub Icon
                    SocialCircleBadgeContainer(onClick = { Toast.makeText(context, "GitHub: laxmikant14", Toast.LENGTH_SHORT).show() }) {
                        GitHubIconVector(size = 18.dp)
                    }

                    // 2. LinkedIn "in"
                    SocialCircleBadgeContainer(onClick = { Toast.makeText(context, "LinkedIn: laxmikant-nayak", Toast.LENGTH_SHORT).show() }) {
                        Text("in", fontWeight = FontWeight.ExtraBold, fontSize = 15.sp, color = Color(0xFF1E293B))
                    }

                    // 3. Owl Icon
                    SocialCircleBadgeContainer(onClick = { Toast.makeText(context, "Duolingo / Owl", Toast.LENGTH_SHORT).show() }) {
                        Text("🦉", fontSize = 16.sp)
                    }

                    // 4. "un" Icon
                    SocialCircleBadgeContainer(onClick = { Toast.makeText(context, "Unstop: laxmikant1418", Toast.LENGTH_SHORT).show() }) {
                        Text("un", fontWeight = FontWeight.Bold, fontSize = 13.5.sp, color = Color(0xFF1E293B))
                    }

                    // 5. Plus (+) Button
                    SocialCircleBadgeContainer(onClick = { Toast.makeText(context, "Add social handle", Toast.LENGTH_SHORT).show() }) {
                        Icon(Icons.Filled.Add, contentDescription = "Add", tint = Color(0xFF1E293B), modifier = Modifier.size(20.dp))
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Action Buttons Row (Edit profile, Share, Drafts - 36.png)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    ActionPillButton("Edit profile", Modifier.weight(1f)) {
                        showPhotoChooserDialog = true
                    }
                    ActionPillButton("Share", Modifier.weight(0.7f)) {
                        activeModal = "Share"
                    }
                    ActionPillButton("Drafts", Modifier.weight(1f)) {
                        activeModal = "Drafts"
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // 6 Category Pink Pills (2 rows of 3 columns - Exact spelling & shape from 36.png)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    CategoryPillButton("Achivements", Modifier.weight(1f)) { activeModal = "Achivements" }
                    CategoryPillButton("ID Card", Modifier.weight(1f)) { onNavigateToIdCard() }
                    CategoryPillButton("Exprience", Modifier.weight(1f)) { activeModal = "Exprience" }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    CategoryPillButton("Education", Modifier.weight(1f)) { activeModal = "Education" }
                    CategoryPillButton("Cirtificates", Modifier.weight(1f)) { activeModal = "Cirtificates" }
                    CategoryPillButton("More..", Modifier.weight(1f)) { onNavigateToAchievements() }
                }

                Spacer(modifier = Modifier.height(84.dp))
            }
        }

        // Photo Chooser Modal Dialog (Permanent storage in Room & filesDir)
        if (showPhotoChooserDialog) {
            Dialog(onDismissRequest = { showPhotoChooserDialog = false }) {
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
                        Text(
                            text = "Student Profile Photo",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = TextDark,
                            fontSize = 17.sp
                        )
                        Text(
                            text = "Upload a photo from your gallery to personalize your profile",
                            fontSize = 11.5.sp,
                            color = Color(0xFF64748B),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(top = 4.dp, bottom = 16.dp)
                        )

                        // Current Avatar Preview
                        Box(
                            modifier = Modifier
                                .size(90.dp)
                                .clip(CircleShape)
                                .border(2.dp, OdishaMagenta, CircleShape)
                                .background(Color(0xFF1E293B)),
                            contentAlignment = Alignment.Center
                        ) {
                            val photoUri = profile?.photoUri
                            if (!photoUri.isNullOrEmpty() && File(photoUri).exists()) {
                                AsyncImage(
                                    model = File(photoUri),
                                    contentDescription = "Current Photo",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                            } else {
                                DefaultStudentAvatarCanvas(modifier = Modifier.fillMaxSize())
                            }
                        }

                        Spacer(modifier = Modifier.height(18.dp))

                        // Option 1: Choose from Gallery
                        Button(
                            onClick = {
                                showPhotoChooserDialog = false
                                photoPickerLauncher.launch(
                                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                )
                            },
                            modifier = Modifier.fillMaxWidth().height(48.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = OdishaMagenta)
                        ) {
                            Icon(Icons.Filled.PhotoLibrary, contentDescription = null, tint = PureWhite, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Upload from Photos / Gallery", color = PureWhite, fontWeight = FontWeight.Bold, fontSize = 13.5.sp)
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // Option 2: Remove Photo
                        if (!profile?.photoUri.isNullOrEmpty()) {
                            OutlinedButton(
                                onClick = {
                                    viewModel.removeStudentPhoto()
                                    showPhotoChooserDialog = false
                                    Toast.makeText(context, "Custom photo removed", Toast.LENGTH_SHORT).show()
                                },
                                modifier = Modifier.fillMaxWidth().height(44.dp),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Icon(Icons.Filled.Delete, contentDescription = null, tint = Color(0xFFE11D48), modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Remove Photo", color = Color(0xFFE11D48), fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                        }

                        // Close Button
                        OutlinedButton(
                            onClick = { showPhotoChooserDialog = false },
                            modifier = Modifier.fillMaxWidth().height(44.dp),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("Cancel", color = TextDark, fontSize = 13.sp)
                        }
                    }
                }
            }
        }

        // BANNER CHOOSER MODAL DIALOG
        if (showBannerChooserDialog) {
            Dialog(onDismissRequest = { showBannerChooserDialog = false }) {
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
                        Text(
                            text = "Student Profile Banner",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = TextDark,
                            fontSize = 17.sp
                        )
                        Text(
                            text = "Choose a custom cover banner photo from your gallery",
                            fontSize = 11.5.sp,
                            color = Color(0xFF64748B),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(top = 4.dp, bottom = 16.dp)
                        )

                        // Option 1: Choose from Gallery
                        Button(
                            onClick = {
                                showBannerChooserDialog = false
                                bannerPickerLauncher.launch(
                                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                )
                            },
                            modifier = Modifier.fillMaxWidth().height(48.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = OdishaMagenta)
                        ) {
                            Icon(Icons.Filled.PhotoLibrary, contentDescription = null, tint = PureWhite, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Upload Banner from Gallery", color = PureWhite, fontWeight = FontWeight.Bold, fontSize = 13.5.sp)
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // Option 2: Remove Banner
                        if (!profile?.bannerUri.isNullOrEmpty()) {
                            OutlinedButton(
                                onClick = {
                                    viewModel.removeStudentBanner()
                                    showBannerChooserDialog = false
                                    Toast.makeText(context, "Custom banner reset to default candle", Toast.LENGTH_SHORT).show()
                                },
                                modifier = Modifier.fillMaxWidth().height(44.dp),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Icon(Icons.Filled.Delete, contentDescription = null, tint = Color(0xFFE11D48), modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Reset to Default Candle Banner", color = Color(0xFFE11D48), fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                        }

                        // Close Button
                        OutlinedButton(
                            onClick = { showBannerChooserDialog = false },
                            modifier = Modifier.fillMaxWidth().height(44.dp),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("Cancel", color = TextDark, fontSize = 13.sp)
                        }
                    }
                }
            }
        }

        // Interactive modal for details (Achievements, Experience, Education & Skills, Certificates, Share, Drafts)
        activeModal?.let { type ->
            ProfileModuleDialog(
                title = type,
                onDismiss = { activeModal = null }
            )
        }
    }
}

// -----------------------------------------------------------
// PHOTOREALISTIC CANDLE BANNER CANVAS (Exact Aesthetic from 36.png)
// -----------------------------------------------------------
@Composable
fun CandleBannerCanvas(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        // 1. Rich Draped Brown Satin Fabric with Specular Folds
        val bgBrush = Brush.verticalGradient(
            colors = listOf(
                Color(0xFF23130C),
                Color(0xFF381F14),
                Color(0xFF4E2D1F),
                Color(0xFF341C12),
                Color(0xFF1C0D07)
            )
        )
        drawRect(brush = bgBrush)

        // Soft curved folds and shadow valleys of drapery
        val fold1 = Path().apply {
            moveTo(0f, 0f)
            cubicTo(w * 0.25f, h * 0.4f, w * 0.4f, h * 0.9f, w * 0.5f, h)
            lineTo(0f, h)
            close()
        }
        drawPath(fold1, color = Color(0xFF180A05).copy(alpha = 0.6f))

        val fold2 = Path().apply {
            moveTo(w, 0f)
            cubicTo(w * 0.75f, h * 0.35f, w * 0.65f, h * 0.7f, w * 0.55f, h)
            lineTo(w, h)
            close()
        }
        drawPath(fold2, color = Color(0xFF5A3525).copy(alpha = 0.35f))

        // 2. Dark Wooden Board / Tray (Angled in center)
        val trayCenterX = w * 0.46f
        val trayCenterY = h * 0.44f
        val trayW = w * 0.42f
        val trayH = h * 0.58f

        drawRoundRect(
            color = Color(0xFF3B2317),
            topLeft = Offset(trayCenterX - trayW / 2, trayCenterY - trayH / 2),
            size = Size(trayW, trayH),
            cornerRadius = CornerRadius(12f, 12f)
        )
        drawRoundRect(
            color = Color(0xFF523322),
            topLeft = Offset(trayCenterX - trayW / 2 + 4f, trayCenterY - trayH / 2 + 4f),
            size = Size(trayW - 8f, trayH - 8f),
            cornerRadius = CornerRadius(10f, 10f)
        )

        // 3. Clear Glass Votive Candleholder
        val glassRadius = 38.dp.toPx()
        val glassCenter = Offset(trayCenterX, trayCenterY - 4.dp.toPx())

        // Ambient radial light spread
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(
                    Color(0xFFFFD54F).copy(alpha = 0.75f),
                    Color(0xFFFF8F00).copy(alpha = 0.45f),
                    Color(0xFFE65100).copy(alpha = 0.18f),
                    Color.Transparent
                ),
                center = glassCenter,
                radius = 85.dp.toPx()
            ),
            radius = 85.dp.toPx(),
            center = glassCenter
        )

        // Glass jar body
        drawCircle(
            color = Color(0xFFEFEBE9).copy(alpha = 0.35f),
            radius = glassRadius,
            center = glassCenter
        )
        drawCircle(
            color = Color(0xFFFFFFFF).copy(alpha = 0.55f),
            radius = glassRadius,
            center = glassCenter,
            style = Stroke(width = 3.dp.toPx())
        )

        // Wax surface pool
        drawCircle(
            color = Color(0xFFFFF8E1).copy(alpha = 0.85f),
            radius = glassRadius * 0.82f,
            center = glassCenter
        )

        // Candle Wick & Flame
        val flameCenter = Offset(glassCenter.x, glassCenter.y - 4.dp.toPx())

        // Wick
        drawLine(
            color = Color(0xFF212121),
            start = Offset(flameCenter.x, flameCenter.y + 10.dp.toPx()),
            end = flameCenter,
            strokeWidth = 2.5.dp.toPx(),
            cap = StrokeCap.Round
        )

        // Outer Orange Flame Glow
        val flamePath = Path().apply {
            moveTo(flameCenter.x, flameCenter.y - 18.dp.toPx()) // Tip
            cubicTo(
                flameCenter.x + 8.dp.toPx(), flameCenter.y - 6.dp.toPx(),
                flameCenter.x + 7.dp.toPx(), flameCenter.y + 4.dp.toPx(),
                flameCenter.x, flameCenter.y + 6.dp.toPx()
            )
            cubicTo(
                flameCenter.x - 7.dp.toPx(), flameCenter.y + 4.dp.toPx(),
                flameCenter.x - 8.dp.toPx(), flameCenter.y - 6.dp.toPx(),
                flameCenter.x, flameCenter.y - 18.dp.toPx()
            )
            close()
        }
        drawPath(flamePath, color = Color(0xFFFF9800))

        // Inner Golden Yellow Flame Core
        val coreFlame = Path().apply {
            moveTo(flameCenter.x, flameCenter.y - 12.dp.toPx())
            cubicTo(
                flameCenter.x + 4.dp.toPx(), flameCenter.y - 4.dp.toPx(),
                flameCenter.x + 3.dp.toPx(), flameCenter.y + 2.dp.toPx(),
                flameCenter.x, flameCenter.y + 4.dp.toPx()
            )
            cubicTo(
                flameCenter.x - 3.dp.toPx(), flameCenter.y + 2.dp.toPx(),
                flameCenter.x - 4.dp.toPx(), flameCenter.y - 4.dp.toPx(),
                flameCenter.x, flameCenter.y - 12.dp.toPx()
            )
            close()
        }
        drawPath(coreFlame, color = Color(0xFFFFF59D))

        // Pure white flame center spark
        drawCircle(
            color = Color.White,
            radius = 2.5.dp.toPx(),
            center = Offset(flameCenter.x, flameCenter.y - 2.dp.toPx())
        )
    }
}

// -----------------------------------------------------------
// DEFAULT STUDENT AVATAR ARTWORK (Matching 36.png & 37.png)
// -----------------------------------------------------------
@Composable
fun DefaultStudentAvatarCanvas(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        // Dark moody background
        drawRect(color = Color(0xFF111827))

        // Subtle ambient red-orange rim light behind hair (from 36.png)
        drawCircle(
            color = Color(0xFFE11D48).copy(alpha = 0.45f),
            radius = w * 0.45f,
            center = Offset(w * 0.78f, h * 0.42f)
        )

        // Head & curly silhouette
        drawCircle(
            color = Color(0xFF334155),
            radius = w * 0.28f,
            center = Offset(w * 0.50f, h * 0.42f)
        )

        // Stylized curly hair volumes
        drawCircle(color = Color(0xFF0F172A), radius = w * 0.22f, center = Offset(w * 0.44f, h * 0.28f))
        drawCircle(color = Color(0xFF1E293B), radius = w * 0.18f, center = Offset(w * 0.60f, h * 0.30f))

        // Torso & collar
        val torso = Path().apply {
            moveTo(w * 0.15f, h)
            cubicTo(w * 0.25f, h * 0.68f, w * 0.75f, h * 0.68f, w * 0.85f, h)
            close()
        }
        drawPath(torso, color = Color(0xFF0F172A))
    }
}

// -----------------------------------------------------------
// GITHUB VECTOR ICON
// -----------------------------------------------------------
@Composable
fun GitHubIconVector(size: androidx.compose.ui.unit.Dp) {
    Canvas(modifier = Modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height
        val darkColor = Color(0xFF1E293B)

        // GitHub Octocat silhouette approximation
        drawCircle(color = darkColor, radius = w * 0.46f, center = Offset(w / 2, h / 2))
        drawCircle(color = Color(0xFFFCE7F3), radius = w * 0.28f, center = Offset(w / 2, h * 0.58f))

        // Cat ears
        val leftEar = Path().apply {
            moveTo(w * 0.22f, h * 0.32f)
            lineTo(w * 0.32f, h * 0.15f)
            lineTo(w * 0.42f, h * 0.26f)
            close()
        }
        drawPath(leftEar, color = darkColor)

        val rightEar = Path().apply {
            moveTo(w * 0.78f, h * 0.32f)
            lineTo(w * 0.68f, h * 0.15f)
            lineTo(w * 0.58f, h * 0.26f)
            close()
        }
        drawPath(rightEar, color = darkColor)
    }
}

// -----------------------------------------------------------
// Helper Components for 36.png and 37.png
// -----------------------------------------------------------
@Composable
private fun DetailTextRow(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodySmall,
        color = Color(0xFF475569),
        fontSize = 12.5.sp,
        fontWeight = FontWeight.Normal
    )
}

@Composable
private fun SocialCircleBadgeContainer(
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .size(38.dp)
            .clip(CircleShape)
            .background(Color(0xFFFCE7F3))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}

@Composable
private fun ActionPillButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier
            .height(38.dp)
            .clip(RoundedCornerShape(10.dp))
            .clickable { onClick() },
        shape = RoundedCornerShape(10.dp),
        color = Color(0xFFFCE7F3)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF334155),
                fontSize = 12.5.sp
            )
        }
    }
}

@Composable
private fun CategoryPillButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier
            .height(42.dp)
            .clip(RoundedCornerShape(10.dp))
            .clickable { onClick() },
        shape = RoundedCornerShape(10.dp),
        color = Color(0xFFFCE7F3)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF334155),
                fontSize = 12.sp
            )
        }
    }
}

// =========================================================================
// SCREEN 37: Main Settings (Exact Pixel-Perfect Replica of 37.png)
// =========================================================================
@Composable
fun AchievementsScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var selectedSettingDialog by remember { mutableStateOf<String?>(null) }
    var searchQuery by remember { mutableStateOf("") }
    var isSearchActive by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(CanvasCream)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // 1. TOP HEADER: Profile Avatar + Name + EduLocker ID + Back Button (Exact from 37.png)
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    // Circular Avatar
                    Box(
                        modifier = Modifier
                            .size(54.dp)
                            .clip(CircleShape)
                            .border(2.dp, OdishaMagenta, CircleShape)
                            .background(Color(0xFF1E293B)),
                        contentAlignment = Alignment.Center
                    ) {
                        DefaultStudentAvatarCanvas(modifier = Modifier.fillMaxSize())
                    }

                    Column {
                        Text(
                            text = "Laxmikant Nayak",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1E293B),
                            fontSize = 18.sp
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "ODIST-KMD-PLBM-LN001",
                            color = Color(0xFFE11D48),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                // Solid Round Pink Button with White Back Arrow (Exact from 37.png)
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFE11D48))
                        .clickable { onBack() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = PureWhite,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // 2. SUB-HEADER: "Main Settings" Filled Pink Pill + Circular Search Button (Exact from 37.png)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Filled Pink Pill: "Main Settings"
                Surface(
                    shape = RoundedCornerShape(24.dp),
                    color = Color(0xFFE11D48),
                    modifier = Modifier.clickable {
                        Toast.makeText(context, "Main Settings Active", Toast.LENGTH_SHORT).show()
                    }
                ) {
                    Text(
                        text = "Main Settings",
                        color = PureWhite,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.5.sp,
                        modifier = Modifier.padding(horizontal = 22.dp, vertical = 9.dp)
                    )
                }

                // Circular Pink Search Button
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFE11D48))
                        .clickable {
                            isSearchActive = !isSearchActive
                            if (isSearchActive) {
                                Toast.makeText(context, "Search settings...", Toast.LENGTH_SHORT).show()
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search",
                        tint = PureWhite,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // 3. CARD 1: PRIMARY SETTINGS BLOCK (Exact Soft Blush Pink Card from 37.png)
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(26.dp),
                color = Color(0xFFFDE8EF)
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 18.dp, vertical = 22.dp),
                    verticalArrangement = Arrangement.spacedBy(22.dp)
                ) {
                    SettingsRowItem37(
                        icon = Icons.Filled.Person,
                        title = "Account Details"
                    ) {
                        selectedSettingDialog = "Account Details"
                    }

                    SettingsRowItem37(
                        icon = Icons.Filled.Security,
                        title = "Privacy Settings"
                    ) {
                        selectedSettingDialog = "Privacy Settings"
                    }

                    SettingsRowItem37(
                        icon = Icons.Filled.Lightbulb,
                        title = "Persionalization & Display"
                    ) {
                        selectedSettingDialog = "Persionalization & Display"
                    }

                    SettingsRowItem37(
                        icon = Icons.Filled.Accessibility,
                        title = "Accessibility"
                    ) {
                        selectedSettingDialog = "Accessibility"
                    }

                    SettingsRowItem37(
                        icon = Icons.Filled.Lock,
                        title = "Content Privacy"
                    ) {
                        selectedSettingDialog = "Content Privacy"
                    }

                    SettingsRowItem37(
                        icon = Icons.Filled.CheckCircle,
                        title = "Permissions"
                    ) {
                        selectedSettingDialog = "Permissions"
                    }

                    SettingsRowItem37(
                        icon = Icons.Filled.Build,
                        title = "Control & Tools"
                    ) {
                        selectedSettingDialog = "Control & Tools"
                    }

                    SettingsRowItem37(
                        icon = Icons.Filled.Headphones,
                        title = "Help & Support"
                    ) {
                        selectedSettingDialog = "Help & Support"
                    }

                    SettingsRowItem37(
                        icon = Icons.Filled.Description,
                        title = "Privacy Policy & Terms of Use"
                    ) {
                        selectedSettingDialog = "Privacy Policy & Terms of Use"
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // 4. CARD 2: ACCOUNT MANAGEMENT BLOCK (Exact Soft Blush Pink Card from 37.png)
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(26.dp),
                color = Color(0xFFFDE8EF)
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 18.dp, vertical = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    SettingsRowItem37(
                        icon = Icons.Filled.List,
                        title = "Manage Accounts"
                    ) {
                        selectedSettingDialog = "Manage Accounts"
                    }

                    SettingsRowItem37(
                        icon = Icons.Filled.PersonAdd,
                        title = "Add Account"
                    ) {
                        selectedSettingDialog = "Add Account"
                    }

                    SettingsRowItem37(
                        icon = Icons.AutoMirrored.Filled.Logout,
                        title = "Log Out",
                        isRed = true
                    ) {
                        selectedSettingDialog = "Log Out"
                    }
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            // 5. FOOTER: "EduLocker | All Rights Reserved" (Exact from 37.png)
            Text(
                text = "EduLocker | All Rights Reserved",
                color = Color(0xFF94A3B8),
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(96.dp))
        }

        // Detailed Demo Content Sheets / Modals for each setting item
        selectedSettingDialog?.let { settingName ->
            SettingItemDetailDialog(
                title = settingName,
                onDismiss = { selectedSettingDialog = null }
            )
        }
    }
}

// -----------------------------------------------------------
// Settings Row Item for 37.png (Clean Dark Icons & Text)
// -----------------------------------------------------------
@Composable
private fun SettingsRowItem37(
    icon: ImageVector,
    title: String,
    isRed: Boolean = false,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = if (isRed) Color(0xFFE11D48) else Color(0xFF1E293B),
            modifier = Modifier.size(22.dp)
        )
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            color = if (isRed) Color(0xFFE11D48) else Color(0xFF334155),
            fontSize = 14.5.sp
        )
    }
}

// -----------------------------------------------------------
// Setting Item Detail Dialog (Displays Demo Content for each Setting)
// -----------------------------------------------------------
@Composable
private fun SettingItemDetailDialog(
    title: String,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    var fontSizeSlider by remember { mutableFloatStateOf(14f) }
    var selectedTheme by remember { mutableStateOf("Canvas Classic") }
    var highContrastEnabled by remember { mutableStateOf(false) }
    var talkBackEnabled by remember { mutableStateOf(false) }
    var hapticEnabled by remember { mutableStateOf(true) }
    var eKycStealth by remember { mutableStateOf(true) }
    var notificationPerm by remember { mutableStateOf(true) }
    var cameraPerm by remember { mutableStateOf(true) }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .clip(RoundedCornerShape(22.dp)),
            shape = RoundedCornerShape(22.dp),
            color = PureWhite
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                // Dialog Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Color(0xFF0F172A)
                    )
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.size(30.dp)
                    ) {
                        Icon(Icons.Filled.Close, contentDescription = "Close", tint = Color(0xFF475569), modifier = Modifier.size(20.dp))
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))
                HorizontalDivider(color = Color(0xFFF1F5F9), thickness = 1.dp)
                Spacer(modifier = Modifier.height(14.dp))

                // Content Specific to each Setting
                when (title) {
                    "Account Details" -> {
                        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            SettingDetailField("Student Name", "Laxmikant Nayak")
                            SettingDetailField("EduLocker ID", "ODIST-KMD-PLBM-LN001")
                            SettingDetailField("Father's Name", "Sachidananda Nayak")
                            SettingDetailField("Registered Mobile", "+91 98614 •••••")
                            SettingDetailField("Email Address", "n.laxmikant1418@gmail.com")
                            SettingDetailField("School / College", "Govt. Higher Secondary School, Phulbani")
                            SettingDetailField("Class & Stream", "Class 12ᵗʰ Science (CHSE)")
                            SettingDetailField("eKYC Status", "Verified via Aadhaar OTP (Block-Locker Sealed)")

                            Spacer(modifier = Modifier.height(10.dp))
                            Button(
                                onClick = { Toast.makeText(context, "Profile details updated in cache", Toast.LENGTH_SHORT).show() },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = OdishaMagenta)
                            ) {
                                Text("Edit Account Info", fontWeight = FontWeight.Bold, fontSize = 13.5.sp)
                            }
                        }
                    }

                    "Privacy Settings" -> {
                        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            SettingToggleRow("Two-Factor Authentication", "Requires 4-digit PIN for scholarship submission", true) {}
                            SettingToggleRow("Aadhaar Stealth Mode", "Masks UIDAI numbers with encrypted token hash", eKycStealth) { eKycStealth = it }
                            SettingToggleRow("Profile Visibility", "Visible only to school teachers and verification officers", true) {}
                            SettingToggleRow("Anonymous Community Posts", "Hides roll number when asking doubt queries", false) {}

                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "🔒 Protected by Odisha State Data Security Framework 2026",
                                color = Color(0xFF059669),
                                fontSize = 11.5.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }

                    "Persionalization & Display" -> {
                        Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                            Text(text = "App Theme Mode", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color(0xFF1E293B))
                            listOf("Canvas Classic (Default)", "Light Crisp", "Dark Slate").forEach { theme ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable { selectedTheme = theme },
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    RadioButton(
                                        selected = (selectedTheme == theme),
                                        onClick = { selectedTheme = theme },
                                        colors = RadioButtonDefaults.colors(selectedColor = OdishaMagenta)
                                    )
                                    Text(text = theme, fontSize = 13.5.sp, color = Color(0xFF334155))
                                }
                            }

                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = "Font Size Adjustment (${fontSizeSlider.toInt()} sp)", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color(0xFF1E293B))
                            Slider(
                                value = fontSizeSlider,
                                onValueChange = { fontSizeSlider = it },
                                valueRange = 12f..20f,
                                colors = SliderDefaults.colors(
                                    thumbColor = OdishaMagenta,
                                    activeTrackColor = OdishaMagenta
                                )
                            )

                            Text(
                                text = "Sample Text: EduLocker makes studying and scholarships effortless.",
                                fontSize = fontSizeSlider.sp,
                                color = Color(0xFF475569)
                            )
                        }
                    }

                    "Accessibility" -> {
                        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            SettingToggleRow("High Contrast Mode", "Enhances text readability with sharp dark borders", highContrastEnabled) { highContrastEnabled = it }
                            SettingToggleRow("Screen Reader (TalkBack)", "Optimizes voice cues for vision impaired students", talkBackEnabled) { talkBackEnabled = it }
                            SettingToggleRow("Haptic Feedback", "Provides subtle vibration on buttons and tabs", hapticEnabled) { hapticEnabled = it }
                            SettingToggleRow("Lesson Subtitles (Captions)", "Auto-enables Odia & English subtitles for videos", true) {}
                        }
                    }

                    "Content Privacy" -> {
                        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            Text(text = "Vault Encryption Standard: AES-256 GCM", fontWeight = FontWeight.Bold, fontSize = 13.5.sp, color = Color(0xFF1E293B))
                            Text(
                                text = "All certificates and study notes uploaded to EduLocker are encrypted locally on device before synchronizing with Odisha State Block-Locker nodes.",
                                fontSize = 12.5.sp,
                                color = Color(0xFF475569),
                                lineHeight = 17.sp
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            SettingToggleRow("Safe Search in EduCreators", "Filters community videos strictly to academic topics", true) {}
                            SettingToggleRow("Auto-Sync Revision Notes", "Keeps cheat-sheets available offline", true) {}
                        }
                    }

                    "Permissions" -> {
                        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            SettingToggleRow("Push Notifications", "For scholarship deadlines & exam alerts", notificationPerm) { notificationPerm = it }
                            SettingToggleRow("Camera Access", "For QR document verification & OCR scanner", cameraPerm) { cameraPerm = it }
                            SettingToggleRow("Photo Library Access", "Zero-permission native photo picker active", true) {}
                        }
                    }

                    "Control & Tools" -> {
                        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            Button(
                                onClick = { Toast.makeText(context, "Cleared 42 MB cached offline PDFs", Toast.LENGTH_SHORT).show() },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF1F5F9))
                            ) {
                                Text("Clear Downloaded PDF Cache (42 MB)", color = Color(0xFF1E293B), fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                            }

                            Button(
                                onClick = {
                                    NotificationHelper.sendAcademicNotification(context, "📄 Portfolio Exported", "Student Portfolio PDF generated successfully.")
                                    Toast.makeText(context, "Exported Student Portfolio PDF to Vault!", Toast.LENGTH_SHORT).show()
                                },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = OdishaMagenta)
                            ) {
                                Icon(Icons.Filled.Download, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Export Student Portfolio (PDF)", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            }
                        }
                    }

                    "Help & Support" -> {
                        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            Text(text = "Govt. of Odisha Higher Education Helpline", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color(0xFF1E293B))
                            Text(text = "📞 Toll-Free: 1800-345-6770 / 155335\n📧 Email: support.edulocker@odisha.gov.in\n⏰ Support Hours: 9:00 AM - 6:00 PM (Mon-Sat)", fontSize = 12.5.sp, color = Color(0xFF475569), lineHeight = 18.sp)

                            Spacer(modifier = Modifier.height(4.dp))
                            Button(
                                onClick = { Toast.makeText(context, "Connecting to Odisha Student Grievance Desk...", Toast.LENGTH_SHORT).show() },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = OdishaMagenta)
                            ) {
                                Text("Raise Grievance Ticket", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            }
                        }
                    }

                    "Privacy Policy & Terms of Use" -> {
                        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            Text(text = "Digital Personal Data Protection (DPDP) Act 2023", fontWeight = FontWeight.Bold, fontSize = 13.5.sp, color = Color(0xFF1E293B))
                            Text(
                                text = "1. Student identity and marksheet credentials are used exclusively for verification and scholarship disbursement.\n2. No commercial third-party trackers or advertising algorithms are utilized.\n3. Encrypted backups reside strictly within MeitY certified Indian data centers.",
                                fontSize = 12.sp,
                                color = Color(0xFF475569),
                                lineHeight = 17.sp
                            )
                        }
                    }

                    "Manage Accounts" -> {
                        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = Color(0xFFECFDF5),
                                border = BorderStroke(1.dp, Color(0xFFA7F3D0)),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    modifier = Modifier.padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Column {
                                        Text(text = "Laxmikant Nayak (Active)", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color(0xFF065F46))
                                        Text(text = "Student Account • Class 12", fontSize = 11.sp, color = Color(0xFF047857))
                                    }
                                    Icon(Icons.Filled.CheckCircle, contentDescription = null, tint = Color(0xFF10B981), modifier = Modifier.size(18.dp))
                                }
                            }

                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = Color(0xFFF8FAFC),
                                border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
                                modifier = Modifier.fillMaxWidth().clickable {
                                    Toast.makeText(context, "Switching to Parent Account...", Toast.LENGTH_SHORT).show()
                                }
                            ) {
                                Row(
                                    modifier = Modifier.padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column {
                                        Text(text = "Sachidananda Nayak", fontWeight = FontWeight.SemiBold, fontSize = 13.sp, color = Color(0xFF334155))
                                        Text(text = "Linked Parent Account", fontSize = 11.sp, color = Color(0xFF64748B))
                                    }
                                }
                            }
                        }
                    }

                    "Add Account" -> {
                        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            Text(text = "Link Sibling or Secondary Student ID", fontWeight = FontWeight.Bold, fontSize = 13.5.sp, color = Color(0xFF1E293B))
                            Text(text = "Enter the 12-digit APAAR ID or EduLocker student identification code to switch seamlessly between family student profiles.", fontSize = 12.sp, color = Color(0xFF475569))

                            Button(
                                onClick = { Toast.makeText(context, "Account verification request initiated", Toast.LENGTH_SHORT).show() },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = OdishaMagenta)
                            ) {
                                Text("+ Link New Student ID", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            }
                        }
                    }

                    "Log Out" -> {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Are you sure you want to log out?",
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = Color(0xFF1E293B)
                            )
                            Text(
                                text = "Your cached documents and scholarship drafts will remain securely stored on device.",
                                fontSize = 12.sp,
                                color = Color(0xFF64748B),
                                textAlign = TextAlign.Center
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                OutlinedButton(
                                    onClick = onDismiss,
                                    modifier = Modifier.weight(1f),
                                    shape = RoundedCornerShape(10.dp)
                                ) {
                                    Text("Cancel", color = Color(0xFF475569))
                                }

                                Button(
                                    onClick = {
                                        Toast.makeText(context, "Logged out successfully", Toast.LENGTH_SHORT).show()
                                        onDismiss()
                                    },
                                    modifier = Modifier.weight(1f),
                                    shape = RoundedCornerShape(10.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE11D48))
                                ) {
                                    Text("Log Out", color = PureWhite, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))
            }
        }
    }
}

@Composable
private fun SettingDetailField(label: String, value: String) {
    Column {
        Text(text = label, color = Color(0xFF64748B), fontSize = 11.5.sp, fontWeight = FontWeight.Medium)
        Text(text = value, color = Color(0xFF1E293B), fontSize = 13.5.sp, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
private fun SettingToggleRow(title: String, subtitle: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f).padding(end = 8.dp)) {
            Text(text = title, fontWeight = FontWeight.SemiBold, fontSize = 13.sp, color = Color(0xFF1E293B))
            Text(text = subtitle, fontSize = 11.sp, color = Color(0xFF64748B))
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(checkedThumbColor = PureWhite, checkedTrackColor = OdishaMagenta)
        )
    }
}

// =========================================================================
// INTERACTIVE PROFILE MODULE DIALOG (Achievements, Experience, Education, Certificates, Share, Drafts)
// =========================================================================
@Composable
private fun ProfileModuleDialog(
    title: String,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .clip(RoundedCornerShape(22.dp)),
            shape = RoundedCornerShape(22.dp),
            color = PureWhite
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = OdishaMagenta.copy(alpha = 0.1f)
                        ) {
                            Text(
                                text = when (title) {
                                    "Achivements" -> "🏆 Achievements"
                                    "Exprience" -> "💼 Experience"
                                    "Education" -> "🎓 Education & Skills"
                                    "Cirtificates" -> "📜 Verified Certificates"
                                    "Share" -> "🔗 Share Profile"
                                    "Drafts" -> "📝 Saved Drafts"
                                    else -> "ℹ️ $title"
                                },
                                color = OdishaMagenta,
                                fontSize = 13.5.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                            )
                        }
                    }

                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.size(30.dp)
                    ) {
                        Icon(Icons.Filled.Close, contentDescription = "Close", tint = Color(0xFF475569), modifier = Modifier.size(20.dp))
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))
                HorizontalDivider(color = Color(0xFFF1F5F9), thickness = 1.dp)
                Spacer(modifier = Modifier.height(14.dp))

                when (title) {
                    "Achivements" -> {
                        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            AchievementCardItem(
                                icon = "🏆",
                                title = "Gold Medalist - State Science Fair 2025",
                                subtitle = "Project: IoT Smart Irrigation System (IIT BBS Mentored)",
                                date = "Dec 2025"
                            )
                            AchievementCardItem(
                                icon = "🥇",
                                title = "State Rank 14 - Science & Math Olympiad",
                                subtitle = "Qualified Tier 1 CBT with 98.6 percentile among 45,000 students",
                                date = "Oct 2026"
                            )
                            AchievementCardItem(
                                icon = "📜",
                                title = "HP LIFE Web Development Level 1 Honors",
                                subtitle = "Scored 100/100 on final assessment • Block-Locker verified",
                                date = "Sept 2026"
                            )
                            AchievementCardItem(
                                icon = "⭐",
                                title = "100% Attendance & Academic Excellence Merit",
                                subtitle = "Awarded by CHSE Odisha & Kendrapada District Collectorate",
                                date = "Annual 2025-26"
                            )
                            AchievementCardItem(
                                icon = "🚀",
                                title = "STEM Innovation Challenge Finalist",
                                subtitle = "Autonomous Obstacle Avoidance Quadcopter Drone prototype",
                                date = "Aug 2026"
                            )
                        }
                    }

                    "Exprience" -> {
                        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            ExperienceCardItem(
                                role = "Student Web Developer Intern",
                                organization = "TechOdisha Youth Innovation Lab",
                                duration = "June 2026 - Present (4 Mos)",
                                description = "Building responsive Android & Kotlin modules for state student portals. Cryptographically integrated digital certificates with QR validation."
                            )
                            ExperienceCardItem(
                                role = "Peer STEM Tutor & Physics Lead",
                                organization = "Govt. Higher Secondary School Science Club",
                                duration = "July 2025 - Present (1 Yr 2 Mos)",
                                description = "Conducted weekly problem-solving masterclasses for Class 11-12 students in Calculus, Electromagnetism, and Olympiad mathematics."
                            )
                            ExperienceCardItem(
                                role = "District Student Ambassador",
                                organization = "EduLocker Odisha e-Governance Cell",
                                duration = "Jan 2026 - Present",
                                description = "Helped over 350 rural students generate their 12-digit APAAR Digital IDs and submit zero-cost scholarship applications."
                            )
                        }
                    }

                    "Education" -> {
                        Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                            Text(text = "Academic Background", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color(0xFF1E293B))

                            EducationCardItem(
                                degree = "Class 12ᵗʰ Higher Secondary (Science)",
                                institution = "Govt. Higher Secondary School, Phulbani (CHSE Odisha)",
                                score = "Current Session 2026-27 • Physics, Chem, Math, CS",
                                status = "In Progress"
                            )

                            EducationCardItem(
                                degree = "Class 10ᵗʰ High School Certificate (Matric)",
                                institution = "Board of Secondary Education (BSE) Odisha",
                                score = "88.4% First Class Distinction (A1 Grade)",
                                status = "Completed (2024)"
                            )

                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = "Key Skills & Technologies", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color(0xFF1E293B))

                            val skills = listOf(
                                "Kotlin & Jetpack Compose", "Python & Automation", "SQL & Database Design",
                                "Advanced Calculus & Integrals", "Electrodynamics & Optics", "Organic Chemistry Mechanisms",
                                "Git & Open Source", "STEM Peer Mentoring", "Public Speaking & Leadership"
                            )

                            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                skills.chunked(2).forEach { rowSkills ->
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        rowSkills.forEach { skill ->
                                            Surface(
                                                shape = RoundedCornerShape(8.dp),
                                                color = Color(0xFFF1F5F9),
                                                modifier = Modifier.weight(1f)
                                            ) {
                                                Text(
                                                    text = "⚡ $skill",
                                                    color = Color(0xFF334155),
                                                    fontSize = 11.5.sp,
                                                    fontWeight = FontWeight.Medium,
                                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                                                    maxLines = 1,
                                                    overflow = TextOverflow.Ellipsis
                                                )
                                            }
                                        }
                                        if (rowSkills.size == 1) {
                                            Spacer(modifier = Modifier.weight(1f))
                                        }
                                    }
                                }
                            }
                        }
                    }

                    "Cirtificates" -> {
                        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            CertificateCardItem(
                                title = "HP LIFE Web Development Certified Credential",
                                issuer = "HP Foundation & EduLocker",
                                certId = "HP-EDU-2026-88192",
                                date = "24 Sept 2026"
                            )
                            CertificateCardItem(
                                title = "BSE Odisha 10ᵗʰ Matriculation Pass Certificate",
                                issuer = "Board of Secondary Education, Odisha",
                                certId = "OD-BSE-2024-981245",
                                date = "May 2024"
                            )
                            CertificateCardItem(
                                title = "State Science Olympiad Tier 1 Merit Rank 14",
                                issuer = "Odisha Academic Council & IIT BBS",
                                certId = "SSO-2026-OD-014",
                                date = "Oct 2026"
                            )
                            CertificateCardItem(
                                title = "APAAR One Nation One Student Digital ID",
                                issuer = "Ministry of Education, Govt. of India",
                                certId = "APAAR-12-8819-01009864",
                                date = "Verified 2026"
                            )
                        }
                    }

                    "Share" -> {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Laxmikant Nayak's Verified Portfolio",
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = Color(0xFF1E293B)
                            )

                            Surface(
                                shape = RoundedCornerShape(16.dp),
                                color = Color(0xFFF8FAFC),
                                border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
                                modifier = Modifier.size(140.dp)
                            ) {
                                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                    Icon(Icons.Filled.QrCode, contentDescription = "QR", tint = Color(0xFF1E293B), modifier = Modifier.size(100.dp))
                                }
                            }

                            Text(
                                text = "https://edulocker.odisha.gov.in/p/ODIST-KMD-LN001",
                                color = OdishaMagenta,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                OutlinedButton(
                                    onClick = { Toast.makeText(context, "Link copied to clipboard!", Toast.LENGTH_SHORT).show() },
                                    modifier = Modifier.weight(1f),
                                    shape = RoundedCornerShape(10.dp)
                                ) {
                                    Icon(Icons.Filled.ContentCopy, contentDescription = null, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("Copy Link", fontSize = 12.5.sp)
                                }

                                Button(
                                    onClick = { Toast.makeText(context, "Sharing profile via WhatsApp / Telegram / SMS", Toast.LENGTH_SHORT).show() },
                                    modifier = Modifier.weight(1f),
                                    shape = RoundedCornerShape(10.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = OdishaMagenta)
                                ) {
                                    Icon(Icons.Filled.Share, contentDescription = null, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("Share", fontWeight = FontWeight.Bold, fontSize = 12.5.sp)
                                }
                            }
                        }
                    }

                    "Drafts" -> {
                        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            DraftCardItem(
                                title = "Odisha Post-Matric ST/SC Scholarship 2026-27",
                                progress = "Step 7 Completed • ₹9 Fee Paid",
                                date = "Saved 27 Sept 2026",
                                tag = "Scholarship"
                            )
                            DraftCardItem(
                                title = "e-Medhabruti Junior Merit Application",
                                progress = "Academic Details & SAMS Marks Uploaded",
                                date = "Saved 24 Sept 2026",
                                tag = "State Merit"
                            )
                            DraftCardItem(
                                title = "State Science Olympiad Stage 2 Project Proposal",
                                progress = "Synopsis Draft (AI Drone Navigation)",
                                date = "Saved 21 Sept 2026",
                                tag = "Olympiad"
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))
            }
        }
    }
}

// -----------------------------------------------------------
// Card Components for Profile Sub-Modals
// -----------------------------------------------------------
@Composable
private fun AchievementCardItem(icon: String, title: String, subtitle: String, date: String) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color(0xFFF8FAFC),
        border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = icon, fontSize = 24.sp)
            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color(0xFF1E293B))
                Spacer(modifier = Modifier.height(2.dp))
                Text(text = subtitle, fontSize = 11.5.sp, color = Color(0xFF64748B))
                Spacer(modifier = Modifier.height(2.dp))
                Text(text = "📅 $date", fontSize = 10.5.sp, color = OdishaMagenta, fontWeight = FontWeight.Medium)
            }
        }
    }
}

@Composable
private fun ExperienceCardItem(role: String, organization: String, duration: String, description: String) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color(0xFFF8FAFC),
        border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(text = role, fontWeight = FontWeight.Bold, fontSize = 13.5.sp, color = Color(0xFF1E293B))
            Text(text = organization, fontSize = 12.sp, color = OdishaMagenta, fontWeight = FontWeight.SemiBold)
            Text(text = "⏱️ $duration", fontSize = 11.sp, color = Color(0xFF64748B))
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = description, fontSize = 11.5.sp, color = Color(0xFF475569), lineHeight = 16.sp)
        }
    }
}

@Composable
private fun EducationCardItem(degree: String, institution: String, score: String, status: String) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color(0xFFF8FAFC),
        border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = degree, fontWeight = FontWeight.Bold, fontSize = 13.5.sp, color = Color(0xFF1E293B))
                Surface(
                    shape = RoundedCornerShape(4.dp),
                    color = Color(0xFFEFF6FF)
                ) {
                    Text(text = status, color = Color(0xFF2563EB), fontSize = 10.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                }
            }
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = institution, fontSize = 12.sp, color = Color(0xFF64748B))
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = "📊 $score", fontSize = 11.5.sp, color = Color(0xFF059669), fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
private fun CertificateCardItem(title: String, issuer: String, certId: String, date: String) {
    val context = LocalContext.current
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color(0xFFF8FAFC),
        border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color(0xFF1E293B))
                Text(text = "Issued by: $issuer", fontSize = 11.5.sp, color = Color(0xFF64748B))
                Text(text = "Ref: #$certId • $date", fontSize = 10.5.sp, color = OdishaMagenta)
            }
            IconButton(
                onClick = {
                    NotificationHelper.sendAcademicNotification(context, "📄 Certificate Downloaded", "$title saved to EduLocker Vault.")
                    Toast.makeText(context, "Certificate #$certId downloaded to Vault!", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier.size(32.dp)
            ) {
                Icon(Icons.Filled.Download, contentDescription = "Download", tint = OdishaMagenta, modifier = Modifier.size(20.dp))
            }
        }
    }
}

@Composable
private fun DraftCardItem(title: String, progress: String, date: String, tag: String) {
    val context = LocalContext.current
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color(0xFFF8FAFC),
        border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
        modifier = Modifier.fillMaxWidth().clickable {
            Toast.makeText(context, "Resuming draft: $title", Toast.LENGTH_SHORT).show()
        }
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(4.dp),
                    color = Color(0xFFFEF3C7)
                ) {
                    Text(text = tag, color = Color(0xFF92400E), fontSize = 9.5.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                }
                Text(text = date, color = Color(0xFF64748B), fontSize = 10.5.sp)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = title, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color(0xFF1E293B))
            Text(text = "📌 $progress", fontSize = 11.5.sp, color = Color(0xFF059669), fontWeight = FontWeight.Medium)
        }
    }
}
