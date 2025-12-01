package com.example.smartband.navigation

 sealed class Screen(val route: String) {
     data object Splash : Screen("SplashScreen")
     data object Onboarding : Screen("screens/OnBoardingScreen")
     data object SignIn : Screen("screens/auth/SignIn")
     data object SignUp : Screen("screens/auth/SignUp")
     data object Landing : Screen("screens/LandingPage")
     data object ChildInfo : Screen("screens/auth/ChildInfo")
     data object Profile : Screen("screens/navbar/Profile")
     data object Schedule : Screen("screens/navbar/Schedule")
     data object VerifyOtp : Screen("screens/auth/VerifyOtp/{otp}")
     data object EditProfile : Screen("screens/navbar/settings/EditProfile")
     data object Reports : Screen("screens/navbar/Reports")
     data object Help : Screen("screens/navbar/settings/Help")
     data object Notification : Screen("screens/navbar/settings/Notifications")

     data object Pattern : Screen("screens/Pattern")

     data object Map : Screen("screens/navbar/Map")
     data object ChangeAvatar : Screen("screens/navbar/settings/ChangeAvatar")

 }
