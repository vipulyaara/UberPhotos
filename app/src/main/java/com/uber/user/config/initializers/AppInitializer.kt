package com.uber.user.config.initializers

import android.app.Application

interface AppInitializer {
    fun init(application: Application)
}
