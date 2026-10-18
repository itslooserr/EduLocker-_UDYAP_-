package com.example.ui.screens.social

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.FormatSize
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Share
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.OdishaMagenta
import com.example.ui.theme.PureWhite

data class WikipediaSection(
    val title: String,
    val paragraphs: List<String>,
    val quote: String? = null
)

data class WikipediaArticleData(
    val id: String,
    val title: String,
    val subtitle: String,
    val lastModified: String,
    val readingTime: String,
    val infoboxData: Map<String, String>,
    val sections: List<WikipediaSection>,
    val references: List<String>
)

enum class ReaderTheme(
    val bg: Color,
    val text: Color,
    val secondaryText: Color,
    val cardBg: Color,
    val cardBorder: Color
) {
    WHITE(
        bg = Color(0xFFFFFFFF),
        text = Color(0xFF1E293B),
        secondaryText = Color(0xFF64748B),
        cardBg = Color(0xFFF8FAFC),
        cardBorder = Color(0xFFE2E8F0)
    ),
    SEPIA(
        bg = Color(0xFFFBF0D9),
        text = Color(0xFF3E2723),
        secondaryText = Color(0xFF795548),
        cardBg = Color(0xFFF5E6C8),
        cardBorder = Color(0xFFD7CCC8)
    ),
    NIGHT(
        bg = Color(0xFF121212),
        text = Color(0xFFE0E0E0),
        secondaryText = Color(0xFF9E9E9E),
        cardBg = Color(0xFF1E1E1E),
        cardBorder = Color(0xFF333333)
    )
}

