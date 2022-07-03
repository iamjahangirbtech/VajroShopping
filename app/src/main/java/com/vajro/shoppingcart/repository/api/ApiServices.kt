package com.vajro.shoppingcart.repository.api

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vajro.shoppingcart.repository.api.network.Resource
import com.vajro.shoppingcart.repository.model.MainModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap

/**
 * Api services to communicate with server
 */

interface ApiServices {

    @GET("5def7b172f000063008e0aa2")
    fun getProductList() : Call<MainModel>

}
