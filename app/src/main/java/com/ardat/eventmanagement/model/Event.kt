package com.ardat.eventmanagement.model

data class Event(
    var eventName : String,
    var eventDate : String,
    var eventInfo : String,
    var eventUid : String
){
    constructor(): this("", "", "", "")
}