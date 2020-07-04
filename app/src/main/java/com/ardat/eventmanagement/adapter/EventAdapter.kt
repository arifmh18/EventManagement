package com.ardat.eventmanagement.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.ardat.eventmanagement.AddEventActivity
import com.ardat.eventmanagement.R
import com.ardat.eventmanagement.model.Event
import com.ardat.eventmanagement.utils.showToast
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.layout_item_event.*

class EventAdapter (private val context: Context) : RecyclerView.Adapter<EventAdapter.ViewHolder> () {

    private var eventList = emptyList<Event>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.layout_item_event,
                parent,
                false
            )
        )

    override fun getItemCount(): Int {
        return eventList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(eventList[position])
    }

    class ViewHolder (override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer{

        fun bindItem(item : Event){
            tvEventNameItemEvent.text = item.eventName
            tvEventInfoEventItem.text = item.eventInfo
            tvEventDateEventItem.text = item.eventDate

            val database = FirebaseDatabase.getInstance()
            val databaseReference = database.getReference("event")

            ivMenuItemEvent.setOnClickListener {
                val popupMenu = PopupMenu(containerView.context, ivMenuItemEvent)
                popupMenu.inflate(R.menu.menu_item_event)
                popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener {
                    return@OnMenuItemClickListener when (it.itemId){
                        R.id.menuEventUpdate -> {

                            val intent = Intent(containerView.context, AddEventActivity::class.java)
                            intent.putExtra(AddEventActivity.EVENT_NAME, item.eventName)
                            intent.putExtra(AddEventActivity.EVENT_DATE, item.eventDate)
                            intent.putExtra(AddEventActivity.EVENT_INFO, item.eventInfo)
                            intent.putExtra(AddEventActivity.EVENT_UID, item.eventUid)
                            intent.putExtra(AddEventActivity.OPERATION_TYPE, "UPDATE")
                            containerView.context.startActivity(intent)

                            true
                        }
                        R.id.menuEventDelete -> {
                            databaseReference.child(item.eventUid).removeValue()
                                .addOnSuccessListener {
                                    showToast(containerView.context, "Berhasil hapus event")

                                }
                                .addOnFailureListener {
                                    Log.e("event item", "onFailureListener : "+it.message)
                                }
                            true
                        }
                        else -> false
                    }
                })
                popupMenu.show()
            }
        }
    }

    internal fun setEvent(eventList : List<Event>){
        this.eventList = eventList
        notifyDataSetChanged()
    }
}