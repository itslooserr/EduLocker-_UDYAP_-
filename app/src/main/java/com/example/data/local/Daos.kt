package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface StudentProfileDao {
    @Query("SELECT * FROM student_profile WHERE id = 1 LIMIT 1")
    fun getProfile(): Flow<StudentProfileEntity?>

    @Query("SELECT * FROM student_profile WHERE id = 1 LIMIT 1")
    suspend fun getProfileDirect(): StudentProfileEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateProfile(profile: StudentProfileEntity)

    @Query("UPDATE student_profile SET photoUri = :photoUri WHERE id = 1")
    suspend fun updateProfilePhoto(photoUri: String?)

    @Query("UPDATE student_profile SET bannerUri = :bannerUri WHERE id = 1")
    suspend fun updateProfileBanner(bannerUri: String?)
}

@Dao
interface CustomProfileAssetDao {
    @Query("SELECT * FROM custom_profile_assets")
    fun getAllAssets(): Flow<List<CustomProfileAssetEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAsset(asset: CustomProfileAssetEntity)

    @Query("DELETE FROM custom_profile_assets WHERE assetKey = :assetKey")
    suspend fun deleteAsset(assetKey: String)
}

@Dao
interface ContactAvatarDao {
    @Query("SELECT * FROM contact_avatars")
    fun getAllAvatars(): Flow<List<ContactAvatarEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAvatar(avatar: ContactAvatarEntity)
}

@Dao
interface ScholarshipDao {
    @Query("SELECT * FROM scholarship_applications ORDER BY id DESC")
    fun getAllApplications(): Flow<List<ScholarshipApplicationEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertApplication(app: ScholarshipApplicationEntity): Long
}

@Dao
interface DocumentDao {
    @Query("SELECT * FROM digital_documents")
    fun getAllDocuments(): Flow<List<DocumentItemEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDocuments(docs: List<DocumentItemEntity>)
}

@Dao
interface NotificationDao {
    @Query("SELECT * FROM notifications ORDER BY id DESC")
    fun getAllNotifications(): Flow<List<NotificationItemEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotifications(notifications: List<NotificationItemEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotification(notification: NotificationItemEntity): Long

    @Query("UPDATE notifications SET isRead = 1 WHERE id = :id")
    suspend fun markAsRead(id: Int)

    @Query("UPDATE notifications SET isRead = 1")
    suspend fun markAllAsRead()

    @Query("DELETE FROM notifications")
    suspend fun clearAllNotifications()
}

@Dao
interface ChatDao {
    @Query("SELECT * FROM chat_messages WHERE contactName = :contactName ORDER BY id ASC")
    fun getMessagesForContact(contactName: String): Flow<List<ChatMessageEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(msg: ChatMessageEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessages(msgs: List<ChatMessageEntity>)
}

@Dao
interface SocialPostDao {
    @Query("SELECT * FROM social_posts ORDER BY timestamp ASC")
    fun getAllPosts(): Flow<List<SocialPostEntity>>

    @Query("SELECT COUNT(*) FROM social_posts")
    suspend fun getPostCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPosts(posts: List<SocialPostEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPost(post: SocialPostEntity)

    @Query("UPDATE social_posts SET customImageUri = :imageUri WHERE id = :postId")
    suspend fun updatePostPhoto(postId: String, imageUri: String?)

    @Query("UPDATE social_posts SET customTheme = :theme WHERE id = :postId")
    suspend fun updatePostTheme(postId: String, theme: String?)

    @Query("UPDATE social_posts SET content = :content WHERE id = :postId")
    suspend fun updatePostContent(postId: String, content: String)

    @Query("UPDATE social_posts SET isLiked = :isLiked, likesCount = :likesCount WHERE id = :postId")
    suspend fun updateLike(postId: String, isLiked: Boolean, likesCount: Int)
}
