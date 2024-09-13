package co.edu.unab.damo.jatt.storeapp.home.data.repository

import co.edu.unab.damo.jatt.storeapp.core.data.local.dao.ProductDAO
import co.edu.unab.damo.jatt.storeapp.core.data.local.entity.ProductEntity
import co.edu.unab.damo.jatt.storeapp.core.data.network.datasource.ProductAPIDataSource
import co.edu.unab.damo.jatt.storeapp.core.data.network.datasource.ProductFirestoreDataSource
import co.edu.unab.damo.jatt.storeapp.core.ui.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val productDAO: ProductDAO,
    private val dataSource: ProductFirestoreDataSource,
    private val productAPIDataSource: ProductAPIDataSource
) {
    val productList: Flow<List<Product>> =
        productDAO.getAll().map { items -> items.map { it.toProduct() } }

    suspend fun addProduct(product: Product) {
        productDAO.add(product.toProductEntity())
    }

    suspend fun removeProduct(product: Product) {
        productDAO.delete(product.toProductEntityId())
    }

    fun productListFirebase(): Flow<List<Product>> {
        return dataSource.getAll()
    }

    fun addProductFirebase(product: Product) {
        dataSource.add(product)
    }

    fun removeProductFirebase(product: Product) {
        dataSource.remove(product)
    }

    fun productListAPI(): Flow<List<Product>> {
        return productAPIDataSource.getAll()
    }

    suspend fun addProductAPI(product: Product) {
        productAPIDataSource.add(product);
    }
}

fun ProductEntity.toProduct(): Product = Product(
    id = this.id,
    name = this.name,
    price = this.price,
    description = this.description,
    image = this.image
)


fun Product.toProductEntity(): ProductEntity = ProductEntity(
    name = this.name,
    price = this.price,
    description = this.description,
    image = this.image
)

fun Product.toProductEntityId(): ProductEntity = ProductEntity(
    id = this.id,
    name = this.name,
    price = this.price,
    description = this.description,
    image = this.image
)