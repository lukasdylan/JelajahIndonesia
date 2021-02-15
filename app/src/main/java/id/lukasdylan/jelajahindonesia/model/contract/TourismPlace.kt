package id.lukasdylan.jelajahindonesia.model.contract

/**
 * Created by Lukas Dylan on 20/10/20.
 */
interface TourismPlace : PlaceCoordinates {
    val id: String
    val distance: Double
    val tags: List<String>
    val rate: Int
    val imageUrl: String

    override fun equals(other: Any?): Boolean
}