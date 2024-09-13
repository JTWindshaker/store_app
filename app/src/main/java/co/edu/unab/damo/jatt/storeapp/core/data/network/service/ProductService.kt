package co.edu.unab.damo.jatt.storeapp.core.data.network.service

import co.edu.unab.damo.jatt.storeapp.core.data.local.entity.ProductEntity
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.PUT
import retrofit2.http.Path

interface ProductService {
    @GET("products.json")
    fun getAll():Call<Map<String, ProductEntity>>

    @GET("products/{id}.json")
    fun getById(@Path("id") id:Int):Call<ProductEntity>

    @PUT("products/{id}.json")
    fun add(@Path("id") id:Int, @Body productEntity: ProductEntity):Call<Any>

    @PATCH("products/{id}.json")
    fun update(@Path("id") id:Int, @Body productEntity: ProductEntity):Call<Any>

    @DELETE("products/{id}.json")
    fun remove(@Path("id") id:Int):Call<Unit>

}