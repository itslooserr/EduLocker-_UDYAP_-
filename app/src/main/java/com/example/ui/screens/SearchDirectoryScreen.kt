package com.example.ui.screens

import android.widget.Toast
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
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Reply
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CastForEducation
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.DesignServices
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.FactCheck
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Style
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Icon
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
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.DocumentPreviewDialog
import com.example.ui.theme.DarkBorder
import com.example.ui.theme.HairlineDivider
import com.example.ui.theme.NewsreaderFontFamily
import com.example.ui.theme.PureWhite
import com.example.ui.theme.TextDark
import com.example.ui.theme.TextMuted
import com.example.ui.theme.UdyapCrimson
import com.example.ui.theme.UdyapGreenCta
import com.example.ui.theme.UdyapPeachSoft
import com.example.ui.theme.UdyapVerifiedOrange
import com.example.ui.viewmodel.EduLockerViewModel

data class SchoolItem(
    val id: String,
    val name: String,
    val location: String,
    val code: String,
    val type: String
)

data class ScholarshipItem(
    val id: String,
    val title: String,
    val authority: String,
    val reward: String,
    val deadline: String,
    val isOlympiad: Boolean = false
)

// =========================================================================
// UDYAP SEARCH & RESOURCE DIRECTORY (Redesigned cleanly per 11_fed48d83.png)
// =========================================================================
@Composable
fun SearchDirectoryScreen(
    viewModel: EduLockerViewModel,
    onNavigateToSchoolProfile: () -> Unit,
    onNavigateToDeveloperProgram: () -> Unit,
    onNavigateToAiGuide: () -> Unit,
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    var showFilterBar by remember { mutableStateOf(false) }
    var captionExpanded by remember { mutableStateOf(false) }
    val posts by viewModel.socialPosts.collectAsState()
    val documents by viewModel.digitalDocuments.collectAsState()
    val selectedDoc by viewModel.selectedDocument.collectAsState()
    val schoolPost = posts.firstOrNull()
    val context = LocalContext.current

    val allInstitutions = viewModel.allInstitutions

    val scholarshipsList = remember {
        listOf(
            ScholarshipItem("sch_1", "Asha Foundation Science Olympiad 2026", "Asha Educational Trust", "₹50,000 + Certificate", "Nov 2026", isOlympiad = true),
            ScholarshipItem("sch_2", "Post Matric Scholarship (PRERANA Odisha)", "ST & SC Development Dept, Odisha", "100% Tuition Waiver", "31 Oct 2026"),
            ScholarshipItem("sch_3", "e-Medhabruti Merit Scholarship", "Higher Education Dept, Odisha", "₹10,000 / Year", "15 Dec 2026"),
            ScholarshipItem("sch_4", "National Means-cum-Merit Scholarship (NMMS)", "Ministry of Education, Govt. of India", "₹12,000 / Year", "20 Nov 2026"),
            ScholarshipItem("sch_5", "Kalinga Youth Science & Coding Fellowship", "Odisha Bigyan Academy & HP LIFE", "₹25,000 + Internship", "Ongoing")
        )
    }

    val queryTrimmed = searchQuery.trim().lowercase()
    val isSearching = queryTrimmed.isNotEmpty()

    val filteredSchools = if (isSearching) {
        allInstitutions.filter {
            it.name.lowercase().contains(queryTrimmed) ||
                it.location.lowercase().contains(queryTrimmed) ||
                it.type.lowercase().contains(queryTrimmed) ||
                it.code.lowercase().contains(queryTrimmed)
        }
    } else emptyList()

    val filteredScholarships = if (isSearching) {
        scholarshipsList.filter {
            it.title.lowercase().contains(queryTrimmed) ||
                it.authority.lowercase().contains(queryTrimmed)
        }
    } else emptyList()

    val filteredDocuments = if (isSearching) {
        documents.filter {
            it.title.lowercase().contains(queryTrimmed) ||
                it.subtitle.lowercase().contains(queryTrimmed) ||
                it.issuedBy.lowercase().contains(queryTrimmed)
        }
    } else emptyList()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(PureWhite)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 18.dp)
        ) {
            Spacer(modifier = Modifier.height(12.dp))

            // 1. CLEAN PILL SEARCH BAR + FILTER BUTTON (Exact from 11_fed48d83.png)
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(46.dp)
                        .clip(RoundedCornerShape(50.dp))
                        .border(1.1.dp, DarkBorder, RoundedCornerShape(50.dp))
                        .background(PureWhite)
                        .padding(horizontal = 16.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Box(modifier = Modifier.weight(1f)) {
                            if (searchQuery.isEmpty()) {
                                Text(
                                    text = "Document, Scholarship, School, etc..",
                                    color = Color(0xFF7B7B7B),
                                    fontSize = 13.sp,
                                    maxLines = 1
                                )
                            }
                            BasicTextField(
                                value = searchQuery,
                                onValueChange = { searchQuery = it },
                                singleLine = true,
                                cursorBrush = SolidColor(UdyapCrimson),
                                textStyle = TextStyle(
                                    color = TextDark,
                                    fontSize = 13.5.sp
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("search_directory_input")
                            )
                        }

                        if (searchQuery.isNotEmpty()) {
                            Icon(
                                imageVector = Icons.Filled.Close,
                                contentDescription = "Clear search",
                                tint = TextDark,
                                modifier = Modifier
                                    .size(20.dp)
                                    .clickable { searchQuery = "" }
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Filled.Search,
                                contentDescription = "Search",
                                tint = Color(0xFF333333),
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    }
                }

                // Filter icon (3 staggered horizontal bars matching 11_fed48d83.png)
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .clickable { showFilterBar = !showFilterBar },
                    contentAlignment = Alignment.Center
                ) {
                    StaggeredFilterIcon(tint = Color(0xFF2B2B2B))
                }
            }

            if (showFilterBar) {
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf("Adarsha" to "Schools", "Aadhaar" to "Documents", "Olympiad" to "Olympiads", "Scholarship" to "Grants").forEach { (q, label) ->
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = UdyapPeachSoft,
                            modifier = Modifier.clickable { searchQuery = q }
                        ) {
                            Text(
                                text = label,
                                fontSize = 12.sp,
                                color = TextDark,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (isSearching) {
                // LIVE FILTERED RESULTS
                Text(
                    text = "Results for \"$searchQuery\"",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = TextDark
                )
                Spacer(modifier = Modifier.height(12.dp))

                if (filteredSchools.isNotEmpty()) {
                    Text("Schools & Institutions", fontWeight = FontWeight.SemiBold, color = TextMuted, fontSize = 12.5.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    filteredSchools.forEach { school ->
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp)
                                .clickable {
                                    viewModel.selectInstitution(school)
                                    onNavigateToSchoolProfile()
                                },
                            shape = RoundedCornerShape(14.dp),
                            color = PureWhite,
                            border = BorderStroke(1.dp, HairlineDivider)
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(38.dp)
                                        .clip(CircleShape)
                                        .background(UdyapPeachSoft),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(Icons.Filled.School, contentDescription = null, tint = UdyapCrimson, modifier = Modifier.size(20.dp))
                                }
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(school.name, fontWeight = FontWeight.SemiBold, color = TextDark, fontSize = 13.5.sp)
                                    Text("${school.location} • ${school.code}", color = TextMuted, fontSize = 11.5.sp)
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }

                if (filteredDocuments.isNotEmpty()) {
                    Text("UDYAP Vault Documents", fontWeight = FontWeight.SemiBold, color = TextMuted, fontSize = 12.5.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    filteredDocuments.forEach { doc ->
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp)
                                .clickable { viewModel.openDocument(doc) },
                            shape = RoundedCornerShape(14.dp),
                            color = PureWhite,
                            border = BorderStroke(1.dp, HairlineDivider)
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Icon(Icons.Filled.Description, contentDescription = null, tint = UdyapCrimson, modifier = Modifier.size(22.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(doc.title, fontWeight = FontWeight.SemiBold, color = TextDark, fontSize = 13.5.sp)
                                    Text(doc.issuedBy, color = TextMuted, fontSize = 11.5.sp)
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }

                if (filteredScholarships.isNotEmpty()) {
                    Text("Scholarships & Olympiads", fontWeight = FontWeight.SemiBold, color = TextMuted, fontSize = 12.5.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    filteredScholarships.forEach { sch ->
                        ScholarshipListRow(sch) {
                            if (sch.isOlympiad) viewModel.navigateToPage(25) else viewModel.navigateToPage(14)
                        }
                    }
                }

                if (filteredSchools.isEmpty() && filteredDocuments.isEmpty() && filteredScholarships.isEmpty()) {
                    Text(
                        text = "No matches for \"$searchQuery\". Try \"Phulbani\", \"Aadhaar\", \"Marksheet\", or \"Olympiad\".",
                        color = TextMuted,
                        fontSize = 13.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 32.dp)
                    )
                }
            } else {
                // =============================================================
                // DEFAULT FEED: SCHOOL POST + 2x5 RESOURCES GRID + SCHOLARSHIPS
                // =============================================================
                // School Post Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier
                            .weight(1f)
                            .clickable { onNavigateToSchoolProfile() }
                    ) {
                        // Authentic School Seal Emblem
                        SchoolSealEmblem(modifier = Modifier.size(44.dp))

                        Column {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(5.dp)
                            ) {
                                Text(
                                    text = "Govt. Higher Secondary School",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Normal,
                                    color = TextDark,
                                    fontSize = 14.sp
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
                                        contentDescription = "Verified Institution",
                                        tint = PureWhite,
                                        modifier = Modifier.size(10.dp)
                                    )
                                }
                                Text(
                                    text = "AOR 56",
                                    color = TextMuted,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(3.dp),
                                modifier = Modifier.padding(top = 2.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.LocationOn,
                                    contentDescription = null,
                                    tint = UdyapCrimson,
                                    modifier = Modifier.size(13.dp)
                                )
                                Text(
                                    text = "Phulbani, Kandhamal, Odisha",
                                    color = TextMuted,
                                    fontSize = 11.5.sp
                                )
                            }
                        }
                    }

                    Icon(
                        imageVector = Icons.Filled.MoreHoriz,
                        contentDescription = "More options",
                        tint = TextDark,
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {
                                Toast.makeText(context, "Save post • Report • Visit School Profile", Toast.LENGTH_SHORT).show()
                            }
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Campus Photo Card (Lush Green Odisha School Campus matching 11_fed48d83.png)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(210.dp)
                        .clip(RoundedCornerShape(18.dp))
                        .clickable { onNavigateToSchoolProfile() }
                ) {
                    SchoolCampusArtwork(modifier = Modifier.fillMaxSize())
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Engagement Row: Crimson Thumbs-Up + Heart-Bubble Circles + Like Count + Soft Peach Share Pill
                val likesCount = schoolPost?.likesCount ?: 582
                val isLiked = schoolPost?.isLiked ?: false
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(30.dp)
                                .clip(CircleShape)
                                .background(UdyapCrimson)
                                .clickable { schoolPost?.let { viewModel.togglePostLike(it.id) } },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.ThumbUp,
                                contentDescription = "Like",
                                tint = PureWhite,
                                modifier = Modifier.size(15.dp)
                            )
                        }

                        Box(
                            modifier = Modifier
                                .size(30.dp)
                                .clip(CircleShape)
                                .background(UdyapCrimson)
                                .clickable { schoolPost?.let { viewModel.togglePostLike(it.id) } },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Favorite,
                                contentDescription = "Love",
                                tint = if (isLiked) Color(0xFFFFE4EC) else PureWhite,
                                modifier = Modifier.size(15.dp)
                            )
                        }

                        Text(
                            text = "liked by $likesCount Users",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF4A4A4A),
                            fontSize = 12.sp
                        )
                    }

                    // Soft Peach Share Pill
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = UdyapPeachSoft,
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .clickable {
                                Toast.makeText(context, "Shared GHSS Phulbani update via UDYAP", Toast.LENGTH_SHORT).show()
                            }
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 7.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(5.dp)
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.Reply,
                                contentDescription = "Share",
                                tint = TextDark,
                                modifier = Modifier
                                    .size(16.dp)
                                    .graphicsLayer { scaleX = -1f }
                            )
                            Text(
                                text = "Share",
                                color = TextDark,
                                fontWeight = FontWeight.Medium,
                                fontSize = 12.5.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Caption with orange "more."
                val fullCaption = "Celebrating Swachhata Abhiyan in school is a fantastic way to instill the value of hygiene, responsibility, and civic pride among all Class 11 & 12 science and humanities students across Kandhamal."
                val shortCaption = "Celebrating Swachhata Abhiyan in school is a fantastic way to instill the value of hygiene, re..  "
                Text(
                    text = buildAnnotatedString {
                        withStyle(SpanStyle(fontWeight = FontWeight.Bold, color = TextDark)) {
                            append("Govt. Higher Secondary School: ")
                        }
                        withStyle(SpanStyle(color = Color(0xFF424242))) {
                            append(if (captionExpanded) fullCaption else shortCaption)
                        }
                        if (!captionExpanded) {
                            withStyle(SpanStyle(color = UdyapVerifiedOrange, fontWeight = FontWeight.Medium)) {
                                append("more.")
                            }
                        }
                    },
                    style = MaterialTheme.typography.bodySmall,
                    fontSize = 12.5.sp,
                    lineHeight = 18.sp,
                    modifier = Modifier.clickable { captionExpanded = !captionExpanded }
                )

                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "1hr ago",
                    color = TextMuted,
                    fontSize = 11.sp
                )

                Spacer(modifier = Modifier.height(22.dp))

                // 2. "Resources" SECTION (Exact 2x5 Soft Peach Rounded-Square Tiles from 11_fed48d83.png)
                Text(
                    text = "Resources",
                    fontFamily = NewsreaderFontFamily,
                    fontWeight = FontWeight.Normal,
                    color = TextDark,
                    fontSize = 23.sp
                )

                Spacer(modifier = Modifier.height(14.dp))

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Row 1 (5 tiles)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        ResourceSquareTile(
                            modifier = Modifier.weight(1f),
                            onClick = { searchQuery = "Marksheet" }
                        ) {
                            Icon(Icons.Filled.MenuBook, contentDescription = "Study Library", tint = Color(0xFF262626), modifier = Modifier.size(26.dp))
                        }
                        ResourceSquareTile(
                            modifier = Modifier.weight(1f),
                            onClick = onNavigateToDeveloperProgram
                        ) {
                            Icon(Icons.Filled.CastForEducation, contentDescription = "Live Classes", tint = Color(0xFF262626), modifier = Modifier.size(26.dp))
                        }
                        ResourceSquareTile(
                            modifier = Modifier.weight(1f),
                            onClick = { searchQuery = "Certificate" }
                        ) {
                            Icon(Icons.Filled.Style, contentDescription = "Revision Decks", tint = Color(0xFF262626), modifier = Modifier.size(26.dp))
                        }
                        ResourceSquareTile(
                            modifier = Modifier.weight(1f),
                            onClick = { Toast.makeText(context, "UDYAP Creative Design Studio", Toast.LENGTH_SHORT).show() }
                        ) {
                            Icon(Icons.Filled.DesignServices, contentDescription = "Design Studio", tint = Color(0xFF262626), modifier = Modifier.size(26.dp))
                        }
                        ResourceSquareTile(
                            modifier = Modifier.weight(1f),
                            onClick = { viewModel.navigateToPage(25) }
                        ) {
                            Icon(Icons.Filled.FactCheck, contentDescription = "Practice Tests", tint = Color(0xFF262626), modifier = Modifier.size(26.dp))
                        }
                    }

                    // Row 2 (5 tiles)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        ResourceSquareTile(
                            modifier = Modifier.weight(1f),
                            onClick = onNavigateToDeveloperProgram
                        ) {
                            Icon(Icons.Filled.Code, contentDescription = "Dev Coding", tint = Color(0xFF262626), modifier = Modifier.size(26.dp))
                        }
                        ResourceSquareTile(
                            modifier = Modifier.weight(1f),
                            onClick = onNavigateToAiGuide
                        ) {
                            Icon(Icons.Filled.Psychology, contentDescription = "Disha AI Tools", tint = Color(0xFF262626), modifier = Modifier.size(26.dp))
                        }
                        ResourceSquareTile(
                            modifier = Modifier.weight(1f),
                            onClick = { viewModel.navigateToPage(14) }
                        ) {
                            Icon(Icons.Filled.RocketLaunch, contentDescription = "Career Launch", tint = Color(0xFF262626), modifier = Modifier.size(26.dp))
                        }
                        ResourceSquareTile(
                            modifier = Modifier.weight(1f),
                            onClick = { viewModel.navigateToPage(25) }
                        ) {
                            Icon(Icons.Filled.EmojiEvents, contentDescription = "Competitions", tint = Color(0xFF262626), modifier = Modifier.size(26.dp))
                        }
                        ResourceSquareTile(
                            modifier = Modifier.weight(1f),
                            onClick = {
                                searchQuery = "a"
                                Toast.makeText(context, "Showing full UDYAP resource directory", Toast.LENGTH_SHORT).show()
                            }
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(Icons.Filled.Add, contentDescription = "More", tint = Color(0xFF262626), modifier = Modifier.size(22.dp))
                                Text("More", fontSize = 10.sp, color = Color(0xFF333333), fontWeight = FontWeight.Medium)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // 3. "Scholarships" SECTION
                Text(
                    text = "Scholarships",
                    fontFamily = NewsreaderFontFamily,
                    fontWeight = FontWeight.Normal,
                    color = TextDark,
                    fontSize = 23.sp
                )

                Spacer(modifier = Modifier.height(12.dp))

                scholarshipsList.forEach { sch ->
                    ScholarshipListRow(sch) {
                        if (sch.isOlympiad) viewModel.navigateToPage(25) else viewModel.navigateToPage(14)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }

        selectedDoc?.let { doc ->
            DocumentPreviewDialog(
                doc = doc,
                onDismiss = { viewModel.selectedDocument.value = null }
            )
        }
    }
}

