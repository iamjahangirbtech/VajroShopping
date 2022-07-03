package com.vajro.shoppingcart.ui.ProductList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vajro.shoppingcart.databinding.ListItemProductListBinding
import com.vajro.shoppingcart.repository.model.Products
import com.vajro.shoppingcart.utils.extensions.gone
import com.vajro.shoppingcart.utils.extensions.visible
import com.vajro.shoppingcart.utils.load

class ProductsListAdapter(private var productList: List<Products>) :
    RecyclerView.Adapter<ProductsListAdapter.ViewHolder>() {

    var onDetailClicked: ((Products,Int) -> Unit)? = null
    var onAddClicked: ((Products,Int,Int,Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding =
            ListItemProductListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val country = productList[position]
        holder.bindView(country)
    }

    override fun getItemCount(): Int = productList.size

    inner class ViewHolder(private val itemBinding: ListItemProductListBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        init {
            itemBinding.root.setOnClickListener {
                onDetailClicked?.invoke(productList[adapterPosition],adapterPosition)
            }
            itemBinding.btnAdd.setOnClickListener {
                itemBinding.quantyTxt.text = "1"
                itemBinding.btnAdd.gone()
                itemBinding.rlCountLayout.visible()
                onAddClicked?.invoke(productList[adapterPosition],adapterPosition,1,1)
            }
            itemBinding.ivAdd.setOnClickListener {
                val count:Int = Integer.parseInt(itemBinding.quantyTxt.text.toString()) + 1
                if(count == 0){
                    //showCustomToast(itemBinding.root.context,"Count is Zero")
                }else{
                    itemBinding.quantyTxt.text = count.toString()
                }
                onAddClicked?.invoke(productList[adapterPosition],adapterPosition,
                    Integer.parseInt(itemBinding.quantyTxt.text.toString()),1)
            }
            itemBinding.ivMinus.setOnClickListener {
                val count:Int = Integer.parseInt(itemBinding.quantyTxt.text.toString()) - 1
                if(count == 0){
                    itemBinding.rlCountLayout.gone()
                    itemBinding.btnAdd.visible()
                }else{
                    itemBinding.quantyTxt.text = count.toString()
                }
                onAddClicked?.invoke(productList[adapterPosition],adapterPosition,
                    count,2)
            }
        }

        fun bindView(products: Products) {
            itemBinding.tvProductName.text = products.name
            itemBinding.tvProductPrice.text = products.price
            itemBinding.ivImage.load(itemBinding.root.context, products.image ?: "")
        }
    }

    fun updateList(productList: List<Products>) {
        this.productList = productList
        notifyDataSetChanged()
    }
}
