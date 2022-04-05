package com.example.mainscreenlayout.ui.market

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MarketViewModelFactory(val context : Context): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MarketViewModel::class.java)) {
            MarketViewModel(context) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}