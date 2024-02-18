package com.example.simpleregister
import LoginScreen
import RegistrationScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.simpleregister.Screens.AppWithCustomNavigationBar
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val auth = FirebaseAuth.getInstance()

        setContent {
            MaterialTheme {
                Surface {
                    // State to determine if the user is logged in
                    val isLoggedIn = remember { mutableStateOf(auth.currentUser != null) }

                    LaunchedEffect(key1 = Unit) {
                        // AuthStateListener to listen for sign in/out
                        val authStateListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
                            isLoggedIn.value = firebaseAuth.currentUser != null
                        }

                        // Adding the AuthStateListener to FirebaseAuth
                        auth.addAuthStateListener(authStateListener)

                        // Cleanup: Remove the AuthStateListener when the composable is disposed
                        /*onDispose {
                            auth.removeAuthStateListener(authStateListener)
                        }*/
                    }

                    if (isLoggedIn.value) {
                        AppWithCustomNavigationBar()
                    } else {
                        AuthNavigation() // A composable function to navigate between Login and Registration
                    }
                }
            }
        }
    }

    @Composable
    fun AuthNavigation() {
        var showLoginScreen by remember { mutableStateOf(true) }

        if (showLoginScreen) {
            LoginScreen(navigateToRegistration = { showLoginScreen = false })
        } else {
            RegistrationScreen(navigateToLogin = { showLoginScreen = true })
        }
    }

}



