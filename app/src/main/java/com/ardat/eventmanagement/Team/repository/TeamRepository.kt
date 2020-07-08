package com.ardat.eventmanagement.Team.repository

import androidx.lifecycle.LiveData
import com.ardat.eventmanagement.Team.model.TeamModel
import com.coding.smkcoding_project_2.dao.TeamDao

class TeamRepository(private val teamDao: TeamDao) {

    val allTutorial: LiveData<List<TeamModel>> = teamDao.getAllTeam()

    suspend fun insert(team: TeamModel) {
        teamDao.insert(team)
    }

    suspend fun insertAll(team: List<TeamModel>) {
        teamDao.insertAll(team)
    }

    suspend fun deleteAll() {
        teamDao.deleteAll()
    }

    suspend fun update(team: TeamModel) {
        teamDao.update(team)
    }

    suspend fun delete(team: TeamModel) {
        teamDao.delete(team)
    }
}