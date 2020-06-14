package com.example.emergencybutton.model

class EmergencyItem {

    var id: String
    var name: String
    var msg: String
    var lat: Double
    var lng: Double
    var distanceInKm: Double

    constructor(id: String, name: String, msg: String, lat: Double, lng: Double, distance_in_km: Double) {
        this.id = id
        this.name = name
        this.msg = msg
        this.lat = lat
        this.lng = lng
        this.distanceInKm = distance_in_km
    }

}