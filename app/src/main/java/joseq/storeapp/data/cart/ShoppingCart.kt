package joseq.storeapp.data.cart

import io.paperdb.Paper

class ShoppingCart {

    companion object {

        fun addItem(cartItem: CartItem) {
            val cart = getCart()
            val targetItem = cart.singleOrNull { it.product.id == cartItem.product.id }

            if (targetItem == null) {
                cart.add(cartItem)
            } else {
                targetItem.quantity = targetItem.quantity + cartItem.quantity
            }

            saveCart(cart)
        }

        fun removeItem(cartItem: CartItem) {
            val cart = getCart()
            val targetItem = cart.singleOrNull { it.product.id == cartItem.product.id }

            if (targetItem != null) {
                if (targetItem.quantity > 1) {
                    targetItem.quantity--
                } else {
                    cart.remove(targetItem)
                }
            }
            saveCart(cart)
        }

        fun clearItem(cartItem: CartItem) {
            val cart = getCart()
            val targetItem = cart.singleOrNull { it.product.id == cartItem.product.id }

            if (targetItem != null) {
                cart.remove(targetItem)
            }
            saveCart(cart)
        }

        fun saveCart(cart: MutableList<CartItem>) {
            Paper.book().write("cart", cart)
        }

        fun getCart(): MutableList<CartItem> {
            return Paper.book().read("cart", mutableListOf())
        }

        fun productExists(id: Int): Boolean {
            val cart = getCart()
            val targetItem = cart.singleOrNull { it.product.id == id }

            if (targetItem != null) {
                return true
            } else {
                return false
            }
        }

        fun getProduct(id: Int): CartItem? {
            val cart = getCart()
            val targetItem = cart.singleOrNull { it.product.id == id }

            return targetItem
        }

        fun getShoppingCartSize(): Int {
            var cartSize = 0
            getCart().forEach {
                cartSize += it.quantity
            }
            return cartSize
        }

        fun cleanCart() {
            Paper.book().delete("cart")
        }
    }

}