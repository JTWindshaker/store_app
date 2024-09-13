package co.edu.unab.damo.jatt.storeapp.productupdate.data.repository

import co.edu.unab.damo.jatt.storeapp.core.data.local.dao.ProductDAO
import co.edu.unab.damo.jatt.storeapp.core.data.network.FirebaseClient
import co.edu.unab.damo.jatt.storeapp.core.data.network.datasource.ProductFirestoreDataSource
import co.edu.unab.damo.jatt.storeapp.core.ui.model.Product
import co.edu.unab.damo.jatt.storeapp.home.data.repository.toProductEntity
import co.edu.unab.damo.jatt.storeapp.home.data.repository.toProductEntityId
import javax.inject.Inject

class UpdateProductRepository @Inject constructor(
    private val productDAO: ProductDAO,
    private val dataSource: ProductFirestoreDataSource
) {

    suspend fun updateProduct(product: Product) {
//        productDAO.update(product.toProductEntity().copy(id = product.id));
        productDAO.update(product.toProductEntityId())
    }

    fun updateProductFirebase(product: Product) {
        dataSource.update(product)
    }
}