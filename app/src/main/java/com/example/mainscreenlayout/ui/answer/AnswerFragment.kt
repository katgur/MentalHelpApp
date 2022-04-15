package com.example.mainscreenlayout.ui.answer

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mainscreenlayout.R
import com.example.mainscreenlayout.databinding.AnswerFragmentBinding
import com.example.mainscreenlayout.data.PersonalDatabase
import com.example.mainscreenlayout.model.entities.Record
import com.example.mainscreenlayout.ui.adapter.RecordAdapter
import com.example.mainscreenlayout.ui.question.QuestionActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

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

        val historyId = requireArguments().get("historyId") as String
        val historyItem = PersonalDatabase.getInstance(requireContext()).dao().getHistoryItem(historyId)
        if (historyItem.answer_id != null) {
            val answer = PersonalDatabase.getInstance(requireContext()).dao().getAnswer(historyItem.answer_id)
            val adapter = RecordAdapter(Record("", linkedMapOf(
                Pair("Тревожность", answer.anxious.toString() + " - " + answer.answers[0]),
                Pair("Депрессивность", answer.depressed.toString() + " - " + answer.answers[1]),
                Pair("Уровень стресса", answer.stress.toString() + " - " + answer.answers[2]),
                Pair("Беспокоит", answer.problem)), ""))
            binding.answerRv.adapter = adapter
            binding.answerRv.layoutManager = LinearLayoutManager(requireContext())
        }

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