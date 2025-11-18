package com.example.smartband.screens.auth

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.smartband.R
import com.example.smartband.navigation.Screen
import com.example.smartband.screens.navbar.bodyFont
import com.example.smartband.screens.navbar.headlineFont2
import com.google.common.base.Strings.repeat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlin.repeat

fun saveUserEmail() {
    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()

    val user = auth.currentUser
    val email = user?.email
    val uid = user?.uid

    if (uid != null && email != null) {
        db.collection("users").document(uid)
            .set(hashMapOf("email" to email))
            .addOnSuccessListener {
                Log.d("Firestore", "User data saved")
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Error writing document", e)
            }
    }
}

@Composable
fun VerifyOtp(navController: NavController, generatedOtp: String) {
    var otpValue by remember { mutableStateOf("") }
    val maxLength = 6
    val headlineFont = FontFamily(Font(com.example.smartband.R.font.playfairdispla_black))

    val user = FirebaseAuth.getInstance().currentUser
    val email = user?.email


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.yellow))
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Enter Verification Code",
            fontFamily = headlineFont2,
            fontSize = 26.sp
        )
        
        Text(
            text = "An email has been sent to $email",
            fontSize = 15.sp,
            color = Color.Gray,
            fontFamily = bodyFont
        )


        // Invisible text field capturing input
        BasicTextField(
            value = otpValue,
            onValueChange = { newValue:String ->
                if (newValue.length <= maxLength) {
                    otpValue = newValue
                }
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            cursorBrush = SolidColor(Color.Black), // ✅ enables blinking cursor

            decorationBox = {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    repeat(maxLength) { index ->
                        val char = if (index < otpValue.length) {
                            otpValue[index].toString()
                        }
                        else {
                            ""
                        }

                        Text(
                            modifier = Modifier
                                .width(48.dp)
                                .height(56.dp)
                                .border(
                                    1.dp,
                                    Color.Gray,
                                    RoundedCornerShape(8.dp)
                                )
                                .background(Color.White, RoundedCornerShape(8.dp))
                                .padding(5.dp),
                            text = char,
                            color = Color.Black,
                            fontSize = 20.sp,

                            textAlign = TextAlign.Center
                        )
                    }
                        Spacer(modifier = Modifier.width(8.dp))
                    }
            }
        )
        val context = LocalContext.current

        Spacer(modifier = Modifier.height(13.dp))

        Button(
            onClick = {
                if (otpValue == generatedOtp) {
                    Toast.makeText(context, "OTP Verified ✅", Toast.LENGTH_SHORT).show()
                    saveUserEmail() // 🔹 just call function
                    navController.navigate(Screen.Landing.route)
                } else {
                    Toast.makeText(context, "Incorrect OTP ❌", Toast.LENGTH_SHORT).show()
                }
            },
            enabled = otpValue.length == maxLength,
            shape = RectangleShape,
            colors = ButtonDefaults.buttonColors(Color(0xFF000000)),
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                "Verify",
                fontFamily = headlineFont,
                color = Color.White,
                fontSize = 20.sp,
                letterSpacing = 0.02.em
            )
        }
    }
}



@Preview(showBackground = true)
@Composable
fun ViewVerify(){
    VerifyOtp(navController = rememberNavController(), generatedOtp= "123321")
}
