package com.example.smartband.screens.auth

import android.util.Patterns
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(navController: NavController) {
    var text by remember { mutableStateOf("") }  // Holds the input text state
    var email by remember { mutableStateOf("") }
    var isValid by remember { mutableStateOf(true) }
    var password by remember {mutableStateOf(value = "")}
    var confirmPassword by remember {mutableStateOf(value = "")}
    var passwordVisible by remember { mutableStateOf(false) }


    val headlineFont = FontFamily(Font(R.font.playfairdispla_black))
    val bodyFont = FontFamily(Font(R.font.helmet_regular))
    val isStrong = isValidPassword(password)
    val isMatching = password == confirmPassword && password.isNotEmpty()
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text("",
                    fontFamily = headlineFont,
                    fontSize = 29.sp) })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { innerPadding ->
        // Main content goes here
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color.White),
            contentAlignment = Alignment.Center) {

            Column(
                modifier = Modifier.fillMaxWidth().fillMaxHeight(0.9f).padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                Text("Sign Up",
                    fontFamily = headlineFont,
                    fontSize = 30.sp,
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
                    Text("Invalid email address", color = Color.Red)
                }

                OutlinedTextField(
                    value = password,
                    onValueChange = {password = it},
                    label = {Text ("Password")},
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = if (passwordVisible)
                        VisualTransformation.None
                    else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password), // ensures spell check does not apply to password
                    trailingIcon = {
                        val image = if (passwordVisible)
                            Icons.Default.Visibility
                        else Icons.Default.VisibilityOff},
                        isError = !isStrong && password.isEmpty()
                    )

                if (!isStrong && password.isEmpty()){
                    Text("Password must be 8 chars,include numbers, uppercase and special character",
                        color = Color.Red,
                        style = MaterialTheme.typography.bodySmall
                    )}

                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange ={confirmPassword = it},
                    label = {Text ("Confirm Password")},
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = PasswordVisualTransformation(),
                    isError = confirmPassword.isNotEmpty() && !isMatching
                )

                if (!isMatching){
                    Text("Passwords do not match",
                        color = Color.Red,
                        textAlign = TextAlign.Left,
                        style = MaterialTheme.typography.bodySmall
                    )}

                Spacer(modifier = Modifier.height(5.dp))

                Button(
                    onClick = { /* Handle form submission */},
                    shape = RectangleShape,
                    colors = ButtonDefaults.buttonColors(Color(0xFFFED711)),
                    modifier = Modifier.fillMaxWidth().height(42.dp)
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
                Button(
                    onClick = { navController.navigate(Screen.ChildInfo.route)},
                    shape = RectangleShape,
                    colors = ButtonDefaults.buttonColors(Color(0xFFFED711)),
                    modifier = Modifier.fillMaxWidth(0.6f)
                )
                {
                    Text(
                        text = "SKIP",
                        fontFamily = headlineFont,
                        color = Color.Black,
                        fontSize = 20.sp,
                        letterSpacing = 0.02.em
                    )
                }
            }
        }
    }
}

@Composable
fun isValidPassword(password:String):Boolean{
    val passwordPattern =  "^(?=.[0-9])(?=.[A-Z])(?=.*[@#\$%^&+=!])(?=\\S+$).{8,}$"
    return Regex(passwordPattern).matches(password)
}