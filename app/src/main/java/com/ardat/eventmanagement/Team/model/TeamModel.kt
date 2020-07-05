package com.ardat.eventmanagement.Team.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tb_team")
data class TeamModel(
    var nama: String,
    var email: String,
    var telepon: String,
    var ukuran_baju: String,
    var bagian: String,
    @PrimaryKey var key: String
) {
    constructor() : this("","","","","","")
}