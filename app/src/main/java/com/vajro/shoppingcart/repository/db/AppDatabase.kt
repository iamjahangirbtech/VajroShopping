package com.vajro.shoppingcart.repository.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vajro.shoppingcart.repository.db.cart.CartDao
import com.vajro.shoppingcart.repository.model.cart.Cart


/**
 * App Database
 * Define all entities and access doa's here/ Each entity is a table.
 */
@Database(entities =[Cart::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun cartDao(): CartDao
}