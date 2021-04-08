package joseq.storeapp.ui.main.adapter

import com.bumptech.glide.Glide
import joseq.storeapp.R
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import joseq.storeapp.data.model.Product
import kotlinx.android.synthetic.main.item_layout.*

class ProductItem(val product: Product) : Item(){

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.apply {
            textViewProductName.text = product.title
            textViewProductPrice.text = product.price.toString() + "$"
            Glide.with(imageViewProduct)
                    .load(product.image)
                    .into(imageViewProduct)
        }
    }

    override fun getLayout(): Int  = R.layout.item_layout

    override fun getSpanSize(spanCount: Int, position: Int): Int  = spanCount / 3

}