package com.example.unittestsworkshop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.unittestsworkshop.ui.theme.UnitTestsWorkshopTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UnitTestsWorkshopTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    var currentUserId by remember { mutableStateOf("") }
    
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "login",
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            composable("login") {
                LoginScreen(
                    onLoginSuccess = { uid ->
                        currentUserId = uid
                        navController.navigate("userList")
                    }
                )
            }
            composable("userList") {
                UserListScreen(
                    currentUserId = currentUserId,
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}