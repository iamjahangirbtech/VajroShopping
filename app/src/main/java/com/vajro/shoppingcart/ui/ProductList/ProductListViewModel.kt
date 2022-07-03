package com.vajro.shoppingcart.ui.ProductList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vajro.shoppingcart.repository.model.MainModel
import com.vajro.shoppingcart.repository.model.cart.Cart
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProductListViewModel @Inject constructor(private val productsListRepository: ProductsListRepository) : ViewModel() {

    var servicesLiveData: MutableLiveData<MainModel>? = null

    fun getProductsList() : LiveData<MainModel>? {
        servicesLiveData = productsListRepository.getProductListApiCall()
        return servicesLiveData
    }

    var cartLiveData: MutableLiveData<List<Cart>> = MutableLiveData()
    fun getCartList(): LiveData<List<Cart>> {
        viewModelScope.launch {
            val result = productsListRepository.getCartItemCall()
            cartLiveData.postValue(result)
        }
        return cartLiveData
    }

    fun addToCart(cart: Cart) {
        viewModelScope.launch {
            productsListRepository.add(cart)
        }
    }

    fun updateToCart(qty: Int,productId: String) {
        viewModelScope.launch {
            productsListRepository.updateByProductId(qty,productId)
        }
    }

    fun removeFromCart(productId: String) {
        viewModelScope.launch {
            productsListRepository.remove(productId)
        }
    }


}