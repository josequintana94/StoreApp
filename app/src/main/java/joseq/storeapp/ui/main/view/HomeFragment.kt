package joseq.storeapp.ui.main.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.xwray.groupie.ExpandableGroup
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import joseq.storeapp.R
import joseq.storeapp.data.model.Product
import joseq.storeapp.ui.main.adapter.ExpandableItem
import joseq.storeapp.ui.main.adapter.ProductItem
import joseq.storeapp.ui.main.viewmodel.MainViewModel
import joseq.storeapp.utils.Status
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()

    private lateinit var searchView: SearchView
    var groupAdapter = GroupAdapter<GroupieViewHolder>()

    var list: MutableList<Product>? = mutableListOf()
    var searchList: MutableList<Product>? = mutableListOf()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchView = view.findViewById(R.id.searchView)!!
        setupAdapter()
        setupObserver()
    }

    private fun setupSearchView() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(text: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(text: String?): Boolean {
                searchList?.clear()

                for (product in list!!) {
                    if (product.title.contains(text!!, true)) {
                        searchList?.add(product)
                    }
                }

                groupAdapter.clear()
                var groupieList = getProductsList(searchList!!)
                setupGroupieList(groupieList)

                return false
            }
        })

    }

    private fun setupAdapter() {
        groupAdapter.apply {
            spanCount = 3
        }

        recyclerView.apply {
            layoutManager = GridLayoutManager(context, groupAdapter.spanCount).apply {
                spanSizeLookup = groupAdapter.spanSizeLookup
            }
            adapter = groupAdapter
        }
    }

    private fun setupObserver() {
        viewModel.getProducts().observe(viewLifecycleOwner, Observer {

            when (it.status) {
                Status.SUCCESS -> {
                    progressBar.visibility = View.GONE

                    list = it.data?.toMutableList()
                    var groupieList = getProductsList(list!!)
                    setupGroupieList(groupieList)

                    setupSearchView()
                    recyclerView.visibility = View.VISIBLE
                }
                Status.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                }
                Status.ERROR -> {
                    progressBar.visibility = View.GONE
                    Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun getProductsList(list: MutableList<Product>): MutableList<ProductItem> {
        var lista: MutableList<ProductItem> = mutableListOf()

        for (data in list) {
            lista.add(ProductItem(data))
        }

        return lista
    }

    private fun setupGroupieList(groupieList: MutableList<ProductItem>) {
        var tempList: MutableList<ProductItem> = mutableListOf()
        groupieList.forEachIndexed { index, product ->

            if (index == 0) {
                tempList.add(groupieList[index])
                if (groupieList.lastIndex == 0) {
                    ExpandableGroup(ExpandableItem(groupieList[index].product.category.toUpperCase()), true).apply {
                        add(Section(tempList))
                        groupAdapter.add(this)
                    }
                }

            } else if (index > 0 && groupieList[index - 1].product.category.equals(groupieList[index].product.category)) {

                tempList.add(groupieList[index])

                if (groupieList.lastIndex == index) {
                    ExpandableGroup(ExpandableItem(groupieList[index].product.category.toUpperCase()), true).apply {
                        add(Section(tempList))
                        groupAdapter.add(this)
                    }
                }

            } else {
                ExpandableGroup(
                    ExpandableItem(groupieList[index - 1].product.category.toUpperCase()),
                    true
                ).apply {
                    add(Section(tempList))
                    groupAdapter.add(this)
                }
                tempList.clear()
                tempList.add(groupieList[index])

            }
        }
    }
}