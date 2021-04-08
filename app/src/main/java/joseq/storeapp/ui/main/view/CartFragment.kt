package joseq.storeapp.ui.main.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import joseq.storeapp.R
import joseq.storeapp.data.cart.CartItem
import joseq.storeapp.data.cart.ShoppingCart
import joseq.storeapp.data.cart.ShoppingCartAdapter
import java.math.RoundingMode
import java.text.DecimalFormat

class CartFragment : Fragment() {

    lateinit var adapter: ShoppingCartAdapter
    lateinit var continueLayout: RelativeLayout
    lateinit var vaciarCarritoTextView: TextView
    lateinit var cartRecyclerView: RecyclerView
    lateinit var totalQuantityTextView: TextView
    lateinit var totalPriceTextView: TextView
    lateinit var tuPedidoTitleTextView: TextView
    lateinit var continuarBTTextView: TextView

    var cartItemsList: MutableList<CartItem>? = mutableListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        continueLayout = view.findViewById(R.id.cart_continue_button)
        vaciarCarritoTextView = view.findViewById(R.id.vaciar_carrito_textview)
        cartRecyclerView = view.findViewById(R.id.shopping_cart_recyclerView)
        totalQuantityTextView = view.findViewById(R.id.cantidad_in_cart_textview)
        totalPriceTextView = view.findViewById(R.id.total_price)
        tuPedidoTitleTextView = view.findViewById(R.id.tu_pedido_carrito_label)
        continuarBTTextView = view.findViewById(R.id.label_continuar)

        vaciarCarritoTextView.setOnClickListener {
            ShoppingCart.cleanCart()
            Toast.makeText(context, "Eliminaste todos los productos del carrito correctamente", Toast.LENGTH_SHORT).show()
            activity?.findNavController(R.id.main_nav_fragment)?.popBackStack()
        }
        continueLayout.setOnClickListener {
            ShoppingCart.cleanCart()
            Toast.makeText(context, "Pedido enviado", Toast.LENGTH_SHORT).show()
            activity?.findNavController(R.id.main_nav_fragment)?.popBackStack()
        }

        cartItemsList?.clear()
        cartItemsList = ShoppingCart.getCart()

        if (cartItemsList != null) {
            adapter = context?.let { ShoppingCartAdapter(it, cartItemsList!!) }!!
        }

        adapter.notifyDataSetChanged()

        cartRecyclerView.adapter = adapter
        cartRecyclerView.layoutManager = LinearLayoutManager(context)


        var totalPrice = ShoppingCart.getCart()
            .fold(0.toDouble()) { acc, cartItem -> acc + cartItem.quantity.times(cartItem.product.price) }

        totalQuantityTextView.text = ShoppingCart.getShoppingCartSize().toString()
        totalPriceTextView.text = roundOffDecimal(totalPrice).toString() + "$"
    }

    fun roundOffDecimal(number: Double): Double? {
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.FLOOR
        return df.format(number).toDouble()
    }
}