package org.ascarafia.modoconciencia.application.location

expect class LocationProviderFactory {
    fun create(): LocationProvider
}