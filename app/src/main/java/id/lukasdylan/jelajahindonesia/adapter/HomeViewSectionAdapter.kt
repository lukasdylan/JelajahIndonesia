package id.lukasdylan.jelajahindonesia.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.lukasdylan.jelajahindonesia.R
import id.lukasdylan.jelajahindonesia.databinding.ItemHomeContainerBinding
import id.lukasdylan.jelajahindonesia.model.section.HomeNearbyViewSection
import id.lukasdylan.jelajahindonesia.model.section.HomePopularDestinationViewSection
import id.lukasdylan.jelajahindonesia.model.section.ViewSection
import id.lukasdylan.jelajahindonesia.utils.inflateView
import id.lukasdylan.jelajahindonesia.viewholder.HomeNearbyContainerViewHolder
import id.lukasdylan.jelajahindonesia.viewholder.HomePopularDestinationContainerViewHolder

/**
 * Created by Lukas Dylan on 30/10/20.
 */
class HomeViewSectionAdapter(private val screenWidthSize: Int) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val sectionList = mutableListOf<ViewSection<*>>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder = when (viewType) {
        ViewSection.NEARBY_PLACES_ID -> {
            val view = parent.inflateView(R.layout.item_home_container)
            HomeNearbyContainerViewHolder(ItemHomeContainerBinding.bind(view), screenWidthSize)
        }
        ViewSection.POPULAR_DESTINATION_ID -> {
            val view = parent.inflateView(R.layout.item_home_container)
            HomePopularDestinationContainerViewHolder(ItemHomeContainerBinding.bind(view))
        }
        else -> throw IllegalStateException("Unknown viewType")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is HomeNearbyContainerViewHolder) {
            holder.bind(sectionList[position] as HomeNearbyViewSection)
        } else if (holder is HomePopularDestinationContainerViewHolder) {
            holder.bind(sectionList[position] as HomePopularDestinationViewSection)
        }
    }

    override fun getItemCount(): Int = sectionList.size

    override fun getItemViewType(position: Int): Int = sectionList[position].id

    fun updateNearbyPlaces(data: HomeNearbyViewSection) {
        getSectionPosition(ViewSection.NEARBY_PLACES_ID)?.let {
            sectionList[it] = data
            notifyItemChanged(it)
        } ?: kotlin.run {
            sectionList.add(data)
            notifyItemInserted(sectionList.size - 1)
        }
    }

    fun updatePopularDestination(data: HomePopularDestinationViewSection) {
        getSectionPosition(ViewSection.POPULAR_DESTINATION_ID)?.let {
            sectionList[it] = data
            notifyItemChanged(it)
        } ?: kotlin.run {
            sectionList.add(data)
            notifyItemInserted(sectionList.size - 1)
        }
    }

    private fun getSectionPosition(id: Int): Int? {
        return sectionList.indexOf(sectionList.find { it.id == id }).takeIf { it >= 0 }
    }

}