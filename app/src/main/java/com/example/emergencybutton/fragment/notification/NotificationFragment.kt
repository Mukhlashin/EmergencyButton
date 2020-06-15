package com.example.emergencybutton.fragment.notification

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.emergencybutton.activity.detail.DetailActivity
import com.example.emergencybutton.R
import com.example.emergencybutton.adapter.NotificationAdapter
import com.example.emergencybutton.model.NotificationItem
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.fragment_notification.*

/**
 * A simple [Fragment] subclass.
 */
class NotificationFragment : Fragment(), NotificationConstruct.View {

    private var presenter: NotificationPresenter = NotificationPresenter(this)
    private lateinit var notificationAdapter: NotificationAdapter

    lateinit var myPref: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notification, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        myPref = context!!.getSharedPreferences("userInfo", Context.MODE_PRIVATE)
        var nama = myPref.getString("nama", "Nama pengguna")
        tv_nama_pengguna.text = nama

        presenter.getNotificationData()
    }

    override fun onFailure(msg: String) {
        Toasty.success(activity!!, "Fail with $msg", Toasty.LENGTH_LONG).show()
    }

    override fun onSuccess(msg: String, toString: String) {
        Toasty.success(activity!!, "Success with $msg", Toasty.LENGTH_LONG).show()
    }

    override fun showData(data: List<NotificationItem?>?) {
        notificationAdapter = NotificationAdapter(data) {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("namaPemosting", it.postersName)
            intent.putExtra("nama", it.name)
            intent.putExtra("tanggal", it.date)
            intent.putExtra("deskripsi", it.description)
            intent.putExtra("image", it.image)
            intent.putExtra("nomor", it.number)
            intent.putExtra("lokasi", it.location)
            startActivity(intent)
        }
        rv_notification.layoutManager = LinearLayoutManager(context)
        rv_notification.adapter = notificationAdapter
    }

}
