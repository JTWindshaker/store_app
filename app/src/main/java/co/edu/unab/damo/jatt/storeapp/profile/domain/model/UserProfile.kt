package co.edu.unab.damo.jatt.storeapp.profile.domain.model

data class UserProfile(
    val uid: String,
    val name: String,
    val email: String,
    val document: String,
    val image: String,
)
