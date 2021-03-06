package com.ardat.eventmanagement.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.ardat.eventmanagement.AddEventActivity
import com.ardat.eventmanagement.DetailEventActivity
import com.ardat.eventmanagement.R
import com.ardat.eventmanagement.model.Event
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.layout_item_event.*

class EventAdapter (private val context: Context) : RecyclerView.Adapter<EventAdapter.ViewHolder> () {

    private var eventList = emptyList<Event>()
    var eventListener: EventListener? = null

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

        val item = eventList[position]

        holder.ivMenuItemEvent.setOnClickListener {
            val popupMenu = PopupMenu(context, holder.ivMenuItemEvent)
            popupMenu.inflate(R.menu.menu_item_event)
            popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener {
                return@OnMenuItemClickListener when (it.itemId){
                    R.id.menuEventUpdate -> {

                        val intent = Intent(context, AddEventActivity::class.java)
                        intent.putExtra(AddEventActivity.EVENT_NAME, item.eventName)
                        intent.putExtra(AddEventActivity.EVENT_DATE, item.eventDate)
                        intent.putExtra(AddEventActivity.EVENT_INFO, item.eventInfo)
                        intent.putExtra(AddEventActivity.EVENT_UID, item.eventUid)
                        intent.putExtra(AddEventActivity.OPERATION_TYPE, "UPDATE")
                        context.startActivity(intent)

                        true
                    }
                    R.id.menuEventDelete -> {
                        eventListener?.deleteEvent(eventList[position])
                        true
                    }
                    else -> false
                }
            })
            popupMenu.show()
        }
    }

    class ViewHolder (override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer{

        fun bindItem(item : Event){
            tvEventNameItemEvent.text = item.eventName
            tvEventInfoEventItem.text = item.eventInfo
            tvEventDateEventItem.text = item.eventDate

//            val database = FirebaseDatabase.getInstance()
//            val databaseReference = database.getReference("event")

            containerView.setOnClickListener {
                val intent = Intent(containerView.context, DetailEventActivity::class.java)
                intent.putExtra(DetailEventActivity.EVENT_NAME, item.eventName)
                intent.putExtra(DetailEventActivity.EVENT_DATE, item.eventDate)
                intent.putExtra(DetailEventActivity.EVENT_INFO, item.eventInfo)
                containerView.context.startActivity(intent)
            }
        }
    }

    internal fun setEvent(eventList : List<Event>){
        this.eventList = eventList
        notifyDataSetChanged()
    }

    interface EventListener{
        fun deleteEvent(event: Event)
    }
}