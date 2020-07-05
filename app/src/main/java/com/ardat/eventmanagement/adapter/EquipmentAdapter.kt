package com.ardat.eventmanagement.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.ardat.eventmanagement.R
import com.ardat.eventmanagement.itemPeralatan.UpdatePeralatan
import com.ardat.eventmanagement.model.ModelPeralatan
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.list_peralatan.*

class EquipmentAdapter(private var mctx : Context,private var list : List<ModelPeralatan>) : RecyclerView.Adapter<EquipmentAdapter.ViewHolder> (){
    var     listener: dataListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= ViewHolder(LayoutInflater.from(mctx).inflate(
        R.layout.list_peralatan,parent,false))

    override fun getItemCount(): Int {
        return list.size
    }
    fun setData(list: List<ModelPeralatan>){
        this.list = list
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(list.get(position))
        var auth : FirebaseAuth
        var ref : DatabaseReference
        auth = FirebaseAuth.getInstance()
        holder.listItem?.setOnLongClickListener {
            val action = arrayOf("Update", "Delete")
            var builder = AlertDialog.Builder(it.context)
            builder.setItems(action){dialog, which ->
                when(which){
                        0->{
                            val bundle = Bundle()
                            bundle.putString("nama",list.get(position).nama)
                            bundle.putString("jumlahUnit",list.get(position).jumlahUnit)
                            bundle.putString("status",list.get(position).status)
                            bundle.putString("bagian",list.get(position).bagian)
                            bundle.putString("idKey",list.get(position).key)
                            var intent = Intent(it.context,UpdatePeralatan::class.java)
                            intent.putExtras(bundle)
                            mctx.startActivity(intent)
                        }
                    1->{
                        auth = FirebaseAuth.getInstance()
                        ref = FirebaseDatabase.getInstance().getReference()
                        val getUserID: String = auth?.getCurrentUser()?.getUid().toString()
                        if (ref!=null){
                            ref.child(getUserID).child("Peralatan").child(list.get(position)?.key.toString()).removeValue().addOnSuccessListener {view->
                                Toast.makeText(it.context,"deleted",Toast.LENGTH_SHORT).show()
                            }
                        }else{
                            Toast.makeText(it.context,list.get(position).key.toString(),Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
            builder.setTitle("pilih")
            builder.create().show()
            true
        }


    }

    class ViewHolder (override val containerView : View): RecyclerView.ViewHolder(containerView),LayoutContainer {
        var listItem : LinearLayout? = null

        fun bindItem(item : ModelPeralatan){
            namaAlat.text = item.nama
            jumlahUnit.text = item.jumlahUnit
            status.text = item.status
            bagian.text =item.bagian
            listItem = itemView.findViewById(R.id.alertShow)
        }

    }
    interface dataListener {
        fun onDeleteData(data: ModelPeralatan, position: Int)
    }

}
