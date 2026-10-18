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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.NorthEast
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import java.io.File
import com.example.data.local.DocumentItemEntity
import com.example.ui.components.DocumentPreviewDialog
import com.example.ui.components.HalfyearlyResultDialog
import com.example.ui.components.LearningOverviewsDialog
import com.example.ui.components.NavTab
import com.example.ui.theme.CanvasCream
import com.example.ui.theme.OdishaMagenta
import com.example.ui.theme.OdishaMagentaDark
import com.example.ui.theme.PureWhite
import com.example.ui.viewmodel.EduLockerViewModel

data class HomeScholarshipItem(
    val id: String,
    val title: String,
    val department: String,
    val amount: String,
    val deadline: String,
    val eligibility: String,
    val categoryTag: String,
    val badgeBgColor: Color,
    val badgeTextColor: Color,
    val description: String,
    val requiredDocs: List<String>,
    var isApplied: Boolean = false
)

// =========================================================================
// SCREEN 11: Home Screen (Exact Replica of 11.png with Functional Buttons)
// =========================================================================
@Composable
fun HomeScreen(
    viewModel: EduLockerViewModel,
    isOlympiadVariant: Boolean = false,
    onNavigateToScholarship: () -> Unit,
    onNavigateToOlympiad: () -> Unit,
    onNavigateToNotifications: () -> Unit,
    onNavigateToMessages: () -> Unit,
    onNavigateToProfile: () -> Unit,
    modifier: Modifier = Modifier
) {
    val documents by viewModel.digitalDocuments.collectAsState()
    val selectedDoc by viewModel.selectedDocument.collectAsState()
    val profile by viewModel.studentProfile.collectAsState()
    val context = LocalContext.current

    var showHalfyearlyDialog by remember { mutableStateOf(false) }
    var showLearningOverviewDialog by remember { mutableStateOf(false) }
    var selectedScholarship by remember { mutableStateOf<HomeScholarshipItem?>(null) }

    val scholarshipsList = remember {
        mutableStateListOf(
            HomeScholarshipItem(
                id = "sch_medhabruti",
                title = "e-Medhabruti Junior Merit Scholarship 2026-27",
                department = "Higher Education Dept., Govt. of Odisha",
                amount = "₹10,000 / yr",
                deadline = "31 Oct 2026",
                eligibility = "Minimum 60% aggregate in 10th Board (BSE / CBSE / ICSE) & permanent resident of Odisha.",
                categoryTag = "STATE MERIT",
                badgeBgColor = Color(0xFFFEF3C7),
                badgeTextColor = Color(0xFF92400E),
                description = "Merit-cum-poverty financial assistance for +2 Science, Arts & Vocational students across Odisha. Directly disbursed into student bank accounts via Aadhaar DBT.",
                requiredDocs = listOf("Class X Marksheet (Verified)", "Odisha Resident Certificate", "Income Certificate (< ₹6 Lakh/yr)", "Aadhaar e-KYC")
            ),
            HomeScholarshipItem(
                id = "sch_nmmss",
                title = "National Means-cum-Merit Scholarship (NMMSS)",
                department = "Ministry of Education, Government of India",
                amount = "₹12,000 / yr",
                deadline = "15 Nov 2026",
                eligibility = "Studying in Class IX–XII with minimum 55% marks. Parental income not exceeding ₹3,50,000 per annum.",
                categoryTag = "CENTRAL GOVT",
                badgeBgColor = Color(0xFFE0F2FE),
                badgeTextColor = Color(0xFF0369A1),
                description = "Centrally sponsored scholarship scheme to arrest student dropout at Class 8 level and encourage them to continue study at secondary stage.",
                requiredDocs = listOf("Class VIII/X Scorecard", "Parental Income Declaration", "Bank Passbook seeded with Aadhaar", "APAAR Student ID")
            ),
            HomeScholarshipItem(
                id = "sch_biju_yuba",
                title = "Biju Yuba Sashaktikaran Digital Grant",
                department = "Electronics & IT Dept., Govt. of Odisha",
                amount = "₹15,000 (One-Time)",
                deadline = "28 Dec 2026",
                eligibility = "Class 12 High School Students enrolled in CHSE Odisha affiliated institutions.",
                categoryTag = "DIGITAL EMPOWERMENT",
                badgeBgColor = Color(0xFFDCFCE7),
                badgeTextColor = Color(0xFF166534),
                description = "Special technological hardware grant provided to deserving merit students for acquiring digital learning laptops/tablets for board & entrance preparation.",
                requiredDocs = listOf("CHSE Enrolment Certificate", "Aadhaar Card", "College Bonafide Certificate")
            ),
            HomeScholarshipItem(
                id = "sch_prerana",
                title = "Post-Matric ST/SC Scholarship (PRERANA)",
                department = "ST & SC Development Department, Odisha",
                amount = "100% Tuition Fee + ₹1.2K/mo",
                deadline = "Open / Ongoing",
                eligibility = "ST/SC students pursuing post-matriculation courses whose parents' annual income does not exceed ₹2,50,000.",
                categoryTag = "POST-MATRIC",
                badgeBgColor = Color(0xFFF3E8FF),
                badgeTextColor = Color(0xFF6B21A8),
                description = "Comprehensive welfare support covering complete course fee, exam charges, and monthly maintenance allowance.",
                requiredDocs = listOf("Caste Certificate (Digital)", "Income Certificate", "Matriculation Certificate", "EduLocker Bank Seed Record")
            )
        )
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(CanvasCream)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 22.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Top Spacing to align content downwards cleanly
            Spacer(modifier = Modifier.height(34.dp))

            // 1. TOP HEADER: Avatar + Hello Laxmikant + Bell & Chat icons (Exact from 11.png)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Avatar + Greeting
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.clickable { onNavigateToProfile() }
                ) {
                    // Dark Circular Avatar with LN or uploaded photo
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF1E293B)),
                        contentAlignment = Alignment.Center
                    ) {
                        val photoUri = profile?.photoUri
                        if (!photoUri.isNullOrEmpty() && File(photoUri).exists()) {
                            AsyncImage(
                                model = File(photoUri),
                                contentDescription = "Profile Photo",
                                modifier = Modifier.fillMaxSize().clip(CircleShape),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            Text(
                                text = "LN",
                                color = PureWhite,
                                fontWeight = FontWeight.Bold,
                                fontSize = 17.sp
                            )
                        }
                    }

                    Column {
                        Text(
                            text = "Hello",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF475569),
                            fontSize = 12.5.sp
                        )
                        Text(
                            text = profile?.fullName ?: "Laxmikant",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF111827),
                            fontSize = 17.5.sp
                        )
                    }
                }

                // Bell & Chat Icons (Exact from 11.png)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Notification Bell Outline Icon
                    Icon(
                        imageVector = Icons.Filled.NotificationsNone,
                        contentDescription = "Notifications",
                        tint = Color(0xFF1F2937),
                        modifier = Modifier
                            .size(26.dp)
                            .clickable { onNavigateToNotifications() }
                    )

                    // Dark Speech Bubble Chat Icon
                    Box(
                        modifier = Modifier
                            .size(26.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .background(Color(0xFF1F2937))
                            .clickable { onNavigateToMessages() },
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(2.dp)
                        ) {
                            Box(modifier = Modifier.width(12.dp).height(2.dp).background(PureWhite))
                            Box(modifier = Modifier.width(8.dp).height(2.dp).background(PureWhite))
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // 2. "Popular" HEADING (Exact from 11.png)
            Text(
                text = "Popular",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF111827),
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            // 3. SCHOLARSHIP HERO CARD (Exact from 11.png - Soft Peach #FFEDD5)
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(22.dp))
                    .clickable { onNavigateToScholarship() },
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEBE1))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Post matric\nscholarship is Live",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1E293B),
                            fontSize = 17.sp,
                            lineHeight = 22.sp
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "You are\nEligible to apply",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF475569),
                            fontSize = 12.sp,
                            lineHeight = 15.sp
                        )
                    }

                    Column(
                        horizontalAlignment = Alignment.End,
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Large Outline Graduation Cap
                        Icon(
                            imageVector = Icons.Filled.School,
                            contentDescription = null,
                            tint = Color(0xFF94A3B8),
                            modifier = Modifier.size(54.dp)
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        // Magenta "Apply Now" Button
                        Button(
                            onClick = onNavigateToScholarship,
                            modifier = Modifier
                                .height(38.dp)
                                .clip(RoundedCornerShape(12.dp)),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = OdishaMagenta)
                        ) {
                            Text(
                                text = "Apply Now",
                                color = PureWhite,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.5.sp
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 4. TWO-CARD ROW: Results Card & Learning Overviews (Exact from 11.png)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Left Card: "Halfyearly Results are out now" (Mint #CCFBF1) -> Opens Halfyearly marksheet
                Surface(
                    modifier = Modifier
                        .weight(1f)
                        .height(105.dp)
                        .clip(RoundedCornerShape(18.dp))
                        .clickable { showHalfyearlyDialog = true },
                    shape = RoundedCornerShape(18.dp),
                    color = Color(0xFFCCFBF1)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(12.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Halfyearly Results\nare out now",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF042F2E),
                            fontSize = 13.sp,
                            lineHeight = 16.sp
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Lets check",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color(0xFF0F766E),
                                fontSize = 11.5.sp
                            )

                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFF99F6E4)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.NorthEast,
                                    contentDescription = null,
                                    tint = Color(0xFF0F766E),
                                    modifier = Modifier.size(14.dp)
                                )
                            }
                        }
                    }
                }

                // Right Card: Pie Chart + Learning Overviews -> Opens Weak areas & Teacher remarks analysis
                Surface(
                    modifier = Modifier
                        .weight(1.1f)
                        .height(105.dp)
                        .clip(RoundedCornerShape(18.dp))
                        .clickable { showLearningOverviewDialog = true },
                    shape = RoundedCornerShape(18.dp),
                    color = PureWhite
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Orange / Maroon Pie Chart Canvas
                        Canvas(modifier = Modifier.size(54.dp)) {
                            drawArc(
                                color = Color(0xFFF97316), // Bright Orange
                                startAngle = 0f,
                                sweepAngle = 140f,
                                useCenter = true
                            )
                            drawArc(
                                color = Color(0xFF7C2D12), // Deep Maroon
                                startAngle = 140f,
                                sweepAngle = 120f,
                                useCenter = true
                            )
                            drawArc(
                                color = Color(0xFFEA580C), // Red-Orange
                                startAngle = 260f,
                                sweepAngle = 100f,
                                useCenter = true
                            )
                        }

                        Column {
                            // Checklist icon
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(3.dp)) {
                                Box(modifier = Modifier.size(5.dp).background(Color(0xFF7C2D12)))
                                Box(modifier = Modifier.width(14.dp).height(2.dp).background(Color(0xFF7C2D12)))
                            }
                            Spacer(modifier = Modifier.height(3.dp))
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(3.dp)) {
                                Box(modifier = Modifier.size(5.dp).background(Color(0xFF7C2D12)))
                                Box(modifier = Modifier.width(14.dp).height(2.dp).background(Color(0xFF7C2D12)))
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "Learning\nOverviews",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF1E293B),
                                fontSize = 11.5.sp,
                                lineHeight = 13.sp
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // 5. CAROUSEL DOTS INDICATOR (● ○ ○ ○ - Exact from 11.png)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(OdishaMagenta)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFFBCFE8))
                )
                Spacer(modifier = Modifier.width(6.dp))
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFFBCFE8))
                )
                Spacer(modifier = Modifier.width(6.dp))
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFFBCFE8))
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            // 6. "Docouments" SECTION (Exact from 11.png)
            Text(
                text = "Docouments",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF111827),
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            // 7. 6-GRID OF DOCUMENTS (Exact from 11.png - 3 Columns x 2 Rows)
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Row 1: Aadhar Card, PAN Card, X Marksheet
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    DocGridCard(
                        title = "Aadhar Card",
                        modifier = Modifier.weight(1f),
                        onClick = {
                            val doc = documents.find { it.id == "aadhar" } ?: documents.firstOrNull()
                            doc?.let { viewModel.openDocument(it) }
                        }
                    ) {
                        AadhaarIconGraphic()
                    }
                    DocGridCard(
                        title = "PAN Card",
                        modifier = Modifier.weight(1f),
                        onClick = {
                            val doc = documents.find { it.id == "pan" } ?: documents.firstOrNull()
                            doc?.let { viewModel.openDocument(it) }
                        }
                    ) {
                        PanCardIconGraphic()
                    }
                    DocGridCard(
                        title = "X Marksheet",
                        modifier = Modifier.weight(1f),
                        onClick = {
                            val doc = documents.find { it.id == "x_marksheet" } ?: documents.firstOrNull()
                            doc?.let { viewModel.openDocument(it) }
                        }
                    ) {
                        MarksheetXIconGraphic()
                    }
                }

                // Row 2: AAPAR ID, XI Marksheet, More
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    DocGridCard(
                        title = "AAPAR ID",
                        modifier = Modifier.weight(1f),
                        onClick = {
                            val doc = documents.find { it.id == "aapar" } ?: documents.firstOrNull()
                            doc?.let { viewModel.openDocument(it) }
                        }
                    ) {
                        AaparIdIconGraphic()
                    }
                    DocGridCard(
                        title = "XI Marksheet",
                        modifier = Modifier.weight(1f),
                        onClick = {
                            val doc = documents.find { it.id == "xi_marksheet" } ?: documents.firstOrNull()
                            doc?.let { viewModel.openDocument(it) }
                        }
                    ) {
                        MarksheetXiIconGraphic()
                    }
                    DocGridCard(
                        title = "More",
                        modifier = Modifier.weight(1f),
                        onClick = {
                            viewModel.selectTab(NavTab.SEARCH)
                            Toast.makeText(context, "Explore all 20+ verified locker documents in Search", Toast.LENGTH_SHORT).show()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "More",
                            tint = Color(0xFF1E293B),
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(26.dp))

            // =============================================================
            // 6. SCHOLARSHIP & GRANTS SECTION (Under Documents Tab)
            // =============================================================
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Scholarships & Grants",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0F172A),
                        fontSize = 19.sp
                    )
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Color(0xFFDCFCE7)
                    ) {
                        Text(
                            text = "4 Open",
                            color = Color(0xFF166534),
                            fontSize = 10.5.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }
                }

                Text(
                    text = "View All →",
                    style = MaterialTheme.typography.labelLarge,
                    color = OdishaMagentaDark,
                    fontSize = 13.5.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { onNavigateToScholarship() }
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Scholarship Horizontal Cards Carousel
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(14.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(scholarshipsList) { sch ->
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = PureWhite,
                        border = androidx.compose.foundation.BorderStroke(1.5.dp, Color(0xFF1E293B).copy(alpha = 0.15f)),
                        modifier = Modifier
                            .width(268.dp)
                            .height(174.dp)
                            .clickable {
                                selectedScholarship = sch
                            }
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(14.dp),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Surface(
                                        shape = RoundedCornerShape(8.dp),
                                        color = sch.badgeBgColor
                                    ) {
                                        Text(
                                            text = sch.categoryTag,
                                            color = sch.badgeTextColor,
                                            fontSize = 9.5.sp,
                                            fontWeight = FontWeight.ExtraBold,
                                            modifier = Modifier.padding(horizontal = 7.dp, vertical = 3.dp)
                                        )
                                    }

                                    Text(
                                        text = sch.amount,
                                        color = Color(0xFF059669),
                                        fontWeight = FontWeight.ExtraBold,
                                        fontSize = 13.5.sp
                                    )
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                Text(
                                    text = sch.title,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = Color(0xFF0F172A),
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis,
                                    lineHeight = 19.sp
                                )

                                Spacer(modifier = Modifier.height(4.dp))

                                Text(
                                    text = sch.department,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color(0xFF64748B),
                                    fontSize = 11.5.sp,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "⏳ ${sch.deadline}",
                                    color = Color(0xFF64748B),
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Medium
                                )

                                Surface(
                                    shape = RoundedCornerShape(10.dp),
                                    color = if (sch.isApplied) Color(0xFFDCFCE7) else Color(0xFFFCE7F3)
                                ) {
                                    Text(
                                        text = if (sch.isApplied) "Applied ✓" else "Apply ⚡",
                                        color = if (sch.isApplied) Color(0xFF166534) else OdishaMagentaDark,
                                        fontSize = 11.5.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 9.dp, vertical = 4.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(96.dp))
        }

        // Halfyearly Marksheet Report Dialog
        if (showHalfyearlyDialog) {
            HalfyearlyResultDialog(onDismiss = { showHalfyearlyDialog = false })
        }

        // Learning Overviews & Teacher Remarks Diagnostic Dialog
        if (showLearningOverviewDialog) {
            LearningOverviewsDialog(
                onDismiss = { showLearningOverviewDialog = false },
                onNavigateToHPProgram = onNavigateToOlympiad
            )
        }

        // Selected Document Viewer Dialog
        selectedDoc?.let { doc ->
            DocumentPreviewDialog(
                doc = doc,
                onDismiss = { viewModel.selectedDocument.value = null }
            )
        }

        // Scholarship Detail & Apply Dialog
        selectedScholarship?.let { sch ->
            ScholarshipDetailApplyDialog(
                scholarship = sch,
                onApply = {
                    sch.isApplied = true
                    viewModel.applyScholarshipDirect(sch.title, sch.amount)
                    selectedScholarship = null
                    Toast.makeText(context, "🎉 Application for ${sch.title} submitted with APAAR ID!", Toast.LENGTH_LONG).show()
                },
                onDismiss = { selectedScholarship = null }
            )
        }
    }
}

// -----------------------------------------------------------
// Document Grid Card & Graphic Helpers for 11.png
// -----------------------------------------------------------
@Composable
private fun DocGridCard(
    title: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    iconContent: @Composable () -> Unit
) {
    Surface(
        modifier = modifier
            .height(96.dp)
            .clip(RoundedCornerShape(18.dp))
            .clickable { onClick() },
        shape = RoundedCornerShape(18.dp),
        color = Color(0xFFFDE8E8) // Soft pink container
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(6.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier.size(38.dp),
                contentAlignment = Alignment.Center
            ) {
                iconContent()
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.labelSmall,
                color = Color(0xFF1E293B),
                fontWeight = FontWeight.Medium,
                fontSize = 11.sp
            )
        }
    }
}

