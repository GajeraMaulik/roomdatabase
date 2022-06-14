package com.example.roomdatabase

import android.content.Context
import androidx.room.*


@Database(entities = [Person::class], version = 1, exportSchema = false)
abstract class PersonDatabase: RoomDatabase() {
    companion object {
        fun getInstance(context: Context): PersonDatabase {
            return Room.databaseBuilder(context, PersonDatabase::class.java, "db")
                .fallbackToDestructiveMigration().build()
        }
    }


    abstract fun getPersonDao(): PersonDao

}