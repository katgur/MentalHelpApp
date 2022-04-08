package com.example.mainscreenlayout.ui.favorites

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.mainscreenlayout.model.MarkedItem
import com.example.mainscreenlayout.data.PersonalDatabase

class FavoritesViewModel : ViewModel() {

    fun getFavourites(context : Context) : List<MarkedItem> {
        return PersonalDatabase.getInstance(context).dao().getAllFavourites()
    }
}