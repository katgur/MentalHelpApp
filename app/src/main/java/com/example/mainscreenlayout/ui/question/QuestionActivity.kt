package com.example.mainscreenlayout.ui.question

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import com.example.mainscreenlayout.R
import com.example.mainscreenlayout.domain.HistoryItem
import com.example.mainscreenlayout.ui.MainActivity

class QuestionActivity : AppCompatActivity() {

    private val viewModel: QuestionViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)

        val historyItem = intent.extras?.get("historyItem")

        //todo remove !
        if (viewModel.hasAnswersToday(this)) {
            val startMainActivityIntent = Intent(this, MainActivity::class.java)
            startActivity(startMainActivityIntent)
            finish()
        } else {
            if (historyItem != null) {
                viewModel.isUpdated = historyItem as HistoryItem
            }

            // todo loading animation
            viewModel.observeSelected(this, {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.question_fcv, QuestionFragment.newInstance(it))
                    .commit()
            })
        }
    }
}