package com.ardat.eventmanagement.itemPeralatan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ardat.eventmanagement.Fragment.EquipmentFragment
import com.ardat.eventmanagement.MainActivity
import com.ardat.eventmanagement.R
import com.ardat.eventmanagement.model.ModelPeralatan
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_create_peralatan.*

class CreatePeralatan : AppCompatActivity() {
    private val ref : DatabaseReference = FirebaseDatabase.getInstance().getReference()
    private val auth : FirebaseAuth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_peralatan)
        when {
            edtNamaAlat.text.isEmpty() -> edtNamaAlat.error = "required"
            edtJumlahUnit.text.isEmpty() -> edtNamaAlat.error = "required"
            edtStatus.selectedItem.toString() == "status" -> Toast.makeText(
                this,
                "status required",
                Toast.LENGTH_SHORT
            ).show()
            edtBagian.text.isEmpty() -> edtBagian.error = "required"
            else -> {
                btnInsert.setOnClickListener {
                    insertPeralatan(
                        edtNamaAlat.text.toString(),
                        edtJumlahUnit.text.toString(),
                        edtStatus.selectedItem.toString(),
                        edtBagian.text.toString()
                    )
                }
            }
        }
    }

    private fun insertPeralatan(nama: String, jumlah: String, status: String, bagian: String) {
        var userId = auth?.getCurrentUser()?.getUid().toString()
        var modelPeralatan : ModelPeralatan = ModelPeralatan(nama,jumlah,status,bagian)
        ref.child(userId).child("Peralatan").push().setValue(modelPeralatan).addOnCompleteListener {
            Toast.makeText(this,"berhasil",Toast.LENGTH_SHORT).show()
            startActivity(Intent(this,EquipmentFragment::class.java))
        }
    }

}




