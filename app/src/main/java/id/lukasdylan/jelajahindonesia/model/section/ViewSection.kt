package id.lukasdylan.jelajahindonesia.model.section

import id.lukasdylan.jelajahindonesia.utils.StringWrapper

/**
 * Created by Lukas Dylan on 30/10/20.
 */
interface ViewSection<T> {
    val id: Int
    val title: StringWrapper
    val data: List<T>
    val isLoading: Boolean
    val errorType: ErrorType

    companion object {
        const val NEARBY_PLACES_ID = 1
        const val POPULAR_DESTINATION_ID = 2
    }

    enum class ErrorType {
        NONE, GENERAL, NO_PERMISSION, EMPTY
    }
}