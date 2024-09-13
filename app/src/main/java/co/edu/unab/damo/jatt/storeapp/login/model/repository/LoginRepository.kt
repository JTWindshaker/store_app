package co.edu.unab.damo.jatt.storeapp.login.model.repository

import co.edu.unab.damo.jatt.storeapp.core.data.network.FirebaseClient
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class LoginRepository @Inject constructor(private val client: FirebaseClient) {
    suspend fun login(email: String, password: String): AuthResult? {
        return kotlin.runCatching {
            return client.auth.signInWithEmailAndPassword(email, password).await()
        }.getOrNull()
    }
}