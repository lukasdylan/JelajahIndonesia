package id.lukasdylan.jelajahindonesia.utils

import android.content.res.Configuration
import android.graphics.Color
import android.os.Build
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.LayoutRes
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavOptionsBuilder
import com.facebook.shimmer.ShimmerFrameLayout
import id.lukasdylan.jelajahindonesia.R
import kotlin.math.roundToInt

/**
 * Created by Lukas Dylan on 30/10/20.
 */
fun ShimmerFrameLayout.onVisibilityListener(isLoading: Boolean) {
    if (!isLoading && isShimmerStarted) {
        stopShimmer()
    } else {
        if (!isShimmerStarted) {
            startShimmer()
        }
    }
    isVisible = isLoading
}

fun FragmentActivity.makeStatusBarTransparent() {
    window.apply {
        clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val uiVisibility =
                when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
                    Configuration.UI_MODE_NIGHT_NO -> {
                        // Night mode is not active, we're using the light theme
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                    }
                    else -> View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                }
            decorView.systemUiVisibility = uiVisibility
        } else {
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
        statusBarColor = Color.TRANSPARENT
    }
}

fun View.setMargin(
    marginTop: Int = 0,
    marginStart: Int = 0,
    marginEnd: Int = 0,
    marginBottom: Int = 0
) {
    val menuLayoutParams = this.layoutParams as ViewGroup.MarginLayoutParams
    menuLayoutParams.setMargins(marginStart, marginTop, marginEnd, marginBottom)
    this.layoutParams = menuLayoutParams
}

inline fun ViewGroup.applyWindowInsets(crossinline listener: (WindowInsetsCompat) -> Unit) {
    ViewCompat.setOnApplyWindowInsetsListener(this) { _, insets ->
        insets?.run {
            listener(this)
            consumeSystemWindowInsets()
        }
    }
}

fun ViewGroup.inflateView(@LayoutRes resId: Int): View {
    return LayoutInflater.from(context).inflate(resId, this, false)
}

fun View.dpToPx(dp: Int): Int {
    val displayMetrics = context.resources.displayMetrics
    return (dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT)).roundToInt()
}

fun NavOptionsBuilder.slideLeftRightAnim() {
    anim {
        enter = R.anim.slide_in_right
        exit = R.anim.slide_out_left
        popEnter = R.anim.slide_in_left
        popExit = R.anim.slide_out_right
    }
}

fun NavOptionsBuilder.fadeAnim() {
    anim {
        enter = R.anim.nav_default_enter_anim
        exit = R.anim.nav_default_exit_anim
        popEnter = R.anim.nav_default_pop_enter_anim
        popExit = R.anim.nav_default_pop_exit_anim
    }
}