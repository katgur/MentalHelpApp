package com.example.mainscreenlayout.ui.answer

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.mainscreenlayout.R
import com.example.mainscreenlayout.databinding.AnswerFragmentBinding
import com.example.mainscreenlayout.databinding.FragmentHistoryBinding
import com.example.mainscreenlayout.domain.HistoryItem
import com.example.mainscreenlayout.ui.history.HistoryFragment
import com.example.mainscreenlayout.ui.question.QuestionActivity
import com.example.mainscreenlayout.ui.question.QuestionFragment
import com.example.mainscreenlayout.ui.question.QuestionViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class AnswerFragment : Fragment() {

    companion object {
        fun newInstance(historyItem : HistoryItem) : AnswerFragment {
            val instance = AnswerFragment()
            val args = Bundle()
            args.putParcelable("historyItem", historyItem)
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
        viewModel = ViewModelProvider(this).get(AnswerViewModel::class.java)

        //todo
        val historyItem = requireArguments().get("historyItem") as HistoryItem

        binding.answerText.text = historyItem.answer_id?.let { viewModel.getContent(requireContext(), it) }

        binding.answerText.setOnLongClickListener {
            val intent = Intent(requireActivity(), QuestionActivity::class.java)
            intent.putExtra("historyItem", historyItem)
            startActivity(intent)
            return@setOnLongClickListener true
        }

        binding.answerDeleteBtn.setOnClickListener {
            viewModel.deleteAnswer(requireContext(), historyItem)
            val navView: BottomNavigationView = requireActivity().findViewById(R.id.nav_view)
            navView.selectedItemId = R.id.navigation_history
        }
    }
}