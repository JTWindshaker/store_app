package co.edu.unab.damo.jatt.storeapp.core.data.di

import android.content.Context
import androidx.room.Room
import co.edu.unab.damo.jatt.storeapp.core.data.local.StoreAppDatabase
import co.edu.unab.damo.jatt.storeapp.core.data.local.dao.ProductDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideStoreAppDatabase(@ApplicationContext context: Context): StoreAppDatabase {
        return Room.databaseBuilder(context, StoreAppDatabase::class.java, "storeapp.db").build()
    }

    @Provides
    @Singleton
    fun provideProductDAO(storeAppDatabase: StoreAppDatabase): ProductDAO {
        return storeAppDatabase.productDao()
    }
}