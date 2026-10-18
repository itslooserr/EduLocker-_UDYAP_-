package com.example.util

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.MainActivity

object NotificationHelper {
    const val CHANNEL_SCHOLARSHIP = "channel_scholarship"
    const val CHANNEL_ACTIVITY = "channel_activity"
    const val CHANNEL_ACADEMIC = "channel_academic"
    const val CHANNEL_SECURITY = "channel_security"
    const val CHANNEL_SOCIAL = "channel_social"

    fun createNotificationChannels(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val scholarshipChannel = NotificationChannel(
                CHANNEL_SCHOLARSHIP,
                "Scholarships & Grants",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Updates on state scholarship applications, disbursements & grants"
                enableVibration(true)
                vibrationPattern = longArrayOf(0, 250, 150, 250)
            }

            val activityChannel = NotificationChannel(
                CHANNEL_ACTIVITY,
                "Olympiads & Competitions",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Instant notifications on Olympiad tests, results & state rankings"
                enableVibration(true)
                vibrationPattern = longArrayOf(0, 200, 100, 200)
            }

            val academicChannel = NotificationChannel(
                CHANNEL_ACADEMIC,
                "HP LIFE Courses & Certifications",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Lesson milestones, certifications, code completions & study tracks"
                enableVibration(true)
            }

            val securityChannel = NotificationChannel(
                CHANNEL_SECURITY,
                "e-KYC & Document Vault",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Biometric Aadhaar e-KYC status, APAAR ID sync & cryptographic hashes"
                enableVibration(true)
            }

            val socialChannel = NotificationChannel(
                CHANNEL_SOCIAL,
                "EduLocker Social & Chat Hub",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Direct messages from teachers, classmates and community posts"
                enableVibration(true)
            }

            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager
            notificationManager?.let {
                it.createNotificationChannel(scholarshipChannel)
                it.createNotificationChannel(activityChannel)
                it.createNotificationChannel(academicChannel)
                it.createNotificationChannel(securityChannel)
                it.createNotificationChannel(socialChannel)
            }
        }
    }

    fun sendScholarshipNotification(
        context: Context,
        title: String,
        body: String,
        notificationId: Int = (System.currentTimeMillis() % 100000).toInt()
    ) {
        sendNotification(context, CHANNEL_SCHOLARSHIP, title, body, notificationId)
    }

    fun sendOlympiadNotification(
        context: Context,
        title: String,
        body: String,
        notificationId: Int = (System.currentTimeMillis() % 100000).toInt()
    ) {
        sendNotification(context, CHANNEL_ACTIVITY, title, body, notificationId)
    }

    fun sendActivityNotification(
        context: Context,
        title: String,
        body: String,
        notificationId: Int = (System.currentTimeMillis() % 100000).toInt()
    ) {
        sendNotification(context, CHANNEL_ACTIVITY, title, body, notificationId)
    }

    fun sendAcademicNotification(
        context: Context,
        title: String,
        body: String,
        notificationId: Int = (System.currentTimeMillis() % 100000).toInt()
    ) {
        sendNotification(context, CHANNEL_ACADEMIC, title, body, notificationId)
    }

    fun sendCourseNotification(
        context: Context,
        title: String,
        body: String,
        notificationId: Int = (System.currentTimeMillis() % 100000).toInt()
    ) {
        sendNotification(context, CHANNEL_ACADEMIC, title, body, notificationId)
    }

    fun sendKycNotification(
        context: Context,
        title: String,
        body: String,
        notificationId: Int = (System.currentTimeMillis() % 100000).toInt()
    ) {
        sendNotification(context, CHANNEL_SECURITY, title, body, notificationId)
    }

    fun sendSocialNotification(
        context: Context,
        title: String,
        body: String,
        notificationId: Int = (System.currentTimeMillis() % 100000).toInt()
    ) {
        sendNotification(context, CHANNEL_SOCIAL, title, body, notificationId)
    }

    fun sendChatNotification(
        context: Context,
        title: String,
        body: String,
        notificationId: Int = (System.currentTimeMillis() % 100000).toInt()
    ) {
        sendNotification(context, CHANNEL_SOCIAL, title, body, notificationId)
    }

    fun sendGeneralNotification(
        context: Context,
        title: String,
        body: String,
        notificationId: Int = (System.currentTimeMillis() % 100000).toInt()
    ) {
        sendNotification(context, CHANNEL_SCHOLARSHIP, title, body, notificationId)
    }

    fun sendNotification(
        context: Context,
        channelId: String,
        title: String,
        body: String,
        notificationId: Int = (System.currentTimeMillis() % 100000).toInt()
    ) {
        createNotificationChannels(context)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // Return gracefully if permission not granted
                return
            }
        }

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(
            context,
            notificationId,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(title)
            .setContentText(body)
            .setStyle(NotificationCompat.BigTextStyle().bigText(body))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setDefaults(NotificationCompat.DEFAULT_ALL)

        try {
            val notificationManager = NotificationManagerCompat.from(context)
            notificationManager.notify(notificationId, builder.build())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
