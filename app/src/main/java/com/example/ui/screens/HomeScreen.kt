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
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.local.DocumentItemEntity
import com.example.ui.components.DocumentPreviewDialog
import com.example.ui.components.HalfyearlyResultDialog
import com.example.ui.components.LearningOverviewsDialog
import com.example.ui.components.NavTab
import com.example.ui.theme.CaveatFontFamily
import com.example.ui.theme.HairlineDivider
import com.example.ui.theme.NewsreaderFontFamily
import com.example.ui.theme.PureWhite
import com.example.ui.theme.TextDark
import com.example.ui.theme.TextMuted
import com.example.ui.theme.UdyapCrimson
import com.example.ui.theme.UdyapGreenCta
import com.example.ui.theme.UdyapIndigoCta
import com.example.ui.theme.UdyapMintGreen
import com.example.ui.theme.UdyapPinkCard
import com.example.ui.theme.UdyapSkyCard
import com.example.ui.theme.UdyapStackPink
import com.example.ui.theme.UdyapYellowCard
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
// UDYAP HOME SCREEN (Redesigned cleanly per 10_d9df07b3.png)
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

    var showCatchUpBanner by remember { mutableStateOf(true) }
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
                description = "Centrally sponsored scholarship scheme to support meritorious students at secondary and higher secondary stages.",
                requiredDocs = listOf("Class VIII/X Scorecard", "Parental Income Declaration", "Bank Passbook seeded with Aadhaar", "UDYAP APAAR ID")
            ),
            HomeScholarshipItem(
                id = "sch_biju_yuba",
                title = "Biju Yuba Sashaktikaran Digital Grant",
                department = "Electronics & IT Dept., Govt. of Odisha",
                amount = "₹15,000 (One-Time)",
                deadline = "28 Dec 2026",
                eligibility = "Class 12 High School Students enrolled in CHSE Odisha affiliated institutions.",
                categoryTag = "DIGITAL GRANT",
                badgeBgColor = Color(0xFFDCFCE7),
                badgeTextColor = Color(0xFF166534),
                description = "Special technological hardware grant provided to deserving merit students for acquiring digital learning devices for board & entrance preparation.",
                requiredDocs = listOf("CHSE Enrolment Certificate", "Aadhaar Card", "College Bonafide Certificate")
            ),
            HomeScholarshipItem(
                id = "sch_prerana",
                title = "Post-Matric ST/SC Scholarship (PRERANA)",
                department = "ST & SC Development Department, Odisha",
                amount = "100% Tuition + ₹1.2K/mo",
                deadline = "Open / Ongoing",
                eligibility = "ST/SC students pursuing post-matriculation courses whose parents' annual income does not exceed ₹2,50,000.",
                categoryTag = "POST-MATRIC",
                badgeBgColor = Color(0xFFFFE4EC),
                badgeTextColor = UdyapCrimson,
                description = "Comprehensive welfare support covering complete course fee, exam charges, and monthly maintenance allowance.",
                requiredDocs = listOf("Caste Certificate (Digital)", "Income Certificate", "Matriculation Certificate", "UDYAP Bank Seed Record")
            )
        )
    }

    fun openDocByIdOrFallback(id: String, title: String, subtitle: String, issuedBy: String) {
        val existing = documents.find { it.id == id }
        if (existing != null) {
            viewModel.openDocument(existing)
        } else {
            viewModel.openDocument(
                DocumentItemEntity(
                    id = id,
                    title = title,
                    subtitle = subtitle,
                    documentNo = "UDYAP-${id.uppercase()}-762001",
                    issuedBy = issuedBy,
                    sha256Hash = "8f4b2e19a0c7d3f5e6a1b9c8d2e4f0a7",
                    issueDate = "14 Aug 2025",
                    fileSize = "1.4 MB",
                    isVerified = true
                )
            )
        }
    }

    val firstName = remember(profile?.fullName) {
        (profile?.fullName ?: "Laxmikant").substringBefore(" ")
    }

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
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(14.dp))

            // 1. TOP HEADER: "Hello / Laxmikant" + Notification Bell & Chat Bubble
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .clickable { onNavigateToProfile() }
                        .padding(vertical = 2.dp)
                ) {
                    Text(
                        text = "Hello",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextMuted,
                        fontSize = 13.5.sp
                    )
                    Text(
                        text = firstName,
                        style = MaterialTheme.typography.displayMedium,
                        fontWeight = FontWeight.Normal,
                        color = TextDark,
                        fontSize = 24.sp,
                        lineHeight = 28.sp
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    // Notification Bell with indicator dot
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .testTag("home_notifications_button")
                            .clickable { onNavigateToNotifications() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.NotificationsNone,
                            contentDescription = "Notifications",
                            tint = Color(0xFF2B2B2B),
                            modifier = Modifier.size(27.dp)
                        )
                        Box(
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(top = 7.dp, end = 8.dp)
                                .size(7.dp)
                                .clip(CircleShape)
                                .background(UdyapCrimson)
                        )
                    }

                    // Dark Speech Bubble Message Icon
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .testTag("home_messages_button")
                            .clickable { onNavigateToMessages() },
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF333333)),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.Start,
                                verticalArrangement = Arrangement.spacedBy(3.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .width(12.dp)
                                        .height(2.dp)
                                        .clip(RoundedCornerShape(2.dp))
                                        .background(PureWhite)
                                )
                                Box(
                                    modifier = Modifier
                                        .width(8.dp)
                                        .height(2.dp)
                                        .clip(RoundedCornerShape(2.dp))
                                        .background(PureWhite)
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // 2. "CatchUp" SECTION
            Text(
                text = "CatchUp",
                fontFamily = NewsreaderFontFamily,
                fontWeight = FontWeight.Normal,
                color = TextDark,
                fontSize = 23.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            // 3. STACKED MINT-GREEN SCHOLARSHIP CARD (with Pink Card Peeking Below)
            if (showCatchUpBanner) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                ) {
                    // Bottom peeking soft pink stacked card
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp)
                            .height(28.dp)
                            .align(Alignment.BottomCenter)
                            .offset(y = 7.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(UdyapStackPink)
                    )

                    // Foreground Mint Green CatchUp Card
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(18.dp))
                            .testTag("catchup_scholarship_card")
                            .clickable { onNavigateToScholarship() },
                        shape = RoundedCornerShape(18.dp),
                        color = UdyapMintGreen
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 18.dp, end = 14.dp, top = 16.dp, bottom = 14.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.Top,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Post matric\nscholarship is Live",
                                    style = MaterialTheme.typography.headlineMedium,
                                    fontWeight = FontWeight.Normal,
                                    color = Color(0xFF2B3A2C),
                                    fontSize = 18.5.sp,
                                    lineHeight = 25.sp,
                                    modifier = Modifier.weight(1f)
                                )
                                Icon(
                                    imageVector = Icons.Filled.Close,
                                    contentDescription = "Dismiss",
                                    tint = Color(0xFF3E4C3F),
                                    modifier = Modifier
                                        .size(22.dp)
                                        .clickable { showCatchUpBanner = false }
                                )
                            }

                            Spacer(modifier = Modifier.height(14.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.School,
                                        contentDescription = null,
                                        tint = Color(0xFF556B57),
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Text(
                                        text = "You are Eligible to apply",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = Color(0xFF425444),
                                        fontSize = 12.5.sp
                                    )
                                }

                                Button(
                                    onClick = onNavigateToScholarship,
                                    modifier = Modifier
                                        .height(36.dp)
                                        .testTag("catchup_apply_button"),
                                    shape = RoundedCornerShape(9.dp),
                                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 0.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = UdyapGreenCta)
                                ) {
                                    Text(
                                        text = "Apply Now",
                                        color = PureWhite,
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 12.5.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 4. TWO-COLUMN ROW: Halfyearly Results (Soft Pink) + Learning Overviews (Exploded Pie)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Left Card: Halfyearly Results
                Surface(
                    modifier = Modifier
                        .weight(1f)
                        .height(112.dp)
                        .clip(RoundedCornerShape(18.dp))
                        .testTag("halfyearly_results_card")
                        .clickable { showHalfyearlyDialog = true },
                    shape = RoundedCornerShape(18.dp),
                    color = UdyapPinkCard
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(14.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Halfyearly Results\nare out now",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Normal,
                            color = Color(0xFF2D252D),
                            fontSize = 15.sp,
                            lineHeight = 20.sp
                        )
                        Text(
                            text = "Click here to Check",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF4D404D),
                            fontSize = 12.sp
                        )
                    }
                }

                // Right Block: Exploded 4-slice Pie Chart + Legend + "Learning Overviews"
                Row(
                    modifier = Modifier
                        .weight(1.05f)
                        .height(112.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .testTag("learning_overviews_card")
                        .clickable { showLearningOverviewDialog = true }
                        .padding(vertical = 4.dp, horizontal = 2.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // 4-Slice Exploded Pastel Pie Chart matching 10_d9df07b3.png
                    Canvas(modifier = Modifier.size(82.dp)) {
                        val diameter = size.minDimension * 0.84f
                        val pieSize = Size(diameter, diameter)
                        val baseOffset = Offset(
                            (size.width - diameter) / 2f,
                            (size.height - diameter) / 2f
                        )
                        // Top-right coral/crimson wedge (exploded slightly up-right)
                        drawArc(
                            color = Color(0xFFF65C71),
                            startAngle = -90f,
                            sweepAngle = 92f,
                            useCenter = true,
                            topLeft = baseOffset + Offset(4.dp.toPx(), (-3).dp.toPx()),
                            size = pieSize
                        )
                        // Bottom pink wedge
                        drawArc(
                            color = Color(0xFFFBD0F3),
                            startAngle = 6f,
                            sweepAngle = 124f,
                            useCenter = true,
                            topLeft = baseOffset + Offset(1.dp.toPx(), 3.dp.toPx()),
                            size = pieSize
                        )
                        // Left sky blue wedge
                        drawArc(
                            color = Color(0xFFCDE9FF),
                            startAngle = 134f,
                            sweepAngle = 86f,
                            useCenter = true,
                            topLeft = baseOffset + Offset((-3).dp.toPx(), 1.dp.toPx()),
                            size = pieSize
                        )
                        // Top-left soft peach wedge
                        drawArc(
                            color = Color(0xFFFCE2CE),
                            startAngle = 224f,
                            sweepAngle = 42f,
                            useCenter = true,
                            topLeft = baseOffset + Offset((-2).dp.toPx(), (-3).dp.toPx()),
                            size = pieSize
                        )
                    }

                    Column(
                        horizontalAlignment = Alignment.End,
                        verticalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        // Mini color legend rows
                        Column(
                            verticalArrangement = Arrangement.spacedBy(4.dp),
                            modifier = Modifier.padding(top = 4.dp, end = 6.dp)
                        ) {
                            LegendMiniRow(Color(0xFF475569))
                            LegendMiniRow(Color(0xFF64748B))
                            LegendMiniRow(UdyapGreenCta)
                            LegendMiniRow(UdyapCrimson)
                        }

                        Text(
                            text = "Learning\nOverviews",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Normal,
                            color = TextDark,
                            fontSize = 15.5.sp,
                            lineHeight = 19.sp,
                            textAlign = TextAlign.End
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(22.dp))

            // 5. "Documents" SECTION (2 rows x 5 columns matching 10_d9df07b3.png)
            Text(
                text = "Documents",
                fontFamily = NewsreaderFontFamily,
                fontWeight = FontWeight.Normal,
                color = TextDark,
                fontSize = 22.sp
            )

            Spacer(modifier = Modifier.height(14.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // Row 1: Aadhaar Card, PAN Card, X Marksheet, XI Marksheet, EWS Certificate
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    CompactDocumentTile(
                        label = "Aadhaar Card",
                        modifier = Modifier.weight(1f),
                        onClick = { openDocByIdOrFallback("aadhar", "Aadhaar Card", "UIDAI Digital Identity", "UIDAI Govt. of India") }
                    ) { AadhaarIconGraphic() }

                    CompactDocumentTile(
                        label = "PAN Card",
                        modifier = Modifier.weight(1f),
                        onClick = { openDocByIdOrFallback("pan", "PAN Card", "Income Tax Permanent Account", "Income Tax Dept.") }
                    ) { PanCardIconGraphic() }

                    CompactDocumentTile(
                        label = "X Marksheet",
                        modifier = Modifier.weight(1f),
                        onClick = { openDocByIdOrFallback("x_marksheet", "Class X Marksheet", "BSE Odisha Board 2024", "BSE Odisha") }
                    ) { MarksheetXIconGraphic() }

                    CompactDocumentTile(
                        label = "XI Marksheet",
                        modifier = Modifier.weight(1f),
                        onClick = { openDocByIdOrFallback("xi_marksheet", "Class XI Marksheet", "CHSE Higher Secondary 2025", "CHSE Odisha") }
                    ) { MarksheetXiIconGraphic() }

                    CompactDocumentTile(
                        label = "EWS Certifica..",
                        modifier = Modifier.weight(1f),
                        onClick = { openDocByIdOrFallback("ews_cert", "EWS Certificate", "Economically Weaker Section", "Revenue Dept. Odisha") }
                    ) { EwsCertIconGraphic() }
                }

                // Row 2: Caste, Migration, Birth, Family ID, + MORE
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    CompactDocumentTile(
                        label = "Caste\nCertificate",
                        modifier = Modifier.weight(1f),
                        onClick = { openDocByIdOrFallback("caste_cert", "Caste Certificate", "Verified Digital Certificate", "Tehsildar Phulbani") }
                    ) { CasteCertIconGraphic() }

                    CompactDocumentTile(
                        label = "Migration\nCertificate",
                        modifier = Modifier.weight(1f),
                        onClick = { openDocByIdOrFallback("migration_cert", "Migration Certificate", "CHSE Digital Migration", "CHSE Odisha") }
                    ) { MigrationCertIconGraphic() }

                    CompactDocumentTile(
                        label = "Birth\nCertificate",
                        modifier = Modifier.weight(1f),
                        onClick = { openDocByIdOrFallback("birth_cert", "Birth Certificate", "Municipal Birth Registry", "Govt. of Odisha") }
                    ) { BirthCertIconGraphic() }

                    CompactDocumentTile(
                        label = "FAMILY ID\nCertificate",
                        modifier = Modifier.weight(1f),
                        onClick = { openDocByIdOrFallback("aapar", "FAMILY ID & APAAR", "Unified State Student Family ID", "UDYAP Odisha Registry") }
                    ) { FamilyIdIconGraphic() }

                    CompactDocumentTile(
                        label = "MORE",
                        modifier = Modifier.weight(1f),
                        onClick = {
                            viewModel.selectTab(NavTab.SEARCH)
                            Toast.makeText(context, "Browse all verified UDYAP documents", Toast.LENGTH_SHORT).show()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "More documents",
                            tint = TextDark,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(22.dp))

            // 6. "Live Quiz & Action" SECTION (Exact from 10_d9df07b3.png)
            Text(
                text = "Live Quiz & Action",
                fontFamily = NewsreaderFontFamily,
                fontWeight = FontWeight.Normal,
                color = TextDark,
                fontSize = 22.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier
                        .weight(1.1f)
                        .clickable { onNavigateToOlympiad() }
                ) {
                    Text(
                        text = "Run for",
                        fontFamily = CaveatFontFamily,
                        fontWeight = FontWeight.Bold,
                        color = UdyapCrimson,
                        fontSize = 24.sp,
                        lineHeight = 24.sp
                    )
                    Text(
                        text = "State icon trophy",
                        fontFamily = CaveatFontFamily,
                        fontWeight = FontWeight.Bold,
                        color = UdyapCrimson,
                        fontSize = 33.sp,
                        lineHeight = 32.sp
                    )
                }

                Column(
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "Starts from 10 Dec 2026",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF4B4B4B),
                        fontSize = 11.5.sp
                    )
                    Button(
                        onClick = onNavigateToOlympiad,
                        modifier = Modifier
                            .height(36.dp)
                            .testTag("olympiad_register_button"),
                        shape = RoundedCornerShape(9.dp),
                        contentPadding = PaddingValues(horizontal = 18.dp, vertical = 0.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = UdyapIndigoCta)
                    ) {
                        Text(
                            text = "Register Now",
                            color = PureWhite,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 12.5.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Two Pastel Olympiad Cards (Yellow + Sky Blue)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Surface(
                    modifier = Modifier
                        .weight(1f)
                        .height(74.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .clickable { onNavigateToOlympiad() },
                    shape = RoundedCornerShape(16.dp),
                    color = UdyapYellowCard
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 14.dp, vertical = 12.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Geometry in Action",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Normal,
                            color = Color(0xFF2D2A1E),
                            fontSize = 14.5.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = "Participate Now",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF5A5440),
                            fontSize = 12.sp
                        )
                    }
                }

                Surface(
                    modifier = Modifier
                        .weight(1f)
                        .height(74.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .clickable { onNavigateToOlympiad() },
                    shape = RoundedCornerShape(16.dp),
                    color = UdyapSkyCard
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 14.dp, vertical = 12.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Series of Olympiads",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Normal,
                            color = Color(0xFF1E2E3B),
                            fontSize = 14.5.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = "Know More.",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF475C6E),
                            fontSize = 12.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(26.dp))

            // 7. OPEN SCHOLARSHIPS & GRANTS (Clean Minimal Horizontal Carousel Below Fold)
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Scholarships & Grants",
                    fontFamily = NewsreaderFontFamily,
                    fontWeight = FontWeight.Normal,
                    color = TextDark,
                    fontSize = 20.sp
                )
                Text(
                    text = "more",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextMuted,
                    fontSize = 13.5.sp,
                    modifier = Modifier.clickable { onNavigateToScholarship() }
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(scholarshipsList) { sch ->
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = PureWhite,
                        border = BorderStroke(1.dp, HairlineDivider),
                        modifier = Modifier
                            .width(256.dp)
                            .height(152.dp)
                            .clickable { selectedScholarship = sch }
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
                                        shape = RoundedCornerShape(6.dp),
                                        color = sch.badgeBgColor
                                    ) {
                                        Text(
                                            text = sch.categoryTag,
                                            color = sch.badgeTextColor,
                                            fontSize = 9.5.sp,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.padding(horizontal = 7.dp, vertical = 3.dp)
                                        )
                                    }
                                    Text(
                                        text = sch.amount,
                                        color = UdyapGreenCta,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.5.sp
                                    )
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = sch.title,
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.SemiBold,
                                    color = TextDark,
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis,
                                    lineHeight = 18.sp
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = sch.department,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = TextMuted,
                                    fontSize = 11.sp,
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
                                    text = "Due: ${sch.deadline}",
                                    color = TextMuted,
                                    fontSize = 11.sp
                                )
                                Text(
                                    text = if (sch.isApplied) "Applied ✓" else "Apply →",
                                    color = if (sch.isApplied) UdyapGreenCta else UdyapCrimson,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(28.dp))
        }

        // Document Preview Dialog
        selectedDoc?.let { doc ->
            DocumentPreviewDialog(
                doc = doc,
                onDismiss = { viewModel.selectedDocument.value = null }
            )
        }

        // Halfyearly Results Dialog
        if (showHalfyearlyDialog) {
            HalfyearlyResultDialog(onDismiss = { showHalfyearlyDialog = false })
        }

        // Learning Overviews Dialog
        if (showLearningOverviewDialog) {
            LearningOverviewsDialog(onDismiss = { showLearningOverviewDialog = false })
        }

        // Scholarship Detail Dialog
        selectedScholarship?.let { sch ->
            ScholarshipDetailModal(
                scholarship = sch,
                onDismiss = { selectedScholarship = null },
                onApply = {
                    val idx = scholarshipsList.indexOfFirst { it.id == sch.id }
                    if (idx >= 0) {
                        scholarshipsList[idx] = scholarshipsList[idx].copy(isApplied = true)
                    }
                    viewModel.applyScholarshipDirect(sch.title, sch.amount)
                    Toast.makeText(context, "Applied for ${sch.title}", Toast.LENGTH_LONG).show()
                    selectedScholarship = null
                }
            )
        }
    }
}

@Composable
private fun LegendMiniRow(dotColor: Color) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Box(
            modifier = Modifier
                .size(5.dp)
                .clip(RoundedCornerShape(1.dp))
                .background(dotColor)
        )
        Box(
            modifier = Modifier
                .width(18.dp)
                .height(2.dp)
                .clip(RoundedCornerShape(1.dp))
                .background(Color(0xFF9CA3AF))
        )
    }
}

