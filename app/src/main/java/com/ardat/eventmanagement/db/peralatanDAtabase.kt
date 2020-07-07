package com.ardat.eventmanagement.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ardat.eventmanagement.dao.peralatanDao
import com.ardat.eventmanagement.model.ModelPeralatan

@Database(entities = arrayOf(ModelPeralatan::class), version = 1, exportSchema = false)

public abstract  class peralatanDAtabase:RoomDatabase() {
    abstract fun peralatandao(): peralatanDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: peralatanDAtabase? = null

        fun getDatabase(context: Context): peralatanDAtabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    peralatanDAtabase::class.java,
                    "peralatan_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }


}