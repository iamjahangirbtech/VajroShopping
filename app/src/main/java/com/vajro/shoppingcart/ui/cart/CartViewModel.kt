package com.vajro.shoppingcart.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vajro.shoppingcart.repository.model.cart.Cart
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(private val cartRepository: CartRepository) : ViewModel() {

    var cartLiveData: MutableLiveData<List<Cart>> = MutableLiveData()

    fun getCartList(): LiveData<List<Cart>> {
        viewModelScope.launch {
            val result = cartRepository.getCartItemCall()
            cartLiveData!!.postValue(result)
        }
        return cartLiveData
    }

    fun updateToCart(qty: Int,productId: String) {
        viewModelScope.launch {
            cartRepository.updateByProductId(qty,productId)
        }
    }

    fun removeFromCart(productId: String) {
        viewModelScope.launch {
            cartRepository.remove(productId)
        }
    }

}