@Composable
private fun CompactDocumentTile(
    label: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    iconContent: @Composable () -> Unit
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .clickable { onClick() }
            .padding(vertical = 4.dp, horizontal = 2.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.size(44.dp),
            contentAlignment = Alignment.Center
        ) {
            iconContent()
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = Color(0xFF404040),
            fontSize = 10.sp,
            lineHeight = 12.5.sp,
            textAlign = TextAlign.Center,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

// =========================================================================
// CRISP CUSTOM DOCUMENT ICON GRAPHICS (Matching 10_d9df07b3.png)
// =========================================================================
@Composable
private fun AadhaarIconGraphic() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Canvas(modifier = Modifier.size(34.dp, 22.dp)) {
            drawArc(
                color = Color(0xFFF59E0B),
                startAngle = 180f,
                sweepAngle = 180f,
                useCenter = false,
                topLeft = Offset(3f, 4f),
                size = Size(size.width - 6f, size.height * 1.3f),
                style = Stroke(width = 3.5f, cap = StrokeCap.Round)
            )
            drawArc(
                color = Color(0xFFDC2626),
                startAngle = 180f,
                sweepAngle = 180f,
                useCenter = false,
                topLeft = Offset(10f, 9f),
                size = Size(size.width - 20f, size.height * 0.9f),
                style = Stroke(width = 3f, cap = StrokeCap.Round)
            )
            drawCircle(
                color = Color(0xFFDC2626),
                radius = 4f,
                center = Offset(size.width / 2f, size.height * 0.85f)
            )
        }
        Text(
            text = "AADHAAR",
            fontSize = 6.5.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color(0xFFDC2626),
            letterSpacing = 0.2.sp
        )
    }
}

