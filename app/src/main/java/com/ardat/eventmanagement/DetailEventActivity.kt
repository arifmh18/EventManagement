package com.ardat.eventmanagement

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_detail_event.*

class DetailEventActivity : AppCompatActivity() {

    companion object {
        var EVENT_NAME = "EVENT_NAME"
        var EVENT_DATE = "EVENT_DATE"
        var EVENT_INFO = "EVENT_INFO"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_event)

        initView()
    }

    private fun initView() {

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Detail Event"

        val intent = intent
        val eventName = intent.getStringExtra(EVENT_NAME)
        val eventDate = intent.getStringExtra(EVENT_DATE)
        val eventInfo = intent.getStringExtra(EVENT_INFO)

        setData(eventName, eventDate, eventInfo)
    }

    private fun setData(eventName: String?, eventDate: String?, eventInfo: String?) {
        tvNameDetailEvent.text = eventName
        tvDateDetailEvent.text = eventDate
        tvInfoDetailEvent.text = eventInfo
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            android.R.id.home -> {
                finish()
                true
            }
            else -> false
        }
    }
}