package com.ardat.eventmanagement.Fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ardat.eventmanagement.AddEventActivity
import com.ardat.eventmanagement.R
import com.ardat.eventmanagement.adapter.EventAdapter
import com.ardat.eventmanagement.model.EventLocal
import com.ardat.eventmanagement.model.Event
import com.ardat.eventmanagement.utils.checkNetworkIsConnected
import com.ardat.eventmanagement.utils.showToast
import com.ardat.eventmanagement.viewmodel.EventViewModel
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_event.*

class EventFragment : Fragment(), EventAdapter.EventListener {


    private lateinit var databaseReference: DatabaseReference
    private var database = FirebaseDatabase.getInstance()

    private var eventList: MutableList<Event> = mutableListOf()
    private var eventLocalList = emptyList<EventLocal>()

    private lateinit var adapter : EventAdapter

    private lateinit var eventViewModel: EventViewModel

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

        FirebaseDatabase.getInstance().setPersistenceEnabled(true)

        initView()
    }

    private fun initView() {

        eventViewModel = ViewModelProvider(this).get(EventViewModel::class.java)

        databaseReference = database.getReference("event")

        eventList = ArrayList()

        adapter = EventAdapter(requireContext())
        adapter.eventListener = this

        rvEvent.layoutManager = LinearLayoutManager(requireContext())
        rvEvent.adapter = adapter

        fabEvent.setOnClickListener {
            val intent = Intent(requireActivity(), AddEventActivity::class.java)
            intent.putExtra(AddEventActivity.OPERATION_TYPE, "INSERT")
            startActivity(intent)
        }

        getEventData()

//        if (checkNetworkIsConnected(requireContext())){
//            Log.d(TAG, "has internet")
//            if (eventViewModel.eventData.hasActiveObservers()){
//                eventViewModel.eventData.removeObservers(viewLifecycleOwner)
//                Log.d(TAG, "observer removed!")
//            }
//            getEventData()
//        }else{
//            Log.d(TAG, "has no internet")
//            eventViewModel.eventData.observe(viewLifecycleOwner, Observer { listEvent ->
//                listEvent?.let {
//                    eventLocalList = it
//                    Log.d(TAG, "event local list size : "+eventLocalList.size)
//                    if(eventLocalList.isNotEmpty()){
//                        Log.d(TAG, "local data not empty !")
//                        val eventList : ArrayList<Event> = ArrayList()
//                        for (item in eventLocalList){
//                            eventList.add(Event(item.eventName, item.eventDate, item.eventInfo, item.eventUid))
//                        }
//                        adapter.setEvent(eventList)
//                        layoutEmptyStateEvent.visibility = View.GONE
//                        pbLoadEvent.visibility = View.GONE
//                    }else{
//                        Log.d(TAG, "local data empty !")
//                        layoutEmptyStateEvent.visibility = View.VISIBLE
//                        pbLoadEvent.visibility = View.GONE
//                    }
//                }
//            })
//        }

//        eventViewModel.eventData.observe(viewLifecycleOwner, Observer { listEvent ->
//            listEvent?.let {
//                eventList = it
//                if(eventList.isNotEmpty()){
//                    adapter.setEvent(eventList)
//                    layoutEmptyStateEvent.visibility = View.GONE
//                    pbLoadEvent.visibility = View.GONE
//                }else{
//                    layoutEmptyStateEvent.visibility = View.VISIBLE
//                    pbLoadEvent.visibility = View.GONE
//                }
//            }
//        })
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

                        val event = data.getValue(Event::class.java)!!

                        event.eventUid = data.key!!

//                        val eventLocal = EventLocal(event.eventName, event.eventDate, event.eventInfo, "select", "true", data.key!!)

                        eventList.add(event)

//                        eventViewModel.insert(eventLocal)

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

    override fun deleteEvent(event: Event) {
        databaseReference.child(event.eventUid).removeValue()
            .addOnSuccessListener {
//                eventViewModel.delete(event)
                showToast(requireContext(), "Berhasil hapus event")
            }
            .addOnFailureListener {
                Log.e("event item", "onFailureListener : "+it.message)
            }
    }
}