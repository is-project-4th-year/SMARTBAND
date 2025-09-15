package com.example.smartband.screens

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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartband.R
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.em
import androidx.navigation.NavController
import com.example.smartband.navigation.Screen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnBoardingScreen(navController: NavController) {
    val pages = listOf(
        Triple(R.drawable.onboard1, " ", "Discover amazing features."),
        Triple(R.drawable.onboard2, " ", "Keep in touch with friends."),
        Triple(R.drawable.onboard2, " ", "Let’s begin your journey!")
    )
    val pagerState = rememberPagerState(pageCount = { 3 })
    val scope = rememberCoroutineScope()
    val customFont = FontFamily(Font(R.font.playfairdispla_black))
    val bodyFont = FontFamily(Font(R.font.helmet_regular))
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp)
            .background(Color.White)
    ) {

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { page ->
            when (page) {
                0 -> { // Page 1 with pattern background
                    Box (
                        modifier = Modifier
                            .fillMaxSize()
                            .offset(y = (30).dp)
                            .background(Color.White),
                        ) {

                        Column(
                            modifier = Modifier
                                .height(230.dp)
                                .padding(20.dp),
                            verticalArrangement = Arrangement.Top
                        ) {

                            Text(
                                text = "You're Not Alone",
                                fontSize = 30.sp,
                                fontFamily = customFont,
                                letterSpacing = 0.03.em,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(horizontal = 10.dp)
                            )

                            Text(
                                text = "Receive insights and guidance to help create a safe, supportive environment for your child.",
                                fontSize = 17.sp,
                                color = Color.Gray,
                                fontFamily = bodyFont,
                                letterSpacing = 0.02.em,
                                lineHeight = 28.sp,
                                textAlign = TextAlign.Left,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 15.dp)
                            )
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .align(Alignment.CenterStart),
                            contentAlignment = Alignment.BottomCenter
                        )
                        {

                            Image(
                                painter = painterResource(id = R.drawable.cheer), // replace with your image
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(top = 20.dp),
                                contentScale = ContentScale.Fit
                            )
                            Box(
                                modifier = Modifier
                                    .align(Alignment.BottomEnd)
                                    .padding(end = 20.dp)
                                    .clickable { navController.navigate(Screen.SignIn.route)},
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = "Skip",
                                    fontFamily = bodyFont,
                                    fontSize = 23.sp,
                                    color = Color.Gray)
                            }
                        }
                    }
                }
                1 -> { // Page 2
                    Box (
                        modifier = Modifier
                            .fillMaxSize()
                            .offset(y = (30).dp)
                            .background(Color.White),
                    ) {

                        Column(
                            modifier = Modifier
                                .height(230.dp)
                                .padding(20.dp),
                            verticalArrangement = Arrangement.Top
                        ) {

                            Text(
                                text = "Track & Support",
                                fontSize = 30.sp,
                                letterSpacing = 0.02.em,
                                fontFamily = customFont,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Left,
                                modifier = Modifier.padding(horizontal = 10.dp)
                            )

                            Text(
                                text = "Monitor activity, sleep and health patterns to better understand and support your child's daily needs.",
                                fontSize = 17.sp,
                                color = Color.Gray,
                                fontFamily = bodyFont,
                                letterSpacing = 0.02.em,
                                lineHeight = 28.sp,
                                textAlign = TextAlign.Left,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp)
                            )
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(vertical = 10.dp)
                                .align(Alignment.CenterStart),
                            contentAlignment = Alignment.BottomCenter
                        )
                        {
                            Image(
                                painter = painterResource(id = R.drawable.support),
                                contentDescription = "Girl floating on hand",
                                modifier = Modifier.fillMaxSize()
                            )

                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(end = 20.dp)
                                .clickable { navController.navigate(Screen.SignIn.route)},
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "Skip",
                                fontFamily = bodyFont,
                                fontSize = 23.sp,
                                color = Color.Gray)
                        }}
                    }

                }
                2 -> { // Page 3
                    Box (
                        modifier = Modifier
                            .fillMaxSize()
                            .offset(y = (30).dp)
                            .background(Color.White),
                    ) {

                        Column(
                            modifier = Modifier
                                .height(230.dp)
                                .padding(20.dp),
                            verticalArrangement = Arrangement.Top
                        ) {

                            Text(
                                text = "Peace of Mind",
                                fontSize = 30.sp,
                                fontFamily = customFont,
                                letterSpacing = 0.02.em,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Left,
                                modifier = Modifier.padding(horizontal = 10.dp)
                            )

                            Text(
                                text = "Stay connected to your child's well-being with real-time updates from their smartband.",
                                fontSize = 17.sp,
                                color = Color.Gray,
                                fontFamily = bodyFont,
                                letterSpacing = 0.02.em,
                                lineHeight = 28.sp,
                                textAlign = TextAlign.Left,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp)
                            )
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .align(Alignment.CenterStart),
                            contentAlignment = Alignment.BottomCenter
                        )
                        {
                            Image(
                                painter = painterResource(id = R.drawable.sulky), // the phone
                                contentDescription = "Sulky girl",
                                modifier = Modifier.fillMaxSize().padding(top=20.dp),
                                contentScale = ContentScale.Inside
                            )

                        }
                        Box(
                            modifier = Modifier
                            .fillMaxSize()
                            .align(Alignment.BottomEnd),
                            contentAlignment = Alignment.BottomCenter
                        ){
                        Button(
                            onClick =
                                {
                                    navController.navigate(Screen.SignIn.route)
                                },
                            shape  = RectangleShape,
                            modifier = Modifier
                                .fillMaxWidth(0.7f).height(45.dp)
//                                .align(Alignment.BottomCenter)
                                .offset(y=(-50).dp)
                        ) {
                            Text(
                                text = "Get Started",
                                color = Color.White,
                                fontSize = 19.sp,
                                fontFamily = bodyFont
                            )
                        }
                        }
                }
            }
            }
        }

        // Simple dots indicator
        Row(
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(start = 22.dp)
        ) {

            repeat(pages.size) { index ->
                Box(
                    modifier = Modifier
                        .size(if (pagerState.currentPage == index) 14.dp else 13.dp)
                        .padding(4.dp)
                        .background(
                            if (pagerState.currentPage == index) {
                                Color(0xFFA56ABD)
                            } else {
                                // MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                                Color(0xFFFED711)
                            },
                            shape = CircleShape
                        )
                )

            }
        }

    }
}




