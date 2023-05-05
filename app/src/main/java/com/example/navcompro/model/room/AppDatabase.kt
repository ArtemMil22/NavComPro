package com.example.navcompro.model.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.navcompro.model.accounts.room.AccountsDao
import com.example.navcompro.model.accounts.room.entities.AccountDbEntity
import com.example.navcompro.model.boxes.room.BoxesDao
import com.example.navcompro.model.boxes.room.entities.AccountBoxSettingDbEntity
import com.example.navcompro.model.boxes.room.entities.BoxDbEntity

//  Create a database class by extending RoomDatabase.
//  - use 'views' parameter to list all views (classes annotated with @DatabaseView)
@Database(
    version = 1,
    entities = [
        AccountDbEntity::class,
        BoxDbEntity::class,
        AccountBoxSettingDbEntity::class
    ]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getAccountsDao(): AccountsDao

    abstract fun getBoxesDao():BoxesDao

}