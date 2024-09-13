package co.edu.unab.damo.jatt.storeapp.core.data.network.entity

data class UserEntity(
    var id: String = "",
    var name: String,
    var document: String = "",
    var email: String,
    var image: String = ""
)