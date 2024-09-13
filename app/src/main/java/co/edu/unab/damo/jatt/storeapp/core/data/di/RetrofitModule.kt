package co.edu.unab.damo.jatt.storeapp.core.data.di

import co.edu.unab.damo.jatt.storeapp.core.data.network.service.ProductService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {

    @Provides
    @Singleton
    fun providesRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl("https://storeapp-37369-default-rtdb.firebaseio.com/")
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    @Provides
    @Singleton
    fun providesProductService(retrofit: Retrofit): ProductService {
        return retrofit.create(ProductService::class.java)
    }
}