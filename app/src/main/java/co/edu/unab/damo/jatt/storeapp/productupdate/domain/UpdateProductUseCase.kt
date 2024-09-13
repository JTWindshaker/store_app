package co.edu.unab.damo.jatt.storeapp.productupdate.domain

import co.edu.unab.damo.jatt.storeapp.core.ui.model.Product
import co.edu.unab.damo.jatt.storeapp.home.data.repository.HomeRepository
import co.edu.unab.damo.jatt.storeapp.productupdate.data.repository.UpdateProductRepository
import javax.inject.Inject

class UpdateProductUseCase @Inject constructor(private val updateProductRepository: UpdateProductRepository) {

    suspend operator fun invoke(product: Product) {
        updateProductRepository.updateProduct(product)
//        updateProductRepository.updateProductFirebase(product)
    }
}