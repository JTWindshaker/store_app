package co.edu.unab.damo.jatt.storeapp.core.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product")
data class ProductEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
//    val id: Int = System.currentTimeMillis().hashCode(),

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "price")
    val price: Int,

    @ColumnInfo(name = "description", defaultValue = "Sin Descripción")
    val description: String,

    @ColumnInfo(name = "image", defaultValue = "")
    val image: String,
){
    constructor() : this(0, "", 0, "", "")
}