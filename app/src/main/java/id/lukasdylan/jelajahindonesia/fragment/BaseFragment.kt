package id.lukasdylan.jelajahindonesia.fragment

import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import id.lukasdylan.jelajahindonesia.intention.ViewState
import id.lukasdylan.jelajahindonesia.utils.FragmentViewBindingDelegate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

/**
 * Created by Lukas Dylan on 28/10/20.
 */
open class BaseFragment(@LayoutRes layoutId: Int) : Fragment(layoutId) {

    protected fun <T : ViewBinding> Fragment.viewBinding(viewBindingFactory: (View) -> T) =
        FragmentViewBindingDelegate(this, viewBindingFactory)

    protected fun <T : ViewState> observeWhenStarted(
        stateFlow: Flow<T>,
        callbackValue: (T) -> Unit
    ) {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            stateFlow.onEach(callbackValue::invoke).catch { Timber.e(it) }.launchIn(this)
        }
    }

    protected inline fun <T> observeNavigationResult(
        key: String,
        crossinline listener: (T) -> Unit
    ) {
        val observer = Observer<T> {
            it?.let(listener)
        }
        val liveData =
            findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<T>(key)
        liveData?.observe(this) {
            observer.onChanged(it)
            liveData.removeObserver(observer)
        }
    }

    protected fun <T> setNavigationResult(key: String, result: T?) {
        findNavController().previousBackStackEntry?.savedStateHandle?.set(key, result)
    }

}