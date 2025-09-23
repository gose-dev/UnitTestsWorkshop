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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.unittestsworkshop.ui.theme.UnitTestsWorkshopTheme

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = viewModel(),
    onLoginSuccess: (String) -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    
    if (uiState.isLoggedIn) {
        LoggedInScreen(
            username = uiState.username,
            isLoading = uiState.isLoading,
            onLogout = { viewModel.logout() },
            onNavigateToUsers = { onLoginSuccess(uiState.userId) }
        )
    } else {
        LoginContent(
            uiState = uiState,
            onUsernameChange = viewModel::onUsernameChange,
            onPasswordChange = viewModel::onPasswordChange,
            onLoginClick = {
                viewModel.login()
            }
        )
    }
}

@Composable
fun LoginContent(
    uiState: LoginUiState,
    onUsernameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit,
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
            text = "Login",
            modifier = Modifier.padding(bottom = 32.dp)
        )

        OutlinedTextField(
            value = uiState.username,
            onValueChange = onUsernameChange,
            label = { Text("Username") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            singleLine = true
        )

        OutlinedTextField(
            value = uiState.password,
            onValueChange = onPasswordChange,
            label = { Text("Password") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            singleLine = true
        )

        if (uiState.errorMessage.isNotEmpty()) {
            Text(
                text = uiState.errorMessage,
                modifier = Modifier.padding(bottom = 16.dp),
                color = androidx.compose.ui.graphics.Color.Red
            )
        }

        Button(
            onClick = onLoginClick,
            enabled = !uiState.isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(32.dp),
                    strokeWidth = 4.dp,
                    color = androidx.compose.ui.graphics.Color.White
                )
            } else {
                Text("Login")
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    UnitTestsWorkshopTheme {
        LoginScreen()
    }
}