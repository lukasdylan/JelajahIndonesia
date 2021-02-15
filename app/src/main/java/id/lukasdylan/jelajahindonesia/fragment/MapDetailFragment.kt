package id.lukasdylan.jelajahindonesia.fragment

import android.content.res.Configuration
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.Point
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.style.layers.PropertyFactory
import com.mapbox.mapboxsdk.style.layers.SymbolLayer
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource
import id.lukasdylan.jelajahindonesia.R
import id.lukasdylan.jelajahindonesia.databinding.FragmentMapDetailBinding
import id.lukasdylan.jelajahindonesia.utils.applyWindowInsets
import id.lukasdylan.jelajahindonesia.utils.dpToPx
import id.lukasdylan.jelajahindonesia.utils.setMargin
import id.lukasdylan.jelajahindonesia.widget.MapViewLifecycleObserver
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Created by Lukas Dylan on 08/11/20.
 */
class MapDetailFragment : BaseFragment(R.layout.fragment_map_detail), OnMapReadyCallback {

    private val mapDetailBinding by viewBinding(FragmentMapDetailBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            finish()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapDetailBinding.apply {
            root.applyWindowInsets {
                fabClose.setMargin(
                    marginStart = view.dpToPx(8),
                    marginTop = it.systemWindowInsetTop + view.dpToPx(12)
                )
            }

            mapView.apply {
                onCreate(savedInstanceState)
                viewLifecycleOwner.lifecycle.addObserver(MapViewLifecycleObserver(this))
                getMapAsync(this@MapDetailFragment)
            }

            fabClose.setOnClickListener {
                finish()
            }
        }
    }

    private fun finish() {
        mapDetailBinding.mapView.isInvisible = true
        findNavController().navigateUp()
    }

    override fun onMapReady(mapboxMap: MapboxMap) {
        val args: MapDetailFragmentArgs by navArgs()
        val latitude = args.latitude.toDouble()
        val longitude = args.longitude.toDouble()

        mapboxMap.setStyle(
            Style.Builder()
                .fromUri(getMapStyleBasedDarkMode())
                .withDefaultImage()
                .withDefaultSource(latitude, longitude)
                .withDefaultLayer()
        ) {
            viewLifecycleOwner.lifecycleScope.launch {
                val position = CameraPosition.Builder()
                    .target(LatLng(latitude, longitude))
                    .zoom(17.0)
                    .build()
                mapboxMap.moveCamera(CameraUpdateFactory.newCameraPosition(position))
                delay(350)
                mapDetailBinding.mapView.isVisible = true
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

    private fun Style.Builder.withDefaultSource(
        latitude: Double,
        longitude: Double
    ): Style.Builder {
        return this.withSource(
            GeoJsonSource(
                SOURCE_ID,
                FeatureCollection.fromFeatures(
                    listOf(
                        Feature.fromGeometry(
                            Point.fromLngLat(
                                longitude,
                                latitude
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
                    PropertyFactory.iconImage(ICON_ID),
                    PropertyFactory.iconAllowOverlap(true),
                    PropertyFactory.iconIgnorePlacement(true)
                )
        )
    }

    companion object {
        private const val SOURCE_ID = "SOURCE_ID"
        private const val ICON_ID = "ICON_ID"
        private const val LAYER_ID = "LAYER_ID"
    }
}