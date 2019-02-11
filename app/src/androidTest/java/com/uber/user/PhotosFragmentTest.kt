package com.uber.user

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.pressImeActionButton
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.uber.user.ui.MainActivity
import com.uber.user.ui.photos.PhotosFragment
import com.uber.user.ui.photos.PhotosViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

/**
 * @author Vipul Kumar; dated 06/02/19.
 */
@RunWith(AndroidJUnit4::class)
class PhotosFragmentTest {

    @Rule
    @JvmField
    val activityRule = ActivityTestRule(
        MainActivity::class.java,
        true,
        true
    )

    var fragment = PhotosFragment()
    lateinit var viewModel: PhotosViewModel

    @Before
    fun setUp() {
        fragment = PhotosFragment()
        viewModel = mock(PhotosViewModel::class.java)
    }

    @Test
    fun testIfDisplayDataFromNetworkCall() {
        onView(withId(R.id.etSearch))
            .perform(replaceText("kitten"))
            .perform(pressImeActionButton())

        Thread.sleep(4000) // Wait network to finish call the ugly way

        onView(withId(R.id.rvPhotos)).check(RecyclerViewItemCountAssertion(2))
    }

    @Test
    @Throws(Throwable::class)
    fun loadMore() {
        onView(withId(R.id.etSearch))
            .perform(replaceText("kitten"))
            .perform(pressImeActionButton())

        Thread.sleep(4000) // Wait network to finish call the ugly way

        onView(withId(R.id.rvPhotos)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(9)
        )

        onView(listMatcher().atPosition(9)).check(matches(isDisplayed()))
        verify<PhotosViewModel>(viewModel).fetchPhotos("kitten", 1, {}, {})
    }

    private fun listMatcher(): RecyclerViewMatcher {
        return RecyclerViewMatcher(R.id.rvPhotos)
    }
}
