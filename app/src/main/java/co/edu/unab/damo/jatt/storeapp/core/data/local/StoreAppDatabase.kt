package co.edu.unab.damo.jatt.storeapp.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import co.edu.unab.damo.jatt.storeapp.core.data.local.dao.ProductDAO
import co.edu.unab.damo.jatt.storeapp.core.data.local.entity.ProductEntity

@Database(entities = [ProductEntity::class], version = 1, exportSchema = false)
abstract class StoreAppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDAO
}