@Composable
private fun PanCardIconGraphic() {
    Canvas(modifier = Modifier.size(36.dp, 24.dp)) {
        drawRoundRect(
            color = Color(0xFF0064B7),
            cornerRadius = CornerRadius(4.dp.toPx(), 4.dp.toPx()),
            size = size
        )
        drawRoundRect(
            color = Color(0xFFFBBF24),
            topLeft = Offset(size.width * 0.12f, size.height * 0.24f),
            size = Size(size.width * 0.22f, size.height * 0.30f),
            cornerRadius = CornerRadius(2.dp.toPx(), 2.dp.toPx())
        )
        drawLine(
            color = Color.White.copy(alpha = 0.85f),
            start = Offset(size.width * 0.44f, size.height * 0.34f),
            end = Offset(size.width * 0.86f, size.height * 0.34f),
            strokeWidth = 2.dp.toPx(),
            cap = StrokeCap.Round
        )
        drawLine(
            color = Color.White.copy(alpha = 0.65f),
            start = Offset(size.width * 0.14f, size.height * 0.72f),
            end = Offset(size.width * 0.72f, size.height * 0.72f),
            strokeWidth = 1.8.dp.toPx(),
            cap = StrokeCap.Round
        )
    }
}

@Composable
private fun MarksheetXIconGraphic() {
    Canvas(modifier = Modifier.size(34.dp, 36.dp)) {
        drawRoundRect(
            color = Color(0xFFF8FAFC),
            cornerRadius = CornerRadius(4.dp.toPx(), 4.dp.toPx()),
            size = Size(size.width * 0.82f, size.height)
        )
        drawRoundRect(
            color = Color(0xFF334155),
            cornerRadius = CornerRadius(4.dp.toPx(), 4.dp.toPx()),
            size = Size(size.width * 0.82f, size.height),
            style = Stroke(width = 1.5.dp.toPx())
        )
        // Mini bar chart inside
        drawRect(
            color = Color(0xFF3B82F6),
            topLeft = Offset(5.dp.toPx(), 18.dp.toPx()),
            size = Size(4.dp.toPx(), 10.dp.toPx())
        )
        drawRect(
            color = Color(0xFFF59E0B),
            topLeft = Offset(11.dp.toPx(), 13.dp.toPx()),
            size = Size(4.dp.toPx(), 15.dp.toPx())
        )
        drawRect(
            color = Color(0xFF10B981),
            topLeft = Offset(17.dp.toPx(), 20.dp.toPx()),
            size = Size(4.dp.toPx(), 8.dp.toPx())
        )
        // Green check badge
        drawCircle(
            color = Color(0xFF10B981),
            radius = 6.dp.toPx(),
            center = Offset(size.width * 0.82f, size.height * 0.25f)
        )
    }
}

