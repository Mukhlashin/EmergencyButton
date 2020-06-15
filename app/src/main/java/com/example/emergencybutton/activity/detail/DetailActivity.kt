package com.example.emergencybutton.activity.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.emergencybutton.R

class DetailActivity : AppCompatActivity(), DetailConstruct.View {

    var namaPemosting: String? = intent.getStringExtra("namaPemosting")
    var nama: String? = intent.getStringExtra("nama")
    var tanggal: String? = intent.getStringExtra("tanggal")
    var deskripsi: String? = intent.getStringExtra("deskripsi")
    var image: String? = intent.getStringExtra("image")
    var nomor: String? = intent.getStringExtra("nomor")
    var lokasi: String? = intent.getStringExtra("lokasi")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
    }

    override fun showData() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
