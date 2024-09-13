package co.edu.unab.damo.jatt.storeapp.users.domain.model

data class User(
    val uid: String,
    val name: String,
    val email: String,
    val document: String,
    val image: String
)
