package com.uber.user.ui.photos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.uber.data.model.Photos
import com.uber.data.photosPerPage
import com.uber.user.DependencyProvider.photoRepository
import com.uber.user.extensions.hideIme
import com.uber.user.widget.EndlessRecyclerViewScrollListener
import kotlinx.android.synthetic.main.fragment_photos.*

/**
 * @author Vipul Kumar; dated 30/01/19.
 *
 * Fragment responsible for hosting a list of photos.
 * It mostly interacts with [PhotoAdapter]
 */
class PhotosFragment : Fragment() {
    private val viewModel by lazy { PhotosViewModel(photosRepository = photoRepository) }
    private val photoAdapter = PhotoAdapter()
    private var query = "kittens" // default search keyword
    private val initialPage = 1
    private val incrementCoefficient = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(com.uber.user.R.layout.fragment_photos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val gridLayoutManager = GridLayoutManager(context, numberOfColumns)

        // ime listener on search view
        etSearch.setOnEditorActionListener(TextView.OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                query = etSearch.text.toString()
                fetchPhotos(initialPage)
                etSearch.hideIme()
                return@OnEditorActionListener true
            }
            false
        })

        // setup recyclerView
        rvPhotos.apply {
            adapter = photoAdapter.also { it.spanSizeOverride(gridLayoutManager) }
            layoutManager = gridLayoutManager

            addOnScrollListener(EndlessRecyclerViewScrollListener(gridLayoutManager) { item, _ ->
                if (!photoAdapter.hasExtraRow()) {
                    fetchPhotos(item / photosPerPage + incrementCoefficient)
                }
            })
        }

        fetchPhotos(initialPage) // initial load with default query
    }

    /**
     * Fetches photos from [PhotosViewModel] and shows them in ui.
     * The errors could be handled better but is out of the scope of this POC.
     * */
    private fun fetchPhotos(page: Int) {
        photoAdapter.setNetworkState(NetworkState.LOADING)
        viewModel.fetchPhotos(
            query,
            page,
            {
                showInUi(it.data)
                photoAdapter.setNetworkState(NetworkState.LOADED)
            },
            {
                Toast.makeText(context, it.exception.message, Toast.LENGTH_SHORT).show()
            } // do something with error
        )
    }

    private fun showInUi(photos: Photos?) {
        photoAdapter.append(photos?.photo ?: arrayListOf())
    }
}
