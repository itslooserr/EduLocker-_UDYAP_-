package com.example.ui.screens

import android.content.Intent
import android.speech.RecognizerIntent
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.NorthEast
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.ui.theme.CanvasCream
import com.example.ui.theme.DarkBorder
import com.example.ui.theme.NewsreaderFontFamily
import com.example.ui.theme.OdishaMagenta
import com.example.ui.theme.PureWhite
import com.example.ui.theme.SoftBlushPink
import com.example.ui.theme.TextDark
import com.example.ui.theme.TextMuted
import com.example.ui.theme.UdyapAquaCard
import com.example.ui.theme.UdyapCrimson
import com.example.ui.theme.UdyapDishaBubble
import com.example.ui.theme.UdyapMintGreen
import com.example.ui.theme.UdyapPeach
import com.example.ui.theme.UdyapPinkCard
import com.example.ui.theme.UdyapSkyCard
import com.example.ui.theme.UdyapStackPink
import com.example.ui.theme.UdyapYellowCard
import com.example.ui.viewmodel.EduLockerViewModel
import com.example.ui.viewmodel.RecommendationItem
import com.example.util.TextToSpeechHelper
import kotlinx.coroutines.delay

// =========================================================================
// DATA MODELS FOR OPPORTUNITIES (Image 1) & EDITORIAL ARTICLES (Image 2)
// =========================================================================
data class OpportunityCardItem(
    val id: String,
    val company: String,
    val logoType: String,
    val postedAgo: String,
    val title: String,
    val tags: List<String>,
    val compensation: String,
    val location: String,
    val category: String,
    val description: String,
    val isInitiallySaved: Boolean = false
)

data class EditorialArticleItem(
    val id: String,
    val title: String,
    val subtitle: String,
    val readTime: String,
    val category: String,
    val fullContent: String,
    val takeaways: List<String>
)

// =========================================================================
// SCREEN 34: Guide / Ai Smart Think Hub (Exact Replica of 34.png)
// + Full Screen AI Chatbot Page (Exact Replica of image.png & image (1).png)
// + Professional Opportunity Cards (image 1) & Editorial Cards (image 2)
// =========================================================================
@Composable
fun GuideAIScreen(
    viewModel: EduLockerViewModel,
    modifier: Modifier = Modifier
) {
    val isFullScreenChatOpen by viewModel.isAiChatOpen.collectAsState()
    var activeNetworkType by remember { mutableStateOf<String?>(null) }
    var activeArticleItem by remember { mutableStateOf<EditorialArticleItem?>(null) }
    var showQuizBanner by remember { mutableStateOf(true) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(PureWhite)
    ) {
        // 2-SECTION SPLIT LAYOUT:
        // Top Section pinned cleanly to Top; Bottom Disha Ask Dock pinned cleanly to Bottom; flexible breathing gap in the middle!
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
        ) {
            // =================================================================
            // TOP SECTION (Aligned to TOP; scrollable on compact viewports)
            // =================================================================
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                    Spacer(modifier = Modifier.height(14.dp))

                    // 1. CLEAN HEADER: Ai Smart Think / Your Networks
                    Text(
                        text = "Ai Smart Think",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = TextDark,
                        fontSize = 20.sp,
                        letterSpacing = (-0.2).sp
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "Your Networks",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextMuted,
                        fontSize = 13.5.sp
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // 2. 2x3 GRID OF SOFT PEACH PILL BUTTONS
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        NetworkPillButton("My Learning", Modifier.weight(1f)) { activeNetworkType = "My Learning" }
                        NetworkPillButton("MY Applications", Modifier.weight(1f)) { activeNetworkType = "MY Applications" }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        NetworkPillButton("Internships", Modifier.weight(1f)) { activeNetworkType = "Internships" }
                        NetworkPillButton("Job Eligibility", Modifier.weight(1f)) { activeNetworkType = "Job Eligibility" }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        NetworkPillButton("Recognition", Modifier.weight(1f)) { activeNetworkType = "Recognition" }
                        NetworkPillButton("+  More", Modifier.weight(1f)) { activeNetworkType = "More Opportunities" }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // 3. ✨ Recommended for You Header
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(text = "✨", fontSize = 18.sp)
                        Text(
                            text = "Recommended for You",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = TextDark,
                            fontSize = 19.sp,
                            letterSpacing = (-0.2).sp
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                }

                // Dual Opposing Slow-Moving Uniform Marquee Rows (Top: Left -> Right, Bottom: Right -> Left)
                RecommendedDualMovingCarousel(
                    row1Items = viewModel.recommendationsRow1,
                    row2Items = viewModel.recommendationsRow2,
                    onItemClick = { item -> viewModel.selectRecommendation(item) }
                )

                Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                    Spacer(modifier = Modifier.height(16.dp))

                    // 4. DISCLAIMER: Graduation Cap + "Disha can make mistakes."
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.School,
                            contentDescription = null,
                            tint = Color(0xFF7B7B7B),
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = "Disha can make mistakes.",
                            style = MaterialTheme.typography.bodySmall,
                            color = TextMuted,
                            fontSize = 12.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // 5. STACKED MINT-GREEN QUIZ PROMPT CARD
                    if (showQuizBanner) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 2.dp, vertical = 4.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 14.dp)
                                    .height(20.dp)
                                    .align(Alignment.BottomCenter)
                                    .offset(y = 6.dp)
                                    .clip(RoundedCornerShape(14.dp))
                                    .background(UdyapStackPink)
                            )
                            Surface(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(16.dp))
                                    .clickable {
                                        viewModel.askRunwayAi("Start a quick 3-question interactive science & aptitude quiz for me!")
                                        viewModel.setAiChatOpen(true)
                                    },
                                shape = RoundedCornerShape(16.dp),
                                color = UdyapMintGreen
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.dp, vertical = 13.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = "Hiee buddy! How's your day going ??\nLets try a QUIZ",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = Color(0xFF283629),
                                        fontSize = 13.5.sp,
                                        lineHeight = 19.sp,
                                        modifier = Modifier.weight(1f)
                                    )
                                    Icon(
                                        imageVector = Icons.Filled.Close,
                                        contentDescription = "Dismiss quiz card",
                                        tint = Color(0xFF3E4C3F),
                                        modifier = Modifier
                                            .size(22.dp)
                                            .clickable { showQuizBanner = false }
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))
                }
            }

            // =================================================================
            // BOTTOM SECTION (Pinned cleanly to Bottom with Middle Gap above)
            // =================================================================
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(PureWhite)
                    .padding(horizontal = 20.dp, vertical = 8.dp)
            ) {
                // 6. FLOATING DARK SPEECH BUBBLE
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp)
                        .testTag("disha_speech_bubble")
                        .clickable { viewModel.setAiChatOpen(true) }
                ) {
                    Column {
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(16.dp)),
                            shape = RoundedCornerShape(16.dp),
                            color = UdyapDishaBubble
                        ) {
                            Text(
                                text = "Any question? Let's explore it together.",
                                color = PureWhite,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Normal,
                                fontSize = 14.sp,
                                modifier = Modifier.padding(horizontal = 18.dp, vertical = 14.dp),
                                textAlign = TextAlign.Center
                            )
                        }
                        Canvas(
                            modifier = Modifier
                                .padding(start = 26.dp)
                                .size(18.dp, 10.dp)
                        ) {
                            val pointerPath = Path().apply {
                                moveTo(0f, 0f)
                                lineTo(size.width, 0f)
                                lineTo(0f, size.height)
                                close()
                            }
                            drawPath(pointerPath, color = UdyapDishaBubble)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                // 7. DISHA ASSISTANT BAR
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier
                            .weight(1f)
                            .clickable { viewModel.setAiChatOpen(true) }
                    ) {
                        RunwayHeadIcon(modifier = Modifier.size(54.dp))
                        Column {
                            Text(
                                text = "Disha",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = TextDark,
                                fontSize = 18.sp
                            )
                            Text(
                                text = "Analyzing your session..",
                                style = MaterialTheme.typography.bodySmall,
                                color = TextMuted,
                                fontSize = 12.5.sp
                            )
                        }
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .testTag("disha_ask_button")
                            .clickable { viewModel.setAiChatOpen(true) }
                    ) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(RoundedCornerShape(14.dp))
                                .background(Color(0xFF0F0F12)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.Send,
                                contentDescription = "Ask Disha",
                                tint = PureWhite,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(3.dp))
                        Text(
                            text = "Ask",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.SemiBold,
                            color = TextDark,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }

        // =================================================================
        // FULL-SCREEN SLIDE-UP OVERLAY FOR AI CHATBOT (image.png)
        // =================================================================
        AnimatedVisibility(
            visible = isFullScreenChatOpen,
            enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
            exit = slideOutVertically(targetOffsetY = { it }) + fadeOut()
        ) {
            FullScreenAiChatPage(
                viewModel = viewModel,
                onClose = { viewModel.setAiChatOpen(false) }
            )
        }

        // =================================================================
        // PROFESSIONAL OPPORTUNITY DIRECTORY MODAL (Matches 6 Cards from Image 1)
        // =================================================================
        activeNetworkType?.let { categoryName ->
            OpportunityDirectoryModal(
                initialCategory = categoryName,
                onDismiss = { activeNetworkType = null },
                onAskRunway = { prompt ->
                    activeNetworkType = null
                    viewModel.askRunwayAi(prompt)
                    viewModel.setAiChatOpen(true)
                }
            )
        }

        // =================================================================
        // EDITORIAL ARTICLE READER MODAL (Image 2)
        // =================================================================
        activeArticleItem?.let { article ->
            ArticleReaderModal(
                article = article,
                onDismiss = { activeArticleItem = null },
                onAskRunway = { prompt ->
                    activeArticleItem = null
                    viewModel.askRunwayAi(prompt)
                    viewModel.setAiChatOpen(true)
                }
            )
        }
    }
}

