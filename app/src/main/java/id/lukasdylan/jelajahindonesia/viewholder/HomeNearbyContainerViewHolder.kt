package id.lukasdylan.jelajahindonesia.viewholder

import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import id.lukasdylan.jelajahindonesia.MainNavGraphDirections
import id.lukasdylan.jelajahindonesia.R
import id.lukasdylan.jelajahindonesia.adapter.HomeNearbySectionAdapter
import id.lukasdylan.jelajahindonesia.databinding.ItemHomeContainerBinding
import id.lukasdylan.jelajahindonesia.model.section.HomeNearbyViewSection
import id.lukasdylan.jelajahindonesia.model.section.ViewSection
import id.lukasdylan.jelajahindonesia.utils.dpToPx
import id.lukasdylan.jelajahindonesia.utils.onVisibilityListener
import id.lukasdylan.jelajahindonesia.utils.slideLeftRightAnim
import id.lukasdylan.jelajahindonesia.widget.GridSpacingItemDecoration

/**
 * Created by Lukas Dylan on 30/10/20.
 */
class HomeNearbyContainerViewHolder(
    itemHomeContainerBinding: ItemHomeContainerBinding,
    screenWidthSize: Int
) : BaseViewHolder<HomeNearbyViewSection, ItemHomeContainerBinding>(itemHomeContainerBinding) {

    private val homeNearbySectionAdapter = HomeNearbySectionAdapter(screenWidthSize)

    init {
        binding.rvData.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            addItemDecoration(GridSpacingItemDecoration(10, dpToPx(16), true))
            isNestedScrollingEnabled = false
            adapter = homeNearbySectionAdapter
        }
    }

    override fun bind(item: HomeNearbyViewSection) {
        binding.apply {
            tvTitleSection.text = item.title.getString(context)
            item.getUserLocationName(context).takeUnless { it.isBlank() }?.let {
                tvSubtitleSection.text = "Berdasarkan lokasi kamu di $it"
            }
            tvSeeMore.isVisible = item.errorType == ViewSection.ErrorType.NONE && !item.isLoading
            shimmerLayout.onVisibilityListener(item.isLoading)
            homeNearbySectionAdapter.submitList(item.data)
            groupError.isVisible = item.errorType != ViewSection.ErrorType.NONE
            when (item.errorType) {
                ViewSection.ErrorType.NONE -> {
                    ivError.setImageResource(0)
                    tvErrorReason.text = null
                }
                ViewSection.ErrorType.NO_PERMISSION -> {
                    ivError.setImageResource(R.drawable.vector_location_off)
                    tvErrorReason.text =
                        "Kami membutuhkan izin penggunaan lokasi untuk menampilkan objek wisata di sekitar kamu"
                }
                else -> {
                    ivError.setImageResource(R.drawable.vector_no_result)
                    tvErrorReason.text = "Kami tidak dapat menemukan objek wisata di sekitar kamu"
                }
            }
            tvSeeMore.setOnClickListener {
                it.findNavController().navigate(
                    MainNavGraphDirections.actionToPlacesByLocation(
                        title = "Lokasi wisata di sekitar kamu",
                        longitude = item.userCoordinates.longitude.toFloat(),
                        latitude = item.userCoordinates.latitude.toFloat(),
                        subtitle = tvSubtitleSection.text.toString()
                    ),
                    navOptions { slideLeftRightAnim() })
            }
        }
    }
}