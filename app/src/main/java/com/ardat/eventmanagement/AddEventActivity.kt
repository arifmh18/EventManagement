package com.ardat.eventmanagement

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_add_event.*
import java.util.*

class AddEventActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_event)

        initView()

    }

    private fun initView() {

        supportActionBar?.title = "Tambah Event"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

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

    private fun showDatePickDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener {
                _, mYear, mMonth, mDay ->
            tieEventDate.setText("$mDay-$mMonth-$mYear")
        }, year, month, day)
        datePickerDialog.show()

    }

    private fun checkEventForm() {

        val eventName = tieEventName.text.toString()
        val eventDate = tieEventDate.text.toString()
        val eventInfo = tieEventInfo.text.toString()

        when {
            eventName.isEmpty() -> tilEventName.error = "Mohon isikan nama event !"
            eventDate.isEmpty() -> tilEventDate.error = "Pilih tanggal event !"
            eventInfo.isEmpty() -> tilEventInfo.error = "Mohon isikan informasi event !"
            else -> addEvent(eventName, eventDate, eventInfo)
        }

    }

    private fun addEvent(eventName: String, eventDate: String, eventInfo: String) {

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            android.R.id.home -> finish()
        }

        return super.onOptionsItemSelected(item)
    }
}