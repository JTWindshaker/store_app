package co.edu.unab.damo.jatt.storeapp.core.ui.model

data class User(
    var id: String = "",
    var name: String,
    var document: String = "",
    var email: String,
    var image: String = ""
)