// =========================================================================
// FULL-SCREEN DISHA AI CHATBOT (Ethereal Pastel Aurora Mesh Design — UDYAP Matched)
// =========================================================================
@Composable
fun FullScreenAiChatPage(
    viewModel: EduLockerViewModel,
    onClose: () -> Unit
) {
    BackHandler { onClose() }

    val messages by viewModel.aiMessages.collectAsState()
    val isThinking by viewModel.isAiThinking.collectAsState()
    val profile by viewModel.studentProfile.collectAsState()
    var inputText by remember { mutableStateOf("") }
    val listState = rememberLazyListState()
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current

    val studentFirstName = remember(profile) {
        (profile?.fullName ?: "Laxmikant").substringBefore(" ")
    }
    val hasUserMessages = remember(messages) { messages.any { it.isUser } }
    val latestUserRequest = remember(messages) {
        messages.lastOrNull { it.isUser }?.text ?: ""
    }

    var selectedModel by remember { mutableStateOf("Disha 3.0") }
    var showModelDropdown by remember { mutableStateOf(false) }
    var currentlySpeakingTimestamp by remember { mutableStateOf<String?>(null) }

    DisposableEffect(Unit) {
        onDispose { TextToSpeechHelper.stop() }
    }

    val speechRecognizerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == android.app.Activity.RESULT_OK) {
            val spokenList = result.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            val spokenText = spokenList?.firstOrNull()
            if (!spokenText.isNullOrBlank()) {
                inputText = spokenText
            }
        }
    }

    val models = listOf("Disha 3.0", "UDYAP Pro 4.0", "Career Advisor")

    val quickMoodTopics = remember {
        listOf(
            Triple("Exam Prep", Color(0xFFFFF4CC), "Create a smart 7-day study plan for CHSE & JEE prep"),
            Triple("Scholarships", Color(0xFFD9F7D9), "Which Odisha scholarships am I eligible to apply for right now?"),
            Triple("Olympiad", Color(0xFFFCE0EB), "Tell me about the State Icon Olympiad Trophy dates & syllabus"),
            Triple("Coding Track", Color(0xFFDCEEFF), "How do I complete the UDYAP Developer Program and earn my certificate?"),
            Triple("Quick Quiz", Color(0xFFEDE4FF), "Give me a quick 3-question interactive science & logic quiz"),
            Triple("APAAR & Docs", Color(0xFFFFE6D6), "Check the verification status of my APAAR ID and marksheets")
        )
    }

    LaunchedEffect(messages.size, isThinking) {
        if (hasUserMessages && messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // 1. ETHEREAL PASTEL AURORA MESH BACKGROUND (UDYAP Rose-Blush + Peach + Lavender + Mint)
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height

            // Soft luminous warm base
            drawRect(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFF5D8E2),
                        Color(0xFFF9E5DB),
                        Color(0xFFFDF8F6),
                        PureWhite
                    )
                )
            )
            // Soft Periwinkle-Lavender Glow (Top-Left)
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(Color(0xFFC5C8F5).copy(alpha = 0.72f), Color.Transparent),
                    center = Offset(w * 0.14f, h * 0.08f),
                    radius = w * 0.72f
                ),
                radius = w * 0.72f,
                center = Offset(w * 0.14f, h * 0.08f)
            )
            // Soft Sage-Mint Glow (Top-Right)
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(Color(0xFFC6EBD1).copy(alpha = 0.75f), Color.Transparent),
                    center = Offset(w * 0.90f, h * 0.14f),
                    radius = w * 0.68f
                ),
                radius = w * 0.68f,
                center = Offset(w * 0.90f, h * 0.14f)
            )
            // UDYAP Signature Rose-Blush Glow (Mid-Left)
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(UdyapCrimson.copy(alpha = 0.28f), Color(0xFFF9B8C9).copy(alpha = 0.42f), Color.Transparent),
                    center = Offset(w * 0.16f, h * 0.36f),
                    radius = w * 0.75f
                ),
                radius = w * 0.75f,
                center = Offset(w * 0.16f, h * 0.36f)
            )
            // Warm Sunlit Peach Glow (Center-Right)
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(Color(0xFFFCE0BC).copy(alpha = 0.72f), Color.Transparent),
                    center = Offset(w * 0.82f, h * 0.42f),
                    radius = w * 0.70f
                ),
                radius = w * 0.70f,
                center = Offset(w * 0.82f, h * 0.42f)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .imePadding()
        ) {
            // 2. MINIMAL FROSTED TOP BAR
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(PureWhite.copy(alpha = 0.72f))
                        .testTag("disha_chat_back_button")
                        .clickable { onClose() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = TextDark,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = PureWhite.copy(alpha = 0.68f)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 7.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(7.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(UdyapCrimson)
                        )
                        Text(
                            text = "Disha AI",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.SemiBold,
                            color = TextDark,
                            fontSize = 14.sp
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(PureWhite.copy(alpha = 0.72f))
                        .testTag("disha_chat_reset_button")
                        .clickable {
                            viewModel.clearAiChat()
                            Toast.makeText(context, "New conversation started", Toast.LENGTH_SHORT).show()
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Refresh,
                        contentDescription = "New Chat",
                        tint = TextDark,
                        modifier = Modifier.size(19.dp)
                    )
                }
            }

            // 3. MAIN CONTENT AREA: WELCOME HERO vs ACTIVE CONVERSATION SHEET
            if (!hasUserMessages) {
                // --- WELCOME HERO STATE ---
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(horizontal = 22.dp)
                ) {
                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "Hi, $studentFirstName!",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF3F2D36).copy(alpha = 0.80f),
                        fontSize = 16.sp
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "What do you\nwant to explore\n✦ today?",
                        style = MaterialTheme.typography.displaySmall,
                        fontWeight = FontWeight.Bold,
                        color = PureWhite,
                        fontSize = 35.sp,
                        lineHeight = 41.sp,
                        letterSpacing = (-0.8).sp
                    )

                    Spacer(modifier = Modifier.height(22.dp))

                    // Soft Pastel Quick-Focus Pills (inspired by mood/topic picker)
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        quickMoodTopics.chunked(3).forEach { rowTopics ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                rowTopics.forEach { (label, bgColor, prompt) ->
                                    Surface(
                                        shape = RoundedCornerShape(14.dp),
                                        color = bgColor.copy(alpha = 0.90f),
                                        modifier = Modifier
                                            .weight(1f)
                                            .clip(RoundedCornerShape(14.dp))
                                            .clickable { viewModel.askRunwayAi(prompt) }
                                    ) {
                                        Text(
                                            text = label,
                                            color = Color(0xFF2B262C),
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Medium,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis,
                                            textAlign = TextAlign.Center,
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 10.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    // Floating Staggered Frosted Prompt Cards above Composer
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Surface(
                            shape = RoundedCornerShape(18.dp),
                            color = PureWhite.copy(alpha = 0.88f),
                            shadowElevation = 4.dp,
                            modifier = Modifier
                                .fillMaxWidth(0.72f)
                                .align(Alignment.Start)
                                .rotate(-1.5f)
                                .clip(RoundedCornerShape(18.dp))
                                .clickable {
                                    viewModel.askRunwayAi("How to prepare for CHSE boards & JEE together?")
                                }
                        ) {
                            Text(
                                text = "How to prepare for\nCHSE boards & JEE?",
                                color = Color(0xFF1F1D24),
                                fontSize = 13.5.sp,
                                fontWeight = FontWeight.Medium,
                                lineHeight = 19.sp,
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 13.dp)
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(18.dp),
                            color = PureWhite.copy(alpha = 0.88f),
                            shadowElevation = 4.dp,
                            modifier = Modifier
                                .fillMaxWidth(0.70f)
                                .align(Alignment.End)
                                .rotate(1.4f)
                                .clip(RoundedCornerShape(18.dp))
                                .clickable {
                                    viewModel.askRunwayAi("Best scholarships I can apply for right now")
                                }
                        ) {
                            Text(
                                text = "Best scholarships I can\napply for right now",
                                color = Color(0xFF1F1D24),
                                fontSize = 13.5.sp,
                                fontWeight = FontWeight.Medium,
                                lineHeight = 19.sp,
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 13.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(18.dp))
                }
            } else {
                // --- ACTIVE CONVERSATION STATE (Request Header + Frosted Rounded Chat Sheet) ---
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 22.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "Your request",
                        color = Color(0xFF3F2D36).copy(alpha = 0.72f),
                        fontSize = 12.5.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = latestUserRequest,
                        color = PureWhite,
                        fontSize = 23.sp,
                        fontWeight = FontWeight.Bold,
                        lineHeight = 28.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Frosted Glass Chat Stream Sheet
                Surface(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp, bottomStart = 22.dp, bottomEnd = 22.dp),
                    color = PureWhite.copy(alpha = 0.86f)
                ) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        // Subtle Drag Pill
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 10.dp, bottom = 6.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .width(38.dp)
                                    .height(4.dp)
                                    .clip(RoundedCornerShape(2.dp))
                                    .background(Color(0xFFD4D4D8))
                            )
                        }

                        LazyColumn(
                            state = listState,
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth(),
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(messages) { message ->
                                if (message.isUser) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.End
                                    ) {
                                        Surface(
                                            shape = RoundedCornerShape(
                                                topStart = 18.dp,
                                                topEnd = 18.dp,
                                                bottomStart = 18.dp,
                                                bottomEnd = 6.dp
                                            ),
                                            color = PureWhite,
                                            shadowElevation = 2.dp,
                                            border = BorderStroke(1.dp, Color(0xFFF3E2E8)),
                                            modifier = Modifier.fillMaxWidth(0.84f)
                                        ) {
                                            Text(
                                                text = message.text,
                                                color = Color(0xFF18181B),
                                                style = MaterialTheme.typography.bodyMedium,
                                                fontSize = 14.sp,
                                                lineHeight = 20.sp,
                                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 12.dp)
                                            )
                                        }
                                    }
                                } else {
                                    Column(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .size(22.dp)
                                                    .clip(CircleShape)
                                                    .background(Color(0xFF18181B)),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Filled.AutoAwesome,
                                                    contentDescription = null,
                                                    tint = PureWhite,
                                                    modifier = Modifier.size(12.dp)
                                                )
                                            }
                                            Text(
                                                text = "Disha AI",
                                                color = Color(0xFF71717A),
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight.Medium
                                            )
                                        }

                                        Text(
                                            text = message.text,
                                            color = Color(0xFF18181B),
                                            style = MaterialTheme.typography.bodyMedium,
                                            fontSize = 14.5.sp,
                                            lineHeight = 21.sp
                                        )

                                        // Minimal Action Strip
                                        Row(
                                            horizontalArrangement = Arrangement.spacedBy(14.dp),
                                            verticalAlignment = Alignment.CenterVertically,
                                            modifier = Modifier.padding(top = 2.dp)
                                        ) {
                                            val isSpeakingThis = currentlySpeakingTimestamp == message.timestamp
                                            Icon(
                                                imageVector = if (isSpeakingThis) Icons.Filled.Stop else Icons.Filled.GraphicEq,
                                                contentDescription = if (isSpeakingThis) "Stop Audio" else "Read Aloud",
                                                tint = if (isSpeakingThis) UdyapCrimson else Color(0xFF71717A),
                                                modifier = Modifier
                                                    .size(18.dp)
                                                    .clickable {
                                                        if (isSpeakingThis) {
                                                            TextToSpeechHelper.stop()
                                                            currentlySpeakingTimestamp = null
                                                        } else {
                                                            currentlySpeakingTimestamp = message.timestamp
                                                            TextToSpeechHelper.speak(
                                                                context = context,
                                                                text = message.text,
                                                                onStart = { currentlySpeakingTimestamp = message.timestamp },
                                                                onDone = { currentlySpeakingTimestamp = null }
                                                            )
                                                        }
                                                    }
                                            )
                                            Icon(
                                                imageVector = Icons.Filled.ContentCopy,
                                                contentDescription = "Copy",
                                                tint = Color(0xFF71717A),
                                                modifier = Modifier
                                                    .size(16.dp)
                                                    .clickable {
                                                        clipboardManager.setText(AnnotatedString(message.text))
                                                        Toast.makeText(context, "Copied to clipboard", Toast.LENGTH_SHORT).show()
                                                    }
                                            )
                                            Icon(
                                                imageVector = Icons.Filled.ThumbUp,
                                                contentDescription = "Helpful",
                                                tint = Color(0xFF71717A),
                                                modifier = Modifier
                                                    .size(16.dp)
                                                    .clickable {
                                                        Toast.makeText(context, "Thanks for your feedback!", Toast.LENGTH_SHORT).show()
                                                    }
                                            )
                                        }
                                    }
                                }
                            }

                            // Animated 3-dot Thinking Indicator
                            if (isThinking) {
                                item {
                                    DishaTypingDotsIndicator()
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))
            }

            // 4. FLOATING PASTEL-TRIMMED COMPOSER DOCK (Matching ai_ui_d69c81cb.jpg)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 10.dp)
                    .clip(RoundedCornerShape(26.dp))
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(
                                Color(0xFFFCE4EC),
                                Color(0xFFF9EFE8),
                                Color(0xFFE3F5E8)
                            )
                        )
                    )
                    .padding(4.dp)
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    // Pastel Top Status Strip
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 14.dp, vertical = 6.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "UDYAP Smart",
                                color = Color(0xFF27272A),
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 11.5.sp
                            )
                            Text(
                                text = " • Free AI Guide",
                                color = Color(0xFF71717A),
                                fontSize = 11.5.sp
                            )
                        }

                        Box {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(10.dp))
                                    .clickable { showModelDropdown = true }
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = selectedModel,
                                    color = Color(0xFF27272A),
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 11.5.sp
                                )
                                Icon(
                                    imageVector = Icons.Filled.KeyboardArrowDown,
                                    contentDescription = "Switch Model",
                                    tint = Color(0xFF52525B),
                                    modifier = Modifier.size(15.dp)
                                )
                            }

                            DropdownMenu(
                                expanded = showModelDropdown,
                                onDismissRequest = { showModelDropdown = false }
                            ) {
                                models.forEach { model ->
                                    DropdownMenuItem(
                                        text = {
                                            Text(
                                                model,
                                                fontWeight = if (model == selectedModel) FontWeight.Bold else FontWeight.Normal
                                            )
                                        },
                                        onClick = {
                                            selectedModel = model
                                            showModelDropdown = false
                                            Toast.makeText(context, "Switched to $model", Toast.LENGTH_SHORT).show()
                                        }
                                    )
                                }
                            }
                        }
                    }

                    // Inner Pure White Composer Surface
                    Surface(
                        shape = RoundedCornerShape(22.dp),
                        color = PureWhite,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 12.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 12.dp)
                            ) {
                                if (inputText.isEmpty()) {
                                    Text(
                                        text = "Ask Disha anything about study, exams or scholarships...",
                                        color = Color(0xFFA1A1AA),
                                        fontSize = 14.sp
                                    )
                                }
                                BasicTextField(
                                    value = inputText,
                                    onValueChange = { inputText = it },
                                    textStyle = TextStyle(
                                        color = Color(0xFF18181B),
                                        fontSize = 14.5.sp,
                                        lineHeight = 20.sp
                                    ),
                                    cursorBrush = SolidColor(UdyapCrimson),
                                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                                    keyboardActions = KeyboardActions(
                                        onSend = {
                                            if (inputText.isNotBlank()) {
                                                viewModel.askRunwayAi(inputText)
                                                inputText = ""
                                            }
                                        }
                                    ),
                                    maxLines = 3,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .testTag("disha_chat_input")
                                )
                            }

                            // Bottom Minimal Controls: [Attach] [Sparkle] [Mic] ...... [Circular Send ↑]
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.AttachFile,
                                        contentDescription = "Attach Document",
                                        tint = Color(0xFF52525B),
                                        modifier = Modifier
                                            .size(20.dp)
                                            .clickable {
                                                Toast.makeText(context, "Attach marksheet or notes PDF", Toast.LENGTH_SHORT).show()
                                            }
                                    )
                                    Icon(
                                        imageVector = Icons.Filled.AutoAwesome,
                                        contentDescription = "Suggest Prompt",
                                        tint = Color(0xFF52525B),
                                        modifier = Modifier
                                            .size(19.dp)
                                            .clickable {
                                                inputText = "Create a 7-day board exam & Olympiad study schedule for me"
                                            }
                                    )
                                    Icon(
                                        imageVector = Icons.Filled.Mic,
                                        contentDescription = "Voice Input",
                                        tint = Color(0xFF52525B),
                                        modifier = Modifier
                                            .size(20.dp)
                                            .clickable {
                                                val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                                                    putExtra(
                                                        RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                                                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
                                                    )
                                                    putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to Disha AI...")
                                                }
                                                try {
                                                    speechRecognizerLauncher.launch(intent)
                                                } catch (e: Exception) {
                                                    Toast.makeText(context, "Voice input not supported on this device", Toast.LENGTH_SHORT).show()
                                                }
                                            }
                                    )
                                }

                                Box(
                                    modifier = Modifier
                                        .size(38.dp)
                                        .clip(CircleShape)
                                        .background(if (inputText.isNotBlank()) UdyapCrimson else Color(0xFF27272A))
                                        .testTag("disha_send_button")
                                        .clickable {
                                            if (inputText.isNotBlank()) {
                                                viewModel.askRunwayAi(inputText)
                                                inputText = ""
                                            }
                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.ArrowUpward,
                                        contentDescription = "Send",
                                        tint = PureWhite,
                                        modifier = Modifier.size(19.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun DishaTypingDotsIndicator() {
    val infiniteTransition = rememberInfiniteTransition(label = "dishaTypingDots")
    val dot1Alpha by infiniteTransition.animateFloat(
        initialValue = 0.25f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(550, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "dot1"
    )
    val dot2Alpha by infiniteTransition.animateFloat(
        initialValue = 0.25f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(550, delayMillis = 160, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "dot2"
    )
    val dot3Alpha by infiniteTransition.animateFloat(
        initialValue = 0.25f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(550, delayMillis = 320, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "dot3"
    )
    Row(
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 6.dp)
    ) {
        listOf(dot1Alpha, dot2Alpha, dot3Alpha).forEach { alphaVal ->
            Box(
                modifier = Modifier
                    .size(7.dp)
                    .alpha(alphaVal)
                    .clip(CircleShape)
                    .background(Color(0xFF27272A))
            )
        }
    }
}

// =========================================================================
// EDITORIAL CARDS (Exact Replica of image 2)
// =========================================================================

/**
 * Top Card from image 2: Deep Blue Gradient with 3D Invoices & "Beyond the Invoice"
 */
@Composable
fun EditorialBlueInvoiceCard(
    title: String,
    subtitle: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(138.dp)
            .shadow(6.dp, RoundedCornerShape(26.dp))
            .clip(RoundedCornerShape(26.dp))
            .clickable { onClick() },
        shape = RoundedCornerShape(26.dp),
        color = Color(0xFF1E3A8A)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFF1E3A8A),
                            Color(0xFF2563EB),
                            Color(0xFF3B82F6)
                        )
                    )
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Left Graphic: 3D Slanted White Invoices
                Box(
                    modifier = Modifier
                        .weight(0.95f)
                        .fillMaxHeight(),
                    contentAlignment = Alignment.CenterStart
                ) {
                    SlantedInvoicesIllustration(
                        modifier = Modifier
                            .size(width = 135.dp, height = 110.dp)
                    )
                }

                // Right Content: Title & "Read article ↗" Button
                Column(
                    modifier = Modifier
                        .weight(1.05f)
                        .fillMaxHeight()
                        .padding(start = 8.dp),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = PureWhite,
                        fontSize = 20.sp,
                        lineHeight = 24.sp,
                        textAlign = TextAlign.End
                    )

                    // "Read article ↗" button
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(Color(0x33FFFFFF))
                            .padding(start = 12.dp, end = 4.dp, top = 4.dp, bottom = 4.dp)
                    ) {
                        Text(
                            text = "Read article",
                            color = PureWhite,
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 12.5.sp
                        )

                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .clip(CircleShape)
                                .background(PureWhite),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.NorthEast,
                                contentDescription = "Read",
                                tint = Color(0xFF0F172A),
                                modifier = Modifier.size(15.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * Bottom Card from image 2: Crisp Ivory/White with 3D Iridescent Clock
 */
@Composable
fun EditorialWhiteClockCard(
    title: String,
    subtitle: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(138.dp)
            .shadow(6.dp, RoundedCornerShape(26.dp))
            .clip(RoundedCornerShape(26.dp))
            .border(1.dp, Color(0xFFE2E8F0), RoundedCornerShape(26.dp))
            .clickable { onClick() },
        shape = RoundedCornerShape(26.dp),
        color = PureWhite
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 18.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Left Content: Title & "↗ Read article"
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF0F172A),
                    fontSize = 19.sp,
                    lineHeight = 23.sp
                )

                // "↗ Read article" Button (with gradient circle)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .clip(CircleShape)
                            .background(
                                Brush.linearGradient(
                                    listOf(Color(0xFFF472B6), Color(0xFFA855F7), Color(0xFF38BDF8))
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.NorthEast,
                            contentDescription = "Read",
                            tint = PureWhite,
                            modifier = Modifier.size(14.dp)
                        )
                    }

                    Text(
                        text = "Read article",
                        color = Color(0xFF475569),
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.5.sp
                    )
                }
            }

            // Right Graphic: 3D Iridescent Clock
            Box(
                modifier = Modifier
                    .size(110.dp),
                contentAlignment = Alignment.Center
            ) {
                IridescentClockIllustration(
                    modifier = Modifier.size(105.dp)
                )
            }
        }
    }
}

