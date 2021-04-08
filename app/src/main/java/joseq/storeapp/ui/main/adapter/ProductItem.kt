package joseq.storeapp.ui.main.adapter

import com.bumptech.glide.Glide
import joseq.storeapp.R
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import joseq.storeapp.data.cart.CartItem
import joseq.storeapp.data.cart.ShoppingCart
import joseq.storeapp.data.model.Product
import kotlinx.android.synthetic.main.item_layout.*

class ProductItem(val product: Product) : Item() {
    private var currentQuantity: Int? = 0

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.apply {
            textViewProductName.text = product.title
            textViewProductPrice.text = product.price.toString() + "$"
            Glide.with(imageViewProduct)
                .load(product.image)
                .into(imageViewProduct)

            if (ShoppingCart.productExists(product.id)) {
                val cartProduct = ShoppingCart.getProduct(product.id)
                currentQuantity = cartProduct?.quantity
            } else {
                currentQuantity = 0
            }

            quantity.text = currentQuantity.toString()

            increment.setOnClickListener {
                currentQuantity = currentQuantity?.plus(1)
                quantity.text = currentQuantity.toString()
                val cartItem = CartItem(product, 1)
                ShoppingCart.addItem(cartItem)
            }

            decrement.setOnClickListener {
                if (currentQuantity!! > 0) {
                    currentQuantity = currentQuantity?.minus(1)
                    quantity.text = currentQuantity.toString()

                    if (ShoppingCart.productExists(product.id)) {
                        val cartProduct = ShoppingCart.getProduct(product.id)
                        cartProduct?.let { it1 -> ShoppingCart.removeItem(it1) }
                    }
                }
            }
        }
    }

    override fun getLayout(): Int = R.layout.item_layout

    override fun getSpanSize(spanCount: Int, position: Int): Int = spanCount / 3

}