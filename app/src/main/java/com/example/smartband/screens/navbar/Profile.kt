package com.example.smartband.screens.navbar

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Person3
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.smartband.R
import com.example.smartband.navigation.Screen
import com.example.smartband.navigation.Screen.Analytics.route
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

val headlineFont1 = FontFamily(Font(R.font.playfairdispla_black))
val headlineFont2 = FontFamily(Font(R.font.playfairdisplay_bold))
val bodyFont = FontFamily(Font(R.font.helmet_regular))

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Profile(
    navController: NavController
){

//    val userFirstName = remember { mutableStateOf("Loading...") }
//    val userSurname = remember { mutableStateOf("")}
//    val userEmail = remember { mutableStateOf("") }
//
//    val uid = FirebaseAuth.getInstance().currentUser?.uid
//    val db = FirebaseFirestore.getInstance()
//    LaunchedEffect(uid) {
//        if (uid != null) {
//            db.collection("users").document(uid).get()
//                .addOnSuccessListener { document ->
//                    if (document.exists()) {
//                        userFirstName.value = document.getString("firstName") ?: "Unknown User"
//                        userSurname.value = document.getString("surname") ?:  "Unknown User"
//                        userEmail.value = document.getString("email") ?:  "Unknown User"
//                    }
//                }
//                .addOnFailureListener {
//                    userFirstName.value = "Error loading"
//                    userSurname.value = "Error loading"
//                }
//        }
//    }

    SettingsPage(navController = navController)
}

@Composable
fun SettingsPage(navController: NavController) {

    val settingsItems = listOf(
        Triple("Edit Profile", Icons.Default.Person, Screen.EditProfile),
        Triple("Notifications", Icons.Default.Notifications, "Landing"),
        Triple("Change Avatar", Icons.Default.Person3, "Help"),
        Triple("Reset Password", Icons.Default.LockOpen, "Help"),
//        Triple("Privacy", Icons.Default.Lock, "SignIn"),
//        Triple("Help & Support", Icons.Default.Help, "Help")
    )

    var isSelected by remember { mutableStateOf(false) }

    Spacer(modifier = Modifier.height(27.dp))

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFCE798)),
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .background(Color.Gray)
        ) {
                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    IconButton(
                        onClick = { navController.navigate(Screen.Landing.route) },
                        modifier = Modifier.size(53.dp) //the clickable area
                    ){
                        androidx.compose.material3.Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back icon",
                            modifier = Modifier.size(32.dp)
                        )
                    }

                    Image(
                        painter = painterResource(id = R.drawable.boy), // replace with your image
                        contentDescription = null,
                        modifier = Modifier
                            .size(60.dp)
                            //     .align(Alignment.End)
                            .padding(end = 20.dp)
                    )
                }

                Spacer(modifier = Modifier.height(18.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {

                    Image(
                        painter = painterResource(id = R.drawable.user), // replace with your image
                        contentDescription = null,
                        modifier = Modifier
                            .size(70.dp)
                    )
                }

            Spacer(modifier = Modifier.height(14.dp))
            Row (
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ){
                Text(
//            text = "${userFirstName.value} ${userSurname.value}",
                    text = "Gawiwi",
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center,
                    fontFamily = headlineFont2
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

             Row (
                 modifier = Modifier
                     .fillMaxWidth(),
                 horizontalArrangement = Arrangement.Center
                    ){
                        Text(
        //                        text = "${userFirstName.value} ${userSurname.value}",
                            text = "gawi@gmail.com",
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center,
                            fontFamily = bodyFont
                        )
    
                        Spacer(modifier = Modifier.height(7.dp))
                    }
        }

        Column(
            modifier = Modifier
                .fillMaxHeight(0.5f)
                .offset(y= 20.dp),
            verticalArrangement = Arrangement.spacedBy(23.dp), // adds space between cards
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            settingsItems.forEach { (title, icon, route) ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .padding(horizontal = 15.dp)
                    .clickable { isSelected = !isSelected },
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isSelected) Color(0xFFA56ABD) else Color.White
                    ),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {

                    SettingsRow(title = title, icon = icon) {
                        when (route) {
                            is Screen -> navController.navigate(route.route) // for your Screen objects
                            is String -> navController.navigate(route)       // for plain strings
                        }
                    }
                }

            }
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}


@Composable
fun SettingsRow(title: String, icon: ImageVector, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 14.dp, horizontal = 13.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = Color.DarkGray,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = title,
            fontSize = 17.sp,
            letterSpacing = 0.02.em,
            fontFamily = bodyFont,
            modifier = Modifier.weight(1f)
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun ViewSettings(){
    Profile(navController = rememberNavController())
}