// =========================================================================
// OPPORTUNITY CARD (Exact Replica of image 1)
// =========================================================================
@Composable
fun OpportunityItemCard(
    item: OpportunityCardItem,
    isSaved: Boolean,
    isApplied: Boolean,
    onToggleSave: () -> Unit,
    onApply: () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(22.dp))
            .clip(RoundedCornerShape(22.dp))
            .border(1.dp, Color(0xFFE2E8F0), RoundedCornerShape(22.dp))
            .clickable { onClick() },
        shape = RoundedCornerShape(22.dp),
        color = PureWhite
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
        ) {
            // Row 1: Company Logo Badge & Save/Saved Bookmark
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Company Vector Avatar
                CompanyLogoBadge(
                    company = item.company,
                    logoType = item.logoType,
                    modifier = Modifier.size(42.dp)
                )

                // Save Bookmark Pill
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .clickable { onToggleSave() }
                        .padding(horizontal = 6.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = if (isSaved) "Saved" else "Save",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = if (isSaved) Color(0xFF0F172A) else Color(0xFF64748B),
                        fontSize = 12.sp
                    )
                    Icon(
                        imageVector = if (isSaved) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
                        contentDescription = "Bookmark",
                        tint = if (isSaved) Color(0xFF0F172A) else Color(0xFF94A3B8),
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Company Name & Posted Date
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = item.company,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF0F172A),
                    fontSize = 14.5.sp
                )
                Text(
                    text = item.postedAgo,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF94A3B8),
                    fontSize = 11.5.sp
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Job / Opportunity Title
            Text(
                text = item.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1E293B),
                fontSize = 16.5.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Attribute Tags (e.g., [Part-Time] [Senior Level])
            Row(
                modifier = Modifier.horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item.tags.forEach { tag ->
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Color(0xFFF1F5F9)
                    ) {
                        Text(
                            text = tag,
                            style = MaterialTheme.typography.labelSmall,
                            color = Color(0xFF475569),
                            fontWeight = FontWeight.Medium,
                            fontSize = 11.5.sp,
                            modifier = Modifier.padding(horizontal = 9.dp, vertical = 5.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Bottom Row: Compensation & Location (Left) vs [Apply now] (Right)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Column {
                    Text(
                        text = item.compensation,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF0F172A),
                        fontSize = 16.sp
                    )
                    Text(
                        text = item.location,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF94A3B8),
                        fontSize = 11.5.sp
                    )
                }

                // Black "Apply now" Pill Button
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = if (isApplied) Color(0xFF059669) else Color(0xFF171717),
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { onApply() }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 9.dp)
                    ) {
                        if (isApplied) {
                            Icon(
                                imageVector = Icons.Filled.Check,
                                contentDescription = "Applied",
                                tint = PureWhite,
                                modifier = Modifier.size(14.dp)
                            )
                        }
                        Text(
                            text = if (isApplied) "Applied" else "Apply now",
                            color = PureWhite,
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.5.sp
                        )
                    }
                }
            }
        }
    }
}

