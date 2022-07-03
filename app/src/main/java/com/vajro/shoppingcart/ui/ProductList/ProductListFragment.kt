package com.vajro.shoppingcart.ui.ProductList

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.google.gson.Gson
import com.vajro.shoppingcart.base.BaseFragment
import com.vajro.shoppingcart.databinding.FragmentProductListBinding
import com.vajro.shoppingcart.repository.model.MainModel
import com.vajro.shoppingcart.repository.model.Products
import com.vajro.shoppingcart.repository.model.cart.Cart
import com.vajro.shoppingcart.ui.cart.CartFragment
import com.vajro.shoppingcart.ui.product_details.ProductDetailsFragment
import com.vajro.shoppingcart.utils.extensions.gone
import com.vajro.shoppingcart.utils.extensions.visible
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class ProductListFragment : BaseFragment<FragmentProductListBinding>() {

    private val productListViewModel: ProductListViewModel by activityViewModels()
    private lateinit var productsListAdapter: ProductsListAdapter
    private var productsModel = ArrayList<Products>()
    private var cart = ArrayList<Cart>()

    /**
     * Create Binding
     */
    override fun onCreateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentProductListBinding = FragmentProductListBinding.inflate(inflater, container, false)

    companion object {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        load()
        observeProduct()
        getCartItems()
    }


    private fun load() {
        binding?.emptyView?.emptyTitle?.text = "No Products Found"
        productsListAdapter = ProductsListAdapter(productsModel)
        binding?.rvProducts?.adapter = productsListAdapter

        productsListAdapter.onDetailClicked = { products, pos ->
            var arguments: Bundle
            arguments = Bundle().apply { putParcelable("arg_1", products) }
            changeFragment(binding!!.root.id, ProductDetailsFragment(), arguments, true)
        }

        binding!!.ivCart.setOnClickListener {
            changeFragment(binding!!.root.id, CartFragment(), null, true)
        }

        productsListAdapter.onAddClicked = { products, pos, count, type ->
            println("count = ${count}")
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
                getCartItems()
            }, 1000)
        }

        binding?.etSearch?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (s != null && s.length > 0) {
                    filter(binding?.etSearch?.text.toString().lowercase(Locale.getDefault()))
                } else {
                    productsListAdapter.updateList(productsModel)
                }
            }

        })

    }


    private fun observeProduct() {

        productListViewModel.getProductsList()!!.observe(activity!!, Observer { productResponse ->
            binding?.progressView?.root?.gone()
            println("productListViewModel = ${Gson().toJson(productResponse.products)}")
            productsModel.clear()
            productsModel.addAll(productResponse.products)
            productsListAdapter.notifyDataSetChanged()

        })


    }

    fun getCartItems() {
        productListViewModel.getCartList().observe(activity!!, Observer { cartLiveData ->
            cart.clear()
            cart.addAll(cartLiveData)
            if (cart.size > 0) {
                binding!!.cartCount.text = cart.size.toString()
            } else {
                binding!!.cartCount.text = ""
            }

        })
    }

    fun filter(text: String) {
        val temp: MutableList<Products> = ArrayList<Products>()
        for (d in productsModel) {
            if (d.name!!.lowercase(Locale.getDefault()).contains(text)) {
                temp.add(d)
            }
        }
        if (temp.size > 0) {
            binding?.emptyView?.root?.gone()
        } else {
            binding?.emptyView?.root?.visible()
        }

        productsListAdapter.updateList(temp)
    }

}
