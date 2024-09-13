package co.edu.unab.damo.jatt.storeapp.productupdate.ui.viewmodel

//Home no debería estar acá... Esto solo debería tener dependencias de productAdd y core. Si se usa en más de 2 módulos, sacarlo en core o duplicar
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.edu.unab.damo.jatt.storeapp.core.ui.model.Product
import co.edu.unab.damo.jatt.storeapp.productdetail.domain.GetProductUseCase
import co.edu.unab.damo.jatt.storeapp.productupdate.domain.UpdateProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

// Si en vez de heredar de viewModel, se hereda de otro viewmodel más grande, se pueden usar los metodos genericos que ellas tengan, perfecto para viewmodels de la carpeta core
@HiltViewModel
class UpdateProductViewModel @Inject constructor(
    private val updateProductUseCase: UpdateProductUseCase,
    private val getProductUseCase: GetProductUseCase
) :
    ViewModel() {

    fun updateProduct(id: Int, name: String, price: Int, description: String, image: String) {
        viewModelScope.launch(Dispatchers.IO) {
            updateProductUseCase.invoke(
                Product(
                    id = id,
                    name = name,
                    price = price,
                    description = description,
                    image = image
                )
            )
        }
    }

    fun getProductById(id: Int): LiveData<Product> = getProductUseCase.invoke(id)
}