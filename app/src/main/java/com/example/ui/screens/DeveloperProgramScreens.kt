package com.example.ui.screens

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ClosedCaption
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Comment
import androidx.compose.material.icons.filled.Forward10
import androidx.compose.material.icons.filled.Fullscreen
import androidx.compose.material.icons.filled.FullscreenExit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Replay10
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.ScreenRotation
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.R
import com.example.ui.components.EduLockerDualCircleLogo
import com.example.ui.theme.CanvasCream
import com.example.ui.theme.OdishaMagenta
import com.example.ui.theme.PureWhite
import com.example.ui.theme.SoftBlushPink
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.TextDark
import com.example.ui.theme.TextMuted
import com.example.ui.theme.UdyapCrimson
import com.example.ui.viewmodel.EduLockerViewModel
import kotlinx.coroutines.delay

// =========================================================================
// DARK CINEMATIC COURSE HUB PALETTE (Inspired by reference streaming UI)
// =========================================================================
private val CourseDarkBg = Color(0xFF0E0B14)
private val CourseDarkSurface = Color(0xFF1A1624)
private val CourseDarkCard = Color(0xFF211C2E)
private val CourseDarkMuted = Color(0xFF9E97AD)
private val CourseDarkDivider = Color(0xFF2E273D)

data class DevCourseItem(
    val id: String,
    val title: String,
    val shortTitle: String,
    val category: String,
    val year: String,
    val duration: String,
    val rating: String,
    val qualityTag: String,
    val instructor: String,
    val progressFraction: Float,
    val progressTimeLabel: String,
    val description: String,
    val lessonTitles: List<Triple<String, String, Boolean>>
)

private val udyapDevCourses = listOf(
    DevCourseItem(
        id = "web_dev",
        title = "Web Development : Full Stack",
        shortTitle = "Web Development",
        category = "Web Dev",
        year = "2026",
        duration = "54m 25s",
        rating = "4.9",
        qualityTag = "HD 1080p",
        instructor = "UDYAP x HP LIFE",
        progressFraction = 0.62f,
        progressTimeLabel = "34:10",
        description = "Master modern responsive web development from HTML5 and CSS3 Flexbox to interactive JavaScript ES6+ and live API integration. Built for high-school and college developers with real-world capstone projects and a verified UDYAP certificate.",
        lessonTitles = listOf(
            Triple("01. Orientation & VS Code Setup", "10:15", true),
            Triple("02. Semantic HTML5 & Layout Foundations", "18:40", true),
            Triple("03. Modern Web Architecture & DOM Tree", "54:25", false),
            Triple("04. CSS3 Flexbox & Responsive Design", "35:10", false),
            Triple("05. JavaScript ES6+ & Async Fetch API", "44:20", false),
            Triple("06. Capstone: Student Services Portal", "50:00", false)
        )
    ),
    DevCourseItem(
        id = "app_dev",
        title = "Android & Kotlin Compose",
        shortTitle = "App Development",
        category = "App Dev",
        year = "2026",
        duration = "1h 12m",
        rating = "4.8",
        qualityTag = "4K Ultra",
        instructor = "UDYAP Mobile Lab",
        progressFraction = 0.38f,
        progressTimeLabel = "26:45",
        description = "Design and ship real Android apps using Kotlin, Jetpack Compose, Material 3, and clean MVVM state architecture. Learn animations, navigation, local Room storage, and Play Store publishing.",
        lessonTitles = listOf(
            Triple("01. Kotlin Fundamentals for Android", "14:20", true),
            Triple("02. Jetpack Compose Layouts & Modifiers", "24:15", false),
            Triple("03. StateFlow & ViewModel Architecture", "32:10", false),
            Triple("04. Material 3 Dynamic Theming", "28:00", false),
            Triple("05. Publishing Your First App", "19:30", false)
        )
    ),
    DevCourseItem(
        id = "ai_ml",
        title = "AI & Machine Learning Basics",
        shortTitle = "AI & ML Basics",
        category = "AI & ML",
        year = "2026",
        duration = "48m 10s",
        rating = "4.9",
        qualityTag = "HD 1080p",
        instructor = "Odisha AI Youth Guild",
        progressFraction = 0.25f,
        progressTimeLabel = "12:05",
        description = "Understand how neural networks, prompt engineering, Python data pipelines, and real-world AI models work. Build practical mini-projects tailored for student competitions and hackathons.",
        lessonTitles = listOf(
            Triple("01. How AI & LLMs Think", "12:05", true),
            Triple("02. Python & NumPy Quickstart", "21:30", false),
            Triple("03. Training Your First Classifier", "30:15", false),
            Triple("04. Building Smart Student Assistants", "26:40", false)
        )
    ),
    DevCourseItem(
        id = "git_github",
        title = "Git & GitHub Collaboration",
        shortTitle = "Git & GitHub",
        category = "Git & Cloud",
        year = "2026",
        duration = "36m 40s",
        rating = "4.7",
        qualityTag = "HD 1080p",
        instructor = "Open Source India",
        progressFraction = 0.80f,
        progressTimeLabel = "29:15",
        description = "Learn version control like a pro: commits, branching, pull requests, resolving merge conflicts, and showcasing a standout developer portfolio on GitHub.",
        lessonTitles = listOf(
            Triple("01. Repositories, Commits & History", "11:20", true),
            Triple("02. Branching & Pull Request Workflow", "15:10", true),
            Triple("03. Open Source Contributions & Actions", "18:45", false)
        )
    ),
    DevCourseItem(
        id = "prog_fund",
        title = "Programming Fundamentals",
        shortTitle = "DSA & Logic",
        category = "Web Dev",
        year = "2026",
        duration = "1h 05m",
        rating = "4.8",
        qualityTag = "HD 1080p",
        instructor = "UDYAP Core Faculty",
        progressFraction = 0.15f,
        progressTimeLabel = "09:40",
        description = "Build rock-solid logic with variables, loops, functions, recursion, and clean data structures so you can pick up any language effortlessly.",
        lessonTitles = listOf(
            Triple("01. Thinking Like a Programmer", "14:00", true),
            Triple("02. Control Flow, Loops & Functions", "22:30", false),
            Triple("03. Arrays, Maps & Problem Solving", "28:50", false)
        )
    )
)

