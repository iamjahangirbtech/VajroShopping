package com.vajro.shoppingcart.ui.ProductList

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.vajro.shoppingcart.repository.api.ApiServices
import com.vajro.shoppingcart.repository.db.cart.CartDao
import com.vajro.shoppingcart.repository.model.MainModel
import com.vajro.shoppingcart.repository.model.cart.Cart
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductsListRepository @Inject constructor(
    private val cartDao: CartDao,
    private val apiServices: ApiServices,
    @ApplicationContext val context: Context
) {

    val productResponse = MutableLiveData<MainModel>()
    fun getProductListApiCall(): MutableLiveData<MainModel> {

        val call =  apiServices.getProductList()
        call.enqueue(object: Callback<MainModel> {
            override fun onFailure(call: Call<MainModel>, t: Throwable) {
                Log.v("DEBUG : ", t.message.toString())
            }
            override fun onResponse(
                call: Call<MainModel>,
                response: Response<MainModel>
            ) {
                Log.v("DEBUG : ", response.body().toString())
                val data = response.body()
                productResponse.value = data
            }
        })

        return productResponse
    }

    suspend fun getCartItemCall(): List<Cart> {
        var cartLiveData: List<Cart> = ArrayList()
        withContext(Dispatchers.IO) {
            cartLiveData =  cartDao.getAllCartItems()
        }
        return cartLiveData
    }

    suspend fun add(cart: Cart){
        withContext(Dispatchers.IO) {
            cartDao.insert(cart)
        }
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