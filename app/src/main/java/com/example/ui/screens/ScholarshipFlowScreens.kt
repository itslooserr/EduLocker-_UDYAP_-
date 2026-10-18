package com.example.ui.screens

import android.widget.Toast
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.InsertDriveFile
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.EduLockerDualCircleLogo
import com.example.ui.components.RealtimeLiveCameraKYCView
import com.example.ui.theme.CanvasCream
import com.example.ui.theme.DarkBorder
import com.example.ui.theme.OdishaMagenta
import com.example.ui.theme.OdishaMagentaDark
import com.example.ui.theme.PureWhite
import com.example.ui.theme.SoftBlushPink
import com.example.ui.theme.SoftCyan
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.TextDark
import com.example.ui.theme.TextMuted
import com.example.ui.viewmodel.EduLockerViewModel

// Screen 14: Scholarship Step 1
@Composable
fun ScholarshipStep1Screen(
    viewModel: EduLockerViewModel,
    onBack: () -> Unit,
    onContinue: () -> Unit,
    modifier: Modifier = Modifier
) {
    val mobile by viewModel.scholarshipMobile.collectAsState()
    val eduId by viewModel.scholarshipEduLockerId.collectAsState()
    val otp by viewModel.scholarshipOtp.collectAsState()
    val context = LocalContext.current

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(CanvasCream)
            .padding(horizontal = 24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Back button
            IconButton(
                onClick = onBack,
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(PureWhite)
                    .border(1.5.dp, DarkBorder, CircleShape)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = TextDark
                )
            }

            Spacer(modifier = Modifier.height(120.dp))

            EduLockerDualCircleLogo(size = 46.dp)
            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "Post Matric Scholarship",
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.ExtraBold,
                color = TextDark,
                fontSize = 22.sp
            )
            Text(
                text = "Fill in the required details carefully",
                style = MaterialTheme.typography.bodyLarge,
                color = TextDark.copy(alpha = 0.8f),
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(28.dp))

            // Input: Mobile Number
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .border(1.5.dp, DarkBorder, RoundedCornerShape(30.dp))
                    .clip(RoundedCornerShape(30.dp))
                    .background(PureWhite)
                    .padding(horizontal = 20.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                OutlinedTextField(
                    value = mobile,
                    onValueChange = { viewModel.scholarshipMobile.value = it },
                    placeholder = { Text("Mobile Number", color = TextMuted) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    singleLine = true
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Pre-filled EduLocker ID
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .border(1.5.dp, DarkBorder, RoundedCornerShape(30.dp))
                    .clip(RoundedCornerShape(30.dp))
                    .background(SoftCyan.copy(alpha = 0.5f))
                    .padding(horizontal = 20.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = eduId,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold,
                        color = TextDark
                    )

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(PureWhite)
                            .border(1.dp, DarkBorder, RoundedCornerShape(12.dp))
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "Pre-filled",
                            color = TextDark,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Input: OTP with Resend
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .border(1.5.dp, DarkBorder, RoundedCornerShape(30.dp))
                    .clip(RoundedCornerShape(30.dp))
                    .background(PureWhite)
                    .padding(horizontal = 20.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(modifier = Modifier.weight(1f)) {
                        OutlinedTextField(
                            value = otp,
                            onValueChange = { viewModel.scholarshipOtp.value = it },
                            placeholder = { Text("OTP", color = TextMuted) },
                            modifier = Modifier.fillMaxWidth(),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            singleLine = true
                        )
                    }

                    Text(
                        text = "Resend",
                        color = OdishaMagenta,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .clickable {
                                Toast.makeText(context, "OTP resent", Toast.LENGTH_SHORT).show()
                            }
                            .padding(end = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(60.dp))

            // Bottom Continue
            Button(
                onClick = onContinue,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth(0.65f)
                    .height(52.dp)
                    .border(2.dp, DarkBorder, RoundedCornerShape(30.dp)),
                shape = RoundedCornerShape(30.dp),
                colors = ButtonDefaults.buttonColors(containerColor = OdishaMagenta)
            ) {
                Text(
                    text = "Continue",
                    color = PureWhite,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

// Screen 15: UIDAI Aadhaar Verification Step 2
@Composable
fun ScholarshipStep2UidaiScreen(
    viewModel: EduLockerViewModel,
    onContinue: () -> Unit,
    modifier: Modifier = Modifier
) {
    val aadhar by viewModel.scholarshipAadhaarNo.collectAsState()
    val otp by viewModel.scholarshipOtp.collectAsState()
    val context = LocalContext.current

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(CanvasCream)
            .padding(horizontal = 24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 180.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Fingerprint,
                contentDescription = "Aadhaar",
                tint = OdishaMagenta,
                modifier = Modifier.size(54.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "UIDAI Verification",
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.ExtraBold,
                color = TextDark,
                fontSize = 22.sp
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Verify your identity using your Aadhaar number to continue securely.",
                style = MaterialTheme.typography.bodyLarge,
                color = TextDark.copy(alpha = 0.8f),
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(28.dp))

            // Input: Aadhar No
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .border(1.5.dp, DarkBorder, RoundedCornerShape(30.dp))
                    .clip(RoundedCornerShape(30.dp))
                    .background(PureWhite)
                    .padding(horizontal = 20.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                OutlinedTextField(
                    value = aadhar,
                    onValueChange = { viewModel.scholarshipAadhaarNo.value = it },
                    placeholder = { Text("Aadhar No", color = TextMuted) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    singleLine = true
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Input: OTP
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .border(1.5.dp, DarkBorder, RoundedCornerShape(30.dp))
                    .clip(RoundedCornerShape(30.dp))
                    .background(PureWhite)
                    .padding(horizontal = 20.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(modifier = Modifier.weight(1f)) {
                        OutlinedTextField(
                            value = otp,
                            onValueChange = { viewModel.scholarshipOtp.value = it },
                            placeholder = { Text("OTP", color = TextMuted) },
                            modifier = Modifier.fillMaxWidth(),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            singleLine = true
                        )
                    }

                    Text(
                        text = "Resend",
                        color = OdishaMagenta,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .clickable {
                                Toast.makeText(context, "UIDAI OTP sent", Toast.LENGTH_SHORT).show()
                            }
                            .padding(end = 4.dp)
                    )
                }
            }
        }

        // Bottom Verify Button
        Button(
            onClick = onContinue,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 36.dp)
                .fillMaxWidth(0.55f)
                .height(52.dp)
                .border(2.dp, DarkBorder, RoundedCornerShape(30.dp)),
            shape = RoundedCornerShape(30.dp),
            colors = ButtonDefaults.buttonColors(containerColor = OdishaMagenta)
        ) {
            Text(
                text = "Verify",
                color = PureWhite,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

// Screen 16: Complete Real Camera Video KYC Step 3
@Composable
fun ScholarshipStep3VideoKycScreen(
    onKycVerified: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(CanvasCream)
            .padding(horizontal = 20.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp)
        ) {
            EduLockerDualCircleLogo(size = 46.dp)
            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Complete Video KYC",
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.ExtraBold,
                color = TextDark,
                fontSize = 22.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "To confirm this submission, please complete Video KYC so we can confirm it's really you.",
                style = MaterialTheme.typography.bodyLarge,
                color = TextDark.copy(alpha = 0.8f),
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Live Camera View with Oval Reticle, 7s Countdown, and Verification
            RealtimeLiveCameraKYCView(
                onVerificationSuccess = onKycVerified
            )
        }
    }
}

// Screen 17: Video KYC Successful Step 4
@Composable
fun ScholarshipStep4SuccessScreen(
    onContinue: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(CanvasCream)
            .padding(horizontal = 24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(bottom = 80.dp)
        ) {
            // Pink circle with checkmark
            Box(
                modifier = Modifier
                    .size(110.dp)
                    .clip(CircleShape)
                    .background(SoftBlushPink)
                    .border(2.dp, DarkBorder, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = "Success",
                    tint = OdishaMagenta,
                    modifier = Modifier.size(54.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Video KYC Successful",
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.ExtraBold,
                color = TextDark,
                fontSize = 22.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Your verification is completed, you can\nnow securely continue with your\napplication.",
                style = MaterialTheme.typography.bodyLarge,
                color = TextDark.copy(alpha = 0.8f),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                fontSize = 15.sp,
                lineHeight = 22.sp
            )
        }

        // Bottom Continue
        Button(
            onClick = onContinue,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 36.dp)
                .fillMaxWidth(0.55f)
                .height(52.dp)
                .border(2.dp, DarkBorder, RoundedCornerShape(30.dp)),
            shape = RoundedCornerShape(30.dp),
            colors = ButtonDefaults.buttonColors(containerColor = OdishaMagenta)
        ) {
            Text(
                text = "Continue",
                color = PureWhite,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

// Screen 18: Fill the Form Step 5
@Composable
fun ScholarshipStep5FormScreen(
    viewModel: EduLockerViewModel,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier
) {
    val parentLinked by viewModel.scholarshipParentLinked.collectAsState()
    val incomeConsent by viewModel.scholarshipIncomeConsent.collectAsState()
    val attendanceConsent by viewModel.scholarshipAttendanceConsent.collectAsState()
    val academicConsent by viewModel.scholarshipAcademicConsent.collectAsState()
    val casteCategory by viewModel.scholarshipCasteCategory.collectAsState()
    val bankLinked by viewModel.scholarshipBankLinked.collectAsState()

    var dropdownExpanded by remember { mutableStateOf(false) }
    val categories = listOf("General / SEBC", "OBC", "SC", "ST", "Minority")
    val context = LocalContext.current

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(CanvasCream)
            .padding(horizontal = 20.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                EduLockerDualCircleLogo(size = 46.dp)

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.clickable {
                        Toast.makeText(context, "Opening application preview...", Toast.LENGTH_SHORT).show()
                    }
                ) {
                    Text(
                        text = "Preview",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = TextDark
                    )
                    Icon(
                        imageVector = Icons.Filled.Visibility,
                        contentDescription = "Preview",
                        tint = TextDark,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "Fill the form",
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.ExtraBold,
                color = TextDark,
                fontSize = 22.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Provide the required information to complete your scholarship application.\n\nMake sure all details are accurate and match your official documents",
                style = MaterialTheme.typography.bodyMedium,
                color = TextDark.copy(alpha = 0.8f),
                fontSize = 13.sp,
                lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Connect Your PARENT ID
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .border(1.5.dp, DarkBorder, RoundedCornerShape(30.dp)),
                shape = RoundedCornerShape(30.dp),
                color = PureWhite
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 18.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Connect Your PARENT ID",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold,
                        color = TextDark
                    )
                    Text(
                        text = if (parentLinked) "Linked ✓" else "Connect",
                        color = if (parentLinked) SuccessGreen else OdishaMagenta,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable {
                            viewModel.scholarshipParentLinked.value = !parentLinked
                            Toast.makeText(context, "Parent ID PARENT-ACLN-SHNO24 toggled", Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Make sure your parent's ID is linked to your account",
                style = MaterialTheme.typography.labelSmall,
                color = TextMuted
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Checkbox 1: Family Income
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { viewModel.scholarshipIncomeConsent.value = !incomeConsent },
                verticalAlignment = Alignment.Top
            ) {
                RadioButton(
                    selected = incomeConsent,
                    onClick = { viewModel.scholarshipIncomeConsent.value = !incomeConsent },
                    colors = RadioButtonDefaults.colors(selectedColor = OdishaMagenta)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Allow us to access your family income information to verify your scholarship & complete the application.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextDark,
                    fontSize = 13.sp,
                    lineHeight = 18.sp
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Checkbox 2: Verify School & Attendance
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { viewModel.scholarshipAttendanceConsent.value = !attendanceConsent },
                verticalAlignment = Alignment.Top
            ) {
                RadioButton(
                    selected = attendanceConsent,
                    onClick = { viewModel.scholarshipAttendanceConsent.value = !attendanceConsent },
                    colors = RadioButtonDefaults.colors(selectedColor = OdishaMagenta)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Give Permission to Verify School & Attendance.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextDark,
                    fontSize = 13.sp,
                    lineHeight = 18.sp
                )
            }

            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Choose your caste category from the list.",
                style = MaterialTheme.typography.labelMedium,
                color = TextMuted
            )
            Spacer(modifier = Modifier.height(6.dp))

            // Dropdown: Select Category
            Box {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .border(1.5.dp, DarkBorder, RoundedCornerShape(30.dp))
                        .clip(RoundedCornerShape(30.dp))
                        .clickable { dropdownExpanded = true },
                    color = PureWhite
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 18.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = casteCategory,
                            style = MaterialTheme.typography.bodyLarge,
                            color = TextDark
                        )
                        Icon(
                            imageVector = Icons.Filled.KeyboardArrowDown,
                            contentDescription = "Dropdown",
                            tint = TextDark
                        )
                    }
                }

                DropdownMenu(
                    expanded = dropdownExpanded,
                    onDismissRequest = { dropdownExpanded = false }
                ) {
                    categories.forEach { cat ->
                        DropdownMenuItem(
                            text = { Text(cat) },
                            onClick = {
                                viewModel.scholarshipCasteCategory.value = cat
                                dropdownExpanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Checkbox 3: Academic records
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { viewModel.scholarshipAcademicConsent.value = !academicConsent },
                verticalAlignment = Alignment.Top
            ) {
                RadioButton(
                    selected = academicConsent,
                    onClick = { viewModel.scholarshipAcademicConsent.value = !academicConsent },
                    colors = RadioButtonDefaults.colors(selectedColor = OdishaMagenta)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Allow EduLocker to securely access your previous academic records to verify your educational details.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextDark,
                    fontSize = 13.sp,
                    lineHeight = 18.sp
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Link Bank Account
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "LINK your bank account",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = TextDark
                )
                Text(
                    text = if (bankLinked) "LINKED (SBI ****1418) ✓" else "LINK",
                    color = if (bankLinked) SuccessGreen else OdishaMagenta,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable {
                        viewModel.scholarshipBankLinked.value = !bankLinked
                        Toast.makeText(context, "State Bank of India account linked", Toast.LENGTH_SHORT).show()
                    }
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            // Bottom Confirm Button
            Button(
                onClick = onConfirm,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth(0.55f)
                    .height(52.dp)
                    .border(2.dp, DarkBorder, RoundedCornerShape(30.dp)),
                shape = RoundedCornerShape(30.dp),
                colors = ButtonDefaults.buttonColors(containerColor = OdishaMagenta)
            ) {
                Text(
                    text = "Confirm",
                    color = PureWhite,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

// Screen 19: Consent & Confirmation Step 6
@Composable
fun ScholarshipStep6ConsentScreen(
    viewModel: EduLockerViewModel,
    onContinue: () -> Unit,
    modifier: Modifier = Modifier
) {
    val consent1 by viewModel.scholarshipReviewConsent1.collectAsState()
    val consent2 by viewModel.scholarshipReviewConsent2.collectAsState()
    val context = LocalContext.current

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(CanvasCream)
            .padding(horizontal = 22.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            EduLockerDualCircleLogo(size = 46.dp)
            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "Consent & Confirmation",
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.ExtraBold,
                color = TextDark,
                fontSize = 22.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Please select the checkbox below to confirm that you give your consent and wish to proceed.",
                style = MaterialTheme.typography.bodyMedium,
                color = TextDark.copy(alpha = 0.8f),
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Checkbox 1
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { viewModel.scholarshipReviewConsent1.value = !consent1 },
                verticalAlignment = Alignment.Top
            ) {
                RadioButton(
                    selected = consent1,
                    onClick = { viewModel.scholarshipReviewConsent1.value = !consent1 },
                    colors = RadioButtonDefaults.colors(selectedColor = OdishaMagenta)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Please confirm that you have reviewed your scholarship form again, verified all the details, and wish to proceed with the submission.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextDark,
                    fontSize = 13.sp,
                    lineHeight = 18.sp
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Preview link
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier
                    .padding(start = 36.dp)
                    .clickable {
                        Toast.makeText(context, "Full verified form preview loaded", Toast.LENGTH_SHORT).show()
                    }
            ) {
                Icon(
                    imageVector = Icons.Filled.Visibility,
                    contentDescription = null,
                    tint = TextDark,
                    modifier = Modifier.size(18.dp)
                )
                Text(
                    text = "Preview",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = TextDark
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Checkbox 2
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { viewModel.scholarshipReviewConsent2.value = !consent2 },
                verticalAlignment = Alignment.Top
            ) {
                RadioButton(
                    selected = consent2,
                    onClick = { viewModel.scholarshipReviewConsent2.value = !consent2 },
                    colors = RadioButtonDefaults.colors(selectedColor = OdishaMagenta)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = "I confirm that the information and documents provided by me are true, accurate, and complete to the best of my knowledge.\n\nI give consent to EduLocker and the concerned school/authorities to verify my identity, academic records, attendance, family income, caste certificate, and other information required for processing my scholarship application.\n\nI understand that providing false or misleading information may affect my eligibility and may result in rejection or revert of my application.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextDark.copy(alpha = 0.85f),
                        fontSize = 13.sp,
                        lineHeight = 18.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Bottom Continue Button
            Button(
                onClick = onContinue,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth(0.55f)
                    .height(52.dp)
                    .border(2.dp, DarkBorder, RoundedCornerShape(30.dp)),
                shape = RoundedCornerShape(30.dp),
                colors = ButtonDefaults.buttonColors(containerColor = OdishaMagenta)
            ) {
                Text(
                    text = "Continue",
                    color = PureWhite,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

// Screen 20: Submit Your Application ₹9 Fee Step 7
@Composable
fun ScholarshipStep7FeeScreen(
    onDraftAndExit: () -> Unit,
    onPayAndSubmit: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(CanvasCream)
            .padding(horizontal = 24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(bottom = 120.dp)
        ) {
            Text(
                text = "₹9",
                style = MaterialTheme.typography.displayLarge,
                fontWeight = FontWeight.ExtraBold,
                color = OdishaMagenta,
                fontSize = 68.sp
            )

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "Submit Your Application",
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.Bold,
                color = TextDark,
                fontSize = 22.sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Once submitted, your application will be\nprocessed for further verification.",
                style = MaterialTheme.typography.bodyLarge,
                color = TextDark.copy(alpha = 0.8f),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                fontSize = 14.sp
            )
        }

        // Bottom Actions (Screens 20)
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 36.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth(0.55f)
                    .height(50.dp)
                    .border(2.dp, DarkBorder, RoundedCornerShape(30.dp))
                    .clip(RoundedCornerShape(30.dp))
                    .clickable { onDraftAndExit() },
                color = Color(0xFF1E293B)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = "Draft and exit",
                        color = PureWhite,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Button(
                onClick = onPayAndSubmit,
                modifier = Modifier
                    .fillMaxWidth(0.55f)
                    .height(50.dp)
                    .border(2.dp, DarkBorder, RoundedCornerShape(30.dp)),
                shape = RoundedCornerShape(30.dp),
                colors = ButtonDefaults.buttonColors(containerColor = OdishaMagenta)
            ) {
                Text(
                    text = "Pay & Submit",
                    color = PureWhite,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

// Screen 21: Form Saved as Draft Step 8
@Composable
fun ScholarshipStep8DraftScreen(
    onHome: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(CanvasCream)
            .clickable { onHome() }
            .padding(horizontal = 24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(bottom = 60.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.InsertDriveFile,
                contentDescription = "Draft",
                tint = Color(0xFF334155),
                modifier = Modifier.size(100.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Form Saved as Draft",
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.ExtraBold,
                color = TextDark,
                fontSize = 22.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Your form has been saved as a draft. You\ncan continue from where you left off\nanytime.",
                style = MaterialTheme.typography.bodyLarge,
                color = TextDark.copy(alpha = 0.8f),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                fontSize = 14.sp
            )
        }
    }
}

// Screen 22: Payment Successful Step 9
@Composable
fun ScholarshipStep9PaymentSuccessScreen(
    onContinue: () -> Unit,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(2000)
        onContinue()
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(CanvasCream)
            .clickable { onContinue() }
            .padding(horizontal = 24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(bottom = 60.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Security,
                contentDescription = "Success",
                tint = SuccessGreen,
                modifier = Modifier.size(90.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Payment Successful",
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.ExtraBold,
                color = TextDark,
                fontSize = 22.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Your payment of ₹9 has been successfully\ncompleted.",
                style = MaterialTheme.typography.bodyLarge,
                color = TextDark.copy(alpha = 0.8f),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                fontSize = 14.sp
            )
        }
    }
}

// Screen 23: Application Submitted Successfully Step 10
@Composable
fun ScholarshipStep10SubmittedScreen(
    viewModel: EduLockerViewModel,
    onDone: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.submitScholarshipApplication()
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(CanvasCream)
            .padding(horizontal = 24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(bottom = 80.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(90.dp)
                    .clip(CircleShape)
                    .background(OdishaMagenta),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = "Success",
                    tint = PureWhite,
                    modifier = Modifier.size(48.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Application Submitted\nSuccessfully",
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.ExtraBold,
                color = TextDark,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                fontSize = 22.sp,
                lineHeight = 28.sp
            )

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "Your Post Matric Scholarship application\nhas been successfully submitted\n\n\nYou can now download your application\nform for your records.",
                style = MaterialTheme.typography.bodyLarge,
                color = TextDark.copy(alpha = 0.8f),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Download 11.78 MB action
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .clickable {
                        Toast.makeText(context, "Downloading verified receipt: OD-PMS-2026-98124.pdf (11.78 MB)", Toast.LENGTH_LONG).show()
                    }
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Download,
                    contentDescription = "Download",
                    tint = TextDark,
                    modifier = Modifier.size(32.dp)
                )
                Column {
                    Text(
                        text = "Download",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = TextDark
                    )
                    Text(
                        text = "11.78 MB",
                        style = MaterialTheme.typography.labelSmall,
                        color = TextMuted
                    )
                }
            }
        }

        // Bottom Done Button
        Button(
            onClick = onDone,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 36.dp)
                .fillMaxWidth(0.55f)
                .height(52.dp)
                .border(2.dp, DarkBorder, RoundedCornerShape(30.dp)),
            shape = RoundedCornerShape(30.dp),
            colors = ButtonDefaults.buttonColors(containerColor = OdishaMagenta)
        ) {
            Text(
                text = "Done",
                color = PureWhite,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
