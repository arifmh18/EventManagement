package com.coding.smkcoding_project_2.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ardat.eventmanagement.Team.model.TeamModel

@Dao
interface TeamDao {
    @Query("SELECT * from tb_team")
    fun getAllTeam(): LiveData<List<TeamModel>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(team: TeamModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(team: List<TeamModel>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(team: TeamModel)

    @Delete()
    suspend fun delete(team: TeamModel)

    @Query("DELETE FROM tb_team")
    suspend fun deleteAll()
}