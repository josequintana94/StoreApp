package joseq.storeapp.data.api

class ApiHelper(private val apiService: ApiService) {

    fun getProducts() = apiService.getProducts()
}