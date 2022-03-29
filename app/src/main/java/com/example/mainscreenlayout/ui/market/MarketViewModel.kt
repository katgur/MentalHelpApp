package com.example.mainscreenlayout.ui.market

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.*
import androidx.preference.PreferenceManager
import com.example.mainscreenlayout.R
import com.example.mainscreenlayout.domain.GamificationSystem

class MarketViewModel(private val context : Context) : ViewModel() {

    private val items = MutableLiveData<ArrayList<Int>>()

    private val selected = MutableLiveData<Pair<Int, Int>>()
    private val current = MutableLiveData<ArrayList<Int>>()

    init {
        loadItems(context)
        loadCurrent(context)

        selected.observeForever {
            current.value!!.add(
                it.first
            )
        }
    }

    private fun loadItems(context : Context) {
        val items = arrayListOf<Int>()
        val market = context.resources.obtainTypedArray(R.array.market)
        for (i: Int in 0 until 6) {
            items.add(market.getResourceId(i, 0))
        }
        this.items.postValue(items)
    }

    private fun loadCurrent(context : Context) {
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
        val current = sharedPref.getStringSet("current", emptySet())
        val current1 = arrayListOf<Int>()
        current1.add(R.drawable.bear)
        if (current != null) {
            for (item in current) {
                val id = Integer.parseInt(item)
                current1.add(id)
            }
        }
        this.current.postValue(current1)
    }

    fun observePoints(owner: LifecycleOwner, observer: Observer<Pair<Int, Int>>, context: Context) {
        GamificationSystem.getInstance(context).points.observe(owner, observer)
    }

    fun observeItems(owner: LifecycleOwner, observer: Observer<List<Int>>) {
        items.observe(owner, observer)
    }

    fun observeSelected(owner: LifecycleOwner, observer: Observer<Pair<Int, Int>>) {
        selected.observe(owner, observer)
    }

    fun observeCurrent(owner: LifecycleOwner, observer: Observer<List<Int>>) {
        current.observe(owner, observer)
    }

    fun save() {
        val set = HashSet<String>()
        for (id in this.current.value!!) {
            set.add(id.toString())
        }
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
        with (sharedPref.edit()) {
            putStringSet("current", set)
            apply()
        }
    }

    fun setSelected(selected: Pair<Int, Int>) {
        this.selected.postValue(selected)
    }

    fun addCurrent(id: Int) {
        val value = current.value
        value!!.add(id)
        current.postValue(value!!)
        GamificationSystem.buyItem(context)
    }

    fun removeCurrent(id: Int) {
        val value = current.value
        value!!.remove(id)
        current.postValue(value!!)
        GamificationSystem.removeItem(context)
    }
}