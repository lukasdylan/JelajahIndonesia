package id.lukasdylan.jelajahindonesia.fragment

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import id.lukasdylan.jelajahindonesia.R
import id.lukasdylan.jelajahindonesia.utils.FragmentViewBindingDelegate

/**
 * Created by Lukas Dylan on 06/11/20.
 */
open class BaseBottomSheetDialogFragment(@LayoutRes private val layoutId: Int) :
    BottomSheetDialogFragment() {

    override fun getTheme(): Int = R.style.BottomSheetDialogTheme

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        BottomSheetDialog(requireContext(), theme).also {
            it.window?.attributes?.windowAnimations = R.style.BottomSheetDialogAnimation
            it.dismissWithAnimation = true
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(layoutId, container, false)

    protected fun <T> setNavigationResult(key: String, result: T?) {
        findNavController().previousBackStackEntry?.savedStateHandle?.set(key, result)
    }

    protected fun <T : ViewBinding> BottomSheetDialogFragment.viewBinding(viewBindingFactory: (View) -> T) =
        FragmentViewBindingDelegate(this, viewBindingFactory)
}