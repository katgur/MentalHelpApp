package com.example.mainscreenlayout.domain

import android.content.Context
import androidx.lifecycle.*
import androidx.preference.PreferenceManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow

class GamificationSystem(private val context : Context) {

    val points = MutableLiveData<Pair<Int, Int>>()

    init {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val points = sharedPreferences.getInt("points", 0)
        val level = sharedPreferences.getInt("level", 0)
        this.points.postValue(Pair(points, level))
    }

    companion object {

        private var instance : GamificationSystem? = null
        private val ITEM_COST = 50
        private val ACTION_VALUE = 10

        fun getInstance(context : Context) : GamificationSystem {
            if (instance == null) {
                instance = GamificationSystem(context)
            }
            return instance!!
        }

        fun updatePoints(context: Context) {
            val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
            var points = sharedPref.getInt("points", 0)
            var level = sharedPref.getInt("level", 0)
            points += ACTION_VALUE
            if (points != 0 && points % 30 == 0) {
                level += 1
            }
            with (sharedPref.edit()) {
                putInt("points", points)
                putInt("level", level)
                apply()
            }
            GamificationSystem.getInstance(context).points.postValue(Pair(points, level))
        }

        fun buyItem(context: Context): Boolean {
            val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
            var points = sharedPref.getInt("points", 0)
            val level = sharedPref.getInt("level", 0)
            if (points < ITEM_COST) {
                return false
            }
            points -= ITEM_COST
            with (sharedPref.edit()) {
                putInt("points", points)
                putInt("level", level)
                apply()
            }
            GamificationSystem.getInstance(context).points.postValue(Pair(points, level))
            return true
        }

        fun removeItem(context: Context) {
            val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
            var points = sharedPref.getInt("points", 0)
            val level = sharedPref.getInt("level", 0)
            points += ITEM_COST
            with (sharedPref.edit()) {
                putInt("points", points)
                putInt("level", level)
                apply()
            }
            GamificationSystem.getInstance(context).points.postValue(Pair(points, level))
        }
    }
}