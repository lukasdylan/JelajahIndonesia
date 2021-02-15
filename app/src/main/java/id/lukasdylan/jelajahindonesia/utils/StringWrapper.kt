package id.lukasdylan.jelajahindonesia.utils

import android.content.Context
import androidx.annotation.StringRes

/**
 * Created by Lukas Dylan on 30/10/20.
 */
sealed class StringWrapper {

    abstract fun getString(context: Context): String

    data class StringType(val text: String) : StringWrapper() {
        override fun getString(context: Context): String = text
    }

    data class ResourcesType(@StringRes val resourceId: Int) : StringWrapper() {
        override fun getString(context: Context): String {
            return context.getString(resourceId)
        }
    }

    data class ThrowableType(val throwable: Throwable) : StringWrapper() {
        override fun getString(context: Context): String {
            return throwable.message.orEmpty()
        }
    }

    companion object {
        fun wrap(text: String): StringWrapper = StringType(text)
        fun wrap(@StringRes resourceId: Int): StringWrapper = ResourcesType(resourceId)
        fun wrap(throwable: Throwable): StringWrapper = ThrowableType(throwable)
    }
}