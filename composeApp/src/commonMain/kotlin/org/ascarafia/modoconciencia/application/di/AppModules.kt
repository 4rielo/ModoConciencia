package org.ascarafia.modoconciencia.application.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import org.ascarafia.modoconciencia.data.database.DataBaseFactory
import org.ascarafia.modoconciencia.data.database.LogDatabase
import org.ascarafia.modoconciencia.domain.repository.LogRepository
import org.ascarafia.modoconciencia.data.repository.LogRepositoryImpl
import org.ascarafia.modoconciencia.ui.main_screen.MainViewModel
import org.ascarafia.modoconciencia.ui.log_list.LogListViewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.core.module.dsl.*
import org.koin.dsl.bind

val appModules: List<Module> get() = sharedModules + platformModule

expect val platformModule: Module

val sharedModules: List<Module> get() = listOf( viewModelsModule, dataBaseModule, repositoryModule )

val viewModelsModule = module {
    viewModelOf(::LogListViewModel)
    viewModelOf(::MainViewModel)
}

val dataBaseModule = module {
    single {
        get<DataBaseFactory>().create()
            .setDriver(BundledSQLiteDriver())
            .build()
    }
    single {
        get<LogDatabase>().logDao
    }
}

val repositoryModule = module {
    singleOf(::LogRepositoryImpl).bind<LogRepository>()
}