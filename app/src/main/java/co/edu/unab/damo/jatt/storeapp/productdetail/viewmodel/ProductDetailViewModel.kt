package co.edu.unab.damo.jatt.storeapp.productdetail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import co.edu.unab.damo.jatt.storeapp.core.ui.model.Product
import co.edu.unab.damo.jatt.storeapp.productdetail.domain.GetProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(private val getProductUseCase: GetProductUseCase) : ViewModel() {

    fun getProductById(id: Int):LiveData<Product> = getProductUseCase.invoke(id)
}