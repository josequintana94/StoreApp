package joseq.storeapp

import android.app.Application
import android.content.Context
import io.paperdb.Paper

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        Paper.init(this)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }

    companion object {
        lateinit var instance: App
    }
}