// =========================================================================
// COMPANY LOGO BADGE COMPONENT
// =========================================================================
@Composable
fun CompanyLogoBadge(
    company: String,
    logoType: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .clip(CircleShape)
            .border(1.dp, Color(0xFFE2E8F0), CircleShape),
        shape = CircleShape,
        color = PureWhite
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when (logoType.lowercase()) {
                "amazon" -> {
                    // Amazon black circle with 'a' and smile
                    Canvas(modifier = Modifier.size(26.dp)) {
                        val w = size.width
                        val h = size.height
                        // Draw 'a'
                        drawCircle(color = Color.Black, radius = w * 0.45f)
                        // Orange smile curve
                        val path = Path().apply {
                            moveTo(w * 0.25f, h * 0.68f)
                            quadraticBezierTo(w * 0.5f, h * 0.88f, w * 0.75f, h * 0.68f)
                        }
                        drawPath(path, color = Color(0xFFFF9900), style = Stroke(width = 2.dp.toPx()))
                    }
                }
                "google" -> {
                    // Google 4-color 'G'
                    Canvas(modifier = Modifier.size(24.dp)) {
                        val w = size.width
                        val h = size.height
                        val stroke = 3.dp.toPx()
                        // Blue, Red, Yellow, Green arcs
                        drawArc(Color(0xFF4285F4), startAngle = 0f, sweepAngle = 90f, useCenter = false, style = Stroke(stroke))
                        drawArc(Color(0xFF34A853), startAngle = 90f, sweepAngle = 90f, useCenter = false, style = Stroke(stroke))
                        drawArc(Color(0xFFFBBC05), startAngle = 180f, sweepAngle = 90f, useCenter = false, style = Stroke(stroke))
                        drawArc(Color(0xFFEA4335), startAngle = 270f, sweepAngle = 90f, useCenter = false, style = Stroke(stroke))
                        // Crossbar
                        drawLine(Color(0xFF4285F4), Offset(w * 0.45f, h * 0.5f), Offset(w * 0.9f, h * 0.5f), strokeWidth = stroke)
                    }
                }
                "dribbble" -> {
                    // Dribbble Hot Pink Basketball
                    Canvas(modifier = Modifier.size(26.dp)) {
                        val w = size.width
                        val h = size.height
                        drawCircle(color = Color(0xFFEA4C89))
                        // Basketball curved ribs
                        val ribPath = Path().apply {
                            moveTo(w * 0.2f, h * 0.3f)
                            quadraticBezierTo(w * 0.5f, h * 0.5f, w * 0.8f, h * 0.3f)
                            moveTo(w * 0.2f, h * 0.7f)
                            quadraticBezierTo(w * 0.5f, h * 0.5f, w * 0.8f, h * 0.7f)
                        }
                        drawPath(ribPath, color = Color.White.copy(alpha = 0.8f), style = Stroke(width = 1.5.dp.toPx()))
                    }
                }
                "figma" -> {
                    // Figma 5 colored pills
                    Canvas(modifier = Modifier.size(24.dp)) {
                        val w = size.width
                        val h = size.height
                        // 5 Circles / Pills
                        drawCircle(Color(0xFFF24E1E), radius = w * 0.18f, center = Offset(w * 0.35f, h * 0.25f))
                        drawCircle(Color(0xFFFF7262), radius = w * 0.18f, center = Offset(w * 0.65f, h * 0.25f))
                        drawCircle(Color(0xFFA259FF), radius = w * 0.18f, center = Offset(w * 0.35f, h * 0.5f))
                        drawCircle(Color(0xFF1ABCFE), radius = w * 0.18f, center = Offset(w * 0.65f, h * 0.5f))
                        drawCircle(Color(0xFF0ACF83), radius = w * 0.18f, center = Offset(w * 0.35f, h * 0.75f))
                    }
                }
                "airbnb" -> {
                    // Airbnb Coral icon
                    Canvas(modifier = Modifier.size(26.dp)) {
                        val w = size.width
                        val h = size.height
                        drawCircle(Color(0xFFFF5A5F))
                        val loopPath = Path().apply {
                            moveTo(w * 0.5f, h * 0.2f)
                            cubicTo(w * 0.25f, h * 0.5f, w * 0.25f, h * 0.8f, w * 0.5f, h * 0.8f)
                            cubicTo(w * 0.75f, h * 0.8f, w * 0.75f, h * 0.5f, w * 0.5f, h * 0.2f)
                        }
                        drawPath(loopPath, color = Color.White, style = Stroke(width = 2.dp.toPx()))
                    }
                }
                "apple" -> {
                    // Apple black icon
                    Canvas(modifier = Modifier.size(24.dp)) {
                        val w = size.width
                        val h = size.height
                        // Apple silhouette
                        drawCircle(Color.Black, radius = w * 0.35f, center = Offset(w * 0.5f, h * 0.55f))
                        // Stem leaf
                        drawOval(Color.Black, topLeft = Offset(w * 0.45f, h * 0.12f), size = Size(w * 0.2f, h * 0.15f))
                    }
                }
                "microsoft" -> {
                    // Microsoft 4 squares
                    Canvas(modifier = Modifier.size(22.dp)) {
                        val w = size.width
                        val h = size.height
                        val s = w * 0.4f
                        val gap = w * 0.1f
                        drawRect(Color(0xFFF25022), topLeft = Offset(0f, 0f), size = Size(s, s))
                        drawRect(Color(0xFF7FBA00), topLeft = Offset(s + gap, 0f), size = Size(s, s))
                        drawRect(Color(0xFF00A4EF), topLeft = Offset(0f, s + gap), size = Size(s, s))
                        drawRect(Color(0xFFFFB900), topLeft = Offset(s + gap, s + gap), size = Size(s, s))
                    }
                }
                else -> {
                    // State / EduLocker Shield
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(SoftBlushPink),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = company.take(2).uppercase(),
                            fontWeight = FontWeight.ExtraBold,
                            color = OdishaMagenta,
                            fontSize = 13.sp
                        )
                    }
                }
            }
        }
    }
}

