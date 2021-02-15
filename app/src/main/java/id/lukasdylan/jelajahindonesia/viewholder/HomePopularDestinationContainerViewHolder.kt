package id.lukasdylan.jelajahindonesia.viewholder

import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import id.lukasdylan.jelajahindonesia.R
import id.lukasdylan.jelajahindonesia.adapter.HomePopularSectionAdapter
import id.lukasdylan.jelajahindonesia.databinding.ItemHomeContainerBinding
import id.lukasdylan.jelajahindonesia.model.section.HomePopularDestinationViewSection
import id.lukasdylan.jelajahindonesia.model.section.ViewSection
import id.lukasdylan.jelajahindonesia.utils.dpToPx
import id.lukasdylan.jelajahindonesia.utils.onVisibilityListener
import id.lukasdylan.jelajahindonesia.widget.GridSpacingItemDecoration

/**
 * Created by Lukas Dylan on 30/10/20.
 */
class HomePopularDestinationContainerViewHolder(
    itemHomeContainerBinding: ItemHomeContainerBinding
) : BaseViewHolder<HomePopularDestinationViewSection, ItemHomeContainerBinding>(
    itemHomeContainerBinding
) {

    private val homePopularSectionAdapter = HomePopularSectionAdapter()

    init {
        binding.rvData.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context, 2)
            addItemDecoration(GridSpacingItemDecoration(2, dpToPx(16), true))
            isNestedScrollingEnabled = false
            adapter = homePopularSectionAdapter
        }
    }

    override fun bind(item: HomePopularDestinationViewSection) {
        binding.apply {
            tvTitleSection.text = item.title.getString(context)
            tvSeeMore.isGone = true
            tvSubtitleSection.isGone = true
            shimmerLayout.onVisibilityListener(item.isLoading)
            homePopularSectionAdapter.submitList(item.data)
            groupError.isVisible = item.errorType != ViewSection.ErrorType.NONE
            when (item.errorType) {
                ViewSection.ErrorType.NONE -> {
                    ivError.setImageResource(0)
                    tvErrorReason.text = null
                }
                else -> {
                    ivError.setImageResource(R.drawable.vector_no_result)
                    tvErrorReason.text = "Kami tidak dapat menemukan objek wisata populer"
                }
            }
        }
    }
}