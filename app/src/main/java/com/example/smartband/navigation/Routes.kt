package com.example.smartband.navigation

 sealed class Screen(val route: String) {
     data object Onboarding : Screen("screens/OnBoardingScreen")
     data object SignIn : Screen("screens/auth/SignIn")
     data object SignUp : Screen("screens/auth/SignUp")
     data object Landing : Screen("screens/LandingPage")
     data object ChildInfo : Screen("screens/auth/ChildInfo")
     data object Analytics : Screen("screens/navbar/Analytics")
     data object Profile : Screen("screens/navbar/Profile")
     data object Schedule : Screen("screens/navbar/Schedule")




 }
