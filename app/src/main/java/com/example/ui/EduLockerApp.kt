package com.example.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.components.AppPermissionsDialog
import com.example.ui.components.EduLockerFloatingBottomBar
import com.example.ui.components.EvaluatorScreenJumper
import com.example.ui.components.NavTab
import com.example.ui.screens.AadhaarVerificationScreen
import com.example.ui.screens.AchievementsScreen
import com.example.ui.screens.CourseLevelSelectorScreen
import com.example.ui.screens.CourseSelectorScreen
import com.example.ui.screens.DeveloperProgramAboutScreen
import com.example.ui.screens.DeveloperProgramIntroScreen
import com.example.ui.screens.DigitalIDCardScreen
import com.example.ui.screens.GuideAIScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.LessonOverviewScreen
import com.example.ui.screens.LoginOptionsScreen
import com.example.ui.screens.MessagesScreen
import com.example.ui.screens.NotificationsScreen
import com.example.ui.screens.OlympiadSeriesScreen
import com.example.ui.screens.OlympiadSuccessScreen
import com.example.ui.screens.OnboardingScreen
import com.example.ui.screens.ParentProfileScreen
import com.example.ui.screens.PinConfirmationScreen
import com.example.ui.screens.ProfileHubScreen
import com.example.ui.screens.RecommendationDetailScreen
import com.example.ui.screens.ScholarshipStep10SubmittedScreen
import com.example.ui.screens.ScholarshipStep1Screen
import com.example.ui.screens.ScholarshipStep2UidaiScreen
import com.example.ui.screens.ScholarshipStep3VideoKycScreen
import com.example.ui.screens.ScholarshipStep4SuccessScreen
import com.example.ui.screens.ScholarshipStep5FormScreen
import com.example.ui.screens.ScholarshipStep6ConsentScreen
import com.example.ui.screens.ScholarshipStep7FeeScreen
import com.example.ui.screens.ScholarshipStep8DraftScreen
import com.example.ui.screens.ScholarshipStep9PaymentSuccessScreen
import com.example.ui.screens.SchoolDetailScreen
import com.example.ui.screens.SearchDirectoryScreen
import com.example.ui.screens.SocialFeedScreen
import com.example.ui.screens.SplashScreen
import com.example.ui.screens.TeacherProfileScreen
import com.example.ui.screens.VideoPlayerScreen
import com.example.ui.theme.CanvasCream
import com.example.ui.viewmodel.EduLockerViewModel

