package com.uber.user.config.initializers

import android.app.Application
import com.uber.data.AppExecutors

/**
 * Initializes [AppExecutors].
 * */
class AppExecutorInitializer : AppInitializer {
    override fun init(application: Application) {
        AppExecutors.initialize()
    }
}
