package com.example.smartband.screens.navbar.settings

import android.graphics.Paint
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.smartband.R
import com.example.smartband.navigation.Screen

@Composable
fun Help(
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
            .height(160.dp)
            .verticalScroll(rememberScrollState())
            .background(
                Color(0xFFFED711),
                shape = RoundedCornerShape(
                    bottomStart = 19.dp,
                    bottomEnd = 19.dp
                )
            ),
    ) {

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = { navController.navigate(Screen.SignIn.route) },
                modifier = Modifier.size(53.dp) //the clickable area
            ) {
                androidx.compose.material3.Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back icon",
                    modifier = Modifier.size(32.dp)
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Help & Support",
                fontSize = 29.sp,
                fontFamily = headlineFont2,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
    }
        Column() {
            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "🔹 Getting Started",
                fontSize = 22.sp,
                fontFamily = headlineFont2,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .border(
                        width = 1.dp,
                        color = Color.Black,
                        shape = RoundedCornerShape(16.dp)
                    )
            ) {
                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text =  " 1. Pair your SmartBand",
                    modifier = Modifier
                        .padding(start = 15.dp, end = 15.dp),
                    lineHeight = 2.em,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 19.sp,
                )
                Text(
                    "- Turn on Bluetooth on your phone.\n- Open the app and select Connect Device." +
                            "\n- Choose your smartband from the list.\n",
                    modifier = Modifier
                        .padding(start = 15.dp, end = 15.dp),
                    lineHeight = 2.em,
                    fontSize = 18.sp,
                )

                Text(
                    text =  "2. Wear your SmartBand\n",
                    modifier = Modifier
                        .padding(start = 15.dp),
                    lineHeight = 1.em,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 19.sp,
                )

                Text(
                    "- Fasten it securely on your wrist.\n- For best results, " +
                            "the sensors should touch your skin.\n",
                    modifier = Modifier
                        .padding(start = 15.dp, end = 15.dp),
                    lineHeight = 2.em,
                    fontSize = 18.sp

                )
            }

            // Tracking Features
            Text(
                text = "🔹 Tracking Features",
                fontSize = 22.sp,
                fontFamily = headlineFont2,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            Column(
                modifier = Modifier.padding(16.dp)
                    .border(
                        width = 1.dp,
                        color = Color.Black,
                        shape = RoundedCornerShape(16.dp)
                    )

            ) {

                Text("• Heart Rate ❤️ – View your real-time heart rate or check history under Analytics.",
                    modifier = Modifier
                        .padding(start = 15.dp, end = 15.dp),
                    fontFamily = bodyFont,
                    lineHeight = 2.em,
                    fontSize = 18.sp
                )
                Text("• Steps & Distance 👟 – Track your daily steps, calories burned, and distance walked.",
                    modifier = Modifier
                        .padding(start = 15.dp, end = 15.dp),
                    fontFamily = bodyFont,
                    lineHeight = 2.em,
                    fontSize = 18.sp
                )
                Text("• Sleep Monitoring 😴 – Wear the band at night to see your sleep patterns in the morning.",
                    modifier = Modifier
                        .padding(start = 15.dp, end = 15.dp),
                    fontFamily = bodyFont,
                    lineHeight = 2.em,
                    fontSize = 18.sp
                )
                Text("• Notifications 🔔 – Enable app notifications in settings to receive calls and message alerts.\n",
                    modifier = Modifier
                        .padding(start = 15.dp, end = 15.dp),
                    fontFamily = bodyFont,
                    lineHeight = 2.em,
                    fontSize = 18.sp
                )
            }

            // Common Issues
            Text(
                text = "🔹 Common Issues & Fixes",
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(vertical = 8.dp)
            )


            Column(
                modifier = Modifier.padding(14.dp)
                    .border(
                        width = 1.dp,
                        color = Color.Black,
                        shape = RoundedCornerShape(16.dp)
                    )

            ) {
                Text("• SmartBand not connecting – Ensure Bluetooth is on. Restart the smartband and try again.",
                    modifier = Modifier
                        .padding(start = 15.dp, end = 15.dp),
                    fontFamily = bodyFont,
                    lineHeight = 2.em,
                    fontSize = 18.sp
                    )

                Text("• No heart rate detected – Make sure the band is worn correctly and sensors are clean.",
                    modifier = Modifier
                        .padding(start = 15.dp, end = 15.dp),
                    fontFamily = bodyFont,
                    lineHeight = 2.em,
                    fontSize = 18.sp)

                Text("• Battery draining quickly – Reduce screen brightness or limit background sync.\n",
                    modifier = Modifier
                        .padding(start = 15.dp, end = 15.dp),
                    fontFamily = bodyFont,
                    lineHeight = 2.em,
                    fontSize = 18.sp)
            }
            // Support
            Text(
                text = "🔹 Need More Help?",
                fontSize = 22.sp,
                fontFamily = headlineFont2,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Text("• Check the FAQ section in the app.")
            Text("• Contact our support team at: support@smartbandapp.com")
            Text("• Visit our website: www.smartbandapp.com")
        }

    }
}

@Preview
@Composable
fun ViewHelp(){
    Help(navController = rememberNavController())
}