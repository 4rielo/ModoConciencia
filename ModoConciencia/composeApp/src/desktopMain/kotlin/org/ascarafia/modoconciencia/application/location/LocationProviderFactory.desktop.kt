package org.ascarafia.modoconciencia.application.location

actual class LocationProviderFactory {
    actual fun create(): LocationProvider {
        return LocationProviderImpl()
    }
}