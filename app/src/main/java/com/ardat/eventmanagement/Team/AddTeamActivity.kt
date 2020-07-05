package com.ardat.eventmanagement.Team

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import com.ardat.eventmanagement.R
import com.ardat.eventmanagement.Team.model.TeamModel
import com.ardat.eventmanagement.Team.model.TeamViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_add_team.*

class AddTeamActivity : AppCompatActivity() {

    private var nameTeam: EditText? = null
    private var emailTeam: EditText? = null
    private var telpTeam: EditText? = null
    private var sizeShirt: EditText? = null
    private var sectionTeam: EditText? = null
    lateinit var ref : DatabaseReference
    private var auth: FirebaseAuth? = null
    private val viewModel by viewModels<TeamViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_team)
        supportActionBar?.title = "Add Team Data"

        nameTeam = etNameTeam
        emailTeam = etEmailTeam
        telpTeam = etTelpTeam
        sizeShirt = etSizeShirtTeam
        sectionTeam = etSectionTeam

        viewModel.init(this)

        ref = FirebaseDatabase.getInstance().reference
        auth = FirebaseAuth.getInstance()

        btnSaveDataTeam.setOnClickListener {
            prosesSaveDataTeam()
        }
    }

    private fun prosesSaveDataTeam() {
        val getName: String = nameTeam?.text.toString()
        val getEmail:String = emailTeam?.text.toString()
        val getTlp: String = telpTeam?.text.toString()
        var getSShirt: String = sizeShirt?.text.toString()
        var getSection: String = sectionTeam?.text.toString()

        if (getName.isEmpty() && getEmail.isEmpty() && getTlp.isEmpty() && getSShirt.isEmpty() && getSection.isEmpty()) {
            Toast.makeText(this, "Data tidak boleh ada yang kosong", Toast.LENGTH_SHORT).show()
        } else {
            val dataTeam = TeamModel(getName, getEmail, getTlp, getSShirt, getSection," ")
            ref.child("Team").push().setValue(dataTeam).addOnCompleteListener {
                Toast.makeText(this, "Data Berhasil Disimpan", Toast.LENGTH_SHORT).show()
                nameTeam!!.setText("")
                emailTeam!!.setText("")
                telpTeam!!.setText("")
                sizeShirt!!.setText("")
                sectionTeam!!.setText("")

                dataTeam.key = ref.key.toString()
                viewModel.addData(dataTeam)
            }
            finish()
        }
    }
}