package co.edu.unab.damo.jatt.storeapp.home.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.edu.unab.damo.jatt.storeapp.core.ui.model.Product
import co.edu.unab.damo.jatt.storeapp.home.domain.AddProductUseCase
import co.edu.unab.damo.jatt.storeapp.home.domain.DeleteProductUseCase
import co.edu.unab.damo.jatt.storeapp.home.domain.GetProductListUseCase
import co.edu.unab.damo.jatt.storeapp.home.ui.ProductListUIState
import co.edu.unab.damo.jatt.storeapp.home.ui.ProductListUIState.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getProductListUseCase: GetProductListUseCase,
    private val addProductUseCase: AddProductUseCase,
    private val deleteProductUseCase: DeleteProductUseCase
) : ViewModel() {

    //    val productList: LiveData<List<Product>> = getProductListUseCase()

    //Flow 2:
    val uiState: StateFlow<ProductListUIState> = getProductListUseCase().map(
        ::Success
    ).catch { error ->
        ProductListUIState.Error(error)
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),//Estará vivo el estado con un tiempo finito de 5000 hasta que salga de la vista principal
        ProductListUIState.Loading
    )

    fun removeProduct(product: Product) {
        viewModelScope.launch {
            deleteProductUseCase(product)
        }
    }
}