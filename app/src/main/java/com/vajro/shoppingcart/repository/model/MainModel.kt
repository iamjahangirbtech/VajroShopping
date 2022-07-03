package com.vajro.shoppingcart.repository.model

import com.google.gson.annotations.SerializedName


data class MainModel(
    @SerializedName("products" ) var products : ArrayList<Products> = arrayListOf()
)