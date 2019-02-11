package com.uber.user

import android.app.Application
import com.uber.data.AppExecutors
import com.uber.user.config.initializers.AppInitializers

/**
 * @author Vipul Kumar; dated 11/02/19.
 */
class UberApplication : Application() {
    private val appInitializers by lazy { AppInitializers() }

    override fun onCreate() {
        super.onCreate()
        // initialize all the configuration here
        appInitializers.init(this)
    }
}
