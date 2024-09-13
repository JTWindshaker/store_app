package co.edu.unab.damo.jatt.storeapp.productadd.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.edu.unab.damo.jatt.storeapp.core.ui.model.Product

//Home no debería estar acá... Esto solo debería tener dependencias de productAdd y core. Si se usa en más de 2 módulos, sacarlo en core o duplicar
import co.edu.unab.damo.jatt.storeapp.home.domain.AddProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddProductViewModel @Inject constructor(private val addProductUseCase: AddProductUseCase) :
    ViewModel() {

    fun addProduct(name: String, price: Int, description: String, image: String) {
        viewModelScope.launch(Dispatchers.IO) {
            addProductUseCase.invoke(
                Product(
                    id = 0,
                    name = name,
                    price = price,
                    description = description,
                    image = image
                )
            )
        }
    }
}