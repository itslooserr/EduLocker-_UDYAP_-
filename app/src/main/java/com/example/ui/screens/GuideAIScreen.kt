package com.example.ui.screens

import android.content.Intent
import android.speech.RecognizerIntent
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.AttachFile
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
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.Videocam
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.ui.theme.CanvasCream
import com.example.ui.theme.DarkBorder
import com.example.ui.theme.OdishaMagenta
import com.example.ui.theme.PureWhite
import com.example.ui.theme.SoftBlushPink
import com.example.ui.theme.TextDark
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

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(CanvasCream)
    ) {
        // =================================================================
        // MAIN GUIDE SCREEN VIEW (34.png + Image 2 Cards)
        // =================================================================
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // 1. TOP MAGENTA ARCH DOME (Exact Symmetrical Curve from 34.png)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(96.dp)
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val w = size.width
                    val h = size.height

                    val domePath = Path().apply {
                        moveTo(-w * 0.35f, 0f)
                        quadraticBezierTo(
                            w * 0.5f, h * 2.2f,
                            w * 1.35f, 0f
                        )
                        close()
                    }
                    drawPath(domePath, color = Color(0xFFD81B60))
                }
            }

            // 2. MAIN BODY CONTENT (34.png + Professional Cards)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 22.dp)
            ) {
                Spacer(modifier = Modifier.height(6.dp))

                // Header: Ai Smart Think / Your Networks
                Text(
                    text = "Ai Smart Think",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF334155),
                    fontSize = 21.sp
                )
                Text(
                    text = "Your Networks & Opportunities",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF64748B),
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(18.dp))

                // 6 Pink Pill Buttons (2 columns x 3 rows - Exact from 34.png)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    NetworkPillButton(
                        text = "My Learning",
                        modifier = Modifier.weight(1f),
                        onClick = { activeNetworkType = "My Learning" }
                    )
                    NetworkPillButton(
                        text = "MY Applications",
                        modifier = Modifier.weight(1f),
                        onClick = { activeNetworkType = "MY Applications" }
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    NetworkPillButton(
                        text = "Internships",
                        modifier = Modifier.weight(1f),
                        onClick = { activeNetworkType = "Internships" }
                    )
                    NetworkPillButton(
                        text = "Job Eligibility",
                        modifier = Modifier.weight(1f),
                        onClick = { activeNetworkType = "Job Eligibility" }
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    NetworkPillButton(
                        text = "Recognition",
                        modifier = Modifier.weight(1f),
                        onClick = { activeNetworkType = "Recognition" }
                    )
                    NetworkPillButton(
                        text = "+ More",
                        modifier = Modifier.weight(1f),
                        onClick = { activeNetworkType = "More Opportunities" }
                    )
                }

                Spacer(modifier = Modifier.height(28.dp))

                // ✨ Recommended for You Section Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(text = "✨", fontSize = 20.sp)
                        Text(
                            text = "Recommended for You",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF334155),
                            fontSize = 20.sp
                        )
                    }

                    Text(
                        text = "Explore All",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = OdishaMagenta,
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .clickable {
                                viewModel.selectRecommendation(viewModel.allRecommendations.first())
                            }
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                // ✨ Side-to-Side Dual Smooth Motion Carousel (Two Lines: One moving Left, One moving Right)
                RecommendedDualMovingCarousel(
                    row1Items = viewModel.recommendationsRow1,
                    row2Items = viewModel.recommendationsRow2,
                    onItemClick = { item ->
                        viewModel.selectRecommendation(item)
                    }
                )

                Spacer(modifier = Modifier.height(28.dp))

                // Black Speech Bubble (Exact from 34.png)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                        .clickable { viewModel.setAiChatOpen(true) }
                ) {
                    Column {
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(16.dp)),
                            shape = RoundedCornerShape(16.dp),
                            color = Color(0xFF171717)
                        ) {
                            Text(
                                text = "Any question? Let's explore it together.",
                                color = PureWhite,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Medium,
                                fontSize = 13.5.sp,
                                modifier = Modifier.padding(horizontal = 18.dp, vertical = 14.dp),
                                textAlign = TextAlign.Center
                            )
                        }

                        // Speech bubble downward triangle pointer
                        Canvas(
                            modifier = Modifier
                                .padding(start = 28.dp)
                                .size(16.dp, 10.dp)
                        ) {
                            val pointerPath = Path().apply {
                                moveTo(0f, 0f)
                                lineTo(size.width, 0f)
                                lineTo(size.width / 2f, size.height)
                                close()
                            }
                            drawPath(pointerPath, color = Color(0xFF171717))
                        }
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))
            }

            // 3. BOTTOM RUNWAY SECTION (Curved pink container - Exact from 34.png)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 36.dp, topEnd = 36.dp))
                    .background(Color(0xFFFCE7F3))
                    .padding(horizontal = 22.dp, vertical = 18.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Left: Runway Head / Brain Profile Icon & Title
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(14.dp),
                        modifier = Modifier
                            .weight(1f)
                            .clickable { viewModel.setAiChatOpen(true) }
                    ) {
                        RunwayHeadIcon(modifier = Modifier.size(54.dp))

                        Column {
                            Text(
                                text = "Runway",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color(0xFF1E293B),
                                fontSize = 17.sp
                            )
                            Text(
                                text = "Analyzing your session..",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFF64748B),
                                fontSize = 12.sp
                            )
                        }
                    }

                    // Right: Black Ask Square Button with Paper Plane Icon (34.png)
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.clickable { viewModel.setAiChatOpen(true) }
                    ) {
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .background(Color(0xFF171717)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.Send,
                                contentDescription = "Ask Runway",
                                tint = PureWhite,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Ask",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1E293B),
                            fontSize = 11.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(84.dp))
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
// FULL-SCREEN AI CHATBOT PAGE (Exact Replica of image.png & image (1).png)
// Styled to EduLocker Color Theme
// =========================================================================
@Composable
fun FullScreenAiChatPage(
    viewModel: EduLockerViewModel,
    onClose: () -> Unit
) {
    val messages by viewModel.aiMessages.collectAsState()
    val isThinking by viewModel.isAiThinking.collectAsState()
    var inputText by remember { mutableStateOf("") }
    val listState = rememberLazyListState()
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current

    var selectedModel by remember { mutableStateOf("Quantum 3") }
    var showModelDropdown by remember { mutableStateOf(false) }
    var currentlySpeakingTimestamp by remember { mutableStateOf<String?>(null) }

    DisposableEffect(Unit) {
        onDispose {
            TextToSpeechHelper.stop()
        }
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

    val models = listOf("Quantum 3", "EduLocker Pro 4.0", "State Career Advisor")

    // Auto-scroll on new message
    LaunchedEffect(messages.size, isThinking) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(CanvasCream)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // -------------------------------------------------------------
            // TOP APP BAR: [Menu/Back] [Free plan • Upgrade] [Assistant Bot]
            // -------------------------------------------------------------
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Back Button
                IconButton(
                    onClick = onClose,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(PureWhite)
                        .border(1.dp, Color(0xFFE2E8F0), RoundedCornerShape(12.dp))
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = TextDark,
                        modifier = Modifier.size(20.dp)
                    )
                }

                // Center AI Assistant Header Badge
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF10B981))
                    )
                    Text(
                        text = "Runway AI Advisor",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0F172A),
                        fontSize = 16.sp
                    )
                }

                // Right Assistant Icon / Bot Avatar (image.png)
                IconButton(
                    onClick = {
                        Toast.makeText(context, "EduLocker AI Model: $selectedModel", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(PureWhite)
                        .border(1.dp, Color(0xFFE2E8F0), RoundedCornerShape(12.dp))
                ) {
                    Icon(
                        imageVector = Icons.Filled.SmartToy,
                        contentDescription = "AI Agent",
                        tint = Color(0xFF1E293B),
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            // -------------------------------------------------------------
            // CHAT MESSAGES STREAM / EMPTY STATE
            // -------------------------------------------------------------
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Header Space
                item { Spacer(modifier = Modifier.height(10.dp)) }

                // If only initial message or no user message yet, show the Geometric Hero Logo
                if (messages.size <= 1) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 40.dp, bottom = 20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            // Stylized Geometric AI Logo (from image.png)
                            AiGeometricHeroLogo(size = 56.dp)
                            Spacer(modifier = Modifier.height(18.dp))
                            Text(
                                text = "Good Morning, Laxmikant",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF1E293B),
                                fontSize = 22.sp
                            )
                            Text(
                                text = "How can I assist your studies & career journey today?",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color(0xFF64748B),
                                fontSize = 14.sp
                            )
                        }
                    }
                }

                items(messages) { message ->
                    if (message.isUser) {
                        // User message bubble (Clean dark slate pill / rounded)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Surface(
                                shape = RoundedCornerShape(topStart = 18.dp, topEnd = 4.dp, bottomStart = 18.dp, bottomEnd = 18.dp),
                                color = Color(0xFF0F172A),
                                modifier = Modifier.fillMaxWidth(0.85f)
                            ) {
                                Text(
                                    text = message.text,
                                    color = PureWhite,
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontSize = 14.sp,
                                    lineHeight = 20.sp,
                                    modifier = Modifier.padding(14.dp)
                                )
                            }
                        }
                    } else {
                        // AI Assistant message box (Clean white card with action buttons)
                        Column(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Surface(
                                shape = RoundedCornerShape(18.dp),
                                color = PureWhite,
                                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0)),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Text(
                                        text = message.text,
                                        color = Color(0xFF1E293B),
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontSize = 14.sp,
                                        lineHeight = 21.sp
                                    )

                                    Spacer(modifier = Modifier.height(12.dp))

                                    // Action bar: [Speaker] [Copy] [Thumbs Up]
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        val isSpeakingThis = currentlySpeakingTimestamp == message.timestamp
                                        Icon(
                                            imageVector = if (isSpeakingThis) Icons.Filled.Stop else Icons.Filled.GraphicEq,
                                            contentDescription = if (isSpeakingThis) "Stop Audio" else "Read Aloud",
                                            tint = if (isSpeakingThis) OdishaMagenta else Color(0xFF64748B),
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
                                                            onStart = {
                                                                currentlySpeakingTimestamp = message.timestamp
                                                            },
                                                            onDone = {
                                                                currentlySpeakingTimestamp = null
                                                            }
                                                        )
                                                    }
                                                }
                                        )
                                        Icon(
                                            imageVector = Icons.Filled.ContentCopy,
                                            contentDescription = "Copy Text",
                                            tint = Color(0xFF64748B),
                                            modifier = Modifier
                                                .size(18.dp)
                                                .clickable {
                                                    clipboardManager.setText(AnnotatedString(message.text))
                                                    Toast.makeText(context, "Copied to clipboard!", Toast.LENGTH_SHORT).show()
                                                }
                                        )
                                        Icon(
                                            imageVector = Icons.Filled.ThumbUp,
                                            contentDescription = "Helpful",
                                            tint = Color(0xFF64748B),
                                            modifier = Modifier
                                                .size(18.dp)
                                                .clickable {
                                                    Toast.makeText(context, "Thank you for the feedback!", Toast.LENGTH_SHORT).show()
                                                }
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                // AI Thinking Indicator
                if (isThinking) {
                    item {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.padding(start = 8.dp)
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(18.dp),
                                color = OdishaMagenta,
                                strokeWidth = 2.dp
                            )
                            Text(
                                text = "Runway AI is thinking...",
                                color = Color(0xFF64748B),
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }

                item { Spacer(modifier = Modifier.height(20.dp)) }
            }

            // -------------------------------------------------------------
            // BOTTOM PROMPT BAR (Exact from image.png & image (1).png)
            // -------------------------------------------------------------
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)),
                color = PureWhite,
                shadowElevation = 8.dp
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                ) {
                    // Input TextField
                    OutlinedTextField(
                        value = inputText,
                        onValueChange = { inputText = it },
                        placeholder = {
                            Text(
                                text = "Ask anything about careers, scholarships & exams..",
                                color = Color(0xFF94A3B8),
                                fontSize = 13.5.sp
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color(0xFFF8FAFC),
                            unfocusedContainerColor = Color(0xFFF8FAFC),
                            focusedIndicatorColor = Color(0xFFE2E8F0),
                            unfocusedIndicatorColor = Color(0xFFE2E8F0),
                            cursorColor = OdishaMagenta
                        ),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                        keyboardActions = KeyboardActions(
                            onSend = {
                                if (inputText.isNotBlank()) {
                                    viewModel.askRunwayAi(inputText)
                                    inputText = ""
                                }
                            }
                        ),
                        maxLines = 3
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Media Action Icons & Send Control Row (image (1).png)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Left Media Buttons: [Plus] [Image] [Attach] [Video]
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Add,
                                contentDescription = "Add",
                                tint = Color(0xFF64748B),
                                modifier = Modifier
                                    .size(22.dp)
                                    .clickable {
                                        Toast.makeText(context, "Attach documents or marksheet..", Toast.LENGTH_SHORT).show()
                                    }
                            )
                            Icon(
                                imageVector = Icons.Filled.Image,
                                contentDescription = "Image Upload",
                                tint = Color(0xFF64748B),
                                modifier = Modifier
                                    .size(20.dp)
                                    .clickable {
                                        Toast.makeText(context, "Select photo from gallery", Toast.LENGTH_SHORT).show()
                                    }
                            )
                            Icon(
                                imageVector = Icons.Filled.AttachFile,
                                contentDescription = "Attach File",
                                tint = Color(0xFF64748B),
                                modifier = Modifier
                                    .size(20.dp)
                                    .clickable {
                                        Toast.makeText(context, "Browse certificate PDF", Toast.LENGTH_SHORT).show()
                                    }
                            )
                            Icon(
                                imageVector = Icons.Filled.Videocam,
                                contentDescription = "Video",
                                tint = Color(0xFF64748B),
                                modifier = Modifier
                                    .size(22.dp)
                                    .clickable {
                                        Toast.makeText(context, "Video lecture query mode", Toast.LENGTH_SHORT).show()
                                    }
                            )
                        }

                        // Right Controls: [Model Pill Dropdown] [Mic] [Send Circle]
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Model Pill Selector Dropdown (image (1).png)
                            Box {
                                Surface(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(12.dp))
                                        .border(1.dp, Color(0xFFE2E8F0), RoundedCornerShape(12.dp))
                                        .clickable { showModelDropdown = true },
                                    shape = RoundedCornerShape(12.dp),
                                    color = Color(0xFFF1F5F9)
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp)
                                    ) {
                                        Text(
                                            text = selectedModel,
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFF334155)
                                        )
                                        Icon(
                                            imageVector = Icons.Filled.KeyboardArrowDown,
                                            contentDescription = "Select Model",
                                            tint = Color(0xFF64748B),
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                }

                                DropdownMenu(
                                    expanded = showModelDropdown,
                                    onDismissRequest = { showModelDropdown = false }
                                ) {
                                    models.forEach { model ->
                                        DropdownMenuItem(
                                            text = { Text(model, fontWeight = if (model == selectedModel) FontWeight.Bold else FontWeight.Normal) },
                                            onClick = {
                                                selectedModel = model
                                                showModelDropdown = false
                                                Toast.makeText(context, "Switched to $model", Toast.LENGTH_SHORT).show()
                                            }
                                        )
                                    }
                                }
                            }

                            // Microphone icon
                            Icon(
                                imageVector = Icons.Filled.Mic,
                                contentDescription = "Voice Input",
                                tint = Color(0xFF64748B),
                                modifier = Modifier
                                    .size(20.dp)
                                    .clickable {
                                        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                                            putExtra(
                                                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                                                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
                                            )
                                            putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to Runway AI...")
                                        }
                                        try {
                                            speechRecognizerLauncher.launch(intent)
                                        } catch (e: Exception) {
                                            Toast.makeText(context, "Voice input not supported on this device", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                            )

                            // Blue / Magenta Glowing Gradient Circular Send Button (image (1).png)
                            Box(
                                modifier = Modifier
                                    .size(38.dp)
                                    .clip(CircleShape)
                                    .background(
                                        Brush.verticalGradient(
                                            colors = listOf(Color(0xFF38BDF8), Color(0xFF0284C7))
                                        )
                                    )
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
                    onClick = { onAskRunway("Recommend the best jobs and internships based on my EduLocker profile") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = OdishaMagenta)
                ) {
                    Icon(Icons.AutoMirrored.Filled.Send, contentDescription = null, tint = PureWhite, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Ask Runway AI for Personalized Matches", color = PureWhite, fontWeight = FontWeight.Bold, fontSize = 13.5.sp)
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
                        text = "EduLocker Editorial • ${article.readTime}",
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
                    Text("Deep Dive with Runway AI", color = PureWhite, fontWeight = FontWeight.Bold, fontSize = 13.5.sp)
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
            .height(46.dp)
            .clip(RoundedCornerShape(14.dp))
            .clickable { onClick() },
        shape = RoundedCornerShape(14.dp),
        color = Color(0xFFFDE8E8) // Exact soft pink from 34.png
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF334155),
                fontSize = 13.5.sp
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
// Smooth Dual Moving Carousel for Recommended For You (Two Separate Lines: Left & Right)
// -----------------------------------------------------------
@Composable
fun RecommendedDualMovingCarousel(
    row1Items: List<RecommendationItem>,
    row2Items: List<RecommendationItem>,
    onItemClick: (RecommendationItem) -> Unit
) {
    // ROW 1: Smooth continuous motion moving towards LEFT
    val row1ScrollState = rememberLazyListState()
    var isRow1Interacting by remember { mutableStateOf(false) }

    LaunchedEffect(isRow1Interacting, row1Items) {
        if (!isRow1Interacting && row1Items.isNotEmpty()) {
            while (true) {
                delay(30)
                row1ScrollState.scrollBy(1.3f)
                if (!row1ScrollState.canScrollForward) {
                    row1ScrollState.scrollToItem(0)
                }
            }
        }
    }

    // ROW 2: Smooth continuous motion moving towards RIGHT (Reverse scroll)
    val row2ScrollState = rememberLazyListState(initialFirstVisibleItemIndex = 400)
    var isRow2Interacting by remember { mutableStateOf(false) }

    LaunchedEffect(isRow2Interacting, row2Items) {
        if (!isRow2Interacting && row2Items.isNotEmpty()) {
            while (true) {
                delay(30)
                if (row2ScrollState.canScrollBackward) {
                    row2ScrollState.scrollBy(-1.3f)
                } else {
                    row2ScrollState.scrollToItem(row2Items.size * 100)
                }
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // Line 1: Gliding smoothly to the LEFT
        LazyRow(
            state = row1ScrollState,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .pointerInput(Unit) {
                    awaitPointerEventScope {
                        while (true) {
                            val event = awaitPointerEvent()
                            isRow1Interacting = event.changes.any { it.pressed }
                        }
                    }
                }
        ) {
            items(1000) { index ->
                if (row1Items.isNotEmpty()) {
                    val item = row1Items[index % row1Items.size]
                    RecommendationCardSimple(item = item, onItemClick = onItemClick)
                }
            }
        }

        // Line 2: Gliding smoothly to the RIGHT
        LazyRow(
            state = row2ScrollState,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .pointerInput(Unit) {
                    awaitPointerEventScope {
                        while (true) {
                            val event = awaitPointerEvent()
                            isRow2Interacting = event.changes.any { it.pressed }
                        }
                    }
                }
        ) {
            items(1000) { index ->
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
            .width(168.dp)
            .height(108.dp)
            .clip(RoundedCornerShape(14.dp))
            .clickable { onItemClick(item) },
        shape = RoundedCornerShape(14.dp),
        color = Color(item.backgroundColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(13.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = item.title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1E293B),
                fontSize = 14.sp
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

