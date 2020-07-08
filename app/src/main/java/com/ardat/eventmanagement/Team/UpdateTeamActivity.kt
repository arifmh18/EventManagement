package com.ardat.eventmanagement.Team

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import com.ardat.eventmanagement.R
import com.ardat.eventmanagement.Team.model.TeamModel
import com.ardat.eventmanagement.Team.model.TeamUpdateViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_update_team.*

class UpdateTeamActivity : AppCompatActivity() {

    private var newName: EditText? = null
    private var newEmail: EditText? = null
    private var newTelepon: EditText? = null
    private var newUkuran: EditText? = null
    private var newBagian: EditText? = null
    lateinit var update: Button
    private var database: DatabaseReference? = null
    private var auth: FirebaseAuth? = null
    private var cekName: String? = null
    private var cekEmail: String? = null
    private var cekTelepon: String? = null
    private var cekUkuran: String? = null
    private var cekBagian: String? = null
    private val viewModel by viewModels<TeamUpdateViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_team)
        supportActionBar?.title = "Update Team"

        viewModel.init(this)

        newName = etNameTeamNew
        newEmail = etEmailTeamNew
        newTelepon = etTelpTeamNew
        newUkuran = etSizeShirtTeamNew
        newBagian = etSectionTeamNew
        update = btnUpdateDataTeam

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference
        getData()

        update.setOnClickListener{
            cekName = newName?.text.toString()
            cekEmail = newEmail?.text.toString()
            cekTelepon = newTelepon?.text.toString()
            cekUkuran = newUkuran?.text.toString()
            cekBagian = newBagian?.text.toString()

            if (TextUtils.isEmpty(cekName) || TextUtils.isEmpty(cekEmail) || TextUtils.isEmpty(
                    cekTelepon) || TextUtils.isEmpty(cekUkuran) || TextUtils.isEmpty(cekBagian)
            ) {
                Toast.makeText(this, "Data tidak boleh ada yang kosong", Toast.LENGTH_SHORT).show()
            } else {
                val getKey: String = intent.getStringExtra("getPrimaryKey").toString()
                val teamBaru = TeamModel(cekName!!, cekEmail!!, cekTelepon!!, cekUkuran!!, cekBagian!!, getKey)
                database!!.child("Team")
                    .child(getKey).setValue(teamBaru)
                    .addOnCompleteListener {
                        viewModel.updateData(teamBaru)
                        Toast.makeText(this, "Data Berhasil Disimpan", Toast.LENGTH_SHORT).show()
                    }
                viewModel.updateData(teamBaru)
                finish()
            }
        }
    }

    private fun getData() {
        val getNama: String  = intent.getStringExtra("dataNama").toString()
        val getEmail: String  = intent.extras?.getString("dataEmail").toString()
        val getTelp: String  = intent.extras?.getString("dataTlp").toString()
        val getUkuran: String  = intent.extras?.getString("dataUkr").toString()
        val getBagian: String  = intent.extras?.getString("dataBgn").toString()

        newName?.setText(getNama)
        newEmail?.setText(getEmail)
        newTelepon?.setText(getTelp)
        newUkuran?.setText(getUkuran)
        newBagian?.setText(getBagian)
        Toast.makeText(this, getNama, Toast.LENGTH_SHORT).show()

    }
}