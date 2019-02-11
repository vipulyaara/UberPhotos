package com.uber.data

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.PriorityBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

/**
 * @author Vipul Kumar; dated 30/01/19.
 *
 * Global executor pools for the whole application.
 *
 * Grouping tasks like this avoids the effects of task starvation.
 */
object AppExecutors {
    @Volatile
    private var bestThreadCount: Int = 0
    // Don't use more than four threads when automatically determining thread count..
    private const val MAXIMUM_AUTOMATIC_THREAD_COUNT = 4

    val networkIO: Executor = Executors.newFixedThreadPool(3)

    /** creates an executor for downloading images based on cpu core count */
    val imagesIO: ExecutorService = Executors.newFixedThreadPool(calculateBestThreadCount())

    var mainThread: Executor = MainThreadExecutor()

    class MainThreadExecutor : Executor {
        private val mainThreadHandler = Handler(Looper.getMainLooper())
        override fun execute(command: Runnable) {
            mainThreadHandler.post(command)
        }
    }

    private fun calculateBestThreadCount(): Int {
        if (bestThreadCount == 0) {
            bestThreadCount =
                    Math.min(
                        MAXIMUM_AUTOMATIC_THREAD_COUNT,
                        Runtime.getRuntime().availableProcessors()
                    )
        }
        return bestThreadCount
    }
}
