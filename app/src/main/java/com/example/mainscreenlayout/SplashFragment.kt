package com.example.mainscreenlayout

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mainscreenlayout.databinding.ChatFragmentBinding
import com.example.mainscreenlayout.databinding.SplashFragmentBinding
import android.R
import android.content.Intent
import androidx.fragment.app.commit


class SplashFragment : Fragment() {

    companion object {
        fun newInstance() = SplashFragment()
    }

    private lateinit var viewModel: SplashViewModel
    private lateinit var binding: SplashFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SplashFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SplashViewModel::class.java)

        val name = viewModel.getName()
        if (name == null) {
            binding.splashText.setText("Привет!")
            binding.root.setOnClickListener{
                loadNicknameFragment()
            }
        } else {
            binding.splashText.setText("Привет, $name!")
            binding.root.setOnClickListener{
                loadQuestionActivity()
            }
        }
    }

    private fun loadNicknameFragment() {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(com.example.mainscreenlayout.R.id.fragment_container_view, NicknameFragment.newInstance())
            .disallowAddToBackStack()
            .commit()
    }

    private fun loadQuestionActivity() {
        val startQuestionActivityIntent = Intent(context, QuestionActivity::class.java)
        startQuestionActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(startQuestionActivityIntent)
    }
}