package id.lukasdylan.jelajahindonesia.adapter

import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import id.lukasdylan.jelajahindonesia.R
import id.lukasdylan.jelajahindonesia.databinding.ItemCardNearbySectionBinding
import id.lukasdylan.jelajahindonesia.fragment.HomeFragmentDirections
import id.lukasdylan.jelajahindonesia.model.contract.TourismPlace
import id.lukasdylan.jelajahindonesia.utils.inflateView
import id.lukasdylan.jelajahindonesia.utils.slideLeftRightAnim
import id.lukasdylan.jelajahindonesia.viewholder.HomeNearbyCardViewHolder

/**
 * Created by Lukas Dylan on 01/11/20.
 */
class HomeNearbySectionAdapter(
    private val screenWidthSize: Int
) : ListAdapter<TourismPlace, HomeNearbyCardViewHolder>(TourismPlaceDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeNearbyCardViewHolder {
        val view = parent.inflateView(R.layout.item_card_nearby_section)
        return HomeNearbyCardViewHolder(ItemCardNearbySectionBinding.bind(view).also {
            it.itemCardRootLayout.layoutParams.width = screenWidthSize * 3 / 4
        }) { rootView, position ->
            getItem(position)?.let {
                rootView.findNavController().navigate(
                    HomeFragmentDirections.actionToDetailPlace(
                        xid = it.id,
                        title = it.name
                    ),
                    navOptions { slideLeftRightAnim() })
            }
        }
    }

    override fun onBindViewHolder(holder: HomeNearbyCardViewHolder, position: Int) {
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