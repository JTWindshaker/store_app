package co.edu.unab.damo.jatt.storeapp.home.domain

import co.edu.unab.damo.jatt.storeapp.core.ui.model.Product
import co.edu.unab.damo.jatt.storeapp.home.data.repository.HomeRepository
import javax.inject.Inject

class AddProductUseCase @Inject constructor(private val homeRepository: HomeRepository) {
    suspend operator fun invoke(product: Product) {
        return homeRepository.addProduct(product)
//        return homeRepository.addProductFirebase(product)
//        return homeRepository.addProductAPI(product)
    }
}