// =========================================================================
// 3D GRAPHIC ILLUSTRATIONS FOR IMAGE 2 EDITORIAL CARDS
// =========================================================================

/**
 * Slanted Invoices Illustration (Matches top card of image 2)
 */
@Composable
fun SlantedInvoicesIllustration(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        // Layer 1 (Back Invoice)
        val backPath = Path().apply {
            moveTo(w * 0.05f, h * 0.15f)
            lineTo(w * 0.7f, h * 0.05f)
            lineTo(w * 0.85f, h * 0.75f)
            lineTo(w * 0.2f, h * 0.85f)
            close()
        }
        drawPath(backPath, color = Color.White.copy(alpha = 0.6f))

        // Layer 2 (Middle Invoice)
        val midPath = Path().apply {
            moveTo(w * 0.12f, h * 0.22f)
            lineTo(w * 0.75f, h * 0.12f)
            lineTo(w * 0.9f, h * 0.82f)
            lineTo(w * 0.25f, h * 0.92f)
            close()
        }
        drawPath(midPath, color = Color.White.copy(alpha = 0.85f))

        // Layer 3 (Front Crisp Invoice with text lines)
        val frontPath = Path().apply {
            moveTo(w * 0.18f, h * 0.28f)
            lineTo(w * 0.82f, h * 0.18f)
            lineTo(w * 0.95f, h * 0.9f)
            lineTo(w * 0.32f, h * 0.98f)
            close()
        }
        drawPath(frontPath, color = Color.White)

        // Text lines & "INVOICE" header on front receipt
        drawLine(Color(0xFF0F172A), Offset(w * 0.30f, h * 0.38f), Offset(w * 0.65f, h * 0.33f), strokeWidth = 3.dp.toPx())
        drawLine(Color(0xFF94A3B8), Offset(w * 0.32f, h * 0.50f), Offset(w * 0.75f, h * 0.43f), strokeWidth = 2.dp.toPx())
        drawLine(Color(0xFF94A3B8), Offset(w * 0.34f, h * 0.60f), Offset(w * 0.78f, h * 0.53f), strokeWidth = 2.dp.toPx())
        drawLine(Color(0xFF94A3B8), Offset(w * 0.36f, h * 0.70f), Offset(w * 0.80f, h * 0.63f), strokeWidth = 2.dp.toPx())
        drawLine(Color(0xFF3B82F6), Offset(w * 0.38f, h * 0.82f), Offset(w * 0.70f, h * 0.77f), strokeWidth = 2.5.dp.toPx())
    }
}

