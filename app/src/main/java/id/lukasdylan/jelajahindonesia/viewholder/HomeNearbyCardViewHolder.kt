package id.lukasdylan.jelajahindonesia.viewholder

import android.view.View
import coil.load
import id.lukasdylan.jelajahindonesia.R
import id.lukasdylan.jelajahindonesia.databinding.ItemCardNearbySectionBinding
import id.lukasdylan.jelajahindonesia.model.contract.TourismPlace

/**
 * Created by Lukas Dylan on 01/11/20.
 */
class HomeNearbyCardViewHolder(
    itemCardNearbySectionBinding: ItemCardNearbySectionBinding,
    listener: (View, Int) -> Unit
) : BaseViewHolder<TourismPlace, ItemCardNearbySectionBinding>(itemCardNearbySectionBinding) {

    init {
        binding.itemCardRootLayout.setOnClickListener {
            listener.invoke(it, adapterPosition)
        }
    }

    override fun bind(item: TourismPlace) {
        binding.apply {
            ivPlacePhoto.load(R.drawable.ic_default_place_photo)
            tvPlaceName.text = item.name
        }
    }
}