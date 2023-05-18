package com.example.navcompro

import android.content.Context
import androidx.room.Room
import com.example.navcompro.model.accounts.AccountsRepository
import com.example.navcompro.model.accounts.room.RoomAccountsRepository
import com.example.navcompro.model.boxes.BoxesRepository
import com.example.navcompro.model.boxes.room.RoomBoxesRepository
import com.example.navcompro.model.room.AppDatabase
import com.example.navcompro.model.room.MIGRATION_2_3
import com.example.navcompro.model.settings.AppSettings
import com.example.navcompro.model.settings.SharedPreferencesAppSettings
import com.example.navcompro.utils.security.DefaultSecurityUtilsImpl
import com.example.navcompro.utils.security.SecurityUtils
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

object Repositories {

    private lateinit var applicationContext: Context

    val securityUtils: SecurityUtils by lazy {DefaultSecurityUtilsImpl()}

    //  Create an AppDatabase instance by using Room.databaseBuilder static method.
    //  Use createFromAssets method to initialize
    //  a new database from the pre-packaged SQLite file from assets
    private val database: AppDatabase by lazy<AppDatabase> {
        Room.databaseBuilder(applicationContext,AppDatabase::class.java,"database.db")
            .createFromAsset("initial_database.db")
            .addMigrations(MIGRATION_2_3)
            .build()
    }

    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

    private val appSettings: AppSettings by lazy {
        SharedPreferencesAppSettings(applicationContext)
    }

    // --- repositories

    val accountsRepository: AccountsRepository by lazy {
        RoomAccountsRepository(database.getAccountsDao(), appSettings, securityUtils, ioDispatcher)
    }

    val boxesRepository: BoxesRepository by lazy {
        RoomBoxesRepository(accountsRepository, database.getBoxesDao(), ioDispatcher)
    }

    /**
     * Call this method in all application components that may be created at app startup/restoring
     * (e.g. in onCreate of activities and services)
     */
    fun init(context: Context) {
        applicationContext = context
    }
}