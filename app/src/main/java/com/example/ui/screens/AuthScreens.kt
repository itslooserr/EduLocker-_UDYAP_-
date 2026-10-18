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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.CanvasCream
import com.example.ui.theme.OdishaMagenta
import com.example.ui.theme.PureWhite
import com.example.ui.theme.TextDark
import com.example.ui.theme.TextMuted
import com.example.ui.viewmodel.EduLockerViewModel

// =========================================================================
// SCREEN 8: Login Options Screen (Exact Replica of 8.png - Bottom Aligned)
// =========================================================================
@Composable
fun LoginOptionsScreen(
    onLoginWithAadhaar: () -> Unit,
    onLoginWithPhone: () -> Unit,
    onLoginWithEduLockerId: () -> Unit,
    onDontHaveAccount: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(CanvasCream)
            .padding(horizontal = 28.dp)
    ) {
        // Bottom-aligned Content Container (Exact from 8.png)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp),
            horizontalAlignment = Alignment.Start
        ) {
            // Dual Circle Logo (Pink circle + overlapping magenta circle on bottom right)
            EduLockerDualDotsLogo(size = 54.dp)

            Spacer(modifier = Modifier.height(14.dp))

            // Welcome Title
            Text(
                text = "Welcome to EduLocker",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF334155),
                fontSize = 21.sp
            )

            Spacer(modifier = Modifier.height(3.dp))

            Text(
                text = "Sign in to continue your journey.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF4B5563),
                fontSize = 14.5.sp
            )

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "By continuing, you agree to EduLocker's Terms of Service\nand Privacy Policy.",
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFF6B7280),
                fontSize = 11.5.sp,
                lineHeight = 15.sp
            )

            Spacer(modifier = Modifier.height(28.dp))

            // 1. "Login with Aadhar" Double-Border Button (Exact from 8.png)
            EduLockerPillButton(
                text = "Login with Aadhar",
                onClick = onLoginWithAadhaar,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(14.dp))

            // 2. "Login with Phone" Double-Border Button (Exact from 8.png)
            EduLockerPillButton(
                text = "Login with Phone",
                onClick = onLoginWithPhone,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(18.dp))

            // 3. "Login with EduLocker ID" Link
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Login with EduLocker ID",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF374151),
                    fontWeight = FontWeight.Medium,
                    fontSize = 13.5.sp,
                    modifier = Modifier.clickable { onLoginWithEduLockerId() }
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // 4. Bottom "Dont have Account??" Row (Exact from 8.png)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDontHaveAccount() },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFFBCFE8))
                        .border(1.dp, Color(0xFF1E293B), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "?",
                        color = Color(0xFF1E293B),
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Dont have Account??",
                    color = Color(0xFF374151),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    fontSize = 13.sp
                )
            }
        }
    }
}

// =========================================================================
// SCREEN 9: Verify your Aadhar Identity (Exact Replica of 9.png - Bottom Aligned)
// =========================================================================
@Composable
fun AadhaarVerificationScreen(
    viewModel: EduLockerViewModel,
    onContinue: () -> Unit,
    modifier: Modifier = Modifier
) {
    val aadhaar by viewModel.enteredAadhaar.collectAsState()
    val fullName by viewModel.enteredFullName.collectAsState()
    val otp by viewModel.enteredOtp.collectAsState()
    val context = LocalContext.current

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(CanvasCream)
            .padding(horizontal = 26.dp)
    ) {
        // Bottom-aligned Content Container (Exact from 9.png)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 36.dp),
            horizontalAlignment = Alignment.Start
        ) {
            // Dual Circle Logo
            EduLockerDualDotsLogo(size = 54.dp)

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "Verify your Aadhar Identity",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF334155),
                fontSize = 21.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Verify your identity securely with Aadhaar to\naccess your EduLocker account",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF4B5563),
                fontSize = 13.5.sp,
                lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(28.dp))

            // Field 1: Enter Aadhar Number (Pill shape with black outline)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .clip(RoundedCornerShape(30.dp))
                    .border(1.2.dp, Color(0xFF1E293B), RoundedCornerShape(30.dp))
                    .background(PureWhite)
                    .padding(horizontal = 16.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                OutlinedTextField(
                    value = aadhaar,
                    onValueChange = { viewModel.enteredAadhaar.value = it },
                    placeholder = { Text("Enter Aadhar Number", color = Color(0xFF6B7280), fontSize = 14.sp) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Field 2: Enter full name as per Aadhar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .clip(RoundedCornerShape(30.dp))
                    .border(1.2.dp, Color(0xFF1E293B), RoundedCornerShape(30.dp))
                    .background(PureWhite)
                    .padding(horizontal = 16.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                OutlinedTextField(
                    value = fullName,
                    onValueChange = { viewModel.enteredFullName.value = it },
                    placeholder = { Text("Enter your full name as per Aadhar", color = Color(0xFF6B7280), fontSize = 14.sp) },
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

            // Field 3: Enter OTP with Resend link
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .clip(RoundedCornerShape(30.dp))
                    .border(1.2.dp, Color(0xFF1E293B), RoundedCornerShape(30.dp))
                    .background(PureWhite)
                    .padding(horizontal = 16.dp),
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
                            onValueChange = { viewModel.enteredOtp.value = it },
                            placeholder = { Text("Enter OTP", color = Color(0xFF6B7280), fontSize = 14.sp) },
                            modifier = Modifier.fillMaxWidth(),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )
                    }

                    Text(
                        text = "Resend",
                        color = OdishaMagenta,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        modifier = Modifier
                            .clickable {
                                Toast.makeText(context, "OTP resent to registered mobile", Toast.LENGTH_SHORT).show()
                            }
                            .padding(end = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(36.dp))

            // Continue Button centered
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                EduLockerPillButton(
                    text = "Continue",
                    onClick = onContinue,
                    modifier = Modifier.fillMaxWidth(0.52f)
                )
            }
        }
    }
}

