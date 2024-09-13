package co.edu.unab.damo.jatt.storeapp.users.model.repository

import co.edu.unab.damo.jatt.storeapp.users.domain.model.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepository @Inject constructor(private val firestore: FirebaseFirestore) {

    suspend fun getUsers(): List<User> {
        return try {
            val snapshot = firestore.collection("users").get().await()
            snapshot.documents.mapNotNull { document ->
                val name = document.getString("name") ?: ""
                val email = document.getString("email") ?: ""
                val documentId = document.getString("document") ?: ""
                val image = document.getString("image") ?: ""
                val uid = document.id

                User(uid, name, email, documentId, image)
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
}
