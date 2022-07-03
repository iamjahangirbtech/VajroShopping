package com.vajro.shoppingcart.ui.cart

import android.content.Context
import com.vajro.shoppingcart.repository.api.ApiServices
import com.vajro.shoppingcart.repository.db.cart.CartDao
import com.vajro.shoppingcart.repository.model.cart.Cart
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CartRepository @Inject constructor(
    private val cartDao: CartDao,
    private val apiServices: ApiServices,
    @ApplicationContext val context: Context
) {


    suspend fun getCartItemCall(): List<Cart> {
        var cartLiveData: List<Cart> = ArrayList()
        withContext(Dispatchers.IO) {
            cartLiveData =   cartDao.getAllCartItems()
        }
       /* Executors.newSingleThreadExecutor().execute(Runnable {
           //replace with your code
        })*/
        return cartLiveData
    }

    suspend fun remove(productId: String) {
        withContext(Dispatchers.IO) {
            cartDao.deleteByProductId(productId)
        }
    }
    suspend fun updateByProductId(qty: Int,productId: String) {
        withContext(Dispatchers.IO) {
            cartDao.updateByProductId(qty,productId)
        }
    }
}