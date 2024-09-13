package co.edu.unab.damo.jatt.storeapp.core.ui.activity

import android.app.Activity
import android.os.Bundle
import co.edu.unab.damo.jatt.storeapp.core.ui.model.ProductDiscount
import co.edu.unab.damo.jatt.storeapp.R
import co.edu.unab.damo.jatt.storeapp.core.ui.model.User
import kotlin.random.Random

class KotlinActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin)

        //Variables, Constantes, Valores y Tipos de datos
        var name: String = "José"
        var age = 28
        var height = 1.65
        var document = 1098780154
        var float = 1.6f
        var admin = true

        println("Mi nombre es $name. Tengo $age años. Mido $height metros. Mi documento es $document. Soy Administrador: $admin. Mi variable flotante es $float.")
        name = "Alfredo"
        println("Mi nombre es $name. Tengo $age años. Mido $height metros. Mi documento es $document. Soy Administrador: $admin. Mi variable flotante es $float.")

        //Estructuras de Control If/Else
        val emailData: String = getString(R.string.email_data)
        var passwordData: String = getString(R.string.password_data)
        var emailInput: String = "leidy@correo.com"
        var passwordInput: String = "12345"

        var messageData: String =
            if (login(emailInput, passwordInput)) {
                "¡Login correcto!"
            } else if (emailInput != emailData) {
                "El usuario no es correcto."
            } else {
                "La contraseña no es correcta."
            }

        println(messageData)

        var typeUser: Int = 0

        messageData = when (typeUser) {
            1 -> {
                "Soy Administrador"
            }

            in 2..4 -> {
                "Soy Cliente"
            }

            else -> {
                "Soy Invitado"
            }
        }

        println(messageData)

        //Estructuras de Datos
        val productsList: ArrayList<String> = arrayListOf("Pantalla", "Teclado", "Mouse")

        println(productsList)
        productsList.add("CPU")
        println(productsList)

        val product = productsList[2]
        println(product)

        productsList[2] = "Disco Duro"
        println(productsList)

        productsList.remove("CPU")
        productsList.removeAt(0)
        println(productsList)

        productsList.first()
        productsList.last()
        productsList.size
        productsList.forEach {
            println(it)
        }

        //Map o Diccionario / Clave-Valor
        val monitor = mutableMapOf<String, Any>("id" to 1)
        monitor["name"] = "Sony"
        monitor["price"] = 258.80
        monitor["date"] = "23/11/1995"
        monitor["promo"] = true
        monitor["characteristic"] = mutableMapOf<String, Any>()

        val characteristics = monitor["characteristic"] as MutableMap<String, Any>

        characteristics["resolution"] = "1920x1080"
        characteristics["refreshRate"] = 60

        println(monitor)

        //Bucles
        for (myProduct in monitor) {
            print(myProduct.key)
            print(" -> ")
            println(myProduct.value)
        }

        for (i: Int in 0 downTo productsList.size step 1) {
            println(productsList[i])
        }

        var x: Int = Random.nextInt(0, 10)

        while (x < 3) {
            println(x)
            x = Random.nextInt(0, 10)
        }

        var y: Int = Random.nextInt(0, 10)
        do {
            println(y)
            y = Random.nextInt(0, 10)
        } while (y < 3)

        //Null Safety
        val testNull: String? = "Hola"

        var count2 = testNull?.length

        if (testNull != null) {
            var count: Int = testNull.length
        }

        testNull?.let {
            println(testNull.uppercase())
        } ?: run {
            println("Value is null")
        }

//        var keyboard = Product(
//            name = "Teclado",
//            price = 60000
//        )
//
//        var screen = Product(
//            name = "Pantalla Dell",
//            price = 50000,
//            description = "Pantalla Dell 32\" 4k"
//        )

        println("Lambda Example")

        var newScreen =
            ProductDiscount(
                id = 60,
                name = "Pantalla Led",
                price = 1000,
                discount = 30
            ) { message ->
                "Mensaje personalizado: $message"
            }

//        val printedMessage = newScreen.printMessage("Descuento aplicado")
//        println(printedMessage)

//        keyboard.price = 40000
//        println(keyboard)

//        var one = Product(
//            name = "CPU",
//            price = 1200000
//        )
//
//        var two = Product(
//            name = "CPU",
//            price = 1200000
//        )

//        var response: Boolean = (one == two)
//        println(response)

//        var oneU = User(
//            name = "Jose",
//            email = "jose@hotmail.com",
//            password = "xD"
//        )
//
//        var twoU = User(
//            name = "Jose",
//            email = "jose@hotmail.com",
//            password = "xD"
//        )

//        var responseU: Boolean = (oneU == twoU)
//        println(responseU)
//
//        val (a, b, c) = oneU
    }

    //Funciones
    private fun login(emailInput: String = "", passwordInput: String): Boolean {
        val emailData: String = getString(R.string.email_data)
        val passwordData: String = getString(R.string.password_data)

        return (emailInput == emailData && passwordInput == passwordData)
    }
}