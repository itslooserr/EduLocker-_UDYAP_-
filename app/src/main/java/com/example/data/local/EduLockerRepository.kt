package com.example.data.local

import kotlinx.coroutines.flow.Flow

class EduLockerRepository(private val database: EduLockerDatabase) {
    val studentProfile: Flow<StudentProfileEntity?> = database.studentProfileDao().getProfile()
    val scholarshipApplications: Flow<List<ScholarshipApplicationEntity>> = database.scholarshipDao().getAllApplications()
    val digitalDocuments: Flow<List<DocumentItemEntity>> = database.documentDao().getAllDocuments()
    val notifications: Flow<List<NotificationItemEntity>> = database.notificationDao().getAllNotifications()
    val contactAvatars: Flow<List<ContactAvatarEntity>> = database.contactAvatarDao().getAllAvatars()
    val customProfileAssets: Flow<List<CustomProfileAssetEntity>> = database.customProfileAssetDao().getAllAssets()
    val socialPosts: Flow<List<SocialPostEntity>> = database.socialPostDao().getAllPosts()

    fun getChatMessages(contactName: String): Flow<List<ChatMessageEntity>> {
        return database.chatDao().getMessagesForContact(contactName)
    }

    suspend fun saveProfile(profile: StudentProfileEntity) {
        database.studentProfileDao().insertOrUpdateProfile(profile)
    }

    suspend fun updateStudentPhoto(photoUri: String?) {
        database.studentProfileDao().updateProfilePhoto(photoUri)
    }

    suspend fun updateStudentBanner(bannerUri: String?) {
        database.studentProfileDao().updateProfileBanner(bannerUri)
    }

    suspend fun saveProfileAsset(assetKey: String, photoUri: String) {
        database.customProfileAssetDao().saveAsset(
            CustomProfileAssetEntity(
                assetKey = assetKey,
                photoUri = photoUri
            )
        )
    }

    suspend fun deleteProfileAsset(assetKey: String) {
        database.customProfileAssetDao().deleteAsset(assetKey)
    }

    suspend fun saveContactAvatar(contactName: String, photoUri: String) {
        database.contactAvatarDao().saveAvatar(
            ContactAvatarEntity(
                contactName = contactName,
                photoUri = photoUri
            )
        )
    }

    suspend fun updatePostPhoto(postId: String, imageUri: String?) {
        database.socialPostDao().updatePostPhoto(postId, imageUri)
    }

    suspend fun updatePostTheme(postId: String, theme: String?) {
        database.socialPostDao().updatePostTheme(postId, theme)
    }

    suspend fun updatePostContent(postId: String, content: String) {
        database.socialPostDao().updatePostContent(postId, content)
    }

    suspend fun togglePostLike(postId: String, isLiked: Boolean, likesCount: Int) {
        database.socialPostDao().updateLike(postId, isLiked, likesCount)
    }

    suspend fun addNewPost(post: SocialPostEntity) {
        database.socialPostDao().insertPost(post)
    }

    suspend fun submitScholarship(app: ScholarshipApplicationEntity): Long {
        return database.scholarshipDao().insertApplication(app)
    }

    suspend fun sendChatMessage(contactName: String, text: String, isFromUser: Boolean = true) {
        database.chatDao().insertMessage(
            ChatMessageEntity(
                contactName = contactName,
                messageText = text,
                isFromUser = isFromUser
            )
        )
    }

    suspend fun markNotificationRead(id: Int) {
        database.notificationDao().markAsRead(id)
    }

    suspend fun markAllNotificationsRead() {
        database.notificationDao().markAllAsRead()
    }

    suspend fun clearAllNotifications() {
        database.notificationDao().clearAllNotifications()
    }

    suspend fun addNotification(
        title: String,
        description: String,
        category: String,
        timeAgo: String = "Just now"
    ) {
        database.notificationDao().insertNotification(
            NotificationItemEntity(
                title = title,
                description = description,
                category = category,
                timeAgo = timeAgo,
                isRead = false
            )
        )
    }

