package joseq.storeapp.data.repository

import io.reactivex.Single
import joseq.storeapp.data.api.ApiHelper
import joseq.storeapp.data.model.Product

class MainRepository(private val apiHelper: ApiHelper) {

    fun getProducts(): Single<List<Product>> {
        return apiHelper.getProducts()
    }

}