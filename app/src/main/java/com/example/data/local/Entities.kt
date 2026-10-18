package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "student_profile")
data class StudentProfileEntity(
    @PrimaryKey val id: Int = 1,
    val fullName: String = "Laxmikant Nayak",
    val isVerified: Boolean = true,
    val schoolName: String = "Govt. Higher Secondary School",
    val schoolAor: String = "AOR 56",
    val schoolLocation: String = "Phulbani, Kandhamal, Odisha",
    val eduLockerId: String = "ODIST-KMD-PLBM-LNO01",
    val userId: String = "01009864",
    val region: String = "Kandhamal, Odisha, India",
    val parentName: String = "Sachidananda Nayak",
    val dob: String = "14.08.2007",
    val bloodGroup: String = "B +ve",
    val address: String = "Phulbani, Kandhamal, Odisha",
    val pinCode: String = "762001",
    val studentClass: String = "12th Science",
    val ekycStatus: String = "Verified",
    val githubUrl: String = "github.com/laxmikant14",
    val linkedinUrl: String = "linkedin.com/in/laxmikant-nayak",
    val discordHandle: String = "laxmi_k#1418",
    val unstopUrl: String = "unstop.com/u/laxmikant1418",
    val photoUri: String? = null,
    val bannerUri: String? = null
)

@Entity(tableName = "custom_profile_assets")
data class CustomProfileAssetEntity(
    @PrimaryKey val assetKey: String,
    val photoUri: String,
    val updatedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "contact_avatars")
data class ContactAvatarEntity(
    @PrimaryKey val contactName: String,
    val photoUri: String,
    val updatedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "scholarship_applications")
data class ScholarshipApplicationEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val applicationNo: String = "OD-PMS-2026-98124",
    val eduLockerId: String = "ODIST-KMD-PLBM-LNO01",
    val mobileNumber: String = "+91 98610 54321",
    val aadharNumber: String = "XXXX-XXXX-1418",
    val casteCategory: String = "General / SEBC",
    val parentIdLinked: Boolean = true,
    val incomeConsent: Boolean = true,
    val attendanceConsent: Boolean = true,
    val academicConsent: Boolean = true,
    val feePaidAmount: Int = 9,
    val status: String = "Submitted",
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "digital_documents")
data class DocumentItemEntity(
    @PrimaryKey val id: String,
    val title: String,
    val subtitle: String,
    val documentNo: String,
    val issuedBy: String,
    val sha256Hash: String,
    val issueDate: String,
    val fileSize: String,
    val isVerified: Boolean = true
)

@Entity(tableName = "notifications")
data class NotificationItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String,
    val category: String, // School, Exam, Scholarship, Olympiad, Course, Achievement
    val timeAgo: String,
    val isRead: Boolean = false
)

@Entity(tableName = "chat_messages")
data class ChatMessageEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val contactName: String,
    val messageText: String,
    val isFromUser: Boolean,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "social_posts")
data class SocialPostEntity(
    @PrimaryKey val id: String,
    val author: String,
    val handle: String,
    val timeAgo: String,
    val content: String,
    val likesCount: Int,
    val isLiked: Boolean = false,
    val imageResId: String? = null,
    val customImageUri: String? = null,
    val customTheme: String? = null,
    val timestamp: Long = System.currentTimeMillis()
)
