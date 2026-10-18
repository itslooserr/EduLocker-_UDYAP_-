package com.example.ui.screens

import android.widget.Toast
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.DocumentItemEntity
import com.example.ui.components.DocumentPreviewDialog
import com.example.ui.theme.CanvasCream
import com.example.ui.theme.DarkBorder
import com.example.ui.theme.OdishaMagenta
import com.example.ui.theme.PureWhite
import com.example.ui.theme.TextDark
import com.example.ui.theme.TextMuted
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
// SCREEN 27: Search & Educational Resource Directory
// Cleanly positioned Top Search Bar + Seamless Content Below
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
    val posts by viewModel.socialPosts.collectAsState()
    val documents by viewModel.digitalDocuments.collectAsState()
    val selectedDoc by viewModel.selectedDocument.collectAsState()
    val schoolPost = posts.firstOrNull() ?: return
    val context = LocalContext.current

    val allInstitutions = viewModel.allInstitutions

    val scholarshipsList = remember {
        listOf(
            ScholarshipItem("sch_1", "Asha Foundation Science Olympiad 2026", "Asha Educational Trust", "₹50,000 + Certificate", "Nov 2026", isOlympiad = true),
            ScholarshipItem("sch_2", "Post Matric Scholarship (PRERANA Odisha)", "ST & SC Development Dept, Odisha", "100% Tuition Fee Waiver", "31 Oct 2026"),
            ScholarshipItem("sch_3", "Biju Yuba Sashaktikaran Medhabruti", "Higher Education Dept, Odisha", "₹10,000 / Year", "15 Dec 2026"),
            ScholarshipItem("sch_4", "National Means-cum-Merit Scholarship (NMMS)", "Ministry of Education, Govt. of India", "₹12,000 / Year", "20 Nov 2026"),
            ScholarshipItem("sch_5", "Kalinga Youth Science & Coding Fellowship", "Odisha Bigyan Academy & HP LIFE", "₹25,000 + HP Internship", "Ongoing")
        )
    }

    // Filtered lists based on search query
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
        scholarshipsList.filter { it.title.lowercase().contains(queryTrimmed) || it.authority.lowercase().contains(queryTrimmed) }
    } else emptyList()

    val filteredDocuments = if (isSearching) {
        documents.filter { it.title.lowercase().contains(queryTrimmed) || it.subtitle.lowercase().contains(queryTrimmed) || it.issuedBy.lowercase().contains(queryTrimmed) }
    } else emptyList()

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
            // 1. TOP HEADER & SEARCH BAR (Clean, spacious top alignment)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(OdishaMagenta)
                    .padding(top = 36.dp, bottom = 20.dp, start = 20.dp, end = 20.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = "Search & Directory",
                                color = PureWhite,
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 20.sp
                            )
                            Text(
                                text = "Find Schools, Scholarships & Documents",
                                color = PureWhite.copy(alpha = 0.85f),
                                fontSize = 12.sp
                            )
                        }

                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(PureWhite.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.School,
                                contentDescription = null,
                                tint = PureWhite,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Clean Search Capsule Bar
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .clip(RoundedCornerShape(50.dp))
                            .border(1.5.dp, DarkBorder, RoundedCornerShape(50.dp)),
                        placeholder = {
                            Text(
                                text = "Search School, Scholarship, Marksheet, etc..",
                                color = TextMuted,
                                fontSize = 13.sp,
                                maxLines = 1
                            )
                        },
                        trailingIcon = {
                            if (searchQuery.isNotEmpty()) {
                                IconButton(onClick = { searchQuery = "" }) {
                                    Icon(
                                        imageVector = Icons.Filled.Close,
                                        contentDescription = "Clear",
                                        tint = TextDark,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            } else {
                                Icon(
                                    imageVector = Icons.Filled.Search,
                                    contentDescription = "Search",
                                    tint = TextDark,
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(50.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = PureWhite,
                            unfocusedContainerColor = PureWhite,
                            disabledContainerColor = PureWhite,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedTextColor = TextDark,
                            unfocusedTextColor = TextDark
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // =========================================================================
            // SEARCH RESULTS VIEW (When user types e.g. "a", "s", "mark", "scholarship")
            // =========================================================================
            if (isSearching) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                ) {
                    Text(
                        text = "Results for \"$searchQuery\"",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF111827),
                        fontSize = 16.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // 1. SCHOOLS & INSTITUTIONS SECTION
                    if (filteredSchools.isNotEmpty()) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(Icons.Filled.School, contentDescription = null, tint = Color(0xFF0284C7), modifier = Modifier.size(18.dp))
                            Text(
                                text = "Schools & Institutions (${filteredSchools.size})",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = Color(0xFF0369A1)
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))

                        filteredSchools.forEach { school ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                                    .clickable {
                                        viewModel.selectInstitution(school)
                                        onNavigateToSchoolProfile()
                                        Toast.makeText(context, "Opening ${school.name}", Toast.LENGTH_SHORT).show()
                                    },
                                shape = RoundedCornerShape(14.dp),
                                colors = CardDefaults.cardColors(containerColor = PureWhite)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(school.name, fontWeight = FontWeight.Bold, fontSize = 13.5.sp, color = Color(0xFF1E293B))
                                        Text("${school.type} • ${school.location}", fontSize = 11.5.sp, color = Color(0xFF64748B))
                                    }
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(Color(0xFFE0F2FE))
                                            .padding(horizontal = 8.dp, vertical = 4.dp)
                                    ) {
                                        Text(school.code.split("•").first().trim(), fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0284C7))
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))
                    }

                    // 2. SCHOLARSHIPS & OLYMPIADS SECTION
                    if (filteredScholarships.isNotEmpty()) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(Icons.Filled.EmojiEvents, contentDescription = null, tint = OdishaMagenta, modifier = Modifier.size(18.dp))
                            Text(
                                text = "Scholarships & Competitions (${filteredScholarships.size})",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = OdishaMagenta
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))

                        filteredScholarships.forEach { sch ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                                    .clickable {
                                        if (sch.isOlympiad) {
                                            viewModel.navigateToPage(24)
                                        } else {
                                            viewModel.navigateToPage(12)
                                        }
                                        Toast.makeText(context, "Opening ${sch.title}", Toast.LENGTH_SHORT).show()
                                    },
                                shape = RoundedCornerShape(14.dp),
                                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF1F2))
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(sch.title, fontWeight = FontWeight.Bold, fontSize = 13.5.sp, color = Color(0xFF881337))
                                        Text(sch.authority, fontSize = 11.5.sp, color = Color(0xFF9F1239))
                                        Spacer(modifier = Modifier.height(2.dp))
                                        Text("Reward: ${sch.reward}", fontSize = 11.sp, fontWeight = FontWeight.Medium, color = Color(0xFFE11D48))
                                    }
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(OdishaMagenta)
                                            .padding(horizontal = 10.dp, vertical = 6.dp)
                                    ) {
                                        Text("Apply", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = PureWhite)
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))
                    }

                    // 3. VERIFIED DOCUMENTS SECTION (20 Items)
                    if (filteredDocuments.isNotEmpty()) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(Icons.Filled.Description, contentDescription = null, tint = Color(0xFF059669), modifier = Modifier.size(18.dp))
                            Text(
                                text = "Verified Locker Documents (${filteredDocuments.size})",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = Color(0xFF047857)
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))

                        filteredDocuments.forEach { doc ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                                    .clickable {
                                        viewModel.openDocument(doc)
                                    },
                                shape = RoundedCornerShape(14.dp),
                                colors = CardDefaults.cardColors(containerColor = PureWhite)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(36.dp)
                                                .clip(RoundedCornerShape(8.dp))
                                                .background(Color(0xFFDCFCE7)),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Icon(Icons.Filled.Description, contentDescription = null, tint = Color(0xFF15803D), modifier = Modifier.size(20.dp))
                                        }
                                        Column {
                                            Text(doc.title, fontWeight = FontWeight.Bold, fontSize = 13.5.sp, color = Color(0xFF1E293B))
                                            Text(doc.subtitle, fontSize = 11.5.sp, color = Color(0xFF64748B), maxLines = 1)
                                            Text("No: ${doc.documentNo}", fontSize = 10.5.sp, color = Color(0xFF059669))
                                        }
                                    }
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(Color(0xFFF1F5F9))
                                            .padding(horizontal = 8.dp, vertical = 4.dp)
                                    ) {
                                        Text("View", fontSize = 11.5.sp, fontWeight = FontWeight.Bold, color = Color(0xFF334155))
                                    }
                                }
                            }
                        }
                    }

                    if (filteredSchools.isEmpty() && filteredScholarships.isEmpty() && filteredDocuments.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "No results found for \"$searchQuery\". Try searching \"Adarsha\", \"Olympiad\", \"Marksheet\", \"Aadhaar\", or \"PAN\".",
                                fontSize = 13.sp,
                                color = Color(0xFF64748B),
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center
                            )
                        }
                    }
                }
            } else {
                // =========================================================================
                // DEFAULT VIEW: Quick Search Chips + GHSS Post + 8 Resources Grid
                // =========================================================================
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                ) {
                    // Quick Search Suggestion Pills
                    Text(
                        text = "Quick Searches",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF475569),
                        fontSize = 12.5.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Surface(
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .clickable { searchQuery = "Adarsha" },
                            shape = RoundedCornerShape(20.dp),
                            color = Color(0xFFE0F2FE)
                        ) {
                            Text("🏫 Adarsha Vidyalaya", fontSize = 11.5.sp, color = Color(0xFF0369A1), modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp))
                        }

                        Surface(
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .clickable { searchQuery = "Aadhaar" },
                            shape = RoundedCornerShape(20.dp),
                            color = Color(0xFFFEF3C7)
                        ) {
                            Text("📄 Aadhaar Card", fontSize = 11.5.sp, color = Color(0xFFB45309), modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp))
                        }

                        Surface(
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .clickable { searchQuery = "Olympiad" },
                            shape = RoundedCornerShape(20.dp),
                            color = Color(0xFFFCE7F3)
                        ) {
                            Text("🏆 Olympiad", fontSize = 11.5.sp, color = OdishaMagenta, modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp))
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // MAIN GHSS PHULBANI POST CARD (Exact from 27.png)
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(18.dp),
                        colors = CardDefaults.cardColors(containerColor = PureWhite)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp)
                        ) {
                            // Header: School Profile Icon + Name + AOR + More Icon
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                                    modifier = Modifier.clickable { onNavigateToSchoolProfile() }
                                ) {
                                    // Circular Logo with School Icon
                                    Box(
                                        modifier = Modifier
                                            .size(38.dp)
                                            .clip(CircleShape)
                                            .background(OdishaMagenta),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.School,
                                            contentDescription = "School Logo",
                                            tint = PureWhite,
                                            modifier = Modifier.size(22.dp)
                                        )
                                    }

                                    Column {
                                        Text(
                                            text = schoolPost.author,
                                            style = MaterialTheme.typography.titleSmall,
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFF111827),
                                            fontSize = 13.5.sp
                                        )
                                        Text(
                                            text = schoolPost.handle,
                                            style = MaterialTheme.typography.bodySmall,
                                            color = Color(0xFF64748B),
                                            fontSize = 11.sp
                                        )
                                    }
                                }

                                Icon(
                                    imageVector = Icons.Filled.MoreHoriz,
                                    contentDescription = "Post Options",
                                    tint = Color(0xFF64748B),
                                    modifier = Modifier
                                        .size(22.dp)
                                        .clickable {
                                            Toast.makeText(context, "Post options: Share, Report, Save", Toast.LENGTH_SHORT).show()
                                        }
                                )
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            // Large Campus Photo Banner
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(180.dp)
                                    .clip(RoundedCornerShape(14.dp))
                                    .background(
                                        Brush.verticalGradient(
                                            listOf(Color(0xFF0F172A), Color(0xFF1E293B), Color(0xFF334155))
                                        )
                                    )
                                    .clickable { onNavigateToSchoolProfile() }
                            ) {
                                Canvas(modifier = Modifier.fillMaxSize()) {
                                    drawCircle(
                                        color = Color(0xFF38BDF8).copy(alpha = 0.25f),
                                        radius = size.width * 0.4f,
                                        center = Offset(size.width * 0.8f, size.height * 0.2f)
                                    )
                                }

                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(14.dp),
                                    verticalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.End
                                    ) {
                                        Surface(
                                            shape = RoundedCornerShape(8.dp),
                                            color = Color(0x99000000)
                                        ) {
                                            Text(
                                                text = "AOR 56 • Campus",
                                                color = PureWhite,
                                                fontSize = 11.sp,
                                                fontWeight = FontWeight.Medium,
                                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                            )
                                        }
                                    }

                                    Column {
                                        Text(
                                            text = "Swachhata Abhiyan 2026",
                                            color = PureWhite,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 16.sp
                                        )
                                        Text(
                                            text = "Govt. Higher Secondary School • Phulbani",
                                            color = Color(0xFF94A3B8),
                                            fontSize = 12.sp
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            // Likes Count + Share Button
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    Icon(
                                        imageVector = if (schoolPost.isLiked) Icons.Filled.Favorite else Icons.Filled.ThumbUp,
                                        contentDescription = "Like",
                                        tint = if (schoolPost.isLiked) OdishaMagenta else Color(0xFF475569),
                                        modifier = Modifier
                                            .size(20.dp)
                                            .clickable { viewModel.togglePostLike(schoolPost.id) }
                                    )

                                    Text(
                                        text = "liked by ${schoolPost.likesCount} Users",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = Color(0xFF1E293B),
                                        fontWeight = FontWeight.Medium,
                                        fontSize = 12.sp
                                    )
                                }

                                Surface(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(12.dp))
                                        .clickable {
                                            Toast.makeText(context, "School post shared with classmates!", Toast.LENGTH_SHORT).show()
                                        },
                                    shape = RoundedCornerShape(12.dp),
                                    color = Color(0xFFFDE8E8)
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.Share,
                                            contentDescription = "Share",
                                            tint = OdishaMagenta,
                                            modifier = Modifier.size(14.dp)
                                        )
                                        Text(
                                            text = "Share",
                                            color = OdishaMagenta,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 12.sp
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            // Post Caption text
                            Row {
                                Text(
                                    text = "Govt. Higher Secondary School : celebrating Swachhata abhiyaan in school is a fantastic way to in...",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color(0xFF334155),
                                    fontSize = 11.5.sp,
                                    lineHeight = 15.sp,
                                    modifier = Modifier.weight(1f)
                                )
                                Text(
                                    text = "more.",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color(0xFF64748B),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 11.5.sp,
                                    modifier = Modifier.clickable {
                                        Toast.makeText(context, schoolPost.content, Toast.LENGTH_LONG).show()
                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    // "Resources" SECTION HEADING (Exact from 27.png)
                    Text(
                        text = "Resources",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF111827),
                        fontSize = 18.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // 8-GRID OF RESOURCES (Exact 4x2 layout from 27.png)
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        // Row 1: Library, Fast learning, Records, Design
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            ResourceGridItem(
                                title = "Library",
                                modifier = Modifier.weight(1f),
                                onClick = { searchQuery = "Marksheet" }
                            ) {
                                Icon(imageVector = Icons.Filled.School, contentDescription = "Library", tint = OdishaMagenta, modifier = Modifier.size(24.dp))
                            }

                            ResourceGridItem(
                                title = "Fast learning",
                                modifier = Modifier.weight(1f),
                                onClick = onNavigateToDeveloperProgram
                            ) {
                                Icon(imageVector = Icons.Filled.Campaign, contentDescription = "Fast learning", tint = OdishaMagenta, modifier = Modifier.size(24.dp))
                            }

                            ResourceGridItem(
                                title = "Records",
                                modifier = Modifier.weight(1f),
                                onClick = { searchQuery = "Certificate" }
                            ) {
                                Icon(imageVector = Icons.Filled.Check, contentDescription = "Records", tint = OdishaMagenta, modifier = Modifier.size(24.dp))
                            }

                            ResourceGridItem(
                                title = "Design",
                                modifier = Modifier.weight(1f),
                                onClick = { Toast.makeText(context, "Creative Arts & Design Resources", Toast.LENGTH_SHORT).show() }
                            ) {
                                Icon(imageVector = Icons.Filled.AutoAwesome, contentDescription = "Design", tint = OdishaMagenta, modifier = Modifier.size(24.dp))
                            }
                        }

                        // Row 2: Dev Coding, AI Assistant, Career Launch, + More
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            ResourceGridItem(
                                title = "Dev Coding",
                                modifier = Modifier.weight(1f),
                                onClick = onNavigateToDeveloperProgram
                            ) {
                                Icon(imageVector = Icons.Filled.Code, contentDescription = "Dev Coding", tint = OdishaMagenta, modifier = Modifier.size(24.dp))
                            }

                            ResourceGridItem(
                                title = "AI Assistant",
                                modifier = Modifier.weight(1f),
                                onClick = onNavigateToAiGuide
                            ) {
                                Icon(imageVector = Icons.Filled.AutoAwesome, contentDescription = "AI Assistant", tint = OdishaMagenta, modifier = Modifier.size(24.dp))
                            }

                            ResourceGridItem(
                                title = "Career Launch",
                                modifier = Modifier.weight(1f),
                                onClick = {
                                    viewModel.navigateToPage(12)
                                    Toast.makeText(context, "Opening Career Opportunities & Scholarships", Toast.LENGTH_SHORT).show()
                                }
                            ) {
                                Icon(imageVector = Icons.Filled.RocketLaunch, contentDescription = "Career Launch", tint = OdishaMagenta, modifier = Modifier.size(24.dp))
                            }

                            ResourceGridItem(
                                title = "+ More",
                                modifier = Modifier.weight(1f),
                                onClick = {
                                    searchQuery = "a"
                                    Toast.makeText(context, "Showing full educational directory index", Toast.LENGTH_SHORT).show()
                                }
                            ) {
                                Icon(imageVector = Icons.Filled.Add, contentDescription = "More", tint = OdishaMagenta, modifier = Modifier.size(24.dp))
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(84.dp))
        }

        // Document Viewer Dialog if a document is opened from search
        selectedDoc?.let { doc ->
            DocumentPreviewDialog(
                doc = doc,
                onDismiss = { viewModel.selectedDocument.value = null }
            )
        }
    }
}

// -----------------------------------------------------------
// 27.png Resource Grid Card Helper
// -----------------------------------------------------------
@Composable
private fun ResourceGridItem(
    title: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    iconContent: @Composable () -> Unit
) {
    Surface(
        modifier = modifier
            .height(82.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        color = Color(0xFFFDE8E8)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(6.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier.size(32.dp),
                contentAlignment = Alignment.Center
            ) {
                iconContent()
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.labelSmall,
                color = Color(0xFF1E293B),
                fontWeight = FontWeight.Medium,
                fontSize = 10.5.sp,
                maxLines = 1
            )
        }
    }
}
