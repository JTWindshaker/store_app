package co.edu.unab.damo.jatt.storeapp.core.data.network.datasource

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import co.edu.unab.damo.jatt.storeapp.core.data.local.entity.ProductEntity
import co.edu.unab.damo.jatt.storeapp.core.data.network.FirebaseClient
import co.edu.unab.damo.jatt.storeapp.core.ui.model.Product
import co.edu.unab.damo.jatt.storeapp.home.data.repository.toProduct
import co.edu.unab.damo.jatt.storeapp.home.data.repository.toProductEntity
import co.edu.unab.damo.jatt.storeapp.home.data.repository.toProductEntityId
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

private const val PRODUCTS = "products"

class ProductFirestoreDataSource @Inject constructor(private val firebaseClient: FirebaseClient) {

    fun getAll(): Flow<List<Product>> {
        return callbackFlow {
            val query = firebaseClient.firestoreDB.collection(PRODUCTS)
            val subscription = query.addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                if (snapshot != null) {
                    val documents =
                        snapshot.documents.map { document -> document.toObject(ProductEntity::class.java) }
                    trySend(documents.map { productEntity -> productEntity!!.toProduct() })
                }
            }
            awaitClose {
                subscription.remove()
            }
        }
    }

    fun getById(id: Int): LiveData<Product> {
        val product = MutableLiveData<Product>()
        firebaseClient.firestoreDB.collection(PRODUCTS).document(id.toString())
            .addSnapshotListener { snapshow, _ ->
                val productEntity = snapshow?.toObject(ProductEntity::class.java)
                productEntity?.id = id
                product.postValue(productEntity?.toProduct())
            }
        return product
    }

    fun add(product: Product) {
        val productEntity = product.toProductEntity()
        firebaseClient.firestoreDB.collection(PRODUCTS).document(productEntity.id.toString())
            .set(productEntity)
    }

    fun update(product: Product) {
        val productEntity = product.toProductEntityId()
        firebaseClient.firestoreDB.collection("products").document(product.id.toString())
            .set(productEntity)
    }

    fun remove(product: Product) {
        firebaseClient.firestoreDB.collection(PRODUCTS).document(product.id.toString()).delete()
    }

}