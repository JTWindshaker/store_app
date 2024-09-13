package co.edu.unab.damo.jatt.storeapp.profile.model.repository

import co.edu.unab.damo.jatt.storeapp.profile.domain.model.UserProfile
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ProfileRepository @Inject constructor(private val firestore: FirebaseFirestore) {

    suspend fun getUserProfile(uid: String): UserProfile? {
        return try {
            val user = firestore.collection("users").document(uid).get().await()

            val name = user.getString("name") ?: ""
            val email = user.getString("email") ?: ""
            val document = user.getString("document") ?: ""
            val image = user.getString("image") ?: ""

            UserProfile(uid, name, email, document, image)
        } catch (e: Exception) {
            null
        }
    }
}
