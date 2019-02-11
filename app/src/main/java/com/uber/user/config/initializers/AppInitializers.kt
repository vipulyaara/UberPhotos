package com.uber.user.config.initializers

import android.app.Application

class AppInitializers {
    private val initializers: MutableSet<@JvmSuppressWildcards AppInitializer> = hashSetOf()

    init {
        initializers.add(AppExecutorInitializer())
    }

    fun init(application: Application) {
        initializers.forEach {
            it.init(application)
        }
    }
}
