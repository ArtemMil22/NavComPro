package com.example.navcompro.model.room

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.navcompro.model.accounts.room.AccountsDao
import com.example.navcompro.model.accounts.room.entities.AccountDbEntity
import com.example.navcompro.model.boxes.room.BoxesDao
import com.example.navcompro.model.boxes.room.entities.AccountBoxSettingDbEntity
import com.example.navcompro.model.boxes.room.entities.BoxDbEntity
import com.example.navcompro.model.boxes.room.views.SettingDbView

//  Create a database class by extending RoomDatabase.
//  - use 'views' parameter to list all views (classes annotated with @DatabaseView)
@Database(
    version = 3,
    entities = [
        AccountDbEntity::class,
        BoxDbEntity::class,
        AccountBoxSettingDbEntity::class
    ],
    views = [SettingDbView::class],
    autoMigrations = [
        AutoMigration(
            from = 1,
            to =2,
            spec = AutoMigrationSpec1To2::class
        )
    ]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getAccountsDao(): AccountsDao

    abstract fun getBoxesDao():BoxesDao

}