@Composable
private fun MarksheetXiIconGraphic() {
    Canvas(modifier = Modifier.size(34.dp, 36.dp)) {
        drawRoundRect(
            color = Color(0xFFEFF6FF),
            cornerRadius = CornerRadius(4.dp.toPx(), 4.dp.toPx()),
            size = Size(size.width * 0.76f, size.height)
        )
        drawRoundRect(
            color = Color(0xFF334155),
            cornerRadius = CornerRadius(4.dp.toPx(), 4.dp.toPx()),
            size = Size(size.width * 0.76f, size.height),
            style = Stroke(width = 1.4.dp.toPx())
        )
        for (i in 0..3) {
            val y = 9.dp.toPx() + i * 6.dp.toPx()
            drawLine(
                color = Color(0xFF3B82F6),
                start = Offset(5.dp.toPx(), y),
                end = Offset(20.dp.toPx(), y),
                strokeWidth = 1.6.dp.toPx(),
                cap = StrokeCap.Round
            )
        }
        // Vertical red pen accent on right
        drawRoundRect(
            color = UdyapCrimson,
            topLeft = Offset(size.width * 0.84f, 6.dp.toPx()),
            size = Size(4.dp.toPx(), 24.dp.toPx()),
            cornerRadius = CornerRadius(2.dp.toPx(), 2.dp.toPx())
        )
    }
}

