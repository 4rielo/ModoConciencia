package org.ascarafia.modoconciencia.application.location

import dev.jordond.compass.Location

interface LocationProvider {
    suspend fun getCurrentLocation(): Location?
}