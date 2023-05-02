package com.example.navcompro.model.sqlite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

//класс поможет создавать и инициализировать базу данных
class AppSQLiteHelper(
    private val applicationContext: Context
):SQLiteOpenHelper(applicationContext,"database.db",null,1) {

    override fun onCreate(db: SQLiteDatabase) {
        // все содержимое файла sql в этой переменной  - читаем)
        val sql = applicationContext.assets.open("bd_init.sql").bufferedReader().use {
            it.readText()
        }
            // разбиваем содержимое на отдельные объекты (понимает единичные инструкции -> ; (разделитель))
        sql.split(";")
            .filter { it.isNotBlank() }
            .forEach{
                //передаем инструкции на выполнение базе данных
                db.execSQL(it)
            }
    }

    //миграция из старой в актуальную
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
}