@Composable
private fun EwsCertIconGraphic() {
    Canvas(modifier = Modifier.size(36.dp, 26.dp)) {
        drawRoundRect(
            color = Color(0xFFCDE5F8),
            cornerRadius = CornerRadius(4.dp.toPx(), 4.dp.toPx()),
            size = size
        )
        drawRoundRect(
            color = Color(0xFF60A5FA),
            topLeft = Offset(size.width * 0.65f, size.height * 0.2f),
            size = Size(size.width * 0.24f, size.height * 0.56f),
            cornerRadius = CornerRadius(2.dp.toPx(), 2.dp.toPx())
        )
        drawLine(
            color = Color(0xFF1E3A8A),
            start = Offset(4.dp.toPx(), 8.dp.toPx()),
            end = Offset(19.dp.toPx(), 8.dp.toPx()),
            strokeWidth = 2.dp.toPx()
        )
        drawLine(
            color = Color(0xFF3B82F6),
            start = Offset(4.dp.toPx(), 14.dp.toPx()),
            end = Offset(16.dp.toPx(), 14.dp.toPx()),
            strokeWidth = 1.5.dp.toPx()
        )
        drawLine(
            color = Color(0xFF3B82F6),
            start = Offset(4.dp.toPx(), 19.dp.toPx()),
            end = Offset(18.dp.toPx(), 19.dp.toPx()),
            strokeWidth = 1.5.dp.toPx()
        )
    }
}

