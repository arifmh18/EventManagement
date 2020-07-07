package com.ardat.eventmanagement.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ardat.eventmanagement.db.peralatanDAtabase
import com.ardat.eventmanagement.model.ModelPeralatan
import com.ardat.eventmanagement.repo.peralatanRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class peralatanUpdateViewModel() : ViewModel() {
    lateinit var repository: peralatanRepo

    fun init(context: Context) {
        val myFriendDao = peralatanDAtabase.getDatabase(context).peralatandao()
        repository = peralatanRepo(myFriendDao)
    }

    fun updateData(myFriend: ModelPeralatan) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(myFriend)
    }

}