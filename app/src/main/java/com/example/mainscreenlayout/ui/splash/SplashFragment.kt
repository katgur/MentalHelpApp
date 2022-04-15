package com.example.mainscreenlayout.ui.splash

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mainscreenlayout.databinding.SplashFragmentBinding
import com.example.mainscreenlayout.ui.nick.NicknameFragment
import com.example.mainscreenlayout.R
import com.example.mainscreenlayout.domain.utils.ProjectClassScanner
import com.example.mainscreenlayout.ui.PasswordFragment
import com.example.mainscreenlayout.ui.question.QuestionActivity
import java.io.File
import java.net.URL
import dalvik.system.DexFile

import dalvik.system.PathClassLoader
import java.io.IOException
import java.util.*


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
        viewModel = ViewModelProvider(this)[SplashViewModel::class.java]

        if (viewModel.hasPassword(requireActivity())) {
            loadPasswordFragment()
        }

        val name = viewModel.getName(requireActivity())
        if (name == null) {
            binding.splashText.text = "Привет!"
            binding.root.setOnClickListener {
                if (viewModel.hasPassword(requireActivity())) {
                    loadPasswordFragment()
                } else {
                    loadNicknameFragment()
                }
            }
        } else {
            binding.splashText.setText("Привет, $name!")
            binding.root.setOnClickListener {
                if (viewModel.hasPassword(requireActivity())) {
                    loadPasswordFragment()
                } else {
                    val intent = Intent(requireActivity(), QuestionActivity::class.java)
                    intent.flags = intent.flags or Intent.FLAG_ACTIVITY_NO_HISTORY
                    startActivity(intent)
                }
            }
        }
    }

    private fun loadNicknameFragment() {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view, NicknameFragment.newInstance())
            .disallowAddToBackStack()
            .commit()
    }

    private fun loadPasswordFragment() {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view, PasswordFragment.newInstance())
            .disallowAddToBackStack()
            .commit()
    }


    private fun exportData() {
        scan()
    }

    @Throws(IOException::class, ClassNotFoundException::class, NoSuchMethodException::class)
    fun scan() {
        val folder = requireActivity().getExternalFilesDir("reflect")
        val file = File(folder, "reflect4.txt")
        val scanner = ProjectClassScanner(requireContext(), file)
        scanner.scan()
    }
}