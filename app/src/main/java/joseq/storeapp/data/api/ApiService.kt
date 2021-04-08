package joseq.storeapp.data.api

import io.reactivex.Single
import joseq.storeapp.data.model.Product

interface ApiService {

    fun getProducts(): Single<List<Product>>

}