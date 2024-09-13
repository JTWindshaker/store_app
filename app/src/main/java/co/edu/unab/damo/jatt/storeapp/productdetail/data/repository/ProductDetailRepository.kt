package co.edu.unab.damo.jatt.storeapp.productdetail.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import co.edu.unab.damo.jatt.storeapp.core.data.local.dao.ProductDAO
import co.edu.unab.damo.jatt.storeapp.core.data.local.entity.ProductEntity
import co.edu.unab.damo.jatt.storeapp.core.data.network.datasource.ProductFirestoreDataSource
import co.edu.unab.damo.jatt.storeapp.core.ui.model.Product
import javax.inject.Inject

class ProductDetailRepository @Inject constructor(
    private val productDAO: ProductDAO,
    private val dataSource: ProductFirestoreDataSource
) {

    fun getProductById(id: Int): LiveData<Product> {
        return productDAO.getById(id).map { product ->
            product.toProduct()
        }
    }

    fun getProductByIdFirebase(id: Int): LiveData<Product> {
        return dataSource.getById(id)
    }

    private fun ProductEntity.toProduct(): Product = Product(
        id = this.id,
        name = this.name,
        price = this.price,
        description = this.description,
        image = this.image
    )
}