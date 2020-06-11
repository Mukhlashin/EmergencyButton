package com.example.emergencybutton.model

class ResponseEmergency {

    var id: String
    var name: String
    var lat: Double
    var lng: Double
    var distanceInKm: Double

    constructor(id: String, name: String, lat: Double, lng: Double, distance_in_km: Double) {
        this.id = id
        this.name = name
        this.lat = lat
        this.lng = lng
        this.distanceInKm = distance_in_km
    }

}