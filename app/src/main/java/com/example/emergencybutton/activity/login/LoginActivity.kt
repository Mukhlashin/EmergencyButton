package com.example.emergencybutton.activity.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.emergencybutton.R
import com.example.emergencybutton.activity.MainActivity
import com.example.emergencybutton.activity.forpass.LoginPresenter
import com.example.emergencybutton.activity.forpass.LoginView
import com.example.emergencybutton.model.UserItem
import com.example.emergencybutton.network.BaseApiService
import com.example.emergencybutton.network.UtilsApi
import kotlinx.android.synthetic.main.activity_login.*


open class LoginActivity : AppCompatActivity(), LoginView  {

    private var presenter: LoginPresenter = LoginPresenter(this)

    private lateinit var mApiService: BaseApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mApiService = UtilsApi.getAPIService()!!

        val email = edt_email.text.toString()
        val pass = edt_pass.text.toString()

        btn_login.setOnClickListener {
            goToHome()
            presenter.postLoginData(email, pass)
        }
    }

    override fun goToHome() {
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
    }

    override fun saveUserData(data: List<UserItem>) {

        val mSharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE)
        val mEditor = mSharedPreferences.edit()

        mEditor?.putString("id", data[0].data?.id.toString())
        mEditor?.putString("email", data[0].data?.name.toString())
        mEditor?.putString("pass", data[0].data?.pass.toString())
        mEditor?.putString("number", data[0].data?.number.toString())
        mEditor?.putString("id", data[0].data?.image.toString())
    }
}
