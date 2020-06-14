package com.example.emergencybutton.activity.forpass

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.emergencybutton.R
import com.example.emergencybutton.activity.login.LoginActivity
import kotlinx.android.synthetic.main.activity_forpass.*

class ForpassActivity : AppCompatActivity(), ForpassConstruct.View {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forpass)

        imb_forpass_back.setOnClickListener {
            goToLogin()
        }

    }

    override fun goToLogin() {
        val intent = Intent(this@ForpassActivity, LoginActivity::class.java)
        startActivity(intent)
    }
}
