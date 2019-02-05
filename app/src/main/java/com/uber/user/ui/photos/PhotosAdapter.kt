package com.uber.user.ui.photos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uber.data.flickrImageUrl
import com.uber.data.model.Photo
import com.uber.user.R
import com.uber.user.extensions.loadImage
import com.uber.user.ui.common.BaseRecyclerAdapter

/**
 * @author Vipul Kumar; dated 04/02/19.
 *
 * Implementation of [RecyclerView.Adapter] to show photo items in a grid.
 */
const val numberOfColumns = 3

class PhotoAdapter : BaseRecyclerAdapter<Photo>() {

    private var networkState: NetworkState? = null

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.item_photo -> {
                val item = items!![position]
                (holder as PhotoAdapterHolder).apply {
                    ivImage?.setImageDrawable(null)
                    ivImage?.loadImage(item.flickrImageUrl(), position)
                }
            }
            else -> {
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            com.uber.user.R.layout.item_photo -> PhotoAdapterHolder.create(parent)
            com.uber.user.R.layout.item_progress_bar -> LoadingViewHolder.create(parent)
            else -> throw IllegalArgumentException("unknown view type $viewType")
        }
    }

    class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        companion object {
            fun create(parent: ViewGroup): LoadingViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(com.uber.user.R.layout.item_progress_bar, parent, false)
                return LoadingViewHolder(view)
            }
        }
    }

    class PhotoAdapterHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var ivImage: ImageView? = null

        init {
            ivImage = itemView.findViewById(R.id.ivImage)
        }

        companion object {
            fun create(parent: ViewGroup): PhotoAdapterHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(com.uber.user.R.layout.item_photo, parent, false)
                return PhotoAdapterHolder(view)
            }
        }
    }

    fun hasExtraRow() = networkState != null && networkState != NetworkState.LOADED

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            R.layout.item_progress_bar
        } else {
            return R.layout.item_photo
        }
    }

    override fun getItemCount(): Int {
        return items?.size ?: 0 + if (hasExtraRow()) 1 else 0
    }

    /**
     * Sets the [NetworkState] which in turn, determines whether to add progress item and notify.
     * */
    fun setNetworkState(newNetworkState: NetworkState?) {
        val previousState = this.networkState
        val hadExtraRow = hasExtraRow()
        this.networkState = newNetworkState
        val hasExtraRow = hasExtraRow()
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(itemCount)
            } else {
                notifyItemInserted(itemCount)
            }
        } else if (hasExtraRow && previousState != newNetworkState) {
            notifyItemChanged(itemCount - 1)
        }
    }

    /**
     * Override span size so that progress item takes all columns.
     * */
    fun spanSizeOverride(gridLayoutManager: GridLayoutManager) {
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (getItemViewType(position)) {
                    com.uber.user.R.layout.item_photo -> 1
                    com.uber.user.R.layout.item_progress_bar -> numberOfColumns
                    else -> -1
                }
            }
        }
    }

    override fun areItemsTheSame(oldItem: Photo, newItem: Photo) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Photo, newItem: Photo) = oldItem == newItem
}

enum class NetworkState { LOADED, LOADING }

