package com.example.emergencybutton.activity.detail

interface DetailConstruct {
    interface View{
        fun showData()
        fun showLoading()
        fun hideLoading()
    }
    interface Presenter{
        fun loadImage()
    }
}