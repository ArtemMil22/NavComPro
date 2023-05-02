package com.example.navcompro.model.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.navcompro.model.accounts.room.entities.AccountDbEntity

//  Create a database class by extending RoomDatabase.
//  - use 'views' parameter to list all views (classes annotated with @DatabaseView)
@Database(
    version = 1,
    entities = [
        AccountDbEntity::class
    ]
)
abstract class AppDatabase : RoomDatabase() {

    // todo #10: Add abstract getAccountsDao() method

    // todo #18: Add abstract getBoxesDao() method

}