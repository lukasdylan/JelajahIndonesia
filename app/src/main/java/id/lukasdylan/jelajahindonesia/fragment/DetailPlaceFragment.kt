package id.lukasdylan.jelajahindonesia.fragment

import android.content.res.Configuration
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navOptions
import coil.load
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.Point
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.style.layers.PropertyFactory.*
import com.mapbox.mapboxsdk.style.layers.SymbolLayer
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource
import dagger.hilt.android.AndroidEntryPoint
import id.lukasdylan.jelajahindonesia.R
import id.lukasdylan.jelajahindonesia.databinding.FragmentDetailPlaceBinding
import id.lukasdylan.jelajahindonesia.intention.DetailPlaceViewAction
import id.lukasdylan.jelajahindonesia.model.Coordinates
import id.lukasdylan.jelajahindonesia.utils.applyWindowInsets
import id.lukasdylan.jelajahindonesia.utils.onContentChanged
import id.lukasdylan.jelajahindonesia.utils.setMargin
import id.lukasdylan.jelajahindonesia.utils.slideLeftRightAnim
import id.lukasdylan.jelajahindonesia.viewmodel.DetailPlaceViewModel
import id.lukasdylan.jelajahindonesia.widget.MapViewLifecycleObserver
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


/**
 * Created by Lukas Dylan on 05/11/20.
 */
@AndroidEntryPoint
class DetailPlaceFragment : BaseFragment(R.layout.fragment_detail_place) {

    private val viewModel by viewModels<DetailPlaceViewModel>()
    private val detailPlaceBinding by viewBinding(FragmentDetailPlaceBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args: DetailPlaceFragmentArgs by navArgs()
        viewModel.onAction(
            DetailPlaceViewAction.LoadDetailPlace(
                xid = args.xid.orEmpty(),
                title = args.title.orEmpty()
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        peekContents()
        detailPlaceBinding.layoutDetailPlace.mapView.apply {
            onCreate(savedInstanceState)
            viewLifecycleOwner.lifecycle.addObserver(MapViewLifecycleObserver(this))
        }

        detailPlaceBinding.rootLayout.applyWindowInsets {
            detailPlaceBinding.toolbar.setMargin(it.systemWindowInsetTop)
        }

        detailPlaceBinding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        observeWhenStarted(viewModel.viewState) { detailPlaceViewState ->
            detailPlaceViewState.detailPlace.onContentChanged {
                detailPlaceBinding.layoutDetailPlace.groupContent.isVisible = it != null
                if (it != null) {
                    loadBannerPhoto(it.imageUrl)
                    setPlaceName(it.name)
                    setPlaceAddress(it.address)
                    setPlaceDescription(it.description)
                    setTags(it.tags)
                    initMaps(it.coordinates)
                }
            }
            detailPlaceViewState.isLoading.onContentChanged {
                detailPlaceBinding.layoutDetailPlace.progressBar.isVisible = it
            }
            detailPlaceViewState.title.onContentChanged {
                detailPlaceBinding.toolbarLayout.title = it
            }
            detailPlaceViewState.errorLoadDetailPlace.onContentChanged { errorMessage ->
                errorMessage.getString(requireContext()).takeUnless { it.isBlank() }?.let {
                    Snackbar.make(detailPlaceBinding.root, it, Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun peekContents() {
        with(viewModel.viewState.value) {
            detailPlaceBinding.layoutDetailPlace.progressBar.isVisible = isLoading.peekContent()
            detailPlaceBinding.layoutDetailPlace.groupContent.isVisible =
                detailPlace.peekContent() != null

            detailPlace.peekContent()?.let {
                loadBannerPhoto(it.imageUrl)
                setPlaceName(it.name)
                setPlaceAddress(it.address)
                setPlaceDescription(it.description)
                setTags(it.tags)
                initMaps(it.coordinates)
            }

            detailPlaceBinding.toolbarLayout.title = title.peekContent()
        }
    }

    private fun loadBannerPhoto(imageUrl: String) {
        detailPlaceBinding.ivDefaultBanner.load(imageUrl)
    }

    private fun setPlaceName(placeName: String) {
        detailPlaceBinding.layoutDetailPlace.tvPlaceName.text = placeName
    }

    private fun setPlaceAddress(placeAddress: String) {
        detailPlaceBinding.layoutDetailPlace.tvPlaceAddress.text = placeAddress
    }

    private fun setPlaceDescription(placeDescription: String) {
        detailPlaceBinding.layoutDetailPlace.tvPlaceDescription.text = placeDescription
    }

    private fun setTags(tags: List<String>) {
        tags.forEach { tag ->
            val chip =
                layoutInflater.inflate(R.layout.item_chip_tag, null, false) as Chip
            chip.text = tag
            detailPlaceBinding.layoutDetailPlace.chipGroupTag.addView(chip)
        }
    }

    private fun initMaps(coordinates: Coordinates) {
        detailPlaceBinding.layoutDetailPlace.mapView.getMapAsync { mapboxMap ->
            mapboxMap.uiSettings.apply {
                isDoubleTapGesturesEnabled = false
                isScrollGesturesEnabled = false
                isZoomGesturesEnabled = false
            }
            mapboxMap.addOnMapClickListener {
                findNavController().navigate(
                    DetailPlaceFragmentDirections.actionToMapDetail(
                        longitude = coordinates.longitude.toFloat(),
                        latitude = coordinates.latitude.toFloat()
                    ), navOptions { slideLeftRightAnim() }
                )
                return@addOnMapClickListener true
            }
            mapboxMap.setStyle(
                Style.Builder()
                    .fromUri(getMapStyleBasedDarkMode())
                    .withDefaultImage()
                    .withDefaultSource(coordinates)
                    .withDefaultLayer()
            ) {
                viewLifecycleOwner.lifecycleScope.launch {
                    val position = CameraPosition.Builder()
                        .target(LatLng(coordinates.latitude, coordinates.longitude))
                        .zoom(17.0)
                        .build()
                    mapboxMap.moveCamera(CameraUpdateFactory.newCameraPosition(position))
                    delay(350)
                    detailPlaceBinding.layoutDetailPlace.mapView.isVisible = true
                }
            }
        }
    }

    private fun getMapStyleBasedDarkMode(): String {
        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return if (currentNightMode == Configuration.UI_MODE_NIGHT_YES) {
            Style.DARK
        } else {
            Style.LIGHT
        }
    }

    private fun Style.Builder.withDefaultImage(): Style.Builder {
        return this.withImage(
            ICON_ID, BitmapFactory.decodeResource(
                resources,
                R.drawable.mapbox_marker_icon_default
            )
        )
    }

    private fun Style.Builder.withDefaultSource(coordinates: Coordinates): Style.Builder {
        return this.withSource(
            GeoJsonSource(
                SOURCE_ID,
                FeatureCollection.fromFeatures(
                    listOf(
                        Feature.fromGeometry(
                            Point.fromLngLat(
                                coordinates.longitude,
                                coordinates.latitude
                            )
                        )
                    )
                )
            )
        )
    }

    private fun Style.Builder.withDefaultLayer(): Style.Builder {
        return this.withLayer(
            SymbolLayer(LAYER_ID, SOURCE_ID)
                .withProperties(
                    iconImage(ICON_ID),
                    iconAllowOverlap(true),
                    iconIgnorePlacement(true)
                )
        )
    }

    companion object {
        private const val SOURCE_ID = "SOURCE_ID"
        private const val ICON_ID = "ICON_ID"
        private const val LAYER_ID = "LAYER_ID"
    }
}