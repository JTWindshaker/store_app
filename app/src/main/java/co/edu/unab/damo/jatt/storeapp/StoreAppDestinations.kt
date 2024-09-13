package co.edu.unab.damo.jatt.storeapp

sealed class StoreAppDestinations(var title: String, var route: String) {
    data object LoginDestination : StoreAppDestinations("Login", "login")
    data object RegisterDestination : StoreAppDestinations("Register", "register")
    data object HomeDestination : StoreAppDestinations("Home", "home")
//    data object ProfileDestination : StoreAppDestinations("Profile", "profile/{uid}")
    data object UserDestination : StoreAppDestinations("Users", "users")
    data object ProductAddDestination : StoreAppDestinations("New Product", "product-add")

    data object ProductDetailDestination :
        StoreAppDestinations("Product Details", "product-detail/{id}") {
        fun createRoute(id: Int) = "product-detail/$id"
    }

    data object ProfileDestination :
        StoreAppDestinations("Profile", "profile/{uid}") {
        fun createRoute(uid: String) = "profile/$uid"
    }

    data object ProductUpdateDestination :
        StoreAppDestinations("Product Update", "product-update/{id}") {
        fun createRoute(id: Int) = "product-update/$id"
    }

    fun mainScreens() =
        listOf(
            HomeDestination,
            ProfileDestination,
            UserDestination,
            ProductDetailDestination,
            ProductUpdateDestination,
            ProductAddDestination
        )
}