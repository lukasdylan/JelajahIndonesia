package id.lukasdylan.jelajahindonesia.adapter

import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import id.lukasdylan.jelajahindonesia.MainNavGraphDirections
import id.lukasdylan.jelajahindonesia.R
import id.lukasdylan.jelajahindonesia.databinding.ItemCardPopularSectionBinding
import id.lukasdylan.jelajahindonesia.model.contract.TourismPlace
import id.lukasdylan.jelajahindonesia.utils.inflateView
import id.lukasdylan.jelajahindonesia.utils.slideLeftRightAnim
import id.lukasdylan.jelajahindonesia.viewholder.ExploreRecommendedCityViewHolder

/**
 * Created by Lukas Dylan on 08/11/20.
 */
class ExploreRecommendedCityAdapter :
    ListAdapter<TourismPlace, ExploreRecommendedCityViewHolder>(TourismPlaceDiffUtil) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ExploreRecommendedCityViewHolder {
        val view = parent.inflateView(R.layout.item_card_popular_section)
        return ExploreRecommendedCityViewHolder(ItemCardPopularSectionBinding.bind(view)) { rootView, position ->
            getItem(position)?.let { data ->
                rootView.findNavController().navigate(
                    MainNavGraphDirections.actionToPlacesByLocation(
                        title = "Lokasi wisata di ${data.name}",
                        longitude = data.coordinates.longitude.toFloat(),
                        latitude = data.coordinates.latitude.toFloat(),
                        subtitle = null
                    ),
                    navOptions { slideLeftRightAnim() })
            }
        }
    }

    override fun onBindViewHolder(holder: ExploreRecommendedCityViewHolder, position: Int) {
        getItem(position)?.let(holder::bind)
    }

    private object TourismPlaceDiffUtil : DiffUtil.ItemCallback<TourismPlace>() {
        override fun areItemsTheSame(oldItem: TourismPlace, newItem: TourismPlace): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TourismPlace, newItem: TourismPlace): Boolean {
            return oldItem == newItem
        }
    }
}