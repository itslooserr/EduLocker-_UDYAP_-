package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.material.icons.filled.Layers
import androidx.compose.material.icons.filled.Navigation
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.ui.theme.CanvasCream
import com.example.ui.theme.DarkBorder
import com.example.ui.theme.OdishaMagenta
import com.example.ui.theme.PureWhite
import com.example.ui.theme.SoftBlushPink
import com.example.ui.theme.TextDark
import kotlin.math.roundToInt

data class PageItem(
    val pageNumber: Int,
    val title: String,
    val category: String,
    val subtitle: String = ""
)

val ALL_42_PAGES = listOf(
    PageItem(1, "Splash Screen (1.png)", "Onboarding", "Issued by Govt. of Odisha"),
    PageItem(2, "Welcome to EduLocker (2.png)", "Onboarding", "Get Started"),
    PageItem(3, "Onboarding 01 (3.png)", "Onboarding", "Secure digital access"),
    PageItem(4, "Onboarding 02 (4.png)", "Onboarding", "Everything You Need"),
    PageItem(5, "Onboarding 03 (5.png)", "Onboarding", "Your Journey. Identity"),
    PageItem(6, "Onboarding 04 (6.png)", "Onboarding", "Discover. Learn. Grow"),
    PageItem(7, "Onboarding 05 (7.png)", "Onboarding", "Your Future. Connected"),
    PageItem(8, "Sign In Hub", "Auth", "Aadhaar / Phone / ID login"),
    PageItem(9, "Aadhaar Verify OTP", "Auth", "UIDAI 6-digit OTP"),
    PageItem(10, "Confirm PIN (1418)", "Auth", "Security 4-digit PIN"),
    PageItem(11, "Home Hub & Locker", "Main", "Digital locker & documents"),
    PageItem(12, "Notifications Center", "Main", "Alerts & updates"),
    PageItem(13, "Messages & Chat", "Main", "Teacher & parent chats"),
    PageItem(14, "Scholarship Step 1", "Scholarship", "Select category & start"),
    PageItem(15, "Scholarship UIDAI (2)", "Scholarship", "Aadhaar OTP authorization"),
    PageItem(16, "Real Camera KYC (3)", "Scholarship", "Live facial verification"),
    PageItem(17, "KYC Successful (4)", "Scholarship", "Identity confirmed"),
    PageItem(18, "Fill Form & Parent (5)", "Scholarship", "Academic & income details"),
    PageItem(19, "Consent Checks (6)", "Scholarship", "Legal declaration"),
    PageItem(20, "Submit Fee ₹9 (7)", "Scholarship", "Processing charge"),
    PageItem(21, "Saved as Draft (8)", "Scholarship", "Draft saved screen"),
    PageItem(22, "Payment Success (9)", "Scholarship", "UPI payment confirmed"),
    PageItem(23, "Application Done (10)", "Scholarship", "Acknowledgment receipt"),
    PageItem(24, "Home Hub (Olympiad)", "Main", "Olympiad spotlight variant"),
    PageItem(25, "Olympiad Details", "Olympiad", "Odisha Talent Olympiad"),
    PageItem(26, "Olympiad Registered 🏆", "Olympiad", "Admit card generated"),
    PageItem(27, "School Search & Posts", "Directory", "Find schools & faculties"),
    PageItem(28, "HP Developer Program", "Learning", "HP LIFE portal"),
    PageItem(29, "About HP LIFE", "Learning", "Curriculum details"),
    PageItem(30, "Select Course", "Learning", "Web dev, Python, AI"),
    PageItem(31, "Web Dev Levels", "Learning", "Beginner to Advanced"),
    PageItem(32, "Lesson Overview", "Learning", "Course outline & notes"),
    PageItem(33, "54-Min Video Player", "Learning", "Interactive video lecture"),
    PageItem(34, "Guide / AI Smart Think", "AI Guide", "Learning assistant"),
    PageItem(35, "Social Stories & Reels", "Social", "Student community feed"),
    PageItem(36, "Student Profile (Laxmi)", "Profile", "Certificates & badges"),
    PageItem(37, "Achievements & Awards", "Profile", "All student medals"),
    PageItem(38, "Digital ID Card (Front)", "Profile", "APAAR ID Card front"),
    PageItem(39, "Digital ID Card (Back)", "Profile", "ID Card barcode & back"),
    PageItem(40, "Teacher Profile", "Directory", "Faculty details & contact"),
    PageItem(41, "Parent Profile", "Directory", "Guardian details"),
    PageItem(42, "School Detailed Profile", "Directory", "Campus info & enquiry")
)

