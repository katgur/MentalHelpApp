package com.example.mainscreenlayout.ui.answer

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mainscreenlayout.R
import com.example.mainscreenlayout.databinding.AnswerFragmentBinding
import com.example.mainscreenlayout.data.PersonalDatabase
import com.example.mainscreenlayout.ui.question.QuestionActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class AnswerFragment : Fragment() {

    companion object {
        fun newInstance(id : String) : AnswerFragment {
            val instance = AnswerFragment()
            val args = Bundle()
            args.putString("historyId", id)
            instance.arguments = args
            return instance
        }
    }

    private lateinit var viewModel: AnswerViewModel
    private lateinit var binding: AnswerFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = AnswerFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[AnswerViewModel::class.java]

        //todo
        val historyId = requireArguments().get("historyId") as String
        val historyItem = PersonalDatabase.getInstance(requireContext()).dao().getHistoryItem(historyId)

        binding.answerText.text = historyItem.answer_id?.let { viewModel.getContent(requireContext(), it) }
        binding.answerDateText.text = LocalDateTime.ofEpochSecond(historyItem.date, 0, ZoneOffset.ofHours(3)).format(
            DateTimeFormatter.ofPattern("dd-MM-yyyy"))

        binding.answerEditBtn.setOnClickListener {
            val intent = Intent(requireActivity(), QuestionActivity::class.java)
            intent.putExtra("answerId", historyItem.answer_id)
            startActivity(intent)
        }

        binding.answerDeleteBtn.setOnClickListener {
            viewModel.deleteAnswer(requireContext(), historyItem)
            val navView: BottomNavigationView = requireActivity().findViewById(R.id.nav_view)
            navView.selectedItemId = R.id.navigation_history
        }
    }
}