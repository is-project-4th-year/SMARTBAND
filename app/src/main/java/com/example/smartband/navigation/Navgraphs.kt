package com.example.smartband.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.smartband.screens.OnBoardingScreen
import com.example.smartband.screens.auth.SignInScreen
import com.example.smartband.screens.auth.SignUpScreen
import com.example.smartband.screens.auth.ChildInfo
import com.example.smartband.screens.LandingPage
import com.example.smartband.screens.auth.VerifyOtp
import com.example.smartband.screens.navbar.Profile
import com.example.smartband.screens.navbar.Analytics
import com.example.smartband.screens.navbar.Schedule
import com.example.smartband.screens.navbar.settings.ChangeAvatar
import com.example.smartband.screens.navbar.settings.EditProfile
import com.example.smartband.screens.navbar.settings.Help

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SetupNavGraph(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = Screen.Landing.route
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
        composable(
            Screen.VerifyOtp.route,
            arguments = listOf(navArgument("otp") { type = NavType.StringType })
        ) {backStackEntry ->
            val otp = backStackEntry.arguments?.getString("otp") ?: ""
            VerifyOtp(navController = navController, generatedOtp = otp)
        }

        composable(Screen.EditProfile.route) {
            EditProfile(navController = navController)
        }

        composable(Screen.Help.route) {
            Help(navController = navController)
        }

        composable(Screen.ChangeAvatar.route) {
            ChangeAvatar(navController = navController)
        }




    }
}
