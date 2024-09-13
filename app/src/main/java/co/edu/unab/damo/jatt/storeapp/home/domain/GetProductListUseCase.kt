package co.edu.unab.damo.jatt.storeapp.home.domain

import co.edu.unab.damo.jatt.storeapp.core.ui.model.Product
import co.edu.unab.damo.jatt.storeapp.home.data.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductListUseCase @Inject constructor(private val homeRepository: HomeRepository) {
    operator fun invoke(): Flow<List<Product>> {
//        return homeRepository.productList //Para traer información de Room

        //Para traer de Flow

//        val flow = flow<List<Product>> {
//            homeRepository.productListFirebase().addOnSuccessListener { data ->
//                if (data.documents.isNotEmpty()) {
//                    emit(data.documents.map { it.toObject(Product::class.java) })
//                }
//            }
//        }

//        return flow {
//            val productList: List<Product> = homeRepository.productListFirebase()?.documents?.map {
//                it.toObject(Product::class.java)!!
//            } ?: emptyList()
//            emit(productList)
//        }
        return homeRepository.productList
    }
}