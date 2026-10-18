package com.example.ui.screens.social

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.PureWhite
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class ShortReelItem(
    val id: String,
    val title: String,
    val creatorHandle: String,
    val creatorAvatar: String,
    val initialLikes: Int,
    val initialComments: Int,
    val soundTrack: String,
    val hashtags: String,
    val bgGradient: List<Color>,
    val centralEmoji: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShortsReelsViewerModal(
    shortsList: List<ShortReelItem>,
    initialIndex: Int = 0,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    val pagerState = rememberPagerState(
        initialPage = initialIndex.coerceIn(0, (shortsList.size - 1).coerceAtLeast(0)),
        pageCount = { shortsList.size }
    )

    var showCommentsSheet by remember { mutableStateOf(false) }
    val comments = remember(pagerState.currentPage) {
        mutableStateListOf(
            VideoComment(
                id = "sc1",
                userName = "Sneha Mohapatra",
                userAvatar = "SM",
                timeAgo = "10m ago",
                commentText = "This trick saved me so much time in physics solving! 🔥",
                likesCount = 38
            ),
            VideoComment(
                id = "sc2",
                userName = "Odisha STEM Club",
                userAvatar = "⚡",
                timeAgo = "1h ago",
                commentText = "Share this with all Class 10 & 12 board aspirants.",
                likesCount = 94
            )
        )
    }
    var newCommentText by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        // -------------------------------------------------------------
        // VERTICAL SWIPEABLE PAGER (Full-Screen Vertical Scroll)
        // -------------------------------------------------------------
        VerticalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { pageIndex ->
            val currentShort = shortsList[pageIndex]
            SingleShortReelPage(
                shortItem = currentShort,
                isActivePage = pagerState.currentPage == pageIndex,
                onOpenComments = { showCommentsSheet = true }
            )
        }

        // -------------------------------------------------------------
        // TOP LEFT CLEAN BACK BUTTON (No Shorts text, No 1/4, No Next button)
        // -------------------------------------------------------------
        IconButton(
            onClick = onDismiss,
            modifier = Modifier
                .padding(start = 14.dp, top = 18.dp)
                .align(Alignment.TopStart)
                .size(38.dp)
                .background(Color.Black.copy(alpha = 0.4f), CircleShape)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = PureWhite,
                modifier = Modifier.size(22.dp)
            )
        }

        // -------------------------------------------------------------
        // COMMENTS BOTTOM SHEET
        // -------------------------------------------------------------
        if (showCommentsSheet) {
            ModalBottomSheet(
                onDismissRequest = { showCommentsSheet = false },
                containerColor = Color(0xFF1E1E1E),
                contentColor = PureWhite
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
                            color = PureWhite
                        )
                        IconButton(onClick = { showCommentsSheet = false }) {
                            Icon(Icons.Filled.Close, contentDescription = "Close", tint = PureWhite)
                        }
                    }

                    HorizontalDivider(color = Color.White.copy(alpha = 0.1f))
                    Spacer(modifier = Modifier.height(10.dp))

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f, fill = false)
                            .height(260.dp)
                    ) {
                        items(comments) { c ->
                            Column(modifier = Modifier.padding(vertical = 8.dp)) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Text(text = c.userAvatar, fontSize = 16.sp)
                                    Text(text = c.userName, fontWeight = FontWeight.Bold, fontSize = 12.5.sp, color = PureWhite)
                                    Text(text = c.timeAgo, fontSize = 11.sp, color = Color(0xFFAAAAAA))
                                }
                                Spacer(modifier = Modifier.height(3.dp))
                                Text(text = c.commentText, fontSize = 12.5.sp, color = Color(0xFFE2E8F0))
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = newCommentText,
                            onValueChange = { newCommentText = it },
                            placeholder = { Text("Add comment...", color = Color(0xFFAAAAAA), fontSize = 12.sp) },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(24.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = PureWhite,
                                unfocusedTextColor = PureWhite,
                                focusedBorderColor = Color(0xFFE11D48),
                                unfocusedBorderColor = Color.White.copy(alpha = 0.3f),
                                focusedContainerColor = Color(0xFF272727),
                                unfocusedContainerColor = Color(0xFF272727)
                            )
                        )

                        IconButton(
                            onClick = {
                                if (newCommentText.isNotBlank()) {
                                    comments.add(
                                        VideoComment(
                                            id = "sc_${System.currentTimeMillis()}",
                                            userName = "You",
                                            userAvatar = "🧑‍🎓",
                                            timeAgo = "Just now",
                                            commentText = newCommentText,
                                            likesCount = 0
                                        )
                                    )
                                    newCommentText = ""
                                }
                            },
                            modifier = Modifier
                                .size(42.dp)
                                .background(Color(0xFFE11D48), CircleShape)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Send,
                                contentDescription = "Send",
                                tint = PureWhite,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SingleShortReelPage(
    shortItem: ShortReelItem,
    isActivePage: Boolean,
    onOpenComments: () -> Unit
) {
    val context = LocalContext.current
    var isLiked by remember { mutableStateOf(false) }
    var isSaved by remember { mutableStateOf(false) }
    var likesCount by remember { mutableIntStateOf(shortItem.initialLikes) }
    var isPlaying by remember { mutableStateOf(true) }
    var showBigHeart by remember { mutableStateOf(false) }
    val playProgress = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()

    // Auto-looping progress bar for active reel
    LaunchedEffect(isActivePage, isPlaying) {
        if (isActivePage && isPlaying) {
            while (isPlaying && isActivePage) {
                playProgress.snapTo(0f)
                playProgress.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(durationMillis = 8000, easing = LinearEasing)
                )
            }
        } else {
            playProgress.snapTo(0f)
        }
    }

    // Music disk rotation animation
    val infiniteTransition = rememberInfiniteTransition(label = "music_disk")
    val diskRotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "disk"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(
                    onDoubleTap = {
                        if (!isLiked) {
                            isLiked = true
                            likesCount++
                        }
                        showBigHeart = true
                        scope.launch {
                            delay(900)
                            showBigHeart = false
                        }
                    },
                    onTap = {
                        isPlaying = !isPlaying
                    }
                )
            }
    ) {
        // Background Gradient Art
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.verticalGradient(shortItem.bgGradient))
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val cx = size.width * 0.5f
                val cy = size.height * 0.45f
                drawCircle(
                    brush = Brush.radialGradient(
                        listOf(Color.White.copy(alpha = 0.12f), Color.Transparent),
                        center = Offset(cx, cy),
                        radius = size.width * 0.7f
                    ),
                    radius = size.width * 0.7f,
                    center = Offset(cx, cy)
                )
            }

            // Big Central Emoji Art
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(bottom = 90.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = shortItem.centralEmoji, fontSize = 72.sp)
                Spacer(modifier = Modifier.height(14.dp))
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = Color.Black.copy(alpha = 0.4f)
                ) {
                    Text(
                        text = "⚡ EduShorts Reel",
                        color = PureWhite,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }
            }
        }

        // Double-Tap Big Heart
        AnimatedVisibility(
            visible = showBigHeart,
            enter = scaleIn(tween(250)) + fadeIn(),
            exit = scaleOut(tween(350)) + fadeOut(),
            modifier = Modifier.align(Alignment.Center)
        ) {
            Icon(
                imageVector = Icons.Filled.Favorite,
                contentDescription = null,
                tint = Color(0xFFE11D48),
                modifier = Modifier.size(110.dp)
            )
        }

        // Pause Indicator
        if (!isPlaying) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .align(Alignment.Center)
                    .background(Color.Black.copy(alpha = 0.5f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.PlayArrow,
                    contentDescription = "Play",
                    tint = PureWhite,
                    modifier = Modifier.size(36.dp)
                )
            }
        }

        // Right Floating Action Bar
        Column(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 12.dp, bottom = 48.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 1. Like Button
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                IconButton(
                    onClick = {
                        isLiked = !isLiked
                        if (isLiked) likesCount++ else likesCount--
                    },
                    modifier = Modifier
                        .size(46.dp)
                        .background(Color.Black.copy(alpha = 0.45f), CircleShape)
                ) {
                    Icon(
                        imageVector = if (isLiked) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        contentDescription = "Like",
                        tint = if (isLiked) Color(0xFFE11D48) else PureWhite,
                        modifier = Modifier.size(26.dp)
                    )
                }
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = "${likesCount / 1000}k",
                    color = PureWhite,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // 2. Comments Button
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                IconButton(
                    onClick = onOpenComments,
                    modifier = Modifier
                        .size(46.dp)
                        .background(Color.Black.copy(alpha = 0.45f), CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Filled.ChatBubble,
                        contentDescription = "Comments",
                        tint = PureWhite,
                        modifier = Modifier.size(24.dp)
                    )
                }
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = "${shortItem.initialComments}",
                    color = PureWhite,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // 3. Save / Bookmark Button
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                IconButton(
                    onClick = {
                        isSaved = !isSaved
                        Toast.makeText(
                            context,
                            if (isSaved) "Saved to Quick Vault! 🔖" else "Removed bookmark",
                            Toast.LENGTH_SHORT
                        ).show()
                    },
                    modifier = Modifier
                        .size(46.dp)
                        .background(Color.Black.copy(alpha = 0.45f), CircleShape)
                ) {
                    Icon(
                        imageVector = if (isSaved) Icons.Filled.Bookmark else Icons.Filled.BookmarkBorder,
                        contentDescription = "Save",
                        tint = if (isSaved) Color(0xFF38BDF8) else PureWhite,
                        modifier = Modifier.size(24.dp)
                    )
                }
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = "Save",
                    color = PureWhite,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // 4. Share Button
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                IconButton(
                    onClick = {
                        Toast.makeText(context, "Short shared with classmates!", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier
                        .size(46.dp)
                        .background(Color.Black.copy(alpha = 0.45f), CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Share,
                        contentDescription = "Share",
                        tint = PureWhite,
                        modifier = Modifier.size(24.dp)
                    )
                }
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = "Share",
                    color = PureWhite,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // 5. Rotating Vinyl Sound Disc
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(Color.Black.copy(alpha = 0.8f))
                    .rotate(diskRotation),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.MusicNote,
                    contentDescription = "Sound",
                    tint = PureWhite,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        // Bottom Creator Info & Caption
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth(0.78f)
                .padding(start = 16.dp, bottom = 20.dp)
        ) {
            // Creator Handle (Clean & Sleek)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(CircleShape)
                        .background(PureWhite),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = shortItem.creatorAvatar,
                        fontSize = 15.sp
                    )
                }

                Text(
                    text = shortItem.creatorHandle,
                    color = PureWhite,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.5.sp
                )

                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = Color.White.copy(alpha = 0.2f)
                ) {
                    Text(
                        text = "Official",
                        color = PureWhite,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(horizontal = 7.dp, vertical = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Title / Caption
            Text(
                text = shortItem.title,
                color = PureWhite,
                fontSize = 13.5.sp,
                fontWeight = FontWeight.Medium,
                lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Hashtags
            Text(
                text = shortItem.hashtags,
                color = Color(0xFF38BDF8),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Sound Track Row
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.MusicNote,
                    contentDescription = null,
                    tint = PureWhite.copy(alpha = 0.8f),
                    modifier = Modifier.size(14.dp)
                )
                Text(
                    text = shortItem.soundTrack,
                    color = PureWhite.copy(alpha = 0.8f),
                    fontSize = 11.5.sp
                )
            }
        }

        // Bottom Edge Progress Line
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(3.dp)
                .align(Alignment.BottomCenter)
                .background(Color.White.copy(alpha = 0.25f))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(playProgress.value)
                    .fillMaxHeight()
                    .background(Color(0xFFE11D48))
            )
        }
    }
}
