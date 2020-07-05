package com.ardat.eventmanagement.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.ardat.eventmanagement.AppLocalDatabase
import com.ardat.eventmanagement.model.EventLocal
import com.ardat.eventmanagement.repository.EventRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EventViewModel (application: Application) : AndroidViewModel(application){

    private val repository: EventRepository

    val eventData: LiveData<List<EventLocal>>

    init {
        val eventDao = AppLocalDatabase.getDatabase(application).eventDao()
        repository = EventRepository(eventDao)
        eventData = repository.eventData
    }

    fun insert(event: EventLocal) = viewModelScope.launch(Dispatchers.IO){
        repository.insert(event)
    }

    fun update(event: EventLocal) = viewModelScope.launch(Dispatchers.IO){
        repository.update(event)
    }

    fun delete(event: EventLocal) = viewModelScope.launch(Dispatchers.IO){
        repository.delete(event)
    }

    fun deleteAll() = viewModelScope.launch(Dispatchers.IO){
        repository.deleteAll()
    }

}