/**
 * 3D Iridescent Clock Illustration (Matches bottom card of image 2)
 */
@Composable
fun IridescentClockIllustration(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val center = Offset(w * 0.55f, h * 0.5f)
        val radius = w * 0.44f

        // Outer Dark Metallic Bezel
        drawCircle(
            color = Color(0xFF1E293B),
            radius = radius,
            center = center
        )

        // Iridescent Rainbow Reflection Ring (Gradient ring)
        drawCircle(
            brush = Brush.sweepGradient(
                colors = listOf(
                    Color(0xFFFDA4AF),
                    Color(0xFFC084FC),
                    Color(0xFF93C5FD),
                    Color(0xFF6EE7B7),
                    Color(0xFFFDE047),
                    Color(0xFFFDA4AF)
                ),
                center = center
            ),
            radius = radius * 0.92f,
            center = center
        )

        // Inner White Dial Face
        drawCircle(
            color = PureWhite,
            radius = radius * 0.82f,
            center = center
        )

        // Subtle dial concentric rings
        drawCircle(
            color = Color(0xFFF1F5F9),
            radius = radius * 0.55f,
            center = center,
            style = Stroke(width = 1.dp.toPx())
        )

        // Hour Ticks around the clock
        for (i in 0 until 12) {
            val angle = Math.toRadians((i * 30).toDouble())
            val startX = (center.x + (radius * 0.65f) * Math.cos(angle)).toFloat()
            val startY = (center.y + (radius * 0.65f) * Math.sin(angle)).toFloat()
            val endX = (center.x + (radius * 0.76f) * Math.cos(angle)).toFloat()
            val endY = (center.y + (radius * 0.76f) * Math.sin(angle)).toFloat()
            drawLine(
                color = Color(0xFF334155),
                start = Offset(startX, startY),
                end = Offset(endX, endY),
                strokeWidth = 1.5.dp.toPx()
            )
        }

        // Orange / Red Hour Hand
        val hourAngle = Math.toRadians(310.0)
        val hourX = (center.x + (radius * 0.55f) * Math.cos(hourAngle)).toFloat()
        val hourY = (center.y + (radius * 0.55f) * Math.sin(hourAngle)).toFloat()
        drawLine(
            color = Color(0xFFEF4444),
            start = center,
            end = Offset(hourX, hourY),
            strokeWidth = 3.dp.toPx()
        )

        // Dark Navy Minute Hand
        val minAngle = Math.toRadians(70.0)
        val minX = (center.x + (radius * 0.68f) * Math.cos(minAngle)).toFloat()
        val minY = (center.y + (radius * 0.68f) * Math.sin(minAngle)).toFloat()
        drawLine(
            color = Color(0xFF0F172A),
            start = center,
            end = Offset(minX, minY),
            strokeWidth = 3.5.dp.toPx()
        )

        // Center Pin Cap
        drawCircle(color = Color(0xFFEF4444), radius = 3.5.dp.toPx(), center = center)
        drawCircle(color = PureWhite, radius = 1.5.dp.toPx(), center = center)
    }
}

