package com.example.mainscreenlayout.ui.history

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mainscreenlayout.R
import com.example.mainscreenlayout.adapter.HistoryAdapter
import com.example.mainscreenlayout.databinding.FragmentHistoryBinding
import com.example.mainscreenlayout.ui.answer.AnswerFragment
import com.example.mainscreenlayout.ui.nick.NicknameFragment
import com.example.mainscreenlayout.ui.record.RecordActivity

class HistoryFragment : Fragment() {

    private lateinit var historyViewModel: HistoryViewModel
    private lateinit var _binding: FragmentHistoryBinding
    private var viewManager = LinearLayoutManager(context)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        historyViewModel = ViewModelProvider(this).get(HistoryViewModel::class.java)

        //todo catch ex i require activity
        val history = historyViewModel.getHistory(requireActivity())
        val historyAdapter = HistoryAdapter(history)
        historyAdapter.onItemClick = {
            if (it.record_id != "") {
                val startRecordActivityIntent = Intent(requireContext(), RecordActivity::class.java)
                startRecordActivityIntent.putExtra("id", it.record_id)
                startActivity(startRecordActivityIntent)
            } else if (it.answer_id != "") {
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.nav_host_fragment, AnswerFragment.newInstance(it))
                    .disallowAddToBackStack()
                    .commit()
            }
        }

        _binding.recyclerHistory.layoutManager = viewManager
        _binding.recyclerHistory.adapter = historyAdapter
    }
}