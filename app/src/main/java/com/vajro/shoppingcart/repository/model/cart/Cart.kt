package com.vajro.shoppingcart.repository.model.cart

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * Cart Model an entity that represents a table and holds schema of Cart
 *
 * By default, Room uses the class name as the database table name. If you want the table to have a different name, set the tableName
 * property of the @Entity annotation, as shown in the following code snippet:
 * @Entity(tableName = "cart")
 */
@Entity(tableName = "cart_table")
data class Cart(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @SerializedName("productId") var productId: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("qty") var qty: Int? = null,
    @SerializedName("price") var price: String? = null,
    @SerializedName("image") var image: String? = null,

    )