@Composable
private fun AadhaarIconGraphic() {
    Canvas(modifier = Modifier.size(34.dp)) {
        // Aadhaar red/yellow sunburst rays
        for (angle in 0 until 180 step 20) {
            drawArc(
                color = Color(0xFFEF4444),
                startAngle = angle.toFloat() + 180f,
                sweepAngle = 10f,
                useCenter = true
            )
        }
        drawCircle(color = Color(0xFFF59E0B), radius = 10f, center = Offset(size.width / 2, size.height * 0.55f))
    }
}

@Composable
private fun PanCardIconGraphic() {
    Surface(
        modifier = Modifier
            .size(width = 34.dp, height = 24.dp)
            .clip(RoundedCornerShape(4.dp)),
        color = Color(0xFF0284C7)
    ) {
        Column(modifier = Modifier.padding(2.dp)) {
            Box(modifier = Modifier.size(5.dp).background(Color(0xFFFEF08A)))
            Spacer(modifier = Modifier.height(2.dp))
            Box(modifier = Modifier.width(18.dp).height(2.dp).background(PureWhite))
        }
    }
}

@Composable
private fun MarksheetXIconGraphic() {
    Surface(
        modifier = Modifier
            .size(width = 28.dp, height = 32.dp)
            .clip(RoundedCornerShape(3.dp))
            .border(1.dp, Color(0xFF1E293B), RoundedCornerShape(3.dp)),
        color = PureWhite
    ) {
        Column(
            modifier = Modifier.padding(3.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Box(modifier = Modifier.fillMaxWidth(0.8f).height(2.dp).background(Color(0xFF2563EB)))
            Box(modifier = Modifier.fillMaxWidth(0.9f).height(2.dp).background(Color(0xFF64748B)))
            Box(modifier = Modifier.fillMaxWidth(0.6f).height(2.dp).background(Color(0xFF64748B)))
        }
    }
}

@Composable
private fun AaparIdIconGraphic() {
    Surface(
        modifier = Modifier
            .size(width = 34.dp, height = 24.dp)
            .clip(RoundedCornerShape(3.dp))
            .border(0.8.dp, Color(0xFF0284C7), RoundedCornerShape(3.dp)),
        color = Color(0xFFE0F2FE)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(2.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Box(modifier = Modifier.size(10.dp).background(Color(0xFF0284C7)))
            Column(verticalArrangement = Arrangement.spacedBy(1.5.dp)) {
                Box(modifier = Modifier.width(16.dp).height(2.dp).background(Color(0xFF0369A1)))
                Box(modifier = Modifier.width(12.dp).height(2.dp).background(Color(0xFF7DD3FC)))
            }
        }
    }
}

@Composable
private fun MarksheetXiIconGraphic() {
    Surface(
        modifier = Modifier
            .size(width = 28.dp, height = 32.dp)
            .clip(RoundedCornerShape(3.dp))
            .border(1.dp, Color(0xFF0D9488), RoundedCornerShape(3.dp)),
        color = Color(0xFFF0FDFA)
    ) {
        Column(
            modifier = Modifier.padding(3.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Box(modifier = Modifier.fillMaxWidth(0.7f).height(2.dp).background(Color(0xFF0F766E)))
            Box(modifier = Modifier.fillMaxWidth(0.9f).height(2.dp).background(Color(0xFF99F6E4)))
            Box(modifier = Modifier.fillMaxWidth(0.5f).height(2.dp).background(Color(0xFF99F6E4)))
        }
    }
}

// =========================================================================
// SCHOLARSHIP DETAIL & APPLY DIALOG (Clean & Modern Bottom / Modal Dialog)
// =========================================================================
@Composable
private fun ScholarshipDetailApplyDialog(
    scholarship: HomeScholarshipItem,
    onApply: () -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = Modifier
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

                // Top Navigation Bar
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(PureWhite)
                            .border(1.5.dp, Color(0xFF1E293B), CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color(0xFF0F172A)
                        )
                    }

                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = scholarship.badgeBgColor
                    ) {
                        Text(
                            text = scholarship.categoryTag,
                            color = scholarship.badgeTextColor,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.ExtraBold,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                        )
                    }

                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(PureWhite)
                            .border(1.5.dp, Color(0xFF1E293B).copy(alpha = 0.3f), CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "Close",
                            tint = Color(0xFF0F172A),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                // Scholarship Title Card
                Surface(
                    shape = RoundedCornerShape(22.dp),
                    color = PureWhite,
                    border = androidx.compose.foundation.BorderStroke(1.5.dp, Color(0xFF1E293B).copy(alpha = 0.15f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = scholarship.title,
                            style = MaterialTheme.typography.titleLarge,
                            fontSize = 19.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF0F172A),
                            lineHeight = 25.sp
                        )

                        Text(
                            text = "🏛️ ${scholarship.department}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFF475569),
                            fontSize = 13.5.sp,
                            fontWeight = FontWeight.SemiBold
                        )

                        HorizontalDivider(color = Color(0xFFF1F5F9))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "SCHOLARSHIP GRANT",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Color(0xFF64748B),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 10.sp
                                )
                                Text(
                                    text = scholarship.amount,
                                    color = Color(0xFF059669),
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.ExtraBold
                                )
                            }

                            Column(horizontalAlignment = Alignment.End) {
                                Text(
                                    text = "APPLICATION DEADLINE",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Color(0xFF64748B),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 10.sp
                                )
                                Text(
                                    text = scholarship.deadline,
                                    color = Color(0xFFDC2626),
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.ExtraBold
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Scheme Overview
                Text(
                    text = "Scheme Description",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color(0xFF0F172A)
                )
                Spacer(modifier = Modifier.height(6.dp))
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = PureWhite,
                    border = androidx.compose.foundation.BorderStroke(1.5.dp, Color(0xFF1E293B).copy(alpha = 0.12f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = scholarship.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF334155),
                        fontSize = 13.5.sp,
                        lineHeight = 20.sp,
                        modifier = Modifier.padding(16.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Eligibility Criteria
                Text(
                    text = "Eligibility Criteria",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color(0xFF0F172A)
                )
                Spacer(modifier = Modifier.height(6.dp))
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = PureWhite,
                    border = androidx.compose.foundation.BorderStroke(1.5.dp, Color(0xFF1E293B).copy(alpha = 0.12f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Verified,
                            contentDescription = null,
                            tint = OdishaMagenta,
                            modifier = Modifier.size(22.dp)
                        )
                        Text(
                            text = scholarship.eligibility,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFF334155),
                            fontSize = 13.5.sp,
                            lineHeight = 20.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Required Verified Documents (EduLocker Integrated)
                Text(
                    text = "Required Documents (Auto-Attached from Vault)",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color(0xFF0F172A)
                )
                Spacer(modifier = Modifier.height(6.dp))

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    scholarship.requiredDocs.forEach { docName ->
                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = PureWhite,
                            border = androidx.compose.foundation.BorderStroke(1.2.dp, Color(0xFF1E293B).copy(alpha = 0.12f)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Description,
                                        contentDescription = null,
                                        tint = OdishaMagenta,
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Text(
                                        text = docName,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = Color(0xFF0F172A),
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }

                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = Color(0xFFDCFCE7)
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.CheckCircle,
                                            contentDescription = null,
                                            tint = Color(0xFF166534),
                                            modifier = Modifier.size(13.dp)
                                        )
                                        Text(
                                            text = "Attached & Verified",
                                            color = Color(0xFF166534),
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Apply Action Button
                Button(
                    onClick = onApply,
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (scholarship.isApplied) Color(0xFF16A34A) else OdishaMagenta,
                        contentColor = PureWhite
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp)
                        .border(1.5.dp, Color(0xFF1E293B), RoundedCornerShape(16.dp))
                ) {
                    Text(
                        text = if (scholarship.isApplied) "Application Submitted (Ref #ODS-2026-9812) ✓" else "Apply Now with APAAR ID ⚡",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                }

                Spacer(modifier = Modifier.height(36.dp))
            }
        }
    }
}
