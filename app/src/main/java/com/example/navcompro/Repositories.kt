package com.example.navcompro

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.navcompro.model.accounts.AccountsRepository
import com.example.navcompro.model.accounts.SQLiteAccountsRepository
import com.example.navcompro.model.boxes.BoxesRepository
import com.example.navcompro.model.boxes.SQLiteBoxesRepository
import com.example.navcompro.model.settings.AppSettings
import com.example.navcompro.model.settings.SharedPreferencesAppSettings
import com.example.navcompro.model.sqlite.AppSQLiteHelper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

object Repositories {

    private lateinit var applicationContext: Context

    // -- stuffs
    // создание нашей БД
    private val database: SQLiteDatabase by lazy<SQLiteDatabase> {
        AppSQLiteHelper(applicationContext).writableDatabase
    }

    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

    private val appSettings: AppSettings by lazy {
        SharedPreferencesAppSettings(applicationContext)
    }

    // --- repositories

    val accountsRepository: AccountsRepository by lazy {
        SQLiteAccountsRepository(database, appSettings, ioDispatcher)
    }

    val boxesRepository: BoxesRepository by lazy {
        SQLiteBoxesRepository(database, accountsRepository, ioDispatcher)
    }

    fun init(context: Context) {
        applicationContext = context
    }
}