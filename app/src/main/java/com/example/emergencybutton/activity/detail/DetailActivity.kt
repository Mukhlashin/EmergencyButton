package com.example.emergencybutton.activity.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Spinner
import com.bumptech.glide.Glide
import com.example.emergencybutton.R
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.notification_item.*
import kotlinx.android.synthetic.main.notification_item.tv_nama_barang

class DetailActivity : AppCompatActivity(), DetailConstruct.View {

    lateinit var spinner: ProgressBar

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

        showLoading()
        showData()
    }

    override fun showData() {
        tv_nama_pemosting.text = namaPemosting
        tv_detail_nama_barang.text = nama
        tv_nomor_hp.text = nomor
        tv_lokasi_barang.text = lokasi
        tv_tanggal_barang.text = tanggal
        tv_deskripsi_barang.text = deskripsi
        Glide.with(this).load(image).into(img_barang)
        hideLoading()
    }

    override fun showLoading() {
        spinner.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        spinner.visibility = View.GONE
    }
}
