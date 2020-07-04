package com.ardat.eventmanagement.itemPeralatan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Spinner
import com.ardat.eventmanagement.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import kotlinx.android.synthetic.main.activity_update.*

class UpdateActivity : AppCompatActivity() {
    private var namaAlat : EditText? =null
    private var jumlahUnit : EditText? = null
    private var bagian : EditText? = null
    private var database: DatabaseReference? = null
    private var auth: FirebaseAuth? = null
    private var cekNamaAlat : String? =null
    private var cekJumlahUnit : String? = null
    private var cekStatus : String? = null
    private var cekBagian : String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)
        getSupportActionBar()?.setTitle("Update Data Peralatan")
        namaAlat = findViewById(R.id.newNama)
        jumlahUnit = findViewById(R.id.newJumlahUnit)
        bagian = findViewById(R.id.newBagian)
        
    }


}