@Composable
private fun CasteCertIconGraphic() {
    Canvas(modifier = Modifier.size(36.dp, 28.dp)) {
        drawRoundRect(
            color = Color(0xFFFFFBEB),
            cornerRadius = CornerRadius(3.dp.toPx(), 3.dp.toPx()),
            size = size
        )
        drawRoundRect(
            color = Color(0xFF0D9488),
            cornerRadius = CornerRadius(3.dp.toPx(), 3.dp.toPx()),
            size = size,
            style = Stroke(width = 1.8.dp.toPx())
        )
        drawLine(
            color = Color(0xFFD97706),
            start = Offset(6.dp.toPx(), 9.dp.toPx()),
            end = Offset(24.dp.toPx(), 9.dp.toPx()),
            strokeWidth = 1.8.dp.toPx()
        )
        drawLine(
            color = Color(0xFF94A3B8),
            start = Offset(6.dp.toPx(), 15.dp.toPx()),
            end = Offset(20.dp.toPx(), 15.dp.toPx()),
            strokeWidth = 1.5.dp.toPx()
        )
        drawCircle(
            color = Color(0xFF2563EB),
            radius = 5.dp.toPx(),
            center = Offset(size.width * 0.78f, size.height * 0.72f)
        )
        drawCircle(
            color = Color(0xFFFBBF24),
            radius = 2.5.dp.toPx(),
            center = Offset(size.width * 0.78f, size.height * 0.72f)
        )
    }
}

