package com.example.mainscreenlayout

import android.content.Intent
import com.example.mainscreenlayout.NicknameViewModel
import com.example.mainscreenlayout.R
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mainscreenlayout.databinding.NicknameFragmentBinding

class NicknameFragment : Fragment() {

    companion object {
        fun newInstance() = NicknameFragment()
    }

    private lateinit var viewModel: NicknameViewModel
    private lateinit var binding: NicknameFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = NicknameFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NicknameViewModel::class.java)

        binding.nickOk.setOnClickListener {
            val nick = binding.nickText.text
            viewModel.addNickname(nick.toString())
            loadQuestionActivity()
        }
    }

    private fun loadQuestionActivity() {
        val startQuestionActivityIntent = Intent(context, QuestionActivity::class.java)
        startQuestionActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(startQuestionActivityIntent)
    }
}