    suspend fun seedInitialDataIfEmpty() {
        val existingProfile = database.studentProfileDao().getProfileDirect()
        if (existingProfile == null) {
            // Default student profile only if no profile exists yet
            database.studentProfileDao().insertOrUpdateProfile(
                StudentProfileEntity(
                    id = 1,
                    fullName = "Laxmikant Nayak",
                    isVerified = true,
                    schoolName = "Govt. Higher Secondary School",
                    schoolAor = "AOR 56",
                    schoolLocation = "Phulbani, Kandhamal, Odisha",
                    eduLockerId = "ODIST-KMD-PLBM-LNO01",
                    userId = "01009864",
                    region = "Kandhamal, Odisha, India",
                    parentName = "Sachidananda Nayak",
                    dob = "14.08.2007",
                    bloodGroup = "B +ve",
                    address = "Phulbani, Kandhamal, Odisha",
                    pinCode = "762001",
                    studentClass = "12th Science",
                    ekycStatus = "Verified"
                )
            )
        }

        // Seed 20 verified student documents in locker
        val initialDocs = listOf(
            DocumentItemEntity(
                id = "aadhar",
                title = "Aadhar Card",
                subtitle = "Unique Identification Authority of India",
                documentNo = "XXXX-XXXX-1418",
                issuedBy = "UIDAI, Govt. of India",
                sha256Hash = "8f9b2a7d4e1c0b3f89e21045da671efbc9901418a0029bce18471928374a56c0",
                issueDate = "18.04.2016",
                fileSize = "2.4 MB"
            ),
            DocumentItemEntity(
                id = "pan",
                title = "PAN Card",
                subtitle = "Income Tax Department of India",
                documentNo = "CPXPN1418K",
                issuedBy = "Govt. of India",
                sha256Hash = "a3e47b9192c019284ba5c719820f12847bbac0192847aefbc00192837418a002",
                issueDate = "05.11.2024",
                fileSize = "1.8 MB"
            ),
            DocumentItemEntity(
                id = "x_marksheet",
                title = "X Marksheet",
                subtitle = "High School Certificate Examination 2024",
                documentNo = "BSE-ODI-2024-81920",
                issuedBy = "Board of Secondary Education, Odisha",
                sha256Hash = "7c9810a90bc7819fba2014c0091823746a5ef1092837418290bc9812739410ea",
                issueDate = "22.05.2024",
                fileSize = "3.2 MB"
            ),
            DocumentItemEntity(
                id = "aapar",
                title = "AAPAR ID",
                subtitle = "Automated Permanent Academic Registry",
                documentNo = "OD-APAAR-762-0019-1418",
                issuedBy = "Ministry of Education, Govt. of India",
                sha256Hash = "3f89a01c9b2e71829304a5ef0192837418290bc9812739410ea7c9810a90bc78",
                issueDate = "10.01.2025",
                fileSize = "1.5 MB"
            ),
            DocumentItemEntity(
                id = "xi_marksheet",
                title = "XI Marksheet",
                subtitle = "Higher Secondary Examination (Annual)",
                documentNo = "CHSE-ODI-2025-14189",
                issuedBy = "Council of Higher Secondary Education, Odisha",
                sha256Hash = "5a09b18273645cdef0192837418290bc9812739410ea7c9810a90bc7819fba20",
                issueDate = "15.06.2025",
                fileSize = "2.9 MB"
            ),
            DocumentItemEntity(
                id = "halfyearly_marksheet",
                title = "Halfyearly Marksheet",
                subtitle = "Class 12 Science Mid-Term Evaluation",
                documentNo = "GHSS-HY-2025-01418",
                issuedBy = "Govt. Higher Secondary School, Phulbani",
                sha256Hash = "1418f789abc091823746a5ef1092837418290bc9812739410ea7c9810a90bc78",
                issueDate = "20.12.2025",
                fileSize = "2.1 MB"
            ),
            DocumentItemEntity(
                id = "resident_cert",
                title = "Resident Certificate",
                subtitle = "e-District Odisha Permanent Resident Proof",
                documentNo = "ODI-RES-2024-99812",
                issuedBy = "Revenue & Disaster Management, Odisha",
                sha256Hash = "b4a09c8192038475ef0192837418290bc9812739410ea7c9810a90bc7819fba2",
                issueDate = "12.08.2024",
                fileSize = "1.6 MB"
            ),
            DocumentItemEntity(
                id = "caste_cert",
                title = "Caste Certificate",
                subtitle = "SEBC Category Certificate",
                documentNo = "ODI-SEBC-2024-41892",
                issuedBy = "Tahasildar Phulbani, Govt. of Odisha",
                sha256Hash = "c789a01c9b2e71829304a5ef0192837418290bc9812739410ea7c9810a90bc78",
                issueDate = "14.08.2024",
                fileSize = "1.4 MB"
            ),
            DocumentItemEntity(
                id = "income_cert",
                title = "Income Certificate",
                subtitle = "Annual Family Income Statement (SEBC)",
                documentNo = "ODI-INC-2025-10492",
                issuedBy = "Tahasildar Phulbani, Govt. of Odisha",
                sha256Hash = "d901a01c9b2e71829304a5ef0192837418290bc9812739410ea7c9810a90bc78",
                issueDate = "05.01.2025",
                fileSize = "1.2 MB"
            ),
            DocumentItemEntity(
                id = "transfer_cert",
                title = "Transfer Certificate (TC)",
                subtitle = "Institutional Relocation & Conduct Record",
                documentNo = "TC-GHSS-2024-089",
                issuedBy = "Principal, GHSS Phulbani",
                sha256Hash = "e123a01c9b2e71829304a5ef0192837418290bc9812739410ea7c9810a90bc78",
                issueDate = "10.07.2024",
                fileSize = "2.0 MB"
            ),
            DocumentItemEntity(
                id = "migration_cert",
                title = "Migration Certificate",
                subtitle = "Secondary School Board Migration",
                documentNo = "BSE-MIG-2024-3381",
                issuedBy = "Board of Secondary Education, Odisha",
                sha256Hash = "f456a01c9b2e71829304a5ef0192837418290bc9812739410ea7c9810a90bc78",
                issueDate = "01.06.2024",
                fileSize = "1.7 MB"
            ),
            DocumentItemEntity(
                id = "character_cert",
                title = "Character Certificate",
                subtitle = "Bonafide Academic & Moral Standing",
                documentNo = "CC-GHSS-2025-141",
                issuedBy = "Govt. Higher Secondary School, Phulbani",
                sha256Hash = "0987a01c9b2e71829304a5ef0192837418290bc9812739410ea7c9810a90bc78",
                issueDate = "18.01.2025",
                fileSize = "1.1 MB"
            ),
            DocumentItemEntity(
                id = "bonafide_cert",
                title = "Bonafide Student Certificate",
                subtitle = "Current Academic Enrolment Verification",
                documentNo = "BON-GHSS-2025-782",
                issuedBy = "Govt. Higher Secondary School, Phulbani",
                sha256Hash = "1234a01c9b2e71829304a5ef0192837418290bc9812739410ea7c9810a90bc78",
                issueDate = "02.02.2025",
                fileSize = "1.3 MB"
            ),
            DocumentItemEntity(
                id = "ncc_cert",
                title = "NCC 'A' Certificate",
                subtitle = "National Cadet Corps Junior Division",
                documentNo = "NCC-ODI-2024-A-1418",
                issuedBy = "Directorate of NCC, Odisha",
                sha256Hash = "5678a01c9b2e71829304a5ef0192837418290bc9812739410ea7c9810a90bc78",
                issueDate = "28.02.2024",
                fileSize = "3.5 MB"
            ),
            DocumentItemEntity(
                id = "science_olympiad_cert",
                title = "Science Olympiad Merit Certificate",
                subtitle = "State Level Science Talent Search 2025",
                documentNo = "SLSTS-ODI-2025-GOLD-14",
                issuedBy = "Odisha Bigyan Academy",
                sha256Hash = "9101a01c9b2e71829304a5ef0192837418290bc9812739410ea7c9810a90bc78",
                issueDate = "14.11.2025",
                fileSize = "2.8 MB"
            ),
            DocumentItemEntity(
                id = "sports_cert",
                title = "State Athletics Certificate",
                subtitle = "State Youth Sports & Athletics Meet",
                documentNo = "SYS-ODI-2024-ATH-82",
                issuedBy = "Sports & Youth Services Dept, Odisha",
                sha256Hash = "1121a01c9b2e71829304a5ef0192837418290bc9812739410ea7c9810a90bc78",
                issueDate = "19.12.2024",
                fileSize = "2.2 MB"
            ),
            DocumentItemEntity(
                id = "student_id_card",
                title = "College Identity Card",
                subtitle = "Higher Secondary Science Stream (2024-26)",
                documentNo = "ID-GHSS-24-SCI-014",
                issuedBy = "Govt. Higher Secondary School, Phulbani",
                sha256Hash = "3141a01c9b2e71829304a5ef0192837418290bc9812739410ea7c9810a90bc78",
                issueDate = "01.07.2024",
                fileSize = "1.9 MB"
            ),
            DocumentItemEntity(
                id = "hostel_slip",
                title = "Hostel Allocation Slip",
                subtitle = "Boys Hostel No. 2 Room 14",
                documentNo = "HSTL-GHSS-2024-B2-14",
                issuedBy = "Warden, GHSS Hostel, Phulbani",
                sha256Hash = "5161a01c9b2e71829304a5ef0192837418290bc9812739410ea7c9810a90bc78",
                issueDate = "05.07.2024",
                fileSize = "1.0 MB"
            ),
            DocumentItemEntity(
                id = "bus_pass",
                title = "Student Bus Pass",
                subtitle = "OSRTC Concessional Student Travel Pass",
                documentNo = "OSRTC-STU-2025-7819",
                issuedBy = "Odisha State Road Transport Corp.",
                sha256Hash = "7181a01c9b2e71829304a5ef0192837418290bc9812739410ea7c9810a90bc78",
                issueDate = "10.01.2025",
                fileSize = "1.5 MB"
            ),
            DocumentItemEntity(
                id = "health_card",
                title = "BSKY Student Health Card",
                subtitle = "Biju Swasthya Kalyan Yojana Digital Health Card",
                documentNo = "BSKY-ODI-2024-141890",
                issuedBy = "Health & Family Welfare Dept, Odisha",
                sha256Hash = "9202a01c9b2e71829304a5ef0192837418290bc9812739410ea7c9810a90bc78",
                issueDate = "01.03.2024",
                fileSize = "2.3 MB"
            )
        )
        database.documentDao().insertDocuments(initialDocs)

        // Seed notifications
        val initialNotifications = listOf(
            NotificationItemEntity(
                title = "New School Notice",
                description = "Your school has published a new notice. Tap to view the details.",
                category = "School",
                timeAgo = "10m ago"
            ),
            NotificationItemEntity(
                title = "Examination Schedule Updated",
                description = "The examination timetable has been updated.",
                category = "Exam",
                timeAgo = "1h ago"
            ),
            NotificationItemEntity(
                title = "Application Submitted Successfully",
                description = "Your Post Matric Scholarship application has been successfully submitted.",
                category = "Scholarship",
                timeAgo = "3h ago"
            ),
            NotificationItemEntity(
                title = "Olympiad Registration Confirmed",
                description = "Your registration for the Olympiad Series is confirmed. We'll notify you before it begins.",
                category = "Olympiad",
                timeAgo = "1d ago"
            ),
            NotificationItemEntity(
                title = "Aadhaar e-KYC Verified",
                description = "Your Aadhaar identity has been verified with UIDAI biometric vault.",
                category = "KYC",
                timeAgo = "2d ago"
            ),
            NotificationItemEntity(
                title = "Marksheet Issued to EduLocker",
                description = "Council of Higher Secondary Education (CHSE) uploaded your verified marksheet.",
                category = "Document",
                timeAgo = "3d ago"
            )
        )
        database.notificationDao().insertNotifications(initialNotifications)

        // Seed initial chat contacts
        val initialMessages = listOf(
            ChatMessageEntity(contactName = "Ashneer Gorver", messageText = "Hey Laxmikant! How is the HP LIFE Web Development module going?", isFromUser = false),
            ChatMessageEntity(contactName = "Ashneer Gorver", messageText = "I finished Lesson 4 on Responsive UI today!", isFromUser = true),
            ChatMessageEntity(contactName = "Ashneer Gorver", messageText = "Great! Make sure to take the quiz before 5 PM to get verified.", isFromUser = false),
            ChatMessageEntity(contactName = "Principal - GHSS Phulbani", messageText = "Dear Laxmikant, your Post-Matric scholarship endorsement is signed.", isFromUser = false),
            ChatMessageEntity(contactName = "Science HOD", messageText = "Reminder: Physics lab practical file submission is tomorrow.", isFromUser = false)
        )
        database.chatDao().insertMessages(initialMessages)

        // Seed initial social posts if table is empty
        if (database.socialPostDao().getPostCount() == 0) {
            val initialPosts = listOf(
                SocialPostEntity(
                    id = "post1",
                    author = "Govt. Higher Secondary School",
                    handle = "AOR 56 • Phulbani, Kandhamal, Odisha",
                    timeAgo = "1hr ago",
                    content = "Govt. Higher Secondary School: Celebrating Swachhata Abhiyan in school is a fantastic way to instill the value of hygiene, responsibility, and environmental stewardship among students! Our NSS and Eco-Club volunteers planted 200 indigenous saplings across the science block.",
                    likesCount = 582,
                    isLiked = true,
                    imageResId = "campus_green"
                ),
                SocialPostEntity(
                    id = "post2",
                    author = "Department of School & Mass Education",
                    handle = "Govt. of Odisha • Official",
                    timeAgo = "3hr ago",
                    content = "Odisha State Post-Matric Scholarship portal is now live for 2026-27 session. Eligible Class 11 and 12 students are advised to submit eKYC via EduLocker before 31st October.",
                    likesCount = 1420,
                    isLiked = false,
                    imageResId = "scholarship_banner"
                ),
                SocialPostEntity(
                    id = "post3",
                    author = "HP LIFE Global",
                    handle = "Skill & Innovation Partner",
                    timeAgo = "6hr ago",
                    content = "Congratulations to all Odisha students who cleared the Web Development Certification Level 1 on EduLocker! Keep building practical skills.",
                    likesCount = 890,
                    isLiked = true,
                    imageResId = "code_mastery"
                )
            )
            database.socialPostDao().insertPosts(initialPosts)
        }
    }
}
