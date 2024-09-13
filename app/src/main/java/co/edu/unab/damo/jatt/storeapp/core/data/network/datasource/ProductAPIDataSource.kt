package co.edu.unab.damo.jatt.storeapp.core.data.network.datasource

import co.edu.unab.damo.jatt.storeapp.core.data.network.service.ProductService
import co.edu.unab.damo.jatt.storeapp.core.ui.model.Product
import co.edu.unab.damo.jatt.storeapp.home.data.repository.toProduct
import co.edu.unab.damo.jatt.storeapp.home.data.repository.toProductEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.await
import javax.inject.Inject

class ProductAPIDataSource @Inject constructor(private val productService: ProductService) {
    fun getAll(): Flow<List<Product>> {
        return flow {
            try {
                val productList: MutableList<Product> = arrayListOf()
                val data = productService.getAll().await()
                data.forEach { (_, productEntity) -> productList.add(productEntity.toProduct()) }
                emit(productList)
            } catch (e: Exception) {
            }
        }
    }

    suspend fun add(product: Product) {
        val addProduct = product.toProductEntity()
        productService.add(addProduct.id, addProduct).await()
    }
}