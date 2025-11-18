package com.example.smartband

import android.os.Bundle
import android.provider.Settings.Global.getString
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.navigation.compose.rememberNavController
import com.example.smartband.navigation.SetupNavGraph
import com.google.firebase.FirebaseApp
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {

    //    installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        FirebaseApp.initializeApp(this) //firebase code step 2

        // Check FirebaseAuth for sign-in state
        val currentUser = FirebaseAuth.getInstance().currentUser
     //   val isSignedIn = currentUser != null
        setContent {
            val navController = rememberNavController()
            SetupNavGraph(navController = navController)
        }
    }
}


private fun getGoogleLoginAuth(context: Context): SignInClient {
    val signInRequest = BeginSignInRequest.builder()
        .setGoogleIdTokenRequestOptions(
            BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                .setSupported(true)
                .setServerClientId(context.getString(R.string.default_web_client_id)) // Your Web Client ID from Firebase Console
                .setFilterByAuthorizedAccounts(false) // Set true if you only want accounts already on device
                .build()
        )
        .setAutoSelectEnabled(true) // Optional: automatically select if only one account
        .build()

    return Identity.getSignInClient(context).also { client ->
        client.beginSignIn(signInRequest)
    }
}