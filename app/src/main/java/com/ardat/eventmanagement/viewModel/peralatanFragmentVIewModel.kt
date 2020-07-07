package com.ardat.eventmanagement.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ardat.eventmanagement.db.peralatanDAtabase
import com.ardat.eventmanagement.model.ModelPeralatan
import com.ardat.eventmanagement.repo.peralatanRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class peralatanFragmentVIewModel():ViewModel() {
    lateinit var repository: peralatanRepo

    lateinit var allPeralatan: LiveData<List<ModelPeralatan>>

    fun init(context: Context) {
        val myFriendDao = peralatanDAtabase.getDatabase(context).peralatandao()
        repository = peralatanRepo(myFriendDao)
        allPeralatan = repository.allPeralatan
    }

    fun delete(myFriend: ModelPeralatan) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(myFriend)
    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insertAll(myFriends: List<ModelPeralatan>) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteAll()
        repository.insertAll(myFriends)
    }

}