package com.uber.user.extensions

import android.graphics.drawable.Drawable
import android.os.AsyncTask
import android.widget.ImageView
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.uber.data.AppExecutors
import com.uber.data.AppExecutors.imagesIO
import com.uber.data.AppExecutors.networkIO
import com.uber.data.dali.Dali
import com.uber.data.model.Photo
import java.io.InputStream
import java.net.URL

/**
 * @author Vipul Kumar; dated 30/01/19.
 */
fun FragmentManager.commit(func: FragmentTransaction.() -> FragmentTransaction) {
    this.beginTransaction().func().commit()
}