// =========================================================================
// OPPORTUNITY DIRECTORY MODAL (Matches 6 Cards from Image 1)
// =========================================================================
@Composable
fun OpportunityDirectoryModal(
    initialCategory: String,
    onDismiss: () -> Unit,
    onAskRunway: (String) -> Unit
) {
    val context = LocalContext.current
    var searchQuery by remember { mutableStateOf("") }
    var selectedFilter by remember {
        mutableStateOf(
            if (initialCategory in listOf("Internships", "Job Eligibility", "MY Applications", "My Learning", "Recognition"))
                initialCategory
            else "All"
        )
    }

    val savedStateMap = remember {
        mutableStateListOf(
            "job_2", // Google Saved
            "job_4", // Figma Saved
            "job_6"  // Apple Saved
        )
    }

    val appliedStateMap = remember {
        mutableStateListOf<String>()
    }

    // Curated Opportunities - Exact match to Image 1 + State Tech Roles
    val allOpportunities = remember {
        listOf(
            OpportunityCardItem(
                id = "job_1",
                company = "Amazon",
                logoType = "amazon",
                postedAgo = "5 days ago",
                title = "Senior UI/UX Designer",
                tags = listOf("Part-Time", "Senior Level"),
                compensation = "$120/hr",
                location = "Mumbai, India",
                category = "Job Eligibility",
                description = "Lead conversational UI design and accessible student journey mapping for Amazon Web Services & Education tools."
            ),
            OpportunityCardItem(
                id = "job_2",
                company = "Google",
                logoType = "google",
                postedAgo = "30 days ago",
                title = "Graphic Designer",
                tags = listOf("Part-Time", "Flexible Schedule"),
                compensation = "$150-220k",
                location = "Kochi, India",
                category = "Internships",
                description = "Craft high-fidelity brand illustrations, design systems, and digital assets for Google Cloud & Developer ecosystems.",
                isInitiallySaved = true
            ),
            OpportunityCardItem(
                id = "job_3",
                company = "Dribbble",
                logoType = "dribbble",
                postedAgo = "18 days ago",
                title = "Senior Motion Designer",
                tags = listOf("Contract", "Remote"),
                compensation = "$85/hr",
                location = "Chennai, India",
                category = "Job Eligibility",
                description = "Create fluid micro-interactions, 3D animated hero elements, and onboarding motion sequences for global creator platforms."
            ),
            OpportunityCardItem(
                id = "job_4",
                company = "Figma",
                logoType = "figma",
                postedAgo = "5 days ago",
                title = "UX Designer",
                tags = listOf("Full-Time", "In office"),
                compensation = "$200-250k",
                location = "Bangalore, India",
                category = "Internships",
                description = "Design next-generation collaborative canvas features, plugin interfaces, and real-time design tokens."
            ),
            OpportunityCardItem(
                id = "job_5",
                company = "Airbnb",
                logoType = "airbnb",
                postedAgo = "5 days ago",
                title = "Junior UI/UX Designer",
                tags = listOf("Contract", "Remote"),
                compensation = "$100/hr",
                location = "Delhi, India",
                category = "Internships",
                description = "Collaborate with global design leads to refine mobile checkout flows, accessibility standards, and localized experiences."
            ),
            OpportunityCardItem(
                id = "job_6",
                company = "Apple",
                logoType = "apple",
                postedAgo = "5 days ago",
                title = "Graphic Designer",
                tags = listOf("Full-Time", "Flexible Schedule"),
                compensation = "$85-120k",
                location = "Kerala, India",
                category = "Job Eligibility",
                description = "Develop editorial graphics, interactive keynotes, and human interface guidelines for developer portals."
            ),
            OpportunityCardItem(
                id = "job_7",
                company = "Odisha Tech Hub",
                logoType = "state",
                postedAgo = "2 days ago",
                title = "State Innovation Fellow",
                tags = listOf("Full-Time", "Bhubaneswar", "Govt Stipend"),
                compensation = "₹65,000/mo",
                location = "Bhubaneswar, India",
                category = "MY Applications",
                description = "Drive state-wide digitization of academic credentials, APAAR verification, and citizen-first student welfare portals."
            ),
            OpportunityCardItem(
                id = "job_8",
                company = "Microsoft",
                logoType = "microsoft",
                postedAgo = "3 days ago",
                title = "AI Research & Cloud Intern",
                tags = listOf("Remote", "Internship", "Mentorship"),
                compensation = "₹50,000/mo",
                location = "Hyderabad, India",
                category = "My Learning",
                description = "Work alongside Azure AI scientists to train educational language models and automated quiz generation engines."
            )
        )
    }

    val filterChips = listOf("All", "Internships", "Job Eligibility", "MY Applications", "My Learning", "Saved")

    val filteredList = allOpportunities.filter { item ->
        val matchesSearch = item.title.contains(searchQuery, ignoreCase = true) ||
                item.company.contains(searchQuery, ignoreCase = true) ||
                item.location.contains(searchQuery, ignoreCase = true)

        val matchesFilter = when (selectedFilter) {
            "All" -> true
            "Saved" -> savedStateMap.contains(item.id)
            else -> item.category.equals(selectedFilter, ignoreCase = true) ||
                    (selectedFilter == "Internships" && item.tags.any { it.contains("Intern", ignoreCase = true) || it.contains("Part-Time", ignoreCase = true) })
        }
        matchesSearch && matchesFilter
    }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.92f)
                .shadow(12.dp, RoundedCornerShape(26.dp)),
            shape = RoundedCornerShape(26.dp),
            color = CanvasCream
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                // Top Header: Title & Close
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = initialCategory,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF0F172A),
                            fontSize = 20.sp
                        )
                        Text(
                            text = "Curated network opportunities & listings",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF64748B),
                            fontSize = 12.sp
                        )
                    }

                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(PureWhite)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "Close",
                            tint = Color(0xFF475569),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Search Bar
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Search roles, companies or skills..", fontSize = 13.sp, color = Color(0xFF94A3B8)) },
                    leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Search", tint = Color(0xFF94A3B8), modifier = Modifier.size(18.dp)) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = PureWhite,
                        unfocusedContainerColor = PureWhite,
                        focusedIndicatorColor = Color(0xFFE2E8F0),
                        unfocusedIndicatorColor = Color(0xFFE2E8F0)
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Filter Chips Row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    filterChips.forEach { chip ->
                        val isSelected = selectedFilter == chip
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = if (isSelected) Color(0xFF0F172A) else PureWhite,
                            border = if (!isSelected) androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0)) else null,
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .clickable { selectedFilter = chip }
                        ) {
                            Text(
                                text = chip,
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                color = if (isSelected) PureWhite else Color(0xFF475569),
                                fontSize = 12.5.sp,
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 7.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Lazy Column of Opportunity Cards (image 1)
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    if (filteredList.isEmpty()) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 40.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "No matching opportunities found.",
                                    color = Color(0xFF94A3B8),
                                    fontSize = 14.sp
                                )
                            }
                        }
                    } else {
                        items(filteredList) { item ->
                            val isSaved = savedStateMap.contains(item.id)
                            val isApplied = appliedStateMap.contains(item.id)

                            OpportunityItemCard(
                                item = item,
                                isSaved = isSaved,
                                isApplied = isApplied,
                                onToggleSave = {
                                    if (isSaved) {
                                        savedStateMap.remove(item.id)
                                        Toast.makeText(context, "Removed from saved posts", Toast.LENGTH_SHORT).show()
                                    } else {
                                        savedStateMap.add(item.id)
                                        Toast.makeText(context, "Saved to your bookmarks!", Toast.LENGTH_SHORT).show()
                                    }
                                },
                                onApply = {
                                    if (!isApplied) {
                                        appliedStateMap.add(item.id)
                                        Toast.makeText(context, "Application submitted to ${item.company}!", Toast.LENGTH_LONG).show()
                                    } else {
                                        Toast.makeText(context, "Already applied! Status: Under Review", Toast.LENGTH_SHORT).show()
                                    }
                                },
                                onClick = {
                                    onAskRunway("How can I prepare my portfolio and resume for ${item.title} at ${item.company}?")
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Bottom Ask Runway Assistant Button
                Button(
                    onClick = { onAskRunway("Recommend the best jobs and internships based on my UDYAP profile") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = OdishaMagenta)
                ) {
                    Icon(Icons.AutoMirrored.Filled.Send, contentDescription = null, tint = PureWhite, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Ask Disha AI for Personalized Matches", color = PureWhite, fontWeight = FontWeight.Bold, fontSize = 13.5.sp)
                }
            }
        }
    }
}

// =========================================================================
// ARTICLE READER MODAL (Image 2 Articles)
// =========================================================================
@Composable
fun ArticleReaderModal(
    article: EditorialArticleItem,
    onDismiss: () -> Unit,
    onAskRunway: (String) -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.88f)
                .shadow(12.dp, RoundedCornerShape(26.dp)),
            shape = RoundedCornerShape(26.dp),
            color = PureWhite
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(22.dp)
            ) {
                // Top Header Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = SoftBlushPink
                    ) {
                        Text(
                            text = article.category,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = OdishaMagenta,
                            fontSize = 11.5.sp,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                        )
                    }

                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFF1F5F9))
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "Close",
                            tint = Color(0xFF475569),
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = article.title,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF0F172A),
                    fontSize = 22.sp,
                    lineHeight = 28.sp
                )

                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "UDYAP Editorial • ${article.readTime}",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF94A3B8),
                        fontSize = 12.sp
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Hero Graphic Banner depending on article
                if (article.title.contains("Invoice", ignoreCase = true)) {
                    EditorialBlueInvoiceCard(
                        title = article.title,
                        subtitle = article.subtitle,
                        onClick = {},
                        modifier = Modifier.fillMaxWidth()
                    )
                } else {
                    EditorialWhiteClockCard(
                        title = article.title,
                        subtitle = article.subtitle,
                        onClick = {},
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(modifier = Modifier.height(18.dp))

                Text(
                    text = article.fullContent,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF334155),
                    fontSize = 14.sp,
                    lineHeight = 22.sp
                )

                Spacer(modifier = Modifier.height(18.dp))

                // Key Takeaways Section
                Text(
                    text = "Key Takeaways:",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0F172A),
                    fontSize = 15.sp
                )

                Spacer(modifier = Modifier.height(10.dp))

                article.takeaways.forEach { takeaway ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.CheckCircle,
                            contentDescription = null,
                            tint = Color(0xFF059669),
                            modifier = Modifier
                                .size(18.dp)
                                .padding(top = 2.dp)
                        )
                        Text(
                            text = takeaway,
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF475569),
                            fontSize = 13.sp,
                            lineHeight = 18.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { onAskRunway("Explain key takeaways of '${article.title}' and how I can apply them") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = OdishaMagenta)
                ) {
                    Icon(Icons.AutoMirrored.Filled.Send, contentDescription = null, tint = PureWhite, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Deep Dive with Disha AI", color = PureWhite, fontWeight = FontWeight.Bold, fontSize = 13.5.sp)
                }
            }
        }
    }
}

// -----------------------------------------------------------
// 6 Network Pink Pill Buttons (Exact replica of 34.png)
// -----------------------------------------------------------
@Composable
private fun NetworkPillButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier
            .height(44.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        color = UdyapPeach
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Normal,
                color = Color(0xFF3A302A),
                fontSize = 13.5.sp
            )
        }
    }
}

