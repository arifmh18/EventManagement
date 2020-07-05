package com.coding.smkcoding_project_2.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ardat.eventmanagement.Team.model.TeamModel
import com.coding.smkcoding_project_2.dao.TeamDao

@Database(entities = arrayOf(TeamModel::class), version = 1, exportSchema = false)
public abstract class TeamDatabase : RoomDatabase() {

    abstract fun teaDao(): TeamDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: TeamDatabase? = null

        fun getDatabase(context: Context): TeamDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TeamDatabase::class.java,
                    "db.team"
                ).
                build()
                INSTANCE = instance
                return instance
            }
        }
    }
}

