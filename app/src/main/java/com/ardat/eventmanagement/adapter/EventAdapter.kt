package com.ardat.eventmanagement.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ardat.eventmanagement.R
import com.ardat.eventmanagement.model.Event
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
        }
    }

    internal fun setEvent(eventList : List<Event>){
        this.eventList = eventList
        notifyDataSetChanged()
    }
}