package com.ardat.eventmanagement.Team.model

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ardat.eventmanagement.Team.model.TeamModel
import com.ardat.eventmanagement.Team.repository.TeamRepository
import com.coding.smkcoding_project_2.db.TeamDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TeamFragmentViewModel() : ViewModel() {

    lateinit var repository: TeamRepository

    lateinit var allTeams: LiveData<List<TeamModel>>

    fun init(context: Context) {
        val teamDao = TeamDatabase.getDatabase(context).teaDao()
        repository = TeamRepository(teamDao)
        allTeams = repository.allTutorial
    }

    fun delete(team: TeamModel) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(team)
    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insertAll(tutorial: List<TeamModel>) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteAll()
        repository.insertAll(tutorial)
    }
}