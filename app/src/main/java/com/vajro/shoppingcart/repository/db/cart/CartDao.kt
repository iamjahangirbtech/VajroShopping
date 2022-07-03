package com.vajro.shoppingcart.repository.db.cart

import androidx.room.*
import com.vajro.shoppingcart.repository.model.cart.Cart

@Dao
abstract class CartDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(item: Cart)

    @Query("DELETE FROM cart_table WHERE productId = :productId")
    abstract fun deleteByProductId(productId: String)

    @Query("UPDATE cart_table SET qty = :qty WHERE productId = :productId")
    abstract fun updateByProductId(qty: Int,productId: String)

    @Update
    abstract fun update(item: Cart)

    @Delete
    abstract fun delete(item: Cart)

    // all the data of database.
    @Query("SELECT * FROM cart_table")
    abstract fun getAllCartItems(): List<Cart>
}