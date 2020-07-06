package com.ardat.eventmanagement.repository

import androidx.lifecycle.LiveData
import com.ardat.eventmanagement.dao.EventDao
import com.ardat.eventmanagement.model.EventLocal

class EventRepository (private val eventDao: EventDao){

    val eventData: LiveData<List<EventLocal>> = eventDao.getAllEvent()

    suspend fun insert(event: EventLocal){
        eventDao.insert(event)
    }

    suspend fun update(event: EventLocal){
        eventDao.update(event)
    }

    suspend fun delete(event: EventLocal){
        eventDao.delete(event)
    }

    suspend fun deleteAll(){
        eventDao.deleteAll()
    }

}