package co.edu.unab.damo.jatt.storeapp.home.domain

import co.edu.unab.damo.jatt.storeapp.core.ui.model.Product
import co.edu.unab.damo.jatt.storeapp.home.data.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteProductUseCase @Inject constructor(private val homeRepository: HomeRepository) {
    suspend operator fun invoke(product: Product) {
        return homeRepository.removeProduct(product)
//        return homeRepository.removeProductFirebase(product)
    }
}