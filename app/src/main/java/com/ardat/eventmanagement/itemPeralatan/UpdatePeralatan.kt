package com.ardat.eventmanagement.itemPeralatan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

import androidx.activity.viewModels
import com.ardat.eventmanagement.MainActivity
import com.ardat.eventmanagement.R
import com.ardat.eventmanagement.model.ModelPeralatan
import com.ardat.eventmanagement.viewModel.peralatanUpdateViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_update.*
import kotlinx.android.synthetic.main.list_peralatan.*

class UpdatePeralatan : AppCompatActivity() {
    private var namaAlat : EditText? =null
    private var bagian : EditText? = null
    private var btnUpdate : Button? = null
    private var database: DatabaseReference? = null
    private var auth: FirebaseAuth? = null
    private var cekNamaAlat : String? =null
    private var cekJumlahUnit : String? = null
    private var cekStatus : String? = null
    private var cekBagian : String? = null
    private val viewModel by viewModels<peralatanUpdateViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        getdata()
        updateData.setOnClickListener {
            viewModel.init(this)
            namaAlat = findViewById(R.id.newNama)
            bagian = findViewById(R.id.newBagian)
            btnUpdate = findViewById(R.id.updateData)
            cekNamaAlat = newNama.getText().toString()
            cekJumlahUnit = newJumlahUnit.getText().toString()
            cekBagian = newBagian.getText().toString()
            cekStatus = newStatus.selectedItem.toString()
            val getKey: String = getIntent().getStringExtra("idKey").toString()

            Toast.makeText(this, getKey, Toast.LENGTH_SHORT).show()
            val temanBaru = ModelPeralatan(
                ubahHurufBesar(cekNamaAlat!!),
                cekJumlahUnit!!,
                ubahHurufBesar(cekStatus!!),
                ubahHurufBesar(cekBagian!!),
                getKey
            )
            val getUserID: String = auth?.getCurrentUser()?.getUid().toString()
            database = FirebaseDatabase.getInstance().getReference()
                    database!!.child(getUserID).child("Peralatan").child(getKey).setValue(temanBaru)
                        .addOnCompleteListener {
                            viewModel.updateData(temanBaru)
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        }
        }
    }
    private fun getdata(){
        namaAlat = findViewById(R.id.newNama)
        bagian = findViewById(R.id.newBagian)
            val getNama : String =getIntent().getStringExtra("nama").toString()
        val getJumlahUnit : String =getIntent().getStringExtra("jumlahUnit").toString()
        val getBagian : String =getIntent().getStringExtra("bagian").toString()
        namaAlat?.setText(getNama)
        newJumlahUnit.setText(getJumlahUnit)
        bagian?.setText(getBagian)
    }
    fun ubahHurufBesar(kalimat : String):String{
        if(kalimat.indexOf(" ")==-1){
            var ubahKeHurufBesarAwalKata = kalimat.substring(0,1).toUpperCase()
            var kataSetelahBesar1 = kalimat.substring(1,kalimat.length)
            var kata1 = ubahKeHurufBesarAwalKata+kataSetelahBesar1
            return kata1
        }else {
            var ambilHurufTerakhir = kalimat.indexOf(" ") + 1
            var ambilHurufTerakhir2 = kalimat.indexOf(" ") + 2
            var ubahKeHurufBesarAwalKata = kalimat.substring(0, 1).toUpperCase()
            var ubahKeHurufBesarSetelahSpasi =
                kalimat.substring(ambilHurufTerakhir, ambilHurufTerakhir2).toUpperCase()
            var kataSetelahBesar1 = kalimat.substring(1, kalimat.indexOf(" "))
            var kataSetelahBesar2 = kalimat.substring(kalimat.indexOf(" ") + 2, kalimat.length)
            var kata1 = ubahKeHurufBesarAwalKata + kataSetelahBesar1
            var kata2 = ubahKeHurufBesarSetelahSpasi + kataSetelahBesar2
            var kalimatJadi = kata1 + " " + kata2
            return kalimatJadi
        }
    }

}

