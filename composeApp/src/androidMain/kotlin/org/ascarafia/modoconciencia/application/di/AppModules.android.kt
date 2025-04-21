package org.ascarafia.modoconciencia.application.di

import org.ascarafia.modoconciencia.application.location.LocationProviderFactory
import org.ascarafia.modoconciencia.application.mp3player.AudioPlayer
import org.ascarafia.modoconciencia.data.database.DataBaseFactory
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.Module
import org.koin.dsl.module

@OptIn(ExperimentalResourceApi::class)
actual val platformModule: Module
    get() = module {
        single { DataBaseFactory(androidApplication()) }
        single { LocationProviderFactory() }
        single { AudioPlayer(androidApplication()) }
    }