package com.example.smartband.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.smartband.SplashScreen
import com.example.smartband.data.AvatarViewModel
import com.example.smartband.influx.InfluxLocationModel
import com.example.smartband.influx.InfluxViewModel
import com.example.smartband.influx.ReportViewModel
import com.example.smartband.screens.OnBoardingScreen
import com.example.smartband.screens.auth.SignInScreen
import com.example.smartband.screens.auth.SignUpScreen
import com.example.smartband.screens.auth.ChildInfo
import com.example.smartband.screens.LandingPage
import com.example.smartband.screens.auth.VerifyOtp
import com.example.smartband.screens.navbar.Profile
import com.example.smartband.screens.navbar.Reports
import com.example.smartband.screens.navbar.Schedule
import com.example.smartband.screens.navbar.Map
import com.example.smartband.screens.navbar.settings.ChangeAvatar
import com.example.smartband.screens.navbar.settings.EditProfile
import com.example.smartband.screens.navbar.settings.Help
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SetupNavGraph(navController: NavHostController) {
    val avatarViewModel: AvatarViewModel = viewModel() //
    val influx: InfluxViewModel = viewModel() //
    val report: ReportViewModel = viewModel() //
    val location: InfluxLocationModel=viewModel()

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        // ✅ Splash first
        composable(Screen.Splash.route) {
            SplashScreen(
                navController = navController,
                onSplashFinished = {
                    navController.navigate("decider") {
                        popUpTo("Splash") { inclusive = true } // remove splash from back stack
                    }
                }
            )
        }

        // ✅ Decider checks Firebase
        composable("decider") {
            val auth = FirebaseAuth.getInstance()
            val user = auth.currentUser

            LaunchedEffect(user) {
                if (user != null) {
                    navController.navigate(Screen.Landing.route) {
                        popUpTo("decider") { inclusive = true }
                    }
                } else {
                    navController.navigate(Screen.Onboarding.route) {
                        popUpTo("decider") { inclusive = true }
                    }
                }
            }
        }


        composable(Screen.Onboarding.route) {
            OnBoardingScreen(navController = navController)
        }
        composable(Screen.SignIn.route) {
            SignInScreen(navController = navController)
        }
        composable(Screen.Landing.route) {
            LandingPage(navController = navController, viewModel = influx)
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
            Profile(navController = navController, viewModel = avatarViewModel)
        }
        composable(Screen.Reports.route) {
            Reports(navController = navController, viewModel = report)
        }
        composable(
            Screen.VerifyOtp.route,
            arguments = listOf(navArgument("otp") { type = NavType.StringType })
        ) {backStackEntry ->
            val otp = backStackEntry.arguments?.getString("otp") ?: ""
            VerifyOtp(navController = navController, generatedOtp = otp)
        }

        composable(Screen.EditProfile.route) {
            val uid = FirebaseAuth.getInstance().currentUser?.uid
            val db = FirebaseFirestore.getInstance()

            if (uid != null) {
                EditProfile(
                    navController = navController,
                    uid = uid,
                    db = db
                )
            } else {
                // Handle the case where user is not logged in
                Text("No user logged in")
            }
        }


        composable(Screen.Help.route) {
            Help(navController = navController)
        }

        composable(Screen.ChangeAvatar.route) {
            ChangeAvatar(navController = navController, viewModel = avatarViewModel )
        }
        composable(Screen.Map.route) {
            Map(navController = navController, viewModel = location)
        }
    }
}
