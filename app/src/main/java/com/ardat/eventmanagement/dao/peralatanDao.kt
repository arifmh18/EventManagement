package com.ardat.eventmanagement.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ardat.eventmanagement.model.ModelPeralatan
@Dao
interface peralatanDao {
    @Query("SELECT * from peralatan")
    fun getAllMyFriend(): LiveData<List<ModelPeralatan>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(peralatan: ModelPeralatan)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(peralatan: List<ModelPeralatan>)
    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(peralatan: ModelPeralatan)
    @Delete()
    suspend fun delete(peralatan: ModelPeralatan)
    @Query("DELETE FROM peralatan")
    suspend fun deleteAll()
}

