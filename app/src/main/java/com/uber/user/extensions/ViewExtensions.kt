package com.uber.user.extensions

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import com.uber.data.dali.Dali
import com.uber.user.R

/**
 * @author Vipul Kumar; dated 04/02/19.
 */

fun View.hideIme() {
    val imm = context.getSystemService(
        Context
            .INPUT_METHOD_SERVICE
    ) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

/**
 * Extension function to load image into an [ImageView] using [Dali].
 */
fun ImageView.loadImage(
    url: String,
    position: Int
) {
    Dali.with(context)
        .source(url)
        .placeholder(R.drawable.ic_linked_camera_black_24dp)
        .loadImage(this, position.toString())
}
