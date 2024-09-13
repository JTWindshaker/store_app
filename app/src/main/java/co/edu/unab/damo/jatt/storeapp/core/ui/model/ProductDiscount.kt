package co.edu.unab.damo.jatt.storeapp.core.ui.model

class ProductDiscount(
    id: Int,
    name: String,
    price: Int,
    var discount: Int = 0,
    private val printMsg: (String) -> String
) :
    Product(id, name, price) {

    fun printMessage(message: String): String {
        return printMsg(message)
    }
}