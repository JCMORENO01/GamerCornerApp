
package com.example.gamercornerapp.data.datasource

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.core.content.ContextCompat
import androidx.core.location.LocationManagerCompat
import androidx.core.os.CancellationSignal
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withTimeoutOrNull
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.math.round

class ReviewLocationDataSource @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val locationManager: LocationManager =
        context.getSystemService(
            Context.LOCATION_SERVICE
        ) as LocationManager

    @SuppressLint("MissingPermission")
    suspend fun getCurrentLocation(): Location? {

        val hasFine = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val hasCoarse = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (!hasFine && !hasCoarse) {
            return null
        }

        val providers = listOf(
            LocationManager.NETWORK_PROVIDER,
            LocationManager.GPS_PROVIDER
        )
        
        // 1. Intentar obtener la última ubicación conocida (rápido)
        for (provider in providers) {
            if (provider == LocationManager.GPS_PROVIDER && !hasFine) {
                continue
            }
            
            try {
                if (!locationManager.isProviderEnabled(provider)) continue
                
                val lastKnown = locationManager.getLastKnownLocation(provider)
                if (lastKnown != null) {
                    // Si es de los últimos 15 minutos, usarla
                    val age = System.currentTimeMillis() - lastKnown.time
                    if (age < 15 * 60 * 1000) {
                        return approximateLocation(lastKnown)
                    }
                }
            } catch (_: Exception) {
                // Ignore
            }
        }

        // 2. Si no hay ubicación reciente, pedir una actual (con timeout más corto)
        for (provider in providers) {

            if (
                provider == LocationManager.GPS_PROVIDER &&
                !hasFine
            ) {
                continue
            }

            val isEnabled = try {
                locationManager.isProviderEnabled(provider)
            } catch (_: Exception) {
                false
            }

            if (!isEnabled) {
                continue
            }

            val location = try {

                withTimeoutOrNull(3_000L) { // Timeout reducido a 3 segundos por proveedor

                    suspendCancellableCoroutine<Location?> {
                            continuation ->

                        val signal = CancellationSignal()

                        continuation.invokeOnCancellation {
                            signal.cancel()
                        }

                        LocationManagerCompat.getCurrentLocation(
                            locationManager,
                            provider,
                            signal,
                            ContextCompat.getMainExecutor(context)
                        ) { result ->

                            if (continuation.isActive) {
                                continuation.resume(result)
                            }
                        }
                    }
                }

            } catch (_: SecurityException) {
                null
            } catch (_: Exception) {
                null
            }

            if (location != null) {
                return approximateLocation(location)
            }
        }

        return null
    }

    private fun approximateLocation(
        location: Location
    ): Location {

        val approximate = Location(location)

        approximate.latitude =
            round(location.latitude * 100.0) / 100.0

        approximate.longitude =
            round(location.longitude * 100.0) / 100.0

        return approximate
    }
}
