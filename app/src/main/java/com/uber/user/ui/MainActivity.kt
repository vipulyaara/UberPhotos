package com.uber.user.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.uber.user.R
import com.uber.user.extensions.commit
import com.uber.user.ui.photos.PhotosFragment

/**
 * @author Vipul Kumar; dated 30/01/19.
 *
 * Activity to host fragments.
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.commit {
            replace(R.id.fragmentContainer, PhotosFragment())
        }
    }
}
