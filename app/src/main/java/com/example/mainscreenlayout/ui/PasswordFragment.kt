package com.example.mainscreenlayout.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.mainscreenlayout.R
import com.example.mainscreenlayout.ui.nick.NicknameViewModel
import com.example.mainscreenlayout.ui.question.QuestionActivity

class PasswordFragment : Fragment() {

    private val viewModel : NicknameViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val okButton = view.findViewById<ImageButton>(R.id.password_ok_btn)
        val password = view.findViewById<EditText>(R.id.password_et)

        okButton.setOnClickListener {
            val entered = password.text.toString()
            if (viewModel.validatePassword(requireActivity(), entered)) {
                loadQuestionActivity()
            }
            else {
                Toast.makeText(requireContext(), "Неверный пароль", Toast.LENGTH_SHORT).show()
                password.setText("")
            }
        }
    }

    fun loadQuestionActivity() {
        val intent = Intent(requireActivity(), QuestionActivity::class.java)
        startActivity(intent)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_password, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() = PasswordFragment()
    }
}