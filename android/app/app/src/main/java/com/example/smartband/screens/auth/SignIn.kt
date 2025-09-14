package com.example.smartband.screens.auth

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.activity.compose.setContent
import androidx.activity.ComponentActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.smartband.R
import com.example.smartband.navigation.Screen
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import sendOtpHtmlEmail
import com.example.smartband.screens.auth.VerifyOtp
import com.google.firebase.auth.GoogleAuthProvider
import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.compose.ui.graphics.RectangleShape
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.identity.SignInCredential

fun generateOtp(length: Int = 6): String {
    val digits = "0123456789"
    return (1..length)
        .map { digits.random() }
        .joinToString("")
}

val headlineFont = FontFamily(Font(com.example.smartband.R.font.playfairdispla_black))

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(navController: NavController) {
    var passwordVisible by remember { mutableStateOf(false) }

    val bodyFont = FontFamily(Font(R.font.helmet_regular))
    
    Scaffold(
        modifier = Modifier.background(Color.White),

    ) { innerPadding ->
        // Main content goes here
        Box(modifier = Modifier.padding(innerPadding).fillMaxSize().background(Color.White),
            contentAlignment = Alignment.Center) {
            var text by remember { mutableStateOf("") }  // Holds the input text state
            var email by remember { mutableStateOf("") }
            var isValid by remember { mutableStateOf(true) }
            var password by remember {mutableStateOf(value = "")}

            Column(
                modifier = Modifier.fillMaxWidth().fillMaxHeight(0.8f).padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text("Sign In",
                    fontFamily = headlineFont,
                    fontSize = 30.sp,
                    modifier = Modifier.padding(top = 10.dp)
                )


                OutlinedTextField(
                    value = email,
                    shape = RectangleShape,
                    onValueChange = {
                        email = it
                        isValid = android.util.Patterns.EMAIL_ADDRESS.matcher(it).matches()
                    },
                    label = { Text("Email") },
                    isError = !isValid,
                    modifier = Modifier.fillMaxWidth()
                )
                if (!isValid) {
                    Text("Invalid email address",
                        color = Color.Red,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.fillMaxWidth(),)
                }

                OutlinedTextField(
                    value = password,
                    onValueChange ={password = it},
                    label = {Text ("Password")},
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
                        }},
                    modifier = Modifier.fillMaxWidth()
                )
                val context = LocalContext.current
                val auth = FirebaseAuth.getInstance()

                Spacer(modifier = Modifier.height(5.dp))
                Button(
                    onClick = {
                            Firebase.auth.signInWithEmailAndPassword(email,password)
                                .addOnCompleteListener {
                                        task ->
                                    if(task.isSuccessful){
                                        val user = auth.currentUser
                                        if (user!= null){
                                            val otp = generateOtp()
                                            sendOtpHtmlEmail(
                                                apiKey = "edd7fc95-2cd3-4b01-8b65-eca88caf3f8c",
                                                senderEmail = "hawiana.bedada@strathmore.edu",
                                                recipientEmail = user.email!!,
                                                otp = otp,
                                                appName = "Smartband"
                                            )
                                            Toast.makeText(context,"Sign In successful.Proceed!", Toast.LENGTH_SHORT).show()
                                            navController.navigate("screens/auth/VerifyOtp/$otp")
                                        }
                                        else{
                                            Toast.makeText(context,"Email didn't work!", Toast.LENGTH_SHORT).show()
                                        }

                                    }
                                    else{
                                        Toast.makeText(context,"Invalid Credentials", Toast.LENGTH_SHORT).show()
                                    }
                                }
                    },
                    shape = RectangleShape,
                    colors = ButtonDefaults.buttonColors(Color(0xFFFED711)),
                    modifier = Modifier.fillMaxWidth(),
                    enabled = isValid

                )
                {
                    Text(
                        text = "Sign In",
                        fontFamily = headlineFont,
                        color = Color.Black,
                        fontSize = 20.sp,
                        letterSpacing = 0.02.em
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ){
                    Text(
                        text = "Don't have an account?",
                        color = Color.Black
                    )
                    Text(
                        text ="Sign Up",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFA56ABD),
                        modifier = Modifier.clickable{
                            navController.navigate(Screen.SignUp.route)
                        }
                    )
                }


                Spacer(modifier = Modifier.height(5.dp))

                HorizontalDivider(modifier = Modifier.fillMaxWidth(0.5f))

                Spacer(modifier = Modifier.height(5.dp))
                GoogleSignInButton()

            }
        }
    }
}


@Composable
fun GoogleSignInButton() {
    val context = LocalContext.current
    val oneTapClient = Identity.getSignInClient(context)

    // Launcher that handles the result
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK && result.data != null) {
            try {
                val credential = oneTapClient.getSignInCredentialFromIntent(result.data)
                val idToken = credential.googleIdToken
                if (idToken != null) {
                    val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
                    FirebaseAuth.getInstance().signInWithCredential(firebaseCredential)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val user = FirebaseAuth.getInstance().currentUser
                                println("✅ Signed in as: ${user?.email}")
                            } else {
                                println("❌ Sign-in failed: ${task.exception?.message}")
                            }
                        }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    Button(onClick = {
        val signInRequest = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(context.getString(R.string.default_web_client_id)) // from strings.xml
                    .setFilterByAuthorizedAccounts(false)
                    .build()
            )
            .build()

        oneTapClient.beginSignIn(signInRequest)
            .addOnSuccessListener { result ->
                val intentSenderRequest =
                    IntentSenderRequest.Builder(result.pendingIntent.intentSender).build()
                launcher.launch(intentSenderRequest)
            }
            .addOnFailureListener { e ->
                println("❌ Sign-in failed to start: ${e.message}")
            }
    } ,
        shape = RectangleShape,
        colors = ButtonDefaults.buttonColors(Color.Black),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row {
            Text(
                text = "Sign in with Google",
                color = Color.White,
                fontFamily = headlineFont,
                fontSize = 20.sp,
                letterSpacing = 0.02.em
            )
        }
    }
}