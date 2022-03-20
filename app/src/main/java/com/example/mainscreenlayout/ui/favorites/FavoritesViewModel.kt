package com.example.mainscreenlayout.ui.favorites

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.mainscreenlayout.domain.MarkedItem
import com.example.mainscreenlayout.model.PersonalDatabase

class FavoritesViewModel : ViewModel() {

    fun getFavourites(context : Context) : List<MarkedItem> {
        //todo
        return PersonalDatabase.getInstance(context).dao().getFavourites()
    }

    fun onMarkedItemClick(item : MarkedItem) {
        //todo
    }
}