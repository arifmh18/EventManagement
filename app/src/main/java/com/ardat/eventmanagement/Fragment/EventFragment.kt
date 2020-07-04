package com.ardat.eventmanagement.Fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.ardat.eventmanagement.AddEventActivity
import com.ardat.eventmanagement.R
import com.ardat.eventmanagement.adapter.EventAdapter
import com.ardat.eventmanagement.model.Event
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_event.*

class EventFragment : Fragment() {

    private lateinit var databaseReference: DatabaseReference
    private var database = FirebaseDatabase.getInstance()

    private var eventList: MutableList<Event> = mutableListOf()

    private lateinit var adapter : EventAdapter

    companion object {
        var TAG = EventFragment::class.java.name
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_event, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {

        databaseReference = database.getReference("event")

        eventList = ArrayList()

        adapter = EventAdapter(requireContext())

        rvEvent.layoutManager = LinearLayoutManager(requireContext())
        rvEvent.adapter = adapter

        fabEvent.setOnClickListener {
            val intent = Intent(requireActivity(), AddEventActivity::class.java)
            intent.putExtra(AddEventActivity.OPERATION_TYPE, "INSERT")
            startActivity(intent)
        }

        getEventData()

    }

    private fun getEventData() {

        layoutEmptyStateEvent.visibility = View.GONE
        pbLoadEvent.visibility = View.VISIBLE

        databaseReference.addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    layoutEmptyStateEvent.visibility = View.GONE
                    pbLoadEvent.visibility = View.VISIBLE

                    eventList.clear()
                    for (data in dataSnapshot.children){

                        val event = data.getValue(Event::class.java)

                        event?.eventUid = data.key!!

                        eventList.add(event!!)

                    }

                    if(eventList.isEmpty()){
                        layoutEmptyStateEvent.visibility = View.VISIBLE
                        pbLoadEvent.visibility = View.GONE
                    }

                    adapter.setEvent(eventList)
                    pbLoadEvent.visibility = View.GONE
                }

                override fun onCancelled(p0: DatabaseError) {
                    Log.e(TAG, "onCancelled : "+p0.message)
                    layoutEmptyStateEvent.visibility = View.VISIBLE
                    pbLoadEvent.visibility = View.GONE
                }
            }
        )
    }
}