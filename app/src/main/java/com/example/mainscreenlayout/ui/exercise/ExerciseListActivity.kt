package com.example.mainscreenlayout.ui.exercise

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mainscreenlayout.R
import com.example.mainscreenlayout.ui.exercise.ExerciseListFragment
import com.example.mainscreenlayout.ui.record.RecordFragment

class ExerciseListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise_list)

        val extras = intent.extras
        //todo
        if (extras != null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.exercise_fcv, ExerciseListFragment.newInstance(extras))
                .disallowAddToBackStack()
                .commit()
        }
    }
}