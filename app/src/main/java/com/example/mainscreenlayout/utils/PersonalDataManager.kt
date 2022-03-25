package com.example.mainscreenlayout.utils

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat
import com.example.mainscreenlayout.domain.Answer
import com.example.mainscreenlayout.domain.HistoryItem
import com.example.mainscreenlayout.domain.MarkedItem
import com.example.mainscreenlayout.domain.Record
import com.example.mainscreenlayout.model.PersonalDatabase
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader
import java.lang.Exception
import java.lang.reflect.Type

class PersonalDataManager(private val activity : Activity) {

    fun importData() {
        if (askPermission(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                43,
                "Это разрешение необходимо для восстановления данных приложения."
            )
        ) {
            val data = arrayListOf<List<*>>()
            val types = listOf(
                object : TypeToken<List<Record>>() {}.type,
                object : TypeToken<List<HistoryItem>>() {}.type,
                object : TypeToken<List<Answer>>() {}.type,
                object : TypeToken<List<MarkedItem>>() {}.type
            )
            for ((i, type) in types.withIndex()) {
                val line = readLineFromExternalStorage(i)
                val piece = line?.let { jsonToData(it, type) }
                if (piece != null) {
                    data.add(piece)
                }
            }
            PersonalDatabase.getInstance(activity).dao().addRecords(data[0] as List<Record>)
            PersonalDatabase.getInstance(activity).dao().addHistory(data[1] as List<HistoryItem>)
            PersonalDatabase.getInstance(activity).dao().addAnswers(data[2] as List<Answer>)
            PersonalDatabase.getInstance(activity).dao().addFavourites(data[3] as List<MarkedItem>)
        }
    }

    fun exportData() {
        val records = PersonalDatabase.getInstance(activity).dao().getAllRecords()
        val history = PersonalDatabase.getInstance(activity).dao().getAllHistory()
        val answers = PersonalDatabase.getInstance(activity).dao().getAllAnswers()
        val favourites = PersonalDatabase.getInstance(activity).dao().getAllFavourites()
        if (askPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, 42, "Это разрешение необходимо для создания резервной копии данных приложения.")) {
            val pieces = listOf(records, history, answers, favourites)
            val path = writeToExternalStorage(pieces.joinToString(separator = "\n") { dataToJson(it) } )
            Toast.makeText(activity, "Сохранено в $path.", Toast.LENGTH_LONG).show()
        }
    }


    private fun dataToJson(data : List<*>) : String {
        return Gson().toJson(data)
    }

    fun jsonToData(json : String, type : Type) : List<*> {
        return Gson().fromJson(json, type)
    }

    fun askPermission(permission : String, requestCode : Int, explanation: String) : Boolean {
        return when {
            ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED -> {
                true
            }
            ActivityCompat.shouldShowRequestPermissionRationale(activity, permission) -> {
                explainPermission(explanation)
                false
            }
            else -> {
                requestPermissions(activity, arrayOf(permission), requestCode)
                false
            }
        }
    }

    fun writeToExternalStorage(data : String) : String {
        val folder = activity.getExternalFilesDir("PersonalData")
        val file = File(folder, "data")
        file.writeText(data)
        return file.absolutePath
    }

    fun readLineFromExternalStorage(index : Int) : String? {
        val folder = activity.getExternalFilesDir("PersonalData")
        val file = File(folder, "data")
        var fis : FileInputStream? = null
        var isr : InputStreamReader? = null
        var br : BufferedReader? = null
        var line : String? = null
        try {
            fis = FileInputStream(file)
            isr = InputStreamReader(fis)
            br = BufferedReader(isr)
            var i = 0
            while (i <= index) {
                line = br.readLine()
                i++
            }
        } catch (e : Exception) {
            Toast.makeText(activity, "Ошибка во время чтения.", Toast.LENGTH_LONG).show()
        } finally {
            br?.close()
            isr?.close()
            fis?.close()
        }
        return line
    }


    fun explainPermission(explanation : String) {
        AlertDialog.Builder(activity)
            .setMessage(explanation)
            .setPositiveButton("Ок") { _,_ -> }
            .create()
    }
}