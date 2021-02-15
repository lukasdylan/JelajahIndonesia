package id.lukasdylan.jelajahindonesia.fragment

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.google.android.material.appbar.AppBarLayout
import dagger.hilt.android.AndroidEntryPoint
import id.lukasdylan.jelajahindonesia.R
import id.lukasdylan.jelajahindonesia.adapter.HomeViewSectionAdapter
import id.lukasdylan.jelajahindonesia.databinding.FragmentHomeBinding
import id.lukasdylan.jelajahindonesia.intention.HomeViewAction
import id.lukasdylan.jelajahindonesia.utils.applyWindowInsets
import id.lukasdylan.jelajahindonesia.utils.onContentChanged
import id.lukasdylan.jelajahindonesia.utils.setMargin
import id.lukasdylan.jelajahindonesia.viewmodel.HomeViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Created by Lukas Dylan on 21/10/20.
 */
@AndroidEntryPoint
class HomeFragment : BaseFragment(R.layout.fragment_home) {

    private val viewModel by viewModels<HomeViewModel>()
    private val homeBinding by viewBinding(FragmentHomeBinding::bind)
    private val homeViewSectionAdapter by lazy {
        val displayMetrics = DisplayMetrics().also {
            activity?.windowManager?.defaultDisplay?.getMetrics(it)
        }
        return@lazy HomeViewSectionAdapter(displayMetrics.widthPixels)
    }
    private val requestLocationPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            lifecycleScope.launchWhenStarted {
                if (isGranted) {
                    delay(200)
                    viewModel.onAction(HomeViewAction.AlreadyShowLocationPermission)
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.onAction(HomeViewAction.LoadData)
        if (savedInstanceState == null) {
            observeNavigationResult<Boolean>(LocationPermissionFragment.LOCATION_PERMISSION_RESULT_KEY) {
                lifecycleScope.launch {
                    delay(300)
                    if (it) {
                        showLocationPermission()
                    } else {
                        viewModel.onAction(HomeViewAction.AlreadyShowLocationPermission)
                    }
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observeViewState()
    }

    private fun initView() {
        homeBinding.apply {
            rootLayout.applyWindowInsets {
                toolbar.setMargin(it.systemWindowInsetTop)
            }

            rvHome.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(context)
                itemAnimator = null
                adapter = homeViewSectionAdapter
            }

            appBar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { _, verticalOffset ->
                cvSearch.isVisible = verticalOffset >= -240
            })

            ivDefaultBanner.load(R.drawable.ic_default_home)

            cvSearch.setOnClickListener {

            }

            swipeRefreshLayout.apply {
                setColorSchemeColors(ContextCompat.getColor(context, R.color.colorPrimaryDark))
                setOnRefreshListener {
                    isRefreshing = false
                    isEnabled = false
                    viewModel.onAction(HomeViewAction.RefreshData)
                }
            }
        }
    }

    private fun observeViewState() {
        observeWhenStarted(viewModel.viewState) { viewState ->
            viewState.nearbyTourismPlacesSection.onContentChanged {
                homeBinding.swipeRefreshLayout.isEnabled = true
                homeViewSectionAdapter.updateNearbyPlaces(it)
            }
            viewState.popularDestinationSection.onContentChanged {
                homeBinding.swipeRefreshLayout.isEnabled = true
                homeViewSectionAdapter.updatePopularDestination(it)
            }
            viewState.isNeedShowLocationPermission.onContentChanged {
                if (it) {
                    findNavController().navigate(HomeFragmentDirections.actionToLocationPermission())
                }
            }
        }
    }

    private fun showLocationPermission() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestLocationPermission.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            viewModel.onAction(HomeViewAction.AlreadyShowLocationPermission)
        }
    }
}