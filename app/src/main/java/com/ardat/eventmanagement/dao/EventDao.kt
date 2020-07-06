package com.ardat.eventmanagement.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ardat.eventmanagement.model.EventLocal

@Dao
interface EventDao {

    @Query("SELECT * FROM eventLocal WHERE eventQuery = 'select' AND eventSynced = 'true'")
    fun getAllEvent(): LiveData<List<EventLocal>>

    @Query("SELECT * FROM eventLocal WHERE eventQuery = 'update' AND eventSynced = 'false'")
    fun getPendingUpdatedEvent() : LiveData<List<EventLocal>>

    @Query("SELECT * FROM eventLocal WHERE eventQuery = 'delete' AND eventSynced = 'false'")
    fun getPendingDeletedEvent() : LiveData<List<EventLocal>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(event: EventLocal)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(event: EventLocal)

    @Delete()
    suspend fun delete(event: EventLocal)

    @Query("DELETE FROM eventLocal")
    suspend fun deleteAll()

}