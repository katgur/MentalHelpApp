package com.example.mainscreenlayout.model

interface IObserver {

    fun onResponse(response: ArrayList<String>)

    fun onFailure(response: String)
}