package id.lukasdylan.jelajahindonesia.utils

/**
 * Created by Lukas Dylan on 29/10/20.
 */
class Content<T>(private val content: T) {

    private var hasBeenHandled = false

    fun getContentIfNotHandled(): T? {
        return if (isHandled()) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    fun isHandled(): Boolean = hasBeenHandled

    fun peekContent(): T = content
}

inline fun <T> Content<T>.onContentChanged(crossinline listener: (T) -> Unit) {
    this.getContentIfNotHandled()?.let(listener)
}