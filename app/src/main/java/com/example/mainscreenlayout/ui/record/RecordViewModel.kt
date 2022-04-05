package com.example.mainscreenlayout.ui.record

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.mainscreenlayout.model.MarkedItem
import com.example.mainscreenlayout.model.Record
import com.example.mainscreenlayout.data.PersonalDatabase
import com.example.mainscreenlayout.utils.QueryUtils

class RecordViewModel(private val id : String) : ViewModel() {

    fun getDescription(record: Record) : String {
        return "Пройдено упражнение ${QueryUtils.idToName[record.type]}"
    }

    fun replaceRecord(context : Context, record : Record) {
        PersonalDatabase.getInstance(context).dao().updateRecord(record)
    }

    fun deleteRecord(context : Context, record : Record) {
        PersonalDatabase.getInstance(context).dao().deleteRecord(record)
    }

    fun getRecord(context : Context) : Record {
        return PersonalDatabase.getInstance(context).dao().getRecord(id)
    }

    fun addToFavourites(context : Context, record: Record) {
        PersonalDatabase.getInstance(context).dao().addFavourite(
            MarkedItem(record.id, null,
            "Вы добавили в избранное запись из упражнения " + QueryUtils.idToName[record.type])
        )
    }

//    class Factory(private val id : Int) : ViewModelProvider.Factory {
//
//        override fun <T : ViewModel> create(modelClass: Class<T>): T {
//            return RecordViewModel(id) as T
//        }
//    }
}