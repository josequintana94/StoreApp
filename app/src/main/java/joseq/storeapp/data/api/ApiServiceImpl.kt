package joseq.storeapp.data.api

import com.rx2androidnetworking.Rx2AndroidNetworking
import io.reactivex.Single
import joseq.storeapp.data.model.Product

class ApiServiceImpl : ApiService {

    override fun getProducts(): Single<List<Product>> {
        return Rx2AndroidNetworking.get("https://fakestoreapi.com/products")
            .build()
            .getObjectListSingle(Product::class.java)
    }

}