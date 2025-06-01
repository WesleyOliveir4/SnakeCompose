package com.example.snakecompose

import android.app.Application
import com.example.snakecompose.di.gameOverModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class MainApplication : Application()  {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            // Declare o contexto do Android para o Koin
            androidContext(this@MainApplication)
            // Declare seus módulos
            modules(
                gameOverModule
            )
        }
    }
}