@Composable
private fun DishaPastelCard(
    title: String,
    subtitle: String,
    backgroundColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier
            .height(94.dp)
            .clip(RoundedCornerShape(14.dp))
            .clickable { onClick() },
        shape = RoundedCornerShape(14.dp),
        color = backgroundColor
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 13.dp, vertical = 11.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Normal,
                color = Color(0xFF2B2B2B),
                fontSize = 15.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFF525252),
                fontSize = 11.5.sp,
                lineHeight = 15.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

// -----------------------------------------------------------
// Recommendation Card Component (34.png)
// -----------------------------------------------------------
@Composable
private fun RecommendCard(
    title: String,
    subtitle: String,
    backgroundColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier
            .height(108.dp)
            .clip(RoundedCornerShape(14.dp))
            .clickable { onClick() },
        shape = RoundedCornerShape(14.dp),
        color = backgroundColor
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1E293B),
                fontSize = 14.sp
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFF475569),
                fontSize = 11.sp,
                lineHeight = 15.sp,
                maxLines = 3
            )
        }
    }
}

// -----------------------------------------------------------
// Stylized Runway Outline Head with Star Spark (34.png)
// -----------------------------------------------------------
@Composable
fun RunwayHeadIcon(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val color = Color(0xFF1E293B)
        val strokeWidth = 2.4.dp.toPx()

        // Profile head outline
        val headPath = Path().apply {
            moveTo(size.width * 0.35f, size.height * 0.95f)
            lineTo(size.width * 0.35f, size.height * 0.85f)
            cubicTo(
                size.width * 0.25f, size.height * 0.78f,
                size.width * 0.18f, size.height * 0.65f,
                size.width * 0.18f, size.height * 0.50f
            )
            cubicTo(
                size.width * 0.12f, size.height * 0.35f,
                size.width * 0.28f, size.height * 0.15f,
                size.width * 0.55f, size.height * 0.18f
            )
            cubicTo(
                size.width * 0.85f, size.height * 0.22f,
                size.width * 0.85f, size.height * 0.65f,
                size.width * 0.7f, size.height * 0.85f
            )
            lineTo(size.width * 0.7f, size.height * 0.95f)
        }
        drawPath(headPath, color = color, style = Stroke(width = strokeWidth))

        // Sparkle / Star near top-right forehead
        val sparkleCenter = Offset(size.width * 0.68f, size.height * 0.32f)
        val sparklePath = Path().apply {
            moveTo(sparkleCenter.x, sparkleCenter.y - 12f)
            lineTo(sparkleCenter.x + 3f, sparkleCenter.y - 3f)
            lineTo(sparkleCenter.x + 12f, sparkleCenter.y)
            lineTo(sparkleCenter.x + 3f, sparkleCenter.y + 3f)
            lineTo(sparkleCenter.x, sparkleCenter.y + 12f)
            lineTo(sparkleCenter.x - 3f, sparkleCenter.y + 3f)
            lineTo(sparkleCenter.x - 12f, sparkleCenter.y)
            lineTo(sparkleCenter.x - 3f, sparkleCenter.y - 3f)
            close()
        }
        drawPath(sparklePath, color = color, style = Stroke(width = strokeWidth))
    }
}

// -----------------------------------------------------------
// Stylized Geometric Hero Logo for Chat Screen (image.png)
// -----------------------------------------------------------
@Composable
private fun AiGeometricHeroLogo(size: androidx.compose.ui.unit.Dp) {
    Canvas(modifier = Modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height

        // Bold black stylized ribbons / polygon symbol (image.png)
        val path1 = Path().apply {
            moveTo(w * 0.30f, h * 0.15f)
            lineTo(w * 0.55f, h * 0.40f)
            lineTo(w * 0.45f, h * 0.52f)
            lineTo(w * 0.20f, h * 0.27f)
            close()
        }
        drawPath(path1, color = Color(0xFF0F172A))

        val path2 = Path().apply {
            moveTo(w * 0.50f, h * 0.45f)
            lineTo(w * 0.75f, h * 0.70f)
            lineTo(w * 0.65f, h * 0.82f)
            lineTo(w * 0.40f, h * 0.57f)
            close()
        }
        drawPath(path2, color = Color(0xFF0F172A))
    }
}

// -----------------------------------------------------------
// Smooth Dual Opposing Slow-Moving Carousel for Recommended For You
// Row 1 glides slowly LEFT -> RIGHT; Row 2 glides slowly RIGHT -> LEFT.
// All cards share identical uniform dimensions with clean ellipsis truncation.
// -----------------------------------------------------------
@Composable
fun RecommendedDualMovingCarousel(
    row1Items: List<RecommendationItem>,
    row2Items: List<RecommendationItem>,
    onItemClick: (RecommendationItem) -> Unit
) {
    val density = LocalDensity.current
    // Smooth gentle speed (~24dp per second)
    val speedPxPerSec = remember(density) { with(density) { 24.dp.toPx() } }

    // ROW 1: Glides slowly LEFT -> RIGHT (negative scrollBy moves items rightwards)
    val row1ScrollState = rememberLazyListState(initialFirstVisibleItemIndex = 500)
    val isRow1Dragged by row1ScrollState.interactionSource.collectIsDraggedAsState()

    LaunchedEffect(isRow1Dragged, row1Items) {
        if (!isRow1Dragged && row1Items.isNotEmpty()) {
            var lastNanos = 0L
            while (true) {
                withFrameNanos { frameNanos ->
                    if (lastNanos != 0L) {
                        val dtSec = ((frameNanos - lastNanos) / 1_000_000_000f).coerceAtMost(0.05f)
                        if (row1ScrollState.canScrollBackward) {
                            row1ScrollState.dispatchRawDelta(-speedPxPerSec * dtSec)
                        }
                    }
                    lastNanos = frameNanos
                }
            }
        }
    }

    // ROW 2: Glides slowly RIGHT -> LEFT (positive scrollBy moves items leftwards)
    val row2ScrollState = rememberLazyListState(initialFirstVisibleItemIndex = 500)
    val isRow2Dragged by row2ScrollState.interactionSource.collectIsDraggedAsState()

    LaunchedEffect(isRow2Dragged, row2Items) {
        if (!isRow2Dragged && row2Items.isNotEmpty()) {
            var lastNanos = 0L
            while (true) {
                withFrameNanos { frameNanos ->
                    if (lastNanos != 0L) {
                        val dtSec = ((frameNanos - lastNanos) / 1_000_000_000f).coerceAtMost(0.05f)
                        if (row2ScrollState.canScrollForward) {
                            row2ScrollState.dispatchRawDelta(speedPxPerSec * dtSec)
                        }
                    }
                    lastNanos = frameNanos
                }
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // Top Row: Moving slowly Left -> Right
        LazyRow(
            state = row1ScrollState,
            horizontalArrangement = Arrangement.spacedBy(11.dp),
            contentPadding = PaddingValues(horizontal = 20.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(1200) { index ->
                if (row1Items.isNotEmpty()) {
                    val item = row1Items[index % row1Items.size]
                    RecommendationCardSimple(item = item, onItemClick = onItemClick)
                }
            }
        }

        // Bottom Row: Moving slowly Right -> Left
        LazyRow(
            state = row2ScrollState,
            horizontalArrangement = Arrangement.spacedBy(11.dp),
            contentPadding = PaddingValues(horizontal = 20.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(1200) { index ->
                if (row2Items.isNotEmpty()) {
                    val item = row2Items[index % row2Items.size]
                    RecommendationCardSimple(item = item, onItemClick = onItemClick)
                }
            }
        }
    }
}

@Composable
private fun RecommendationCardSimple(
    item: RecommendationItem,
    onItemClick: (RecommendationItem) -> Unit
) {
    Surface(
        modifier = Modifier
            .width(164.dp)
            .height(94.dp)
            .clip(RoundedCornerShape(14.dp))
            .clickable { onItemClick(item) },
        shape = RoundedCornerShape(14.dp),
        color = Color(item.backgroundColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 13.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = item.title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF1E293B),
                fontSize = 14.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = item.subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFF475569),
                fontSize = 11.5.sp,
                lineHeight = 15.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

