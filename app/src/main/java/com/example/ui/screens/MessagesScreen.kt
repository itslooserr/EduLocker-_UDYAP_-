package com.example.ui.screens

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import com.example.data.local.ChatMessageEntity
import com.example.ui.theme.CanvasCream
import com.example.ui.theme.DarkBorder
import com.example.ui.theme.OdishaMagenta
import com.example.ui.theme.PureWhite
import com.example.ui.theme.TextDark
import com.example.ui.theme.TextMuted
import com.example.ui.viewmodel.EduLockerViewModel
import java.io.File

data class MessageContact(
    val name: String,
    val snippet: String,
    val initials: String,
    val avatarColor: Color,
    val isUnread: Boolean = false,
    val isImportant: Boolean = false,
    val isFavorite: Boolean = false,
    val time: String = "Just now"
)

@Composable
fun MessagesScreen(
    viewModel: EduLockerViewModel,
    onBackClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var selectedCategory by remember { mutableStateOf("All") }
    var searchQuery by remember { mutableStateOf("") }
    var activeContactDialog by remember { mutableStateOf<MessageContact?>(null) }
    var currentMessageInput by remember { mutableStateOf("") }
    val contactAvatars by viewModel.contactAvatars.collectAsState()
    val context = LocalContext.current
    var contactForPhotoUpload by remember { mutableStateOf<String?>(null) }

    val contactPhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        val target = contactForPhotoUpload
        if (uri != null && target != null) {
            viewModel.uploadContactPhoto(target, uri)
            Toast.makeText(context, "Photo set for $target", Toast.LENGTH_SHORT).show()
        }
    }

    val contacts = remember {
        listOf(
            MessageContact("Ashneer Gorver", "Hows your days going buddy", "AG", Color(0xFFE0E7FF), isFavorite = true, time = "2m ago"),
            MessageContact("Derric Hudston", "Is everything going alright, if u re free then call m..", "DH", Color(0xFFFDE8EF), isUnread = true, time = "5m ago"),
            MessageContact("Mukesh Aruna", "3+ New messages regarding Physics project", "MA", Color(0xFFEDE9FE), isUnread = true, time = "12m ago"),
            MessageContact("Anil jha", "seen 3 min ago", "AJ", Color(0xFFFEF3C7), time = "25m ago"),
            MessageContact("Leon jacknedon", "sent 7 min ago", "LJ", Color(0xFFCCFBF1), time = "40m ago"),
            MessageContact("Silpa setti", "Scholarship documentation approved!", "SS", Color(0xFFFFEDD5), isImportant = true, time = "1h ago"),
            MessageContact("Suksa Aboma", "Why are you not responding dude.. im still wait..", "SA", Color(0xFFE2E8F0), time = "2h ago"),
            MessageContact("Nork Holand", "Share your startup idea in whatsapp", "NH", Color(0xFFDBEAFE), time = "3h ago"),
            MessageContact("Meena Nero", "ah bro can u share me chem mams number", "MN", Color(0xFFFCE7F3), time = "5h ago"),
            MessageContact("Harsh Dipke", "Physics practical lab sheet updated", "HD", Color(0xFFF1F5F9), time = "1d ago")
        )
    }

    val filteredContacts = contacts.filter { contact ->
        val matchesCategory = when (selectedCategory) {
            "Unread" -> contact.isUnread
            "Important" -> contact.isImportant
            "Favorites" -> contact.isFavorite
            else -> true
        }
        val matchesSearch = searchQuery.isBlank() ||
                contact.name.contains(searchQuery, ignoreCase = true) ||
                contact.snippet.contains(searchQuery, ignoreCase = true)
        matchesCategory && matchesSearch
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(CanvasCream)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 18.dp)
        ) {
            Spacer(modifier = Modifier.height(34.dp))

            // 1. TOP HEADER with Back Button & Title
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .border(1.5.dp, DarkBorder, CircleShape)
                            .background(PureWhite)
                            .clickable {
                                if (onBackClick != {}) onBackClick() else viewModel.goBack()
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = TextDark,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Column {
                        Text(
                            text = "Messages",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.ExtraBold,
                            color = TextDark,
                            fontSize = 20.sp
                        )
                        Text(
                            text = "Student & Mentor Connections",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF64748B),
                            fontSize = 12.sp
                        )
                    }
                }

                // New Chat Button
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .background(OdishaMagenta)
                        .clickable {
                            Toast.makeText(context, "Start new discussion with batchmates", Toast.LENGTH_SHORT).show()
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.EditNote,
                        contentDescription = "New Message",
                        tint = PureWhite,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // 2. SEARCH BAR (Positioned at TOP with clear button)
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .clip(RoundedCornerShape(50.dp))
                    .border(1.5.dp, DarkBorder, RoundedCornerShape(50.dp)),
                placeholder = {
                    Text("Search connections & messages...", color = TextMuted, fontSize = 13.sp)
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search",
                        tint = TextDark,
                        modifier = Modifier.size(20.dp)
                    )
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { searchQuery = "" }) {
                            Icon(
                                imageVector = Icons.Filled.Close,
                                contentDescription = "Clear",
                                tint = TextDark,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                },
                singleLine = true,
                shape = RoundedCornerShape(50.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = PureWhite,
                    unfocusedContainerColor = PureWhite,
                    disabledContainerColor = PureWhite,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedTextColor = TextDark,
                    unfocusedTextColor = TextDark
                )
            )

            Spacer(modifier = Modifier.height(10.dp))

            // 3. CATEGORY PILLS (All, Unread, Important, Favorites)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf("All", "Unread", "Important", "Favorites").forEach { cat ->
                    val isSelected = selectedCategory == cat
                    Surface(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .clickable { selectedCategory = cat },
                        shape = RoundedCornerShape(20.dp),
                        color = if (isSelected) OdishaMagenta else PureWhite,
                        border = if (isSelected) null else androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFCBD5E1))
                    ) {
                        Text(
                            text = cat,
                            fontSize = 11.5.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = if (isSelected) PureWhite else TextDark,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // 4. CONTACTS LIST
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(filteredContacts) { contact ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .border(1.dp, Color(0xFFE2E8F0), RoundedCornerShape(16.dp))
                            .clickable { activeContactDialog = contact },
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = PureWhite)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            // Avatar (Displays custom photo if uploaded, or initials + camera icon on click)
                            val customAvatarPath = contactAvatars[contact.name]
                            Box(
                                modifier = Modifier
                                    .size(46.dp)
                                    .clip(CircleShape)
                                    .background(contact.avatarColor)
                                    .clickable {
                                        contactForPhotoUpload = contact.name
                                        contactPhotoPickerLauncher.launch(
                                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                        )
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                if (!customAvatarPath.isNullOrEmpty() && File(customAvatarPath).exists()) {
                                    AsyncImage(
                                        model = File(customAvatarPath),
                                        contentDescription = contact.name,
                                        modifier = Modifier.fillMaxSize().clip(CircleShape),
                                        contentScale = ContentScale.Crop
                                    )
                                } else {
                                    Text(
                                        text = contact.initials,
                                        color = Color(0xFF1E293B),
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 15.sp
                                    )
                                }
                            }

                            // Info
                            Column(modifier = Modifier.weight(1f)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = contact.name,
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = TextDark,
                                        fontSize = 14.5.sp
                                    )
                                    Text(
                                        text = contact.time,
                                        fontSize = 11.sp,
                                        color = Color(0xFF64748B)
                                    )
                                }
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = contact.snippet,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = if (contact.isUnread) Color(0xFF0F172A) else Color(0xFF64748B),
                                    fontWeight = if (contact.isUnread) FontWeight.SemiBold else FontWeight.Normal,
                                    maxLines = 1,
                                    fontSize = 12.sp
                                )
                            }

                            if (contact.isUnread) {
                                Box(
                                    modifier = Modifier
                                        .size(8.dp)
                                        .background(OdishaMagenta, CircleShape)
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        // Real Interactive Chat Dialog
        activeContactDialog?.let { contact ->
            val chatMessages by viewModel.getChatMessages(contact.name).collectAsState(initial = emptyList())

            Dialog(
                onDismissRequest = { activeContactDialog = null },
                properties = DialogProperties(usePlatformDefaultWidth = false)
            ) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth(0.95f)
                        .height(580.dp)
                        .clip(RoundedCornerShape(24.dp)),
                    shape = RoundedCornerShape(24.dp),
                    color = PureWhite
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        // Header
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                val chatAvatarPath = contactAvatars[contact.name]
                                Box(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .clip(CircleShape)
                                        .background(contact.avatarColor),
                                    contentAlignment = Alignment.Center
                                ) {
                                    if (!chatAvatarPath.isNullOrEmpty() && File(chatAvatarPath).exists()) {
                                        AsyncImage(
                                            model = File(chatAvatarPath),
                                            contentDescription = contact.name,
                                            modifier = Modifier.fillMaxSize().clip(CircleShape),
                                            contentScale = ContentScale.Crop
                                        )
                                    } else {
                                        Text(
                                            text = contact.initials,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 14.sp,
                                            color = TextDark
                                        )
                                    }
                                }

                                Column {
                                    Text(
                                        text = contact.name,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 15.sp,
                                        color = TextDark
                                    )
                                    Text(
                                        text = "Online • EduLocker Network",
                                        fontSize = 11.sp,
                                        color = Color(0xFF16A34A)
                                    )
                                }
                            }

                            IconButton(onClick = { activeContactDialog = null }) {
                                Icon(
                                    imageVector = Icons.Filled.Close,
                                    contentDescription = "Close",
                                    tint = Color(0xFF64748B)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Message Feed
                        LazyColumn(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth()
                                .background(Color(0xFFF8FAFC), RoundedCornerShape(16.dp))
                                .padding(12.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            if (chatMessages.isEmpty()) {
                                item {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(top = 40.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "Start a discussion with ${contact.name}",
                                            fontSize = 12.sp,
                                            color = Color(0xFF94A3B8)
                                        )
                                    }
                                }
                            }
                            items(chatMessages) { msg ->
                                val isMe = msg.isFromUser
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = if (isMe) Arrangement.End else Arrangement.Start
                                ) {
                                    Surface(
                                        shape = RoundedCornerShape(
                                            topStart = 14.dp,
                                            topEnd = 14.dp,
                                            bottomStart = if (isMe) 14.dp else 2.dp,
                                            bottomEnd = if (isMe) 2.dp else 14.dp
                                        ),
                                        color = if (isMe) OdishaMagenta else PureWhite,
                                        shadowElevation = 1.dp
                                    ) {
                                        Text(
                                            text = msg.messageText,
                                            color = if (isMe) PureWhite else TextDark,
                                            fontSize = 13.sp,
                                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                                        )
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // Chat Input Box
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            OutlinedTextField(
                                value = currentMessageInput,
                                onValueChange = { currentMessageInput = it },
                                placeholder = { Text("Type a message...", fontSize = 13.sp, color = TextMuted) },
                                modifier = Modifier
                                    .weight(1f)
                                    .height(50.dp)
                                    .clip(RoundedCornerShape(25.dp)),
                                singleLine = true,
                                shape = RoundedCornerShape(25.dp),
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = Color(0xFFF1F5F9),
                                    unfocusedContainerColor = Color(0xFFF1F5F9),
                                    disabledContainerColor = Color(0xFFF1F5F9),
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent
                                )
                            )

                            Box(
                                modifier = Modifier
                                    .size(46.dp)
                                    .clip(CircleShape)
                                    .background(OdishaMagenta)
                                    .clickable {
                                        if (currentMessageInput.isNotBlank()) {
                                            viewModel.sendChatMessage(contact.name, currentMessageInput.trim())
                                            currentMessageInput = ""
                                        }
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.Send,
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
