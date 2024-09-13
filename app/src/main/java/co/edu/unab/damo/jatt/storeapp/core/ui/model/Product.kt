package co.edu.unab.damo.jatt.storeapp.core.ui.model

open class Product(
    var id: Int,
    var name: String,
    var price: Int,
    var description: String = "Sin Descripción",
    var image: String = "https://cdn-icons-png.freepik.com/256/12313/12313717.png?semt=ais_hybrid"
) {
    init {
        println("Creating Product...")
    }

    override fun toString(): String {
        return "Name: $name. Price: $price. Description: $description"
    }
}