@Composable
private fun ResourceSquareTile(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Surface(
        modifier = modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        color = UdyapPeachSoft
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            content()
        }
    }
}

@Composable
private fun ScholarshipListRow(
    item: ScholarshipItem,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp)
            .clip(RoundedCornerShape(14.dp))
            .clickable { onClick() },
        shape = RoundedCornerShape(14.dp),
        color = PureWhite,
        border = BorderStroke(1.dp, HairlineDivider)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = TextDark
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = item.authority,
                    style = MaterialTheme.typography.bodySmall,
                    color = TextMuted,
                    fontSize = 11.5.sp
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = item.reward,
                    color = UdyapGreenCta,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp
                )
                Text(
                    text = item.deadline,
                    color = UdyapCrimson,
                    fontSize = 10.5.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
fun StaggeredFilterIcon(tint: Color, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier.size(22.dp, 18.dp)) {
        val stroke = 2.2.dp.toPx()
        drawLine(tint, Offset(0f, 3.dp.toPx()), Offset(size.width * 0.65f, 3.dp.toPx()), stroke, StrokeCap.Round)
        drawLine(tint, Offset(size.width * 0.15f, size.height * 0.5f), Offset(size.width, size.height * 0.5f), stroke, StrokeCap.Round)
        drawLine(tint, Offset(size.width * 0.45f, size.height - 3.dp.toPx()), Offset(size.width, size.height - 3.dp.toPx()), stroke, StrokeCap.Round)
    }
}