@Composable
private fun MigrationCertIconGraphic() {
    Canvas(modifier = Modifier.size(28.dp, 36.dp)) {
        drawRoundRect(
            color = Color(0xFFFEF9E7),
            cornerRadius = CornerRadius(3.dp.toPx(), 3.dp.toPx()),
            size = size
        )
        drawRoundRect(
            color = Color(0xFFB48C48),
            cornerRadius = CornerRadius(3.dp.toPx(), 3.dp.toPx()),
            size = size,
            style = Stroke(width = 1.6.dp.toPx())
        )
        for (i in 0..2) {
            drawLine(
                color = Color(0xFF9CA3AF),
                start = Offset(5.dp.toPx(), (9 + i * 5).dp.toPx()),
                end = Offset(22.dp.toPx(), (9 + i * 5).dp.toPx()),
                strokeWidth = 1.3.dp.toPx()
            )
        }
        drawCircle(
            color = Color(0xFFD97706),
            radius = 4.dp.toPx(),
            center = Offset(size.width * 0.72f, size.height * 0.78f)
        )
    }
}

@Composable
private fun BirthCertIconGraphic() {
    Canvas(modifier = Modifier.size(38.dp, 28.dp)) {
        drawRoundRect(
            color = Color(0xFFF0F9FF),
            cornerRadius = CornerRadius(4.dp.toPx(), 4.dp.toPx()),
            size = size
        )
        drawRoundRect(
            color = Color(0xFF38BDF8),
            cornerRadius = CornerRadius(4.dp.toPx(), 4.dp.toPx()),
            size = size,
            style = Stroke(width = 1.4.dp.toPx())
        )
        // Header strip
        drawRect(
            color = Color(0xFF0284C7),
            topLeft = Offset(0f, 0f),
            size = Size(size.width, 6.dp.toPx())
        )
        // Pink photo square
        drawRoundRect(
            color = Color(0xFFFBCFE8),
            topLeft = Offset(size.width * 0.64f, 9.dp.toPx()),
            size = Size(10.dp.toPx(), 13.dp.toPx()),
            cornerRadius = CornerRadius(2.dp.toPx(), 2.dp.toPx())
        )
        drawCircle(
            color = UdyapCrimson,
            radius = 2.6.dp.toPx(),
            center = Offset(size.width * 0.64f + 5.dp.toPx(), 13.5.dp.toPx())
        )
        for (i in 0..2) {
            drawLine(
                color = Color(0xFF64748B),
                start = Offset(4.dp.toPx(), (11 + i * 4.5f).dp.toPx()),
                end = Offset(20.dp.toPx(), (11 + i * 4.5f).dp.toPx()),
                strokeWidth = 1.4.dp.toPx()
            )
        }
    }
}

