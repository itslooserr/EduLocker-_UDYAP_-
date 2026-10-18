package com.example.ui.screens.social

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Forward10
import androidx.compose.material.icons.filled.Fullscreen
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Replay10
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ThumbDown
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.ThumbDown
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.CanvasCream
import com.example.ui.theme.PureWhite
import kotlinx.coroutines.delay

data class YouTubeVideoData(
    val id: String,
    val title: String,
    val channelName: String,
    val channelAvatar: String,
    val subscribers: String,
    val views: String,
    val uploadDate: String,
    val durationText: String,
    val description: String,
    val tags: List<String>,
    val gradientColors: List<Color>,
    val iconEmoji: String
)

data class VideoComment(
    val id: String,
    val userName: String,
    val userAvatar: String,
    val timeAgo: String,
    val commentText: String,
    var likesCount: Int,
    var isLiked: Boolean = false,
    val isPinned: Boolean = false
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YouTubeVideoViewerModal(
    video: YouTubeVideoData,
    onDismiss: () -> Unit,
    onSelectRelatedVideo: (YouTubeVideoData) -> Unit = {}
) {
    val context = LocalContext.current
    var isPlaying by remember { mutableStateOf(true) }
    var playbackProgress by remember { mutableFloatStateOf(0.24f) }
    var isLiked by remember { mutableStateOf(false) }
    var isDisliked by remember { mutableStateOf(false) }
    var isSaved by remember { mutableStateOf(false) }
    var likesCount by remember { mutableIntStateOf(14200) }
    var isDescriptionExpanded by remember { mutableStateOf(false) }
    var showCommentsSheet by remember { mutableStateOf(false) }
    var newCommentText by remember { mutableStateOf("") }

    val comments = remember {
        mutableStateListOf(
            VideoComment(
                id = "c1",
                userName = "Ministry of Education India",
                userAvatar = "🏛️",
                timeAgo = "1 day ago",
                commentText = "📌 Pinned: Portal servers are running at 100% capacity. Please verify your APAAR ID and bank account seeding before submitting.",
                likesCount = 845,
                isPinned = true
            ),
            VideoComment(
                id = "c2",
                userName = "Priyanka Sahoo (KIIT)",
                userAvatar = "PS",
                timeAgo = "5 hours ago",
                commentText = "Very clear explanation! I received my Aadhaar DBT status verified within 10 minutes on EduLocker.",
                likesCount = 142
            ),
            VideoComment(
                id = "c3",
                userName = "Bhubaneswar STEM Club",
                userAvatar = "⚡",
                timeAgo = "2 hours ago",
                commentText = "Class 12 students please don't miss the deadline of 30th October.",
                likesCount = 68
            )
        )
    }

    val relatedVideos = remember {
        listOf(
            YouTubeVideoData(
                id = "rel_1",
                title = "APAAR Card 12-Digit ID Generation Step by Step Tutorial",
                channelName = "Education Dept. INDIA",
                channelAvatar = "🎓",
                subscribers = "1.8M",
                views = "240K views",
                uploadDate = "4 days ago",
                durationText = "08:14",
                description = "Complete guide on creating One Nation One Student ID.",
                tags = listOf("#APAAR", "#DigiLocker", "#OdishaStudents"),
                gradientColors = listOf(Color(0xFF0F172A), Color(0xFF1E293B)),
                iconEmoji = "🆔"
            ),
            YouTubeVideoData(
                id = "rel_2",
                title = "Top 10 State Science Projects at IIT Bhubaneswar Tech Conclave",
                channelName = "India Xstream Tech",
                channelAvatar = "⚡",
                subscribers = "920K",
                views = "95K views",
                uploadDate = "1 week ago",
                durationText = "14:22",
                description = "High school innovators showcase AI & clean energy prototypes.",
                tags = listOf("#IIT", "#Innovation", "#STEM"),
                gradientColors = listOf(Color(0xFF312E81), Color(0xFF4338CA)),
                iconEmoji = "🚀"
            ),
            YouTubeVideoData(
                id = "rel_3",
                title = "Pariksha Pe Charcha: Masterclass with Top Olympiad Scorers",
                channelName = "PMO Youth Channel",
                channelAvatar = "🇮🇳",
                subscribers = "4.2M",
                views = "580K views",
                uploadDate = "2 days ago",
                durationText = "19:45",
                description = "Effective study schedule and stress management techniques.",
                tags = listOf("#PPC2027", "#Olympiad", "#Exams"),
                gradientColors = listOf(Color(0xFF7C2D12), Color(0xFFEA580C)),
                iconEmoji = "🏆"
            )
        )
    }

    // Playback progress ticker
    LaunchedEffect(isPlaying) {
        while (isPlaying) {
            delay(1000)
            if (playbackProgress < 1f) {
                playbackProgress += 0.015f
            } else {
                playbackProgress = 0f
            }
        }
    }

    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val waveOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 100f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "wave"
    )

    // App Theme Styled Container (Light CanvasCream)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(CanvasCream)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // -------------------------------------------------------------
            // 1. TOP 16:9 VIDEO PLAYER BOX
            // -------------------------------------------------------------
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f)
                    .background(Color.Black)
            ) {
                // Video Canvas Artwork
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Brush.linearGradient(video.gradientColors))
                ) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        drawCircle(
                            color = Color.White.copy(alpha = 0.08f),
                            radius = size.width * 0.45f,
                            center = Offset(size.width * 0.5f + waveOffset, size.height * 0.5f)
                        )
                    }

                    // Centered Emoji Art & Playing indicator
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = video.iconEmoji, fontSize = 48.sp)
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = if (isPlaying) "Playing • HD 1080p" else "Paused",
                            color = PureWhite.copy(alpha = 0.85f),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                // Top Controls (Back Button)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 10.dp)
                        .align(Alignment.TopCenter),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier
                            .size(34.dp)
                            .background(Color.Black.copy(alpha = 0.45f), CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = PureWhite,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                // Center Play/Pause Controls Overlay
                Row(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalArrangement = Arrangement.spacedBy(24.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {
                            playbackProgress = (playbackProgress - 0.05f).coerceAtLeast(0f)
                        },
                        modifier = Modifier
                            .size(40.dp)
                            .background(Color.Black.copy(alpha = 0.4f), CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Replay10,
                            contentDescription = "-10s",
                            tint = PureWhite,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    IconButton(
                        onClick = { isPlaying = !isPlaying },
                        modifier = Modifier
                            .size(54.dp)
                            .background(Color.Black.copy(alpha = 0.6f), CircleShape)
                    ) {
                        Icon(
                            imageVector = if (isPlaying) Icons.Filled.Pause else Icons.Filled.PlayArrow,
                            contentDescription = "Play/Pause",
                            tint = PureWhite,
                            modifier = Modifier.size(34.dp)
                        )
                    }

                    IconButton(
                        onClick = {
                            playbackProgress = (playbackProgress + 0.05f).coerceAtMost(1f)
                        },
                        modifier = Modifier
                            .size(40.dp)
                            .background(Color.Black.copy(alpha = 0.4f), CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Forward10,
                            contentDescription = "+10s",
                            tint = PureWhite,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }

                // Bottom Scrub Bar
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .background(
                            Brush.verticalGradient(
                                listOf(Color.Transparent, Color.Black.copy(alpha = 0.8f))
                            )
                        )
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${(playbackProgress * 12).toInt()}:${String.format("%02d", ((playbackProgress * 12 * 60) % 60).toInt())} / ${video.durationText}",
                            color = PureWhite,
                            fontSize = 11.sp
                        )
                        IconButton(
                            onClick = {
                                Toast.makeText(context, "Full Screen Mode", Toast.LENGTH_SHORT).show()
                            },
                            modifier = Modifier.size(24.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Fullscreen,
                                contentDescription = "Fullscreen",
                                tint = PureWhite,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }

                    Slider(
                        value = playbackProgress,
                        onValueChange = { playbackProgress = it },
                        colors = SliderDefaults.colors(
                            thumbColor = Color(0xFFE11D48),
                            activeTrackColor = Color(0xFFE11D48),
                            inactiveTrackColor = Color.White.copy(alpha = 0.35f)
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(18.dp)
                    )
                }
            }

            // -------------------------------------------------------------
            // 2. VIDEO METADATA & ACTIONS (Styled in App's Cream / White Theme)
            // -------------------------------------------------------------
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(CanvasCream)
                    .padding(horizontal = 16.dp)
            ) {
                item {
                    Spacer(modifier = Modifier.height(14.dp))

                    // Title in App's Dark Typography
                    Text(
                        text = video.title,
                        color = Color(0xFF111827),
                        fontSize = 16.5.sp,
                        fontWeight = FontWeight.Bold,
                        lineHeight = 22.sp
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    // Views & Upload Date
                    Text(
                        text = "${video.views} • ${video.uploadDate} • ${video.tags.joinToString(" ")}",
                        color = Color(0xFF64748B),
                        fontSize = 12.5.sp
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Channel Row (Clean Without Subscribe Button)
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = PureWhite,
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp, vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFFF1F5F9)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = video.channelAvatar,
                                    fontSize = 18.sp
                                )
                            }

                            Column {
                                Text(
                                    text = video.channelName,
                                    color = Color(0xFF111827),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.5.sp
                                )
                                Text(
                                    text = "${video.subscribers} • Official Partner",
                                    color = Color(0xFF64748B),
                                    fontSize = 11.5.sp
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Horizontal Action Buttons (Like, Share, Save)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        // Like Button
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = PureWhite,
                            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0)),
                            modifier = Modifier
                                .weight(1f)
                                .clickable {
                                    if (!isLiked) {
                                        isLiked = true
                                        likesCount++
                                    } else {
                                        isLiked = false
                                        likesCount--
                                    }
                                }
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier.padding(vertical = 9.dp)
                            ) {
                                Icon(
                                    imageVector = if (isLiked) Icons.Filled.ThumbUp else Icons.Outlined.ThumbUp,
                                    contentDescription = "Like",
                                    tint = if (isLiked) Color(0xFFE11D48) else Color(0xFF334155),
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "${likesCount / 1000}k",
                                    color = Color(0xFF111827),
                                    fontSize = 12.5.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        // Share Button
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = PureWhite,
                            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0)),
                            modifier = Modifier
                                .weight(1f)
                                .clickable {
                                    Toast.makeText(context, "Video link copied to clipboard!", Toast.LENGTH_SHORT).show()
                                }
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier.padding(vertical = 9.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Share,
                                    contentDescription = "Share",
                                    tint = Color(0xFF334155),
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(text = "Share", color = Color(0xFF111827), fontSize = 12.5.sp, fontWeight = FontWeight.Bold)
                            }
                        }

                        // Save Button
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = PureWhite,
                            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0)),
                            modifier = Modifier
                                .weight(1f)
                                .clickable {
                                    isSaved = !isSaved
                                    Toast.makeText(
                                        context,
                                        if (isSaved) "Saved to Study Vault 📁" else "Removed from Vault",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier.padding(vertical = 9.dp)
                            ) {
                                Icon(
                                    imageVector = if (isSaved) Icons.Filled.Bookmark else Icons.Filled.BookmarkBorder,
                                    contentDescription = "Save",
                                    tint = if (isSaved) Color(0xFF0284C7) else Color(0xFF334155),
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = if (isSaved) "Saved" else "Save",
                                    color = Color(0xFF111827),
                                    fontSize = 12.5.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Expandable Description Box
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = PureWhite,
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { isDescriptionExpanded = !isDescriptionExpanded }
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                text = video.description,
                                color = Color(0xFF334155),
                                fontSize = 13.sp,
                                maxLines = if (isDescriptionExpanded) 10 else 2,
                                overflow = TextOverflow.Ellipsis,
                                lineHeight = 18.sp
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = if (isDescriptionExpanded) "Show less" else "...more",
                                color = Color(0xFF0284C7),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Comments Preview Box
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = PureWhite,
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { showCommentsSheet = true }
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Comments (${comments.size})",
                                    color = Color(0xFF111827),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.5.sp
                                )
                                Text(
                                    text = "View all",
                                    color = Color(0xFF0284C7),
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    text = comments.firstOrNull()?.userAvatar ?: "👤",
                                    fontSize = 16.sp
                                )
                                Text(
                                    text = comments.firstOrNull()?.commentText ?: "No comments yet",
                                    color = Color(0xFF64748B),
                                    fontSize = 12.sp,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Up Next Section Header
                    Text(
                        text = "Up Next",
                        color = Color(0xFF111827),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(10.dp))
                }

                items(relatedVideos) { rel ->
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = PureWhite,
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 5.dp)
                            .clickable { onSelectRelatedVideo(rel) }
                    ) {
                        Row(
                            modifier = Modifier.padding(10.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            // Thumbnail
                            Box(
                                modifier = Modifier
                                    .width(110.dp)
                                    .height(68.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Brush.linearGradient(rel.gradientColors)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = rel.iconEmoji, fontSize = 22.sp)
                                Box(
                                    modifier = Modifier
                                        .align(Alignment.BottomEnd)
                                        .padding(3.dp)
                                        .background(Color.Black.copy(alpha = 0.75f), RoundedCornerShape(3.dp))
                                        .padding(horizontal = 4.dp, vertical = 1.dp)
                                ) {
                                    Text(
                                        text = rel.durationText,
                                        color = PureWhite,
                                        fontSize = 9.5.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }

                            // Info
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = rel.title,
                                    color = Color(0xFF111827),
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis,
                                    lineHeight = 17.sp
                                )
                                Spacer(modifier = Modifier.height(3.dp))
                                Text(
                                    text = "${rel.channelName} • ${rel.views}",
                                    color = Color(0xFF64748B),
                                    fontSize = 11.5.sp
                                )
                            }
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(40.dp))
                }
            }
        }

        // -------------------------------------------------------------
        // COMMENTS BOTTOM SHEET (App Theme Styled)
        // -------------------------------------------------------------
        if (showCommentsSheet) {
            ModalBottomSheet(
                onDismissRequest = { showCommentsSheet = false },
                containerColor = PureWhite,
                contentColor = Color(0xFF111827)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 24.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Comments (${comments.size})",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF111827)
                        )
                        IconButton(onClick = { showCommentsSheet = false }) {
                            Icon(Icons.Filled.Close, contentDescription = "Close", tint = Color(0xFF111827))
                        }
                    }

                    HorizontalDivider(color = Color(0xFFE2E8F0))
                    Spacer(modifier = Modifier.height(10.dp))

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f, fill = false)
                            .height(280.dp)
                    ) {
                        items(comments) { comment ->
                            Column(modifier = Modifier.padding(vertical = 8.dp)) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Text(text = comment.userAvatar, fontSize = 16.sp)
                                    Text(
                                        text = comment.userName,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp,
                                        color = Color(0xFF111827)
                                    )
                                    Text(
                                        text = comment.timeAgo,
                                        fontSize = 11.sp,
                                        color = Color(0xFF64748B)
                                    )
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = comment.commentText,
                                    fontSize = 13.sp,
                                    color = Color(0xFF334155),
                                    lineHeight = 18.sp
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Comment Input Bar
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = newCommentText,
                            onValueChange = { newCommentText = it },
                            placeholder = { Text("Add a public comment...", color = Color(0xFF94A3B8), fontSize = 13.sp) },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(24.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color(0xFF111827),
                                unfocusedTextColor = Color(0xFF111827),
                                focusedBorderColor = Color(0xFF0284C7),
                                unfocusedBorderColor = Color(0xFFCBD5E1),
                                focusedContainerColor = Color(0xFFF8FAFC),
                                unfocusedContainerColor = Color(0xFFF8FAFC)
                            )
                        )

                        IconButton(
                            onClick = {
                                if (newCommentText.isNotBlank()) {
                                    comments.add(
                                        VideoComment(
                                            id = "c_${System.currentTimeMillis()}",
                                            userName = "You (EduLocker Student)",
                                            userAvatar = "🧑‍🎓",
                                            timeAgo = "Just now",
                                            commentText = newCommentText,
                                            likesCount = 0
                                        )
                                    )
                                    newCommentText = ""
                                    Toast.makeText(context, "Comment posted!", Toast.LENGTH_SHORT).show()
                                }
                            },
                            modifier = Modifier
                                .size(44.dp)
                                .background(Color(0xFF0284C7), CircleShape)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Send,
                                contentDescription = "Send",
                                tint = PureWhite,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
