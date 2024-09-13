package co.edu.unab.damo.jatt.storeapp.register.data.repository

import co.edu.unab.damo.jatt.storeapp.core.data.network.FirebaseClient
import co.edu.unab.damo.jatt.storeapp.core.data.network.entity.UserEntity
import co.edu.unab.damo.jatt.storeapp.core.ui.model.User
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RegisterRepository @Inject constructor(private val client: FirebaseClient) {

//    suspend fun createAccount(email: String, password: String): AuthResult? {
//        return client.auth.createUserWithEmailAndPassword(email, password).await()
//    }

//    suspend fun createUserFirestore(user: User): Boolean {
//        return kotlin.runCatching {
//            client.firestoreDB.collection("users").document(user.id).set(user.toEntity()).await()
//        }.isSuccess
//    }

    suspend fun createAccount(email: String, password: String): AuthResult? {
        return try {
            client.auth.createUserWithEmailAndPassword(email, password).await().also {
                println("Cuenta creada: ${it.user?.uid}")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun createUserFirestore(user: User): Boolean {
        return try {
            println("Guardando usuario en Firestore con ID: ${user.id}")
            client.firestoreDB.collection("users").document(user.id).set(user.toEntity()).await()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}

fun User.toEntity(): UserEntity = UserEntity(this.id, this.name, this.document, this.email, this.image)