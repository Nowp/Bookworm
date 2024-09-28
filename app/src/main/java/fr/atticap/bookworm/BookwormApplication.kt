package fr.atticap.bookworm

import android.app.Application
import fr.atticap.bookworm.persistence.di.persistenceModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class BookwormApplication: Application() {
    override fun onCreate() {
        super.onCreate()


        startKoin {
            // Log Koin into Android logger
            androidLogger()
            // Reference Android context
            androidContext(this@BookwormApplication)

            modules(persistenceModule())
        }
    }
}