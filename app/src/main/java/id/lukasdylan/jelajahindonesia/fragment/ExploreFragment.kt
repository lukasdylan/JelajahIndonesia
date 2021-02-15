package id.lukasdylan.jelajahindonesia.fragment

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.lukasdylan.jelajahindonesia.R
import id.lukasdylan.jelajahindonesia.adapter.ExploreRecommendedCityAdapter
import id.lukasdylan.jelajahindonesia.databinding.FragmentExploreBinding
import id.lukasdylan.jelajahindonesia.intention.ExploreViewAction
import id.lukasdylan.jelajahindonesia.utils.applyWindowInsets
import id.lukasdylan.jelajahindonesia.utils.dpToPx
import id.lukasdylan.jelajahindonesia.utils.onContentChanged
import id.lukasdylan.jelajahindonesia.utils.setMargin
import id.lukasdylan.jelajahindonesia.viewmodel.ExploreViewModel
import id.lukasdylan.jelajahindonesia.widget.GridSpacingItemDecoration

/**
 * Created by Lukas Dylan on 31/10/20.
 */
@AndroidEntryPoint
class ExploreFragment : BaseFragment(R.layout.fragment_explore) {

    private val exploreBinding by viewBinding(FragmentExploreBinding::bind)
    private val viewModel by viewModels<ExploreViewModel>()
    private val exploreRecommendedCityAdapter = ExploreRecommendedCityAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.onAction(ExploreViewAction.LoadData)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        peekContent()
        exploreBinding.apply {
            rootLayout.applyWindowInsets {
                toolbar.setMargin(it.systemWindowInsetTop)
            }

            rvExplore.apply {
                setHasFixedSize(true)
                layoutManager = GridLayoutManager(context, 2)
                addItemDecoration(GridSpacingItemDecoration(2, dpToPx(16), true))
                adapter = exploreRecommendedCityAdapter
            }
        }

        observeWhenStarted(viewModel.viewState) { viewState ->
            viewState.title.onContentChanged {
                setTitle(it)
            }
            viewState.subtitle.onContentChanged {
                setSubtitle(it)
            }
            viewState.isLoading.onContentChanged {
                setLoadingStatus(it)
            }
            viewState.recommendedCities.onContentChanged {
                exploreRecommendedCityAdapter.submitList(it)
            }
        }
    }

    private fun peekContent() {
        with(viewModel.viewState.value) {
            setTitle(title.peekContent())
            setSubtitle(subtitle.peekContent())
            setLoadingStatus(isLoading.peekContent())
        }
    }

    private fun setTitle(title: String) {
        exploreBinding.tvExploreTitle.text = title
    }

    private fun setSubtitle(subtitle: String) {
        exploreBinding.tvExploreDesc.text = subtitle
    }

    private fun setLoadingStatus(isLoading: Boolean) {
        exploreBinding.progressBar.isVisible = isLoading
    }
}