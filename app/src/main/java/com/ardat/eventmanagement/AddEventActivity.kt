package com.ardat.eventmanagement

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import com.ardat.eventmanagement.model.Event
import com.ardat.eventmanagement.utils.showToast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_add_event.*
import java.util.*

class AddEventActivity : AppCompatActivity() {

    private val database = FirebaseDatabase.getInstance()
    private lateinit var databaseReference: DatabaseReference

    private var operationType = ""
    private var updateUidEvent = ""

    companion object {
        var TAG = AddEventActivity::class.java.name
        var EVENT_NAME = "EVENT_NAME"
        var EVENT_DATE = "EVENT_DATE"
        var EVENT_INFO = "EVENT_INFO"
        var EVENT_UID = "EVENT_UID"
        var OPERATION_TYPE = "OP_TYPE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_event)

        initView()

    }

    private fun initView() {

        supportActionBar?.title = "Tambah Event"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        databaseReference = database.getReference("event")

        val intent = intent
        operationType = intent.getStringExtra(OPERATION_TYPE)!!

        if(operationType == "UPDATE"){
            val eventName = intent.getStringExtra(EVENT_NAME)
            val eventDate = intent.getStringExtra(EVENT_DATE)
            val eventInfo = intent.getStringExtra(EVENT_INFO)
            updateUidEvent = intent.getStringExtra(EVENT_UID)!!

            supportActionBar?.title = "Edit Event"
            btnAddEvent.text = "Edit Event"

            setToTextInput(eventName, eventDate, eventInfo)
        }

        btnAddEvent.setOnClickListener {
            checkEventForm()
        }

        tilEventDate.setOnClickListener {
            showDatePickDialog()
        }

        tieEventDate.setOnClickListener {
            showDatePickDialog()
        }

    }

    private fun setToTextInput(eventName: String?, eventDate: String?, eventInfo: String?) {
        tieEventName.setText(eventName)
        tieEventDate.setText(eventDate)
        tieEventInfo.setText(eventInfo)
    }

    private fun showDatePickDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener {
                _, mYear, mMonth, mDay ->
            val date = "$mDay-$mMonth-$mYear"
            showTimerDialog(date)
        }, year, month, day)
        datePickerDialog.show()

    }

    private fun showTimerDialog(date : String) {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener {
                _, mHour, mMinute ->
            tieEventDate.setText("$date $mHour:$mMinute")
        }, hour, minute, true)
        timePickerDialog.show()
    }

    private fun checkEventForm() {

        val eventName = tieEventName.text.toString()
        val eventDate = tieEventDate.text.toString()
        val eventInfo = tieEventInfo.text.toString()

        when {
            eventName.isEmpty() -> tilEventName.error = "Mohon isikan nama event !"
            eventDate.isEmpty() -> tilEventDate.error = "Pilih tanggal event !"
            eventInfo.isEmpty() -> tilEventInfo.error = "Mohon isikan informasi event !"
            else -> 
                when (operationType){
                    "INSERT" -> addEvent(eventName, eventDate, eventInfo)
                    "UPDATE" -> updateEvent(eventName, eventDate, eventInfo)
                }
        }

    }

    private fun updateEvent(eventName: String, eventDate: String, eventInfo: String) {
        val event = Event(eventName, eventDate, eventInfo, "")
        databaseReference.child(updateUidEvent).setValue(event)
            .addOnCompleteListener {
                showToast(this, "Berhasil edit event")
                finish()
            }
            .addOnFailureListener {
                Log.e(TAG, "onFailureListener : "+it.message)
            }
    }

    private fun addEvent(eventName: String, eventDate: String, eventInfo: String) {
        val event = Event(eventName, eventDate, eventInfo, "")
        databaseReference.push().setValue(event)
            .addOnCompleteListener {
                showToast(this, "Berhasil menambah event !")
                finish()
            }
            .addOnFailureListener {
                Log.e(TAG, "onFailureListener : "+it.message)
            }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            android.R.id.home -> finish()
        }

        return super.onOptionsItemSelected(item)
    }
}