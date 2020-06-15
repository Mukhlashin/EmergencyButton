package com.example.emergencybutton.fragment.home

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.emergencybutton.R
import com.example.emergencybutton.activity.maps.MapsPresenter
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment(), HomeConstruct.View {

    private lateinit var mMap: GoogleMap
    lateinit var locationCallback: LocationCallback
    lateinit var locationRequest: LocationRequest

    lateinit var myPref: SharedPreferences

    var presenter: HomePresenter = HomePresenter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        myPref = context!!.getSharedPreferences("userInfo", Context.MODE_PRIVATE)

        tv_nama.text = myPref.getString("nama", "")

        btn_emergency.setOnClickListener {
            showDexterPermission()
            changeImageON()
        }
    }

    override fun isFailure(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    override fun isSuccess(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    override fun changeImageON() {
        btn_emergency.setBackgroundResource(R.drawable.ic_panic_2)
    }

    override fun changeImageOFF() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showDialog(): AlertDialog? {
        var builder = AlertDialog.Builder(context)
        builder.setMessage("Apakah anda yakin ingin memencet \"panic button\"?\n" +
                "klik ok bahwa anda menyetujui ketentuan dan persyaratan aplikasi ini!! ")
        builder.setIcon(R.drawable.ic_warning)
        builder.setPositiveButton("Ya") { dialog, which -> showDexterPermission() }
        builder.create()
        return builder.show()
    }

    override fun showDexterPermission() {
        Dexter.withContext(context)
            .withPermissions(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ).withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    if (report!!.areAllPermissionsGranted()) {
                        buildLocationRequest()
                        buildLocationCallback()
                    }
                }

                override fun onPermissionRationaleShouldBeShown(permissions: MutableList<PermissionRequest>?, token: PermissionToken?) {
                    Toast.makeText(activity, "permission denied", Toast.LENGTH_SHORT)
                }
            }).check()
    }

    override fun buildLocationRequest() {
        locationRequest = LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 5000
        locationRequest.fastestInterval = 3000
        locationRequest.smallestDisplacement = 10f
    }

    override fun buildLocationCallback() {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                super.onLocationResult(locationResult)

                val latitude = locationResult?.lastLocation?.latitude.toString()
                val longitude = locationResult?.lastLocation?.longitude.toString()

                var name = myPref.getString("nama", "").toString()

                presenter.pushEmergencyON(name, latitude, longitude)
            }
        }
    }

}
