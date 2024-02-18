import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth

@Composable
fun MainScreen() {
    val user = FirebaseAuth.getInstance().currentUser
    val displayName = user?.displayName ?: user?.email ?: "User" // Use email if displayName is null

    Column(
        modifier = Modifier.padding(PaddingValues(16.dp))
    ) {
        Text(
            text = "Hi $displayName!",
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Button(
            onClick = {
                FirebaseAuth.getInstance().signOut()
                // Navigation to the login screen is handled by the AuthStateListener in MainActivity
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Logout")
        }
    }
}
