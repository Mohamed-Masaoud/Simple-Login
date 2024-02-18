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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = viewModel(),
    navigateToRegistration: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var loginMessage by remember { mutableStateOf<String?>(null) }

    // Error state holders for input validation
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }

    Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                emailError = null // Reset error state on user input
                loginMessage = null // Reset login message on user input
            },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            isError = emailError != null,
            singleLine = true
        )
        if (emailError != null) {
            Text(emailError!!, color = MaterialTheme.colorScheme.error)
        }

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                passwordError = null // Reset error state on user input
                loginMessage = null // Reset login message on user input
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

        Button(
            onClick = {
                // Clear previous login messages
                loginMessage = null
                // Perform input validation before attempting to login
                if (validateLoginInput(email, setEmailError = { emailError = it }, password, setPasswordError = { passwordError = it })) {
                    loginViewModel.login(
                        email = email,
                        password = password,
                        onSuccess = { loginMessage = "Login Successful" },
                        onError = { error -> loginMessage = error }
                    )
                }
            },
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
        ) {
            Text("Login")
        }

        // Display loginMessage if not null
        loginMessage?.let {
            Text(it, color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(top = 8.dp))
        }

        Spacer(modifier = Modifier.height(8.dp)) // Add a little space before the registration prompt

        TextButton(onClick = navigateToRegistration) {
            Text("Don't have an account? Sign up")
        }
    }
}

fun validateLoginInput(
    email: String, setEmailError: (String?) -> Unit,
    password: String, setPasswordError: (String?) -> Unit
): Boolean {
    var isValid = true

    if (email.isBlank()) {
        setEmailError("Email cannot be empty")
        isValid = false
    } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
        setEmailError("Enter a valid email")
        isValid = false
    }

    if (password.isBlank()) {
        setPasswordError("Password cannot be empty")
        isValid = false
    }

    return isValid
}

