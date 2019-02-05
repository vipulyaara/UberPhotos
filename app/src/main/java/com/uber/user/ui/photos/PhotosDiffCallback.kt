package com.uber.user.ui.photos

import androidx.recyclerview.widget.DiffUtil
import com.uber.data.model.Photo

/**
 * @author Vipul Kumar; dated 04/02/19.
 */
class PhotosDiffCallback(
    private val oldList: List<Photo>,
    private val newList: List<Photo>
) : DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}
