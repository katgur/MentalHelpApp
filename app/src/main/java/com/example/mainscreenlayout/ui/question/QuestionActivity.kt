package com.example.mainscreenlayout.ui.question

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.mainscreenlayout.R

class QuestionActivity : AppCompatActivity() {

    //private var counter = 0
    //private var questions = ArrayList<Question>()

    private val viewModel: QuestionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)

        // todo loading animation
        viewModel.observeSelected(this, {
            supportFragmentManager.beginTransaction()
                .replace(R.id.question_fcv, QuestionFragment.newInstance(it))
                .addToBackStack("")
                .commit()
        })
    }
}