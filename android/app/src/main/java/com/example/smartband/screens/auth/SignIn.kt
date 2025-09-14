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
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.smartband.R
import com.example.smartband.navigation.Screen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(navController: NavController) {

    val headlineFont = FontFamily(Font(com.example.smartband.R.font.playfairdispla_black))
    val bodyFont = FontFamily(Font(R.font.helmet_regular))
    
    Scaffold(
        topBar = {
            TopAppBar(title = {

                Text("",
                    fontFamily = headlineFont,
                    fontSize = 30.sp,
                    modifier = Modifier.padding(top = 10.dp)) })
        },
        bottomBar = {
            BottomAppBar {

            }
        }
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
                    modifier = Modifier.padding(top = 10.dp))


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
                    Text("Invalid email address", color = Color.Red)
                }

                OutlinedTextField(
                    value = password,
                    onValueChange ={password = it},
                    label = {Text ("Password")},
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(5.dp))
                Button(
                    onClick = { /* Handle form submission */},
                    shape = RectangleShape,
                    colors = ButtonDefaults.buttonColors(Color(0xFFFED711)),
                    modifier = Modifier.fillMaxWidth()
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

                HorizontalDivider(modifier = Modifier.fillMaxWidth(0.4f))
                Text(text = "or")
                HorizontalDivider(modifier = Modifier.fillMaxWidth(0.4f))

                Button(
                    onClick = { /* Handle form submission */},
                    shape = RectangleShape,
                    colors = ButtonDefaults.buttonColors(Color(0xFF000000)),
                    modifier = Modifier.fillMaxWidth()
                )
                {
                    Text(
                        text = "Sign In with Google",
                        fontFamily = headlineFont,
                        color = Color.White,
                        fontSize = 20.sp,
                        letterSpacing = 0.02.em
                    )
                }

            }
        }
    }
}

