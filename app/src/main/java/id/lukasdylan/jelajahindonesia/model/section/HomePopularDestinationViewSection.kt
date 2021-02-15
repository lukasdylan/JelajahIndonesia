package id.lukasdylan.jelajahindonesia.model.section

import id.lukasdylan.jelajahindonesia.model.contract.TourismPlace
import id.lukasdylan.jelajahindonesia.utils.StringWrapper

/**
 * Created by Lukas Dylan on 07/11/20.
 */
data class HomePopularDestinationViewSection(
    override val title: StringWrapper = StringWrapper.wrap(""),
    override val data: List<TourismPlace> = emptyList(),
    override val isLoading: Boolean = true,
    override val errorType: ViewSection.ErrorType = ViewSection.ErrorType.NONE
) : ViewSection<TourismPlace> {

    override val id: Int
        get() = ViewSection.POPULAR_DESTINATION_ID
}