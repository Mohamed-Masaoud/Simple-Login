import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.CoroutineExceptionHandler

class RegistrationViewModel : ViewModel() {

    private val firebaseAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    fun register(email: String, password: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        val handler = CoroutineExceptionHandler { _, exception ->
            onError(exception.localizedMessage ?: "An error occurred")
        }
        viewModelScope.launch(handler) {
            firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            onSuccess()
        }
    }
}