// =========================================================================
// SCREEN 28: UDYAP Developer Program & Courses Hub (Dark Cinematic Streaming UI)
// =========================================================================
@Composable
fun DeveloperProgramIntroScreen(
    onNext: () -> Unit,
    viewModel: EduLockerViewModel? = null,
    onBack: (() -> Unit)? = null,
    onOpenCourseDetail: (() -> Unit)? = null,
    onOpenPlayer: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val profileState = viewModel?.studentProfile?.collectAsState()
    val studentName = profileState?.value?.fullName ?: "Laxmikant Nayak"

    val categories = listOf("All", "Web Dev", "App Dev", "AI & ML", "Git & Cloud")
    var selectedCategory by remember { mutableStateOf("All") }
    var featuredIndex by remember { mutableIntStateOf(0) }
    var showSearchField by remember { mutableStateOf(false) }
    var courseSearch by remember { mutableStateOf("") }
    var showSavedOnly by remember { mutableStateOf(false) }
    val savedCourseIds = remember { mutableStateListOf("web_dev", "ai_ml") }

    BackHandler(enabled = onBack != null) {
        onBack?.invoke()
    }

    val filteredCourses = remember(selectedCategory, courseSearch, showSavedOnly, savedCourseIds.size) {
        udyapDevCourses.filter { c ->
            val matchCat = selectedCategory == "All" || c.category == selectedCategory
            val matchSearch = courseSearch.isBlank() ||
                c.title.contains(courseSearch, ignoreCase = true) ||
                c.category.contains(courseSearch, ignoreCase = true)
            val matchSaved = !showSavedOnly || savedCourseIds.contains(c.id)
            matchCat && matchSearch && matchSaved
        }
    }

    val activeHero = udyapDevCourses[featuredIndex % udyapDevCourses.size]

    fun selectAndOpen(course: DevCourseItem, goDirectlyToPlayer: Boolean = false) {
        viewModel?.selectedCourse?.value = course.shortTitle
        if (goDirectlyToPlayer && onOpenPlayer != null) {
            onOpenPlayer()
        } else if (onOpenCourseDetail != null) {
            onOpenCourseDetail()
        } else {
            onNext()
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(CourseDarkBg)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
        ) {
            // Scrollable Course Catalog Body
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
            ) {
                // 1. TOP GREETING BAR
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        if (onBack != null) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .background(CourseDarkSurface)
                                    .testTag("courses_back_button")
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
                        Column {
                            Text(
                                text = "Hello",
                                color = CourseDarkMuted,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Normal
                            )
                            Text(
                                text = studentName,
                                color = PureWhite,
                                fontSize = 19.sp,
                                fontWeight = FontWeight.SemiBold,
                                letterSpacing = (-0.2).sp
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(CourseDarkSurface)
                            .clickable {
                                Toast.makeText(context, "New Live Batch: Kotlin Compose starts Monday!", Toast.LENGTH_SHORT).show()
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.NotificationsNone,
                            contentDescription = "Course Alerts",
                            tint = PureWhite,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                // Inline Search Bar (toggled from bottom bar)
                AnimatedVisibility(visible = showSearchField) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 6.dp)
                            .height(44.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .background(CourseDarkSurface)
                            .padding(horizontal = 14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Icon(Icons.Filled.Search, contentDescription = null, tint = CourseDarkMuted, modifier = Modifier.size(18.dp))
                        Box(modifier = Modifier.weight(1f)) {
                            if (courseSearch.isEmpty()) {
                                Text("Search courses, skills, tracks...", color = CourseDarkMuted, fontSize = 13.sp)
                            }
                            BasicTextField(
                                value = courseSearch,
                                onValueChange = { courseSearch = it },
                                singleLine = true,
                                cursorBrush = SolidColor(UdyapCrimson),
                                textStyle = TextStyle(color = PureWhite, fontSize = 13.5.sp),
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                        if (courseSearch.isNotEmpty()) {
                            Icon(
                                imageVector = Icons.Filled.Close,
                                contentDescription = "Clear",
                                tint = CourseDarkMuted,
                                modifier = Modifier
                                    .size(18.dp)
                                    .clickable { courseSearch = "" }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // 2. HERO FEATURED COURSE BANNER CAROUSEL
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(udyapDevCourses.size) { index ->
                        val item = udyapDevCourses[index]
                        Box(
                            modifier = Modifier
                                .width(310.dp)
                                .height(176.dp)
                                .clip(RoundedCornerShape(18.dp))
                                .testTag("hero_course_card_$index")
                                .clickable {
                                    featuredIndex = index
                                    selectAndOpen(item)
                                }
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.udyap_red_banner),
                                contentDescription = item.title,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(
                                        Brush.verticalGradient(
                                            listOf(
                                                Color.Black.copy(alpha = 0.15f),
                                                Color.Black.copy(alpha = 0.82f)
                                            )
                                        )
                                    )
                            )
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = UdyapCrimson,
                                modifier = Modifier
                                    .align(Alignment.TopStart)
                                    .padding(14.dp)
                            ) {
                                Text(
                                    text = item.category.uppercase(),
                                    color = PureWhite,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                )
                            }
                            Column(
                                modifier = Modifier
                                    .align(Alignment.BottomStart)
                                    .padding(14.dp)
                            ) {
                                Text(
                                    text = item.title,
                                    color = PureWhite,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Spacer(modifier = Modifier.height(3.dp))
                                Text(
                                    text = "${item.instructor}  •  ${item.duration}  •  ★ ${item.rating}",
                                    color = PureWhite.copy(alpha = 0.82f),
                                    fontSize = 11.5.sp
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Carousel Pagination Dots
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    udyapDevCourses.indices.forEach { idx ->
                        val isActive = idx == featuredIndex
                        Box(
                            modifier = Modifier
                                .padding(horizontal = 3.dp)
                                .height(6.dp)
                                .width(if (isActive) 20.dp else 6.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(if (isActive) PureWhite else CourseDarkMuted.copy(alpha = 0.45f))
                                .clickable { featuredIndex = idx }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                // 3. CATEGORY FILTER TABS (with Crimson Underline)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState())
                        .padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(22.dp)
                ) {
                    categories.forEach { cat ->
                        val isSelected = selectedCategory == cat && !showSavedOnly
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .clickable {
                                    showSavedOnly = false
                                    selectedCategory = cat
                                }
                                .padding(vertical = 4.dp)
                        ) {
                            Text(
                                text = cat,
                                color = if (isSelected) PureWhite else CourseDarkMuted,
                                fontSize = 14.sp,
                                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
                            )
                            Spacer(modifier = Modifier.height(5.dp))
                            Box(
                                modifier = Modifier
                                    .width(20.dp)
                                    .height(2.5.dp)
                                    .clip(RoundedCornerShape(2.dp))
                                    .background(if (isSelected) UdyapCrimson else Color.Transparent)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                // 4. "Continue Learning" SECTION
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = if (showSavedOnly) "Saved Courses" else "Continue Learning",
                        color = PureWhite,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "View All",
                        color = CourseDarkMuted,
                        fontSize = 12.5.sp,
                        modifier = Modifier.clickable {
                            selectedCategory = "All"
                            showSavedOnly = false
                            selectAndOpen(activeHero)
                        }
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(filteredCourses) { course ->
                        Column(
                            modifier = Modifier
                                .width(194.dp)
                                .testTag("continue_course_${course.id}")
                                .clickable { selectAndOpen(course, goDirectlyToPlayer = true) }
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(110.dp)
                                    .clip(RoundedCornerShape(14.dp))
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.udyap_red_banner),
                                    contentDescription = course.title,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(
                                            Brush.verticalGradient(
                                                listOf(Color.Transparent, Color.Black.copy(alpha = 0.78f))
                                            )
                                        )
                                )
                                // Play chip center
                                Box(
                                    modifier = Modifier
                                        .align(Alignment.Center)
                                        .size(36.dp)
                                        .clip(CircleShape)
                                        .background(Color.Black.copy(alpha = 0.55f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.PlayArrow,
                                        contentDescription = "Resume",
                                        tint = PureWhite,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                                // Timestamp badge
                                Text(
                                    text = course.progressTimeLabel,
                                    color = PureWhite,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Medium,
                                    modifier = Modifier
                                        .align(Alignment.BottomEnd)
                                        .padding(end = 8.dp, bottom = 10.dp)
                                )
                                // Progress bar
                                Box(
                                    modifier = Modifier
                                        .align(Alignment.BottomStart)
                                        .fillMaxWidth()
                                        .height(3.5.dp)
                                        .background(PureWhite.copy(alpha = 0.25f))
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth(course.progressFraction)
                                            .fillMaxHeight()
                                            .background(UdyapCrimson)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(7.dp))
                            Text(
                                text = course.shortTitle,
                                color = PureWhite,
                                fontSize = 13.5.sp,
                                fontWeight = FontWeight.Medium,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                text = "${(course.progressFraction * 100).toInt()}% completed",
                                color = CourseDarkMuted,
                                fontSize = 11.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(22.dp))

                // 5. "Top Trending" SECTION (Portrait Cards)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Top Trending",
                        color = PureWhite,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "View All",
                        color = CourseDarkMuted,
                        fontSize = 12.5.sp,
                        modifier = Modifier.clickable {
                            selectedCategory = "All"
                            showSavedOnly = false
                        }
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(udyapDevCourses) { course ->
                        Box(
                            modifier = Modifier
                                .width(126.dp)
                                .height(176.dp)
                                .clip(RoundedCornerShape(14.dp))
                                .testTag("trending_course_${course.id}")
                                .clickable { selectAndOpen(course) }
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.udyap_red_banner),
                                contentDescription = course.shortTitle,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(
                                        Brush.verticalGradient(
                                            listOf(
                                                Color.Black.copy(alpha = 0.15f),
                                                Color.Black.copy(alpha = 0.86f)
                                            )
                                        )
                                    )
                            )
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(3.dp),
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(8.dp)
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(Color.Black.copy(alpha = 0.6f))
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Icon(Icons.Filled.Star, contentDescription = null, tint = Color(0xFFFBBF24), modifier = Modifier.size(11.dp))
                                Text(course.rating, color = PureWhite, fontSize = 10.sp, fontWeight = FontWeight.SemiBold)
                            }
                            Column(
                                modifier = Modifier
                                    .align(Alignment.BottomStart)
                                    .padding(10.dp)
                            ) {
                                Text(
                                    text = course.shortTitle,
                                    color = PureWhite,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    maxLines = 2,
                                    lineHeight = 16.sp,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = course.duration,
                                    color = CourseDarkMuted,
                                    fontSize = 10.5.sp
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
            }

            // 6. SLEEK DARK BOTTOM MINI BAR (Matches left mockup navigation)
            HorizontalDivider(color = CourseDarkDivider, thickness = 0.8.dp)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(CourseDarkBg)
                    .padding(horizontal = 24.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    showSavedOnly = false
                    showSearchField = false
                    selectedCategory = "All"
                }) {
                    Icon(
                        imageVector = Icons.Filled.Home,
                        contentDescription = "Courses Home",
                        tint = if (!showSavedOnly && !showSearchField) UdyapCrimson else CourseDarkMuted
                    )
                }
                IconButton(onClick = { showSearchField = !showSearchField }) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search Courses",
                        tint = if (showSearchField) UdyapCrimson else CourseDarkMuted
                    )
                }
                IconButton(onClick = { showSavedOnly = !showSavedOnly }) {
                    Icon(
                        imageVector = Icons.Filled.Bookmark,
                        contentDescription = "Saved Courses",
                        tint = if (showSavedOnly) UdyapCrimson else CourseDarkMuted
                    )
                }
                IconButton(onClick = { selectAndOpen(activeHero) }) {
                    Icon(
                        imageVector = Icons.Filled.PlayArrow,
                        contentDescription = "Start Course",
                        tint = CourseDarkMuted
                    )
                }
            }
        }
    }
}

// Screen 29: About Developer Program (Clean minimal zero-outline button)
@Composable
fun DeveloperProgramAboutScreen(
    onContinue: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(CanvasCream)
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(horizontal = 24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(100.dp))

            EduLockerDualCircleLogo(size = 46.dp)
            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "About the\nDeveloper Program",
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.SemiBold,
                color = TextDark,
                fontSize = 22.sp,
                lineHeight = 28.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "We provide carefully curated courses, learning materials, projects, and practical challenges to help you develop your skills step by step.\n\nBased on your performance and successful completion of the program, you receive a verified UDYAP Developer Program Certificate.",
                style = MaterialTheme.typography.bodyMedium,
                color = TextDark.copy(alpha = 0.85f),
                fontSize = 14.sp,
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(48.dp))

            Button(
                onClick = onContinue,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth(0.55f)
                    .height(50.dp),
                shape = RoundedCornerShape(30.dp),
                colors = ButtonDefaults.buttonColors(containerColor = OdishaMagenta)
            ) {
                Text(
                    text = "Continue",
                    color = PureWhite,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

// Screen 30: Select a Course (Minimal zero-outline style)
@Composable
fun CourseSelectorScreen(
    viewModel: EduLockerViewModel,
    onContinue: () -> Unit,
    modifier: Modifier = Modifier
) {
    val selectedCourse by viewModel.selectedCourse.collectAsState()

    val courses = listOf(
        Pair("Programming Fundamentals", Icons.Filled.Code),
        Pair("Web Development", Icons.Filled.Code),
        Pair("App Development", Icons.Filled.PhoneAndroid),
        Pair("Git & GitHub", Icons.Filled.Code),
        Pair("AI & Machine Learning", Icons.Filled.SmartToy)
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(CanvasCream)
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(horizontal = 24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(80.dp))

            EduLockerDualCircleLogo(size = 46.dp)
            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "Select a Course",
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.SemiBold,
                color = TextDark,
                fontSize = 22.sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Choose a course that matches your interests and start building practical skills.",
                style = MaterialTheme.typography.bodyMedium,
                color = TextDark.copy(alpha = 0.8f),
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(22.dp))

            courses.forEach { (courseName, icon) ->
                val isSelected = courseName == selectedCourse
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp)
                        .height(54.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .clickable { viewModel.selectedCourse.value = courseName },
                    shape = RoundedCornerShape(16.dp),
                    color = if (isSelected) OdishaMagenta else SoftBlushPink
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(34.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(if (isSelected) PureWhite.copy(alpha = 0.2f) else PureWhite),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = icon,
                                contentDescription = null,
                                tint = if (isSelected) PureWhite else TextDark,
                                modifier = Modifier.size(18.dp)
                            )
                        }

                        Text(
                            text = courseName,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = if (isSelected) PureWhite else TextDark,
                            fontSize = 14.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(36.dp))

            Button(
                onClick = onContinue,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth(0.55f)
                    .height(50.dp),
                shape = RoundedCornerShape(30.dp),
                colors = ButtonDefaults.buttonColors(containerColor = OdishaMagenta)
            ) {
                Text(
                    text = "Continue",
                    color = PureWhite,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

// Screen 31: Choose Learning Level (Minimal zero-outline style)
@Composable
fun CourseLevelSelectorScreen(
    viewModel: EduLockerViewModel,
    onContinue: () -> Unit,
    modifier: Modifier = Modifier
) {
    val selectedCourse by viewModel.selectedCourse.collectAsState()
    val selectedLevel by viewModel.selectedLevel.collectAsState()

    val levels = listOf(
        Pair("Beginner", "Start from the basics with beginner friendly video lessons and simple practical tasks."),
        Pair("Intermediate", "Improve your existing skills through advanced concepts, projects, and coding challenges."),
        Pair("Advanced", "Work on real world projects, advanced development concepts, and complex challenges.")
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(CanvasCream)
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(horizontal = 24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(90.dp))

            EduLockerDualCircleLogo(size = 46.dp)
            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = selectedCourse,
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.SemiBold,
                color = TextDark,
                fontSize = 22.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Choose your learning level",
                style = MaterialTheme.typography.bodyLarge,
                color = TextDark.copy(alpha = 0.8f),
                fontSize = 15.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            levels.forEach { (lvl, desc) ->
                val isSelected = lvl == selectedLevel
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp)
                        .clickable { viewModel.selectedLevel.value = lvl },
                    verticalAlignment = Alignment.Top
                ) {
                    RadioButton(
                        selected = isSelected,
                        onClick = { viewModel.selectedLevel.value = lvl },
                        colors = RadioButtonDefaults.colors(selectedColor = OdishaMagenta)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = lvl,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = TextDark,
                            fontSize = 15.sp
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = desc,
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextDark.copy(alpha = 0.8f),
                            fontSize = 13.sp,
                            lineHeight = 18.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = onContinue,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth(0.55f)
                    .height(50.dp),
                shape = RoundedCornerShape(30.dp),
                colors = ButtonDefaults.buttonColors(containerColor = OdishaMagenta)
            ) {
                Text(
                    text = "Continue",
                    color = PureWhite,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

// =========================================================================
// SCREEN 32: Sleek Course Detail Screen (Matches right side of reference UI)
// =========================================================================
@Composable
fun LessonOverviewScreen(
    onWatch: () -> Unit,
    viewModel: EduLockerViewModel? = null,
    onBack: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val selectedCourseName = viewModel?.selectedCourse?.collectAsState()?.value ?: "Web Development"
    val course = remember(selectedCourseName) {
        udyapDevCourses.find {
            it.shortTitle.equals(selectedCourseName, ignoreCase = true) ||
                it.title.contains(selectedCourseName, ignoreCase = true)
        } ?: udyapDevCourses.first()
    }

    var isLiked by remember(course.id) { mutableStateOf(false) }
    var isSaved by remember(course.id) { mutableStateOf(false) }
    var descriptionExpanded by remember(course.id) { mutableStateOf(false) }

    BackHandler(enabled = onBack != null) {
        onBack?.invoke()
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(CourseDarkBg)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding()
                .verticalScroll(rememberScrollState())
        ) {
            // 1. FULL-BLEED HERO BANNER WITH DARK FADE & TRANSLUCENT BACK BUTTON
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(295.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.udyap_red_banner),
                    contentDescription = course.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                listOf(
                                    Color.Black.copy(alpha = 0.25f),
                                    Color.Transparent,
                                    CourseDarkBg.copy(alpha = 0.85f),
                                    CourseDarkBg
                                )
                            )
                        )
                )

                if (onBack != null) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .statusBarsPadding()
                            .padding(start = 18.dp, top = 12.dp)
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(Color.Black.copy(alpha = 0.45f))
                            .testTag("course_detail_back_button")
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

                // Title + Quality Badge pinned at bottom of hero
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 6.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(
                            text = course.title,
                            color = PureWhite,
                            fontSize = 21.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = (-0.25).sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.weight(1f, fill = false)
                        )
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = Color.Transparent,
                            border = BorderStroke(1.dp, CourseDarkMuted.copy(alpha = 0.5f))
                        ) {
                            Text(
                                text = course.qualityTag,
                                color = PureWhite.copy(alpha = 0.9f),
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.padding(horizontal = 7.dp, vertical = 2.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Metadata Row: 2026 ▸ Category | 🕒 Duration | ★ Rating
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(course.year, color = CourseDarkMuted, fontSize = 12.sp)
                        Text("▸", color = PureWhite, fontSize = 11.sp)
                        Text(course.category, color = CourseDarkMuted, fontSize = 12.sp)
                        Text("|", color = CourseDarkDivider, fontSize = 12.sp)
                        Icon(Icons.Filled.Schedule, contentDescription = null, tint = CourseDarkMuted, modifier = Modifier.size(13.dp))
                        Text(course.duration, color = CourseDarkMuted, fontSize = 12.sp)
                        Text("|", color = CourseDarkDivider, fontSize = 12.sp)
                        Icon(Icons.Filled.Star, contentDescription = null, tint = Color(0xFFFBBF24), modifier = Modifier.size(13.dp))
                        Text("${course.rating} Ratings", color = CourseDarkMuted, fontSize = 12.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 2. FULL-WIDTH MINIMAL CRIMSON CTA: "Play Free Now"
            Button(
                onClick = onWatch,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .height(50.dp)
                    .testTag("play_course_now_button"),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = UdyapCrimson)
            ) {
                Icon(
                    imageVector = Icons.Filled.PlayArrow,
                    contentDescription = null,
                    tint = PureWhite,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Play Free Now",
                    color = PureWhite,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 3. DESCRIPTION WITH READ MORE
            Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                Text(
                    text = course.description,
                    color = CourseDarkMuted,
                    fontSize = 13.sp,
                    lineHeight = 19.sp,
                    maxLines = if (descriptionExpanded) 10 else 3,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = if (descriptionExpanded) "Show Less" else "Read More",
                    color = PureWhite,
                    fontSize = 12.5.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.clickable { descriptionExpanded = !descriptionExpanded }
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // 4. 3-COLUMN ACTION ROW: Like | Review | Save
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(10.dp))
                        .clickable {
                            isLiked = !isLiked
                            Toast.makeText(context, if (isLiked) "Added to Liked Courses" else "Like removed", Toast.LENGTH_SHORT).show()
                        }
                        .padding(vertical = 10.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = if (isLiked) Icons.Filled.ThumbUp else Icons.Outlined.ThumbUp,
                        contentDescription = "Like",
                        tint = if (isLiked) UdyapCrimson else PureWhite,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(7.dp))
                    Text("Like", color = if (isLiked) UdyapCrimson else PureWhite, fontSize = 13.sp)
                }

                Box(modifier = Modifier.width(1.dp).height(20.dp).background(CourseDarkDivider))

                Row(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(10.dp))
                        .clickable {
                            Toast.makeText(context, "Rated ${course.rating}/5 by 2,400+ UDYAP learners", Toast.LENGTH_SHORT).show()
                        }
                        .padding(vertical = 10.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.Comment,
                        contentDescription = "Review",
                        tint = PureWhite,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(7.dp))
                    Text("Review", color = PureWhite, fontSize = 13.sp)
                }

                Box(modifier = Modifier.width(1.dp).height(20.dp).background(CourseDarkDivider))

                Row(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(10.dp))
                        .clickable {
                            isSaved = !isSaved
                            Toast.makeText(context, if (isSaved) "Course saved for later" else "Removed from Saved", Toast.LENGTH_SHORT).show()
                        }
                        .padding(vertical = 10.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = if (isSaved) Icons.Filled.Bookmark else Icons.Filled.BookmarkBorder,
                        contentDescription = "Save",
                        tint = if (isSaved) UdyapCrimson else PureWhite,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(7.dp))
                    Text("Save", color = if (isSaved) UdyapCrimson else PureWhite, fontSize = 13.sp)
                }
            }

            Spacer(modifier = Modifier.height(22.dp))

            // 5. RELATED COURSES
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Related",
                    color = PureWhite,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "View All",
                    color = CourseDarkMuted,
                    fontSize = 12.5.sp,
                    modifier = Modifier.clickable { onBack?.invoke() }
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            LazyRow(
                contentPadding = PaddingValues(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(udyapDevCourses.filter { it.id != course.id }) { related ->
                    Box(
                        modifier = Modifier
                            .width(120.dp)
                            .height(166.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .clickable {
                                viewModel?.selectedCourse?.value = related.shortTitle
                            }
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.udyap_red_banner),
                            contentDescription = related.shortTitle,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.verticalGradient(
                                        listOf(Color.Transparent, Color.Black.copy(alpha = 0.85f))
                                    )
                                )
                        )
                        Column(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(10.dp)
                        ) {
                            Text(
                                text = related.shortTitle,
                                color = PureWhite,
                                fontSize = 12.5.sp,
                                fontWeight = FontWeight.SemiBold,
                                maxLines = 2,
                                lineHeight = 15.sp
                            )
                            Text(
                                text = related.duration,
                                color = CourseDarkMuted,
                                fontSize = 10.sp
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(26.dp))
        }
    }
}

// =========================================================================
// SCREEN 33: Interactive Course Video Player (Reliable Pause + Working Full-Screen)
// =========================================================================
@Composable
fun VideoPlayerScreen(
    viewModel: EduLockerViewModel,
    onSaveLater: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val selectedCourseName by viewModel.selectedCourse.collectAsState()
    val course = remember(selectedCourseName) {
        udyapDevCourses.find {
            it.shortTitle.equals(selectedCourseName, ignoreCase = true) ||
                it.title.contains(selectedCourseName, ignoreCase = true)
        } ?: udyapDevCourses.first()
    }

    val lessonsList = course.lessonTitles
    var currentLessonIndex by remember(course.id) { mutableIntStateOf(0) }
    var progressSeconds by remember(course.id, currentLessonIndex) { mutableIntStateOf(165) }
    val totalSeconds = 3265 // 54:25
    var isPlaying by remember { mutableStateOf(true) }
    var captionsOn by remember { mutableStateOf(true) }
    var playbackSpeed by remember { mutableFloatStateOf(1.0f) }
    var isBookmarked by remember { mutableStateOf(false) }
    var isLiked by remember { mutableStateOf(false) }
    var likeCount by remember { mutableIntStateOf(1428) }
    var selectedTab by remember { mutableIntStateOf(0) } // 0: Lessons, 1: Notes
    var showControls by remember { mutableStateOf(true) }
    var isFullScreen by remember { mutableStateOf(false) }
    var isLandscapeRotated by remember { mutableStateOf(false) }

    BackHandler {
        if (isFullScreen) {
            isFullScreen = false
            isLandscapeRotated = false
        } else {
            onSaveLater()
        }
    }

    // Reliable playback timer that halts immediately whenever isPlaying == false
    LaunchedEffect(isPlaying, playbackSpeed) {
        while (isPlaying) {
            val delayMs = (1000f / playbackSpeed).toLong().coerceAtLeast(250L)
            delay(delayMs)
            if (isPlaying) {
                if (progressSeconds < totalSeconds) {
                    progressSeconds += 1
                } else {
                    isPlaying = false
                }
            }
        }
    }

    // Keep controls visible when paused
    LaunchedEffect(isPlaying) {
        if (!isPlaying) {
            showControls = true
        }
    }

    val currentLesson = lessonsList.getOrElse(currentLessonIndex) { lessonsList.first() }
    val captionLines = remember(currentLessonIndex) {
        listOf(
            "Now let's structure the component hierarchy for ${currentLesson.first.substringAfter(". ")}.",
            "Keep your UI state unidirectional so every user interaction feels instant and predictable.",
            "Test responsive layouts across both compact phones and full-screen landscape viewports.",
            "Commit your progress to GitHub and submit the checkpoint inside UDYAP."
        )
    }
    val activeCaption = captionLines[(progressSeconds / 6) % captionLines.size]

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(CourseDarkBg)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // 1. TOP PLAYER HEADER
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
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
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(CourseDarkSurface)
                            .testTag("player_back_button")
                            .clickable { onSaveLater() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = PureWhite,
                            modifier = Modifier.size(19.dp)
                        )
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = course.shortTitle,
                            color = PureWhite,
                            fontSize = 15.5.sp,
                            fontWeight = FontWeight.SemiBold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = "Lesson ${currentLessonIndex + 1} of ${lessonsList.size}",
                            color = CourseDarkMuted,
                            fontSize = 11.5.sp
                        )
                    }
                }

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(if (isBookmarked) UdyapCrimson.copy(alpha = 0.2f) else CourseDarkSurface)
                            .clickable {
                                isBookmarked = !isBookmarked
                                Toast.makeText(context, if (isBookmarked) "Lesson bookmarked" else "Bookmark removed", Toast.LENGTH_SHORT).show()
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = if (isBookmarked) Icons.Filled.Bookmark else Icons.Filled.BookmarkBorder,
                            contentDescription = "Bookmark",
                            tint = if (isBookmarked) UdyapCrimson else PureWhite,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(CourseDarkSurface)
                            .clickable {
                                Toast.makeText(context, "Course lesson link copied!", Toast.LENGTH_SHORT).show()
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Share,
                            contentDescription = "Share",
                            tint = PureWhite,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }

            // 2. INTERACTIVE VIDEO VIEWPORT (Cleanly separated stage & controls for 100% reliable Pause + Fullscreen)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .aspectRatio(16f / 9.5f)
                    .clip(RoundedCornerShape(18.dp))
                    .background(Color.Black)
            ) {
                VideoPlayerStageContent(
                    lessonTitle = currentLesson.first,
                    courseTitle = course.shortTitle,
                    progressSeconds = progressSeconds,
                    totalSeconds = totalSeconds,
                    isPlaying = isPlaying,
                    captionsOn = captionsOn,
                    activeCaption = activeCaption,
                    playbackSpeed = playbackSpeed,
                    showControls = showControls,
                    isFullScreen = false,
                    onToggleControls = { showControls = !showControls },
                    onTogglePlayPause = { isPlaying = !isPlaying },
                    onSeekBy = { delta ->
                        progressSeconds = (progressSeconds + delta).coerceIn(0, totalSeconds)
                    },
                    onSeekTo = { newSec ->
                        progressSeconds = newSec.coerceIn(0, totalSeconds)
                    },
                    onCycleSpeed = {
                        playbackSpeed = when (playbackSpeed) {
                            1.0f -> 1.25f
                            1.25f -> 1.5f
                            1.5f -> 2.0f
                            else -> 1.0f
                        }
                    },
                    onToggleCaptions = { captionsOn = !captionsOn },
                    onToggleFullScreen = {
                        isFullScreen = true
                        showControls = true
                    },
                    onToggleRotate = null,
                    onPrevLesson = if (currentLessonIndex > 0) {
                        {
                            currentLessonIndex -= 1
                            progressSeconds = 0
                            isPlaying = true
                        }
                    } else null,
                    onNextLesson = if (currentLessonIndex < lessonsList.lastIndex) {
                        {
                            currentLessonIndex += 1
                            progressSeconds = 0
                            isPlaying = true
                        }
                    } else null
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 3. LESSON TITLE + QUICK ACTION PILLS
            Column(modifier = Modifier.padding(horizontal = 18.dp)) {
                Text(
                    text = currentLesson.first,
                    color = PureWhite,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold,
                    lineHeight = 23.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${course.instructor}  •  ${if (isPlaying) "Playing at ${playbackSpeed}x" else "Paused"}",
                    color = CourseDarkMuted,
                    fontSize = 12.5.sp
                )

                Spacer(modifier = Modifier.height(14.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = if (isLiked) UdyapCrimson.copy(alpha = 0.2f) else CourseDarkSurface,
                        modifier = Modifier
                            .weight(1f)
                            .height(42.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .clickable {
                                isLiked = !isLiked
                                likeCount += if (isLiked) 1 else -1
                            }
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Icon(
                                imageVector = if (isLiked) Icons.Filled.ThumbUp else Icons.Outlined.ThumbUp,
                                contentDescription = "Like",
                                tint = if (isLiked) UdyapCrimson else PureWhite,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "$likeCount",
                                color = if (isLiked) UdyapCrimson else PureWhite,
                                fontSize = 12.5.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }

                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = CourseDarkSurface,
                        modifier = Modifier
                            .weight(1f)
                            .height(42.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .testTag("player_fullscreen_pill")
                            .clickable {
                                isFullScreen = true
                                showControls = true
                            }
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Fullscreen,
                                contentDescription = "Full Screen",
                                tint = PureWhite,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Full Screen",
                                color = PureWhite,
                                fontSize = 12.5.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }

                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = CourseDarkSurface,
                        modifier = Modifier
                            .weight(1f)
                            .height(42.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .clickable {
                                isPlaying = !isPlaying
                            }
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Icon(
                                imageVector = if (isPlaying) Icons.Filled.Pause else Icons.Filled.PlayArrow,
                                contentDescription = if (isPlaying) "Pause" else "Play",
                                tint = UdyapCrimson,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                text = if (isPlaying) "Pause" else "Resume",
                                color = PureWhite,
                                fontSize = 12.5.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // 4. TABS: Curriculum / Notes
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp),
                horizontalArrangement = Arrangement.spacedBy(22.dp)
            ) {
                listOf("Lessons (${lessonsList.size})", "Quick Notes").forEachIndexed { idx, label ->
                    val active = selectedTab == idx
                    Column(
                        horizontalAlignment = Alignment.Start,
                        modifier = Modifier.clickable { selectedTab = idx }
                    ) {
                        Text(
                            text = label,
                            color = if (active) PureWhite else CourseDarkMuted,
                            fontSize = 14.5.sp,
                            fontWeight = if (active) FontWeight.SemiBold else FontWeight.Normal
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Box(
                            modifier = Modifier
                                .width(28.dp)
                                .height(2.5.dp)
                                .clip(RoundedCornerShape(2.dp))
                                .background(if (active) UdyapCrimson else Color.Transparent)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            if (selectedTab == 0) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 18.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    lessonsList.forEachIndexed { idx, (lessonTitle, duration, done) ->
                        val isCurrent = idx == currentLessonIndex
                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = if (isCurrent) CourseDarkCard else CourseDarkSurface,
                            border = if (isCurrent) BorderStroke(1.dp, UdyapCrimson.copy(alpha = 0.65f)) else null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(14.dp))
                                .clickable {
                                    currentLessonIndex = idx
                                    progressSeconds = 0
                                    isPlaying = true
                                }
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 14.dp, vertical = 12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(34.dp)
                                            .clip(CircleShape)
                                            .background(if (isCurrent) UdyapCrimson else CourseDarkBg),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = if (isCurrent && isPlaying) Icons.Filled.Pause
                                            else if (done) Icons.Filled.CheckCircle
                                            else Icons.Filled.PlayArrow,
                                            contentDescription = null,
                                            tint = if (done && !isCurrent) SuccessGreen else PureWhite,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = lessonTitle,
                                            color = PureWhite,
                                            fontSize = 13.5.sp,
                                            fontWeight = if (isCurrent) FontWeight.SemiBold else FontWeight.Normal,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                        Text(
                                            text = if (isCurrent) "Now Playing • $duration" else duration,
                                            color = if (isCurrent) UdyapCrimson else CourseDarkMuted,
                                            fontSize = 11.5.sp
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = CourseDarkSurface,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 18.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text("Key Takeaways", color = PureWhite, fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
                        Text(
                            text = "• Break UI into small reusable components with clear state ownership.\n• Use clean responsive spacing (8dp grid) so layouts adapt cleanly on all devices.\n• Practice each module exercise to unlock your UDYAP Developer Certificate.",
                            color = CourseDarkMuted,
                            fontSize = 13.sp,
                            lineHeight = 20.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(28.dp))
        }
    }

    // =========================================================================
    // TRUE FULL-SCREEN IMMERSIVE VIDEO PLAYER MODAL
    // =========================================================================
    if (isFullScreen) {
        Dialog(
            onDismissRequest = {
                isFullScreen = false
                isLandscapeRotated = false
            },
            properties = DialogProperties(
                usePlatformDefaultWidth = false,
                decorFitsSystemWindows = false
            )
        ) {
            BackHandler {
                isFullScreen = false
                isLandscapeRotated = false
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
                    .statusBarsPadding()
                    .navigationBarsPadding(),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer {
                            if (isLandscapeRotated) {
                                rotationZ = 90f
                            }
                        }
                ) {
                    VideoPlayerStageContent(
                        lessonTitle = currentLesson.first,
                        courseTitle = course.shortTitle,
                        progressSeconds = progressSeconds,
                        totalSeconds = totalSeconds,
                        isPlaying = isPlaying,
                        captionsOn = captionsOn,
                        activeCaption = activeCaption,
                        playbackSpeed = playbackSpeed,
                        showControls = showControls,
                        isFullScreen = true,
                        onToggleControls = { showControls = !showControls },
                        onTogglePlayPause = { isPlaying = !isPlaying },
                        onSeekBy = { delta ->
                            progressSeconds = (progressSeconds + delta).coerceIn(0, totalSeconds)
                        },
                        onSeekTo = { newSec ->
                            progressSeconds = newSec.coerceIn(0, totalSeconds)
                        },
                        onCycleSpeed = {
                            playbackSpeed = when (playbackSpeed) {
                                1.0f -> 1.25f
                                1.25f -> 1.5f
                                1.5f -> 2.0f
                                else -> 1.0f
                            }
                        },
                        onToggleCaptions = { captionsOn = !captionsOn },
                        onToggleFullScreen = {
                            isFullScreen = false
                            isLandscapeRotated = false
                        },
                        onToggleRotate = {
                            isLandscapeRotated = !isLandscapeRotated
                        },
                        onPrevLesson = if (currentLessonIndex > 0) {
                            {
                                currentLessonIndex -= 1
                                progressSeconds = 0
                                isPlaying = true
                            }
                        } else null,
                        onNextLesson = if (currentLessonIndex < lessonsList.lastIndex) {
                            {
                                currentLessonIndex += 1
                                progressSeconds = 0
                                isPlaying = true
                            }
                        } else null
                    )
                }
            }
        }
    }
}

@Composable
private fun VideoPlayerStageContent(
    lessonTitle: String,
    courseTitle: String,
    progressSeconds: Int,
    totalSeconds: Int,
    isPlaying: Boolean,
    captionsOn: Boolean,
    activeCaption: String,
    playbackSpeed: Float,
    showControls: Boolean,
    isFullScreen: Boolean,
    onToggleControls: () -> Unit,
    onTogglePlayPause: () -> Unit,
    onSeekBy: (Int) -> Unit,
    onSeekTo: (Int) -> Unit,
    onCycleSpeed: () -> Unit,
    onToggleCaptions: () -> Unit,
    onToggleFullScreen: () -> Unit,
    onToggleRotate: (() -> Unit)?,
    onPrevLesson: (() -> Unit)?,
    onNextLesson: (() -> Unit)?
) {
    val infiniteTransition = rememberInfiniteTransition(label = "videoPulse")
    val pulsePhase by infiniteTransition.animateFloat(
        initialValue = 0.25f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 850, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseAlpha"
    )

    val currentMin = progressSeconds / 60
    val currentSec = progressSeconds % 60
    val totalMin = totalSeconds / 60
    val totalSec = totalSeconds % 60

    Box(modifier = Modifier.fillMaxSize()) {
        // LAYER 1: Video Visual Stage (tapping empty video toggles controls cleanly without fighting buttons)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) { onToggleControls() }
        ) {
            Image(
                painter = painterResource(id = R.drawable.udyap_red_banner),
                contentDescription = lessonTitle,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                Color(0xCC0D0A14),
                                Color(0xBB100C18),
                                Color(0xE608060C)
                            )
                        )
                    )
            )

            // Live Animated Lecture / Code Slide Visual (freezes immediately when paused!)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = if (isFullScreen) 24.dp else 14.dp, vertical = if (isFullScreen) 58.dp else 12.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(
                                if (isPlaying) UdyapCrimson.copy(alpha = pulsePhase)
                                else Color(0xFFF59E0B)
                            )
                    )
                    Text(
                        text = if (isPlaying) "PLAYING • $courseTitle" else "PAUSED • $courseTitle",
                        color = PureWhite.copy(alpha = 0.85f),
                        fontSize = if (isFullScreen) 12.sp else 10.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = FontFamily.Monospace
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "// $lessonTitle\nval progress = \"${String.format("%02d:%02d", currentMin, currentSec)}\"\nfun renderModule() = UdyapLesson.active(state = ${if (isPlaying) "\"LIVE\"" else "\"PAUSED\""})",
                    color = Color(0xFF93C5FD),
                    fontSize = if (isFullScreen) 13.5.sp else 10.5.sp,
                    lineHeight = if (isFullScreen) 20.sp else 15.sp,
                    fontFamily = FontFamily.Monospace
                )

                // Live Audio Bars (freeze flat when paused so user instantly sees pause working)
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.Bottom,
                    modifier = Modifier.height(16.dp)
                ) {
                    val heights = listOf(0.45f, 0.9f, 0.65f, 1.0f, 0.55f, 0.8f, 0.4f)
                    heights.forEachIndexed { idx, base ->
                        val activeFactor = if (isPlaying) {
                            ((base + if (idx % 2 == 0) pulsePhase * 0.4f else (1f - pulsePhase) * 0.4f)).coerceIn(0.25f, 1f)
                        } else {
                            0.2f
                        }
                        Box(
                            modifier = Modifier
                                .width(3.dp)
                                .fillMaxHeight(activeFactor)
                                .clip(RoundedCornerShape(2.dp))
                                .background(if (isPlaying) UdyapCrimson else CourseDarkMuted)
                        )
                    }
                }
            }

            // Captions overlay above bottom controls
            if (captionsOn) {
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(
                            start = 16.dp,
                            end = 16.dp,
                            bottom = if (showControls) (if (isFullScreen) 76.dp else 56.dp) else 16.dp
                        )
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.Black.copy(alpha = 0.72f))
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                ) {
                    Text(
                        text = activeCaption,
                        color = PureWhite,
                        fontSize = if (isFullScreen) 13.sp else 10.5.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }

        // LAYER 2: Interactive Controls Overlay (Buttons sit cleanly above tap layer)
        AnimatedVisibility(
            visible = showControls || !isPlaying,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.35f))
            ) {
                // Top Bar inside Full-Screen Mode
                if (isFullScreen) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.TopCenter)
                            .padding(horizontal = 16.dp, vertical = 14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            IconButton(
                                onClick = onToggleFullScreen,
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .background(Color.Black.copy(alpha = 0.55f))
                                    .testTag("exit_fullscreen_back")
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Exit Full Screen",
                                    tint = PureWhite
                                )
                            }
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = lessonTitle,
                                    color = PureWhite,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Text(
                                    text = courseTitle,
                                    color = CourseDarkMuted,
                                    fontSize = 11.5.sp
                                )
                            }
                        }

                        if (onToggleRotate != null) {
                            IconButton(
                                onClick = onToggleRotate,
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .background(Color.Black.copy(alpha = 0.55f))
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.ScreenRotation,
                                    contentDescription = "Rotate View",
                                    tint = PureWhite,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }
                }

                // Center Transport Controls: Prev | -10s | Play/Pause | +10s | Next
                Row(
                    modifier = Modifier.align(Alignment.Center),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(if (isFullScreen) 22.dp else 16.dp)
                ) {
                    if (isFullScreen && onPrevLesson != null) {
                        IconButton(
                            onClick = onPrevLesson,
                            modifier = Modifier
                                .size(44.dp)
                                .clip(CircleShape)
                                .background(Color.Black.copy(alpha = 0.5f))
                        ) {
                            Icon(Icons.Filled.SkipPrevious, contentDescription = "Previous Lesson", tint = PureWhite)
                        }
                    }

                    IconButton(
                        onClick = { onSeekBy(-10) },
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(Color.Black.copy(alpha = 0.5f))
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Replay10,
                            contentDescription = "Rewind 10 seconds",
                            tint = PureWhite,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    // Primary Play / Pause Button
                    IconButton(
                        onClick = onTogglePlayPause,
                        modifier = Modifier
                            .size(if (isFullScreen) 66.dp else 56.dp)
                            .clip(CircleShape)
                            .background(UdyapCrimson)
                            .testTag(if (isFullScreen) "fullscreen_play_pause" else "video_play_pause")
                    ) {
                        Icon(
                            imageVector = if (isPlaying) Icons.Filled.Pause else Icons.Filled.PlayArrow,
                            contentDescription = if (isPlaying) "Pause Video" else "Play Video",
                            tint = PureWhite,
                            modifier = Modifier.size(if (isFullScreen) 36.dp else 30.dp)
                        )
                    }

                    IconButton(
                        onClick = { onSeekBy(10) },
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(Color.Black.copy(alpha = 0.5f))
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Forward10,
                            contentDescription = "Forward 10 seconds",
                            tint = PureWhite,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    if (isFullScreen && onNextLesson != null) {
                        IconButton(
                            onClick = onNextLesson,
                            modifier = Modifier
                                .size(44.dp)
                                .clip(CircleShape)
                                .background(Color.Black.copy(alpha = 0.5f))
                        ) {
                            Icon(Icons.Filled.SkipNext, contentDescription = "Next Lesson", tint = PureWhite)
                        }
                    }
                }

                // Bottom Controls Bar: Scrubber + Timestamp + Speed + CC + FullScreen Toggle
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .padding(horizontal = if (isFullScreen) 20.dp else 10.dp, vertical = if (isFullScreen) 12.dp else 6.dp)
                ) {
                    Slider(
                        value = progressSeconds.toFloat(),
                        onValueChange = { onSeekTo(it.toInt()) },
                        valueRange = 0f..totalSeconds.toFloat(),
                        colors = SliderDefaults.colors(
                            thumbColor = UdyapCrimson,
                            activeTrackColor = UdyapCrimson,
                            inactiveTrackColor = PureWhite.copy(alpha = 0.3f)
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(26.dp)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = String.format("%02d:%02d / %02d:%02d", currentMin, currentSec, totalMin, totalSec),
                            color = PureWhite,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                            fontFamily = FontFamily.Monospace
                        )

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            // Speed chip
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(PureWhite.copy(alpha = 0.2f))
                                    .clickable { onCycleSpeed() }
                                    .padding(horizontal = 7.dp, vertical = 3.dp)
                            ) {
                                Text(
                                    text = "${playbackSpeed}x",
                                    color = PureWhite,
                                    fontSize = 10.5.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }

                            // CC toggle
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(if (captionsOn) UdyapCrimson else PureWhite.copy(alpha = 0.2f))
                                    .clickable { onToggleCaptions() }
                                    .padding(horizontal = 6.dp, vertical = 3.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.ClosedCaption,
                                    contentDescription = "Toggle Captions",
                                    tint = PureWhite,
                                    modifier = Modifier.size(15.dp)
                                )
                            }

                            // Working Fullscreen Toggle Button
                            IconButton(
                                onClick = onToggleFullScreen,
                                modifier = Modifier
                                    .size(34.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(PureWhite.copy(alpha = 0.2f))
                                    .testTag(if (isFullScreen) "exit_fullscreen_button" else "enter_fullscreen_button")
                            ) {
                                Icon(
                                    imageVector = if (isFullScreen) Icons.Filled.FullscreenExit else Icons.Filled.Fullscreen,
                                    contentDescription = if (isFullScreen) "Exit Full Screen" else "Enter Full Screen",
                                    tint = PureWhite,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
