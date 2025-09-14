package com.example.smartband.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material3.Button
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartband.R
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.em
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.smartband.navigation.Screen
import kotlinx.coroutines.launch

val customFont = FontFamily(Font(R.font.playfairdispla_black))
val bodyFont = FontFamily(Font(R.font.helmet_regular))


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnBoardingScreen(navController: NavController) {
    val pages = listOf(
        Triple(R.drawable.support, "Welcome", "Discover amazing features."),
        Triple(R.drawable.cheer, "Stay Connected", "Keep in touch with friends."),
        Triple(R.drawable.sulky, "Get Started", "Let’s begin your journey!")
    )
    val pagerState = rememberPagerState(pageCount = { 3 })
    val scope = rememberCoroutineScope()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        HorizontalPager(
            state = pagerState
        ) { page ->
            when (page) {
                0 -> { // Page 1 with pattern background
                    Box (
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.White),
                        ) {

                        Column(
                            modifier = Modifier
                                .height(230.dp)
                                .padding(20.dp),
                            verticalArrangement = Arrangement.Top
                        ) {

                            Spacer(modifier = Modifier.height(40.dp))
                            Text(
                                text = "You're Not Alone",
                                fontSize = 30.sp,
                                fontFamily = customFont,
                                letterSpacing = 0.03.em,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(horizontal = 10.dp)
                            )

                            Text(
                                text = "Receive personalized insights and practical guidance designed to " +
                                        "create a safe, nurturing environment where your child can thrive emotionally and socially.",
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
                                painter = painterResource(id = R.drawable.img), // replace with your image
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .offset(y= (-90).dp, x=(-20).dp)
                                    .alpha(0.7f)
                                    .padding(top = 20.dp),
                                contentScale = ContentScale.Fit
                            )

                            Image(
                                painter = painterResource(id = R.drawable.blobb2), // replace with your image
                                contentDescription = null,
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .fillMaxSize()
                                    .offset( y = (20).dp)
                                    .alpha(0.4f),
                                contentScale = ContentScale.Fit
                            )

                            Image(
                                painter = painterResource(id = R.drawable.cheer), // replace with your image
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .offset(y = (10).dp)
                                    .padding(top = 20.dp),
                                contentScale = ContentScale.Fit
                            )

                            DotsWithSkip(
                                pagerState = pagerState,
                                pages = pages,
                            ) {
                                scope.launch {
                                    pagerState.scrollToPage(pages.size - 1)
                                }
                            }
                        }
                    }
                }
                1 -> { // Page 2
                    Box (
                        modifier = Modifier
                            .background(Color(0xFFFFF2AC))
                    ) {

                        Column(
                            modifier = Modifier
                                .height(230.dp)
                                .padding(20.dp),
                            verticalArrangement = Arrangement.Top
                        ) {

                            Spacer(modifier = Modifier.height(40.dp))
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
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Inside
                            )

                            DotsWithSkip(
                                pagerState = pagerState,
                                pages = pages,
                            ) {
                                scope.launch {
                                    pagerState.scrollToPage(pages.size - 1)
                                }
                            }
                        }
                    }

                }
                2 -> { // Page 3
                    Box (
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.White),
                    ) {

                        Column(
                            modifier = Modifier
                                .height(230.dp)
                                .padding(20.dp),
                            verticalArrangement = Arrangement.Top
                        ) {

                            Spacer(modifier = Modifier.height(40.dp))

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
                                text = "Stay connected to your child's well-being with real-time updates from" +
                                        " their smartband.Stay connected to your child's well-being updates from " +
                                        "their smartband.",
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
                                .fillMaxWidth()
                                .height(580.dp)
                                .align(Alignment.Center),
                            contentAlignment = Alignment.BottomCenter
                        )
                        {
                            Image(
                                painter = painterResource(id = R.drawable.depression), // the phone
                                contentDescription = "Depression boy",
                                modifier = Modifier.fillMaxSize().padding(top=10.dp),
                                contentScale = ContentScale.Inside
                            )

                            Button(
                                onClick =
                                    {
                                        navController.navigate(Screen.SignIn.route)
                                    },
                                shape  = RectangleShape,
                                modifier = Modifier
                                    .fillMaxWidth(0.7f)
                                    .height(48.dp)
                                    .align(Alignment.BottomCenter)
                            ) {
                                Text(
                                    text = "Get Started",
                                    color = Color.White,
                                    fontSize = 19.sp,
                                    fontFamily = bodyFont
                                )
                            }

                            DotsWithNoSkip(
                                pagerState = pagerState,
                                pages = pages,
                            ) {
                                scope.launch {
                                    pagerState.scrollToPage(pages.size - 1)
                                }
                            }
                        }
                }
            }
            }}
    }
}
@Composable
fun DotsWithSkip(
    pagerState: PagerState,
    pages: List<Triple<Int, String, String>>,
    onSkipClick: () -> Unit
) {
    val pagerState = rememberPagerState(pageCount = { 3 })
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(horizontal = 22.dp)
            .offset(y = (-40).dp),
        horizontalArrangement = Arrangement.SpaceBetween, // push dots left & SKIP right
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Dots (aligned left)
        Row {
            repeat(pages.size) { index ->
                Box(
                    modifier = Modifier
                        .size(if (pagerState.currentPage == index) 14.dp else 13.dp)
                        .padding(4.dp)
                        .background(
                            if (pagerState.currentPage == index) {
                                Color(0xFFA56ABD) // active dot
                            } else {
                                Color(0xFFFED711) // inactive dot
                            },
                            shape = CircleShape
                        )
                )
            }
        }

        // SKIP text (aligned right)
        Text(
            text = "SKIP",
            fontSize = 23.sp,
            color = Color.DarkGray,
            fontFamily = bodyFont,
            modifier = Modifier.clickable { onSkipClick() }
        )
    }
}

@Composable
fun DotsWithNoSkip(
    pagerState: PagerState,
    pages: List<Triple<Int, String, String>>,
    onSkipClick: () -> Unit
) {
    val pagerState = rememberPagerState(pageCount = { 3 })
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(horizontal = 22.dp),
        horizontalArrangement = Arrangement.SpaceBetween, // push dots left & SKIP right
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Dots (aligned left)
        Row {
            repeat(pages.size) { index ->
                Box(
                    modifier = Modifier
                        .size(if (pagerState.currentPage == index) 14.dp else 13.dp)
                        .padding(4.dp)
                        .background(
                            if (pagerState.currentPage == index) {
                                Color(0xFFA56ABD) // active dot
                            } else {
                                Color(0xFFFED711) // inactive dot
                            },
                            shape = CircleShape
                        )
                )
            }
        }

        // SKIP text (aligned right)
        Text(
            text = "BACK",
            fontSize = 23.sp,
            color = Color.DarkGray,
            fontFamily = bodyFont,
            modifier = Modifier.clickable { onSkipClick() }
        )
    }
}

@Preview
@Composable
fun ViewssPage(){
    OnBoardingScreen(navController = rememberNavController())
}


