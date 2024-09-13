package co.edu.unab.damo.jatt.storeapp.productdetail.domain

import androidx.lifecycle.LiveData
import co.edu.unab.damo.jatt.storeapp.core.ui.model.Product
import co.edu.unab.damo.jatt.storeapp.productdetail.data.repository.ProductDetailRepository
import javax.inject.Inject

class GetProductUseCase @Inject constructor(private val productDetailRepository: ProductDetailRepository) {
    operator fun invoke(id: Int): LiveData<Product> = productDetailRepository.getProductById(id)
}