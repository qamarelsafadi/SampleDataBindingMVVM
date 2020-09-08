package net.qamar.sampledatabindingmvvm.util

import android.app.Application
import android.content.Context
import io.paperdb.Paper

class MyApplication: Application() {
    companion object {
        lateinit var context: Context

    }
    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        Paper.init(context)

    }
}