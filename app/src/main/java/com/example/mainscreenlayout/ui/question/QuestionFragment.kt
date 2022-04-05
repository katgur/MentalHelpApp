package com.example.mainscreenlayout.ui.question

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.annotation.RequiresApi
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.mainscreenlayout.ui.MainActivity
import com.example.mainscreenlayout.databinding.QuestionFragmentBinding
import com.example.mainscreenlayout.model.Question

class QuestionFragment : Fragment() {

    private val viewModel: QuestionViewModel by activityViewModels()
    private lateinit var binding: QuestionFragmentBinding

    companion object {
        fun newInstance(question : Question?) : QuestionFragment {
            val instance = QuestionFragment()
            val args = Bundle()
            args.putParcelable("question", question)
            instance.arguments = args
            return instance
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = QuestionFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val question = arguments?.get("question") as Question
        bind(question)

        binding.questionSkipBtn.setOnClickListener {
            loadMainActivity()
        }

        binding.questionForwardBtn.setOnClickListener {
            if (!viewModel.nextQuestion()) {
                viewModel.saveAnswers(requireActivity())
                loadMainActivity()
            }
        }

        binding.questionRg.setOnCheckedChangeListener { radioGroup, id ->
            val radioButton = radioGroup.findViewById<RadioButton>(id)
            viewModel.addAnswer(radioButton.text as String, ((id + 3) % 4) + 1)
        }
    }

    private fun bind(question : Question) {
        if (question.answers == null) {
            return
        }
        binding.questionText.text = question.content
        var i = 0
        for (child in binding.questionRg.children) {
            (child as RadioButton).text = question.answers[i]
            i += 1
        }
    }

    private fun loadMainActivity() {
        val startMainActivityIntent = Intent(context, MainActivity::class.java)
        startActivity(startMainActivityIntent)
    }
}