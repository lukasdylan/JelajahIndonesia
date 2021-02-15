package id.lukasdylan.jelajahindonesia.widget

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.mapbox.mapboxsdk.maps.MapView
import java.lang.ref.WeakReference

/**
 * Created by Lukas Dylan on 08/11/20.
 */
class MapViewLifecycleObserver(mapView: MapView) : DefaultLifecycleObserver {

    private val mapViewReference = WeakReference(mapView)

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        mapViewReference.get()?.onStart()
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        mapViewReference.get()?.onResume()
    }

    override fun onPause(owner: LifecycleOwner) {
        mapViewReference.get()?.onPause()
        super.onPause(owner)
    }

    override fun onStop(owner: LifecycleOwner) {
        mapViewReference.get()?.onStop()
        super.onStop(owner)
    }

    override fun onDestroy(owner: LifecycleOwner) {
        mapViewReference.get()?.onDestroy()
        mapViewReference.clear()
        super.onDestroy(owner)
    }
}