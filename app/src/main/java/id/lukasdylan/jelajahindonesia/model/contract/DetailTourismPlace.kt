package id.lukasdylan.jelajahindonesia.model.contract

/**
 * Created by Lukas Dylan on 22/10/20.
 */
interface DetailTourismPlace : TourismPlace {
    val address: String
    val description: String
    val wikipediaLink: String
}