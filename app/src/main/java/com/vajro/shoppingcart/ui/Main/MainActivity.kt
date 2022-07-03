package com.vajro.shoppingcart.ui.Main

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.vajro.shoppingcart.base.BaseActivity
import com.vajro.shoppingcart.databinding.ActivityMainBinding
import com.vajro.shoppingcart.ui.ProductList.ProductListFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        changeFragment(binding.root.id, ProductListFragment(),null,false)
    }

    override fun createBinding(): ActivityMainBinding =
        ActivityMainBinding.inflate(layoutInflater)



}