package com.ardat.eventmanagement.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "peralatan")
data class ModelPeralatan(var nama : String, var jumlahUnit : String, var status:String, var bagian :String,
                          @PrimaryKey var key : String){
    constructor() : this("","","","",""
    )
}