@Composable
private fun SchoolSealEmblem(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val r = size.minDimension / 2f
        val c = Offset(r, r)
        drawCircle(color = Color(0xFFFFF8E7), radius = r, center = c)
        drawCircle(color = Color(0xFF8B1E24), radius = r - 1.5.dp.toPx(), center = c, style = Stroke(2.dp.toPx()))
        drawCircle(color = Color(0xFFD97706), radius = r * 0.65f, center = c, style = Stroke(1.5.dp.toPx()))
        drawArc(Color(0xFF15803D), 180f, 180f, true, Offset(c.x - r * 0.45f, c.y - r * 0.45f), Size(r * 0.9f, r * 0.9f))
        drawArc(Color(0xFFF59E0B), 0f, 180f, true, Offset(c.x - r * 0.45f, c.y - r * 0.45f), Size(r * 0.9f, r * 0.9f))
        drawCircle(color = PureWhite, radius = r * 0.16f, center = c)
    }
}

@Composable
private fun SchoolCampusArtwork(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        // Soft sunlit sky & courtyard ground
        drawRect(
            brush = Brush.verticalGradient(
                listOf(Color(0xFF93C572), Color(0xFFD9CBA8), Color(0xFFC2B28F))
            )
        )
        // Lush green campus trees on left
        drawCircle(Color(0xFF1E5631), radius = w * 0.24f, center = Offset(w * 0.08f, h * 0.25f))
        drawCircle(Color(0xFF2D7A3E), radius = w * 0.20f, center = Offset(w * 0.24f, h * 0.15f))
        drawCircle(Color(0xFF3E9C4F), radius = w * 0.16f, center = Offset(w * 0.14f, h * 0.52f))
        // Courtyard pathway
        val path = Path().apply {
            moveTo(w * 0.36f, 0f)
            lineTo(w * 0.56f, 0f)
            lineTo(w * 0.68f, h)
            lineTo(w * 0.24f, h)
            close()
        }
        drawPath(path, color = Color(0xFFE5D8BC))
        // Mint-green two-story school building wing on right (matching 11_fed48d83.png)
        val buildingPath = Path().apply {
            moveTo(w * 0.52f, h * 0.02f)
            lineTo(w, h * 0.02f)
            lineTo(w, h * 0.96f)
            lineTo(w * 0.56f, h * 0.72f)
            close()
        }
        drawPath(buildingPath, color = Color(0xFFA7E3A7))
        // Building roof shadow & corridor
        drawLine(Color(0xFF6B9E6B), Offset(w * 0.53f, h * 0.38f), Offset(w, h * 0.56f), 4.dp.toPx())
        // Dark blue classroom windows along upper & lower stories
        val windowOffsets = listOf(
            Offset(w * 0.62f, h * 0.16f) to Size(w * 0.08f, h * 0.15f),
            Offset(w * 0.74f, h * 0.22f) to Size(w * 0.09f, h * 0.17f),
            Offset(w * 0.87f, h * 0.28f) to Size(w * 0.10f, h * 0.20f),
            Offset(w * 0.63f, h * 0.50f) to Size(w * 0.08f, h * 0.16f),
            Offset(w * 0.76f, h * 0.58f) to Size(w * 0.09f, h * 0.18f),
            Offset(w * 0.89f, h * 0.68f) to Size(w * 0.09f, h * 0.20f)
        )
        windowOffsets.forEach { (pos, sz) ->
            drawRoundRect(Color(0xFF1E3A5F), topLeft = pos, size = sz, cornerRadius = CornerRadius(3.dp.toPx()))
            drawRoundRect(Color(0xFF38BDF8).copy(alpha = 0.35f), topLeft = pos + Offset(2f, 2f), size = Size(sz.width * 0.45f, sz.height * 0.7f))
        }
        // Central school flagpole pedestal (white/red monument matching courtyard)
        drawRoundRect(
            color = Color(0xFFF3EFE6),
            topLeft = Offset(w * 0.42f, h * 0.72f),
            size = Size(w * 0.15f, h * 0.22f),
            cornerRadius = CornerRadius(4.dp.toPx())
        )
        drawRect(
            color = UdyapCrimson.copy(alpha = 0.85f),
            topLeft = Offset(w * 0.45f, h * 0.76f),
            size = Size(w * 0.09f, h * 0.12f)
        )
    }
}
