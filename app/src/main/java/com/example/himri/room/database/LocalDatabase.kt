package com.example.himri.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.himri.room.dao.HistoryDao
import com.example.himri.room.dao.UserDao
import com.example.himri.room.entity.Recipe
import com.example.himri.room.entity.User
import com.example.himri.room.typeConverters.ListConverter

//[..., Recipe::class]
@Database(entities = [User::class, Recipe::class], version = 3, exportSchema = false)
@TypeConverters(ListConverter::class)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun userDAO() : UserDao
    abstract fun historyDAO() : HistoryDao

    companion object {
        private var INSTANCE: LocalDatabase? = null
        fun getInstance(context: Context): LocalDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context, LocalDatabase::class.java, "himriDB.db")
                    // add migrations here
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE as LocalDatabase
        }
    }

}