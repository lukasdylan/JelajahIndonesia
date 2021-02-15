package id.lukasdylan.jelajahindonesia.utils

/**
 * Created by Lukas Dylan on 24/10/20.
 */
fun String?.asTags(): List<String> {
    return this?.replace("_", " ")?.split(",")?.toList().orEmpty()
}