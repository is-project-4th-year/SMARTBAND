package com.example.smartband

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.smartband.navigation.Screen
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavHostController,
    onSplashFinished: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            //.background(Color(0xFFF6E0FF)),
            .background(
                brush = Brush.verticalGradient(
                colors = listOf(
                    Color(0xFFECDFF5),
                    Color(0xFFA97DBB)
                ))),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.neuroband),
            contentDescription = "Logo",
            modifier = Modifier.size(230.dp)
        )
    }

    // Navigate after a delay
    LaunchedEffect(Unit) {
        delay(2000) // 2s
        onSplashFinished()
//        navController.navigate(Screen.Onboarding.route) {
//            popUpTo("Splash") { inclusive = true }
//        }
    }
}
