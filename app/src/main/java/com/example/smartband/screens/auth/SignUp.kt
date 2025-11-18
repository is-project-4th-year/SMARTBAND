package com.example.smartband.screens.auth

import android.annotation.SuppressLint
import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.smartband.R
import com.example.smartband.navigation.Screen
import com.google.firebase.auth.FirebaseAuth
// Firebase core
import com.google.firebase.FirebaseApp
import com.example.smartband.screens.auth.generateOtp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import sendOtpHtmlEmail

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(navController: NavController) {
    var text by remember { mutableStateOf("") }  // Holds the input text state
    var email by remember { mutableStateOf("") }
    var isValid by remember { mutableStateOf(true) }
    var password by remember {mutableStateOf(value = "")}
    var confirmPassword by remember {mutableStateOf(value = "")}
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    val headlineFont = FontFamily(Font(R.font.playfairdispla_black))
    val bodyFont = FontFamily(Font(R.font.helmet_regular))
    val isStrong = isValidPassword(password)
    val isMatching = password == confirmPassword && password.isNotEmpty()
    var passwordTouched by remember { mutableStateOf(false) }

    Scaffold (
        content = {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
//                .background(
//                    brush = Brush.verticalGradient(
//                        colors = listOf(
//                            Color(0xFFECDFF5),
//                            Color(0xFFB491C4)
//                        ))
//                )
        ){

            Image(
                painter = painterResource(id = R.drawable.neuroband),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(170.dp)
                    .align(Alignment.TopStart) // top-left of the screen
                    .padding(horizontal = 7.dp)
                    .offset(y=-(15).dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.9f)
                    .align(Alignment.BottomCenter)
                    .padding(20.dp)
                    .offset(y=(35).dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                Text("Sign Up",
                    fontFamily = headlineFont,
                    fontSize = 28.sp,
                    modifier = Modifier.padding(top = 10.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        email = it
                        isValid = Patterns.EMAIL_ADDRESS.matcher(it).matches()
                    },
                    label = { Text("Enter your email") },
                    isError = !isValid,
                    modifier = Modifier.fillMaxWidth()
                )
                if (!isValid) {
                    Text("Invalid email address",
                        color = Color.Red,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                OutlinedTextField(
                    value = password,
                    onValueChange = {
                        password = it
                        passwordTouched = true},
                    label = {Text ("Password")},
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = if (passwordVisible)
                        VisualTransformation.None
                    else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password), // ensures spell check does not apply to password
                    trailingIcon = {
                        val image = if (passwordVisible)
                            Icons.Default.Visibility
                        else {
                            Icons.Default.VisibilityOff
                        }
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(imageVector = image, contentDescription = null)
                        } },
                        isError = !isStrong && passwordTouched
                    )

                if (!isStrong && passwordTouched){
                    Text("Password must be 8 characters,include numbers, uppercase and special characters",
                        color = Color.Red,
                        style = MaterialTheme.typography.bodySmall
                    )}

                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange ={confirmPassword = it},
                    label = {Text ("Confirm Password")},
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = if (confirmPasswordVisible)
                        VisualTransformation.None
                    else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password), // ensures spell check does not apply to password
                    trailingIcon = {
                        val image = if (confirmPasswordVisible)
                            Icons.Default.Visibility
                        else {
                            Icons.Default.VisibilityOff
                        }
                        IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                            Icon(imageVector = image, contentDescription = null)
                        }},
                    isError = passwordTouched && !isMatching
                )

                if (!isMatching && passwordTouched){
                    Text("Passwords do not match",
                        color = Color.Red,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.fillMaxWidth(),
                        style = MaterialTheme.typography.bodySmall
                    )}

                Spacer(modifier = Modifier.height(5.dp))

                val context = LocalContext.current
                val auth = FirebaseAuth.getInstance()

                Button(
                    onClick = {
                        Firebase.auth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener {
                                task ->
                                if (task.isSuccessful){
                                    val user = auth.currentUser
                                    if (user!= null) {
                                        val otp = generateOtp()
                                        sendOtpHtmlEmail(
                                            apiKey = "edd7fc95-2cd3-4b01-8b65-eca88caf3f8c",
                                            senderEmail = "hawiana.bedada@strathmore.edu",
                                            recipientEmail = user.email!!,
                                            otp = otp,
                                            appName = "Smartband"
                                        )

                                        Toast.makeText(
                                            context,
                                            "Sign Up successful! Proceed",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        navController.navigate("screens/auth/VerifyOtp/$otp") //send otp to be verified
                                    }
                                }
                                else{
                                    Toast.makeText(context,
                                        task.exception?.message ?: "Invalid credentials", Toast.LENGTH_SHORT).show()
                                }
                            }
                    },
                    shape = RectangleShape,
                    colors = ButtonDefaults.buttonColors(colorResource(R.color.yellow)),
                    modifier = Modifier.fillMaxWidth().height(42.dp),
                    enabled = isMatching && passwordTouched
                )
                {
                    Text(
                        text = "Sign Up",
                        fontFamily = headlineFont,
                        color = Color.Black,
                        fontSize =  20.sp,
                        letterSpacing = 0.02.em
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ){
                    Text(
                        text = "Already have an account?",
                        color = Color.Black
                    )
                    Text(
                        text ="Sign In",
                        color = Color(0xFFA56ABD),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable{
                            navController.navigate(Screen.SignIn.route)
                        }
                    )
      }

//                Button(
//                    onClick = { navController.navigate(Screen.ChildInfo.route)},
//                    shape = RectangleShape,
//                    colors = ButtonDefaults.buttonColors(Color(0xFFFED711)),
//                    modifier = Modifier.fillMaxWidth(0.6f)
//                )
//                {
//                    Text(
//                        text = "SKIP",
//                        fontFamily = headlineFont,
//                        color = Color.Black,
//                        fontSize = 20.sp,
//                        letterSpacing = 0.02.em
//                    )
//                }
            }
    }}
    )
}

@Composable
fun isValidPassword(password:String):Boolean{
    val passwordPattern =  "^(?=.[0-9])(?=.[A-Z])(?=.*[@#\$%^&+=!])(?=\\S+$).{8,}$"
    return Regex(passwordPattern).matches(password)
}
