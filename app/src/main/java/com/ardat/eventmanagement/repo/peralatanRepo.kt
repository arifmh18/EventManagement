package com.ardat.eventmanagement.repo

import android.view.Display
import androidx.lifecycle.LiveData
import com.ardat.eventmanagement.dao.peralatanDao
import com.ardat.eventmanagement.db.peralatanDAtabase
import com.ardat.eventmanagement.model.ModelPeralatan
import java.nio.file.Files.delete

class peralatanRepo(private val peralatanDao: peralatanDao) {
    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val allPeralatan: LiveData<List<ModelPeralatan>> = peralatanDao.getAllMyFriend()

    suspend fun insert(peralatan: ModelPeralatan) {
        peralatanDao.insert(peralatan)
    }

    suspend fun insertAll(myFriends: List<ModelPeralatan>) {
        peralatanDao.insertAll(myFriends)
    }

    suspend fun deleteAll() {
        peralatanDao.deleteAll()
    }

    suspend fun update(myFriend: ModelPeralatan) {
        peralatanDao.update(myFriend)
    }

    suspend fun delete(myFriend: ModelPeralatan) {
        peralatanDao.delete(myFriend)
    }
}

