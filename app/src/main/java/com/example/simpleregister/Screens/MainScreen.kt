import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.simpleregister.Model.UserProfile
import com.google.firebase.auth.FirebaseAuth

@Composable
fun MainScreen(userProfileViewModel: UserProfileViewModel = viewModel()) {
    LaunchedEffect(Unit) {
        userProfileViewModel.observeUserChanges()
    }

    val userProfile by userProfileViewModel.userProfile.collectAsState()

    // Initial states
    var name by remember { mutableStateOf("") }
    var bio by remember { mutableStateOf("") }
    var skillsInput by remember { mutableStateOf("") }

    // Update local states when userProfile changes
    LaunchedEffect(userProfile) {
        name = userProfile?.name ?: ""
        bio = userProfile?.bio ?: ""
        skillsInput = userProfile?.skills?.joinToString(", ") ?: ""
    }

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") }
        )
        TextField(
            value = bio,
            onValueChange = { bio = it },
            label = { Text("Bio") }
        )
        TextField(
            value = skillsInput,
            onValueChange = { skillsInput = it },
            label = { Text("Skills (comma-separated)") }
        )
        Button(
            onClick = {
                val updatedSkills = skillsInput.split(",").map { it.trim() }.filter { it.isNotEmpty() }
                val updatedProfile = UserProfile(name, bio, updatedSkills)
                userProfileViewModel.updateUserProfile(updatedProfile)
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Save Changes")
        }

        Button(
            onClick = {
                userProfileViewModel.clearUserProfile() // Clear user profile data
                FirebaseAuth.getInstance().signOut() // Sign out the user

            },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Logout")
        }
    }
}



