package com.vajro.shoppingcart.ui.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vajro.shoppingcart.databinding.ListItemCartListBinding
import com.vajro.shoppingcart.repository.model.cart.Cart
import com.vajro.shoppingcart.utils.extensions.gone
import com.vajro.shoppingcart.utils.load

class CartAdapter(private val cartList: List<Cart>) :
    RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    var onDetailClicked: ((Cart,Int) -> Unit)? = null
    var onAddClicked: ((Cart, Int, Int) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding =
            ListItemCartListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val country = cartList[position]
        holder.bindView(country)
    }


    override fun getItemCount(): Int = cartList.size


    inner class ViewHolder(private val itemBinding: ListItemCartListBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        init {
            itemBinding.root.setOnClickListener {
                onDetailClicked?.invoke(cartList[adapterPosition],adapterPosition)
            }

            itemBinding.ivAdd.setOnClickListener {
                if(Integer.parseInt(itemBinding.quantyTxt.text.toString()) == 0){

                }else{
                    var count:Int = Integer.parseInt(itemBinding.quantyTxt.text.toString()) + 1
                    itemBinding.quantyTxt.text = count.toString()
                }
                onAddClicked?.invoke(cartList[adapterPosition],adapterPosition,
                    Integer.parseInt(itemBinding.quantyTxt.text.toString()))
            }
            itemBinding.ivMinus.setOnClickListener {
                var count:Int = Integer.parseInt(itemBinding.quantyTxt.text.toString()) - 1
                if(count == 0){

                }else{
                    itemBinding.quantyTxt.text = count.toString()
                }
                onAddClicked?.invoke(cartList[adapterPosition],adapterPosition,
                    count)
            }
        }


        fun bindView(products: Cart) {
            itemBinding.tvProductName.text = products.name
            itemBinding.tvProductPrice.text = products.price
            itemBinding.quantyTxt.text = products.qty.toString()
            itemBinding.tvProductTotalPrice.text = "₹"+(products.price!!.drop(1).replace(",","").toDouble() * products.qty!!).toString()
            itemBinding.ivImage.load(itemBinding.root.context, products.image ?: "")

            if(adapterPosition == cartList.size-1)
                itemBinding.ivPlus.gone()
        }

    }



}
