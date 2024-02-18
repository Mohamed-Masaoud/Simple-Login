import android.util.Patterns
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun RegistrationScreen(
    registrationViewModel: RegistrationViewModel = viewModel(),
    navigateToLogin: () -> Unit
) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var registrationMessage by remember { mutableStateOf<String?>(null) }

    // Error state holders
    var usernameError by remember { mutableStateOf<String?>(null) }
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var confirmPasswordError by remember { mutableStateOf<String?>(null) }

    Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        OutlinedTextField(
            value = username,
            onValueChange = {
                username = it
                usernameError = null // Clear error when user is typing
            },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth(),
            isError = usernameError != null,
            singleLine = true
        )
        if (usernameError != null) {
            Text(usernameError!!, color = MaterialTheme.colorScheme.error)
        }

// Email field with validation error display
        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                emailError = null // Clear error when user is typing
            },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            isError = emailError != null,
            singleLine = true
        )
        if (emailError != null) {
            Text(emailError!!, color = MaterialTheme.colorScheme.error)
        }

// Password field with validation error display
        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                passwordError = null // Clear error when user is typing
            },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
            isError = passwordError != null,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            singleLine = true
        )
        if (passwordError != null) {
            Text(passwordError!!, color = MaterialTheme.colorScheme.error)
        }

// Confirm Password field with validation error display
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = {
                confirmPassword = it
                confirmPasswordError = null // Clear error when user is typing
            },
            label = { Text("Confirm Password") },
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
            isError = confirmPasswordError != null,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            singleLine = true
        )
        if (confirmPasswordError != null) {
            Text(confirmPasswordError!!, color = MaterialTheme.colorScheme.error)
        }



        Button(
            onClick = {
                val isInputValid = validateInput(
                    username = username, setUsernameError = { usernameError = it },
                    email = email, setEmailError = { emailError = it },
                    password = password, setPasswordError = { passwordError = it },
                    confirmPassword = confirmPassword, setConfirmPasswordError = { confirmPasswordError = it }
                )
                if (isInputValid) {
                    registrationViewModel.register(
                        email = email,
                        password = password,
                        onSuccess = { registrationMessage = "Registration Successful. Please login." },
                        onError = { error -> registrationMessage = error }
                    )
                }
            },
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
        ) {
            Text("Register")
        }

        TextButton(onClick = navigateToLogin) {
            Text("Have an account? Login")
        }

        registrationMessage?.let {
            Text(it, modifier = Modifier.padding(top = 8.dp))
        }
    }
}

fun validateInput(
    username: String, setUsernameError: (String?) -> Unit,
    email: String, setEmailError: (String?) -> Unit,
    password: String, setPasswordError: (String?) -> Unit,
    confirmPassword: String, setConfirmPasswordError: (String?) -> Unit
): Boolean {
    var isValid = true

    if (username.isBlank()) {
        setUsernameError("Username cannot be empty")
        isValid = false
    }
    if (email.isBlank() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
        setEmailError("Enter a valid email")
        isValid = false
    }
    if (password.length < 6) {
        setPasswordError("Password must be at least 6 characters")
        isValid = false
    }
    if (confirmPassword != password) {
        setConfirmPasswordError("Passwords do not match")
        isValid = false
    }

    return isValid
}

