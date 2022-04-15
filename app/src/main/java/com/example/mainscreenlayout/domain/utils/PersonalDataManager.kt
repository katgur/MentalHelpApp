package com.example.mainscreenlayout.utils

import android.app.Activity
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.preference.PreferenceManager
import com.example.mainscreenlayout.model.Answer
import com.example.mainscreenlayout.model.HistoryItem
import com.example.mainscreenlayout.model.MarkedItem
import com.example.mainscreenlayout.model.entities.Record
import com.example.mainscreenlayout.data.PersonalDatabase
import com.google.common.reflect.TypeToken
import com.google.gson.GsonBuilder
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader
import java.lang.Exception
import java.lang.reflect.Type

class PersonalDataManager(private val activity : Activity) {

    fun importData() {
        val data = arrayListOf<List<*>>()
        val types = arrayListOf(
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
        PersonalDatabase.getInstance(activity).dao().addAnswers(data[2] as List<Answer>)
        PersonalDatabase.getInstance(activity).dao().addHistory(data[1] as List<HistoryItem>)
        PersonalDatabase.getInstance(activity).dao().addFavourites(data[3] as List<MarkedItem>)
        Toast.makeText(activity, "Данные восстановлены", Toast.LENGTH_LONG).show()
    }

    fun exportData() {
        val records = PersonalDatabase.getInstance(activity).dao().getAllRecords()
        val history = PersonalDatabase.getInstance(activity).dao().getAllHistory()
        val answers = PersonalDatabase.getInstance(activity).dao().getAllAnswers()
        val favourites = PersonalDatabase.getInstance(activity).dao().getAllFavourites()
        val pieces = listOf(records, history, answers, favourites)
        val path = writeToExternalStorage(pieces.joinToString(separator = "\n") { dataToJson(it) })
        Toast.makeText(activity, "Сохранено в $path", Toast.LENGTH_LONG).show()
    }
    
    fun deleteData() {
        PersonalDatabase.getInstance(activity).clearAllTables()
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(activity)
        with (sharedPref.edit()) {
            remove("name")
            remove("last")
            remove("points")
            remove("level")
            remove("current")
            apply()
        }
        Toast.makeText(activity, "Данные удалены", Toast.LENGTH_LONG).show()
    }
    
    private fun dataToJson(data : List<*>) : String {
        return GsonBuilder()
            .setPrettyPrinting()
            .serializeNulls()
            .create()
            .toJson(data).replace("\n", "")
    }

    private fun jsonToData(json : String, type : Type) : List<*> {
        return GsonBuilder()
            .setPrettyPrinting()
            .serializeNulls()
            .create()
            .fromJson(json, type)
    }

    public fun askPermission(permission : String, requestCode: Int, explanation: String, requestPermissionLauncher: ActivityResultLauncher<String>) : Boolean {
        Log.d("permission asking", "askPermission")
        return when {
            ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED -> {
                Log.d("permission asking", "permission is granted")
                true
            }
            ActivityCompat.shouldShowRequestPermissionRationale(activity, permission) -> {
                Log.d("permission asking", "should request permission rationale")
                explainPermission(explanation)
                false
            }
            else -> {
                Log.d("permission asking", "request permission")
                requestPermissionLauncher.launch(permission)
//                requestPermissions(activity,
//                    arrayOf(permission),
//                    requestCode)
                false
            }
        }
    }

    private fun writeToExternalStorage(data : String) : String {
        val folder = activity.getExternalFilesDir("PersonalData")
        val file = File(folder, "data.txt")
        file.writeText(data)
        return file.absolutePath
    }

    private fun readLineFromExternalStorage(index : Int) : String? {
        val folder = activity.getExternalFilesDir("PersonalData")
        val file = File(folder, "data.txt")
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


    private fun explainPermission(explanation : String) {
        Log.d("permission asking", "explain permission")
        AlertDialog.Builder(activity)
            .setMessage(explanation)
            .setPositiveButton("Ок") { _,_ -> }
            .create()
            .show()
    }
}