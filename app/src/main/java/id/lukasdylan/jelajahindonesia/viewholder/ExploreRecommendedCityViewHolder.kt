package id.lukasdylan.jelajahindonesia.viewholder

import android.view.View
import coil.load
import id.lukasdylan.jelajahindonesia.databinding.ItemCardPopularSectionBinding
import id.lukasdylan.jelajahindonesia.model.contract.TourismPlace

/**
 * Created by Lukas Dylan on 01/11/20.
 */
class ExploreRecommendedCityViewHolder(
    itemCardPopularSectionBinding: ItemCardPopularSectionBinding,
    listener: (View, Int) -> Unit
) : BaseViewHolder<TourismPlace, ItemCardPopularSectionBinding>(itemCardPopularSectionBinding) {

    init {
        binding.rootLayout.setOnClickListener {
            listener.invoke(it, adapterPosition)
        }
    }

    override fun bind(item: TourismPlace) {
        binding.apply {
            ivPlacePhoto.load(item.imageUrl)
            tvPlaceName.text = item.name
        }
    }
}