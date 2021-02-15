package id.lukasdylan.jelajahindonesia.fragment

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import id.lukasdylan.jelajahindonesia.R
import id.lukasdylan.jelajahindonesia.databinding.FragmentDialogLocationPermissionBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Created by Lukas Dylan on 06/11/20.
 */
class LocationPermissionFragment :
    BaseBottomSheetDialogFragment(R.layout.fragment_dialog_location_permission) {

    private val locationPermissionBinding by viewBinding(
        FragmentDialogLocationPermissionBinding::bind
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        locationPermissionBinding.apply {
            viewLifecycleOwner.lifecycleScope.launch {
                delay(200)
                lottieView.playAnimation()
            }
            btnCancel.setOnClickListener {
                lottieView.cancelAnimation()
                dismiss()
                setNavigationResult(LOCATION_PERMISSION_RESULT_KEY, false)
            }
            btnGivePermission.setOnClickListener {
                lottieView.cancelAnimation()
                dismiss()
                setNavigationResult(LOCATION_PERMISSION_RESULT_KEY, true)
            }
        }
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        setNavigationResult(LOCATION_PERMISSION_RESULT_KEY, false)
    }

    companion object {
        const val LOCATION_PERMISSION_RESULT_KEY = "location_permission"
    }
}