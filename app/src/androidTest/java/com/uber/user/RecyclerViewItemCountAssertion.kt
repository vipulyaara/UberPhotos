package com.uber.user

import android.view.View

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewAssertion

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.greaterThan

class RecyclerViewItemCountAssertion(private val expectedMinCount: Int) : ViewAssertion {

    override fun check(view: View, noViewFoundException: NoMatchingViewException?) {
        if (noViewFoundException != null) {
            throw noViewFoundException
        }

        val recyclerView = view as RecyclerView
        val adapter = recyclerView.adapter
        assertThat(adapter!!.itemCount, greaterThan(expectedMinCount))
    }
}