// Available color options for the assistive ball
val BALL_COLORS = listOf(
    Color(0xFFD81B60) to "Odisha Pink",
    Color(0xFF0F172A) to "Slate Black",
    Color(0xFF4F46E5) to "Royal Indigo",
    Color(0xFF059669) to "Emerald Green",
    Color(0xFFD97706) to "Amber Gold",
    Color(0xFF7C3AED) to "Deep Purple"
)

/**
 * Movable / Draggable Assistive Ball (Assistive Touch)
 * Allows users to:
 * 1. Freely drag and place the ball anywhere on screen.
 * 2. Customize its transparency (opacity) and color.
 * 3. Tap to see the current page and jump to any other page with search and category filters.
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun EvaluatorScreenJumper(
    currentPage: Int,
    onPageSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var showDialog by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf("All") }
    var searchQuery by remember { mutableStateOf("") }
    var activeTab by remember { mutableIntStateOf(0) } // 0 -> Jump Screens, 1 -> Ball Style Settings

    // Assistive Ball Customization State
    var ballColor by remember { mutableStateOf(Color(0xFFD81B60)) }
    var ballOpacity by remember { mutableFloatStateOf(0.85f) }
    var ballSizeDp by remember { mutableIntStateOf(44) }

    // Free Drag Positioning State
    var offsetX by remember { mutableFloatStateOf(0f) }
    var offsetY by remember { mutableFloatStateOf(0f) }

    val categories = remember {
        listOf("All", "Onboarding", "Auth", "Main", "Scholarship", "Olympiad", "Learning", "Profile", "Directory", "AI Guide", "Social")
    }

    val currentPageItem = remember(currentPage) {
        ALL_42_PAGES.find { it.pageNumber == currentPage } ?: PageItem(currentPage, "Screen $currentPage", "General")
    }

    // =========================================================================
    // FLOATING DRAGGABLE ASSISTIVE BALL
    // =========================================================================
    Box(
        modifier = modifier
            .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    offsetX += dragAmount.x
                    offsetY += dragAmount.y
                }
            }
    ) {
        Surface(
            modifier = Modifier
                .size(ballSizeDp.dp)
                .shadow(elevation = 6.dp, shape = CircleShape)
                .clip(CircleShape)
                .alpha(ballOpacity)
                .background(ballColor)
                .border(2.dp, PureWhite.copy(alpha = 0.85f), CircleShape)
                .clickable { showDialog = true },
            color = ballColor
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                // Outer ring & center white dot (Assistive Touch look)
                Box(
                    modifier = Modifier
                        .size((ballSizeDp * 0.62f).dp)
                        .clip(CircleShape)
                        .border(1.5.dp, PureWhite.copy(alpha = 0.9f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size((ballSizeDp * 0.28f).dp)
                            .clip(CircleShape)
                            .background(PureWhite)
                    )
                }
            }
        }
    }

    // =========================================================================
    // DETAILS & PAGE NAVIGATOR DIALOG (Clean & Minimal)
    // =========================================================================
    if (showDialog) {
        Dialog(onDismissRequest = { showDialog = false }) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.88f),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = CanvasCream),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    // Header Bar
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
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .background(ballColor),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Navigation,
                                    contentDescription = null,
                                    tint = PureWhite,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                            Column {
                                Text(
                                    text = "Assistive Navigator",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = TextDark,
                                    fontSize = 16.sp
                                )
                                Text(
                                    text = "Screen Jumper & Ball Customizer",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Color(0xFF64748B),
                                    fontSize = 11.sp
                                )
                            }
                        }

                        IconButton(
                            onClick = { showDialog = false },
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(PureWhite)
                                .border(1.dp, Color(0xFFCBD5E1), CircleShape)
                        ) {
                            Icon(Icons.Filled.Close, contentDescription = "Close", tint = TextDark, modifier = Modifier.size(16.dp))
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // CURRENT SCREEN STATUS CARD
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        color = PureWhite,
                        border = androidx.compose.foundation.BorderStroke(1.5.dp, ballColor.copy(alpha = 0.35f))
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Surface(
                                        color = ballColor,
                                        shape = RoundedCornerShape(6.dp)
                                    ) {
                                        Text(
                                            text = "Page $currentPage",
                                            modifier = Modifier.padding(horizontal = 7.dp, vertical = 2.dp),
                                            color = PureWhite,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 11.sp
                                        )
                                    }
                                    Surface(
                                        color = Color(0xFFF1F5F9),
                                        shape = RoundedCornerShape(6.dp)
                                    ) {
                                        Text(
                                            text = currentPageItem.category,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                            color = Color(0xFF475569),
                                            fontWeight = FontWeight.Medium,
                                            fontSize = 10.5.sp
                                        )
                                    }
                                }

                                // Quick Prev/Next jumps
                                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                    val prevPage = ALL_42_PAGES.filter { it.pageNumber < currentPage }.maxByOrNull { it.pageNumber }?.pageNumber
                                    val nextPage = ALL_42_PAGES.filter { it.pageNumber > currentPage }.minByOrNull { it.pageNumber }?.pageNumber

                                    if (prevPage != null) {
                                        Surface(
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(8.dp))
                                                .clickable {
                                                    onPageSelected(prevPage)
                                                    showDialog = false
                                                },
                                            color = Color(0xFFF1F5F9)
                                        ) {
                                            Row(
                                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp),
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Prev", modifier = Modifier.size(12.dp), tint = TextDark)
                                                Text("Prev", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TextDark)
                                            }
                                        }
                                    }

                                    if (nextPage != null) {
                                        Surface(
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(8.dp))
                                                .clickable {
                                                    onPageSelected(nextPage)
                                                    showDialog = false
                                                },
                                            color = ballColor.copy(alpha = 0.12f)
                                        ) {
                                            Row(
                                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp),
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Text("Next", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = ballColor)
                                                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Next", modifier = Modifier.size(12.dp), tint = ballColor)
                                            }
                                        }
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = currentPageItem.title,
                                fontWeight = FontWeight.Bold,
                                color = TextDark,
                                fontSize = 14.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Tab Switcher: "Screens (42)" vs "Ball Settings"
                    TabRow(
                        selectedTabIndex = activeTab,
                        containerColor = PureWhite,
                        contentColor = ballColor,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp)),
                        indicator = { tabPositions ->
                            SecondaryIndicator(
                                modifier = Modifier.tabIndicatorOffset(tabPositions[activeTab]),
                                color = ballColor
                            )
                        }
                    ) {
                        Tab(
                            selected = activeTab == 0,
                            onClick = { activeTab = 0 },
                            text = {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Icon(Icons.Filled.Layers, contentDescription = null, modifier = Modifier.size(15.dp))
                                    Text("Jump to Page", fontWeight = FontWeight.Bold, fontSize = 12.5.sp)
                                }
                            }
                        )
                        Tab(
                            selected = activeTab == 1,
                            onClick = { activeTab = 1 },
                            text = {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Icon(Icons.Filled.Tune, contentDescription = null, modifier = Modifier.size(15.dp))
                                    Text("Ball Style", fontWeight = FontWeight.Bold, fontSize = 12.5.sp)
                                }
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // TAB 0: PAGE JUMPER
                    if (activeTab == 0) {
                        // Search Field
                        OutlinedTextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            placeholder = { Text("Search screen name or number...", fontSize = 12.sp, color = Color(0xFF94A3B8)) },
                            leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null, tint = Color(0xFF94A3B8), modifier = Modifier.size(16.dp)) },
                            trailingIcon = {
                                if (searchQuery.isNotEmpty()) {
                                    IconButton(onClick = { searchQuery = "" }) {
                                        Icon(Icons.Filled.Close, contentDescription = "Clear", tint = Color(0xFF94A3B8), modifier = Modifier.size(14.dp))
                                    }
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = PureWhite,
                                unfocusedContainerColor = PureWhite,
                                focusedBorderColor = ballColor,
                                unfocusedBorderColor = Color(0xFFE2E8F0)
                            ),
                            singleLine = true
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Category filter chips
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            categories.forEach { cat ->
                                val isCatSelected = cat == selectedCategory
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(if (isCatSelected) ballColor else PureWhite)
                                        .border(1.dp, if (isCatSelected) ballColor else Color(0xFFCBD5E1), RoundedCornerShape(8.dp))
                                        .clickable { selectedCategory = cat }
                                        .padding(horizontal = 8.dp, vertical = 4.dp)
                                ) {
                                    Text(
                                        text = cat,
                                        color = if (isCatSelected) PureWhite else TextDark,
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 11.sp
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Filtered List of Screens
                        val filteredPages = ALL_42_PAGES.filter { item ->
                            val matchCat = selectedCategory == "All" || item.category == selectedCategory
                            val matchQuery = searchQuery.isEmpty() ||
                                item.title.contains(searchQuery, ignoreCase = true) ||
                                item.category.contains(searchQuery, ignoreCase = true) ||
                                item.subtitle.contains(searchQuery, ignoreCase = true) ||
                                item.pageNumber.toString() == searchQuery.trim()
                            matchCat && matchQuery
                        }

                        LazyColumn(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            items(filteredPages) { item ->
                                val isCurrent = item.pageNumber == currentPage
                                Surface(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(12.dp))
                                        .border(
                                            if (isCurrent) 1.5.dp else 1.dp,
                                            if (isCurrent) ballColor else Color(0xFFE2E8F0),
                                            RoundedCornerShape(12.dp)
                                        )
                                        .clickable {
                                            onPageSelected(item.pageNumber)
                                            showDialog = false
                                        },
                                    color = if (isCurrent) ballColor.copy(alpha = 0.08f) else PureWhite
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 10.dp, vertical = 8.dp),
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
                                                    .size(28.dp)
                                                    .clip(CircleShape)
                                                    .background(if (isCurrent) ballColor else Color(0xFFF1F5F9))
                                                    .border(1.dp, if (isCurrent) ballColor else Color(0xFFCBD5E1), CircleShape),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Text(
                                                    text = "${item.pageNumber}",
                                                    color = if (isCurrent) PureWhite else TextDark,
                                                    fontWeight = FontWeight.ExtraBold,
                                                    fontSize = 11.sp
                                                )
                                            }

                                            Column {
                                                Text(
                                                    text = item.title,
                                                    fontWeight = FontWeight.Bold,
                                                    color = if (isCurrent) ballColor else TextDark,
                                                    fontSize = 12.5.sp,
                                                    maxLines = 1,
                                                    overflow = TextOverflow.Ellipsis
                                                )
                                                if (item.subtitle.isNotEmpty()) {
                                                    Text(
                                                        text = item.subtitle,
                                                        color = Color(0xFF64748B),
                                                        fontSize = 10.5.sp,
                                                        maxLines = 1,
                                                        overflow = TextOverflow.Ellipsis
                                                    )
                                                }
                                            }
                                        }

                                        Surface(
                                            color = Color(0xFFF8FAFC),
                                            shape = RoundedCornerShape(6.dp),
                                            border = androidx.compose.foundation.BorderStroke(0.5.dp, Color(0xFFE2E8F0))
                                        ) {
                                            Text(
                                                text = item.category,
                                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                                fontSize = 9.5.sp,
                                                fontWeight = FontWeight.Medium,
                                                color = Color(0xFF64748B)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                    // TAB 1: BALL STYLE & TRANSPARENCY CUSTOMIZER
                    if (activeTab == 1) {
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth()
                                .padding(vertical = 6.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            // 1. Live Preview of Assistive Ball
                            Surface(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(16.dp),
                                color = PureWhite,
                                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0))
                            ) {
                                Column(
                                    modifier = Modifier.padding(16.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text("Ball Preview", style = MaterialTheme.typography.labelSmall, color = Color(0xFF64748B), fontWeight = FontWeight.Bold)
                                    Spacer(modifier = Modifier.height(12.dp))

                                    // Preview Ball
                                    Box(
                                        modifier = Modifier
                                            .size(ballSizeDp.dp)
                                            .shadow(elevation = 6.dp, shape = CircleShape)
                                            .clip(CircleShape)
                                            .alpha(ballOpacity)
                                            .background(ballColor)
                                            .border(2.dp, PureWhite.copy(alpha = 0.85f), CircleShape),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size((ballSizeDp * 0.62f).dp)
                                                .clip(CircleShape)
                                                .border(1.5.dp, PureWhite.copy(alpha = 0.9f), CircleShape),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .size((ballSizeDp * 0.28f).dp)
                                                    .clip(CircleShape)
                                                    .background(PureWhite)
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = "Opacity: ${(ballOpacity * 100).toInt()}% • Size: ${ballSizeDp}dp",
                                        fontSize = 11.5.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = TextDark
                                    )
                                }
                            }

                            // 2. Select Ball Color
                            Column {
                                Text(
                                    text = "Choose Ball Color",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp,
                                    color = TextDark
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    BALL_COLORS.forEach { (c, name) ->
                                        val isSelected = ballColor == c
                                        Column(
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(8.dp))
                                                .clickable { ballColor = c }
                                                .padding(2.dp)
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .size(38.dp)
                                                    .clip(CircleShape)
                                                    .background(c)
                                                    .border(
                                                        if (isSelected) 3.dp else 1.dp,
                                                        if (isSelected) PureWhite else Color(0xFFCBD5E1),
                                                        CircleShape
                                                    )
                                                    .shadow(if (isSelected) 4.dp else 0.dp, CircleShape),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                if (isSelected) {
                                                    Box(
                                                        modifier = Modifier
                                                            .size(10.dp)
                                                            .clip(CircleShape)
                                                            .background(PureWhite)
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                            // 3. Transparency / Opacity Slider
                            Column {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Transparency / Opacity",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp,
                                        color = TextDark
                                    )
                                    Text(
                                        text = "${(ballOpacity * 100).toInt()}%",
                                        fontWeight = FontWeight.ExtraBold,
                                        color = ballColor,
                                        fontSize = 13.sp
                                    )
                                }
                                Slider(
                                    value = ballOpacity,
                                    onValueChange = { ballOpacity = it },
                                    valueRange = 0.20f..1.0f,
                                    colors = SliderDefaults.colors(
                                        thumbColor = ballColor,
                                        activeTrackColor = ballColor,
                                        inactiveTrackColor = Color(0xFFCBD5E1)
                                    )
                                )
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text("20% (Subtle/Glass)", fontSize = 10.5.sp, color = Color(0xFF64748B))
                                    Text("100% (Solid)", fontSize = 10.5.sp, color = Color(0xFF64748B))
                                }
                            }

                            // 4. Size Options
                            Column {
                                Text(
                                    text = "Ball Size",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp,
                                    color = TextDark
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    listOf(36 to "Small", 44 to "Medium", 52 to "Large").forEach { (sz, label) ->
                                        val isSel = ballSizeDp == sz
                                        Surface(
                                            modifier = Modifier
                                                .weight(1f)
                                                .clip(RoundedCornerShape(10.dp))
                                                .border(
                                                    1.5.dp,
                                                    if (isSel) ballColor else Color(0xFFCBD5E1),
                                                    RoundedCornerShape(10.dp)
                                                )
                                                .clickable { ballSizeDp = sz },
                                            color = if (isSel) ballColor.copy(alpha = 0.12f) else PureWhite
                                        ) {
                                            Box(
                                                modifier = Modifier.padding(vertical = 10.dp),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Text(
                                                    text = "$label (${sz}dp)",
                                                    fontWeight = if (isSel) FontWeight.Bold else FontWeight.Medium,
                                                    color = if (isSel) ballColor else TextDark,
                                                    fontSize = 11.5.sp
                                                )
                                            }
                                        }
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.weight(1f))

                            // Reset to default button
                            OutlinedButton(
                                onClick = {
                                    ballColor = Color(0xFFD81B60)
                                    ballOpacity = 0.85f
                                    ballSizeDp = 44
                                    offsetX = 0f
                                    offsetY = 0f
                                },
                                modifier = Modifier.fillMaxWidth().height(42.dp),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text("Reset Ball to Default Position & Style", fontSize = 12.sp, color = TextDark)
                            }
                        }
                    }
                }
            }
        }
    }
}
