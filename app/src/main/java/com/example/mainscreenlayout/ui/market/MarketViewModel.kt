package com.example.mainscreenlayout.ui.market

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.*
import androidx.preference.PreferenceManager
import com.example.mainscreenlayout.R
import com.example.mainscreenlayout.domain.GamificationSystem

class MarketViewModel(context : Context, owner: LifecycleOwner) : ViewModel() {

    private val items = MutableLiveData<HashSet<Int>>()

    private val selected = MutableLiveData<Pair<Int, Int>>()
    private val current = MutableLiveData<HashSet<Int>>()

    init {
        loadItems(context)
        loadCurrent(context)
    }

    private fun loadItems(context : Context) {
        val items = hashSetOf<Int>()
        val market = context.resources.obtainTypedArray(R.array.market)
        for (i: Int in 0 until 6) {
            items.add(market.getResourceId(i, 0))
        }
        this.items.postValue(items)
    }

    private fun loadCurrent(context : Context) {
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
        val current = sharedPref.getStringSet("current", emptySet())
        val current1 = hashSetOf<Int>()
        current1.add(R.drawable.bear)
        if (current != null) {
            for (item in current) {
                val id = Integer.parseInt(item)
                current1.add(id)
            }
        }
        this.current.postValue(current1)
        Log.d("aaa", "loaded current vm " + current1.joinToString(separator=", "))

    }

    fun observePoints(owner: LifecycleOwner, observer: Observer<Pair<Int, Int>>, context: Context) {
        GamificationSystem.getInstance(context).points.observe(owner, observer)
    }

    fun observeItems(owner: LifecycleOwner, observer: Observer<HashSet<Int>>) {
        items.observe(owner, observer)
    }

    fun observeSelected(owner: LifecycleOwner, observer: Observer<Pair<Int, Int>>) {
        selected.observe(owner, observer)
    }

    fun observeCurrent(owner: LifecycleOwner, observer: Observer<HashSet<Int>>) {
        current.observe(owner, observer)
    }

    fun save(context: Context) {
        val set = HashSet<String>()
        for (id in current.value!!) {
            if (id != R.drawable.bear) {
                set.add(id.toString())
            }
        }
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
        with (sharedPref.edit()) {
            putStringSet("current", set)
            apply()
        }
        Log.d("aaa", "save vm " + set.joinToString(separator = ", "))

    }

    fun setSelected(selected: Pair<Int, Int>) {
        Log.d("aaa", "set selected vm")
        this.selected.postValue(selected)
    }

    fun addCurrent(id: Int, context: Context) {
        Log.d("aaa", "add current vm")
        if (GamificationSystem.buyItem(context)) {
            Log.d("aaa", "bought item vm")
            val value = current.value
            value!!.add(id)
            current.value = value!!
            GamificationSystem.buyItem(context)
        } else {
            Log.d("aaa", "not enoghh money vm")
            Toast.makeText(context, "Недостаточно баллов, чтобы купить вещь.", Toast.LENGTH_LONG).show()
        }
    }

    fun removeCurrent(id: Int, context: Context) {
        Log.d("aaa", "remove item vm")
        val value = current.value
        value!!.remove(id)
        current.value = value!!
        GamificationSystem.removeItem(context)
    }
}