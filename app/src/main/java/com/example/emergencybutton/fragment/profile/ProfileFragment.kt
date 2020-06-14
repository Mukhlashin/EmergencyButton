package com.example.emergencybutton.fragment.profile

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide

import com.example.emergencybutton.R
import com.example.emergencybutton.activity.login.LoginActivity
import com.example.emergencybutton.activity.register.RegisterActivity
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*

/**
 * A simple [Fragment] subclass.
 */
class ProfileFragment : Fragment(), ProfileConstruct.View {

    lateinit var myPref: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    lateinit var loginEditor: SharedPreferences.Editor

    var presenter: ProfilePresenter = ProfilePresenter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        myPref = context!!.getSharedPreferences("userInfo", Context.MODE_PRIVATE)
        editor = context!!.getSharedPreferences("userInfo", Context.MODE_PRIVATE).edit()
        loginEditor = context!!.getSharedPreferences("login", Context.MODE_PRIVATE).edit()

        showDataUser()

        view.btn_logout.setOnClickListener {
            clearLoginData()
        }

        view.btn_edit_profile.setOnClickListener {
            presenter.pushUserData(
                edt_profile_nama.text.toString(),
                edt_profile_number.text.toString(),
                edt_profile_email.text.toString(),
                myPref.getString("email", "").toString(),
                edt_profile_pass.text.toString())
        }
    }

    override fun showDataUser() {
        var name = myPref.getString("nama", "")
        var number = myPref.getString("nomor", "")
        var email = myPref.getString("email", "")
        var pass = myPref.getString("pass", "")
        var image = myPref.getString("image", "")

        edt_profile_nama.setText(name)
        edt_profile_number.setText(number)
        edt_profile_email.setText(email)
        edt_profile_pass.setText(pass)

    }

    override fun clearLoginData(): AlertDialog? {
        var builder = AlertDialog.Builder(context)
        builder.setTitle("Logout")
        builder.setMessage("Apakah anda ingin logout?")
        builder.setIcon(R.drawable.ic_warning)
        builder.setPositiveButton("Ya") { dialog, which ->
            editor.remove("id")
            editor.remove("nama")
            editor.remove("nomor")
            editor.remove("email")
            editor.remove("pass")
            editor.remove("image")
            loginEditor.remove("isLogin")
            editor.apply()
            loginEditor.apply()
            goToLogin()
        }
        builder.setNegativeButton("Tidak") { dialog, which ->  }
        builder.create()
        return builder.show()
    }

    override fun goToLogin() {
        val intent = Intent(this.activity, LoginActivity::class.java)
        startActivity(intent)
    }

    override fun onFailure(msg: String) {

    }

    override fun onSuccess(msg: String) {

    }
}
