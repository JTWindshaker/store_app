package co.edu.unab.damo.jatt.storeapp.home.ui

import co.edu.unab.damo.jatt.storeapp.core.ui.model.Product

//Flow 1:
sealed interface ProductListUIState {
    data object Loading : ProductListUIState
    data class Success(val productList: List<Product>) : ProductListUIState
    data class Error(val throwable: Throwable) : ProductListUIState
}