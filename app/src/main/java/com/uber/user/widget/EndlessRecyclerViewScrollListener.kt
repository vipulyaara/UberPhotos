package com.uber.user.widget

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

/**
 * Implementation of [RecyclerView.OnScrollListener] for pagination.
 *
 * This code is copied and modified from a previous personal project;
 * along the same lines of the class from Chris Banes' app "tivi".
 *
 * https://github.com/chrisbanes/tivi
 *
 * */
class EndlessRecyclerViewScrollListener(
    private val layoutManager: RecyclerView.LayoutManager,
    private val loadMore: (totalItemsCount: Int, view: RecyclerView) -> Unit
) : RecyclerView.OnScrollListener() {

    private var loadMoreThreshold = 4
    private var previousItemCount = 0
    private var loading = true

    init {
        // Convert the threshold so that is for rows, not items
        when (layoutManager) {
            is GridLayoutManager -> {
                loadMoreThreshold *= layoutManager.spanCount
            }
        }
    }

    private fun getLastVisibleItem(lastVisibleItemPositions: IntArray): Int {
        var maxSize = 0
        for (i in lastVisibleItemPositions.indices) {
            if (i == 0) {
                maxSize = lastVisibleItemPositions[i]
            } else if (lastVisibleItemPositions[i] > maxSize) {
                maxSize = lastVisibleItemPositions[i]
            }
        }
        return maxSize
    }

    override fun onScrolled(view: RecyclerView, dx: Int, dy: Int) {
        var lastVisibleItemPosition = 0
        val itemCount = layoutManager.itemCount

        when (layoutManager) {
            is StaggeredGridLayoutManager -> {
                val lastVisibleItemPositions = layoutManager.findLastVisibleItemPositions(null)
                lastVisibleItemPosition = getLastVisibleItem(lastVisibleItemPositions)
            }
            is GridLayoutManager -> lastVisibleItemPosition =
                    layoutManager.findLastVisibleItemPosition()
            is LinearLayoutManager -> lastVisibleItemPosition =
                    layoutManager.findLastVisibleItemPosition()
        }

        if (itemCount < previousItemCount) {
            previousItemCount = itemCount
            if (itemCount == 0) {
                loading = true
            }
        }

        if (loading && itemCount > previousItemCount) {
            loading = false
            previousItemCount = itemCount
        }

        if (!loading && lastVisibleItemPosition + loadMoreThreshold > itemCount) {
            loadMore(itemCount, view)
            loading = true
        }
    }
}
