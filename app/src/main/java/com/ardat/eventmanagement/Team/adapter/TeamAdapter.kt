package com.ardat.eventmanagement.Team.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.ardat.eventmanagement.R
import com.ardat.eventmanagement.Team.UpdateTeamActivity
import com.ardat.eventmanagement.Team.model.TeamModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_team_layout.*
import kotlin.collections.ArrayList

class TeamAdapter (private val context: Context, private val list: MutableList<TeamModel>) :
    RecyclerView.Adapter<TeamAdapter.ViewHolder> (){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_team_layout, parent, false)
        )

    override fun getItemCount(): Int {
        return list.size
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(list as ArrayList<TeamModel>)

        val nama: String? = list[position].nama
        val email: String? = list[position].email
        val tlp: String? = list[position].telepon
        var ukuran: String? = list[position].ukuran_baju
        var bagian: String? = list[position].bagian

        lateinit var ref : DatabaseReference
        lateinit var auth: FirebaseAuth

        holder.cvItemTeam.setOnClickListener(View.OnClickListener {
            val bundle = Bundle()
            bundle.putString("dataNama", nama)
            bundle.putString("dataEmail", email)
            bundle.putString("dataTlp", tlp)
            bundle.putString("dataUkr", ukuran)
            bundle.putString("dataBgn", bagian)
            bundle.putString("getPrimaryKey", list[position].key)
//            val intent = Intent(context, DetailTutorialActivity::class.java)
//            intent.putExtras(bundle)
//            context.startActivity(intent)
        })

        holder.cvItemTeam.setOnLongClickListener(View.OnLongClickListener { view ->
            val action = arrayOf("Update", "Delete")
            val alert = AlertDialog.Builder(context)
            alert.setItems(action) { dialog, i ->
                when (i) {
                    0 -> {
                        val bundle = Bundle()
                        bundle.putString("dataNama", nama)
                        bundle.putString("dataEmail", email)
                        bundle.putString("dataTlp", tlp)
                        bundle.putString("dataUkr", ukuran)
                        bundle.putString("dataBgn", bagian)
                        bundle.putString("getPrimaryKey", list[position].key)
                        val intent = Intent(context, UpdateTeamActivity::class.java)
                        intent.putExtras(bundle)
                        context.startActivity(intent)
                    }
                    1 -> {
                        auth = FirebaseAuth.getInstance()
                        ref = FirebaseDatabase.getInstance().reference
                        if (ref != null) {
                            ref.child("Team")
                                .child(list[position].key)
                                .removeValue()
                                .addOnSuccessListener {
                                    Toast.makeText(context,"Data Berhasil Dihapus",Toast.LENGTH_SHORT).show()
                                }
                        }
                    }
                }
            }
            alert.create()
            alert.show()
            true
        })
    }

    class ViewHolder (override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {
        fun bindItem(item: ArrayList<TeamModel>){
            nameTeam.text = item[position].nama
            emailTeam.text = item[position].email
            tlpTeam.text = item[position].telepon
            sizeTeam.text = item[position].ukuran_baju
            sectionTeam.text = item[position].bagian
        }
    }

}