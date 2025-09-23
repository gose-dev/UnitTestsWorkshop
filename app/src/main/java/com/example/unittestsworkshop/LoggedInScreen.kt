package com.example.unittestsworkshop

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.unittestsworkshop.ui.theme.UnitTestsWorkshopTheme

@Composable
fun LoggedInScreen(
    username: String,
    isLoading: Boolean,
    onLogout: () -> Unit,
    onNavigateToUsers: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Welcome, $username!",
            modifier = Modifier.padding(bottom = 32.dp)
        )
        
        Button(
            onClick = { onNavigateToUsers(username) },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .padding(bottom = 16.dp)
        ) {
            Text("View Users")
        }
        
        Button(
            onClick = onLogout,
            enabled = !isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(32.dp),
                    strokeWidth = 4.dp,
                    color = androidx.compose.ui.graphics.Color.White
                )
            } else {
                Text("Logout")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoggedInScreenPreview() {
    UnitTestsWorkshopTheme {
        LoggedInScreen(
            username = "JohnDoe",
            isLoading = false,
            onLogout = {},
            onNavigateToUsers = {}
        )
    }
}