package org.ascarafia.modoconciencia.application.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import org.ascarafia.modoconciencia.application.location.LocationProvider
import org.ascarafia.modoconciencia.application.location.LocationProviderFactory
import org.ascarafia.modoconciencia.data.database.DataBaseFactory
import org.ascarafia.modoconciencia.data.database.TaskDatabase
import org.ascarafia.modoconciencia.domain.repository.TaskRepository
import org.ascarafia.modoconciencia.data.repository.TaskRepositoryImpl
import org.ascarafia.modoconciencia.ui.main_screen.MainViewModel
import org.ascarafia.modoconciencia.ui.task_list.TaskListViewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.core.module.dsl.*
import org.koin.dsl.bind

val appModules: List<Module> get() = sharedModules + platformModule

expect val platformModule: Module

val sharedModules: List<Module> get() = listOf( viewModelsModule, dataBaseModule, repositoryModule, hardwareModule )

val viewModelsModule = module {
    viewModelOf(::TaskListViewModel)
    viewModelOf(::MainViewModel)
}

val dataBaseModule = module {
    single {
        get<DataBaseFactory>().create()
            .setDriver(BundledSQLiteDriver())
            .build()
    }
    single {
        get<TaskDatabase>().taskDao
    }
}

val repositoryModule = module {
    singleOf(::TaskRepositoryImpl).bind<TaskRepository>()
}

val hardwareModule = module {
    single {
        get<LocationProviderFactory>().create()
    }.bind<LocationProvider>()
}