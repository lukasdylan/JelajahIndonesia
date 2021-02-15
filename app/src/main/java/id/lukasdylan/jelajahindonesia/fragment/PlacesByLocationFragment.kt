package id.lukasdylan.jelajahindonesia.fragment

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import id.lukasdylan.jelajahindonesia.R
import id.lukasdylan.jelajahindonesia.databinding.FragmentPlacesByLocationBinding
import id.lukasdylan.jelajahindonesia.utils.applyWindowInsets
import id.lukasdylan.jelajahindonesia.utils.setMargin

/**
 * Created by Lukas Dylan on 16/01/21.
 */
class PlacesByLocationFragment : BaseFragment(R.layout.fragment_places_by_location) {

    private val placesByLocationBinding by viewBinding(FragmentPlacesByLocationBinding::bind)
    private val args by navArgs<PlacesByLocationFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        placesByLocationBinding.rootLayout.applyWindowInsets {
            placesByLocationBinding.toolbar.setMargin(it.systemWindowInsetTop)
        }

        placesByLocationBinding.toolbar.apply {
            title = args.title.orEmpty()
            args.subtitle?.takeUnless { it.isBlank() }?.let { subtitle = it }

            setNavigationOnClickListener {
                findNavController().navigateUp()
            }
        }
    }
}