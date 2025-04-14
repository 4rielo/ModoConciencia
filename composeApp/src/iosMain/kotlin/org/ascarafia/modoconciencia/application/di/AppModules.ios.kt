package org.ascarafia.modoconciencia.application.di

import org.ascarafia.modoconciencia.application.location.LocationProviderFactory
import org.ascarafia.modoconciencia.data.database.DataBaseFactory
import org.koin.core.module.Module
import org.koin.dsl.module


actual val platformModule: Module
    get() = module {
        single { DataBaseFactory() }
        single { LocationProviderFactory() }
    }