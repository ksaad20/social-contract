```kotlin
package com.socialcontract.data.database

import android.content.Context

/**
 * Provides the singleton Room database instance for the application.
 *
 * The database is created lazily and uses the application context so that
 * it does not accidentally retain an Activity or other short-lived Context.
 */
object DatabaseProvider {

    @Volatile
    private var INSTANCE: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
        return INSTANCE ?: synchronized(this) {
            INSTANCE ?: buildDatabase(
                context.applicationContext
            ).also { database ->
                INSTANCE = database
            }
        }
    }

    private fun buildDatabase(
        context: Context
    ): AppDatabase {
        return androidx.room.Room
            .databaseBuilder(
                context,
                AppDatabase::class.java,
                DATABASE_NAME
            )
            .fallbackToDestructiveMigration()
            .build()
    }

    private const val DATABASE_NAME = "social_contract.db"
}
```

