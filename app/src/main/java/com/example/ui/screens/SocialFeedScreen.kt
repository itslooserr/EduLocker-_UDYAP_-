package com.example.ui.screens

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Navigation
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.ui.screens.social.AllDeadlinesDialog
import com.example.ui.screens.social.AllGKNewsDialog
import com.example.ui.screens.social.AllRevisionNotesDialog
import com.example.ui.screens.social.EntranceDeadlineDetailModal
import com.example.ui.screens.social.EntranceDeadlineItem
import com.example.ui.screens.social.GKArticleDetailModal
import com.example.ui.screens.social.GKArticleItem
import com.example.ui.screens.social.GKQuizQuestion
import com.example.ui.screens.social.RevisionNoteDetail
import com.example.ui.screens.social.RevisionNoteViewerModal
import com.example.ui.screens.social.ShortReelItem
import com.example.ui.screens.social.ShortsReelsViewerModal
import com.example.ui.screens.social.WikipediaArticleData
import com.example.ui.screens.social.WikipediaArticleViewerModal
import com.example.ui.screens.social.WikipediaSection
import com.example.ui.screens.social.YouTubeVideoData
import com.example.ui.screens.social.YouTubeVideoViewerModal
import com.example.ui.theme.CanvasCream
import com.example.ui.theme.OdishaMagenta
import com.example.ui.theme.PureWhite
import com.example.ui.theme.TextDark
import com.example.ui.viewmodel.EduLockerViewModel
import com.example.util.NotificationHelper
import kotlinx.coroutines.launch

// =========================================================================
// STORY DATA MODEL
// =========================================================================
data class StoryItem(
    val id: String,
    val authorName: String,
    val authorAvatar: String,
    val timeAgo: String,
    val isGovtOfIndia: Boolean,
    val govtNoticeTag: String? = null,
    val hasCta: Boolean = false,
    val ctaButtonLabel: String = "View Details",
    val title: String,
    val body: String,
    val bgGradient: List<Color>,
    val badgeLabel: String? = null
)

data class DiscussionPost(
    val id: String,
    val authorName: String,
    val authorAvatar: String,
    val category: String,
    val title: String,
    val repliesCount: Int,
    var likesCount: Int,
    val timeAgo: String
)

data class RevisionNote(
    val id: String,
    val subject: String,
    val title: String,
    val chapter: String,
    val bgGradient: List<Color>,
    val pagesCount: String
)

