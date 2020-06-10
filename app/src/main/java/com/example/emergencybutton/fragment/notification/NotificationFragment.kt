package com.example.emergencybutton.fragment.notification

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.emergencybutton.DetailActivity
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notification, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.getNotificationData()
        rv_notification.layoutManager = LinearLayoutManager(context)
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
            intent.putExtra("nama", it.postersName)
            startActivity(intent)
        }
        rv_notification.adapter = notificationAdapter
    }

}
