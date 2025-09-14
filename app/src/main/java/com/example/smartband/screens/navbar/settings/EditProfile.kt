package com.example.smartband.screens.navbar.settings

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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import androidx.compose.ui.tooling.preview.Preview
import com.example.smartband.screens.auth.GoogleSignInButton
import com.example.smartband.screens.auth.generateOtp
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.identity.SignInCredential

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfile(
    navController : NavController
){
    val headlineFont1 = FontFamily(Font(R.font.playfairdispla_black))
    val headlineFont2 = FontFamily(Font(R.font.playfairdisplay_bold))
    val bodyFont = FontFamily(Font(R.font.helmet_regular))

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(190.dp)
                .background(
                    Color(0xFFFCE798),
                    shape = RoundedCornerShape(
                        bottomStart = 19.dp,
                        bottomEnd = 19.dp
                    )
                ),
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                IconButton(
                    onClick = { navController.navigate(Screen.Profile.route) },
                    modifier = Modifier.size(53.dp) //the clickable area
                ){
                    androidx.compose.material3.Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back icon",
                        modifier = Modifier.size(32.dp)
                    )
                }
            }

            Box(
                modifier = Modifier
                    .size(100.dp) // circle size
                    .align(Alignment.CenterHorizontally)
                    .offset(y = (-10).dp)
                    .background(Color.LightGray, CircleShape), // circular background
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.default_parent_profile), // replace with your image
                    contentDescription = null,
                    modifier = Modifier
                        .size(50.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        Column(
          modifier = Modifier
              .fillMaxSize()
              .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(9.dp)
        ){
            var firstName by remember { mutableStateOf("") }
            var surname by remember { mutableStateOf("") }
            var phoneNumber by remember { mutableStateOf("") }
           // var  by remember { mutableStateOf("") }
            var email by remember { mutableStateOf("") }
            var isValid by remember { mutableStateOf(true) }

            Text(
                text = "First Name",
                fontFamily = bodyFont,
                fontSize = 17.sp,
                modifier = Modifier.padding(top = 10.dp)
            )

            OutlinedTextField(
                value = firstName,
                shape = RectangleShape,
                onValueChange = {
                    firstName = it
                },
                label = { Text("") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Text(
                text = "Surname",
                fontFamily = bodyFont,
                fontSize = 17.sp,
                modifier = Modifier.padding(top = 10.dp)
            )

            OutlinedTextField(
                value = surname,
                onValueChange = { surname = it },
                label = { Text("") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

//            Text(
//                text = "Surname",
//                fontFamily = bodyFont,
//                fontSize = 17.sp,
//                modifier = Modifier.padding(top = 10.dp)
//            )
//
//            OutlinedTextField(
//                value = surname,
//                onValueChange = { surname = it },
//                label = { Text("") },
//                modifier = Modifier.fillMaxWidth(),
//                singleLine = true
//            )

            Text(
                text = "Email",
                fontFamily = bodyFont,
                fontSize = 17.sp,
                modifier = Modifier.padding(top = 10.dp)
            )

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("") },
                modifier = Modifier.fillMaxWidth(),
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
                singleLine = true
            )

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = { navController.navigate(Screen.Landing.route) },
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(Color(0xFFCE85EC)),
                modifier = Modifier.fillMaxWidth(0.8f).height(42.dp).align(Alignment.CenterHorizontally)
            ) {
                Text(
                    text = "Update details ",
                    fontFamily = headlineFont2,
                    color = Color.Black,
                    fontSize = 20.sp,
                    letterSpacing = 0.02.em
                )
            }

        }
    }
}




@Preview
@Composable
fun ViewEdit(){
    EditProfile(navController = rememberNavController())
}