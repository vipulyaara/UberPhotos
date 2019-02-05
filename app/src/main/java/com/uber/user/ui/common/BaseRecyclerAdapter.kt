package com.uber.user.ui.common

import android.annotation.SuppressLint
import android.os.AsyncTask
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

/**
 * A generic RecyclerView adapter that uses DiffUtil.
 *
 * @param <T> Type of the items in the list
</T> */
abstract class BaseRecyclerAdapter<T> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var items: List<T>? = null
    // each time data is set, we update this variable so that if DiffUtil calculation returns
    // after repetitive updates, we can ignore the old calculation
    private var dataVersion = 0

    fun append(update: List<T>) {
        replace(if (items == null) update else items?.toMutableList()?.also { it.addAll(update) })
    }

    @SuppressLint("StaticFieldLeak")
    fun replace(update: List<T>?) {
        dataVersion++
        when {
            items == null -> {
                if (update == null) {
                    return
                }
                items = update
                notifyDataSetChanged()
            }
            update == null -> {
                val oldSize = items!!.size
                items = null
                notifyItemRangeRemoved(0, oldSize)
            }
            else -> {
                val startVersion = dataVersion
                val oldItems = items
                object : AsyncTask<Void, Void, DiffUtil.DiffResult>() {
                    override fun doInBackground(vararg voids: Void): DiffUtil.DiffResult {
                        return DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                            override fun getOldListSize(): Int {
                                return oldItems!!.size
                            }

                            override fun getNewListSize(): Int {
                                return update.size
                            }

                            override fun areItemsTheSame(
                                oldItemPosition: Int,
                                newItemPosition: Int
                            ): Boolean {
                                val oldItem = oldItems!![oldItemPosition]
                                val newItem = update[newItemPosition]
                                return this@BaseRecyclerAdapter.areItemsTheSame(oldItem, newItem)
                            }

                            override fun areContentsTheSame(
                                oldItemPosition: Int,
                                newItemPosition: Int
                            ): Boolean {
                                val oldItem = oldItems!![oldItemPosition]
                                val newItem = update[newItemPosition]
                                return this@BaseRecyclerAdapter.areContentsTheSame(oldItem, newItem)
                            }
                        })
                    }

                    override fun onPostExecute(diffResult: DiffUtil.DiffResult) {
                        if (startVersion != dataVersion) {
                            // ignore update
                            return
                        }
                        items = update
                        diffResult.dispatchUpdatesTo(this@BaseRecyclerAdapter)
                    }
                }.execute()
            }
        }
    }

    protected abstract fun areItemsTheSame(oldItem: T, newItem: T): Boolean

    protected abstract fun areContentsTheSame(oldItem: T, newItem: T): Boolean

    override fun getItemCount(): Int {
        return if (items == null) 0 else items!!.size
    }
}
