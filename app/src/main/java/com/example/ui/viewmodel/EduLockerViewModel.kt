package com.example.ui.viewmodel

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.ChatMessageEntity
import com.example.data.local.DocumentItemEntity
import com.example.data.local.EduLockerDatabase
import com.example.data.local.EduLockerRepository
import com.example.data.local.NotificationItemEntity
import com.example.data.local.ScholarshipApplicationEntity
import com.example.data.local.SocialPostEntity
import com.example.data.local.StudentProfileEntity
import com.example.ui.components.NavTab
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.File

data class RecommendationItem(
    val id: String,
    val title: String,
    val badge: String,
    val subtitle: String,
    val category: String,
    val readTime: String,
    val rating: String,
    val bannerGradient: List<Long>,
    val backgroundColor: Long = 0xFFDCFCE7,
    val description: String,
    val syllabusTopics: List<String>,
    val roadmapSteps: List<String>,
    val topColleges: List<String>,
    val scholarshipGrant: String,
    val keyBenefits: List<String>
)

data class SocialPost(
    val id: String,
    val author: String,
    val handle: String,
    val timeAgo: String,
    val content: String,
    val likesCount: Int,
    val isLiked: Boolean = false,
    val imageResId: String? = null,
    val customImageUri: String? = null,
    val customTheme: String? = null
)

data class AiMessage(
    val sender: String,
    val text: String,
    val isUser: Boolean,
    val timestamp: String
)

data class InstitutionDetail(
    val id: String,
    val name: String,
    val location: String,
    val code: String,
    val type: String,
    val affiliation: String,
    val estdYear: String,
    val studentCount: String,
    val facultyCount: String,
    val rating: String,
    val principalName: String,
    val description: String,
    val courses: List<String>,
    val facilities: List<String>,
    val contactEmail: String,
    val contactPhone: String,
    val website: String,
    val heroColor: Long = 0xFF0284C7
)

class EduLockerViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: EduLockerRepository

    init {
        val database = EduLockerDatabase.getDatabase(application)
        repository = EduLockerRepository(database)
        viewModelScope.launch {
            repository.seedInitialDataIfEmpty()
        }
    }

    // Navigation State
    private val _currentPage = MutableStateFlow(1) // 1 to 42
    val currentPage: StateFlow<Int> = _currentPage.asStateFlow()

    private val _currentTab = MutableStateFlow(NavTab.HOME)
    val currentTab: StateFlow<NavTab> = _currentTab.asStateFlow()

    // Auth State
    var enteredAadhaar = MutableStateFlow("1418 9864 7620")
    var enteredFullName = MutableStateFlow("Laxmikant Nayak")
    var enteredOtp = MutableStateFlow("4819")
    var enteredPin = MutableStateFlow("1418")
    var isLoggedIn = MutableStateFlow(true)

    // Scholarship Flow Form State
    var scholarshipMobile = MutableStateFlow("+91 98610 54321")
    var scholarshipEduLockerId = MutableStateFlow("ODIST-KMD-PLBM-LNO01")
    var scholarshipOtp = MutableStateFlow("1418")
    var scholarshipAadhaarNo = MutableStateFlow("XXXX-XXXX-1418")
    var scholarshipParentLinked = MutableStateFlow(true)
    var scholarshipIncomeConsent = MutableStateFlow(true)
    var scholarshipAttendanceConsent = MutableStateFlow(true)
    var scholarshipAcademicConsent = MutableStateFlow(true)
    var scholarshipCasteCategory = MutableStateFlow("General / SEBC")
    var scholarshipBankLinked = MutableStateFlow(true)
    var scholarshipReviewConsent1 = MutableStateFlow(true)
    var scholarshipReviewConsent2 = MutableStateFlow(true)

    // Olympiad State
    var olympiadRulesAgreed = MutableStateFlow(true)

    // HP LIFE Developer Program State
    var selectedCourse = MutableStateFlow("Web Development")
    var selectedLevel = MutableStateFlow("Beginner")
    var videoProgressSeconds = MutableStateFlow(884) // 14:44 out of 54:25 (3265 sec)
    var isVideoPlaying = MutableStateFlow(true)
    var videoSpeed = MutableStateFlow(1.0f)
    var areCaptionsEnabled = MutableStateFlow(true)

    // Profile & 3D ID Card State
    var isIdCardFlipped = MutableStateFlow(false) // false: Front, true: Back
    var selectedProfileModal = MutableStateFlow<String?>(null)

    // Document Modal State
    var selectedDocument = MutableStateFlow<DocumentItemEntity?>(null)

    // Active Chat
    var activeChatContact = MutableStateFlow<String?>("Ashneer Gorver")
    var currentChatInput = MutableStateFlow("")

    // Comprehensive Directory of Institutions
    val allInstitutions = listOf(
        InstitutionDetail(
            id = "sch2",
            name = "Govt. Higher Secondary School, Phulbani",
            location = "Phulbani, Kandhamal, Odisha",
            code = "AOR-56 • GHSSP-PLBM-KMD",
            type = "Higher Secondary (+2)",
            affiliation = "Council of Higher Secondary Education (CHSE Odisha)",
            estdYear = "1960",
            studentCount = "1,850+ Students",
            facultyCount = "48 Teachers",
            rating = "4.8 ★",
            principalName = "Dr. Subhasree Das, M.Sc., Ph.D.",
            description = "Govt. Higher Secondary School, Phulbani is a premier government institution in Kandhamal district offering Science, Commerce, and Arts streams with advanced digital smart classrooms, modern physics and chemistry labs, and comprehensive state scholarship facilitation.",
            courses = listOf("+2 Science (PCMB/IT)", "+2 Commerce (Accountancy/BM)", "+2 Arts (Pol. Sc/Hist/Odia)", "Vocational IT & Robotics"),
            facilities = listOf("Modern Science Labs", "Digital Library & EduLocker Hub", "Boys & Girls Hostel", "Sports Complex", "Smart Classrooms"),
            contactEmail = "ghss.phulbani@odisha.gov.in",
            contactPhone = "+91 6842 253 104",
            website = "https://kandhamal.nic.in/education/ghss-phulbani",
            heroColor = 0xFFD81B60
        ),
        InstitutionDetail(
            id = "sch1",
            name = "Odisha Adarsha Vidyalaya (OAV)",
            location = "Kandhamal, Odisha",
            code = "AOR-48 • OAV-KMD-01",
            type = "Govt. Model English Medium School",
            affiliation = "Central Board of Secondary Education (CBSE)",
            estdYear = "2016",
            studentCount = "620+ Students",
            facultyCount = "32 Teachers",
            rating = "4.9 ★",
            principalName = "Sri Ramesh Chandra Pradhan, M.A., B.Ed.",
            description = "Odisha Adarsha Vidyalaya Kandhamal is an initiative of the School & Mass Education Department, Govt. of Odisha to provide world-class CBSE English medium education to talented rural and tribal students free of cost.",
            courses = listOf("Class VI - X (CBSE General)", "Class XI - XII Science (CBSE PCM/PCB)", "Coding & Robotics Club", "Olympiad Foundation"),
            facilities = listOf("Smart ICT Labs", "Atal Tinkering Lab", "Fully Residential Campus", "Mid-day Nutrition", "Language Lab"),
            contactEmail = "oav.kandhamal@oav.edu.in",
            contactPhone = "+91 6842 250 882",
            website = "https://oav.edu.in",
            heroColor = 0xFF0284C7
        ),
        InstitutionDetail(
            id = "sch3",
            name = "Ravenshaw Higher Secondary School & University",
            location = "Cuttack, Odisha",
            code = "CHSE-019 • RAV-CTK",
            type = "Premier State Heritage University",
            affiliation = "CHSE Odisha & UGC",
            estdYear = "1868",
            studentCount = "8,500+ Students",
            facultyCount = "240+ Professors",
            rating = "4.9 ★",
            principalName = "Prof. Sanjay Kumar Nayak, Ph.D. (Vice Chancellor)",
            description = "Ravenshaw University is one of the oldest and most prestigious educational institutions in India. Famous for academic excellence, historic red-brick architecture, and alumni of national renown.",
            courses = listOf("+2 Science / Arts / Commerce", "B.Tech / BCA Computer Science", "B.Sc. Data Science", "Integrated M.Sc.", "Ph.D. Research"),
            facilities = listOf("Kanika Library (2.5 Lakh books)", "High Performance Computing Lab", "Heritage Clock Tower", "Open Air Auditorium", "Hostel Quads"),
            contactEmail = "registrar@ravenshawuniversity.ac.in",
            contactPhone = "+91 671 220 1690",
            website = "https://ravenshawuniversity.ac.in",
            heroColor = 0xFF7C3AED
        ),
        InstitutionDetail(
            id = "sch4",
            name = "BJB Autonomous College",
            location = "Bhubaneswar, Odisha",
            code = "CHSE-042 • BJB-BBS",
            type = "Autonomous Premier Degree College",
            affiliation = "Utkal University & NAAC 'A++'",
            estdYear = "1957",
            studentCount = "4,200+ Students",
            facultyCount = "120 Faculty",
            rating = "4.8 ★",
            principalName = "Dr. Niranjan Mohanty, Ph.D.",
            description = "Buxi Jagabandhu Bidyadhar (BJB) Autonomous College is a top-ranking autonomous college in Bhubaneswar known for producing state toppers in Civil Services, IITs, and Medical entrances.",
            courses = listOf("+2 Science / Arts / Commerce", "B.Sc. Computer Science Honours", "B.Com. Financial Markets", "BBA & Bio-Technology"),
            facilities = listOf("Central Digital Library", "Wi-Fi Campus", "Modern Seminar Halls", "Placement Cell", "National Cadet Corps (NCC)"),
            contactEmail = "principal@bjbcollege.in",
            contactPhone = "+91 674 243 6971",
            website = "https://bjbcollege.in",
            heroColor = 0xFF059669
        ),
        InstitutionDetail(
            id = "sch5",
            name = "Kendriya Vidyalaya No. 1",
            location = "Unit 9, Bhubaneswar, Odisha",
            code = "CBSE-991 • KVS-BBS-01",
            type = "Central Government Public School",
            affiliation = "CBSE New Delhi (KVS)",
            estdYear = "1967",
            studentCount = "2,600+ Students",
            facultyCount = "95 Teachers",
            rating = "4.9 ★",
            principalName = "Smt. Manorama Senapati, M.Sc.",
            description = "Kendriya Vidyalaya No. 1 Bhubaneswar is a PM-SHRI flagship central school consistently ranked amongst India's top government schools for CBSE board results, NTSE scholars, and sports.",
            courses = listOf("Primary (I-V)", "Middle & Secondary (VI-X)", "Senior Secondary (+2 Science PCM/PCB)", "Senior Secondary (+2 Commerce & Humanities)"),
            facilities = listOf("Atal Tinkering Lab", "Synthetic Athletic Track", "Digital Classroom Suites", "Robotics Arena", "Botanical Garden"),
            contactEmail = "kv1bbsr@kvs.ac.in",
            contactPhone = "+91 674 239 1262",
            website = "https://no1bhubaneswar.kvs.ac.in",
            heroColor = 0xFFEA580C
        ),
        InstitutionDetail(
            id = "sch6",
            name = "IIT Bhubaneswar (Indian Institute of Technology)",
            location = "Argul, Jatni, Bhubaneswar, Odisha",
            code = "IIT-BBS • IN-IIT-08",
            type = "Institute of National Importance",
            affiliation = "Ministry of Education, Govt. of India",
            estdYear = "2008",
            studentCount = "3,100+ Students",
            facultyCount = "180+ Faculty",
            rating = "5.0 ★",
            principalName = "Prof. Shreepad Karmalkar, Ph.D. (Director)",
            description = "IIT Bhubaneswar is a world-class premier technical institution fostering groundbreaking research in AI, Quantum Computing, Climate Sciences, and Semiconductor engineering across a 936-acre green campus.",
            courses = listOf("B.Tech Computer Science & AI", "B.Tech Electronics & Electrical", "Dual Degree B.Tech + M.Tech", "Ph.D. Advanced Computing"),
            facilities = listOf("Supercomputing Center", "Clean Room Labs", "Design Innovation Hub", "Incubation Center", "Sports Stadium"),
            contactEmail = "director@iitbbs.ac.in",
            contactPhone = "+91 674 713 4567",
            website = "https://www.iitbbs.ac.in",
            heroColor = 0xFF0369A1
        ),
        InstitutionDetail(
            id = "sch7",
            name = "NIT Rourkela (National Institute of Technology)",
            location = "Rourkela, Sundargarh, Odisha",
            code = "NIT-RKL • IN-NIT-12",
            type = "Institute of National Importance",
            affiliation = "Ministry of Education, Govt. of India",
            estdYear = "1961",
            studentCount = "7,500+ Students",
            facultyCount = "350+ Faculty",
            rating = "4.9 ★",
            principalName = "Prof. K. Umamaheshwar Rao, Ph.D. (Director)",
            description = "NIT Rourkela is one of India's leading technological universities, recognized for exceptional campus placements, world-ranking engineering departments, and innovation incubators.",
            courses = listOf("B.Tech CSE / AI / Data Science", "B.Tech Mechanical / Metallurgy", "Integrated M.Sc. Physics", "B.Arch Architecture"),
            facilities = listOf("Biju Patnaik Central Library", "TIIR Innovation Park", "Swimming Complex", "Open Air Theatre", "Satellite Labs"),
            contactEmail = "director@nitrkl.ac.in",
            contactPhone = "+91 661 246 2001",
            website = "https://www.nitrkl.ac.in",
            heroColor = 0xFF4338CA
        ),
        InstitutionDetail(
            id = "sch8",
            name = "KIIT Deemed to be University",
            location = "Patia, Bhubaneswar, Odisha",
            code = "KIIT-01 • DEEMED-UGC",
            type = "Deemed to be University (IoE)",
            affiliation = "UGC & NAAC 'A++' Tier-1",
            estdYear = "1992",
            studentCount = "35,000+ Students",
            facultyCount = "2,500+ Faculty",
            rating = "4.8 ★",
            principalName = "Dr. Achyuta Samanta (Founder) / Prof. S. Senapati",
            description = "Kalinga Institute of Industrial Technology (KIIT) is a globally recognized multidisciplinary university with ultra-modern smart campuses, research partnerships with 200+ universities worldwide.",
            courses = listOf("B.Tech Computer Science & Engg", "MBBS & Dental Sciences", "B.A. LL.B Law", "School of Film & Media"),
            facilities = listOf("Olympic Standard Sports Village", "Central AC Libraries", "Multi-Speciality Hospital", "International Hostels", "Robotics Hub"),
            contactEmail = "kiit@kiit.ac.in",
            contactPhone = "+91 674 272 5113",
            website = "https://kiit.ac.in",
            heroColor = 0xFF0D9488
        )
    )

    val allRecommendations = listOf(
        RecommendationItem(
            id = "rec_learning_era",
            title = "Learning Era",
            badge = "✨ AI Curated 2026",
            subtitle = "Personalized syllabus roadmaps",
            category = "Curriculum & Strategy",
            readTime = "6 min read • 4 Modules",
            rating = "4.9 ★",
            bannerGradient = listOf(0xFF0F172A, 0xFF1E293B, 0xFF0284C7),
            backgroundColor = 0xFFDCFCE7,
            description = "A precision-engineered academic framework crafted for students in Odisha. Combines CHSE & CBSE board benchmarks with NEET, JEE, and State Merit preparation. Real-time digital token tracking ensures your attendance and test scores directly unlock government scholarship disbursements.",
            syllabusTopics = listOf(
                "Foundational Mastery: Advanced Calculus, Mechanics & Organic Syntheses",
                "Speed Drills: Previous 10-Year High-Yield Question Pattern Analytics",
                "Cognitive Boosters: Memory Retrieval & Ultradian Study Sprints",
                "Scholarship Tokenization: Auto-Verification via APAAR / DigiLocker"
            ),
            roadmapSteps = listOf(
                "Week 1-4: Concept Diagnostic & Weak Spot Discovery",
                "Week 5-8: State-Level Live Mock Series & Leaderboard",
                "Week 9-12: Revision Matrix & Final Merit Clearance"
            ),
            topColleges = listOf(
                "Govt. Higher Secondary School, Phulbani",
                "Ravenshaw University, Cuttack",
                "BJB Autonomous College, Bhubaneswar",
                "Odisha Adarsha Vidyalaya (OAV)"
            ),
            scholarshipGrant = "Up to ₹50,000 / year (Medhabruti / Biju Yuba)",
            keyBenefits = listOf(
                "100% verified state curriculum alignment",
                "Direct mentor access to top Odisha educators",
                "Automated grant token generation on completion"
            )
        ),
        RecommendationItem(
            id = "rec_best_for_you",
            title = "Best for you",
            badge = "🔥 High Demand",
            subtitle = "Today's top 3 curated career actions",
            category = "Career Discovery",
            readTime = "5 min read • 3 Tracks",
            rating = "5.0 ★",
            bannerGradient = listOf(0xFFB45309, 0xFFD97706, 0xFFF59E0B),
            backgroundColor = 0xFFFEF3C7,
            description = "Discover high-growth future careers tailored for high-achieving school & college students in Odisha. From Full-Stack AI Engineering to Bio-Informatics and Civil Services, access verified syllabus modules and direct internships.",
            syllabusTopics = listOf(
                "Artificial Intelligence & Machine Learning Architecture",
                "Bio-Engineering & Medical Technology Innovation",
                "Civil Services Foundation & Public Policy in Odisha",
                "UI/UX Product Design & Mobile Systems Development"
            ),
            roadmapSteps = listOf(
                "Month 1: Skill Assessment & Track Specialization",
                "Month 2: Real-World Portfolio Project Build",
                "Month 3: Industry Mentorship & Certificate Issuance"
            ),
            topColleges = listOf(
                "IIT Bhubaneswar",
                "NIT Rourkela",
                "KIIT Deemed to be University",
                "GIET University, Gunupur"
            ),
            scholarshipGrant = "₹25,000 HP LIFE & State Tech Stipend",
            keyBenefits = listOf(
                "Global industry micro-credentials",
                "Direct internship pipeline with Odisha IT ecosystem",
                "Portfolio verification on blockchain"
            )
        ),
        RecommendationItem(
            id = "rec_best_thing",
            title = "Best thing",
            badge = "🏆 Rank Booster",
            subtitle = "Pro tips on competitive exams",
            category = "Exam Excellence",
            readTime = "4 min read • High Yield",
            rating = "4.9 ★",
            bannerGradient = listOf(0xFF0369A1, 0xFF0284C7, 0xFF38BDF8),
            backgroundColor = 0xFFE0F2FE,
            description = "Master time management, active recall testing, and psychological endurance during competitive state and national examinations. Tested by Odisha state board toppers and NTSE medalists.",
            syllabusTopics = listOf(
                "90-Minute Focus Blocks & Active Recall Retrieval",
                "Elimination Strategies for Multi-Choice Competitive Tests",
                "Stress Modulation & Peak Performance Bio-Habits",
                "Rapid Formula & Reaction Revision Flashcards"
            ),
            roadmapSteps = listOf(
                "Phase 1: Speed Drills (50 questions in 40 mins)",
                "Phase 2: Error Log Diary & Misconception Debugging",
                "Phase 3: Full-Length Proctored State Simulation"
            ),
            topColleges = listOf(
                "Govt. Higher Secondary School, Phulbani",
                "Stewart Science College, Cuttack",
                "Kendriya Vidyalaya No. 1, Bhubaneswar"
            ),
            scholarshipGrant = "₹30,000 Junior Science & Math Merit Award",
            keyBenefits = listOf(
                "Comprehensive question bank of 5,000+ PYQs",
                "AI-driven instant answer explanations",
                "Statewide percentile benchmarking"
            )
        ),
        RecommendationItem(
            id = "rec_right_path",
            title = "Right path selector",
            badge = "🚀 Emerging Frontiers",
            subtitle = "Explore emerging tech & design disciplines live.",
            category = "Discipline Explorer",
            readTime = "7 min read • Interactive",
            rating = "4.8 ★",
            bannerGradient = listOf(0xFFBE185D, 0xFFDB2777, 0xFFF472B6),
            backgroundColor = 0xFFFCE7F3,
            description = "Unsure whether to pursue Pure Sciences, Data Engineering, Design, or Law? Use our multi-vector aptitude analysis to find the perfect undergraduate program and scholarship in Odisha.",
            syllabusTopics = listOf(
                "Data Analytics & Python for Scientific Research",
                "Design Systems & Digital Interaction Architecture",
                "Green Energy, Renewable Tech & EV Engineering",
                "Corporate Law, Cyber Forensics & IP Regulations"
            ),
            roadmapSteps = listOf(
                "Step 1: Complete 15-Minute Aptitude Compass",
                "Step 2: Virtual Campus Lab Walkthrough",
                "Step 3: College Counseling & Seat Reservation"
            ),
            topColleges = listOf(
                "BJB Autonomous College",
                "Ravenshaw University",
                "GIET University",
                "NIT Rourkela"
            ),
            scholarshipGrant = "₹40,000 Emerging Tech Incentive Scheme",
            keyBenefits = listOf(
                "Interactive aptitude diagnostic report",
                "Direct chat with college admission coordinators",
                "Early scholarship reservation token"
            )
        ),
        RecommendationItem(
            id = "rec_quest",
            title = "Out of brain Quest",
            badge = "⚡ State Challenges",
            subtitle = "State level hackathons & Olympiad challenges.",
            category = "Innovation & Grants",
            readTime = "4 min read • Live Quest",
            rating = "5.0 ★",
            bannerGradient = listOf(0xFF0F766E, 0xFF0D9488, 0xFF2DD4BF),
            backgroundColor = 0xFFCCFBF1,
            description = "Participate in Odisha Youth Innovation Hackathons, build smart digital solutions for rural education and agriculture, and win seed grants up to ₹1,00,000 directly from the Department of Mass Education.",
            syllabusTopics = listOf(
                "Problem Statement 1: Smart Agri-Sensor for Kandhamal Spices",
                "Problem Statement 2: AI Teacher Assistant for Odia Language",
                "Problem Statement 3: Solar Powered Cold Storage System",
                "Pitch Deck Formulation & Prototype Demonstration"
            ),
            roadmapSteps = listOf(
                "Stage 1: Team Formation & Ideation Submission (Oct 15)",
                "Stage 2: Prototype Evaluation by State Jury (Nov 01)",
                "Stage 3: Grand Finale at Kalinga Stadium Bhubaneswar (Nov 20)"
            ),
            topColleges = listOf(
                "IIT Bhubaneswar Innovation Hub",
                "KIIT TBI Incubator",
                "Govt. Higher Secondary School, Phulbani"
            ),
            scholarshipGrant = "₹1,00,000 Chief Minister Youth Innovation Seed Grant",
            keyBenefits = listOf(
                "State government certificate of recognition",
                "Direct incubator prototyping grant",
                "Free incubation space & patent filing support"
            )
        ),
        RecommendationItem(
            id = "rec_ai_skills",
            title = "AI Skill Track",
            badge = "💻 Code & Cloud",
            subtitle = "NextGen AI & Python developer certification",
            category = "Technical Excellence",
            readTime = "5 min read • Live Labs",
            rating = "4.9 ★",
            bannerGradient = listOf(0xFF4C1D95, 0xFF6D28D9, 0xFF8B5CF6),
            backgroundColor = 0xFFEDE9FE,
            description = "Learn hands-on Python programming, Jetpack Compose development, and Generative AI prompt systems with official industry credentials from HP LIFE.",
            syllabusTopics = listOf(
                "Modern Kotlin & Jetpack Compose Fundamentals",
                "Python Scripting for Data Analysis",
                "API Engineering & Cloud Integrations",
                "Ethical AI & Machine Learning Foundations"
            ),
            roadmapSteps = listOf(
                "Module 1: Code Sandbox Exercises",
                "Module 2: Capstone Project Submission",
                "Module 3: Global Certificate Verification"
            ),
            topColleges = listOf(
                "NIT Rourkela Computer Science",
                "IIT Bhubaneswar AI Center",
                "KIIT School of Computer Applications"
            ),
            scholarshipGrant = "Free Global Certification Voucher (Worth ₹15,000)",
            keyBenefits = listOf(
                "Industry recognized badge",
                "100% free for verified Odisha students",
                "Direct link to state tech internships"
            )
        ),
        RecommendationItem(
            id = "rec_scholarship_hub",
            title = "Scholarship Hub",
            badge = "💰 Direct Benefit",
            subtitle = "Medhabruti & Post-Matric grant fast-track",
            category = "Financial Aid",
            readTime = "3 min read • Instant",
            rating = "5.0 ★",
            bannerGradient = listOf(0xFFC2410C, 0xFFEA580C, 0xFFFB923C),
            backgroundColor = 0xFFFFEDD5,
            description = "Instant e-KYC linked application gateway for Odisha State Scholarship Portal. Automated attendance and marksheet verification through DigiLocker.",
            syllabusTopics = listOf(
                "Aadhaar Biometric e-KYC Verification",
                "Income & Caste Certificate Linking",
                "Direct DBT Bank Account Validation",
                "Real-time Sanction Order Tracking"
            ),
            roadmapSteps = listOf(
                "Step 1: One-Click Document Sync",
                "Step 2: Head of Institution Digital Sign-off",
                "Step 3: Direct Treasury DBT Credit"
            ),
            topColleges = listOf(
                "Govt. Higher Secondary School, Phulbani",
                "Ravenshaw University",
                "BJB Autonomous College"
            ),
            scholarshipGrant = "Direct DBT Disbursal up to ₹20,000",
            keyBenefits = listOf(
                "Zero paperwork required",
                "Instant SMS and Push Notifications",
                "Guaranteed government tracking token"
            )
        ),
        RecommendationItem(
            id = "rec_olympiad",
            title = "Olympiad Mastery",
            badge = "🌟 Gold Series",
            subtitle = "Science & Math State Gold Series",
            category = "Competition",
            readTime = "6 min read • Mock Tests",
            rating = "4.9 ★",
            bannerGradient = listOf(0xFF1E3A8A, 0xFF2563EB, 0xFF60A5FA),
            backgroundColor = 0xFFE0E7FF,
            description = "State-level competitive question papers with step-by-step video analysis by top Odisha academicians and state rank holders.",
            syllabusTopics = listOf(
                "Mathematics: Number Theory & Combinatorics",
                "Physics: Mechanics & Wave Dynamics",
                "Chemistry: Organic Mechanisms & Thermochemistry",
                "Mental Ability: Logical Deduction & Spatial Reasoning"
            ),
            roadmapSteps = listOf(
                "Level 1: District Qualifier Drills",
                "Level 2: State Semifinal Simulation",
                "Level 3: National Medalist Masterclass"
            ),
            topColleges = listOf(
                "Odisha Bigyan Academy",
                "NISER Bhubaneswar",
                "Govt. Higher Secondary School, Phulbani"
            ),
            scholarshipGrant = "Gold Medal + ₹50,000 State Honor",
            keyBenefits = listOf(
                "Curated by national Olympiad trainers",
                "Live weekly doubt clearance sessions",
                "Official state recommendation letter"
            )
        )
    )

    val recommendationsRow1 = listOf(
        allRecommendations[0], // Learning Era
        allRecommendations[1], // Best for you
        allRecommendations[3], // Right path selector
        allRecommendations[5]  // AI Skill Track
    )

    val recommendationsRow2 = listOf(
        allRecommendations[4], // Out of brain Quest
        allRecommendations[2], // Best thing
        allRecommendations[6], // Scholarship Hub
        allRecommendations[7]  // Olympiad Mastery
    )

    private val _selectedRecommendation = MutableStateFlow<RecommendationItem?>(allRecommendations[0])
    val selectedRecommendation: StateFlow<RecommendationItem?> = _selectedRecommendation.asStateFlow()

    fun selectRecommendation(item: RecommendationItem) {
        _selectedRecommendation.value = item
        navigateToPage(43) // Open dedicated Recommendation Detail Screen
    }

    fun selectRecommendationById(id: String) {
        val found = allRecommendations.find { it.id.equals(id, ignoreCase = true) }
            ?: allRecommendations.find { it.title.contains(id, ignoreCase = true) }
            ?: allRecommendations[0]
        _selectedRecommendation.value = found
        navigateToPage(43)
    }

    val selectedInstitution = MutableStateFlow(allInstitutions[0])

    fun selectInstitution(inst: InstitutionDetail) {
        selectedInstitution.value = inst
    }

    fun selectInstitutionById(id: String) {
        val found = allInstitutions.find { it.id.equals(id, ignoreCase = true) }
            ?: allInstitutions.find { it.name.contains(id, ignoreCase = true) }
            ?: allInstitutions[0]
        selectedInstitution.value = found
    }

    // Social Feed State (Backed permanently by Room Database)
    val socialPosts: StateFlow<List<SocialPost>> = repository.socialPosts
        .map { list ->
            list.map { entity ->
                SocialPost(
                    id = entity.id,
                    author = entity.author,
                    handle = entity.handle,
                    timeAgo = entity.timeAgo,
                    content = entity.content,
                    likesCount = entity.likesCount,
                    isLiked = entity.isLiked,
                    imageResId = entity.imageResId,
                    customImageUri = entity.customImageUri,
                    customTheme = entity.customTheme
                )
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Custom post photo overrides and design theme overrides
    val customPostImages = MutableStateFlow<Map<String, String>>(emptyMap())
    val customPostThemes = MutableStateFlow<Map<String, String>>(emptyMap())

    fun updatePostPhoto(postId: String, uri: Uri) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val app = getApplication<Application>()
                val inputStream = app.contentResolver.openInputStream(uri) ?: return@launch
                val safeId = postId.replace(" ", "_").lowercase()
                val photoFile = File(app.filesDir, "custom_post_${safeId}.jpg")
                photoFile.outputStream().use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
                inputStream.close()
                repository.updatePostPhoto(postId, photoFile.absolutePath)
                val current = customPostImages.value.toMutableMap()
                current[postId] = photoFile.absolutePath
                customPostImages.value = current
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updatePostTheme(postId: String, themeName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updatePostTheme(postId, themeName)
            val currentThemes = customPostThemes.value.toMutableMap()
            currentThemes[postId] = themeName
            customPostThemes.value = currentThemes
        }
    }

    fun resetPostPhoto(postId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val app = getApplication<Application>()
            val safeId = postId.replace(" ", "_").lowercase()
            val photoFile = File(app.filesDir, "custom_post_${safeId}.jpg")
            if (photoFile.exists()) {
                photoFile.delete()
            }
            repository.updatePostPhoto(postId, null)
            val current = customPostImages.value.toMutableMap()
            current.remove(postId)
            customPostImages.value = current
        }
    }

    fun updatePostContent(postId: String, newContent: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updatePostContent(postId, newContent)
        }
    }

    fun togglePostLike(postId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val currentList = socialPosts.value
            val target = currentList.find { it.id == postId } ?: return@launch
            val newIsLiked = !target.isLiked
            val newLikes = if (newIsLiked) target.likesCount + 1 else (target.likesCount - 1).coerceAtLeast(0)
            repository.togglePostLike(postId, newIsLiked, newLikes)
        }
    }

    // AI Runway Chat Engine
    val aiMessages = MutableStateFlow(
        listOf(
            AiMessage(
                sender = "Runway AI",
                text = "Namaskar Laxmikant! I am your EduLocker Runway Assistant. Ask me anything about your scholarship eligibility, Olympiad exam dates, APAAR ID, or HP LIFE developer tracks.",
                isUser = false,
                timestamp = "Just now"
            )
        )
    )
    val aiInputText = MutableStateFlow("")
    val isAiThinking = MutableStateFlow(false)
    val isAiChatOpen = MutableStateFlow(false)
    val isStoryOpen = MutableStateFlow(false)

    fun setAiChatOpen(isOpen: Boolean) {
        isAiChatOpen.value = isOpen
    }

    fun setStoryOpen(isOpen: Boolean) {
        isStoryOpen.value = isOpen
    }

    // Room Persistent Flows
    val studentProfile: StateFlow<StudentProfileEntity?> = repository.studentProfile
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val digitalDocuments: StateFlow<List<DocumentItemEntity>> = repository.digitalDocuments
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val notifications: StateFlow<List<NotificationItemEntity>> = repository.notifications
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val scholarshipApplications: StateFlow<List<ScholarshipApplicationEntity>> = repository.scholarshipApplications
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val contactAvatars: StateFlow<Map<String, String>> = repository.contactAvatars
        .map { list -> list.associate { it.contactName to it.photoUri } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyMap())

    val customProfileAssets: StateFlow<Map<String, String>> = repository.customProfileAssets
        .map { list -> list.associate { it.assetKey to it.photoUri } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyMap())

    fun uploadStudentPhoto(uri: Uri) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val app = getApplication<Application>()
                val inputStream = app.contentResolver.openInputStream(uri) ?: return@launch
                val photoFile = File(app.filesDir, "profile_photo_student.jpg")
                photoFile.outputStream().use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
                inputStream.close()
                repository.updateStudentPhoto(photoFile.absolutePath)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun removeStudentPhoto() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val app = getApplication<Application>()
                val photoFile = File(app.filesDir, "profile_photo_student.jpg")
                if (photoFile.exists()) {
                    photoFile.delete()
                }
                repository.updateStudentPhoto(null)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun uploadStudentBanner(uri: Uri) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val app = getApplication<Application>()
                val inputStream = app.contentResolver.openInputStream(uri) ?: return@launch
                val bannerFile = File(app.filesDir, "profile_banner_student.jpg")
                bannerFile.outputStream().use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
                inputStream.close()
                repository.updateStudentBanner(bannerFile.absolutePath)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun removeStudentBanner() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val app = getApplication<Application>()
                val bannerFile = File(app.filesDir, "profile_banner_student.jpg")
                if (bannerFile.exists()) {
                    bannerFile.delete()
                }
                repository.updateStudentBanner(null)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun uploadProfileAsset(assetKey: String, uri: Uri) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val app = getApplication<Application>()
                val inputStream = app.contentResolver.openInputStream(uri) ?: return@launch
                val safeKey = assetKey.replace(" ", "_").lowercase()
                val assetFile = File(app.filesDir, "asset_${safeKey}.jpg")
                assetFile.outputStream().use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
                inputStream.close()
                repository.saveProfileAsset(assetKey, assetFile.absolutePath)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun removeProfileAsset(assetKey: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val app = getApplication<Application>()
                val safeKey = assetKey.replace(" ", "_").lowercase()
                val assetFile = File(app.filesDir, "asset_${safeKey}.jpg")
                if (assetFile.exists()) {
                    assetFile.delete()
                }
                repository.deleteProfileAsset(assetKey)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun uploadContactPhoto(contactName: String, uri: Uri) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val app = getApplication<Application>()
                val inputStream = app.contentResolver.openInputStream(uri) ?: return@launch
                val safeName = contactName.replace(" ", "_").lowercase()
                val photoFile = File(app.filesDir, "contact_${safeName}.jpg")
                photoFile.outputStream().use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
                inputStream.close()
                repository.saveContactAvatar(contactName, photoFile.absolutePath)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Navigation methods
    fun navigateToPage(page: Int) {
        _currentPage.value = page
        when (page) {
            11, 24 -> _currentTab.value = NavTab.HOME
            27, 40, 41, 42 -> _currentTab.value = NavTab.SEARCH
            34 -> _currentTab.value = NavTab.GUIDE
            35 -> _currentTab.value = NavTab.SOCIAL
            36, 37, 38, 39 -> _currentTab.value = NavTab.PROFILE
        }
    }

    fun goBack() {
        if (_currentPage.value != 11) {
            _currentPage.value = 11
            _currentTab.value = NavTab.HOME
        }
    }

    fun markNotificationAsRead(id: Int) {
        viewModelScope.launch {
            repository.markNotificationRead(id)
        }
    }

    fun markAllNotificationsRead() {
        viewModelScope.launch {
            repository.markAllNotificationsRead()
        }
    }

    fun clearAllNotifications() {
        viewModelScope.launch {
            repository.clearAllNotifications()
        }
    }

    fun sendChatMessage(contact: String, text: String) {
        sendUserChatMessage(contact, text)
    }

    fun selectTab(tab: NavTab) {
        _currentTab.value = tab
        when (tab) {
            NavTab.HOME -> _currentPage.value = 11
            NavTab.SEARCH -> _currentPage.value = 27
            NavTab.GUIDE -> _currentPage.value = 34
            NavTab.SOCIAL -> _currentPage.value = 35
            NavTab.PROFILE -> _currentPage.value = 36
        }
    }

    fun openDocument(doc: DocumentItemEntity) {
        selectedDocument.value = doc
        downloadOrVerifyDocument(doc)
    }

    fun closeDocument() {
        selectedDocument.value = null
    }

    fun downloadOrVerifyDocument(doc: DocumentItemEntity) {
        viewModelScope.launch {
            repository.addNotification(
                title = "Verified: ${doc.title}",
                description = "${doc.documentNo} (${doc.issuedBy}) verified with SHA-256 hash.",
                category = "Document"
            )
            com.example.util.NotificationHelper.sendKycNotification(
                getApplication(),
                "📄 Document Verified: ${doc.title}",
                "${doc.title} (${doc.documentNo}) cryptographic integrity verified on Odisha EduLocker."
            )
        }
    }

    fun createSocialPost(content: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val newPost = SocialPostEntity(
                id = "post_${System.currentTimeMillis()}",
                author = "Laxmikant Nayak",
                handle = "ODIST-KMD-PLBM-LNO01 • GHSS Phulbani",
                timeAgo = "Just now",
                content = content,
                likesCount = 1,
                isLiked = true,
                timestamp = System.currentTimeMillis()
            )
            repository.addNewPost(newPost)
            repository.addNotification(
                title = "Post Published to Community",
                description = "Your post has been shared with students and faculty at GHSS Phulbani.",
                category = "Social"
            )
            com.example.util.NotificationHelper.sendSocialNotification(
                getApplication(),
                "📢 Post Published to EduLocker Community",
                "Your post has been broadcasted to the student feed: \"${content.take(45)}...\""
            )
        }
    }

    fun sendUserChatMessage(contact: String, text: String) {
        if (text.isBlank()) return
        viewModelScope.launch {
            repository.sendChatMessage(contact, text, isFromUser = true)
            kotlinx.coroutines.delay(1000)
            val reply = when (contact) {
                "Ashneer Gorver" -> "That's great! Keep focusing on your Olympiad prep and tech projects."
                "Derric Hudston" -> "Awesome. Let me know when you finish the HP LIFE Web Dev course."
                "Sushree Adyasha Mohanty" -> "Noted Laxmikant. Make sure your practical index is verified."
                else -> "Received. We'll update the record in the school EduLocker portal."
            }
            repository.sendChatMessage(contact, reply, isFromUser = false)
            com.example.util.NotificationHelper.sendChatNotification(
                getApplication(),
                "💬 Message from $contact",
                reply
            )
        }
    }

    fun askRunwayAi(query: String) {
        if (query.isBlank()) return
        val userMsg = AiMessage("Laxmikant", query, true, "Just now")
        aiMessages.value = aiMessages.value + userMsg
        aiInputText.value = ""
        isAiThinking.value = true

        viewModelScope.launch {
            kotlinx.coroutines.delay(1200)
            val answer = generateAiAnswer(query)
            val aiMsg = AiMessage("Runway AI", answer, false, "Just now")
            aiMessages.value = aiMessages.value + aiMsg
            isAiThinking.value = false
        }
    }

    private fun generateAiAnswer(query: String): String {
        val q = query.lowercase()
        return when {
            "scholarship" in q || "post matric" in q ->
                "The Odisha Post-Matric Scholarship 2026-27 offers up to ₹12,000/year for Class 11 & 12 Science students. Your application status is Verified with Parent ID (Sachidananda Nayak) linked. No physical hardcopies are needed as your biometric Video KYC is completed!"

            "olympiad" in q || "trophy" in q ->
                "The State Icon Olympiad Trophy Series starts on 10 Dec 2026 with a ₹50,000 prize pool. Subjects include Mathematics, Geometry in Action, and Physics. You are already registered with Student ID ODIST-KMD-PLBM-LNO01."

            "hp life" in q || "course" in q || "web dev" in q || "developer" in q ->
                "You are enrolled in the HP LIFE Developer Program: 'Introduction to Web Development' (Progress: 1/17). Upon finishing all 17 lessons and the capstone challenge, your official verifiable certificate will automatically appear in your EduLocker."

            "apaar" in q || "marksheet" in q || "document" in q ->
                "All your academic documents (Class X Marksheet BSE 2024, Class XI Marksheet CHSE 2025, APAAR ID, and Aadhaar) are cryptographically signed with SHA-256 integrity hashes on the Odisha State Block-Locker network."

            "school" in q || "phulbani" in q ->
                "Govt. Higher Secondary School, Phulbani (School ID: GHSSP-PLBM-KMD-ODIST, AOR 56) offers 350 Science seats, 200 Commerce seats, 400 Arts seats, and 290 hostel accommodation capacity."

            else ->
                "EduLocker gives you a unified ecosystem for all Odisha student services. You can track your scholarships, verify biometric KYC, download tamper-proof marksheets, and earn global certifications from HP LIFE!"
        }
    }

    fun getChatMessages(contact: String) = repository.getChatMessages(contact)

    fun submitScholarshipApplication() {
        val appNo = "OD-PMS-2026-${(10000..99999).random()}"
        viewModelScope.launch {
            repository.submitScholarship(
                ScholarshipApplicationEntity(
                    applicationNo = appNo,
                    mobileNumber = scholarshipMobile.value,
                    aadharNumber = scholarshipAadhaarNo.value,
                    casteCategory = scholarshipCasteCategory.value,
                    parentIdLinked = scholarshipParentLinked.value,
                    incomeConsent = scholarshipIncomeConsent.value,
                    attendanceConsent = scholarshipAttendanceConsent.value,
                    academicConsent = scholarshipAcademicConsent.value,
                    feePaidAmount = 9,
                    status = "Submitted"
                )
            )
            repository.addNotification(
                title = "Scholarship Lodged ($appNo)",
                description = "Your Odisha Post-Matric Scholarship application has been submitted to GHSS Phulbani.",
                category = "Scholarship"
            )
            com.example.util.NotificationHelper.sendScholarshipNotification(
                getApplication(),
                "🎓 Scholarship Submitted ($appNo)",
                "Your Odisha Post-Matric Scholarship application has been successfully lodged and sent for institution verification."
            )
        }
    }

    fun applyScholarshipDirect(title: String, amount: String) {
        val refNo = "OD-SCH-${(10000..99999).random()}"
        viewModelScope.launch {
            repository.addNotification(
                title = "Application Lodged: $title",
                description = "Ref: $refNo. Grant: $amount. Auto-verified via APAAR ID OD-APAAR-762-0019-1418.",
                category = "Scholarship"
            )
            com.example.util.NotificationHelper.sendScholarshipNotification(
                getApplication(),
                "🎓 Scholarship Applied: $title",
                "Your application ($refNo) for $amount has been submitted with verified APAAR ID."
            )
        }
    }

    fun recordFeePayment(amount: Int = 9) {
        val txnId = "TXN_ODI_${(10000..99999).random()}"
        viewModelScope.launch {
            repository.addNotification(
                title = "Fee Payment Successful: ₹$amount",
                description = "Processing fee payment verified (Ref: $txnId).",
                category = "Scholarship"
            )
            com.example.util.NotificationHelper.sendScholarshipNotification(
                getApplication(),
                "💳 Payment Successful (₹$amount)",
                "Payment received via UPI (Ref: $txnId) for Post-Matric Scholarship processing."
            )
        }
    }

    fun saveScholarshipDraft() {
        viewModelScope.launch {
            repository.addNotification(
                title = "Scholarship Saved as Draft",
                description = "Your Post-Matric Scholarship application details are saved. Pay ₹9 to submit.",
                category = "Scholarship"
            )
            com.example.util.NotificationHelper.sendScholarshipNotification(
                getApplication(),
                "📝 Scholarship Draft Saved",
                "Your Post-Matric application draft is safely stored in your locker."
            )
        }
    }

    fun registerForOlympiad() {
        viewModelScope.launch {
            repository.addNotification(
                title = "Olympiad Registration Confirmed",
                description = "State Icon Olympiad Series registration confirmed. Exam Date: 10 Dec 2026.",
                category = "Olympiad"
            )
            com.example.util.NotificationHelper.sendOlympiadNotification(
                getApplication(),
                "🏆 Olympiad Registration Confirmed!",
                "You are enrolled in State Icon Olympiad Series. Prep materials unlocked in your locker."
            )
        }
    }

    fun saveCourseForLater(courseName: String, lessonTitle: String) {
        viewModelScope.launch {
            repository.addNotification(
                title = "Course Saved: $courseName",
                description = "Lesson '$lessonTitle' bookmarked to offline library.",
                category = "Course"
            )
            com.example.util.NotificationHelper.sendCourseNotification(
                getApplication(),
                "📚 HP LIFE Course Saved",
                "'$lessonTitle' ($courseName) bookmarked to your offline study locker."
            )
        }
    }

    fun completeCourseLesson(courseName: String, lessonTitle: String) {
        viewModelScope.launch {
            repository.addNotification(
                title = "Lesson Completed: $lessonTitle",
                description = "Great job finishing $lessonTitle in $courseName!",
                category = "Course"
            )
            com.example.util.NotificationHelper.sendCourseNotification(
                getApplication(),
                "🎉 Lesson Completed!",
                "Great work on completing '$lessonTitle'! Up next: Module Challenge."
            )
        }
    }

    fun verifyKycSuccess() {
        viewModelScope.launch {
            repository.addNotification(
                title = "Video KYC Verified",
                description = "Facial biometric identity matched with UIDAI repository.",
                category = "KYC"
            )
            com.example.util.NotificationHelper.sendKycNotification(
                getApplication(),
                "✅ Biometric Video KYC Verified",
                "Your live video facial biometric matched with UIDAI. Verification complete!"
            )
        }
    }

    fun syncAadhaarSuccess() {
        viewModelScope.launch {
            repository.addNotification(
                title = "Aadhaar e-KYC Linked",
                description = "Aadhaar XXXX-XXXX-1418 verified with OTP.",
                category = "KYC"
            )
            com.example.util.NotificationHelper.sendKycNotification(
                getApplication(),
                "🔒 Aadhaar e-KYC Linked",
                "Aadhaar Card (XXXX-XXXX-1418) verified and synced to EduLocker."
            )
        }
    }

    fun sendTestPushNotification(
        title: String = "🔔 EduLocker Push Notification",
        body: String = "Real-time push notifications are active and verified on your device!"
    ) {
        viewModelScope.launch {
            repository.addNotification(
                title = title,
                description = body,
                category = "General"
            )
            com.example.util.NotificationHelper.sendGeneralNotification(
                getApplication(),
                title,
                body
            )
        }
    }

    fun updateProfile(name: String, bloodGroup: String, address: String) {
        viewModelScope.launch {
            val current = studentProfile.value ?: StudentProfileEntity()
            repository.saveProfile(
                current.copy(
                    fullName = name,
                    bloodGroup = bloodGroup,
                    address = address
                )
            )
            repository.addNotification(
                title = "Profile Updated",
                description = "Student profile changes saved for $name.",
                category = "Profile"
            )
            com.example.util.NotificationHelper.sendGeneralNotification(
                getApplication(),
                "👤 Profile Updated Successfully",
                "Your student details and credentials have been updated."
            )
        }
    }
}
