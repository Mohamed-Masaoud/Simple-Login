import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simpleregister.Model.UserProfile
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.CoroutineExceptionHandler

class RegistrationViewModel : ViewModel() {

    private val firebaseAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val db: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }

    fun register(email: String, password: String, username: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val authResult = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
                val userId = authResult.user?.uid ?: throw Exception("User ID not found")

                // Create a user profile with the username
                val userProfile = UserProfile(name = username, bio = "", skills = listOf())
                db.collection("users").document(userId).set(userProfile).await()

                onSuccess()
            } catch (e: Exception) {
                onError(e.localizedMessage ?: "Registration failed. Please try again.")
            }
        }
    }

}