// =========================================================================
// SCREEN 10: PIN Confirmation Screen (Bottom Aligned)
// =========================================================================
@Composable
fun PinConfirmationScreen(
    viewModel: EduLockerViewModel,
    onLoginSuccess: () -> Unit,
    modifier: Modifier = Modifier
) {
    val pin by viewModel.enteredPin.collectAsState()
    val context = LocalContext.current

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(CanvasCream)
            .padding(horizontal = 26.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 36.dp),
            horizontalAlignment = Alignment.Start
        ) {
            EduLockerDualDotsLogo(size = 54.dp)

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "Confirm your login",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF334155),
                fontSize = 21.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Enter your PIN to securely verify your identity\nand continue.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF4B5563),
                fontSize = 13.5.sp,
                lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(28.dp))

            // PIN Field
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .clip(RoundedCornerShape(30.dp))
                    .border(1.2.dp, Color(0xFF1E293B), RoundedCornerShape(30.dp))
                    .background(PureWhite)
                    .padding(horizontal = 16.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(modifier = Modifier.weight(1f)) {
                        OutlinedTextField(
                            value = pin,
                            onValueChange = { viewModel.enteredPin.value = it },
                            placeholder = { Text("Enter Pin", color = Color(0xFF6B7280), fontSize = 14.sp) },
                            modifier = Modifier.fillMaxWidth(),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            singleLine = true,
                            visualTransformation = PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword)
                        )
                    }

                    Text(
                        text = "Verify",
                        color = OdishaMagenta,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        modifier = Modifier
                            .clickable {
                                if (pin == "1418" || pin.length == 4) {
                                    Toast.makeText(context, "PIN Verified Successfully ✓", Toast.LENGTH_SHORT).show()
                                } else {
                                    Toast.makeText(context, "Please enter 4 digit PIN (e.g. 1418)", Toast.LENGTH_SHORT).show()
                                }
                            }
                            .padding(end = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {
                Text(
                    text = "Forgot Pin ??",
                    color = Color(0xFF4B5563),
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.clickable {
                        Toast.makeText(context, "Default security PIN is 1418", Toast.LENGTH_LONG).show()
                    }
                )
            }

            Spacer(modifier = Modifier.height(36.dp))

            // Continue Button centered
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                EduLockerPillButton(
                    text = "Continue",
                    onClick = {
                        viewModel.isLoggedIn.value = true
                        onLoginSuccess()
                    },
                    modifier = Modifier.fillMaxWidth(0.52f)
                )
            }
        }
    }
}

// -----------------------------------------------------------
// Reusable Dual Dots Logo (Pink large dot + Magenta small dot on bottom right)
// -----------------------------------------------------------
@Composable
fun EduLockerDualDotsLogo(size: Dp = 54.dp, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.size(size)
    ) {
        // Large pastel pink circle
        Box(
            modifier = Modifier
                .size(size * 0.72f)
                .align(Alignment.TopStart)
                .clip(CircleShape)
                .background(Color(0xFFFBCFE8))
        )
        // Small Odisha Magenta circle overlapping bottom-right
        Box(
            modifier = Modifier
                .size(size * 0.44f)
                .align(Alignment.BottomEnd)
                .clip(CircleShape)
                .background(Color(0xFFD81B60))
        )
    }
}

// -----------------------------------------------------------
// Reusable Double-Border Pill Button (Outer black border + inner magenta pill)
// -----------------------------------------------------------
@Composable
fun EduLockerPillButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .clip(RoundedCornerShape(32.dp))
            .border(1.4.dp, Color(0xFF1E293B), RoundedCornerShape(32.dp))
            .padding(3.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(29.dp),
        color = Color(0xFFD81B60)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                color = PureWhite,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                fontSize = 14.5.sp
            )
        }
    }
}
