package com.example.mainscreenlayout.ui.question

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.mainscreenlayout.R

class QuestionActivity : AppCompatActivity() {

    private val viewModel: QuestionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)

        if (viewModel.hasAnswersToday(this)) {
            finish()
        }

        // todo loading animation
        viewModel.observeSelected(this, {
            supportFragmentManager.beginTransaction()
                .replace(R.id.question_fcv, QuestionFragment.newInstance(it))
                .addToBackStack("")
                .commit()
        })
    }
}