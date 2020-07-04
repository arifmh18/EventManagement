package com.ardat.eventmanagement.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.recyclerview.widget.LinearLayoutManager
import com.ardat.eventmanagement.R
import com.ardat.eventmanagement.adapter.EquipmentAdapter
import com.ardat.eventmanagement.itemPeralatan.CreatePeralatan
import com.ardat.eventmanagement.model.ModelPeralatan
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_equipment.*
import kotlinx.android.synthetic.main.fragment_equipment.view.*

class EquipmentFragment : Fragment() {
    lateinit var ref : DatabaseReference
    lateinit var auth: FirebaseAuth
    var dataPeralatan: MutableList<ModelPeralatan> = ArrayList()
    private var adapter: EquipmentAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_equipment, container, false)
    }

    override fun onViewCreated(view: View, @Nullable savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getdata()
        view.insertData.setOnClickListener {
            startActivity(Intent(view.context,CreatePeralatan::class.java))
        }

    }

    private fun getdata() {
        auth = FirebaseAuth.getInstance()
        val getUserID: String = auth?.getCurrentUser()?.getUid().toString()
        ref = FirebaseDatabase.getInstance().getReference()
        ref.child(getUserID).child("Peralatan").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(getContext(), "Database Error yaa...", Toast.LENGTH_LONG).show()
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                dataPeralatan = ArrayList()
                for (snapshot in snapshot.children) {
                    //Mapping data pada DataSnapshot ke dalam objek mahasiswa
                    var peralatan = snapshot.getValue(ModelPeralatan::class.java)
                    //Mengambil Primary Key, digunakan untuk proses Update dan Delete
                    peralatan?.key = (snapshot.key!!)
                    dataPeralatan.add(peralatan!!)
                }
                listPeralatan.layoutManager =LinearLayoutManager(context)
                listPeralatan.adapter = EquipmentAdapter(requireContext(),dataPeralatan)
            }
        })
    }
    override fun onDestroy() {
        super.onDestroy()
        this.clearFindViewByIdCache()
    }

}