package com.example.emergencybutton.activity.login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.emergencybutton.R
import com.example.emergencybutton.activity.MainActivity
import com.example.emergencybutton.activity.forpass.LoginConstruct
import com.example.emergencybutton.activity.forpass.LoginPresenter
import com.example.emergencybutton.model.UserResponse
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Response


class LoginActivity : AppCompatActivity(), LoginConstruct.View {

    private val presenter: LoginPresenter = LoginPresenter()
    lateinit var myPref: SharedPreferences
    lateinit var loginEditor: SharedPreferences.Editor
    lateinit var editor: SharedPreferences.Editor

     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

         myPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE)
         editor = getSharedPreferences("userInfo", Context.MODE_PRIVATE).edit()
         loginEditor = getSharedPreferences("login", Context.MODE_PRIVATE).edit()

        btn_login.setOnClickListener {
            goToHome()
            presenter.pushLoginData(edt_email.text.toString(), edt_pass.text.toString())
            saveUserData()
        }
    }

    override fun goToHome() {
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)
    }

    override fun saveUserData() {
        loginEditor.putBoolean("isLogin", true)
        editor.putString("nama", "test")
        loginEditor.apply()
        editor.apply()
    }

    override fun isFailure(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun isSuccess(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}
