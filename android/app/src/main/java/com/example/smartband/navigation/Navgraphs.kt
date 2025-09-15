package com.example.smartband.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.smartband.screens.OnBoardingScreen
import com.example.smartband.screens.auth.SignInScreen
import com.example.smartband.screens.auth.SignUpScreen
import com.example.smartband.screens.auth.ChildInfo
import com.example.smartband.screens.LandingPage
import com.example.smartband.screens.navbar.Schedule
import com.example.smartband.screens.navbar.Analytics
import com.example.smartband.screens.navbar.Profile


@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Onboarding.route
    ) {
        composable(Screen.Onboarding.route) {
            OnBoardingScreen(navController = navController)
        }
        composable(Screen.SignIn.route) {
            SignInScreen(navController = navController)
        }
        composable(Screen.Landing.route) {
            LandingPage(navController = navController)
        }
        composable(Screen.SignUp.route) {
            SignUpScreen(navController = navController)
        }
        composable(Screen.ChildInfo.route) {
            ChildInfo(navController = navController)
        }
        composable(Screen.Schedule.route) {
            Schedule(navController = navController)
        }
        composable(Screen.Profile.route) {
            Profile(navController = navController)
        }
        composable(Screen.Analytics.route) {
            Analytics(navController = navController)
        }
    }
}