@Composable
private fun FamilyIdIconGraphic() {
    Canvas(modifier = Modifier.size(32.dp, 34.dp)) {
        drawRoundRect(
            color = Color(0xFF3B66B0),
            cornerRadius = CornerRadius(4.dp.toPx(), 4.dp.toPx()),
            size = size
        )
        drawRoundRect(
            color = Color(0xFFEFF6FF),
            topLeft = Offset(3.dp.toPx(), 4.dp.toPx()),
            size = Size(size.width * 0.56f, size.height - 8.dp.toPx()),
            cornerRadius = CornerRadius(2.dp.toPx(), 2.dp.toPx())
        )
        drawCircle(
            color = Color(0xFF2563EB),
            radius = 3.5.dp.toPx(),
            center = Offset(11.dp.toPx(), 12.dp.toPx())
        )
        drawLine(
            color = Color(0xFF64748B),
            start = Offset(6.dp.toPx(), 20.dp.toPx()),
            end = Offset(17.dp.toPx(), 20.dp.toPx()),
            strokeWidth = 1.6.dp.toPx()
        )
        drawLine(
            color = Color(0xFF94A3B8),
            start = Offset(6.dp.toPx(), 25.dp.toPx()),
            end = Offset(15.dp.toPx(), 25.dp.toPx()),
            strokeWidth = 1.6.dp.toPx()
        )
    }
}

@Composable
private fun ScholarshipDetailModal(
    scholarship: HomeScholarshipItem,
    onDismiss: () -> Unit,
    onApply: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp),
            shape = RoundedCornerShape(22.dp),
            color = PureWhite
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = scholarship.badgeBgColor
                    ) {
                        Text(
                            text = scholarship.categoryTag,
                            color = scholarship.badgeTextColor,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Filled.Close, contentDescription = "Close", tint = TextDark)
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = scholarship.title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = TextDark
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = scholarship.department,
                    style = MaterialTheme.typography.bodySmall,
                    color = TextMuted
                )

                Spacer(modifier = Modifier.height(14.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Surface(
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        color = Color(0xFFF0FDF4)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text("Grant Amount", fontSize = 11.sp, color = TextMuted)
                            Text(scholarship.amount, fontWeight = FontWeight.Bold, color = UdyapGreenCta, fontSize = 14.sp)
                        }
                    }
                    Surface(
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        color = Color(0xFFFFF1F2)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text("Last Date", fontSize = 11.sp, color = TextMuted)
                            Text(scholarship.deadline, fontWeight = FontWeight.Bold, color = UdyapCrimson, fontSize = 14.sp)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))
                Text("Overview", fontWeight = FontWeight.SemiBold, color = TextDark, fontSize = 13.5.sp)
                Spacer(modifier = Modifier.height(4.dp))
                Text(scholarship.description, color = TextMuted, fontSize = 12.5.sp, lineHeight = 18.sp)

                Spacer(modifier = Modifier.height(12.dp))
                Text("Eligibility", fontWeight = FontWeight.SemiBold, color = TextDark, fontSize = 13.5.sp)
                Spacer(modifier = Modifier.height(4.dp))
                Text(scholarship.eligibility, color = TextMuted, fontSize = 12.5.sp, lineHeight = 18.sp)

                Spacer(modifier = Modifier.height(12.dp))
                Text("Required UDYAP Vault Documents", fontWeight = FontWeight.SemiBold, color = TextDark, fontSize = 13.5.sp)
                Spacer(modifier = Modifier.height(6.dp))
                scholarship.requiredDocs.forEach { doc ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier.padding(vertical = 2.dp)
                    ) {
                        Icon(Icons.Filled.CheckCircle, contentDescription = null, tint = UdyapGreenCta, modifier = Modifier.size(15.dp))
                        Text(doc, fontSize = 12.sp, color = TextDark)
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))
                HorizontalDivider(color = HairlineDivider)
                Spacer(modifier = Modifier.height(14.dp))

                Button(
                    onClick = onApply,
                    enabled = !scholarship.isApplied,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (scholarship.isApplied) UdyapGreenCta else UdyapCrimson
                    )
                ) {
                    Text(
                        text = if (scholarship.isApplied) "Application Submitted ✓" else "Apply Instantly via UDYAP ID",
                        color = PureWhite,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