@Composable
fun WikipediaArticleViewerModal(
    article: WikipediaArticleData,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    var currentTheme by remember { mutableStateOf(ReaderTheme.WHITE) }
    var fontScale by remember { mutableFloatStateOf(1.0f) }
    var isBookmarked by remember { mutableStateOf(false) }
    var showTOC by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(currentTheme.bg)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // -------------------------------------------------------------
            // 1. WIKIPEDIA TOP APP BAR
            // -------------------------------------------------------------
            Surface(
                color = currentTheme.cardBg,
                shadowElevation = 2.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        IconButton(onClick = onDismiss) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = currentTheme.text
                            )
                        }

                        // Wikipedia "W" emblem
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .clip(CircleShape)
                                .background(currentTheme.text),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "W",
                                color = currentTheme.bg,
                                fontWeight = FontWeight.Black,
                                fontSize = 16.sp,
                                fontFamily = FontFamily.Serif
                            )
                        }

                        Column {
                            Text(
                                text = "EduLocker Wiki",
                                color = currentTheme.text,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                            Text(
                                text = "Academic Encyclopedia",
                                color = currentTheme.secondaryText,
                                fontSize = 10.5.sp
                            )
                        }
                    }

                    // Toolbar actions: Theme switcher, Font zoom, Bookmark, Share
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        // Theme Switcher Button
                        IconButton(onClick = {
                            currentTheme = when (currentTheme) {
                                ReaderTheme.WHITE -> ReaderTheme.SEPIA
                                ReaderTheme.SEPIA -> ReaderTheme.NIGHT
                                ReaderTheme.NIGHT -> ReaderTheme.WHITE
                            }
                        }) {
                            Text(
                                text = when (currentTheme) {
                                    ReaderTheme.WHITE -> "☀️"
                                    ReaderTheme.SEPIA -> "📜"
                                    ReaderTheme.NIGHT -> "🌙"
                                },
                                fontSize = 16.sp
                            )
                        }

                        // Font Size Zoom
                        IconButton(onClick = {
                            fontScale = if (fontScale >= 1.25f) 0.9f else fontScale + 0.15f
                            Toast.makeText(context, "Font: ${(fontScale * 100).toInt()}%", Toast.LENGTH_SHORT).show()
                        }) {
                            Icon(
                                imageVector = Icons.Filled.FormatSize,
                                contentDescription = "Font size",
                                tint = currentTheme.text,
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        // Bookmark
                        IconButton(onClick = {
                            isBookmarked = !isBookmarked
                            Toast.makeText(
                                context,
                                if (isBookmarked) "Article saved to EduLocker Vault 📚" else "Bookmark removed",
                                Toast.LENGTH_SHORT
                            ).show()
                        }) {
                            Icon(
                                imageVector = if (isBookmarked) Icons.Filled.Bookmark else Icons.Filled.BookmarkBorder,
                                contentDescription = "Bookmark",
                                tint = if (isBookmarked) Color(0xFF0284C7) else currentTheme.text,
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        // Share
                        IconButton(onClick = {
                            Toast.makeText(context, "Article link copied!", Toast.LENGTH_SHORT).show()
                        }) {
                            Icon(
                                imageVector = Icons.Filled.Share,
                                contentDescription = "Share",
                                tint = currentTheme.text,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }

            // -------------------------------------------------------------
            // 2. ARTICLE SCHOLARLY CONTENT (Scrollable)
            // -------------------------------------------------------------
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 18.dp)
            ) {
                item {
                    Spacer(modifier = Modifier.height(18.dp))

                    // Article Title
                    Text(
                        text = article.title,
                        color = currentTheme.text,
                        fontSize = (26 * fontScale).sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif,
                        lineHeight = (32 * fontScale).sp
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    // Subtitle
                    Text(
                        text = article.subtitle,
                        color = currentTheme.secondaryText,
                        fontSize = (13 * fontScale).sp,
                        fontStyle = FontStyle.Italic
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Meta: Modified date & reading time
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = currentTheme.cardBg,
                            border = androidx.compose.foundation.BorderStroke(1.dp, currentTheme.cardBorder)
                        ) {
                            Text(
                                text = "⏱️ ${article.readingTime}",
                                color = currentTheme.secondaryText,
                                fontSize = 11.sp,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = currentTheme.cardBg,
                            border = androidx.compose.foundation.BorderStroke(1.dp, currentTheme.cardBorder)
                        ) {
                            Text(
                                text = "Verified Peer Review",
                                color = Color(0xFF059669),
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    HorizontalDivider(color = currentTheme.cardBorder)
                    Spacer(modifier = Modifier.height(16.dp))

                    // ---------------------------------------------------------
                    // WIKIPEDIA INFOBOX CARD
                    // ---------------------------------------------------------
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = currentTheme.cardBg),
                        border = androidx.compose.foundation.BorderStroke(1.dp, currentTheme.cardBorder),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            // Infobox Header
                            Text(
                                text = "🏛️ Official Summary Info",
                                color = currentTheme.text,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            HorizontalDivider(color = currentTheme.cardBorder)
                            Spacer(modifier = Modifier.height(8.dp))

                            article.infoboxData.forEach { (key, value) ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = key,
                                        color = currentTheme.secondaryText,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Medium,
                                        modifier = Modifier.weight(0.42f)
                                    )
                                    Text(
                                        text = value,
                                        color = currentTheme.text,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        modifier = Modifier.weight(0.58f)
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                }

                // -------------------------------------------------------------
                // ARTICLE SECTIONS
                // -------------------------------------------------------------
                items(article.sections) { section ->
                    Column(modifier = Modifier.padding(bottom = 20.dp)) {
                        Text(
                            text = section.title,
                            color = currentTheme.text,
                            fontSize = (20 * fontScale).sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Serif
                        )

                        Spacer(modifier = Modifier.height(4.dp))
                        HorizontalDivider(
                            color = currentTheme.cardBorder,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                        section.paragraphs.forEach { para ->
                            Text(
                                text = para,
                                color = currentTheme.text,
                                fontSize = (14.5 * fontScale).sp,
                                lineHeight = (22 * fontScale).sp,
                                modifier = Modifier.padding(bottom = 10.dp)
                            )
                        }

                        // Scholarly blockquote if present
                        section.quote?.let { quoteText ->
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp)
                                    .background(currentTheme.cardBg, RoundedCornerShape(4.dp))
                                    .border(
                                        width = 1.dp,
                                        color = currentTheme.cardBorder,
                                        shape = RoundedCornerShape(4.dp)
                                    )
                                    .padding(start = 12.dp)
                            ) {
                                Row(modifier = Modifier.padding(10.dp)) {
                                    Box(
                                        modifier = Modifier
                                            .width(3.dp)
                                            .height(36.dp)
                                            .background(Color(0xFF0284C7))
                                    )
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Text(
                                        text = "“$quoteText”",
                                        fontStyle = FontStyle.Italic,
                                        color = currentTheme.text,
                                        fontSize = (13.5 * fontScale).sp
                                    )
                                }
                            }
                        }
                    }
                }

                // -------------------------------------------------------------
                // REFERENCES & EXTERNAL LINKS (Wikipedia Style)
                // -------------------------------------------------------------
                item {
                    Column(modifier = Modifier.padding(top = 10.dp, bottom = 60.dp)) {
                        Text(
                            text = "References & Official Citations",
                            color = currentTheme.text,
                            fontSize = (18 * fontScale).sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Serif
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        HorizontalDivider(color = currentTheme.cardBorder)
                        Spacer(modifier = Modifier.height(10.dp))

                        article.references.forEachIndexed { index, ref ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    text = "^ [${index + 1}]",
                                    color = Color(0xFF0284C7),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp
                                )
                                Text(
                                    text = ref,
                                    color = currentTheme.secondaryText,
                                    fontSize = 12.sp,
                                    lineHeight = 16.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