// =========================================================================
// SCREEN 35: Social Feed Screen (Exact Replica of 35.png with Clean Theme)
// =========================================================================
@Composable
fun SocialFeedScreen(
    viewModel: EduLockerViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    // 1. List of 8 rich stories with Govt entities (Official Government & Student Stories)
    val allStories = remember {
        listOf(
            StoryItem(
                id = "story_laxmikant",
                authorName = "Laxmikant Nayak",
                authorAvatar = "LN",
                timeAgo = "2h",
                isGovtOfIndia = false,
                hasCta = false,
                title = "HP LIFE Certification Complete!",
                body = "Cleared the Web Development Level 1 certification with top honors on EduLocker. Marksheet cryptographically anchored on Odisha Block-Locker!",
                bgGradient = listOf(Color(0xFF831843), Color(0xFFE11D48), Color(0xFF1E1B4B)),
                badgeLabel = "Student Achievement"
            ),
            StoryItem(
                id = "story_govt_india",
                authorName = "Govt. of India",
                authorAvatar = "🏛️",
                timeAgo = "Sponsored • Official",
                isGovtOfIndia = true,
                govtNoticeTag = "🇮🇳 Govt. of India • National Scholarship Portal Live",
                hasCta = true,
                ctaButtonLabel = "Apply Now",
                title = "National Scholarship Portal 2026-27",
                body = "Post-Matric & Merit-cum-Means applications are open for all Higher Secondary & College students. Fast-track Aadhaar DBT verification available inside EduLocker.",
                bgGradient = listOf(Color(0xFF831843), Color(0xFFE11D48), Color(0xFF1E1B4B)),
                badgeLabel = "OFFICIAL GAZETTE • GOVT. OF INDIA"
            ),
            StoryItem(
                id = "story_education_dept",
                authorName = "Ministry of Education",
                authorAvatar = "🎓",
                timeAgo = "1h",
                isGovtOfIndia = true,
                govtNoticeTag = "🇮🇳 Ministry of Education • APAAR One Nation One ID",
                hasCta = true,
                ctaButtonLabel = "Download ID",
                title = "APAAR Digital ID Card Release",
                body = "All students from Class 9 to 12 can now generate and store their 12-digit APAAR Card with QR code verification in their EduLocker vault.",
                bgGradient = listOf(Color(0xFF9D174D), Color(0xFFE11D48), Color(0xFF022C22)),
                badgeLabel = "CENTRAL BOARD NOTIFICATION"
            ),
            StoryItem(
                id = "story_odisha_higher_edu",
                authorName = "Odisha Higher Edu",
                authorAvatar = "🏛️",
                timeAgo = "3h",
                isGovtOfIndia = true,
                govtNoticeTag = "🏛️ Govt. of Odisha • Higher Education Dept.",
                hasCta = true,
                ctaButtonLabel = "Apply Scheme",
                title = "e-Medhabruti Merit Portal Open",
                body = "State Higher Education Department initiates online merit scholarship applications for +2 Junior, +3 Degree, and PG courses. Direct DBT disbursement.",
                bgGradient = listOf(Color(0xFF831843), Color(0xFFBE185D), Color(0xFF500724)),
                badgeLabel = "STATE SCHOLARSHIP PORTAL"
            ),
            StoryItem(
                id = "story_nta_portal",
                authorName = "NTA Exam Alerts",
                authorAvatar = "📝",
                timeAgo = "5h",
                isGovtOfIndia = true,
                govtNoticeTag = "🇮🇳 NTA • National Entrance Bulletin",
                hasCta = true,
                ctaButtonLabel = "View Schedule",
                title = "JEE Main & NEET 2027 Portal Schedule",
                body = "National Testing Agency releases information bulletins, chapter weightage, and computer-based mock papers for upcoming national entrances.",
                bgGradient = listOf(Color(0xFF9F1239), Color(0xFFE11D48), Color(0xFF1E1B4B)),
                badgeLabel = "NATIONAL TESTING AGENCY"
            ),
            StoryItem(
                id = "story_skill_india",
                authorName = "Skill India Mission",
                authorAvatar = "⚡",
                timeAgo = "6h",
                isGovtOfIndia = true,
                govtNoticeTag = "🇮🇳 Skill India • PMKVY 4.0 Free Tech Courses",
                hasCta = true,
                ctaButtonLabel = "Enroll Free",
                title = "PMKVY 4.0 Free Tech Certifications",
                body = "Enroll in zero-cost government recognized AI, Robotics, and Web Development certified courses with direct industry apprenticeships.",
                bgGradient = listOf(Color(0xFF881337), Color(0xFFBE123C), Color(0xFF0F172A)),
                badgeLabel = "SKILL INDIA MISSION"
            ),
            StoryItem(
                id = "story_ugc_india",
                authorName = "UGC India",
                authorAvatar = "🏛️",
                timeAgo = "7h",
                isGovtOfIndia = true,
                govtNoticeTag = "🇮🇳 UGC • National Credit Framework (NCrF)",
                hasCta = true,
                ctaButtonLabel = "Read Notice",
                title = "National Credit Framework & Degrees",
                body = "University Grants Commission enables seamless semester credit transfers between state universities using verified DigiLocker student vaults.",
                bgGradient = listOf(Color(0xFF701A75), Color(0xFFC026D3), Color(0xFF3B0764)),
                badgeLabel = "UNIVERSITY GRANTS COMMISSION"
            ),
            StoryItem(
                id = "story_pmo",
                authorName = "PMO India",
                authorAvatar = "🇮🇳",
                timeAgo = "8h",
                isGovtOfIndia = true,
                govtNoticeTag = "🇮🇳 PMO India • Pariksha Pe Charcha 2027",
                hasCta = true,
                ctaButtonLabel = "Register Free",
                title = "Pariksha Pe Charcha 2027",
                body = "Join Hon'ble Prime Minister for exclusive masterclass on exam stress management and STEM innovation. Direct student entries open on EduLocker.",
                bgGradient = listOf(Color(0xFF831843), Color(0xFFE11D48), Color(0xFF18181B)),
                badgeLabel = "NATIONAL YOUTH INITIATIVE"
            )
        )
    }

    // 2. YouTube-style Content Videos
    val xstreamVideos = remember {
        listOf(
            YouTubeVideoData(
                id = "vid_1",
                title = "Odisha State Scholarship 2026-27: Complete Application & Aadhaar KYC Walkthrough",
                channelName = "Education Dept. INDIA",
                channelAvatar = "🏛️",
                subscribers = "1.45M subscribers",
                views = "184K views",
                uploadDate = "2 days ago",
                durationText = "12:45",
                description = "Complete step-by-step video guide on submitting Post-Matric and Merit-cum-Means applications through EduLocker with automated UIDAI authentication.",
                tags = listOf("#OdishaScholarship", "#EduLocker", "#DBTIndia"),
                gradientColors = listOf(Color(0xFF0284C7), Color(0xFF0369A1), Color(0xFF0F172A)),
                iconEmoji = "🎓"
            ),
            YouTubeVideoData(
                id = "vid_2",
                title = "Parliamentary Education Committee: National Student Identity & Credit Framework",
                channelName = "Parliament India Live",
                channelAvatar = "🏛️",
                subscribers = "2.8M subscribers",
                views = "92K views",
                uploadDate = "Yesterday",
                durationText = "18:20",
                description = "Live address by Parliamentary standing committee on digital locker integration for high school and university marksheets across India.",
                tags = listOf("#ParliamentIndia", "#NEP2020", "#Education"),
                gradientColors = listOf(Color(0xFF334155), Color(0xFF1E293B), Color(0xFF0F172A)),
                iconEmoji = "🏛️"
            ),
            YouTubeVideoData(
                id = "vid_3",
                title = "State Science Olympiad Champions: Top Tips for Scoring Full Marks",
                channelName = "TOI Times of Education",
                channelAvatar = "⚡",
                subscribers = "680K subscribers",
                views = "310K views",
                uploadDate = "3 days ago",
                durationText = "15:10",
                description = "Interview with Odisha rank-1 toppers discussing problem-solving techniques in physics, chemistry, and algorithmic mathematics.",
                tags = listOf("#OlympiadToppers", "#ScienceExcellence", "#OdishaSTEM"),
                gradientColors = listOf(Color(0xFFEC4899), Color(0xFF8B5CF6), Color(0xFF1E1B4B)),
                iconEmoji = "🎉"
            ),
            YouTubeVideoData(
                id = "vid_4",
                title = "IIT Bhubaneswar Robotics Lab: Autonomous Drone Prototype Demonstration",
                channelName = "RoboTech Club BBS",
                channelAvatar = "🤖",
                subscribers = "420K subscribers",
                views = "78K views",
                uploadDate = "5 days ago",
                durationText = "10:35",
                description = "Engineering students demonstrate obstacle-avoidance AI navigation drone built with open source microcontrollers.",
                tags = listOf("#Robotics", "#DroneTech", "#Engineering"),
                gradientColors = listOf(Color(0xFF0F766E), Color(0xFF115E59), Color(0xFF134E4A)),
                iconEmoji = "🤖"
            ),
            YouTubeVideoData(
                id = "vid_5",
                title = "Complete Calculus Crash Course: Integration Shortcuts for State Exams",
                channelName = "Odisha Math Academy",
                channelAvatar = "📐",
                subscribers = "850K subscribers",
                views = "215K views",
                uploadDate = "1 week ago",
                durationText = "24:18",
                description = "Master Definite Integrals and Differential Equations in one master lecture with high-yield previous year questions.",
                tags = listOf("#Calculus", "#MathTricks", "#Class12Math"),
                gradientColors = listOf(Color(0xFF7C2D12), Color(0xFF9A3412), Color(0xFF431407)),
                iconEmoji = "🧮"
            )
        )
    }

    // 3. Shorts / Reels Vertical Videos (16 Diverse & Rich Educational Reels)
    val shortsList = remember {
        listOf(
            ShortReelItem(
                id = "short_1",
                title = "5 Secret Govt Scholarships every student must apply in 2026-27! 💸",
                creatorHandle = "@laxmikant_edu",
                creatorAvatar = "LN",
                initialLikes = 34200,
                initialComments = 1240,
                soundTrack = "Original Audio - Odisha Youth Hub",
                hashtags = "#Scholarships #EduLocker #StudentHacks",
                bgGradient = listOf(Color(0xFF57534E), Color(0xFF292524), Color(0xFF1C1917)),
                centralEmoji = "📜"
            ),
            ShortReelItem(
                id = "short_2",
                title = "Euler's Identity in 30 Seconds — The Most Beautiful Equation in Math! 🧮",
                creatorHandle = "@stem_odisha",
                creatorAvatar = "⚡",
                initialLikes = 52800,
                initialComments = 2410,
                soundTrack = "Math Beats - LoFi Study",
                hashtags = "#MathTricks #Olympiad #STEM",
                bgGradient = listOf(Color(0xFF1E3A8A), Color(0xFF1E1B4B), Color(0xFF0F172A)),
                centralEmoji = "📐"
            ),
            ShortReelItem(
                id = "short_3",
                title = "How APAAR Digital Card saves your time during college admissions! ⚡",
                creatorHandle = "@educreators_in",
                creatorAvatar = "🎓",
                initialLikes = 41900,
                initialComments = 890,
                soundTrack = "Tech Beat Synth - EduLocker",
                hashtags = "#APAAR #DigitalIndia #AdmissionGuide",
                bgGradient = listOf(Color(0xFF0284C7), Color(0xFF0369A1), Color(0xFF0C4A6E)),
                centralEmoji = "🆔"
            ),
            ShortReelItem(
                id = "short_4",
                title = "Top 3 Physics Formulas that always repeat in Board Exams! ⚡",
                creatorHandle = "@science_mastery",
                creatorAvatar = "🔬",
                initialLikes = 68400,
                initialComments = 3120,
                soundTrack = "Exam Booster Motivation",
                hashtags = "#Class12Physics #Boards2027 #ExamHacks",
                bgGradient = listOf(Color(0xFF14532D), Color(0xFF064E3B), Color(0xFF022C22)),
                centralEmoji = "⚡"
            ),
            ShortReelItem(
                id = "short_5",
                title = "Why Liquid Nitrogen freezes everyday things instantly — Live Lab Demo! ❄️",
                creatorHandle = "@chemistry_lab_in",
                creatorAvatar = "🧪",
                initialLikes = 38900,
                initialComments = 950,
                soundTrack = "Science Lab Vibes",
                hashtags = "#ChemistryFun #Cryogenics #STEMExperiment",
                bgGradient = listOf(Color(0xFF0E7490), Color(0xFF155E75), Color(0xFF164E63)),
                centralEmoji = "🧪"
            ),
            ShortReelItem(
                id = "short_6",
                title = "Memory Technique: The 20-20-20 rule for scoring 95%+ in state exams! 🧠",
                creatorHandle = "@topper_hacks",
                creatorAvatar = "💡",
                initialLikes = 81200,
                initialComments = 4210,
                soundTrack = "Focus Study Ambient",
                hashtags = "#StudyTips #MemoryHacks #Productivity",
                bgGradient = listOf(Color(0xFF581C87), Color(0xFF3B0764), Color(0xFF1E1B4B)),
                centralEmoji = "🧠"
            ),
            ShortReelItem(
                id = "short_7",
                title = "IIT Bhubaneswar Lunar Rover Prototype testing on simulated surface! 🌙",
                creatorHandle = "@space_robotics",
                creatorAvatar = "🚀",
                initialLikes = 46700,
                initialComments = 1120,
                soundTrack = "Cosmic Synthesizer Track",
                hashtags = "#IITBBS #SpaceTech #Chandrayaan",
                bgGradient = listOf(Color(0xFF18181B), Color(0xFF27272A), Color(0xFF09090B)),
                centralEmoji = "🚀"
            ),
            ShortReelItem(
                id = "short_8",
                title = "Active Recall vs Re-reading: The scientifically proven study method 📖",
                creatorHandle = "@smart_learner",
                creatorAvatar = "📚",
                initialLikes = 55400,
                initialComments = 1780,
                soundTrack = "Deep Work Beat",
                hashtags = "#LearningTechniques #EvidenceBasedStudy",
                bgGradient = listOf(Color(0xFF831843), Color(0xFF701A75), Color(0xFF4A044E)),
                centralEmoji = "📖"
            ),
            ShortReelItem(
                id = "short_9",
                title = "How Indian Constitution protects students against discrimination! 🏛️",
                creatorHandle = "@civics_simplified",
                creatorAvatar = "⚖️",
                initialLikes = 29400,
                initialComments = 640,
                soundTrack = "Patriotic Instrumental",
                hashtags = "#ConstitutionOfIndia #StudentRights #UPSC",
                bgGradient = listOf(Color(0xFF7C2D12), Color(0xFF451A03), Color(0xFF1C1917)),
                centralEmoji = "⚖️"
            ),
            ShortReelItem(
                id = "short_10",
                title = "Write your first Python AI chatbot in under 60 seconds! 🤖",
                creatorHandle = "@code_odisha",
                creatorAvatar = "💻",
                initialLikes = 93100,
                initialComments = 5120,
                soundTrack = "Cyber Tech Flow",
                hashtags = "#PythonCoding #ArtificialIntelligence #Coding101",
                bgGradient = listOf(Color(0xFF064E3B), Color(0xFF022C22), Color(0xFF0F172A)),
                centralEmoji = "🤖"
            ),
            ShortReelItem(
                id = "short_11",
                title = "DNA Double Helix & Replication Animation in 45 seconds! 🔬",
                creatorHandle = "@biology_mastery",
                creatorAvatar = "🧬",
                initialLikes = 62700,
                initialComments = 2150,
                soundTrack = "Bio Discovery Flow",
                hashtags = "#NEET2027 #BiologyNotes #Genetics",
                bgGradient = listOf(Color(0xFF047857), Color(0xFF065F46), Color(0xFF022C22)),
                centralEmoji = "🧬"
            ),
            ShortReelItem(
                id = "short_12",
                title = "Why Odisha coastline has the most efficient offshore wind power! 🍃",
                creatorHandle = "@green_energy_in",
                creatorAvatar = "🌍",
                initialLikes = 36800,
                initialComments = 820,
                soundTrack = "Eco Acoustic Rhythm",
                hashtags = "#CleanTech #OdishaRenewable #STEMGreen",
                bgGradient = listOf(Color(0xFF15803D), Color(0xFF166534), Color(0xFF14532D)),
                centralEmoji = "🍃"
            ),
            ShortReelItem(
                id = "short_13",
                title = "Olympiad Math Trick: How to find remainder of 2^2026 / 7 in 10s! ⏱️",
                creatorHandle = "@olympiad_math",
                creatorAvatar = "🏅",
                initialLikes = 74300,
                initialComments = 3890,
                soundTrack = "Speed Math Ticking",
                hashtags = "#ModularArithmetic #MathOlympiad #Shortcuts",
                bgGradient = listOf(Color(0xFF1E3A8A), Color(0xFF312E81), Color(0xFF1E1B4B)),
                centralEmoji = "🏅"
            ),
            ShortReelItem(
                id = "short_14",
                title = "English Fluency: 5 smart words to replace 'Very Good' in answers ✨",
                creatorHandle = "@vocab_boost",
                creatorAvatar = "✍️",
                initialLikes = 49100,
                initialComments = 1430,
                soundTrack = "Smooth Jazz Study",
                hashtags = "#EnglishFluency #Vocabulary #CommunicationSkills",
                bgGradient = listOf(Color(0xFF854D0E), Color(0xFF713F12), Color(0xFF422006)),
                centralEmoji = "✍️"
            ),
            ShortReelItem(
                id = "short_15",
                title = "Zero Fee Student Scholarships: How to get certified with no cost! 🎯",
                creatorHandle = "@edu_grants",
                creatorAvatar = "🏆",
                initialLikes = 88500,
                initialComments = 4690,
                soundTrack = "Victory Theme Uplift",
                hashtags = "#EduLocker #HigherEducation #MeritScholarship",
                bgGradient = listOf(Color(0xFF991B1B), Color(0xFF7F1D1D), Color(0xFF450A0A)),
                centralEmoji = "🎯"
            ),
            ShortReelItem(
                id = "short_16",
                title = "Quantum Computing in simple terms: Qubits vs Binary Bits! 💻",
                creatorHandle = "@future_tech_in",
                creatorAvatar = "🔍",
                initialLikes = 67200,
                initialComments = 2810,
                soundTrack = "Quantum Synth Wave",
                hashtags = "#QuantumPhysics #ComputerScience #TechExplainer",
                bgGradient = listOf(Color(0xFF3730A3), Color(0xFF1E1B4B), Color(0xFF0F172A)),
                centralEmoji = "💻"
            )
        )
    }

    // 4. Wikipedia Scholarly Articles
    val wikiArticles = remember {
        listOf(
            WikipediaArticleData(
                id = "art_nsp",
                title = "National Scholarship Portal",
                subtitle = "From EduLocker Academic Encyclopedia, the verified educational repository",
                lastModified = "26 September 2026",
                readingTime = "4 min read",
                infoboxData = mapOf(
                    "Program Type" to "Government Student Welfare Initiative",
                    "Jurisdiction" to "Republic of India (Pan-India)",
                    "Administering Body" to "Ministry of Electronics & IT / Ministry of Education",
                    "Target Groups" to "Pre-Matric, Post-Matric & Higher Education",
                    "Disbursement" to "Direct Benefit Transfer (Aadhaar DBT)",
                    "Digital Security" to "Block-Locker cryptographic verification"
                ),
                sections = listOf(
                    WikipediaSection(
                        title = "1. Overview & Objectives",
                        paragraphs = listOf(
                            "The National Scholarship Portal (NSP) is a dedicated digital platform created to provide a unified electronic ecosystem for the implementation and disbursement of central, state, and UGC-AICTE scholarships across India.",
                            "The system automates the lifecycle of student scholarship processing, from application submission, institutional bio-metric verification, district and state officer sanitization, to Aadhaar-seeded Direct Benefit Transfer (DBT) bank deposits."
                        ),
                        quote = "Ensuring fair, transparent, and prompt educational financial assistance directly into student bank accounts without bureaucratic leakage."
                    ),
                    WikipediaSection(
                        title = "2. Integration with EduLocker & APAAR",
                        paragraphs = listOf(
                            "Under the National Education Policy guidelines, NSP is natively integrated with EduLocker and the Automated Permanent Academic Account Registry (APAAR).",
                            "Students can authenticate their documents without submitting physical paper photocopies. Grade sheets, caste certificates, income credentials, and UIDAI e-KYC are verified instantaneously through cryptographic hashes."
                        )
                    ),
                    WikipediaSection(
                        title = "3. Eligibility & Application Deadlines",
                        paragraphs = listOf(
                            "Applicants must maintain an aggregate minimum of 50% marks in the preceding qualifying examination and adhere to the annual family income threshold specified per scholarship category.",
                            "Odisha state specific schemes, including the Medhabruti and Post-Matric ST/SC incentives, run concurrently with central scholarship timelines."
                        )
                    )
                ),
                references = listOf(
                    "Ministry of Electronics and Information Technology (MeitY), Government of India: 'National e-Governance Plan on Education'.",
                    "Odisha Higher Education Department Notification No. HE-SCH-2026/8410.",
                    "DBT Bharat Direct Benefit Transfer Performance Report 2025-26."
                )
            ),
            WikipediaArticleData(
                id = "art_olympiad",
                title = "State Science & Mathematics Olympiad (Odisha)",
                subtitle = "From EduLocker Academic Encyclopedia, the verified educational repository",
                lastModified = "24 September 2026",
                readingTime = "5 min read",
                infoboxData = mapOf(
                    "Competition" to "Junior & Senior Science Olympiad",
                    "Conducting Body" to "Odisha State Academic Council & IIT BBS",
                    "Eligibility" to "Classes 8 to 12",
                    "Format" to "Tier 1 District, Tier 2 State Finals",
                    "Awards" to "Gold Medals, Research Grants & Merit Certificates"
                ),
                sections = listOf(
                    WikipediaSection(
                        title = "1. Background & Scope",
                        paragraphs = listOf(
                            "The Odisha State Science Olympiad is an annual premier competitive assessment designed to identify and mentor exceptional scientific, analytical, and mathematical talent among secondary and higher secondary students.",
                            "Organized in collaboration with premier Indian institutes like IIT Bhubaneswar and NISER, the competition emphasizes conceptual clarity, problem solving, and experimental laboratory aptitude."
                        )
                    ),
                    WikipediaSection(
                        title = "2. Examination Architecture & Evaluation",
                        paragraphs = listOf(
                            "The competition is held across two distinct stages: Stage I consists of computer-adaptive multiple choice and numerical value questions hosted on the EduLocker Testing Engine.",
                            "Stage II candidates participate in hands-on physics and chemistry laboratory prototyping alongside advanced computational algorithm design challenges."
                        )
                    )
                ),
                references = listOf(
                    "Council of Higher Secondary Education (CHSE) Odisha: 'Olympiad Guidelines & Syllabus'.",
                    "National Science Education Council Research Bulletin 2026."
                )
            ),
            WikipediaArticleData(
                id = "art_apaar",
                title = "APAAR (Automated Permanent Academic Account Registry)",
                subtitle = "One Nation One Student ID System in India",
                lastModified = "20 September 2026",
                readingTime = "6 min read",
                infoboxData = mapOf(
                    "Initiative" to "One Nation One Student ID",
                    "Agency" to "Department of School Education & Literacy, MoE",
                    "Format" to "12-Digit Unique Alphanumeric ID",
                    "Storage" to "DigiLocker & EduLocker Vault",
                    "Use Cases" to "Admissions, Credit Transfers, Marksheet Validation"
                ),
                sections = listOf(
                    WikipediaSection(
                        title = "1. Background & Legal Framework",
                        paragraphs = listOf(
                            "APAAR is a digitally verifiable identification ecosystem designed under the National Credit Framework (NCrF) to provide each student in India a single lifelong academic identity.",
                            "All scholastic records, degree certificates, co-curricular accolades, and skill badges are mapped seamlessly to this ID."
                        )
                    )
                ),
                references = listOf(
                    "Ministry of Education, Government of India: 'APAAR User Guide & Security Whitepaper'."
                )
            )
        )
    }

    // 5. Discussion Posts for Campus Pulse section
    val discussions = remember {
        mutableStateListOf(
            DiscussionPost(
                id = "disc_1",
                authorName = "Aditi Panigrahi (Class 12)",
                authorAvatar = "AP",
                category = "Board Exams 2027",
                title = "Which chapters in Organic Chemistry have the highest numerical weightage for state boards?",
                repliesCount = 28,
                likesCount = 45,
                timeAgo = "1h ago"
            ),
            DiscussionPost(
                id = "disc_2",
                authorName = "Rakesh Patra (KIIT)",
                authorAvatar = "RP",
                category = "Scholarship Help",
                title = "Does Medhabruti scholarship require income certificate renewal every academic year?",
                repliesCount = 14,
                likesCount = 32,
                timeAgo = "3h ago"
            ),
            DiscussionPost(
                id = "disc_3",
                authorName = "Odisha STEM Club",
                authorAvatar = "⚡",
                category = "Olympiad Prep",
                title = "Weekly Math Challenge: Share your fastest approach to solving quadratic congruence!",
                repliesCount = 52,
                likesCount = 89,
                timeAgo = "5h ago"
            )
        )
    }

    // 6. Rich Smart Revision Notes (Clickable for full reader view)
    val revisionNotes = remember {
        listOf(
            RevisionNoteDetail(
                id = "note_1",
                subject = "Physics",
                title = "Electromagnetism & Wave Optics Master Cheatsheet",
                chapter = "Chapters 4 to 10 (Electrodynamics & Optics)",
                pagesCount = "4 Pages PDF",
                formulaCount = "28 Key Formulas",
                bgGradient = listOf(Color(0xFF0369A1), Color(0xFF0284C7)),
                overview = "Complete mathematical formulations and concept blueprints covering Gauss's Law, Biot-Savart, Faraday's Induction, Maxwell's equations, Interference & Diffraction.",
                keyFormulas = listOf(
                    "Gauss's Law" to "∮ E · dA = Q_enclosed / ε₀",
                    "Magnetic Force (Lorentz)" to "F = q(E + v × B)",
                    "Faraday's Law of Induction" to "ε = -dΦ_B / dt = -N (dΦ / dt)",
                    "Young's Double Slit Fringe Width" to "β = (λ · D) / d",
                    "Brewster's Law (Polarization)" to "μ = tan(θ_p)"
                ),
                highYieldTips = listOf(
                    "Always check units: 1 Tesla = 10⁴ Gauss. Remember direction with right-hand palm rule.",
                    "In Optics, fringe width is inversely proportional to slit separation d.",
                    "Lenz's Law is a direct manifestation of the Law of Conservation of Energy."
                ),
                importantConcepts = listOf("Displacement Current", "Rayleigh's Criterion", "Self and Mutual Inductance")
            ),
            RevisionNoteDetail(
                id = "note_2",
                subject = "Mathematics",
                title = "Calculus Master Summary: Derivatives & Integrals",
                chapter = "Differential & Integral Calculus (Chapters 5-9)",
                pagesCount = "6 Pages PDF",
                formulaCount = "42 Integrals & Rules",
                bgGradient = listOf(Color(0xFF831843), Color(0xFFE11D48)),
                overview = "Comprehensive high-yield reference guide for limits, chain rule derivatives, standard indefinite integrals, definite integral properties, and differential equations.",
                keyFormulas = listOf(
                    "Integration by Parts" to "∫ u v dx = u ∫ v dx - ∫ [u' (∫ v dx)] dx",
                    "Standard Integral: 1/(x²+a²)" to "∫ dx / (x² + a²) = (1/a) arctan(x/a) + C",
                    "Definite Integral King's Property" to "∫ [a to b] f(x) dx = ∫ [a to b] f(a + b - x) dx",
                    "Linear Differential Equation" to "dy/dx + Py = Q  =>  y · (I.F.) = ∫ Q · (I.F.) dx + C",
                    "Leibniz Integral Rule" to "d/dx [∫ [u(x) to v(x)] f(t) dt] = f(v) v' - f(u) u'"
                ),
                highYieldTips = listOf(
                    "Use King's property first when dealing with symmetric trigonometry bounds like [0, π/2].",
                    "Don't forget the arbitrary constant '+ C' for all indefinite integral questions.",
                    "For homogeneous differential equations, substitute y = vx before separating variables."
                ),
                importantConcepts = listOf("L'Hôpital's Rule", "Area under Curves", "Homogeneous Differential Equations")
            ),
            RevisionNoteDetail(
                id = "note_3",
                subject = "Chemistry",
                title = "Named Organic Reactions & Reagent Cheat-Sheet",
                chapter = "Organic Chemistry (Aldehydes, Ketones & Amines)",
                pagesCount = "5 Pages PDF",
                formulaCount = "35 Reactions",
                bgGradient = listOf(Color(0xFF065F46), Color(0xFF059669)),
                overview = "Direct step-by-step mechanisms, reaction conditions, catalysts, and conversions for Aldol Condensation, Cannizzaro, Sandmeyer, Reimer-Tiemann, and Gabriel Phthalimide.",
                keyFormulas = listOf(
                    "Aldol Condensation" to "2 CH₃CHO + dil. NaOH → CH₃-CH(OH)-CH₂-CHO → CH₃-CH=CH-CHO (But-2-enal)",
                    "Cannizzaro Reaction" to "2 HCHO + conc. NaOH → CH₃OH + HCOONa (Self redox without α-H)",
                    "Sandmeyer Reaction" to "Ar-N₂⁺Cl⁻ + CuCl/HCl → Ar-Cl + N₂",
                    "Reimer-Tiemann Reaction" to "Phenol + CHCl₃ + aq. NaOH → Salicylaldehyde",
                    "Hinsberg Reagent Test" to "Ph-SO₂Cl distinguishes 1°, 2°, 3° amines based on solubility"
                ),
                highYieldTips = listOf(
                    "Benzaldehyde gives Cannizzaro reaction because it lacks α-hydrogen atoms.",
                    "In electrophilic aromatic substitution, -OH and -NH₂ are strongly activating ortho/para directors.",
                    "Lucas reagent (anhydrous ZnCl₂ + conc. HCl) tests primary, secondary, and tertiary alcohols based on turbidity time."
                ),
                importantConcepts = listOf("Markovnikov vs Anti-Markovnikov Rule", "Grignard Reagent synthesis", "Carbylamine Test")
            ),
            RevisionNoteDetail(
                id = "note_4",
                subject = "Biology",
                title = "Genetics, DNA Replication & Molecular Biology",
                chapter = "Molecular Basis of Inheritance & Biotechnology",
                pagesCount = "4 Pages PDF",
                formulaCount = "18 Diagrams & Cycles",
                bgGradient = listOf(Color(0xFF581C87), Color(0xFF7E22CE)),
                overview = "Complete blueprint covering Watson-Crick DNA double helix model, semiconservative replication, transcription unit, genetic code codons, and Lac Operon regulation.",
                keyFormulas = listOf(
                    "Chargaff's Rule" to "A + G = T + C (Purines = Pyrimidines; A/T = 1, G/C = 1)",
                    "Central Dogma" to "DNA --(Transcription)--> mRNA --(Translation)--> Protein",
                    "Initiation & Stop Codons" to "Start: AUG (Methionine) | Stop: UAA, UAG, UGA",
                    "Lac Operon Inducer" to "Lactose binds repressor protein → Operator unblocked → transcription active",
                    "PCR Amplification" to "Copies = 2ⁿ (where n = number of thermal cycles)"
                ),
                highYieldTips = listOf(
                    "DNA polymerase synthesizes only in the 5' → 3' direction. Lagging strand produces Okazaki fragments.",
                    "Genetic code is unambiguous, universal, degenerate, and non-overlapping.",
                    "Meselson-Stahl experiment used heavy isotope ¹⁵N to prove semiconservative DNA replication."
                ),
                importantConcepts = listOf("Lac Operon Regulation", "Hardy-Weinberg Equilibrium", "Recombinant DNA Tools")
            ),
            RevisionNoteDetail(
                id = "note_5",
                subject = "Computer Science",
                title = "Python Data Structures & SQL Database Cheatsheet",
                chapter = "Python OOP, File Handling & MySQL Commands",
                pagesCount = "3 Pages PDF",
                formulaCount = "25 Commands & Code Snippets",
                bgGradient = listOf(Color(0xFF1E293B), Color(0xFF334155)),
                overview = "Quick code syntax reference for Python Lists, Tuples, Dictionaries, Stack implementation, CSV text file reading/writing, and MySQL DDL/DML queries.",
                keyFormulas = listOf(
                    "Stack Push & Pop" to "stack.append(item) | item = stack.pop() if len(stack) > 0 else None",
                    "CSV DictReader" to "with open('file.csv', 'r') as f: reader = csv.DictReader(f)",
                    "SQL Aggregate Group By" to "SELECT dept, COUNT(*), AVG(salary) FROM emp GROUP BY dept HAVING COUNT(*) > 2;",
                    "SQL Foreign Key" to "FOREIGN KEY (student_id) REFERENCES Students(id) ON DELETE CASCADE",
                    "Time Complexity" to "Binary Search: O(log n) | Quick Sort avg: O(n log n) | Dict lookup: O(1)"
                ),
                highYieldTips = listOf(
                    "In MySQL, WHERE filters rows before grouping; HAVING filters aggregated groups after GROUP BY.",
                    "Tuples and Strings in Python are immutable; Lists and Dictionaries are mutable.",
                    "Always close database connections using 'with' context manager to prevent resource leaks."
                ),
                importantConcepts = listOf("Relational Algebra Joins", "Exception Handling in Python", "Stack & Queue Data Structures")
            ),
            RevisionNoteDetail(
                id = "note_6",
                subject = "English",
                title = "Formal Letters, Notice Writing & Grammar Master",
                chapter = "Advanced Writing Skills & Applied Grammar",
                pagesCount = "3 Pages PDF",
                formulaCount = "15 Formats & Rules",
                bgGradient = listOf(Color(0xFF7C2D12), Color(0xFFC2410C)),
                overview = "Standard marking scheme formats for Official Letters to Editor, Job Applications with Bio-data, Event Notices, Article writing, and Active/Passive voice transformations.",
                keyFormulas = listOf(
                    "Notice Format" to "BOX [Org Name -> NOTICE -> Date -> Heading -> Body (50 words) -> Name & Designation]",
                    "Letter to Editor" to "Sender's Addr -> Date -> Receiver's Addr -> Subject -> Salutation -> 3-Para Body -> Compl Close",
                    "Job Bio-Data" to "Personal details -> Educational Qualifications table -> Experience -> References (2)",
                    "Report Writing" to "Headline -> Byline -> Place, Date -> Lead Para -> Detail Para -> Conclusion",
                    "Reported Speech" to "Present Tenses shift to corresponding Past; pronouns shift relative to speaker"
                ),
                highYieldTips = listOf(
                    "Always draw a neat 4-sided box around a Notice for full format marks.",
                    "Maintain formal tone: Avoid contractions (use 'do not' instead of 'don't').",
                    "Keep Notice strictly under 50 words to avoid negative word-limit deductions."
                ),
                importantConcepts = listOf("Reported Speech Shift Rules", "Subject-Verb Agreement", "Analytical Paragraphs")
            )
        )
    }

    // 7. Daily GK & Current Affairs Items (Clickable for full breakdown & quiz)
    val gkNewsList = remember {
        listOf(
            GKArticleItem(
                id = "gk_1",
                category = "SCIENCE & TECH",
                title = "Bhubaneswar Science Center inaugurates Quantum Simulation Lab for Class 11-12",
                date = "27 Sept 2026",
                source = "Odisha Science & Tech Dept.",
                summary = "The State Department of Science and Technology has inaugurated a quantum computing and photonics interactive simulation laboratory in Bhubaneswar. Over 10,000 higher secondary students will receive hands-on training on quantum algorithms.",
                keyBulletPoints = listOf(
                    "First state-funded school-level quantum learning lab in Eastern India.",
                    "Equipped with interactive qubit superposition and entanglement simulators.",
                    "Collaboration with IIT Bhubaneswar and NISER faculty mentors.",
                    "Free practical workshops for government school merit students."
                ),
                examRelevance = "High relevance for State Science Olympiad, JEE Main Modern Physics & OPSC General Science.",
                quizQuestion = GKQuizQuestion(
                    question = "Where was Eastern India's first state-funded school quantum simulation lab inaugurated?",
                    options = listOf("Cuttack", "Bhubaneswar", "Rourkela", "Sambalpur"),
                    correctIndex = 1,
                    explanation = "The Quantum Simulation Lab was inaugurated in Bhubaneswar by the Odisha Department of Science and Technology in collaboration with premier research institutes."
                )
            ),
            GKArticleItem(
                id = "gk_2",
                category = "ACADEMICS",
                title = "CHSE Odisha releases revised sample papers & marking schemes for 2026-27 Board Exams",
                date = "27 Sept 2026",
                source = "CHSE Odisha Official",
                summary = "The Council of Higher Secondary Education (CHSE) Odisha has published chapter-wise blueprint sample question papers incorporating NEP 2020 competency-based questions for Arts, Science, and Commerce streams.",
                keyBulletPoints = listOf(
                    "30% of total marks now allocated to application & competency-based questions.",
                    "Multiple-choice section enhanced to 20 marks with step-by-step automated evaluation.",
                    "Sample answer sheets available directly inside EduLocker Vault for student download."
                ),
                examRelevance = "Essential for all Class 12 CHSE Board examinees for upcoming practical and theory exams.",
                quizQuestion = GKQuizQuestion(
                    question = "What percentage of board exam questions in the new CHSE format are competency-based?",
                    options = listOf("15%", "20%", "30%", "50%"),
                    correctIndex = 2,
                    explanation = "Under the revised NEP 2020 framework, CHSE Odisha has allocated 30% of question weightage to real-world competency and conceptual application."
                )
            ),
            GKArticleItem(
                id = "gk_3",
                category = "DIGITAL INDIA",
                title = "Over 1.2 Crore students complete APAAR digital ID binding across India on EduLocker",
                date = "27 Sept 2026",
                source = "Ministry of Education & MeitY",
                summary = "The Automated Permanent Academic Account Registry (APAAR) reached a historic milestone with over 1.2 Crore students successfully generating their 12-digit verifiable digital ID linked to DigiLocker and EduLocker vaults.",
                keyBulletPoints = listOf(
                    "APAAR serves as 'One Nation, One Student ID' across school and university levels.",
                    "Automates scholarship verification and credit accumulation under National Credit Framework (NCrF).",
                    "Cryptographic QR code verification prevents marksheet forgery and tampering."
                ),
                examRelevance = "Important for National Policy topics in UPSC, OPSC, and Current Affairs assessments.",
                quizQuestion = GKQuizQuestion(
                    question = "How many digits does the unique APAAR student ID contain?",
                    options = listOf("10 digits", "12 digits", "14 digits", "16 digits"),
                    correctIndex = 1,
                    explanation = "The APAAR (Automated Permanent Academic Account Registry) card features a unique 12-digit lifelong student identification number."
                )
            ),
            GKArticleItem(
                id = "gk_4",
                category = "SPACE & ISRO",
                title = "ISRO announces Young Scientist Programme (YUVIKA 2027) registrations for Class 9 students",
                date = "26 Sept 2026",
                source = "ISRO Media Cell",
                summary = "Indian Space Research Organisation (ISRO) is organizing the YUVIKA residential program at 7 centers across India to impart basic space science, satellite design, and rocketry knowledge to young students.",
                keyBulletPoints = listOf(
                    "Special weightage given to students studying in rural and government schools.",
                    "Top selected candidates visit Sriharikota rocket launching spaceport.",
                    "Fully funded 2-week residential training covering planetary astronomy and AI."
                ),
                examRelevance = "Frequent topic in National STEM Olympiads, NTSE, and General Knowledge quizzes.",
                quizQuestion = GKQuizQuestion(
                    question = "What is the full name of ISRO's student initiative abbreviated as YUVIKA?",
                    options = listOf(
                        "Yuva Vigyan Kendra",
                        "Yuva Vigyani Karyakram",
                        "Yuva Vikas Kosh",
                        "Yoddha Vigyan Mission"
                    ),
                    correctIndex = 1,
                    explanation = "YUVIKA stands for 'Yuva Vigyani Karyakram' (Young Scientist Programme), organized annually by ISRO."
                )
            ),
            GKArticleItem(
                id = "gk_5",
                category = "NATIONAL POLICY",
                title = "MoE mandates Zero Redundancy e-KYC for all Central & State Scholarship disbursements",
                date = "25 Sept 2026",
                source = "Ministry of Electronics & IT",
                summary = "Under new e-Governance directives, students will no longer need to upload physical income, caste, or marksheet certificates repeatedly. Instant Aadhaar DBT authentication will verify eligibility in under 5 seconds.",
                keyBulletPoints = listOf(
                    "Integrates National Scholarship Portal (NSP) directly with state repositories.",
                    "Direct Benefit Transfer processing speed increased by 400%.",
                    "Zero bank leakage and elimination of duplicate applicant submissions."
                ),
                examRelevance = "Key topic in Digital Governance, Direct Benefit Transfer (DBT), and Public Policy.",
                quizQuestion = GKQuizQuestion(
                    question = "What is the primary mechanism used for direct electronic scholarship deposits into student accounts?",
                    options = listOf("NEFT Cheque", "Aadhaar DBT (Direct Benefit Transfer)", "Postal Money Order", "Manual Cash Voucher"),
                    correctIndex = 1,
                    explanation = "Aadhaar-seeded Direct Benefit Transfer (DBT) ensures direct, immediate electronic transfer of scholarship grants into verified bank accounts."
                )
            ),
            GKArticleItem(
                id = "gk_6",
                category = "ENVIRONMENT",
                title = "Odisha Coastal Offshore Wind Energy project wins National Renewable Excellence Award",
                date = "24 Sept 2026",
                source = "Ministry of New & Renewable Energy",
                summary = "Odisha's offshore clean wind energy turbine installations along the Puri-Ganjam coastline have been awarded the National Clean Tech Gold Trophy for sustainable maritime power generation.",
                keyBulletPoints = listOf(
                    "Generates 450 MW of zero-emission clean electrical energy.",
                    "Powers over 800 coastal high schools with 24x7 solar-wind hybrid smart classrooms.",
                    "Serves as an experimental field research site for engineering students."
                ),
                examRelevance = "Environmental Science, Clean Energy initiatives in Geography and State GK.",
                quizQuestion = GKQuizQuestion(
                    question = "Which coastline region of Odisha hosts the award-winning offshore renewable turbine project?",
                    options = listOf("Puri-Ganjam Coastline", "Digha Coast", "Paradip Industrial Port", "Chandipur Range"),
                    correctIndex = 0,
                    explanation = "The Puri-Ganjam offshore wind project harnesses high maritime wind velocity to generate 450 MW of clean electricity."
                )
            )
        )
    }

    // 8. Entrance & Scholarship Deadlines (Clickable for full details & reminders)
    val entranceDeadlines = remember {
        listOf(
            EntranceDeadlineItem(
                id = "dl_1",
                title = "State Science & Mathematics Olympiad 2026-27",
                category = "OLYMPIAD",
                conductingBody = "Odisha State Academic Council & IIT BBS",
                lastDate = "14 Oct 2026",
                daysRemaining = "17 Days Left",
                examDate = "08 Nov 2026 (Tier 1 CBT)",
                eligibility = "Students currently enrolled in Classes 8 to 12 in recognized Odisha schools.",
                feeDetails = "Zero Fee (Sponsored by State STEM Innovation Fund)",
                documentsRequired = listOf("School Bonafide Certificate", "Previous Year Marksheet", "APAAR Digital Student ID"),
                portalUrl = "olympiad.odisha.gov.in",
                isUrgent = true
            ),
            EntranceDeadlineItem(
                id = "dl_2",
                title = "NSP National Merit-cum-Means & Post-Matric Scholarship",
                category = "GOVT SCHOLARSHIP",
                conductingBody = "Ministry of Education & MeitY, Govt. of India",
                lastDate = "31 Oct 2026",
                daysRemaining = "34 Days Left",
                examDate = "Merit List on 25 Nov 2026",
                eligibility = "Class 11, 12, Polytechnic & University degree students with annual family income ≤ ₹2.5 Lakhs.",
                feeDetails = "Free to apply (Up to ₹50,000/year annual scholarship grant)",
                documentsRequired = listOf("Aadhaar Card with Mobile Link", "Income Certificate", "Caste Certificate (if applicable)", "Bank Passbook"),
                portalUrl = "scholarships.gov.in",
                isUrgent = false
            ),
            EntranceDeadlineItem(
                id = "dl_3",
                title = "Odisha e-Medhabruti Junior & Degree Merit Scheme",
                category = "STATE SCHOLARSHIP",
                conductingBody = "Higher Education Department, Govt. of Odisha",
                lastDate = "15 Nov 2026",
                daysRemaining = "49 Days Left",
                examDate = "Direct DBT on 20 Dec 2026",
                eligibility = "Minimum 60% aggregate in Class 10/12 exams; resident of Odisha.",
                feeDetails = "Free to apply (₹10,000/yr for +2 Junior; ₹20,000/yr for Degree)",
                documentsRequired = listOf("Odisha Resident Certificate", "10th/12th Marksheet", "Income Certificate", "Aadhaar Card"),
                portalUrl = "scholarship.odisha.gov.in",
                isUrgent = false
            ),
            EntranceDeadlineItem(
                id = "dl_4",
                title = "JEE (Main) 2027 Session 1 Entrance Examination",
                category = "ENGINEERING ENTRANCE",
                conductingBody = "National Testing Agency (NTA)",
                lastDate = "25 Dec 2026",
                daysRemaining = "89 Days Left",
                examDate = "24 Jan to 01 Feb 2027",
                eligibility = "Passed or appearing in Class 12 / equivalent with Physics, Mathematics & Chemistry.",
                feeDetails = "₹1,000 (General/OBC) | ₹500 (SC/ST/PwD/Female)",
                documentsRequired = listOf("Passport Size Photo", "Signature Scan", "Category Certificate", "Class 10 Certificate"),
                portalUrl = "jeemain.nta.nic.in",
                isUrgent = false
            ),
            EntranceDeadlineItem(
                id = "dl_5",
                title = "NEET (UG) 2027 Medical Entrance Registration",
                category = "MEDICAL ENTRANCE",
                conductingBody = "National Testing Agency (NTA)",
                lastDate = "10 Jan 2027",
                daysRemaining = "105 Days Left",
                examDate = "02 May 2027",
                eligibility = "Passed/Appearing in 10+2 with Physics, Chemistry, Biology/Biotech and English.",
                feeDetails = "₹1,700 (General) | ₹1,600 (EWS/OBC) | ₹1,000 (SC/ST)",
                documentsRequired = listOf("Aadhaar Card", "Thumb Impression", "Class 10/12 Marksheets", "Postcard Size Photo"),
                portalUrl = "neet.nta.nic.in",
                isUrgent = false
            ),
            EntranceDeadlineItem(
                id = "dl_6",
                title = "CHSE Odisha Higher Secondary Board Practical Examination",
                category = "BOARD PRACTICALS",
                conductingBody = "Council of Higher Secondary Education, Odisha",
                lastDate = "12 Jan 2027",
                daysRemaining = "107 Days Left",
                examDate = "18 Jan to 05 Feb 2027",
                eligibility = "All regular and ex-regular Class 12 Science & Vocational stream candidates.",
                feeDetails = "Included in CHSE Board Examination Fee",
                documentsRequired = listOf("CHSE Admit Card", "Laboratory Practical Record Notebook", "APAAR ID Card"),
                portalUrl = "chseodisha.nic.in",
                isUrgent = false
            ),
            EntranceDeadlineItem(
                id = "dl_7",
                title = "CUET (UG) 2027 Central Universities Common Entrance Test",
                category = "UNIVERSITY ADMISSION",
                conductingBody = "National Testing Agency & UGC",
                lastDate = "28 Feb 2027",
                daysRemaining = "154 Days Left",
                examDate = "15 May to 31 May 2027",
                eligibility = "Class 12 candidates seeking admission to Delhi University, BHU, Central Univ of Odisha & 250+ colleges.",
                feeDetails = "₹750 to ₹1,500 depending on chosen subject domain tests",
                documentsRequired = listOf("Photo & Signature", "Class 10/12 Marksheets", "APAAR Student Credit ID"),
                portalUrl = "cuetug.nta.nic.in",
                isUrgent = false
            )
        )
    }

    // Modal view states
    var selectedStoryIndex by remember { mutableIntStateOf(-1) }
    var selectedVideo by remember { mutableStateOf<YouTubeVideoData?>(null) }
    var selectedShortsIndex by remember { mutableStateOf<Int?>(null) }
    var selectedArticle by remember { mutableStateOf<WikipediaArticleData?>(null) }

    // New Modals for Revision Notes, Daily GK, and Deadlines
    var selectedRevisionNote by remember { mutableStateOf<RevisionNoteDetail?>(null) }
    var selectedGKItem by remember { mutableStateOf<GKArticleItem?>(null) }
    var selectedDeadline by remember { mutableStateOf<EntranceDeadlineItem?>(null) }

    // "More" Full Gallery Modal States
    var showAllVideosGallery by remember { mutableStateOf(false) }
    var showAllArticlesGallery by remember { mutableStateOf(false) }
    var showAllStoriesGallery by remember { mutableStateOf(false) }
    var showAllShortsGallery by remember { mutableStateOf(false) }
    var showAllRevisionNotes by remember { mutableStateOf(false) }
    var showAllGK by remember { mutableStateOf(false) }
    var showAllDeadlines by remember { mutableStateOf(false) }

    // Notify ViewModel when any full-screen viewer is opened (to hide bottom floating bar)
    val isAnyViewerOpen = selectedStoryIndex >= 0 ||
            selectedVideo != null ||
            selectedShortsIndex != null ||
            selectedArticle != null ||
            selectedRevisionNote != null ||
            selectedGKItem != null ||
            selectedDeadline != null ||
            showAllVideosGallery ||
            showAllArticlesGallery ||
            showAllStoriesGallery ||
            showAllShortsGallery ||
            showAllRevisionNotes ||
            showAllGK ||
            showAllDeadlines

    LaunchedEffect(isAnyViewerOpen) {
        viewModel.setStoryOpen(isAnyViewerOpen)
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(CanvasCream)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Safe Top Spacing
            Spacer(modifier = Modifier.height(34.dp))

            // 1. TITLE: "Social" (Exact from 35.png)
            Text(
                text = "Social",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF111827),
                fontSize = 24.sp
            )

            Spacer(modifier = Modifier.height(18.dp))

            // 2. STORY / CHANNELS ROW (8 Stories with Unified App Theme Pink Rings)
            // Clicks -> Opens Instagram-style Story Viewer
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // All 8 official stories with unified pink theme gradient ring
                items(allStories.size) { index ->
                    val story = allStories[index]
                    StoryCircleItem(
                        label = when (index) {
                            0 -> "Laxmikant N."
                            1 -> "Govt. of India"
                            2 -> "MoE India"
                            3 -> "Odisha Edu"
                            4 -> "NTA Alerts"
                            5 -> "Skill India"
                            6 -> "UGC India"
                            else -> "PMO India"
                        },
                        avatarInitial = story.authorAvatar
                    ) {
                        selectedStoryIndex = index
                    }
                }

                // 9th Item: More Story button -> Opens Full Stories Gallery
                item {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .clickable { showAllStoriesGallery = true }
                            .padding(end = 4.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(62.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFF1F5F9))
                                .border(1.5.dp, Color(0xFFCBD5E1), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Add,
                                contentDescription = "More",
                                tint = Color(0xFF64748B),
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "More",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color(0xFF64748B),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 3. "India Xstream" SECTION (Exact from 35.png)
            // Clicks -> Opens YouTube-style Video Player View; "more" -> Opens Full Video List
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "India Xstream",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF111827),
                    fontSize = 18.sp
                )
                Text(
                    text = "more",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF64748B),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable {
                        showAllVideosGallery = true
                    }
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Horizontal Scroll of YouTube Video Content Cards (Clean without play icon)
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                items(xstreamVideos.size) { index ->
                    val vid = xstreamVideos[index]
                    val tag = when (index) {
                        0 -> "SCHOLARSHIP"
                        1 -> "PARLIAMENT"
                        2 -> "OLYMPIAD"
                        3 -> "ROBOTICS"
                        else -> "MATH"
                    }
                    XstreamNewsCard(
                        title = vid.title,
                        source = vid.channelName,
                        bgGradient = vid.gradientColors,
                        emoji = vid.iconEmoji,
                        duration = vid.durationText,
                        tag = tag
                    ) {
                        selectedVideo = vid
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 4. "Quick Nation by EduCreators" SECTION (Exact from 35.png)
            // Clicks -> Opens Fullscreen Vertical Shorts / Reels Viewer
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "Quick Nation",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF111827),
                        fontSize = 18.sp
                    )
                    Text(
                        text = "by EduCreators",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF64748B),
                        fontSize = 13.sp
                    )
                }

                Text(
                    text = "more",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF64748B),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable {
                        showAllShortsGallery = true
                    }
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 4 Tall Vertical Video Cards (9:16 Aspect Ratio) -> Shorts (Clean without play icon)
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(shortsList.take(6).size) { index ->
                    val shortItem = shortsList[index]
                    val views = when (index) {
                        0 -> "34.2K"
                        1 -> "52.8K"
                        2 -> "41.9K"
                        3 -> "68.4K"
                        4 -> "38.9K"
                        else -> "81.2K"
                    }
                    QuickNationVideoCard(
                        bgColor = shortItem.bgGradient.first(),
                        caption = shortItem.title,
                        emoji = shortItem.centralEmoji,
                        views = views
                    ) {
                        selectedShortsIndex = index
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 5. "Articles" SECTION (Exact from 35.png)
            // Clicks -> Opens Wikipedia-style Academic Reader View; "more" -> Opens Full Directory
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Articles",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF111827),
                    fontSize = 18.sp
                )
                Text(
                    text = "more",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF64748B),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable {
                        showAllArticlesGallery = true
                    }
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 2 Horizontal Article Boxes (Clean Editorial Card Styling)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Card 1: Soft Sky Blue -> National Scholarship Portal Wiki
                Surface(
                    modifier = Modifier
                        .weight(1f)
                        .height(136.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .border(1.dp, Color(0xFFBAE6FD), RoundedCornerShape(16.dp))
                        .clickable { selectedArticle = wikiArticles[0] },
                    shape = RoundedCornerShape(16.dp),
                    color = Color(0xFFE0F2FE),
                    shadowElevation = 2.dp
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(12.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "NSP Gazette",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF0C4A6E),
                                fontSize = 13.5.sp
                            )
                            Surface(
                                shape = RoundedCornerShape(4.dp),
                                color = Color.White.copy(alpha = 0.8f)
                            ) {
                                Text(
                                    text = "4 min",
                                    color = Color(0xFF0284C7),
                                    fontSize = 9.5.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                                )
                            }
                        }

                        Text(
                            text = "Govt. of India e-Governance Gazette on National Student Portal",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF0369A1),
                            fontSize = 11.5.sp,
                            maxLines = 3,
                            lineHeight = 15.sp
                        )

                        Text(
                            text = "Read Wiki →",
                            color = Color(0xFF0284C7),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                // Card 2: Soft Mint -> Science Olympiad Wiki
                Surface(
                    modifier = Modifier
                        .weight(1f)
                        .height(136.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .border(1.dp, Color(0xFFA7F3D0), RoundedCornerShape(16.dp))
                        .clickable { selectedArticle = wikiArticles[1] },
                    shape = RoundedCornerShape(16.dp),
                    color = Color(0xFFD1FAE5),
                    shadowElevation = 2.dp
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(12.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Olympiad Wiki",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF064E3B),
                                fontSize = 13.5.sp
                            )
                            Surface(
                                shape = RoundedCornerShape(4.dp),
                                color = Color.White.copy(alpha = 0.8f)
                            ) {
                                Text(
                                    text = "5 min",
                                    color = Color(0xFF059669),
                                    fontSize = 9.5.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                                )
                            }
                        }

                        Text(
                            text = "Class 12 Science Practical Tips & State Olympiad Guide",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF047857),
                            fontSize = 11.5.sp,
                            maxLines = 3,
                            lineHeight = 15.sp
                        )

                        Text(
                            text = "Read Wiki →",
                            color = Color(0xFF059669),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // =============================================================
            // 6. NEW CONTENT BELOW ARTICLES: "Campus Pulse & Discussions"
            // =============================================================
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Campus Pulse & Discussions",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF111827),
                    fontSize = 18.sp
                )

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = OdishaMagenta.copy(alpha = 0.1f),
                    modifier = Modifier.clickable {
                        Toast.makeText(context, "New discussion query created!", Toast.LENGTH_SHORT).show()
                    }
                ) {
                    Text(
                        text = "+ Ask Query",
                        color = OdishaMagenta,
                        fontSize = 11.5.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Discussion Cards List
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                discussions.forEach { disc ->
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = PureWhite,
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                Toast.makeText(context, "Viewing discussion: ${disc.title}", Toast.LENGTH_SHORT).show()
                            }
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(28.dp)
                                            .clip(CircleShape)
                                            .background(Color(0xFFEDE9FE)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(text = disc.authorAvatar, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF5B21B6))
                                    }
                                    Text(
                                        text = disc.authorName,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.5.sp,
                                        color = Color(0xFF111827)
                                    )
                                }

                                Surface(
                                    shape = RoundedCornerShape(6.dp),
                                    color = Color(0xFFF1F5F9)
                                ) {
                                    Text(
                                        text = disc.category,
                                        color = Color(0xFF475569),
                                        fontSize = 10.5.sp,
                                        fontWeight = FontWeight.Medium,
                                        modifier = Modifier.padding(horizontal = 7.dp, vertical = 2.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = disc.title,
                                color = Color(0xFF1E293B),
                                fontSize = 13.5.sp,
                                fontWeight = FontWeight.SemiBold,
                                lineHeight = 18.sp
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                                        modifier = Modifier.clickable { disc.likesCount++ }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.ThumbUp,
                                            contentDescription = "Upvote",
                                            tint = Color(0xFF0284C7),
                                            modifier = Modifier.size(14.dp)
                                        )
                                        Text(text = "${disc.likesCount} upvotes", fontSize = 11.5.sp, color = Color(0xFF64748B))
                                    }

                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.ChatBubbleOutline,
                                            contentDescription = "Replies",
                                            tint = Color(0xFF64748B),
                                            modifier = Modifier.size(14.dp)
                                        )
                                        Text(text = "${disc.repliesCount} replies", fontSize = 11.5.sp, color = Color(0xFF64748B))
                                    }
                                }

                                Text(text = disc.timeAgo, color = Color(0xFF94A3B8), fontSize = 11.sp)
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // =============================================================
            // 7. SMART REVISION NOTES (Rich, fully clickable study cards)
            // =============================================================
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Smart Revision Notes",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF111827),
                    fontSize = 18.sp
                )
                Text(
                    text = "more",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF64748B),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable {
                        showAllRevisionNotes = true
                    }
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Revision Notes Horizontal Cards
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(revisionNotes) { note ->
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = PureWhite,
                        border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
                        modifier = Modifier
                            .width(230.dp)
                            .height(128.dp)
                            .clickable {
                                selectedRevisionNote = note
                            }
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Brush.linearGradient(note.bgGradient))
                                .padding(12.dp)
                        ) {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Surface(
                                        shape = RoundedCornerShape(4.dp),
                                        color = Color.Black.copy(alpha = 0.35f)
                                    ) {
                                        Text(
                                            text = note.subject,
                                            color = PureWhite,
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }

                                    Text(
                                        text = note.formulaCount,
                                        color = PureWhite.copy(alpha = 0.85f),
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                }

                                Column {
                                    Text(
                                        text = note.title,
                                        color = PureWhite,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.5.sp,
                                        maxLines = 2,
                                        overflow = TextOverflow.Ellipsis,
                                        lineHeight = 16.sp
                                    )
                                    Spacer(modifier = Modifier.height(3.dp))
                                    Text(
                                        text = "${note.chapter} • ${note.pagesCount}",
                                        color = PureWhite.copy(alpha = 0.8f),
                                        fontSize = 10.sp,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // =============================================================
            // 8. DAILY GK & CURRENT AFFAIRS (Clickable headlines & quiz)
            // =============================================================
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Daily GK & Current Affairs",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF111827),
                        fontSize = 18.sp
                    )
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = OdishaMagenta.copy(alpha = 0.1f)
                    ) {
                        Text(
                            text = "TODAY",
                            color = OdishaMagenta,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }

                Text(
                    text = "more",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF64748B),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable {
                        showAllGK = true
                    }
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // GK Digest Card (All bullet points clickable to open detail sheet + quiz)
            Surface(
                shape = RoundedCornerShape(18.dp),
                color = PureWhite,
                border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Odisha & National Academic Bulletin",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = Color(0xFF0F172A)
                        )
                        Text(
                            text = "27 Sept 2026",
                            color = Color(0xFF64748B),
                            fontSize = 11.5.sp
                        )
                    }

                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        gkNewsList.take(4).forEach { item ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(8.dp))
                                    .clickable { selectedGKItem = item }
                                    .padding(vertical = 4.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.Top
                            ) {
                                Text(
                                    text = "•",
                                    color = OdishaMagenta,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                )
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = item.title,
                                        color = Color(0xFF1E293B),
                                        fontSize = 12.5.sp,
                                        fontWeight = FontWeight.Medium,
                                        lineHeight = 17.sp
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = item.category,
                                            color = OdishaMagenta,
                                            fontSize = 9.5.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(text = "•", color = Color(0xFF94A3B8), fontSize = 9.5.sp)
                                        Text(
                                            text = "Tap to read & quiz →",
                                            color = Color(0xFF64748B),
                                            fontSize = 10.sp
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // =============================================================
            // 9. ENTRANCE & SCHOLARSHIP DEADLINES (Clickable for reminders & portal)
            // =============================================================
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Entrance & Scholarship Deadlines",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF111827),
                    fontSize = 18.sp
                )
                Text(
                    text = "more",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF64748B),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable {
                        showAllDeadlines = true
                    }
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Exam Deadlines Horizontal Scroll
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(entranceDeadlines) { item ->
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = PureWhite,
                        border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
                        modifier = Modifier
                            .width(200.dp)
                            .clickable {
                                selectedDeadline = item
                            }
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Surface(
                                    shape = RoundedCornerShape(4.dp),
                                    color = OdishaMagenta.copy(alpha = 0.1f)
                                ) {
                                    Text(
                                        text = item.category,
                                        color = OdishaMagenta,
                                        fontSize = 9.5.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }

                                Surface(
                                    shape = RoundedCornerShape(4.dp),
                                    color = if (item.isUrgent) Color(0xFFFEF2F2) else Color(0xFFEFF6FF)
                                ) {
                                    Text(
                                        text = item.daysRemaining,
                                        color = if (item.isUrgent) Color(0xFFDC2626) else Color(0xFF2563EB),
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(2.dp))

                            Text(
                                text = item.title,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.5.sp,
                                color = Color(0xFF1E293B),
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis,
                                lineHeight = 16.sp
                            )

                            Spacer(modifier = Modifier.height(2.dp))

                            Text(
                                text = "Due: ${item.lastDate}",
                                color = Color(0xFF64748B),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(96.dp))
        }

        // =================================================================
        // MODAL 1: FULL-SCREEN INSTAGRAM-STYLE STORY VIEWER
        // =================================================================
        if (selectedStoryIndex >= 0 && selectedStoryIndex < allStories.size) {
            InstagramStoryViewer(
                stories = allStories,
                initialIndex = selectedStoryIndex,
                onDismiss = {
                    selectedStoryIndex = -1
                }
            )
        }

        // =================================================================
        // MODAL 2: FULL-SCREEN YOUTUBE-STYLE VIDEO PLAYER VIEW
        // =================================================================
        selectedVideo?.let { vid ->
            YouTubeVideoViewerModal(
                video = vid,
                onDismiss = {
                    selectedVideo = null
                },
                onSelectRelatedVideo = { newVid ->
                    selectedVideo = newVid
                }
            )
        }

        // =================================================================
        // MODAL 3: FULL-SCREEN SHORTS / REELS VERTICAL VIDEO VIEW (16 Reels)
        // =================================================================
        selectedShortsIndex?.let { sIndex ->
            ShortsReelsViewerModal(
                shortsList = shortsList,
                initialIndex = sIndex,
                onDismiss = {
                    selectedShortsIndex = null
                }
            )
        }

        // =================================================================
        // MODAL 4: FULL-SCREEN WIKIPEDIA-STYLE ACADEMIC READER VIEW
        // =================================================================
        selectedArticle?.let { articleData ->
            WikipediaArticleViewerModal(
                article = articleData,
                onDismiss = {
                    selectedArticle = null
                }
            )
        }

        // =================================================================
        // MODAL 5: FULL DIRECTORY FOR "India Xstream" VIDEOS (MORE CLICK)
        // =================================================================
        if (showAllVideosGallery) {
            FullVideosDirectoryDialog(
                videos = xstreamVideos,
                onSelectVideo = { vid ->
                    showAllVideosGallery = false
                    selectedVideo = vid
                },
                onDismiss = { showAllVideosGallery = false }
            )
        }

        // =================================================================
        // MODAL 6: FULL DIRECTORY FOR "Articles" (MORE CLICK)
        // =================================================================
        if (showAllArticlesGallery) {
            FullArticlesDirectoryDialog(
                articles = wikiArticles,
                onSelectArticle = { art ->
                    showAllArticlesGallery = false
                    selectedArticle = art
                },
                onDismiss = { showAllArticlesGallery = false }
            )
        }

        // =================================================================
        // MODAL 7: FULL STORIES DIRECTORY (MORE CLICK)
        // =================================================================
        if (showAllStoriesGallery) {
            FullStoriesDirectoryDialog(
                stories = allStories,
                onSelectStory = { index ->
                    showAllStoriesGallery = false
                    selectedStoryIndex = index
                },
                onDismiss = { showAllStoriesGallery = false }
            )
        }

        // =================================================================
        // MODAL 8: FULL SHORTS DIRECTORY (MORE CLICK)
        // =================================================================
        if (showAllShortsGallery) {
            FullShortsDirectoryDialog(
                shorts = shortsList,
                onSelectShort = { index ->
                    showAllShortsGallery = false
                    selectedShortsIndex = index
                },
                onDismiss = { showAllShortsGallery = false }
            )
        }

        // =================================================================
        // MODAL 9: REVISION NOTE VIEWER (CARD CLICK)
        // =================================================================
        selectedRevisionNote?.let { note ->
            RevisionNoteViewerModal(
                note = note,
                onDismiss = { selectedRevisionNote = null }
            )
        }

        // =================================================================
        // MODAL 10: DAILY GK DETAIL & QUIZ (CARD / BULLET CLICK)
        // =================================================================
        selectedGKItem?.let { gkItem ->
            GKArticleDetailModal(
                item = gkItem,
                onDismiss = { selectedGKItem = null }
            )
        }

        // =================================================================
        // MODAL 11: ENTRANCE DEADLINE DETAIL & REMINDER (CARD CLICK)
        // =================================================================
        selectedDeadline?.let { deadline ->
            EntranceDeadlineDetailModal(
                item = deadline,
                onDismiss = { selectedDeadline = null }
            )
        }

        // =================================================================
        // MODAL 12: ALL REVISION NOTES DIRECTORY (MORE CLICK)
        // =================================================================
        if (showAllRevisionNotes) {
            AllRevisionNotesDialog(
                notes = revisionNotes,
                onSelectNote = { note ->
                    showAllRevisionNotes = false
                    selectedRevisionNote = note
                },
                onDismiss = { showAllRevisionNotes = false }
            )
        }

        // =================================================================
        // MODAL 13: ALL DAILY GK DIRECTORY (MORE CLICK)
        // =================================================================
        if (showAllGK) {
            AllGKNewsDialog(
                newsList = gkNewsList,
                onSelectGK = { gkItem ->
                    showAllGK = false
                    selectedGKItem = gkItem
                },
                onDismiss = { showAllGK = false }
            )
        }

        // =================================================================
        // MODAL 14: ALL DEADLINES DIRECTORY (MORE CLICK)
        // =================================================================
        if (showAllDeadlines) {
            AllDeadlinesDialog(
                deadlines = entranceDeadlines,
                onSelectDeadline = { dl ->
                    showAllDeadlines = false
                    selectedDeadline = dl
                },
                onDismiss = { showAllDeadlines = false }
            )
        }
    }
}

// =========================================================================
// FULL DIRECTORY DIALOGS (When clicking "more")
// =========================================================================
@Composable
private fun FullVideosDirectoryDialog(
    videos: List<YouTubeVideoData>,
    onSelectVideo: (YouTubeVideoData) -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(CanvasCream)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 18.dp)
            ) {
                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        IconButton(onClick = onDismiss) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = Color(0xFF111827)
                            )
                        }
                        Text(
                            text = "India Xstream Videos",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF111827)
                        )
                    }

                    Text(
                        text = "${videos.size} Videos",
                        color = Color(0xFF64748B),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(videos) { vid ->
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = PureWhite,
                            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0)),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onSelectVideo(vid) }
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .width(110.dp)
                                        .height(72.dp)
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(Brush.linearGradient(vid.gradientColors)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(text = vid.iconEmoji, fontSize = 24.sp)
                                    Box(
                                        modifier = Modifier
                                            .align(Alignment.BottomEnd)
                                            .padding(4.dp)
                                            .background(Color.Black.copy(alpha = 0.75f), RoundedCornerShape(3.dp))
                                            .padding(horizontal = 4.dp, vertical = 1.dp)
                                    ) {
                                        Text(
                                            text = vid.durationText,
                                            color = PureWhite,
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }

                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = vid.title,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp,
                                        color = Color(0xFF111827),
                                        maxLines = 2,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = "${vid.channelName} • ${vid.views}",
                                        color = Color(0xFF64748B),
                                        fontSize = 11.sp
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
private fun FullArticlesDirectoryDialog(
    articles: List<WikipediaArticleData>,
    onSelectArticle: (WikipediaArticleData) -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(CanvasCream)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 18.dp)
            ) {
                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        IconButton(onClick = onDismiss) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = Color(0xFF111827)
                            )
                        }
                        Text(
                            text = "Academic Encyclopedia",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF111827)
                        )
                    }

                    Text(
                        text = "${articles.size} Articles",
                        color = Color(0xFF64748B),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(articles) { art ->
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = PureWhite,
                            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0)),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onSelectArticle(art) }
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = art.title,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 15.sp,
                                        color = Color(0xFF0C4A6E)
                                    )
                                    Surface(
                                        shape = RoundedCornerShape(4.dp),
                                        color = Color(0xFFE0F2FE)
                                    ) {
                                        Text(
                                            text = art.readingTime,
                                            color = Color(0xFF0284C7),
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(4.dp))

                                Text(
                                    text = art.subtitle,
                                    color = Color(0xFF475569),
                                    fontSize = 12.sp
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                Text(
                                    text = "Read Full Scholarly Entry →",
                                    color = Color(0xFF0284C7),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 11.5.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun FullStoriesDirectoryDialog(
    stories: List<StoryItem>,
    onSelectStory: (Int) -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(CanvasCream)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 18.dp)
            ) {
                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        IconButton(onClick = onDismiss) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = Color(0xFF111827)
                            )
                        }
                        Text(
                            text = "Student & Authority Stories",
                            fontSize = 19.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF111827)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(stories.size) { index ->
                        val story = stories[index]
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = PureWhite,
                            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0)),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onSelectStory(index) }
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(48.dp)
                                        .clip(CircleShape)
                                        .background(Brush.sweepGradient(story.bgGradient)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = story.authorAvatar,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = PureWhite
                                    )
                                }

                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = story.authorName,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp,
                                        color = Color(0xFF111827)
                                    )
                                    Text(
                                        text = story.title,
                                        color = Color(0xFF475569),
                                        fontSize = 12.sp,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
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
private fun FullShortsDirectoryDialog(
    shorts: List<ShortReelItem>,
    onSelectShort: (Int) -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(CanvasCream)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 18.dp)
            ) {
                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        IconButton(onClick = onDismiss) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = Color(0xFF111827)
                            )
                        }
                        Text(
                            text = "Quick Nation Reels",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF111827)
                        )
                    }

                    Text(
                        text = "${shorts.size} Reels",
                        color = Color(0xFFE11D48),
                        fontSize = 12.5.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(shorts.size) { index ->
                        val item = shorts[index]
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = PureWhite,
                            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0)),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onSelectShort(index) }
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(60.dp)
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(Brush.linearGradient(item.bgGradient)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(text = item.centralEmoji, fontSize = 24.sp)
                                }

                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = item.title,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp,
                                        color = Color(0xFF111827),
                                        maxLines = 2,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = "${item.creatorHandle} • ${item.initialLikes / 1000}k likes • ${item.hashtags}",
                                        color = Color(0xFF64748B),
                                        fontSize = 11.sp,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
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

