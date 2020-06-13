package com.example.emergencybutton.activity.maps

import android.location.Location
import android.util.Log
import com.example.emergencybutton.R
import com.example.emergencybutton.model.ResponseEmergency
import com.example.emergencybutton.network.BaseApiService
import com.example.emergencybutton.network.UtilsApi
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.internal.IGoogleMapDelegate
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

class MapsPresenter : MapsConstruct.Presenter {

    var compositeDisposable: CompositeDisposable = CompositeDisposable()
    var mApiService: BaseApiService = UtilsApi.getAPIService()!!
    lateinit var googleMapDelegate: IGoogleMapDelegate
    private  var mMap: GoogleMap = GoogleMap(googleMapDelegate)

    override fun getAllEmergencies(lastLocation: Location) {
        compositeDisposable.add(mApiService.getAllEmergencies(lastLocation.latitude.toString(), lastLocation.longitude.toString())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : Consumer<List<ResponseEmergency>> {
                override fun accept(t: List<ResponseEmergency>?) {
                    for (emergency: ResponseEmergency in t!!) run {
                        var emergencyLocation = LatLng(emergency.lat, emergency.lng)

                        mMap.addMarker(
                            MarkerOptions()
                                .position(emergencyLocation)
                                .title(emergency.name)
                                .snippet(StringBuilder("Distance : "). append(emergency.distanceInKm).append(" km").toString()))
                            .setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_emergency_location))

                    }
                }
            },
                Consumer<Throwable> { t -> Log.d("error : ", t?.localizedMessage) }))
    }
}