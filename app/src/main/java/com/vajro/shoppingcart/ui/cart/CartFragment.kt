package com.vajro.shoppingcart.ui.cart

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.google.gson.Gson
import com.vajro.shoppingcart.base.BaseFragment
import com.vajro.shoppingcart.databinding.FragmentCartBinding
import com.vajro.shoppingcart.repository.model.cart.Cart
import com.vajro.shoppingcart.utils.extensions.gone
import com.vajro.shoppingcart.utils.extensions.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : BaseFragment<FragmentCartBinding>() {

    private val productListViewModel: CartViewModel by activityViewModels()
    private lateinit var cartAdapter: CartAdapter
    private var cart = ArrayList<Cart>()
    var totalPrice : Double = 0.0
    /**
     * Create Binding
     */
    override fun onCreateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentCartBinding = FragmentCartBinding.inflate(inflater, container, false)


    companion object {

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onClick()
        load()
        observeProduct()
    }


    private fun load() {

        cartAdapter = CartAdapter(cart)
        binding?.rvCart?.adapter = cartAdapter

        cartAdapter.onDetailClicked = { products,pos ->

        }

        cartAdapter.onAddClicked = { products,pos,count ->
            println("count = ${count}")
            when(count){
                0->{
                    productListViewModel.removeFromCart(products.productId.toString())
                }
                else->{
                    productListViewModel.updateToCart(count,products.productId.toString())
                }
            }
            observeProduct()
        }

    }

    fun onClick(){
        binding!!.ivBack.setOnClickListener {
            back()
        }

        binding!!.ivHome.setOnClickListener {
            backAll()
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    private fun observeProduct() {

        productListViewModel.getCartList()!!.observe(activity!!, Observer { serviceSetterGetter ->
           // wp7progressBar.hideProgressBar()
            println("productListViewModel = ${Gson().toJson(serviceSetterGetter)}")
            cart.clear()
            cart.addAll(serviceSetterGetter)
            cartAdapter.notifyDataSetChanged()

            if(cart.size >0){
                binding?.tvHeader?.text = "My Cart ("+cart.size.toString()+")"
                calculateTotalPrice()
                binding?.emptyView?.root?.gone()
                binding?.rlBottom?.visible()
            }else{
                binding?.tvHeader?.text = "My Cart"
                binding?.emptyView?.root?.visible()
                binding?.rlBottom?.gone()
            }

        })

    }


    fun calculateTotalPrice(){
        totalPrice = 0.0
        cart.forEach {
            totalPrice = totalPrice + (it.price!!.drop(1).replace(",","").toDouble() * it.qty!!)
        }
        binding?.tvTotalPrice?.text = "₹"+ totalPrice.toString()
    }

}
