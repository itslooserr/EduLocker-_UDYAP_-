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
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.R
import com.example.ui.theme.CanvasCream
import com.example.ui.theme.OdishaMagenta
import com.example.ui.theme.PureWhite
import com.example.ui.theme.TextDark
import com.example.ui.theme.TextMuted
import com.example.ui.theme.UdyapCrimson
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
                .navigationBarsPadding()
                .padding(bottom = 32.dp),
            horizontalAlignment = Alignment.Start
        ) {
            EduLockerDualDotsLogo(size = 44.dp)

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "Welcome to UDYAP",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = TextDark,
                fontSize = 22.sp
            )

            Spacer(modifier = Modifier.height(3.dp))

            Text(
                text = "Sign in to continue your journey.",
                style = MaterialTheme.typography.bodyMedium,
                color = TextMuted,
                fontSize = 14.5.sp
            )

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "By continuing, you agree to UDYAP's Terms of Service\nand Privacy Policy.",
                style = MaterialTheme.typography.bodySmall,
                color = TextMuted,
                fontSize = 11.5.sp,
                lineHeight = 15.sp
            )

            Spacer(modifier = Modifier.height(28.dp))

            EduLockerPillButton(
                text = "Login with Aadhaar",
                onClick = onLoginWithAadhaar,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(14.dp))

            EduLockerPillButton(
                text = "Login with Phone",
                onClick = onLoginWithPhone,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(18.dp))

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Login with UDYAP ID",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextDark,
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
                .navigationBarsPadding()
                .padding(bottom = 36.dp),
            horizontalAlignment = Alignment.Start
        ) {
            EduLockerDualDotsLogo(size = 44.dp)

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "Verify your Aadhaar Identity",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = TextDark,
                fontSize = 21.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Verify your identity securely with Aadhaar to\naccess your UDYAP account",
                style = MaterialTheme.typography.bodyMedium,
                color = TextMuted,
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
                .navigationBarsPadding()
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
fun EduLockerDualDotsLogo(size: Dp = 44.dp, modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = R.drawable.udyap_logo_full),
        contentDescription = "UDYAP Logo",
        contentScale = ContentScale.Fit,
        modifier = modifier
            .height(size)
            .width(size * 2.65f)
    )
}

@Composable
fun EduLockerPillButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .clip(RoundedCornerShape(32.dp))
            .clickable { onClick() },
        shape = RoundedCornerShape(32.dp),
        color = UdyapCrimson
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 14.dp),
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
