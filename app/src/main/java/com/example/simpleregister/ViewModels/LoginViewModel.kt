import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.CoroutineExceptionHandler

class LoginViewModel : ViewModel() {

    private val firebaseAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    fun login(email: String, password: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                firebaseAuth.signInWithEmailAndPassword(email, password).await()
                onSuccess()
            } catch (e: FirebaseAuthInvalidCredentialsException) {
                // This exception can indicate a malformed email or wrong password, but we keep the message generic.
                onError("Email or password is incorrect. Please try again.")
            } catch (e: FirebaseAuthInvalidUserException) {
                // This can indicate that the user does not exist or is disabled, among other things.
                onError("Email or password is incorrect. Please try again.")
            } catch (e: Exception) {
                // Handle other unexpected errors.
                onError(e.localizedMessage ?: "An error occurred. Please try again.")
            }
        }
    }

}

