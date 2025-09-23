package com.example.unittestsworkshop

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.unittestsworkshop.data.models.User
import com.example.unittestsworkshop.ui.theme.UnitTestsWorkshopTheme

@Composable
fun UserListScreen(
    modifier: Modifier = Modifier,
    viewModel: UsersViewModel = viewModel(),
    currentUserId: String = "",
    onBack: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    
    LaunchedEffect(Unit) {
        viewModel.loadUsers()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = onBack
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back"
                )
            }
            
            Text(
                text = "Users",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            
            IconButton(
                onClick = { viewModel.createUser() }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add User"
                )
            }
        }
        
        when (uiState) {
            is UsersViewModel.UiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            
            is UsersViewModel.UiState.Success -> {
                val users = (uiState as UsersViewModel.UiState.Success).users
                val filteredUsers = if (currentUserId.isNotEmpty()) {
                    users.filter { it.id != currentUserId }
                } else {
                    users
                }
                UserList(
                    users = filteredUsers,
                    onDeleteUser = { id -> viewModel.deleteUserById(id) },
                    modifier = Modifier.weight(1f)
                )
            }
            
            is UsersViewModel.UiState.Error -> {
                val message = (uiState as UsersViewModel.UiState.Error).message
                ErrorMessage(
                    message = message,
                    onRetry = { viewModel.loadUsers() }
                )
            }
            
            is UsersViewModel.UiState.UserOperationSuccess -> {
                // Just reload the user list
                viewModel.loadUsers()
            }
            
            is UsersViewModel.UiState.UserDeleted -> {
                // Just reload the user list
                viewModel.loadUsers()
            }
        }
    }
}

@Composable
fun UserList(
    users: List<User>,
    onDeleteUser: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(users) { user ->
            UserItem(
                user = user,
                onDeleteUser = onDeleteUser
            )
            HorizontalDivider()
        }
    }
}

@Composable
fun UserItem(
    user: User,
    onDeleteUser: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = user.username,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = user.email,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            if (user.friends.isNotEmpty()) {
                Text(
                    text = "${user.friends.size} friends",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}

@Composable
fun ErrorMessage(
    message: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.error,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Button(onClick = onRetry) {
            Text("Retry")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserListScreenPreview() {
    UnitTestsWorkshopTheme {
        UserListScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun UserItemPreview() {
    UnitTestsWorkshopTheme {
        UserItem(
            user = User(
                id = "1",
                username = "John Doe",
                email = "john.doe@example.com",
                friends = listOf("Jane", "Bob")
            ),
            onDeleteUser = {}
        )
    }
}