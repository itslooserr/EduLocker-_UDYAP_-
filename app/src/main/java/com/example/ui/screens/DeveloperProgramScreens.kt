package com.example.ui.screens

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ClosedCaption
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.FastForward
import androidx.compose.material.icons.filled.Laptop
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Replay5
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material.icons.filled.Subject
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.EduLockerDualCircleLogo
import com.example.ui.theme.CanvasCream
import com.example.ui.theme.DarkBorder
import com.example.ui.theme.OdishaMagenta
import com.example.ui.theme.OdishaMagentaDark
import com.example.ui.theme.PureWhite
import com.example.ui.theme.SoftBlushPink
import com.example.ui.theme.SoftCyan
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.TextDark
import com.example.ui.theme.TextMuted
import com.example.ui.viewmodel.EduLockerViewModel

// Screen 28: Developer Program Overview
@Composable
fun DeveloperProgramIntroScreen(
    onNext: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(CanvasCream)
            .padding(horizontal = 24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(140.dp))

            // Code speech bubble icon (Screenshot 28)
            Surface(
                modifier = Modifier
                    .size(60.dp)
                    .border(2.dp, DarkBorder, RoundedCornerShape(16.dp)),
                shape = RoundedCornerShape(16.dp),
                color = PureWhite
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = "</>",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = TextDark
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = "Developer Program",
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.ExtraBold,
                color = TextDark,
                fontSize = 22.sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "A practical learning program designed to help students build coding, software development, and technology skills through structured courses, hands-on projects, and real-world challenges.",
                style = MaterialTheme.typography.bodyMedium,
                color = TextDark.copy(alpha = 0.85f),
                fontSize = 14.sp,
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Available Courses",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = TextDark
            )

            Spacer(modifier = Modifier.height(8.dp))

            val coursesList = listOf(
                "Programming Fundamentals: Learn the basics of programming",
                "Web Development: Build websites using HTML, CSS, and JavaScript.",
                "App Development: Learn how mobile applications are designed and developed.",
                "Git & GitHub: Learn version control and how developers collaborate.",
                "AI & Machine Learning Basics: Explore the fundamentals of AI and machine learning."
            )

            coursesList.forEach { c ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 3.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Text("• ", color = TextDark, fontWeight = FontWeight.Bold)
                    Text(
                        text = c,
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextDark.copy(alpha = 0.85f),
                        fontSize = 13.sp,
                        lineHeight = 18.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(60.dp))

            // Bottom Next Button
            Button(
                onClick = onNext,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth(0.55f)
                    .height(52.dp)
                    .border(2.dp, DarkBorder, RoundedCornerShape(30.dp)),
                shape = RoundedCornerShape(30.dp),
                colors = ButtonDefaults.buttonColors(containerColor = OdishaMagenta)
            ) {
                Text(
                    text = "Next",
                    color = PureWhite,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

// Screen 29: About Developer Program
@Composable
fun DeveloperProgramAboutScreen(
    onContinue: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(CanvasCream)
            .padding(horizontal = 24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(140.dp))

            EduLockerDualCircleLogo(size = 46.dp)
            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "About the\nDeveloper Program 🚀",
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.ExtraBold,
                color = TextDark,
                fontSize = 22.sp,
                lineHeight = 28.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "We provide carefully curated courses, learning materials, projects, and practical challenges to help you develop your skills step by step. Your progress and performance throughout the program will be evaluated based on your participation, learning, and completed activities.\n\nBased on your performance and successful completion of the program, you may receive a Developer Program Certificate, recognizing your skills, effort, and achievement.",
                style = MaterialTheme.typography.bodyMedium,
                color = TextDark.copy(alpha = 0.85f),
                fontSize = 14.sp,
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Learn. Build. Perform. Get Certified. 🚀",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = TextDark
            )

            Spacer(modifier = Modifier.height(16.dp))

            // By HP LIFE branding (Screenshot 29)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "By",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = TextDark
                )
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF0096D6)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "hp",
                        color = PureWhite,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 18.sp
                    )
                }
                Text(
                    text = "LIFE",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF0096D6),
                    letterSpacing = 1.sp
                )
            }

            Spacer(modifier = Modifier.height(60.dp))

            // Bottom Continue Button
            Button(
                onClick = onContinue,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth(0.55f)
                    .height(52.dp)
                    .border(2.dp, DarkBorder, RoundedCornerShape(30.dp)),
                shape = RoundedCornerShape(30.dp),
                colors = ButtonDefaults.buttonColors(containerColor = OdishaMagenta)
            ) {
                Text(
                    text = "Continue",
                    color = PureWhite,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

// Screen 30: Select a Course
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
                text = "Select a Course",
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.ExtraBold,
                color = TextDark,
                fontSize = 22.sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Choose a course that matches your interests and start building your skills with the right resources and practical learning.",
                style = MaterialTheme.typography.bodyMedium,
                color = TextDark.copy(alpha = 0.8f),
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            // 5 Course Pill Cards (Screenshot 30)
            courses.forEach { (courseName, icon) ->
                val isSelected = courseName == selectedCourse
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp)
                        .height(56.dp)
                        .border(1.5.dp, DarkBorder, RoundedCornerShape(18.dp))
                        .clip(RoundedCornerShape(18.dp))
                        .clickable { viewModel.selectedCourse.value = courseName },
                    color = if (isSelected) OdishaMagenta else SoftBlushPink
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Surface(
                            modifier = Modifier
                                .size(34.dp)
                                .border(1.dp, if (isSelected) PureWhite else DarkBorder, RoundedCornerShape(8.dp)),
                            shape = RoundedCornerShape(8.dp),
                            color = if (isSelected) PureWhite.copy(alpha = 0.2f) else PureWhite
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = icon,
                                    contentDescription = null,
                                    tint = if (isSelected) PureWhite else TextDark,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }

                        Text(
                            text = courseName,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = if (isSelected) PureWhite else TextDark,
                            fontSize = 14.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Bottom Continue Button
            Button(
                onClick = onContinue,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth(0.55f)
                    .height(52.dp)
                    .border(2.dp, DarkBorder, RoundedCornerShape(30.dp)),
                shape = RoundedCornerShape(30.dp),
                colors = ButtonDefaults.buttonColors(containerColor = OdishaMagenta)
            ) {
                Text(
                    text = "Continue",
                    color = PureWhite,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

// Screen 31: Choose Learning Level
@Composable
fun CourseLevelSelectorScreen(
    viewModel: EduLockerViewModel,
    onContinue: () -> Unit,
    modifier: Modifier = Modifier
) {
    val selectedCourse by viewModel.selectedCourse.collectAsState()
    val selectedLevel by viewModel.selectedLevel.collectAsState()

    val levels = listOf(
        Pair("Beginner", "Start from the basics with beginner friendly video lessons, learning resources, and simple practical tasks."),
        Pair("Intermediate", "Improve your existing skills through advanced concepts, projects, and coding challenges."),
        Pair("Advanced", "Work on real world projects, advanced development concepts, and complex challenges.")
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(CanvasCream)
            .padding(horizontal = 24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(120.dp))

            EduLockerDualCircleLogo(size = 46.dp)
            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = selectedCourse,
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.ExtraBold,
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

            Spacer(modifier = Modifier.height(28.dp))

            // 3 Level Radios (Screenshot 31)
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
                            fontWeight = FontWeight.Bold,
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

            Spacer(modifier = Modifier.height(50.dp))

            // Bottom Continue
            Button(
                onClick = onContinue,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth(0.55f)
                    .height(52.dp)
                    .border(2.dp, DarkBorder, RoundedCornerShape(30.dp)),
                shape = RoundedCornerShape(30.dp),
                colors = ButtonDefaults.buttonColors(containerColor = OdishaMagenta)
            ) {
                Text(
                    text = "Continue",
                    color = PureWhite,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

// Screen 32: Complete Video Tutorial Lesson Overview
@Composable
fun LessonOverviewScreen(
    onWatch: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(CanvasCream)
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
                text = "Complete the Video Tutorial",
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.ExtraBold,
                color = TextDark,
                fontSize = 22.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Introduction to Web Development",
                style = MaterialTheme.typography.bodyLarge,
                color = TextDark.copy(alpha = 0.8f),
                fontSize = 15.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Laptop Screen Mockup Graphic (Screenshot 32)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(190.dp)
                    .border(3.dp, OdishaMagenta, RoundedCornerShape(20.dp))
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color(0xFF0F172A)),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(14.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Laptop,
                        contentDescription = null,
                        tint = PureWhite,
                        modifier = Modifier.size(54.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "<html><body><h1>Hello Odisha!</h1></body></html>",
                        color = SuccessGreen,
                        style = MaterialTheme.typography.labelSmall,
                        fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Learn the fundamentals of web development and understand how websites are built. This beginner-friendly tutorial introduces you to HTML, CSS, and JavaScript and helps you take your first step toward creating your own websites.",
                style = MaterialTheme.typography.bodyMedium,
                color = TextDark.copy(alpha = 0.85f),
                fontSize = 13.sp,
                lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Progress & Save for later
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Progress: 1/17",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = TextDark
                )

                Surface(
                    modifier = Modifier
                        .height(38.dp)
                        .border(1.5.dp, DarkBorder, RoundedCornerShape(12.dp))
                        .clip(RoundedCornerShape(12.dp))
                        .clickable {
                            Toast.makeText(context, "Lesson saved to your EduLocker bookmarks", Toast.LENGTH_SHORT).show()
                        },
                    color = Color(0xFF1E293B)
                ) {
                    Box(
                        modifier = Modifier.padding(horizontal = 14.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Save for Later",
                            color = PureWhite,
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Keep Learning to Unlock More",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = TextDark
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Complete the current lessons and activities to unlock the next steps in your learning journey.",
                style = MaterialTheme.typography.bodyMedium,
                color = TextMuted,
                fontSize = 13.sp
            )

            Spacer(modifier = Modifier.height(30.dp))

            // Bottom Watch Button
            Button(
                onClick = onWatch,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth(0.55f)
                    .height(52.dp)
                    .border(2.dp, DarkBorder, RoundedCornerShape(30.dp)),
                shape = RoundedCornerShape(30.dp),
                colors = ButtonDefaults.buttonColors(containerColor = OdishaMagenta)
            ) {
                Text(
                    text = "Watch",
                    color = PureWhite,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

// Screen 33: Full Interactive Minimalist Clean Video Player Screen
@Composable
fun VideoPlayerScreen(
    viewModel: EduLockerViewModel,
    onSaveLater: () -> Unit,
    modifier: Modifier = Modifier
) {
    var progressSeconds by remember { mutableIntStateOf(884) } // 14:44
    var isPlaying by remember { mutableStateOf(true) }
    var captionsOn by remember { mutableStateOf(true) }
    var playbackSpeed by remember { mutableFloatStateOf(1.0f) }
    var isBookmarked by remember { mutableStateOf(false) }
    var isLiked by remember { mutableStateOf(false) }
    var likeCount by remember { mutableIntStateOf(1428) }
    var isDownloaded by remember { mutableStateOf(false) }
    var selectedTab by remember { mutableIntStateOf(0) } // 0: Curriculum, 1: Notes, 2: Discussion
    var currentLessonIndex by remember { mutableIntStateOf(2) } // Lesson 3 currently playing
    var showControls by remember { mutableStateOf(true) }
    val totalSeconds = 3265 // 54:25
    val context = LocalContext.current
    val selectedCourse by viewModel.selectedCourse.collectAsState()

    val youtubeUrl = "https://youtu.be/ktoYUycRXTA?si=bMkGKp-eR7SfZ-cs"

    val lessonsList = remember {
        listOf(
            Triple("01. Course Orientation & Environment Setup", "10:15", true),
            Triple("02. HTML5 Semantic Elements & Accessibility", "18:40", true),
            Triple("03. Modern Web Architecture & DOM Tree", "54:25", false), // Current
            Triple("04. CSS3 Flexbox & Responsive Layout Systems", "35:10", false),
            Triple("05. JavaScript ES6+ & Async Fetch API", "44:20", false),
            Triple("06. Capstone: Odisha Public Services Portal", "50:00", false)
        )
    }

    LaunchedEffect(isPlaying, playbackSpeed) {
        while (isPlaying) {
            val delayMs = (1000 / playbackSpeed).toLong()
            kotlinx.coroutines.delay(delayMs)
            progressSeconds += 1
            if (progressSeconds >= totalSeconds) {
                isPlaying = false
                break
            }
        }
    }

    val currentMinutes = progressSeconds / 60
    val currentSecs = progressSeconds % 60
    val totalMinutes = totalSeconds / 60
    val totalSecs = totalSeconds % 60

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
            // Top Navigation Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    IconButton(
                        onClick = { viewModel.navigateToPage(11) },
                        modifier = Modifier
                            .size(38.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(PureWhite)
                            .border(1.dp, Color(0xFFE2E8F0), RoundedCornerShape(12.dp))
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = TextDark,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    Column {
                        Text(
                            text = "Web Development",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.ExtraBold,
                            color = TextDark,
                            fontSize = 15.sp
                        )
                        Text(
                            text = "Lesson ${currentLessonIndex + 1} of ${lessonsList.size}",
                            style = MaterialTheme.typography.labelSmall,
                            color = TextMuted,
                            fontSize = 11.sp
                        )
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    IconButton(
                        onClick = {
                            isBookmarked = !isBookmarked
                            Toast.makeText(
                                context,
                                if (isBookmarked) "Lesson bookmarked to EduLocker" else "Bookmark removed",
                                Toast.LENGTH_SHORT
                            ).show()
                        },
                        modifier = Modifier
                            .size(38.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (isBookmarked) SoftBlushPink else PureWhite)
                            .border(1.dp, if (isBookmarked) OdishaMagenta else Color(0xFFE2E8F0), RoundedCornerShape(12.dp))
                    ) {
                        Icon(
                            imageVector = if (isBookmarked) Icons.Filled.Bookmark else Icons.Filled.BookmarkBorder,
                            contentDescription = "Bookmark",
                            tint = if (isBookmarked) OdishaMagenta else TextDark,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    IconButton(
                        onClick = {
                            Toast.makeText(context, "Lesson link copied to clipboard!", Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier
                            .size(38.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(PureWhite)
                            .border(1.dp, Color(0xFFE2E8F0), RoundedCornerShape(12.dp))
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Share,
                            contentDescription = "Share",
                            tint = TextDark,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }

            // Clean Minimal Video Player Viewport
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .height(225.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFF0F172A),
                                Color(0xFF1E293B),
                                Color(0xFF090D16)
                            )
                        )
                    )
                    .border(1.5.dp, Color(0xFF334155), RoundedCornerShape(20.dp))
                    .clickable { showControls = !showControls }
            ) {
                // Background Simulated Live Code Editor Visual
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(14.dp)
                ) {
                    // Simulated IDE Title bar
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                            Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(Color(0xFFEF4444)))
                            Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(Color(0xFFF59E0B)))
                            Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(Color(0xFF10B981)))
                        }
                        Text(
                            text = "index.html • DOM Tree Inspection",
                            color = Color(0xFF94A3B8),
                            fontSize = 10.sp,
                            fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace
                        )
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(Color(0xFFEF4444).copy(alpha = 0.2f))
                                .padding(horizontal = 5.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "● LIVE",
                                color = Color(0xFFF87171),
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Simulated Code lines
                    Text(
                        text = "1  const app = document.querySelector('#edulocker-root');\n2  const navTree = buildNavigationTree(state.modules);\n3  app.appendChild(navTree);\n4  // Active Lesson: DOM Mutation & Event Loop",
                        color = Color(0xFF38BDF8),
                        fontSize = 11.sp,
                        fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                        lineHeight = 15.sp
                    )
                }

                // Overlay Controls (when visible)
                if (showControls) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = 0.45f))
                            .padding(12.dp)
                    ) {
                        // Center Play / Rewind / Fast-Forward
                        Row(
                            modifier = Modifier.align(Alignment.Center),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(20.dp)
                        ) {
                            // 5s Rewind
                            IconButton(
                                onClick = {
                                    progressSeconds = (progressSeconds - 5).coerceAtLeast(0)
                                    Toast.makeText(context, "-5s", Toast.LENGTH_SHORT).show()
                                },
                                modifier = Modifier
                                    .size(42.dp)
                                    .clip(CircleShape)
                                    .background(Color.White.copy(alpha = 0.2f))
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Replay5,
                                    contentDescription = "Rewind 5s",
                                    tint = PureWhite,
                                    modifier = Modifier.size(22.dp)
                                )
                            }

                            // Play / Pause
                            IconButton(
                                onClick = { isPlaying = !isPlaying },
                                modifier = Modifier
                                    .size(58.dp)
                                    .clip(CircleShape)
                                    .background(OdishaMagenta)
                            ) {
                                Icon(
                                    imageVector = if (isPlaying) Icons.Filled.Pause else Icons.Filled.PlayArrow,
                                    contentDescription = if (isPlaying) "Pause" else "Play",
                                    tint = PureWhite,
                                    modifier = Modifier.size(34.dp)
                                )
                            }

                            // 5s Forward
                            IconButton(
                                onClick = {
                                    progressSeconds = (progressSeconds + 5).coerceAtMost(totalSeconds)
                                    Toast.makeText(context, "+5s", Toast.LENGTH_SHORT).show()
                                },
                                modifier = Modifier
                                    .size(42.dp)
                                    .clip(CircleShape)
                                    .background(Color.White.copy(alpha = 0.2f))
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.FastForward,
                                    contentDescription = "Forward 5s",
                                    tint = PureWhite,
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                        }

                        // Bottom In-Video Bar (Time, Speed, CC, Quality)
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.BottomCenter),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = String.format("%02d:%02d / %02d:%02d", currentMinutes, currentSecs, totalMinutes, totalSecs),
                                color = PureWhite,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace
                            )

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                // Speed Chip
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(Color.White.copy(alpha = 0.25f))
                                        .clickable {
                                            playbackSpeed = when (playbackSpeed) {
                                                1.0f -> 1.25f
                                                1.25f -> 1.5f
                                                1.5f -> 2.0f
                                                else -> 1.0f
                                            }
                                        }
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = "${playbackSpeed}x",
                                        color = PureWhite,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }

                                // CC Chip
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(if (captionsOn) OdishaMagenta else Color.White.copy(alpha = 0.25f))
                                        .clickable { captionsOn = !captionsOn }
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = "CC",
                                        color = PureWhite,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }

                                // 1080p Chip
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(Color.White.copy(alpha = 0.25f))
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = "1080p",
                                        color = PureWhite,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                            }
                        }
                    }
                }

                // Live Captions Banner
                if (captionsOn && !showControls) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 10.dp, start = 14.dp, end = 14.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .background(Color.Black.copy(alpha = 0.8f))
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        val sampleSubtitle = when {
                            progressSeconds < 890 -> "[Instructor]: Welcome back! Today we are exploring the DOM tree structure."
                            progressSeconds < 910 -> "[Instructor]: Every HTML element is represented as a node object in memory."
                            progressSeconds < 930 -> "[Instructor]: Use document.querySelector() to target class names dynamically."
                            else -> "[Instructor]: Let's write an event listener for user click interactions."
                        }
                        Text(
                            text = sampleSubtitle,
                            color = PureWhite,
                            fontSize = 10.5.sp,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                    }
                }
            }

            // Scrubber Slider
            Slider(
                value = progressSeconds.toFloat(),
                onValueChange = { progressSeconds = it.toInt() },
                valueRange = 0f..totalSeconds.toFloat(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = SliderDefaults.colors(
                    thumbColor = OdishaMagenta,
                    activeTrackColor = OdishaMagenta,
                    inactiveTrackColor = SoftBlushPink
                )
            )

            // Modern Minimal Content Card
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(20.dp),
                color = PureWhite,
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    // Badges Row
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
                                shape = RoundedCornerShape(6.dp),
                                color = Color(0xFFE0F2FE)
                            ) {
                                Text(
                                    text = "HP LIFE CERTIFIED",
                                    color = Color(0xFF0369A1),
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 7.dp, vertical = 3.dp)
                                )
                            }

                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = SoftBlushPink
                            ) {
                                Text(
                                    text = "FREE COURSE",
                                    color = OdishaMagentaDark,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 7.dp, vertical = 3.dp)
                                )
                            }
                        }

                        Text(
                            text = "54 Mins • Video",
                            fontSize = 11.5.sp,
                            color = Color(0xFF64748B),
                            fontWeight = FontWeight.Medium
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Title
                    Text(
                        text = lessonsList[currentLessonIndex].first,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.ExtraBold,
                        color = TextDark,
                        fontSize = 17.sp,
                        lineHeight = 22.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Instructor Info Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Surface(
                            modifier = Modifier.size(34.dp),
                            shape = CircleShape,
                            color = SoftBlushPink,
                            border = androidx.compose.foundation.BorderStroke(1.dp, OdishaMagenta)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = "AM",
                                    color = OdishaMagenta,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp
                                )
                            }
                        }

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Prof. Arvind Mahapatra",
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.5.sp,
                                color = TextDark
                            )
                            Text(
                                text = "Odisha State ICT Academy • ★ 4.9 (1.8k reviews)",
                                fontSize = 10.5.sp,
                                color = Color(0xFF64748B)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Quick Action Buttons Row (Like, Download, Share, Open YouTube)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Like Button
                        Surface(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(10.dp))
                                .clickable {
                                    isLiked = !isLiked
                                    likeCount += if (isLiked) 1 else -1
                                },
                            shape = RoundedCornerShape(10.dp),
                            color = if (isLiked) SoftBlushPink else Color(0xFFF8FAFC),
                            border = androidx.compose.foundation.BorderStroke(1.dp, if (isLiked) OdishaMagenta else Color(0xFFE2E8F0))
                        ) {
                            Row(
                                modifier = Modifier.padding(vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.ThumbUp,
                                    contentDescription = "Like",
                                    tint = if (isLiked) OdishaMagenta else Color(0xFF64748B),
                                    modifier = Modifier.size(15.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "$likeCount",
                                    fontSize = 11.5.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isLiked) OdishaMagenta else TextDark
                                )
                            }
                        }

                        // Download Button
                        Surface(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(10.dp))
                                .clickable {
                                    isDownloaded = !isDownloaded
                                    if (isDownloaded) {
                                        viewModel.saveCourseForLater(
                                            courseName = selectedCourse,
                                            lessonTitle = lessonsList[currentLessonIndex].first
                                        )
                                    }
                                    Toast.makeText(
                                        context,
                                        if (isDownloaded) "Lesson downloaded for offline viewing" else "Download removed",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                },
                            shape = RoundedCornerShape(10.dp),
                            color = if (isDownloaded) Color(0xFFECFDF5) else Color(0xFFF8FAFC),
                            border = androidx.compose.foundation.BorderStroke(1.dp, if (isDownloaded) SuccessGreen else Color(0xFFE2E8F0))
                        ) {
                            Row(
                                modifier = Modifier.padding(vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    imageVector = if (isDownloaded) Icons.Filled.CheckCircle else Icons.Filled.PlayArrow,
                                    contentDescription = "Download",
                                    tint = if (isDownloaded) SuccessGreen else Color(0xFF64748B),
                                    modifier = Modifier.size(15.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = if (isDownloaded) "Saved" else "Offline",
                                    fontSize = 11.5.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isDownloaded) SuccessGreen else TextDark
                                )
                            }
                        }

                        // YouTube Link Button
                        Surface(
                            modifier = Modifier
                                .weight(1.3f)
                                .clip(RoundedCornerShape(10.dp))
                                .clickable {
                                    try {
                                        val intent = android.content.Intent(
                                            android.content.Intent.ACTION_VIEW,
                                            android.net.Uri.parse(youtubeUrl)
                                        )
                                        context.startActivity(intent)
                                    } catch (e: Exception) {
                                        Toast.makeText(context, "Opening YouTube stream...", Toast.LENGTH_SHORT).show()
                                    }
                                },
                            shape = RoundedCornerShape(10.dp),
                            color = Color(0xFFFFE4E6),
                            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFFDA4AF))
                        ) {
                            Row(
                                modifier = Modifier.padding(vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.PlayArrow,
                                    contentDescription = "YouTube",
                                    tint = Color(0xFFE11D48),
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "YouTube ↗",
                                    fontSize = 11.5.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF9F1239)
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Segmented Tabs Header (Curriculum, Notes, Q&A)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val tabs = listOf("Curriculum (6)", "Key Notes", "Q&A (24)")
                tabs.forEachIndexed { index, tabTitle ->
                    val isSelected = selectedTab == index
                    Surface(
                        modifier = Modifier
                            .weight(1f)
                            .height(38.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .clickable { selectedTab = index },
                        shape = RoundedCornerShape(10.dp),
                        color = if (isSelected) OdishaMagenta else PureWhite,
                        border = androidx.compose.foundation.BorderStroke(1.dp, if (isSelected) OdishaMagenta else Color(0xFFE2E8F0))
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = tabTitle,
                                color = if (isSelected) PureWhite else Color(0xFF475569),
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Tab Contents
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                when (selectedTab) {
                    0 -> {
                        // Curriculum Lessons List
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            lessonsList.forEachIndexed { idx, (lessonTitle, duration, isCompleted) ->
                                val isCurrent = idx == currentLessonIndex
                                Surface(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(14.dp))
                                        .clickable {
                                            currentLessonIndex = idx
                                            progressSeconds = 0
                                            isPlaying = true
                                            Toast.makeText(context, "Playing $lessonTitle", Toast.LENGTH_SHORT).show()
                                        },
                                    shape = RoundedCornerShape(14.dp),
                                    color = when {
                                        isCurrent -> SoftBlushPink
                                        else -> PureWhite
                                    },
                                    border = androidx.compose.foundation.BorderStroke(
                                        1.dp,
                                        when {
                                            isCurrent -> OdishaMagenta
                                            else -> Color(0xFFE2E8F0)
                                        }
                                    )
                                ) {
                                    Row(
                                        modifier = Modifier.padding(12.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Row(
                                            modifier = Modifier.weight(1f),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                                        ) {
                                            Surface(
                                                modifier = Modifier.size(32.dp),
                                                shape = CircleShape,
                                                color = when {
                                                    isCompleted -> SuccessGreen
                                                    isCurrent -> OdishaMagenta
                                                    else -> Color(0xFFF1F5F9)
                                                }
                                            ) {
                                                Box(contentAlignment = Alignment.Center) {
                                                    if (isCompleted) {
                                                        Icon(
                                                            imageVector = Icons.Filled.CheckCircle,
                                                            contentDescription = "Done",
                                                            tint = PureWhite,
                                                            modifier = Modifier.size(16.dp)
                                                        )
                                                    } else if (isCurrent) {
                                                        Icon(
                                                            imageVector = Icons.Filled.PlayArrow,
                                                            contentDescription = "Playing",
                                                            tint = PureWhite,
                                                            modifier = Modifier.size(18.dp)
                                                        )
                                                    } else {
                                                        Text(
                                                            text = "${idx + 1}",
                                                            color = Color(0xFF64748B),
                                                            fontSize = 11.sp,
                                                            fontWeight = FontWeight.Bold
                                                        )
                                                    }
                                                }
                                            }

                                            Column {
                                                Text(
                                                    text = lessonTitle,
                                                    fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.SemiBold,
                                                    fontSize = 12.5.sp,
                                                    color = if (isCurrent) OdishaMagentaDark else TextDark,
                                                    maxLines = 1
                                                )
                                                Text(
                                                    text = "$duration • ${if (isCompleted) "Completed" else if (isCurrent) "Now Playing" else "Upcoming"}",
                                                    fontSize = 10.5.sp,
                                                    color = if (isCurrent) OdishaMagenta else Color(0xFF64748B)
                                                )
                                            }
                                        }

                                        if (isCurrent) {
                                            Surface(
                                                shape = RoundedCornerShape(6.dp),
                                                color = OdishaMagenta
                                            ) {
                                                Text(
                                                    text = "PLAYING",
                                                    color = PureWhite,
                                                    fontSize = 9.sp,
                                                    fontWeight = FontWeight.ExtraBold,
                                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    1 -> {
                        // Notes & Snippets Tab
                        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            Surface(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(14.dp),
                                color = PureWhite,
                                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0))
                            ) {
                                Column(modifier = Modifier.padding(14.dp)) {
                                    Text(
                                        text = "📌 Key Lesson Takeaways",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.5.sp,
                                        color = TextDark
                                    )
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(
                                        text = "• DOM (Document Object Model) represents HTML documents as hierarchical tree nodes in RAM.\n• Use document.getElementById() or document.querySelector() to bind reactive events.\n• Flexbox manages 1-dimensional layouts while CSS Grid is designed for 2-dimensional layouts.",
                                        fontSize = 11.5.sp,
                                        color = Color(0xFF334155),
                                        lineHeight = 17.sp
                                    )
                                }
                            }

                            Surface(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(14.dp))
                                    .clickable {
                                        Toast.makeText(context, "Downloading Web_Dev_Notes.pdf to EduLocker Storage", Toast.LENGTH_SHORT).show()
                                    },
                                shape = RoundedCornerShape(14.dp),
                                color = Color(0xFFEFF6FF),
                                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFBFDBFE))
                            ) {
                                Row(
                                    modifier = Modifier.padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                                    ) {
                                        Text(text = "📄", fontSize = 20.sp)
                                        Column {
                                            Text(
                                                text = "Download Lesson Cheatsheet (PDF)",
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 12.5.sp,
                                                color = Color(0xFF1E40AF)
                                            )
                                            Text(
                                                text = "2.4 MB • Certified by Odisha Skill Hub",
                                                fontSize = 10.5.sp,
                                                color = Color(0xFF3B82F6)
                                            )
                                        }
                                    }
                                    Text(
                                        text = "GET ↓",
                                        color = Color(0xFF1D4ED8),
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 11.sp
                                    )
                                }
                            }
                        }
                    }

                    2 -> {
                        // Discussion / Q&A Tab
                        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            Surface(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(14.dp),
                                color = PureWhite,
                                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0))
                            ) {
                                Column(modifier = Modifier.padding(14.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(
                                            text = "Rohan Das • Cuttack",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 12.sp,
                                            color = TextDark
                                        )
                                        Text(
                                            text = "2 hrs ago",
                                            fontSize = 10.sp,
                                            color = Color(0xFF94A3B8)
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = "Q: When should we prefer querySelector over getElementById?",
                                        fontSize = 11.5.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = TextDark
                                    )
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(Color(0xFFF1F5F9))
                                            .padding(8.dp)
                                    ) {
                                        Text(
                                            text = "👨‍🏫 Instructor: querySelector supports all complex CSS selectors (like .card > .title), while getElementById is strictly for single ID lookups with slightly faster micro-benchmarks.",
                                            fontSize = 10.5.sp,
                                            color = Color(0xFF475569),
                                            lineHeight = 15.sp
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Home / Next Lesson Bottom Action Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Surface(
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .border(1.dp, Color(0xFFCBD5E1), RoundedCornerShape(16.dp))
                        .clip(RoundedCornerShape(16.dp))
                        .clickable { viewModel.navigateToPage(11) },
                    color = PureWhite
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = "Dashboard",
                            color = TextDark,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.5.sp
                        )
                    }
                }

                Button(
                    onClick = {
                        val finishedTitle = lessonsList[currentLessonIndex].first
                        viewModel.completeCourseLesson(selectedCourse, finishedTitle)
                        if (currentLessonIndex < lessonsList.size - 1) {
                            currentLessonIndex += 1
                            progressSeconds = 0
                            isPlaying = true
                            Toast.makeText(context, "Next Lesson loaded!", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, "🎉 Course Completed! Certificate generated.", Toast.LENGTH_LONG).show()
                        }
                    },
                    modifier = Modifier
                        .weight(1.4f)
                        .height(48.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = OdishaMagenta)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = "Next Lesson ➔",
                            color = PureWhite,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.5.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}
