package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        StudentProfileEntity::class,
        ScholarshipApplicationEntity::class,
        DocumentItemEntity::class,
        NotificationItemEntity::class,
        ChatMessageEntity::class,
        ContactAvatarEntity::class,
        CustomProfileAssetEntity::class,
        SocialPostEntity::class
    ],
    version = 4,
    exportSchema = false
)
abstract class EduLockerDatabase : RoomDatabase() {
    abstract fun studentProfileDao(): StudentProfileDao
    abstract fun scholarshipDao(): ScholarshipDao
    abstract fun documentDao(): DocumentDao
    abstract fun notificationDao(): NotificationDao
    abstract fun chatDao(): ChatDao
    abstract fun contactAvatarDao(): ContactAvatarDao
    abstract fun customProfileAssetDao(): CustomProfileAssetDao
    abstract fun socialPostDao(): SocialPostDao

    companion object {
        @Volatile
        private var INSTANCE: EduLockerDatabase? = null

        fun getDatabase(context: Context): EduLockerDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    EduLockerDatabase::class.java,
                    "edulocker_database"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
