package joseq.storeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import joseq.storeapp.data.api.ApiHelper
import joseq.storeapp.data.api.ApiServiceImpl
import joseq.storeapp.ui.base.ViewModelFactory
import joseq.storeapp.ui.main.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    var currentTab: String? = null
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViewModel()

        NavigationUI.setupActionBarWithNavController(this, findNavController(R.id.main_nav_fragment))

        val navController: NavController = Navigation.findNavController(this, R.id.main_nav_fragment)
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            currentTab = (destination.label).toString()
        }

    }

    private fun setupViewModel() {
        mainViewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(ApiHelper(ApiServiceImpl()))
        ).get(MainViewModel::class.java)
    }
}