package com.example.ui.screens

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Accessibility
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddBox
import androidx.compose.material.icons.filled.AutoAwesome
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
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material.icons.outlined.AccountBalance
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.CloudDownload
import androidx.compose.material.icons.outlined.EmojiEvents
import androidx.compose.material.icons.outlined.Fingerprint
import androidx.compose.material.icons.outlined.HelpOutline
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.School
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material.icons.outlined.Translate
import androidx.compose.material.icons.outlined.Tune
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
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import com.example.R
import com.example.ui.theme.CanvasCream
import com.example.ui.theme.DarkBorder
import com.example.ui.theme.OdishaMagenta
import com.example.ui.theme.OdishaMagentaDark
import com.example.ui.theme.PureWhite
import com.example.ui.theme.SoftBlushPink
import com.example.ui.theme.TextDark
import com.example.ui.theme.TextMuted
import com.example.ui.theme.UdyapBlushLight
import com.example.ui.theme.UdyapCrimson
import com.example.ui.theme.UdyapPeachSoft
import com.example.ui.theme.UdyapProfileBorder
import com.example.ui.theme.UdyapProfileCardBg
import com.example.ui.theme.UdyapProfileOrange
import com.example.ui.theme.UdyapVerifiedOrange
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

    var showPortfolioBar by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(PureWhite)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // 1. FULL-BLEED TOP HERO PHOTO BANNER + TOP-RIGHT SETTINGS BUTTON (Exact from 14_ecac9dff.png)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(255.dp)
            ) {
                val heroUri = profile?.photoUri?.takeIf { File(it).exists() }
                    ?: profile?.bannerUri?.takeIf { File(it).exists() }
                if (heroUri != null) {
                    AsyncImage(
                        model = File(heroUri),
                        contentDescription = "Profile Cover",
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable { showPhotoChooserDialog = true },
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.udyap_red_banner),
                        contentDescription = "Default UDYAP Profile Cover",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable { showPhotoChooserDialog = true }
                    )
                }

                // Soft Peach Rounded-Square Settings Button at Top-Right
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .statusBarsPadding()
                        .padding(top = 12.dp, end = 16.dp)
                        .size(42.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(UdyapPeachSoft)
                        .testTag("profile_settings_button")
                        .clickable { onNavigateToAchievements() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Settings,
                        contentDescription = "Settings",
                        tint = Color(0xFF1F1F1F),
                        modifier = Modifier.size(22.dp)
                    )
                }
            }

            // 2. OVERLAPPING GOLD-BORDERED IDENTITY CARDS (Exact from 14_ecac9dff.png)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = (-26).dp)
                    .padding(horizontal = 18.dp)
            ) {
                // Card 1: Name + Verified Shield + School + Location
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    color = UdyapProfileCardBg,
                    border = BorderStroke(1.2.dp, UdyapProfileBorder)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 14.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = profile?.fullName ?: "Laxmikant Nayak",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = TextDark,
                                fontSize = 20.sp
                            )
                            Icon(
                                imageVector = Icons.Filled.VerifiedUser,
                                contentDescription = "Verified",
                                tint = Color(0xFF4B4B4B),
                                modifier = Modifier.size(18.dp)
                            )
                            Text(
                                text = "Verified",
                                style = MaterialTheme.typography.bodySmall,
                                color = TextMuted,
                                fontSize = 12.5.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = profile?.schoolName ?: "Govt. Higher Secondary School",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Normal,
                                color = Color(0xFF424242),
                                fontSize = 13.5.sp
                            )
                            Box(
                                modifier = Modifier
                                    .size(16.dp)
                                    .clip(CircleShape)
                                    .background(UdyapVerifiedOrange),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Check,
                                    contentDescription = "School Verified",
                                    tint = PureWhite,
                                    modifier = Modifier.size(10.dp)
                                )
                            }
                            Text(
                                text = profile?.schoolAor ?: "AOR 56",
                                style = MaterialTheme.typography.labelSmall,
                                color = TextMuted,
                                fontSize = 11.5.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(3.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.LocationOn,
                                contentDescription = null,
                                tint = UdyapCrimson,
                                modifier = Modifier.size(13.dp)
                            )
                            Text(
                                text = profile?.schoolLocation ?: "Phulbani, Kandhamal, Odisha",
                                style = MaterialTheme.typography.bodySmall,
                                color = TextMuted,
                                fontSize = 12.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Card 2: UDYAP ID + User ID + Region + Personal Details (Exact from 14_ecac9dff.png)
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onNavigateToIdCard() },
                    shape = RoundedCornerShape(16.dp),
                    color = UdyapProfileCardBg,
                    border = BorderStroke(1.2.dp, UdyapProfileBorder)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 14.dp)
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(
                                text = "UDYAP ID: ${profile?.eduLockerId ?: "ODIST-KMD-PLBM-LN001"}",
                                fontWeight = FontWeight.Medium,
                                color = UdyapProfileOrange,
                                fontSize = 14.sp
                            )
                            Text(
                                text = "User ID: ${profile?.userId ?: "01009864"}",
                                fontWeight = FontWeight.Medium,
                                color = UdyapProfileOrange,
                                fontSize = 14.sp
                            )
                            Text(
                                text = "Region: ${profile?.region ?: "Kandhamal, Odisha, India"}",
                                fontWeight = FontWeight.Medium,
                                color = UdyapProfileOrange,
                                fontSize = 14.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            DetailTextRow("S/D. of : ${profile?.parentName ?: "Sachidananda Nayak"}")
                            DetailTextRow("D.O.B : 14 . 08 . 2008")
                            DetailTextRow("Blood Group : ${profile?.bloodGroup ?: "B +ve"}")
                            DetailTextRow("Address : ${profile?.address ?: "Phulbani, Kandhamal, Odisha"}")
                            DetailTextRow("Pin Code : ${profile?.pinCode ?: "762001"}")
                            DetailTextRow("Class : 12ᵗʰ Science")
                            DetailTextRow("eKYC Status : ${profile?.ekycStatus ?: "Verified"}")
                        }
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                // 3. SOCIALS & PORTFOLIO MENU ICON ROW (Exact from 14_ecac9dff.png)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SocialCircleBadgeContainer(onClick = { Toast.makeText(context, "GitHub: laxmikant14", Toast.LENGTH_SHORT).show() }) {
                        GitHubIconVector(size = 19.dp)
                    }
                    SocialCircleBadgeContainer(onClick = { Toast.makeText(context, "LinkedIn: laxmikant-nayak", Toast.LENGTH_SHORT).show() }) {
                        Text("in", fontWeight = FontWeight.ExtraBold, fontSize = 16.sp, color = TextDark)
                    }
                    SocialCircleBadgeContainer(onClick = { Toast.makeText(context, "Coding Club / Owl", Toast.LENGTH_SHORT).show() }) {
                        Text("🦉", fontSize = 17.sp)
                    }
                    SocialCircleBadgeContainer(onClick = { Toast.makeText(context, "Unstop: laxmikant1418", Toast.LENGTH_SHORT).show() }) {
                        Text("un", fontWeight = FontWeight.Bold, fontSize = 13.5.sp, color = TextDark)
                    }
                    SocialCircleBadgeContainer(onClick = { Toast.makeText(context, "Link new social profile", Toast.LENGTH_SHORT).show() }) {
                        Icon(Icons.Filled.Add, contentDescription = "Add social", tint = TextDark, modifier = Modifier.size(20.dp))
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .clickable { showPortfolioBar = !showPortfolioBar },
                        contentAlignment = Alignment.Center
                    ) {
                        StaggeredFilterIcon(tint = TextDark)
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Clean Minimal Portfolio Quick Access Chips (Achievements, ID Card, Experience, Education, Certificates)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    PortfolioPillChip("Achievements") { activeModal = "Achivements" }
                    PortfolioPillChip("ID Card") { onNavigateToIdCard() }
                    PortfolioPillChip("Experience") { activeModal = "Exprience" }
                    PortfolioPillChip("Education") { activeModal = "Education" }
                    PortfolioPillChip("Certificates") { activeModal = "Cirtificates" }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // 4. BOTTOM ACTION ROW: Edit profile | Share ....... Drafts
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        ActionPillButton(
                            text = "Edit profile",
                            modifier = Modifier.width(106.dp)
                        ) {
                            showPhotoChooserDialog = true
                        }
                        ActionPillButton(
                            text = "Share",
                            modifier = Modifier.width(74.dp)
                        ) {
                            activeModal = "Share"
                        }
                    }

                    ActionPillButton(
                        text = "Drafts",
                        modifier = Modifier.width(106.dp)
                    ) {
                        activeModal = "Drafts"
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))
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
                                Image(
                                    painter = painterResource(id = R.drawable.udyap_red_banner),
                                    contentDescription = "Default UDYAP Avatar",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
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
                                Icon(Icons.Filled.Delete, contentDescription = null, tint = Color(0xFFF21147), modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Remove Photo", color = Color(0xFFF21147), fontWeight = FontWeight.Bold, fontSize = 13.sp)
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
                                Icon(Icons.Filled.Delete, contentDescription = null, tint = Color(0xFFF21147), modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Reset to Default Candle Banner", color = Color(0xFFF21147), fontWeight = FontWeight.Bold, fontSize = 13.sp)
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
            color = Color(0xFFF21147).copy(alpha = 0.45f),
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
            .size(40.dp)
            .clip(CircleShape)
            .background(UdyapBlushLight)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}

@Composable
private fun PortfolioPillChip(
    text: String,
    onClick: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        color = Color(0xFFF9FAFB),
        border = BorderStroke(1.dp, Color(0xFFECECEC)),
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .clickable { onClick() }
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium,
            color = TextDark,
            fontSize = 12.sp,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp)
        )
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
        color = UdyapPeachSoft
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Normal,
                color = TextDark,
                fontSize = 13.sp
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
// SCREEN 37: Modern Minimal Grouped Settings Screen (Clean iOS/M3 Card Layout)
// =========================================================================
@Composable
fun AchievementsScreen(
    viewModel: EduLockerViewModel? = null,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler { onBack() }

    val context = LocalContext.current
    val profile = viewModel?.studentProfile?.collectAsState()?.value
    var selectedSettingDialog by remember { mutableStateOf<String?>(null) }
    var searchQuery by remember { mutableStateOf("") }
    var isSearchActive by remember { mutableStateOf(false) }
    var biometricLockEnabled by remember { mutableStateOf(true) }
    var selectedLanguage by remember { mutableStateOf("English") }

    val settingsBg = Color(0xFFF4F4F6)
    val dividerColor = Color(0xFFF1F1F4)

    fun matchesSearch(vararg keywords: String): Boolean {
        if (searchQuery.isBlank()) return true
        val q = searchQuery.trim().lowercase()
        return keywords.any { it.lowercase().contains(q) }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(settingsBg)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 18.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(12.dp))

            // 1. TOP HEADER BAR: Circular Back + Centered "Settings" + Circular Search
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFEAEAEF))
                        .testTag("settings_back_button")
                        .clickable { onBack() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color(0xFF18181B),
                        modifier = Modifier.size(20.dp)
                    )
                }

                Text(
                    text = "Settings",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF18181B),
                    fontSize = 17.5.sp
                )

                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(if (isSearchActive) UdyapCrimson.copy(alpha = 0.14f) else Color(0xFFEAEAEF))
                        .testTag("settings_search_button")
                        .clickable {
                            isSearchActive = !isSearchActive
                            if (!isSearchActive) searchQuery = ""
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (isSearchActive) Icons.Filled.Close else Icons.Filled.Search,
                        contentDescription = "Search settings",
                        tint = if (isSearchActive) UdyapCrimson else Color(0xFF18181B),
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            // Inline Search Filter Bar
            AnimatedVisibility(visible = isSearchActive) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                    shape = RoundedCornerShape(16.dp),
                    color = PureWhite
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 14.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = null,
                            tint = Color(0xFF9CA3AF),
                            modifier = Modifier.size(18.dp)
                        )
                        Box(modifier = Modifier.weight(1f)) {
                            if (searchQuery.isEmpty()) {
                                Text(
                                    text = "Search settings, privacy, language...",
                                    color = Color(0xFF9CA3AF),
                                    fontSize = 13.5.sp
                                )
                            }
                            BasicTextField(
                                value = searchQuery,
                                onValueChange = { searchQuery = it },
                                singleLine = true,
                                textStyle = TextStyle(color = Color(0xFF18181B), fontSize = 14.sp),
                                cursorBrush = SolidColor(UdyapCrimson),
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // 2. PROFILE IDENTITY CARD (White Rounded Card with Avatar + Name + Email + Chevron)
            if (searchQuery.isBlank()) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp))
                        .clickable { selectedSettingDialog = "Account Details" },
                    shape = RoundedCornerShape(20.dp),
                    color = PureWhite
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(14.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(48.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFFF3F4F6)),
                                contentAlignment = Alignment.Center
                            ) {
                                val photoUri = profile?.photoUri
                                if (!photoUri.isNullOrEmpty() && File(photoUri).exists()) {
                                    AsyncImage(
                                        model = File(photoUri),
                                        contentDescription = "Profile Avatar",
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier.fillMaxSize()
                                    )
                                } else {
                                    Image(
                                        painter = painterResource(id = R.drawable.udyap_red_banner),
                                        contentDescription = "UDYAP Profile Avatar",
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier.fillMaxSize()
                                    )
                                }
                            }

                            Column {
                                Text(
                                    text = profile?.fullName ?: "Laxmikant Nayak",
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color(0xFF111827),
                                    fontSize = 15.5.sp,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = "n.laxmikant1418@gmail.com",
                                    color = Color(0xFF6B7280),
                                    fontSize = 12.5.sp,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }

                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                            contentDescription = "Open Profile",
                            tint = Color(0xFF9CA3AF),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // 3. SLEEK DARK FEATURE BANNER CARD (UDYAP Verified Plus)
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(22.dp))
                        .clickable { selectedSettingDialog = "UDYAP Verified Plus" },
                    shape = RoundedCornerShape(22.dp),
                    color = Color(0xFF1B1B1F)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 18.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(38.dp)
                                .clip(CircleShape)
                                .background(UdyapCrimson.copy(alpha = 0.18f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.AutoAwesome,
                                contentDescription = null,
                                tint = Color(0xFFFF4D79),
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "UDYAP Verified Student",
                                color = PureWhite,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 14.5.sp
                            )
                            Spacer(modifier = Modifier.height(3.dp))
                            Text(
                                text = "AI study plans, verified certificates & instant scholarship alerts.",
                                color = Color(0xFFA1A1AA),
                                fontSize = 11.5.sp,
                                lineHeight = 15.5.sp
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = Color(0xFF2B2B31),
                            border = BorderStroke(1.dp, Color(0xFF3F3F46)),
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .clickable { selectedSettingDialog = "UDYAP Verified Plus" }
                        ) {
                            Text(
                                text = "Explore ›",
                                color = PureWhite,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }

            // 4. GROUP 1: ACCOUNT, ACADEMICS, BADGES, LINKED ACCOUNTS & BIOMETRIC LOCK
            val group1Visible = matchesSearch("Account Settings", "profile", "Academic", "School", "Badges", "Achievements", "Scholarships", "Bank", "Fingerprint", "Face ID", "Lock", "Notifications")
            if (group1Visible) {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    color = PureWhite
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        if (matchesSearch("Account Settings", "profile", "account", "id")) {
                            ModernSettingsRow(
                                icon = Icons.Outlined.Settings,
                                title = "Account Settings",
                                onClick = { selectedSettingDialog = "Account Details" }
                            )
                            HorizontalDivider(color = dividerColor, thickness = 1.dp, modifier = Modifier.padding(horizontal = 16.dp))
                        }
                        if (matchesSearch("Academic & Linked School", "school", "class", "chse")) {
                            ModernSettingsRow(
                                icon = Icons.Outlined.School,
                                title = "Academic & Linked School",
                                onClick = { selectedSettingDialog = "Academic & Linked School" }
                            )
                            HorizontalDivider(color = dividerColor, thickness = 1.dp, modifier = Modifier.padding(horizontal = 16.dp))
                        }
                        if (matchesSearch("Badges & Achievements", "badges", "awards", "olympiad")) {
                            ModernSettingsRow(
                                icon = Icons.Outlined.EmojiEvents,
                                title = "Badges & Achievements",
                                onClick = { selectedSettingDialog = "Badges & Achievements" }
                            )
                            HorizontalDivider(color = dividerColor, thickness = 1.dp, modifier = Modifier.padding(horizontal = 16.dp))
                        }
                        if (matchesSearch("Linked Scholarships & Bank", "bank", "dbt", "apaar")) {
                            ModernSettingsRow(
                                icon = Icons.Outlined.AccountBalance,
                                title = "Linked Scholarships & Bank",
                                onClick = { selectedSettingDialog = "Linked Scholarships & Bank" }
                            )
                            HorizontalDivider(color = dividerColor, thickness = 1.dp, modifier = Modifier.padding(horizontal = 16.dp))
                        }
                        if (matchesSearch("Notifications & Alerts", "alerts", "reminders", "deadlines")) {
                            ModernSettingsRow(
                                icon = Icons.Outlined.Notifications,
                                title = "Notifications & Alerts",
                                onClick = { selectedSettingDialog = "Notifications & Alerts" }
                            )
                            HorizontalDivider(color = dividerColor, thickness = 1.dp, modifier = Modifier.padding(horizontal = 16.dp))
                        }
                        if (matchesSearch("Enable Fingerprint / App Lock", "fingerprint", "face id", "biometric", "lock")) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        biometricLockEnabled = !biometricLockEnabled
                                        Toast.makeText(
                                            context,
                                            if (biometricLockEnabled) "App Lock enabled" else "App Lock disabled",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                    .padding(horizontal = 16.dp, vertical = 10.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(14.dp),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Icon(
                                        imageVector = Icons.Outlined.Fingerprint,
                                        contentDescription = "App Lock",
                                        tint = Color(0xFF4B5563),
                                        modifier = Modifier.size(21.dp)
                                    )
                                    Text(
                                        text = "Enable Fingerprint / App Lock",
                                        color = Color(0xFF18181B),
                                        fontSize = 14.5.sp,
                                        fontWeight = FontWeight.Normal
                                    )
                                }
                                Switch(
                                    checked = biometricLockEnabled,
                                    onCheckedChange = {
                                        biometricLockEnabled = it
                                        Toast.makeText(
                                            context,
                                            if (it) "App Lock enabled" else "App Lock disabled",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    },
                                    colors = SwitchDefaults.colors(
                                        checkedThumbColor = PureWhite,
                                        checkedTrackColor = Color(0xFF22C55E),
                                        uncheckedThumbColor = PureWhite,
                                        uncheckedTrackColor = Color(0xFFD1D5DB),
                                        uncheckedBorderColor = Color.Transparent
                                    )
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))
            }

            // 5. GROUP 2: PREFERENCES, PRIVACY, STORAGE & HELP
            val group2Visible = matchesSearch("Preferences", "Display", "Theme", "Font", "Privacy Policy", "Security", "Storage", "Downloads", "Help", "Support")
            if (group2Visible) {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    color = PureWhite
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        if (matchesSearch("Preferences", "Display", "Theme", "Font", "Personalization")) {
                            ModernSettingsRow(
                                icon = Icons.Outlined.Tune,
                                title = "Preferences & Display",
                                onClick = { selectedSettingDialog = "Preferences & Display" }
                            )
                            HorizontalDivider(color = dividerColor, thickness = 1.dp, modifier = Modifier.padding(horizontal = 16.dp))
                        }
                        if (matchesSearch("Privacy Policy", "Privacy", "Security", "Permissions")) {
                            ModernSettingsRow(
                                icon = Icons.Outlined.Shield,
                                title = "Privacy & Security",
                                onClick = { selectedSettingDialog = "Privacy & Security" }
                            )
                            HorizontalDivider(color = dividerColor, thickness = 1.dp, modifier = Modifier.padding(horizontal = 16.dp))
                        }
                        if (matchesSearch("Storage & Offline Downloads", "Storage", "Cache", "PDF", "Downloads")) {
                            ModernSettingsRow(
                                icon = Icons.Outlined.CloudDownload,
                                title = "Storage & Downloads",
                                onClick = { selectedSettingDialog = "Storage & Downloads" }
                            )
                            HorizontalDivider(color = dividerColor, thickness = 1.dp, modifier = Modifier.padding(horizontal = 16.dp))
                        }
                        if (matchesSearch("Help & Support", "Helpline", "FAQ", "Support")) {
                            ModernSettingsRow(
                                icon = Icons.Outlined.HelpOutline,
                                title = "Help & Support",
                                onClick = { selectedSettingDialog = "Help & Support" }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))
            }

            // 6. GROUP 3: FEEDBACK, LANGUAGE, ABOUT & LOG OUT
            val group3Visible = matchesSearch("Feedback", "Rate", "Language", "English", "Odia", "Hindi", "About", "Version", "Log Out", "Sign Out")
            if (group3Visible) {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    color = PureWhite
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        if (matchesSearch("Feedback", "Rate", "Review")) {
                            ModernSettingsRow(
                                icon = Icons.Outlined.ChatBubbleOutline,
                                title = "Feedback",
                                onClick = { selectedSettingDialog = "Feedback" }
                            )
                            HorizontalDivider(color = dividerColor, thickness = 1.dp, modifier = Modifier.padding(horizontal = 16.dp))
                        }
                        if (matchesSearch("Language", "English", "Odia", "Hindi")) {
                            ModernSettingsRow(
                                icon = Icons.Outlined.Translate,
                                title = "Language",
                                trailingValue = selectedLanguage,
                                onClick = { selectedSettingDialog = "Language" }
                            )
                            HorizontalDivider(color = dividerColor, thickness = 1.dp, modifier = Modifier.padding(horizontal = 16.dp))
                        }
                        if (matchesSearch("About UDYAP", "Version", "Terms")) {
                            ModernSettingsRow(
                                icon = Icons.Outlined.Info,
                                title = "About UDYAP",
                                trailingValue = "v2.4",
                                onClick = { selectedSettingDialog = "About UDYAP" }
                            )
                            HorizontalDivider(color = dividerColor, thickness = 1.dp, modifier = Modifier.padding(horizontal = 16.dp))
                        }
                        if (matchesSearch("Log Out", "Sign Out", "Exit")) {
                            ModernSettingsRow(
                                icon = Icons.AutoMirrored.Filled.Logout,
                                title = "Log Out",
                                isDestructive = true,
                                showChevron = false,
                                onClick = { selectedSettingDialog = "Log Out" }
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "UDYAP • Unified Digital Youth Advancement Platform",
                color = Color(0xFF9CA3AF),
                fontSize = 11.5.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(28.dp))
        }

        // Interactive Detail Modals for every Settings row
        selectedSettingDialog?.let { settingName ->
            SettingItemDetailDialog(
                title = settingName,
                selectedLanguage = selectedLanguage,
                onLanguageSelected = { lang -> selectedLanguage = lang },
                onConfirmLogout = {
                    selectedSettingDialog = null
                    if (viewModel != null) {
                        viewModel.logout()
                    } else {
                        onBack()
                    }
                },
                onDismiss = { selectedSettingDialog = null }
            )
        }
    }
}

@Composable
private fun ModernSettingsRow(
    icon: ImageVector,
    title: String,
    trailingValue: String? = null,
    isDestructive: Boolean = false,
    showChevron: Boolean = true,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 15.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            modifier = Modifier.weight(1f)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = if (isDestructive) UdyapCrimson else Color(0xFF4B5563),
                modifier = Modifier.size(21.dp)
            )
            Text(
                text = title,
                color = if (isDestructive) UdyapCrimson else Color(0xFF18181B),
                fontSize = 14.5.sp,
                fontWeight = if (isDestructive) FontWeight.Medium else FontWeight.Normal,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            if (!trailingValue.isNullOrEmpty()) {
                Text(
                    text = trailingValue,
                    color = Color(0xFF9CA3AF),
                    fontSize = 13.5.sp
                )
            }
            if (showChevron) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null,
                    tint = Color(0xFF9CA3AF),
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

// -----------------------------------------------------------
// Setting Item Detail Dialog (Interactive Demo Content for each Setting)
// -----------------------------------------------------------
@Composable
private fun SettingItemDetailDialog(
    title: String,
    selectedLanguage: String = "English",
    onLanguageSelected: (String) -> Unit = {},
    onConfirmLogout: () -> Unit = {},
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    var fontSizeSlider by remember { mutableFloatStateOf(14f) }
    var selectedTheme by remember { mutableStateOf("UDYAP Clean Light") }
    var eKycStealth by remember { mutableStateOf(true) }
    var scholarshipAlerts by remember { mutableStateOf(true) }
    var examAlerts by remember { mutableStateOf(true) }
    var quizReminders by remember { mutableStateOf(true) }
    var cameraPerm by remember { mutableStateOf(true) }
    var feedbackStars by remember { mutableIntStateOf(5) }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.92f)
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
                // Dialog Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = title,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp,
                        color = Color(0xFF111827)
                    )
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(Icons.Filled.Close, contentDescription = "Close", tint = Color(0xFF4B5563), modifier = Modifier.size(20.dp))
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))
                HorizontalDivider(color = Color(0xFFF3F4F6), thickness = 1.dp)
                Spacer(modifier = Modifier.height(14.dp))

                when (title) {
                    "Account Details" -> {
                        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            SettingDetailField("Student Name", "Laxmikant Nayak")
                            SettingDetailField("UDYAP ID", "ODIST-KMD-PLBM-LN001")
                            SettingDetailField("Father's Name", "Sachidananda Nayak")
                            SettingDetailField("Registered Mobile", "+91 98614 •••••")
                            SettingDetailField("Email Address", "n.laxmikant1418@gmail.com")
                            SettingDetailField("eKYC Status", "Verified via Aadhaar OTP")

                            Spacer(modifier = Modifier.height(8.dp))
                            Button(
                                onClick = {
                                    Toast.makeText(context, "Account details synced", Toast.LENGTH_SHORT).show()
                                    onDismiss()
                                },
                                modifier = Modifier.fillMaxWidth().height(48.dp),
                                shape = RoundedCornerShape(14.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = UdyapCrimson)
                            ) {
                                Text("Save & Sync Account", color = PureWhite, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                            }
                        }
                    }

                    "UDYAP Verified Plus" -> {
                        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            Text(
                                text = "Your student account is actively verified with full access to all UDYAP tools at zero cost.",
                                color = Color(0xFF4B5563),
                                fontSize = 13.sp,
                                lineHeight = 19.sp
                            )
                            SettingDetailField("Disha AI Advisor", "Unlimited study plans, quizzes & doubt solving")
                            SettingDetailField("Verified Credentials", "Instant SHA-256 digital marksheets & APAAR ID")
                            SettingDetailField("Scholarship Fast-Track", "Priority deadline alerts & 1-click DBT tracking")
                            SettingDetailField("Developer Courses", "Free HD video tracks & completion certificates")
                        }
                    }

                    "Academic & Linked School" -> {
                        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            SettingDetailField("Institution", "Govt. Higher Secondary School, Phulbani")
                            SettingDetailField("Institution Code", "AOR-56 • GHSSP-PLBM-KMD")
                            SettingDetailField("Board Affiliation", "CHSE Odisha")
                            SettingDetailField("Current Class", "Class 12ᵗʰ Science (PCMB + IT)")
                            SettingDetailField("APAAR ID", "7829-4501-9238 (Active)")
                        }
                    }

                    "Badges & Achievements" -> {
                        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            SettingDetailField("State Science Fair 2025", "Gold Medalist — IoT Smart Irrigation")
                            SettingDetailField("Odisha Olympiad Series", "State Rank 14 (98.6 Percentile)")
                            SettingDetailField("UDYAP Developer Program", "Web Development Honors (100/100)")
                            SettingDetailField("Attendance Merit", "100% Academic Excellence Award")
                        }
                    }

                    "Linked Scholarships & Bank" -> {
                        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            SettingDetailField("Active Application", "Odisha Post-Matric Scholarship 2026-27")
                            SettingDetailField("DBT Bank Account", "State Bank of India •••• 4829 (NPCI Seeded)")
                            SettingDetailField("Parent Consent", "Verified — Sachidananda Nayak")
                            SettingDetailField("Disbursal Mode", "Direct Benefit Transfer (Active)")
                        }
                    }

                    "Notifications & Alerts" -> {
                        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            SettingToggleRow("Scholarship Deadlines", "Alerts 7 days before closing dates", scholarshipAlerts) { scholarshipAlerts = it }
                            SettingToggleRow("Exam & Admit Card Drops", "CHSE, JEE, NEET & OJEE live notices", examAlerts) { examAlerts = it }
                            SettingToggleRow("Daily Study & Quiz Reminders", "Gentle morning revision nudges from Disha", quizReminders) { quizReminders = it }
                        }
                    }

                    "Preferences & Display" -> {
                        Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                            Text(text = "App Theme", fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = Color(0xFF18181B))
                            listOf("UDYAP Clean Light", "Warm Cream", "System Default").forEach { theme ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable { selectedTheme = theme },
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    RadioButton(
                                        selected = (selectedTheme == theme),
                                        onClick = { selectedTheme = theme },
                                        colors = RadioButtonDefaults.colors(selectedColor = UdyapCrimson)
                                    )
                                    Text(text = theme, fontSize = 13.5.sp, color = Color(0xFF374151))
                                }
                            }

                            Text(text = "Text Size (${fontSizeSlider.toInt()} sp)", fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = Color(0xFF18181B))
                            Slider(
                                value = fontSizeSlider,
                                onValueChange = { fontSizeSlider = it },
                                valueRange = 12f..20f,
                                colors = SliderDefaults.colors(
                                    thumbColor = UdyapCrimson,
                                    activeTrackColor = UdyapCrimson
                                )
                            )
                            Text(
                                text = "Preview: UDYAP keeps your learning simple and organized.",
                                fontSize = fontSizeSlider.sp,
                                color = Color(0xFF4B5563)
                            )
                        }
                    }

                    "Privacy & Security" -> {
                        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            SettingToggleRow("Aadhaar Masking", "Hides full Aadhaar digits in document preview", eKycStealth) { eKycStealth = it }
                            SettingToggleRow("Camera Verification Access", "Used only during QR scan & Video e-KYC", cameraPerm) { cameraPerm = it }
                            SettingToggleRow("Profile Visibility", "Only visible to verified teachers & institution", true) {}
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "DPDP Act 2023 Compliant • Zero third-party ad trackers. Your documents stay encrypted on-device.",
                                color = Color(0xFF059669),
                                fontSize = 11.5.sp,
                                lineHeight = 16.sp
                            )
                        }
                    }

                    "Storage & Downloads" -> {
                        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            SettingDetailField("Offline Documents", "18.4 MB (6 verified records)")
                            SettingDetailField("Course Cache", "24.0 MB (2 downloaded lessons)")
                            Spacer(modifier = Modifier.height(4.dp))
                            Button(
                                onClick = { Toast.makeText(context, "Cleared 24 MB temporary cache", Toast.LENGTH_SHORT).show() },
                                modifier = Modifier.fillMaxWidth().height(46.dp),
                                shape = RoundedCornerShape(14.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF3F4F6))
                            ) {
                                Text("Clear Temporary Cache", color = Color(0xFF18181B), fontWeight = FontWeight.SemiBold, fontSize = 13.5.sp)
                            }
                            Button(
                                onClick = {
                                    NotificationHelper.sendAcademicNotification(context, "Portfolio Exported", "Student Portfolio PDF generated.")
                                    Toast.makeText(context, "Student Portfolio PDF exported!", Toast.LENGTH_SHORT).show()
                                },
                                modifier = Modifier.fillMaxWidth().height(46.dp),
                                shape = RoundedCornerShape(14.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = UdyapCrimson)
                            ) {
                                Icon(Icons.Filled.Download, contentDescription = null, tint = PureWhite, modifier = Modifier.size(17.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Export Student Portfolio (PDF)", color = PureWhite, fontWeight = FontWeight.SemiBold, fontSize = 13.5.sp)
                            }
                        }
                    }

                    "Help & Support" -> {
                        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            SettingDetailField("Student Helpline", "1800-345-6770 (Toll-Free)")
                            SettingDetailField("Support Email", "support@udyap.in")
                            SettingDetailField("Hours", "Mon–Sat, 9:00 AM – 6:00 PM")
                            Spacer(modifier = Modifier.height(6.dp))
                            Button(
                                onClick = {
                                    Toast.makeText(context, "Support ticket created — response within 24 hrs", Toast.LENGTH_SHORT).show()
                                    onDismiss()
                                },
                                modifier = Modifier.fillMaxWidth().height(46.dp),
                                shape = RoundedCornerShape(14.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = UdyapCrimson)
                            ) {
                                Text("Contact Support", color = PureWhite, fontWeight = FontWeight.SemiBold, fontSize = 13.5.sp)
                            }
                        }
                    }

                    "Feedback" -> {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "How is your experience with UDYAP?",
                                color = Color(0xFF4B5563),
                                fontSize = 13.5.sp
                            )
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                (1..5).forEach { star ->
                                    Icon(
                                        imageVector = Icons.Filled.Star,
                                        contentDescription = "$star star",
                                        tint = if (star <= feedbackStars) Color(0xFFF59E0B) else Color(0xFFE5E7EB),
                                        modifier = Modifier
                                            .size(34.dp)
                                            .clickable { feedbackStars = star }
                                    )
                                }
                            }
                            Button(
                                onClick = {
                                    Toast.makeText(context, "Thank you for your $feedbackStars-star rating!", Toast.LENGTH_SHORT).show()
                                    onDismiss()
                                },
                                modifier = Modifier.fillMaxWidth().height(46.dp),
                                shape = RoundedCornerShape(14.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = UdyapCrimson)
                            ) {
                                Text("Submit Feedback", color = PureWhite, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                            }
                        }
                    }

                    "Language" -> {
                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            listOf("English", "ଓଡ଼ିଆ (Odia)", "हिन्दी (Hindi)").forEach { lang ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(12.dp))
                                        .clickable {
                                            onLanguageSelected(lang.substringBefore(" "))
                                            Toast.makeText(context, "Language set to $lang", Toast.LENGTH_SHORT).show()
                                            onDismiss()
                                        }
                                        .padding(vertical = 8.dp, horizontal = 6.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(text = lang, fontSize = 14.5.sp, color = Color(0xFF18181B))
                                    RadioButton(
                                        selected = lang.startsWith(selectedLanguage),
                                        onClick = {
                                            onLanguageSelected(lang.substringBefore(" "))
                                            onDismiss()
                                        },
                                        colors = RadioButtonDefaults.colors(selectedColor = UdyapCrimson)
                                    )
                                }
                            }
                        }
                    }

                    "About UDYAP" -> {
                        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            SettingDetailField("Application", "UDYAP — Unified Digital Youth Advancement Platform")
                            SettingDetailField("Version", "2.4.0 (Production Build)")
                            SettingDetailField("Data Security", "Local-first Room + Encrypted Preferences")
                        }
                    }

                    "Log Out" -> {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Log out of UDYAP?",
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 15.5.sp,
                                color = Color(0xFF18181B)
                            )
                            Text(
                                text = "Next time you open the app, you will be asked to sign in again.",
                                fontSize = 12.5.sp,
                                color = Color(0xFF6B7280),
                                textAlign = TextAlign.Center
                            )
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                OutlinedButton(
                                    onClick = onDismiss,
                                    modifier = Modifier.weight(1f).height(46.dp),
                                    shape = RoundedCornerShape(12.dp),
                                    border = BorderStroke(1.dp, Color(0xFFE5E7EB))
                                ) {
                                    Text("Cancel", color = Color(0xFF374151))
                                }
                                Button(
                                    onClick = {
                                        Toast.makeText(context, "Logged out successfully", Toast.LENGTH_SHORT).show()
                                        onConfirmLogout()
                                    },
                                    modifier = Modifier.weight(1f).height(46.dp),
                                    shape = RoundedCornerShape(12.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = UdyapCrimson)
                                ) {
                                    Text("Log Out", color = PureWhite, fontWeight = FontWeight.SemiBold)
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
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
                                organization = "UDYAP Odisha e-Governance Cell",
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
                                issuer = "HP Foundation & UDYAP",
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
                                text = "https://udyap.odisha.gov.in/p/ODIST-KMD-LN001",
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
                    NotificationHelper.sendAcademicNotification(context, "📄 Certificate Downloaded", "$title saved to UDYAP Vault.")
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
