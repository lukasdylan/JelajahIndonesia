package id.lukasdylan.jelajahindonesia.repository

import android.annotation.SuppressLint
import android.location.Location
import android.os.Looper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import id.lukasdylan.jelajahindonesia.utils.DataResult
import id.lukasdylan.jelajahindonesia.utils.awaitResult
import id.lukasdylan.jelajahindonesia.utils.map
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * Created by Lukas Dylan on 12/01/21.
 */
interface LocationRepository {
    suspend fun getUserLocation(): DataResult<Location>
}

@SuppressLint("MissingPermission")
class LocationRepositoryImpl @Inject constructor(private val fusedLocationProviderClient: FusedLocationProviderClient) :
    LocationRepository {

    override suspend fun getUserLocation(): DataResult<Location> {
        return fusedLocationProviderClient.lastLocation.awaitResult().map {
            (it ?: (requestLocationUpdate() as DataResult.Success).data)
        }
    }

    private suspend fun requestLocationUpdate(): DataResult<Location> =
        suspendCoroutine { continuation ->
            val locationRequest = LocationRequest.create()
                .setNumUpdates(1)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(0)

            fusedLocationProviderClient.requestLocationUpdates(
                locationRequest,
                object : LocationCallback() {
                    override fun onLocationResult(locationResult: LocationResult?) {
                        super.onLocationResult(locationResult)
                        if (locationResult != null) {
                            continuation.resume(DataResult.Success(locationResult.lastLocation))
                        } else {
                            continuation.resume(DataResult.Failure(Throwable("Failed to update location")))
                        }
                    }
                },
                Looper.getMainLooper()
            )
        }
}