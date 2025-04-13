package org.ascarafia.modoconciencia


import android.app.Application
import org.ascarafia.modoconciencia.application.di.initKoin
import org.koin.android.ext.koin.androidContext

class ModoConcienciaApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidContext(this@ModoConcienciaApplication)
        }
    }
}