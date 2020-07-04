package com.ardat.eventmanagement.itemPeralatan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import com.ardat.eventmanagement.R
import com.ardat.eventmanagement.model.ModelPeralatan
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import kotlinx.android.synthetic.main.activity_update.*

class UpdateActivity : AppCompatActivity() {
    private var namaAlat : EditText? =null
    private var jumlahUnit : EditText? = null
    private var bagian : EditText? = null
    private var btnUpdate : Button? = null
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
        btnUpdate = findViewById(R.id.update)

        cekNamaAlat =namaAlat?.getText().toString()
        cekJumlahUnit = jumlahUnit?.getText().toString()
        cekBagian = bagian?.getText().toString()
        cekStatus = findViewById<Spinner>(R.id.newStatus).selectedItem.toString()
        btnUpdate?.setOnClickListener {
            if (cekNamaAlat!!.isEmpty()||cekJumlahUnit!!.isEmpty()||cekBagian!!.isEmpty()){
                if (cekStatus!! == "pilih"){
                    Toast.makeText(this,"data tidak boleh ada yang kosong",Toast.LENGTH_SHORT).show()
                }
            }else {
                updateData(cekNamaAlat!!, cekJumlahUnit!!, cekBagian!!, cekStatus!!)
            }
        }
    }

    private fun updateData(cekNamaAlat: String, cekJumlahUnit: String, cekBagian: String, cekStatus: String) {
        val getKey: String = getIntent().getStringExtra("idKey").toString()
        val temanBaru = ModelPeralatan(cekNamaAlat!!, cekJumlahUnit!!, cekBagian!!,
            cekStatus!!, getKey)
        val getUserID: String = auth?.getCurrentUser()?.getUid().toString()
        database!!.child(getUserID).child("Peralatan")
            .child(getKey).setValue(temanBaru)
            .addOnCompleteListener {
                finish()
            }
    }


}