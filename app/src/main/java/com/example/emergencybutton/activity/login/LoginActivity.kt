package com.example.emergencybutton.activity.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.emergencybutton.R
import com.example.emergencybutton.activity.MainActivity
import com.example.emergencybutton.activity.forpass.LoginConstruct
import com.example.emergencybutton.activity.forpass.LoginPresenter
import com.example.emergencybutton.model.UserResponse
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_login.*


abstract class LoginActivity : AppCompatActivity(), LoginConstruct.View {

    private val presenter: LoginPresenter = LoginPresenter()

    abstract val data: List<UserResponse>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btn_login.setOnClickListener {
            goToHome()
            presenter.pushLoginData(edt_email.text.toString(), edt_pass.text.toString())
            saveUserData(data)
        }
    }

    override fun goToHome() {
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)
    }

    override fun saveUserData(data: List<UserResponse>) {
        Toast.makeText(this, data[0].user?.get(0)?.data?.name, Toast.LENGTH_SHORT)
    }

    override fun isFailure(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun isSuccess(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}
