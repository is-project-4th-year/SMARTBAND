package com.example.smartband.screens.navbar.settings

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import kotlinx.coroutines.tasks.await

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
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
import com.google.firebase.auth.FirebaseAuth
import androidx.compose.ui.tooling.preview.Preview
import com.example.smartband.screens.auth.GoogleSignInButton
import com.example.smartband.screens.auth.generateOtp
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.identity.SignInCredential
import com.google.firebase.firestore.FirebaseFirestore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfile(
    uid: String,
    db: FirebaseFirestore,
    navController : NavController
){
    val headlineFont1 = FontFamily(Font(R.font.playfairdispla_black))
    val headlineFont2 = FontFamily(Font(R.font.playfairdisplay_bold))
    val bodyFont = FontFamily(Font(R.font.helmet_regular))
//    val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),

            ) {
            var parentFirstName by remember { mutableStateOf("") }
            var parentSurname by remember { mutableStateOf("") }
            var phoneNumber by remember { mutableStateOf("") }
            // var  by remember { mutableStateOf("") }
            var email by remember { mutableStateOf("") }
            var isValid by remember { mutableStateOf(true) }

            val context = LocalContext.current
            LaunchedEffect(uid) {
                db.collection("users").document(uid).get()
                    .addOnSuccessListener { document ->
                        if (document != null && document.exists()) {
                            parentFirstName = document.getString("parentFirstName") ?: ""
                            parentSurname = document.getString("parentSurname") ?: ""
                            phoneNumber = document.getString("phoneNumber") ?: ""
                            email = document.getString("email") ?: ""
                        }
                    }
                    .addOnFailureListener { e ->
                        // ✅ context is safe here because we captured it earlier
                        Toast.makeText(context, "Failed to load profile", Toast.LENGTH_SHORT).show()
                        Log.e("EditProfile", "Firestore error", e)
                    }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colorResource(R.color.yellow),shape = RoundedCornerShape(19.dp))
                    .height(147.dp)
                    .clip(RoundedCornerShape(19.dp)),
                ){

                Spacer(modifier = Modifier.height(20.dp))

                IconButton(
                    onClick = { navController.navigate(Screen.Landing.route) },
                    modifier = Modifier.size(53.dp) //the clickable area
                ) {
                    androidx.compose.material3.Icon(imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back icon",
                        modifier = Modifier.size(32.dp)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Parent Profile",
                    fontFamily = headlineFont2,
                    fontSize = 22.sp,
                    letterSpacing = 0.03.em,
                    modifier = Modifier.padding(horizontal = 24.dp)
                )
            }


            Column(
                modifier = Modifier
                    .background(Color.White)
                    .padding(22.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ){
            Text(
                text = "First Name",
                fontFamily = bodyFont,
                fontSize = 17.sp
            )

            OutlinedTextField(
                value = parentFirstName,
                onValueChange = { parentFirstName = it },
                label = { Text("") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    containerColor = Color(0xFFF5F5F5)


                ),
                singleLine = true
            )

            Text(
                text = "Surname",
                fontFamily = bodyFont,
                fontSize = 17.sp,
            )

            OutlinedTextField(
                value = parentSurname,
                onValueChange = { parentSurname = it },
                label = { Text("") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Black,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    containerColor = Color(0xFFF5F5F5)
                ),
                singleLine = true
            )

            Text(
                text = "Email",
                fontFamily = bodyFont,
                fontSize = 17.sp,
            )

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    containerColor = Color(0xFFF5F5F5)

                ),
                singleLine = true
            )

            Text(
                text = "Phone Number",
                fontFamily = bodyFont,
                fontSize = 17.sp,
                modifier = Modifier.padding(top = 10.dp)
            )

            OutlinedTextField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                label = { Text("") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    containerColor = Color(0xFFF5F5F5)

                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(15.dp))

            Button(
                onClick = {
                    val updates = hashMapOf(
                        "parentFirstName" to parentFirstName,
                        "parentSurname" to parentSurname,
                        "email" to email,
                        "phoneNumber" to phoneNumber
                    )

                    db.collection("users").document(uid)
                        .update(updates as Map<String, Any>)
                        .addOnSuccessListener {
                            Toast.makeText(context, "Profile updated", Toast.LENGTH_SHORT).show()
                            navController.popBackStack() // go back
                        }
                        .addOnFailureListener {
                            Toast.makeText(context, "Update failed", Toast.LENGTH_SHORT).show()
                        }
                },

                colors = ButtonDefaults.buttonColors(colorResource(R.color.purple)),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .border( width = 1.dp, color = Color.Black, shape = RoundedCornerShape(16.dp) )
                    .height(45.dp)
                    .align(Alignment.CenterHorizontally)
            ) {

                Text(
                    text = "Update Details",
                    fontFamily = com.example.smartband.screens.navbar.headlineFont2,
                    color = Color.Black,
                    fontSize = 18.sp,
                    letterSpacing = 0.02.em
                )
            }

        }
        }
}




