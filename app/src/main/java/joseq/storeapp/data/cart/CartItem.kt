package joseq.storeapp.data.cart

import joseq.storeapp.data.model.Product

data class CartItem(var product: Product, var quantity: Int)