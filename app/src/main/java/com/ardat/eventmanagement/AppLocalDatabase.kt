package com.ardat.eventmanagement

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ardat.eventmanagement.dao.EventDao
import com.ardat.eventmanagement.model.EventLocal

@Database(
    entities = [EventLocal::class],
    version = 1,
    exportSchema = true
)

abstract class AppLocalDatabase : RoomDatabase() {

    abstract fun eventDao(): EventDao

    companion object {

        @Volatile
        private var INSTANCE: AppLocalDatabase? = null

        fun getDatabase(context: Context): AppLocalDatabase {
            val tempInstance =
                INSTANCE
            if (tempInstance != null){
                return tempInstance
            }

            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppLocalDatabase::class.java,
                    "app_local_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                return instance
            }
        }
    }
}