import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simpleregister.Model.UserProfile
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class UserProfileViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()

    private val _userProfile = MutableStateFlow<UserProfile?>(null)
    val userProfile: StateFlow<UserProfile?> = _userProfile

    init {
        fetchUserProfile()
    }

    private fun fetchUserProfile() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {
            viewModelScope.launch {
                try {
                    val docSnapshot = db.collection("users").document(userId).get().await()
                    val userProfile = docSnapshot.toObject(UserProfile::class.java)
                    _userProfile.value = userProfile
                } catch (e: Exception) {
                    // Handle exceptions
                }
            }
        }
    }

    fun updateUserProfile(updatedProfile: UserProfile) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {
            viewModelScope.launch {
                try {
                    db.collection("users").document(userId).set(updatedProfile).await()
                    _userProfile.value = updatedProfile // Update the local UI state
                } catch (e: Exception) {
                    // Handle exceptions, e.g., show an error message
                }
            }
        }
    }

    fun observeUserChanges() {
        FirebaseAuth.getInstance().addAuthStateListener {
            fetchUserProfile()
        }
    }

    fun clearUserProfile() {
        _userProfile.value = null
    }





}