@Composable
fun EduLockerApp(
    viewModel: EduLockerViewModel = viewModel()
) {
    val currentPage by viewModel.currentPage.collectAsState()
    val currentTab by viewModel.currentTab.collectAsState()
    val isAiChatOpen by viewModel.isAiChatOpen.collectAsState()
    val isStoryOpen by viewModel.isStoryOpen.collectAsState()

    // Show Bottom Navigation Dock ONLY on primary top-level tabs (Home, Search, Guide, Social, Profile)
    // and ONLY when no full-screen AI chat or Story viewer is active
    val showBottomNav = !isAiChatOpen && !isStoryOpen && currentPage in listOf(11, 24, 27, 34, 35, 36)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(CanvasCream)
    ) {
        // Main Screen Viewport
        AnimatedContent(
            targetState = currentPage,
            transitionSpec = { fadeIn() togetherWith fadeOut() },
            label = "screenTransition",
            modifier = Modifier.fillMaxSize()
        ) { page ->
            when (page) {
                // Splash (Screen 1)
                1 -> SplashScreen(onTimeout = { viewModel.navigateToPage(2) })

                // Onboarding Sequence (Screens 2 to 7)
                in 2..7 -> OnboardingScreen(
                    pageIndex = page,
                    onNext = {
                        if (page < 7) {
                            viewModel.navigateToPage(page + 1)
                        } else {
                            viewModel.navigateToPage(8) // Proceed to Login Options Screen
                        }
                    }
                )

                // Auth Screens (8 to 10)
                8 -> LoginOptionsScreen(
                    onLoginWithAadhaar = { viewModel.navigateToPage(9) },
                    onLoginWithPhone = { viewModel.navigateToPage(9) },
                    onLoginWithEduLockerId = { viewModel.navigateToPage(10) },
                    onDontHaveAccount = { viewModel.navigateToPage(9) }
                )
                9 -> AadhaarVerificationScreen(
                    viewModel = viewModel,
                    onContinue = { viewModel.navigateToPage(10) }
                )
                10 -> PinConfirmationScreen(
                    viewModel = viewModel,
                    onLoginSuccess = { viewModel.navigateToPage(11) }
                )

                // Home Hub Screens (11 & 24)
                11 -> HomeScreen(
                    viewModel = viewModel,
                    isOlympiadVariant = false,
                    onNavigateToScholarship = { viewModel.navigateToPage(14) },
                    onNavigateToOlympiad = { viewModel.navigateToPage(25) },
                    onNavigateToNotifications = { viewModel.navigateToPage(12) },
                    onNavigateToMessages = { viewModel.navigateToPage(13) },
                    onNavigateToProfile = { viewModel.navigateToPage(36) }
                )
                24 -> HomeScreen(
                    viewModel = viewModel,
                    isOlympiadVariant = true,
                    onNavigateToScholarship = { viewModel.navigateToPage(14) },
                    onNavigateToOlympiad = { viewModel.navigateToPage(25) },
                    onNavigateToNotifications = { viewModel.navigateToPage(12) },
                    onNavigateToMessages = { viewModel.navigateToPage(13) },
                    onNavigateToProfile = { viewModel.navigateToPage(36) }
                )

                // Notifications (12) - Full immersive screen without bottom nav
                12 -> NotificationsScreen(
                    viewModel = viewModel,
                    onBackClick = { viewModel.navigateToPage(11) },
                    onNotificationClick = { cat ->
                        when (cat) {
                            "scholarship" -> viewModel.navigateToPage(14)
                            "olympiad" -> viewModel.navigateToPage(25)
                            "course" -> viewModel.navigateToPage(28)
                            "achievement" -> viewModel.navigateToPage(37)
                            else -> viewModel.navigateToPage(42)
                        }
                    }
                )

                // Direct Messages / Chat Hub (13) - Full immersive screen without bottom nav
                13 -> MessagesScreen(
                    viewModel = viewModel,
                    onBackClick = { viewModel.navigateToPage(11) }
                )

                // Scholarship Flow (Screens 14 to 23)
                14 -> ScholarshipStep1Screen(
                    viewModel = viewModel,
                    onBack = { viewModel.navigateToPage(11) },
                    onContinue = { viewModel.navigateToPage(15) }
                )
                15 -> ScholarshipStep2UidaiScreen(
                    viewModel = viewModel,
                    onContinue = {
                        viewModel.syncAadhaarSuccess()
                        viewModel.navigateToPage(16)
                    }
                )
                16 -> ScholarshipStep3VideoKycScreen(
                    onKycVerified = {
                        viewModel.verifyKycSuccess()
                        viewModel.navigateToPage(17)
                    }
                )
                17 -> ScholarshipStep4SuccessScreen(
                    onContinue = { viewModel.navigateToPage(18) }
                )
                18 -> ScholarshipStep5FormScreen(
                    viewModel = viewModel,
                    onConfirm = { viewModel.navigateToPage(19) }
                )
                19 -> ScholarshipStep6ConsentScreen(
                    viewModel = viewModel,
                    onContinue = { viewModel.navigateToPage(20) }
                )
                20 -> ScholarshipStep7FeeScreen(
                    onDraftAndExit = {
                        viewModel.saveScholarshipDraft()
                        viewModel.navigateToPage(21)
                    },
                    onPayAndSubmit = {
                        viewModel.recordFeePayment(9)
                        viewModel.navigateToPage(22)
                    }
                )
                21 -> ScholarshipStep8DraftScreen(
                    onHome = { viewModel.navigateToPage(11) }
                )
                22 -> ScholarshipStep9PaymentSuccessScreen(
                    onContinue = { viewModel.navigateToPage(23) }
                )
                23 -> ScholarshipStep10SubmittedScreen(
                    viewModel = viewModel,
                    onDone = { viewModel.navigateToPage(11) }
                )

                // Olympiad Series (25 & 26)
                25 -> OlympiadSeriesScreen(
                    viewModel = viewModel,
                    onContinue = { viewModel.navigateToPage(26) }
                )
                26 -> OlympiadSuccessScreen(
                    viewModel = viewModel,
                    onDone = { viewModel.navigateToPage(24) }
                )

                // Search Directory (27)
                27 -> SearchDirectoryScreen(
                    viewModel = viewModel,
                    onNavigateToSchoolProfile = { viewModel.navigateToPage(42) },
                    onNavigateToDeveloperProgram = { viewModel.navigateToPage(28) },
                    onNavigateToAiGuide = { viewModel.navigateToPage(34) }
                )

                // HP LIFE Developer Program (28 to 33)
                28 -> DeveloperProgramIntroScreen(
                    onNext = { viewModel.navigateToPage(29) }
                )
                29 -> DeveloperProgramAboutScreen(
                    onContinue = { viewModel.navigateToPage(30) }
                )
                30 -> CourseSelectorScreen(
                    viewModel = viewModel,
                    onContinue = { viewModel.navigateToPage(31) }
                )
                31 -> CourseLevelSelectorScreen(
                    viewModel = viewModel,
                    onContinue = { viewModel.navigateToPage(32) }
                )
                32 -> LessonOverviewScreen(
                    onWatch = { viewModel.navigateToPage(33) }
                )
                33 -> VideoPlayerScreen(
                    viewModel = viewModel,
                    onSaveLater = { viewModel.navigateToPage(28) }
                )

                // Runway AI Guide (34)
                34 -> GuideAIScreen(viewModel = viewModel)

                // Social Feed (35)
                35 -> SocialFeedScreen(viewModel = viewModel)

                // Profile Hub & Achievements (36 & 37)
                36 -> ProfileHubScreen(
                    viewModel = viewModel,
                    onNavigateToIdCard = { viewModel.navigateToPage(38) },
                    onNavigateToAchievements = { viewModel.navigateToPage(37) }
                )
                37 -> AchievementsScreen(
                    onBack = { viewModel.navigateToPage(36) }
                )

                // Digital Student ID Card (38 & 39)
                38, 39 -> DigitalIDCardScreen(viewModel = viewModel)

                // Teacher Faculty Hub (40)
                40 -> TeacherProfileScreen(
                    viewModel = viewModel,
                    onBack = { viewModel.navigateToPage(27) }
                )

                // Parent Link Hub (41)
                41 -> ParentProfileScreen(
                    viewModel = viewModel,
                    onBack = { viewModel.navigateToPage(36) }
                )

                // School Profile (42)
                42 -> SchoolDetailScreen(
                    viewModel = viewModel,
                    onBack = { viewModel.navigateToPage(27) }
                )

                // Dedicated Recommendation Detail Hub (43)
                43 -> RecommendationDetailScreen(
                    viewModel = viewModel,
                    onBack = { viewModel.navigateToPage(34) }
                )

                else -> HomeScreen(
                    viewModel = viewModel,
                    onNavigateToScholarship = { viewModel.navigateToPage(14) },
                    onNavigateToOlympiad = { viewModel.navigateToPage(25) },
                    onNavigateToNotifications = { viewModel.navigateToPage(12) },
                    onNavigateToMessages = { viewModel.navigateToPage(13) },
                    onNavigateToProfile = { viewModel.navigateToPage(36) }
                )
            }
        }

        // Floating Bottom Navigation Dock (shown ONLY on main root tabs: Home, Search, Guide, Social, Profile)
        if (showBottomNav) {
            EduLockerFloatingBottomBar(
                currentTab = currentTab,
                onTabSelected = { tab: NavTab -> viewModel.selectTab(tab) },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 12.dp)
            )
        }

        // Discreet Floating Screen Jumper in bottom-end corner for quick navigation
        EvaluatorScreenJumper(
            currentPage = currentPage,
            onPageSelected = { targetPage: Int -> viewModel.navigateToPage(targetPage) },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = if (showBottomNav) 84.dp else 24.dp, end = 14.dp)
        )

        // Runtime Permissions Onboarding Modal (Microphone, Camera, Contacts, Notifications)
        AppPermissionsDialog()
    }
}
