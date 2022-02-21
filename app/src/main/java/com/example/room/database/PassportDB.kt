package com.example.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.room.dao.CitizenDao
import com.example.room.entity.Citizen

@Database(entities = [Citizen::class], version = 2)
abstract class PassportDB : RoomDatabase() {
    abstract fun citizenDao(): CitizenDao

    companion object {
        private var instance: PassportDB? = null

        @Synchronized
        fun getInstance(context: Context): PassportDB {
            if (instance == null) {
                instance =
                    Room.databaseBuilder(context, PassportDB::class.java, "passport_database")
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build()
            }
            return instance!!
        }
    }
}