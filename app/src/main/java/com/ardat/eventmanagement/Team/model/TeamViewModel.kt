package com.ardat.eventmanagement.Team.model

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ardat.eventmanagement.Team.repository.TeamRepository
import com.coding.smkcoding_project_2.db.TeamDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TeamViewModel() : ViewModel() {

    lateinit var repository: TeamRepository

    fun init(context: Context) {
        val teamDao = TeamDatabase.getDatabase(context).teaDao()
        repository = TeamRepository(teamDao)
    }

    fun addData(teamX: TeamModel) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(teamX)
    }

}