package com.vajro.shoppingcart.ui.product_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.vajro.shoppingcart.repository.model.cart.Cart
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProductDetailsViewModel @Inject constructor(private val productDetailsRepository: ProductDetailsRepository) : ViewModel() {

    var cartLiveData: MutableLiveData<List<Cart>> = MutableLiveData()
    fun getCartList(): LiveData<List<Cart>> {
        viewModelScope.launch {
            val result = productDetailsRepository.getCartItemCall()
            cartLiveData.postValue(result)
        }
        return cartLiveData
    }

    fun addToCart(cart: Cart) {
        viewModelScope.launch {
            productDetailsRepository.add(cart)
        }
    }

    fun updateToCart(qty: Int,productId: String) {
        viewModelScope.launch {
            productDetailsRepository.updateByProductId(qty,productId)
        }
    }

    fun removeFromCart(productId: String) {
        viewModelScope.launch {
            productDetailsRepository.remove(productId)
        }
    }

}