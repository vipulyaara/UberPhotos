package com.uber.user.config.initializers

import android.app.Application

/**
 * Utility for configuration initialization.
 * */
interface AppInitializer {
    fun init(application: Application)
}
