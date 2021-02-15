package id.lukasdylan.jelajahindonesia.adapter

import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import id.lukasdylan.jelajahindonesia.R
import id.lukasdylan.jelajahindonesia.databinding.ItemCardPopularSectionBinding
import id.lukasdylan.jelajahindonesia.fragment.HomeFragmentDirections
import id.lukasdylan.jelajahindonesia.model.contract.TourismPlace
import id.lukasdylan.jelajahindonesia.utils.inflateView
import id.lukasdylan.jelajahindonesia.utils.slideLeftRightAnim
import id.lukasdylan.jelajahindonesia.viewholder.HomePopularCardViewHolder

/**
 * Created by Lukas Dylan on 01/11/20.
 */
class HomePopularSectionAdapter :
    ListAdapter<TourismPlace, HomePopularCardViewHolder>(TourismPlaceDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomePopularCardViewHolder {
        val view = parent.inflateView(R.layout.item_card_popular_section)
        return HomePopularCardViewHolder(ItemCardPopularSectionBinding.bind(view)) { rootView, position ->
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

    override fun onBindViewHolder(holder: HomePopularCardViewHolder, position: Int) {
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