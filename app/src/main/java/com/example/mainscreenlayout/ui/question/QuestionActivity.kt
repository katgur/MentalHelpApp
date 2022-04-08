package com.example.mainscreenlayout.ui.question

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.preference.PreferenceManager
import com.example.mainscreenlayout.R
import com.example.mainscreenlayout.ui.MainActivity
import java.time.LocalDate

class QuestionActivity : AppCompatActivity() {

    private val viewModel: QuestionViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)

        val answerId = intent.getStringExtra("answerId")

        if (answerId == null) {
            if (viewModel.hasAnswersToday(this)) {
                val startMainActivityIntent = Intent(this, MainActivity::class.java)
                startActivity(startMainActivityIntent)
                finish()
            } else {
                viewModel.observeSelected(this, {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.question_fcv, QuestionFragment.newInstance(it))
                        .commit()
                })
            }
        } else {
            viewModel.isUpdated = answerId
            viewModel.observeSelected(this, {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.question_fcv, QuestionFragment.newInstance(it))
                    .commit()
            })
        }
    }
}