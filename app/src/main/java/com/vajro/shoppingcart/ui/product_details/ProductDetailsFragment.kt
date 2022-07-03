package com.vajro.shoppingcart.ui.product_details

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.google.gson.Gson
import com.vajro.shoppingcart.base.BaseFragment
import com.vajro.shoppingcart.databinding.FragmentProductDetailBinding
import com.vajro.shoppingcart.repository.model.Products
import com.vajro.shoppingcart.repository.model.cart.Cart
import com.vajro.shoppingcart.ui.cart.CartFragment
import com.vajro.shoppingcart.utils.extensions.gone
import com.vajro.shoppingcart.utils.extensions.visible
import com.vajro.shoppingcart.utils.loadCenterInside
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList

@AndroidEntryPoint
class ProductDetailsFragment : BaseFragment<FragmentProductDetailBinding>() {

    private val productListViewModel: ProductDetailsViewModel by activityViewModels()

    private var productsModel = Products()
    private var cart = ArrayList<Cart>()
    /**
     * Create Binding
     */
    override fun onCreateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentProductDetailBinding = FragmentProductDetailBinding.inflate(inflater, container, false)


    companion object {
       // const val ARG_1 = "ARG_1"

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            productsModel = it.getParcelable<Products>("arg_1")!!
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onClick()
        load()
        observeProduct()
        getCartItems(0)


    }
    fun onClick(){
        binding?.ivBack?.setOnClickListener {
            back()
        }

        binding?.ivCart?.setOnClickListener {
            changeFragment(binding?.root!!.id, CartFragment(), null, true)
        }

        binding?.btnAdd?.setOnClickListener {
            binding?.quantyTxt?.text = "1"
            binding?.btnAdd?.gone()
            binding?.rlCountLayout?.visible()
            updateCart(productsModel,0,1,1)
        }
        binding?.ivAdd?.setOnClickListener {
            val count:Int = Integer.parseInt(binding?.quantyTxt?.text.toString()) + 1
            if(count == 0){
                //showCustomToast(binding?.root.context,"Count is Zero")
            }else{
                binding?.quantyTxt?.text = count.toString()
            }
            updateCart(productsModel,0,count,1)
        }
        binding?.ivMinus?.setOnClickListener {
            val count:Int = Integer.parseInt(binding?.quantyTxt?.text.toString()) - 1
            if(count == 0){
                binding?.rlCountLayout?.gone()
                binding?.btnAdd?.visible()
            }else{
                binding?.quantyTxt?.text = count.toString()
            }
            updateCart(productsModel,0,count,2)
        }
    }

    private fun load() {
        binding?.tvHeader?.text = productsModel.name
        binding?.tvProductName?.text = productsModel.name
        binding?.tvProductPrice?.text = productsModel.price
        binding?.tvDetails?.text = productsModel.description
        binding?.ivImage?.loadCenterInside(activity!!, productsModel.image ?: "")
    }


    fun updateCart(products:Products, pos:Int, count:Int, type:Int){

        when (count) {
            1 -> {
                when (type) {
                    1 -> {
                        var cartItem: Cart =
                            Cart(
                                //id = it.id,
                                name = products.name,
                                productId = products.productId.toString(),
                                qty = count,
                                image = products.image,
                                price = products.price,
                            )
                        productListViewModel.addToCart(cartItem)
                    }
                    2 -> {
                        productListViewModel.updateToCart(count, products.productId.toString())
                    }
                }
            }
            0 -> {
                productListViewModel.removeFromCart(products.productId.toString())
            }
            else -> {
                productListViewModel.updateToCart(count, products.productId.toString())
            }
        }

        Handler(Looper.getMainLooper()).postDelayed({
            getCartItems(1)
        }, 1000)
    }
    fun getCartItems(type: Int) {
        productListViewModel.getCartList().observe(activity!!, Observer { cartLiveData ->
            cart.clear()
            cart.addAll(cartLiveData)
            if (cart.size > 0) {
                binding?.cartCount?.text = cart.size.toString()
            } else {
                binding?.cartCount?.text = ""
            }

            when(type){
                0->{
                    var cartIsAvailable = cart.filter { it.productId.equals(productsModel.productId) }
                    println("Gson().toJson(cartIsAvailable) = ${Gson().toJson(cartIsAvailable)}")
                    if(cartIsAvailable.size>0){
                        binding?.quantyTxt?.text = cartIsAvailable[0].qty.toString()
                        binding?.btnAdd?.gone()
                        binding?.rlCountLayout?.visible()
                    }
                }
            }


        })
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun observeProduct() {

    }

}
