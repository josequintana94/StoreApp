package joseq.storeapp.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import joseq.storeapp.utils.Resource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import joseq.storeapp.data.model.Product
import joseq.storeapp.data.repository.MainRepository

class MainViewModel(private val mainRepository: MainRepository) : ViewModel() {

    private val products = MutableLiveData<Resource<List<Product>>>()
    private val compositeDisposable = CompositeDisposable()

    init {
        fetchProducts()
    }

    private fun fetchProducts() {
        products.postValue(Resource.loading(null))
        compositeDisposable.add(
            mainRepository.getProducts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ productList ->
                    products.postValue(Resource.success(productList))
                }, { throwable ->
                    products.postValue(Resource.error("Error de conexion, por favor intentalo nuevamente", null))
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    fun getProducts(): LiveData<Resource<List<Product>>> {
        return products
    }

}