// =========================================================================
// INSTAGRAM-STYLE STORY VIEWER
// Supports Govt. of India badge bar, Segmented Progress, CTA & Chat Input
// =========================================================================
@Composable
fun InstagramStoryViewer(
    stories: List<StoryItem>,
    initialIndex: Int,
    onDismiss: () -> Unit
) {
    var currentIndex by remember { mutableIntStateOf(initialIndex) }
    val currentStory = stories[currentIndex]
    val progress = remember { Animatable(0f) }
    var isPaused by remember { mutableStateOf(false) }
    var isLiked by remember { mutableStateOf(false) }
    var userMessageText by remember { mutableStateOf("") }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    // Timer animation for current story
    LaunchedEffect(currentIndex, isPaused) {
        if (!isPaused) {
            progress.snapTo(0f)
            progress.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 5000, easing = LinearEasing)
            )
            // Go to next story or dismiss if last
            if (currentIndex < stories.size - 1) {
                currentIndex++
            } else {
                onDismiss()
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        isPaused = true
                        tryAwaitRelease()
                        isPaused = false
                    },
                    onTap = { offset ->
                        val screenWidth = size.width
                        if (offset.x < screenWidth * 0.35f) {
                            if (currentIndex > 0) {
                                currentIndex--
                            } else {
                                scope.launch { progress.snapTo(0f) }
                            }
                        } else {
                            if (currentIndex < stories.size - 1) {
                                currentIndex++
                            } else {
                                onDismiss()
                            }
                        }
                    }
                )
            }
    ) {
        // BACKGROUND STORY CANVAS / GRADIENT ARTWORK
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = currentStory.bgGradient
                    )
                )
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val step = 32.dp.toPx()
                val lineCol = Color.White.copy(alpha = 0.04f)
                var x = 0f
                while (x < size.width) {
                    drawLine(lineCol, Offset(x, 0f), Offset(x, size.height), strokeWidth = 1f)
                    x += step
                }
                var y = 0f
                while (y < size.height) {
                    drawLine(lineCol, Offset(0f, y), Offset(size.width, y), strokeWidth = 1f)
                    y += step
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 26.dp)
                    .padding(top = 160.dp, bottom = 140.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                currentStory.badgeLabel?.let { badge ->
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = if (currentStory.isGovtOfIndia) Color(0xFFD81B60) else Color(0x33FFFFFF)
                    ) {
                        Text(
                            text = badge,
                            color = PureWhite,
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }

                Text(
                    text = currentStory.title,
                    color = PureWhite,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 25.sp,
                    lineHeight = 32.sp
                )

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = currentStory.body,
                    color = Color(0xFFE2E8F0),
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 15.sp,
                    lineHeight = 23.sp
                )
            }
        }

        // TOP OVERLAY
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                stories.indices.forEach { index ->
                    val segmentProgress = when {
                        index < currentIndex -> 1f
                        index == currentIndex -> progress.value
                        else -> 0f
                    }
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(2.5.dp)
                            .clip(RoundedCornerShape(2.dp))
                            .background(Color.White.copy(alpha = 0.35f))
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(segmentProgress)
                                .fillMaxHeight()
                                .background(PureWhite)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(PureWhite),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = currentStory.authorAvatar,
                            fontSize = if (currentStory.authorAvatar.length > 2) 16.sp else 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextDark
                        )
                    }

                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = currentStory.authorName,
                                color = PureWhite,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.5.sp
                            )
                            if (currentStory.isGovtOfIndia) {
                                Icon(
                                    imageVector = Icons.Filled.Verified,
                                    contentDescription = "Verified Official",
                                    tint = Color(0xFF38BDF8),
                                    modifier = Modifier.size(15.dp)
                                )
                            }
                        }
                        Text(
                            text = currentStory.timeAgo,
                            color = Color.White.copy(alpha = 0.75f),
                            fontSize = 11.5.sp
                        )
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    IconButton(
                        onClick = {
                            Toast.makeText(context, "Story Options: Share / Report", Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.MoreVert,
                            contentDescription = "More",
                            tint = PureWhite,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "Close",
                            tint = PureWhite,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            if (currentStory.isGovtOfIndia && currentStory.govtNoticeTag != null) {
                Spacer(modifier = Modifier.height(10.dp))
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    shape = RoundedCornerShape(8.dp),
                    color = Color(0xFF1E293B).copy(alpha = 0.85f),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF38BDF8).copy(alpha = 0.6f))
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = currentStory.govtNoticeTag,
                            color = Color(0xFFBAE6FD),
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 11.5.sp
                        )
                    }
                }
            }
        }

        // BOTTOM OVERLAY BAR
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 24.dp)
        ) {
            if (currentStory.hasCta) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Filled.KeyboardArrowUp,
                        contentDescription = "Swipe up",
                        tint = PureWhite,
                        modifier = Modifier.size(22.dp)
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Spacer(modifier = Modifier.width(36.dp))

                        Surface(
                            modifier = Modifier
                                .clip(RoundedCornerShape(24.dp))
                                .clickable {
                                    Toast.makeText(context, "Opening ${currentStory.ctaButtonLabel} Portal...", Toast.LENGTH_SHORT).show()
                                },
                            shape = RoundedCornerShape(24.dp),
                            color = PureWhite
                        ) {
                            Text(
                                text = currentStory.ctaButtonLabel,
                                color = Color(0xFF111827),
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                modifier = Modifier.padding(horizontal = 28.dp, vertical = 10.dp)
                            )
                        }

                        IconButton(
                            onClick = {
                                Toast.makeText(context, "Story shared with classmates!", Toast.LENGTH_SHORT).show()
                            },
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Navigation,
                                contentDescription = "Share",
                                tint = PureWhite,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    }
                }
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(46.dp)
                            .clip(RoundedCornerShape(24.dp))
                            .border(1.2.dp, Color.White.copy(alpha = 0.65f), RoundedCornerShape(24.dp))
                            .padding(horizontal = 18.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (userMessageText.isEmpty()) {
                            Text(
                                text = "Send message",
                                color = Color.White.copy(alpha = 0.7f),
                                fontSize = 14.sp
                            )
                        }
                        BasicTextField(
                            value = userMessageText,
                            onValueChange = { userMessageText = it },
                            textStyle = TextStyle(color = PureWhite, fontSize = 14.sp),
                            cursorBrush = SolidColor(PureWhite),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    IconButton(
                        onClick = {
                            isLiked = !isLiked
                            Toast.makeText(context, if (isLiked) "Liked ❤️" else "Unliked", Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            imageVector = if (isLiked) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                            contentDescription = "Like",
                            tint = if (isLiked) Color(0xFFF43F5E) else PureWhite,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    IconButton(
                        onClick = {
                            if (userMessageText.isNotBlank()) {
                                Toast.makeText(context, "Message sent to ${currentStory.authorName}!", Toast.LENGTH_SHORT).show()
                                userMessageText = ""
                            } else {
                                Toast.makeText(context, "Story shared!", Toast.LENGTH_SHORT).show()
                            }
                        },
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Navigation,
                            contentDescription = "Share",
                            tint = PureWhite,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }
            }
        }
    }
}

// -----------------------------------------------------------
// Story Circle Item (Unified Pink App Theme Ring)
// -----------------------------------------------------------
@Composable
private fun StoryCircleItem(
    label: String,
    gradientColors: List<Color> = listOf(Color(0xFFE11D48), Color(0xFFFB7185)),
    avatarInitial: String,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .size(62.dp)
                .clip(CircleShape)
                .background(Brush.sweepGradient(gradientColors))
                .padding(2.5.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
                    .background(PureWhite),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = avatarInitial,
                    fontWeight = FontWeight.Bold,
                    fontSize = if (avatarInitial.length > 2) 18.sp else 15.sp,
                    color = Color(0xFF1E293B)
                )
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = Color(0xFF334155),
            fontSize = 11.5.sp,
            fontWeight = FontWeight.Medium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

// -----------------------------------------------------------
// India Xstream News Card (Professional Clean Thumbnail - NO Play Icon)
// -----------------------------------------------------------
@Composable
private fun XstreamNewsCard(
    title: String,
    source: String,
    bgGradient: List<Color>,
    emoji: String,
    duration: String = "12:45",
    tag: String = "GUIDE",
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .width(200.dp)
            .height(140.dp)
            .clip(RoundedCornerShape(18.dp))
            .clickable { onClick() },
        shape = RoundedCornerShape(18.dp),
        shadowElevation = 2.dp
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.linearGradient(bgGradient))
        ) {
            // Subtle Grid texture lines
            Canvas(modifier = Modifier.fillMaxSize()) {
                val step = 24.dp.toPx()
                val lineCol = Color.White.copy(alpha = 0.05f)
                var x = 0f
                while (x < size.width) {
                    drawLine(lineCol, Offset(x, 0f), Offset(x, size.height), strokeWidth = 1f)
                    x += step
                }
            }

            // Top Header: Tag Pill + Duration Badge
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .align(Alignment.TopCenter),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = Color.Black.copy(alpha = 0.45f)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(text = emoji, fontSize = 11.sp)
                        Text(
                            text = tag,
                            color = PureWhite,
                            fontSize = 9.5.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = Color.Black.copy(alpha = 0.6f)
                ) {
                    Text(
                        text = duration,
                        color = PureWhite,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            // Bottom Text with Gradient Scrim
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .background(
                        Brush.verticalGradient(
                            listOf(Color.Transparent, Color.Black.copy(alpha = 0.88f))
                        )
                    )
                    .padding(horizontal = 10.dp, vertical = 8.dp)
            ) {
                Column {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = PureWhite,
                        fontSize = 12.5.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        lineHeight = 16.sp
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "• $source",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFFBAE6FD),
                        fontSize = 10.5.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

// -----------------------------------------------------------
// Quick Nation Video Card (Professional Clean Shorts - NO Play Icon)
// -----------------------------------------------------------
@Composable
private fun QuickNationVideoCard(
    bgColor: Color,
    caption: String,
    emoji: String = "⚡",
    views: String = "34.2K",
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .width(118.dp)
            .height(175.dp)
            .clip(RoundedCornerShape(18.dp))
            .clickable { onClick() },
        shape = RoundedCornerShape(18.dp),
        shadowElevation = 2.dp,
        color = bgColor
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // Background Glow Artwork
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawCircle(
                    brush = Brush.radialGradient(
                        listOf(Color.White.copy(alpha = 0.15f), Color.Transparent),
                        center = Offset(size.width * 0.5f, size.height * 0.35f),
                        radius = size.width * 0.7f
                    )
                )
            }

            // Top Header: Emoji + Views Pill
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .align(Alignment.TopCenter),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = emoji,
                    fontSize = 18.sp
                )

                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = Color.Black.copy(alpha = 0.45f)
                ) {
                    Text(
                        text = "⚡ $views",
                        color = PureWhite,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
                    )
                }
            }

            // Bottom Text with Dark Gradient Scrim
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .background(
                        Brush.verticalGradient(
                            listOf(Color.Transparent, Color.Black.copy(alpha = 0.9f))
                        )
                    )
                    .padding(horizontal = 8.dp, vertical = 8.dp)
            ) {
                Text(
                    text = caption,
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold,
                    color = PureWhite,
                    fontSize = 11.5.sp,
                    lineHeight = 15.sp,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}
