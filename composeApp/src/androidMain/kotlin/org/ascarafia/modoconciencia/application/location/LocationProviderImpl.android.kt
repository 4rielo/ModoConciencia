package org.ascarafia.modoconciencia.application.location

import dev.jordond.compass.Location
import dev.jordond.compass.geolocation.Geolocator
import dev.jordond.compass.geolocation.GeolocatorResult
import dev.jordond.compass.geolocation.mobile

actual class LocationProviderImpl : LocationProvider {
    val geolocator: Geolocator = Geolocator.mobile()

    override suspend fun getCurrentLocation(): Location? {
        val result: GeolocatorResult = geolocator.current()
        when (result) {
            is GeolocatorResult.Success -> {
                return result.data
            }

            is GeolocatorResult.Error -> {
                return null
            }
        }
    }
}