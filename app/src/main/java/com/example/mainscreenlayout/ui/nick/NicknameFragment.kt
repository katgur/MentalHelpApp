package com.example.mainscreenlayout.ui.nick

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mainscreenlayout.ui.question.QuestionActivity
import com.example.mainscreenlayout.databinding.NicknameFragmentBinding

class NicknameFragment : Fragment() {

    companion object {
        fun newInstance() = NicknameFragment()
    }

    private lateinit var viewModel: NicknameViewModel
    private lateinit var binding: NicknameFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = NicknameFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(NicknameViewModel::class.java)

        binding.nickOk.setOnClickListener {
            val nick = binding.nickText.text
            viewModel.addNickname(requireActivity(), nick.toString())
            loadQuestionActivity()
        }
    }

    private fun loadQuestionActivity() {
        val intent = Intent(context, QuestionActivity::class.java)
        startActivity(intent)
    }
}