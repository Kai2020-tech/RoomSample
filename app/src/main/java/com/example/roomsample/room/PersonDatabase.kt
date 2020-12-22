package com.example.roomsample.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Person::class], version = 1, exportSchema = false)
abstract class PersonDatabase : RoomDatabase() {

    abstract val personDataDao: PersonDataDao

    companion object {

        @Volatile
        private var INSTANCE: PersonDatabase? = null

        fun getInstance(context: Context): PersonDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        PersonDatabase::class.java,
                        "person_database"
                    )
                        .allowMainThreadQueries()   //通常不建議在主執行緒中這樣做
                        .fallbackToDestructiveMigration()
                        .build()
//                    如果INSTANCE是空,透過instance建立之後,再丟給INSTANCE
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}