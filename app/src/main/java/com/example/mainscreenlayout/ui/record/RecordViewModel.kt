package com.example.mainscreenlayout.ui.record

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.mainscreenlayout.domain.Record
import com.example.mainscreenlayout.model.PersonalDatabase

class RecordViewModel(private val id : String) : ViewModel() {

    fun getDescription(context : Context) : String {
        return ""
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

//    class Factory(private val id : Int) : ViewModelProvider.Factory {
//
//        override fun <T : ViewModel> create(modelClass: Class<T>): T {
//            return RecordViewModel(id) as T
//        }
//    }
}