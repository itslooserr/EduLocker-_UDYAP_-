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
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.example.ui.components.PostPhotoViewerData
import com.example.ui.components.PostPhotoViewerModal
import com.example.ui.theme.CanvasCream
import com.example.ui.theme.OdishaMagenta
import com.example.ui.theme.PureWhite
import com.example.ui.theme.TextDark
import com.example.ui.viewmodel.EduLockerViewModel
import java.io.File

// =========================================================================
// SCREEN 42: School & College Profile (Dynamic with Photo Customization)
// =========================================================================
@Composable
fun SchoolDetailScreen(
    viewModel: EduLockerViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableStateOf("Galary") }
    var showEnquiryDialog by remember { mutableStateOf(false) }
    var enquiryMessage by remember { mutableStateOf("") }
    val context = LocalContext.current
    val customAssets by viewModel.customProfileAssets.collectAsState()
    val customImages by viewModel.customPostImages.collectAsState()
    val institution by viewModel.selectedInstitution.collectAsState()

    var showPhotoDialog by remember { mutableStateOf(false) }
    var showBannerDialog by remember { mutableStateOf(false) }
    var activeViewerData by remember { mutableStateOf<PostPhotoViewerData?>(null) }

    val schoolKey = "school_${institution.id}"
    val schoolLogoUri = customAssets["avatar_$schoolKey"] ?: customAssets["avatar_school"]
    val schoolBannerUri = customAssets["banner_$schoolKey"] ?: customAssets["banner_school"]

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            viewModel.uploadProfileAsset("avatar_$schoolKey", uri)
            Toast.makeText(context, "${institution.name} emblem updated!", Toast.LENGTH_SHORT).show()
        }
    }

    val bannerPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            viewModel.uploadProfileAsset("banner_$schoolKey", uri)
            Toast.makeText(context, "${institution.name} campus banner updated!", Toast.LENGTH_SHORT).show()
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
            // 1. TOP SCHOOL CAMPUS BUILDING HERO PHOTO
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(210.dp)
            ) {
                if (!schoolBannerUri.isNullOrEmpty() && File(schoolBannerUri).exists()) {
                    AsyncImage(
                        model = File(schoolBannerUri),
                        contentDescription = "School Banner",
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable { showBannerDialog = true },
                        contentScale = ContentScale.Crop
                    )
                } else {
                    SchoolCampusBannerCanvas(
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable { showBannerDialog = true }
                    )
                }

                // Back Button on top left
                IconButton(
                    onClick = onBack,
                    modifier = Modifier
                        .padding(top = 36.dp, start = 16.dp)
                        .size(38.dp)
                        .clip(CircleShape)
                        .background(PureWhite.copy(alpha = 0.9f))
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = TextDark,
                        modifier = Modifier.size(20.dp)
                    )
                }

                // Overlapping Circular School Emblem Badge on bottom-left
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
                            .background(Color(0xFFFFF7ED))
                            .clickable { showPhotoDialog = true },
                        contentAlignment = Alignment.Center
                    ) {
                        if (!schoolLogoUri.isNullOrEmpty() && File(schoolLogoUri).exists()) {
                            AsyncImage(
                                model = File(schoolLogoUri),
                                contentDescription = "School Emblem",
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(CircleShape),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            SchoolEmblemLogo(size = 80.dp)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(44.dp))

            // 2. SCHOOL DETAILS (Dynamic from Selected Institution)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 22.dp)
            ) {
                // School Name & Code Badge
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = institution.name,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF0F172A),
                        fontSize = 20.sp,
                        lineHeight = 24.sp,
                        modifier = Modifier.weight(1f)
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(5.dp),
                        modifier = Modifier.padding(top = 4.dp, start = 8.dp)
                    ) {
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
                            text = institution.code.split("•").first().trim(),
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF334155),
                            fontSize = 11.5.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                // Location Pin
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(text = "📍", fontSize = 12.sp)
                    Text(
                        text = "${institution.type} • ${institution.location}",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF64748B),
                        fontSize = 12.sp
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Description
                Text(
                    text = institution.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF475569),
                    fontSize = 13.sp,
                    lineHeight = 17.5.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                // School ID
                Text(
                    text = "Institution ID: ${institution.code}",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF64748B),
                    fontSize = 12.5.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Action Buttons Row: "Get in Touch", "Enquiry Now", "Share"
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        modifier = Modifier
                            .weight(1.1f)
                            .height(42.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .clickable {
                                Toast.makeText(context, "Direct line to ${institution.name} office opened", Toast.LENGTH_SHORT).show()
                            },
                        shape = RoundedCornerShape(12.dp),
                        color = OdishaMagenta
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text("Get in Touch", color = PureWhite, fontWeight = FontWeight.Bold, fontSize = 13.5.sp)
                        }
                    }

                    Surface(
                        modifier = Modifier
                            .weight(1.1f)
                            .height(42.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .clickable { showEnquiryDialog = true },
                        shape = RoundedCornerShape(12.dp),
                        color = Color(0xFFFCE7F3)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text("Enquiry Now", color = Color(0xFF334155), fontWeight = FontWeight.Bold, fontSize = 13.5.sp)
                        }
                    }

                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(0xFFFCE7F3))
                            .clickable { Toast.makeText(context, "${institution.name} profile link copied", Toast.LENGTH_SHORT).show() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Filled.Share, contentDescription = "Share", tint = Color(0xFF1E293B), modifier = Modifier.size(20.dp))
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                // Socials Section
                Text(
                    text = "Socials & Portals",
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

                Spacer(modifier = Modifier.height(18.dp))

                // Seats & Stats Line
                Text(
                    text = institution.courses.joinToString(" | "),
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF475569),
                    fontSize = 12.5.sp
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.padding(top = 2.dp)
                ) {
                    Text(
                        text = "Head: ${institution.principalName} | Affiliated: CHSE/Govt",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF475569),
                        fontSize = 12.5.sp
                    )
                    Text(
                        text = "More.",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFE11D48),
                        fontSize = 12.5.sp,
                        modifier = Modifier.clickable {
                            Toast.makeText(context, "All academic statistics loaded for ${institution.name}", Toast.LENGTH_SHORT).show()
                        }
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Tabs: Galary | Highlights | Details
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .clickable { selectedTab = "Galary" },
                        shape = RoundedCornerShape(10.dp),
                        color = if (selectedTab == "Galary") Color(0xFFFCE7F3) else Color.Transparent
                    ) {
                        Text(
                            text = "Galary",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1E293B),
                            modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
                        )
                    }

                    Text(
                        text = "Highlights",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Medium,
                        color = if (selectedTab == "Highlights") Color(0xFF1E293B) else Color(0xFF64748B),
                        modifier = Modifier
                            .clickable { selectedTab = "Highlights" }
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    )

                    Text(
                        text = "Details",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Medium,
                        color = if (selectedTab == "Details") Color(0xFF1E293B) else Color(0xFF64748B),
                        modifier = Modifier
                            .clickable { selectedTab = "Details" }
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                // 6 Photo Gallery Grid (Clicking any photo opens full interactive modal with 3-dot design options)
                val galleryItems = listOf(
                    PostPhotoViewerData(
                        id = "${institution.id}_photo_1",
                        title = "Staff & Principal Council",
                        author = institution.name,
                        authorHandle = "@${institution.id}",
                        timestamp = "Academic Year 2026",
                        description = "Faculty assembly & annual planning session in the main council chamber.",
                        defaultGradient = listOf(Color(0xFF0F766E), Color(0xFF115E59))
                    ),
                    PostPhotoViewerData(
                        id = "${institution.id}_photo_2",
                        title = "Morning Assembly",
                        author = institution.name,
                        authorHandle = "@${institution.id}",
                        timestamp = "Daily 09:30 AM",
                        description = "Full student cohort morning assembly with prayer and state anthem.",
                        defaultGradient = listOf(Color(0xFF15803D), Color(0xFF166534))
                    ),
                    PostPhotoViewerData(
                        id = "${institution.id}_photo_3",
                        title = "NCC Cadet Parade",
                        author = institution.name,
                        authorHandle = "@${institution.id}",
                        timestamp = "Youth Wing",
                        description = "NCC Army & Naval wings annual drill training and awards ceremony.",
                        defaultGradient = listOf(Color(0xFFB45309), Color(0xFF78350F))
                    ),
                    PostPhotoViewerData(
                        id = "${institution.id}_photo_4",
                        title = "Republic Day Flag Ceremony",
                        author = institution.name,
                        authorHandle = "@${institution.id}",
                        timestamp = "National Event",
                        description = "Flag hoisting ceremony celebrating Odisha state excellence and student honors.",
                        defaultGradient = listOf(Color(0xFFC2410C), Color(0xFF9A3412))
                    ),
                    PostPhotoViewerData(
                        id = "${institution.id}_photo_5",
                        title = "Main Campus Gate",
                        author = institution.name,
                        authorHandle = "@${institution.id}",
                        timestamp = "Main Entrance",
                        description = "Grand entrance archway welcoming students, staff, and state guests.",
                        defaultGradient = listOf(Color(0xFF047857), Color(0xFF065F46))
                    ),
                    PostPhotoViewerData(
                        id = "${institution.id}_photo_6",
                        title = "Science & Innovation Lab",
                        author = institution.name,
                        authorHandle = "@${institution.id}",
                        timestamp = "STEM Lab",
                        description = "Advanced physics, chemistry and computer science laboratories.",
                        defaultGradient = listOf(Color(0xFF0284C7), Color(0xFF075985))
                    )
                )

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        galleryItems.take(3).forEach { item ->
                            val customImg = customImages[item.id]
                            SchoolPhotoCard(
                                title = item.title,
                                bgGradient = item.defaultGradient,
                                label = item.title.split(" ").take(2).joinToString(" "),
                                customImageUri = customImg,
                                modifier = Modifier.weight(1f),
                                onClick = { activeViewerData = item }
                            )
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        galleryItems.drop(3).take(3).forEach { item ->
                            val customImg = customImages[item.id]
                            SchoolPhotoCard(
                                title = item.title,
                                bgGradient = item.defaultGradient,
                                label = item.title.split(" ").take(2).joinToString(" "),
                                customImageUri = customImg,
                                modifier = Modifier.weight(1f),
                                onClick = { activeViewerData = item }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(84.dp))
            }
        }

        // Active Photo Post Viewer Modal (Enlarge + 3-Dot Photo Change)
        activeViewerData?.let { data ->
            PostPhotoViewerModal(
                viewModel = viewModel,
                data = data,
                onDismiss = { activeViewerData = null }
            )
        }

        // Enquiry Dialog Modal
        if (showEnquiryDialog) {
            Dialog(onDismissRequest = { showEnquiryDialog = false }) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = CanvasCream)
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Official School Enquiry", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                            IconButton(onClick = { showEnquiryDialog = false }) {
                                Icon(Icons.Filled.Close, contentDescription = "Close")
                            }
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        OutlinedTextField(
                            value = enquiryMessage,
                            onValueChange = { enquiryMessage = it },
                            placeholder = { Text("Enter your message for ${institution.name}...") },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = PureWhite,
                                unfocusedContainerColor = PureWhite
                            )
                        )
                        Spacer(modifier = Modifier.height(14.dp))
                        Button(
                            onClick = {
                                Toast.makeText(context, "Enquiry submitted to ${institution.name}!", Toast.LENGTH_SHORT).show()
                                showEnquiryDialog = false
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = OdishaMagenta)
                        ) {
                            Text("Submit", color = PureWhite, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        // Photo Dialog
        if (showPhotoDialog) {
            Dialog(onDismissRequest = { showPhotoDialog = false }) {
                Surface(
                    modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(20.dp)),
                    shape = RoundedCornerShape(20.dp),
                    color = PureWhite
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("School Emblem / Logo", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = TextDark, fontSize = 17.sp)
                        Text("Upload or change school emblem from gallery", fontSize = 11.5.sp, color = Color(0xFF64748B), modifier = Modifier.padding(top = 4.dp, bottom = 16.dp))

                        Button(
                            onClick = {
                                showPhotoDialog = false
                                photoPickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                            },
                            modifier = Modifier.fillMaxWidth().height(48.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = OdishaMagenta)
                        ) {
                            Icon(Icons.Filled.PhotoLibrary, contentDescription = null, tint = PureWhite, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Upload Emblem from Gallery", color = PureWhite, fontWeight = FontWeight.Bold, fontSize = 13.5.sp)
                        }

                        if (!schoolLogoUri.isNullOrEmpty()) {
                            Spacer(modifier = Modifier.height(10.dp))
                            OutlinedButton(
                                onClick = {
                                    viewModel.removeProfileAsset("avatar_$schoolKey")
                                    viewModel.removeProfileAsset("avatar_school")
                                    showPhotoDialog = false
                                },
                                modifier = Modifier.fillMaxWidth().height(44.dp),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Icon(Icons.Filled.Delete, contentDescription = null, tint = Color(0xFFE11D48), modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Remove Emblem", color = Color(0xFFE11D48), fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                        OutlinedButton(
                            onClick = { showPhotoDialog = false },
                            modifier = Modifier.fillMaxWidth().height(44.dp),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("Cancel", color = TextDark, fontSize = 13.sp)
                        }
                    }
                }
            }
        }

        // Banner Dialog
        if (showBannerDialog) {
            Dialog(onDismissRequest = { showBannerDialog = false }) {
                Surface(
                    modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(20.dp)),
                    shape = RoundedCornerShape(20.dp),
                    color = PureWhite
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("School Campus Banner", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = TextDark, fontSize = 17.sp)
                        Text("Upload or change campus building cover from gallery", fontSize = 11.5.sp, color = Color(0xFF64748B), modifier = Modifier.padding(top = 4.dp, bottom = 16.dp))

                        Button(
                            onClick = {
                                showBannerDialog = false
                                bannerPickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                            },
                            modifier = Modifier.fillMaxWidth().height(48.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = OdishaMagenta)
                        ) {
                            Icon(Icons.Filled.PhotoLibrary, contentDescription = null, tint = PureWhite, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Upload Banner from Gallery", color = PureWhite, fontWeight = FontWeight.Bold, fontSize = 13.5.sp)
                        }

                        if (!schoolBannerUri.isNullOrEmpty()) {
                            Spacer(modifier = Modifier.height(10.dp))
                            OutlinedButton(
                                onClick = {
                                    viewModel.removeProfileAsset("banner_$schoolKey")
                                    viewModel.removeProfileAsset("banner_school")
                                    showBannerDialog = false
                                },
                                modifier = Modifier.fillMaxWidth().height(44.dp),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Icon(Icons.Filled.Delete, contentDescription = null, tint = Color(0xFFE11D48), modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Reset Banner to Default Campus", color = Color(0xFFE11D48), fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                        OutlinedButton(
                            onClick = { showBannerDialog = false },
                            modifier = Modifier.fillMaxWidth().height(44.dp),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("Cancel", color = TextDark, fontSize = 13.sp)
                        }
                    }
                }
            }
        }
    }
}

// -----------------------------------------------------------
// SCHOOL CAMPUS BANNER CANVAS
// -----------------------------------------------------------
@Composable
fun SchoolCampusBannerCanvas(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        // Sky background
        drawRect(
            brush = Brush.verticalGradient(
                colors = listOf(Color(0xFFBAE6FD), Color(0xFFE0F2FE), Color(0xFFF1F5F9))
            )
        )

        // Green two-story building facade (Govt Autonomous College Phulbani)
        val buildingTop = h * 0.22f
        drawRect(
            color = Color(0xFF86EFAC),
            topLeft = Offset(0f, buildingTop),
            size = Size(w, h - buildingTop)
        )

        // Central elevated tower with flag pole
        drawRect(
            color = Color(0xFF4ADE80),
            topLeft = Offset(w * 0.32f, h * 0.08f),
            size = Size(w * 0.36f, h * 0.92f)
        )

        // Blue window frames & doors
        val windowColor = Color(0xFF1E3A8A)
        drawRoundRect(
            color = windowColor,
            topLeft = Offset(w * 0.40f, h * 0.30f),
            size = Size(w * 0.20f, h * 0.22f),
            cornerRadius = CornerRadius(4.dp.toPx(), 4.dp.toPx())
        )
        drawRoundRect(
            color = windowColor,
            topLeft = Offset(w * 0.42f, h * 0.65f),
            size = Size(w * 0.16f, h * 0.35f),
            cornerRadius = CornerRadius(4.dp.toPx(), 4.dp.toPx())
        )

        // Side windows
        drawRect(color = windowColor, topLeft = Offset(w * 0.12f, h * 0.38f), size = Size(w * 0.12f, h * 0.16f))
        drawRect(color = windowColor, topLeft = Offset(w * 0.76f, h * 0.38f), size = Size(w * 0.12f, h * 0.16f))
    }
}

// -----------------------------------------------------------
// SCHOOL EMBLEM CREST
// -----------------------------------------------------------
@Composable
fun SchoolEmblemLogo(size: androidx.compose.ui.unit.Dp) {
    Canvas(modifier = Modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height
        val center = Offset(w / 2, h / 2)

        // Outer circular rings
        drawCircle(color = PureWhite, radius = w * 0.48f, center = center)
        drawCircle(color = Color(0xFFDC2626), radius = w * 0.46f, center = center, style = Stroke(width = 2.dp.toPx()))
        drawCircle(color = Color(0xFFDC2626), radius = w * 0.36f, center = center, style = Stroke(width = 1.5.dp.toPx()))

        // Inner quadrants (green & yellow)
        drawArc(color = Color(0xFFFEF08A), startAngle = 180f, sweepAngle = 90f, useCenter = true, topLeft = Offset(w * 0.16f, h * 0.16f), size = Size(w * 0.68f, h * 0.68f))
        drawArc(color = Color(0xFF86EFAC), startAngle = 90f, sweepAngle = 90f, useCenter = true, topLeft = Offset(w * 0.16f, h * 0.16f), size = Size(w * 0.68f, h * 0.68f))
        drawArc(color = Color(0xFFFEF08A), startAngle = 0f, sweepAngle = 90f, useCenter = true, topLeft = Offset(w * 0.16f, h * 0.16f), size = Size(w * 0.68f, h * 0.68f))
        drawArc(color = Color(0xFF86EFAC), startAngle = 270f, sweepAngle = 90f, useCenter = true, topLeft = Offset(w * 0.16f, h * 0.16f), size = Size(w * 0.68f, h * 0.68f))

        // Center Torch / Book symbol
        drawCircle(color = Color(0xFFDC2626), radius = 2.5.dp.toPx(), center = center)
    }
}

// -----------------------------------------------------------
// GALLERY PHOTO CARD (Interactive with Custom User Image Support)
// -----------------------------------------------------------
@Composable
private fun SchoolPhotoCard(
    title: String,
    bgGradient: List<Color>,
    label: String,
    customImageUri: String? = null,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier
            .aspectRatio(0.95f)
            .clip(RoundedCornerShape(10.dp))
            .clickable { onClick() },
        shape = RoundedCornerShape(10.dp),
        color = Color(0xFFF1F5F9)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            if (!customImageUri.isNullOrEmpty() && File(customImageUri).exists()) {
                AsyncImage(
                    model = File(customImageUri),
                    contentDescription = title,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    drawRect(brush = Brush.verticalGradient(colors = bgGradient))
                    // Decorative scene shapes
                    drawCircle(color = PureWhite.copy(alpha = 0.15f), radius = size.width * 0.4f, center = Offset(size.width * 0.8f, size.height * 0.2f))
                    drawRect(color = Color.Black.copy(alpha = 0.25f), topLeft = Offset(0f, size.height * 0.70f), size = Size(size.width, size.height * 0.30f))
                }
            }

            // Photo label at bottom
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .background(Color.Black.copy(alpha = 0.55f))
                    .padding(horizontal = 4.dp, vertical = 3.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = label,
                    color = PureWhite,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1
                )
            }
        }
    }
}

