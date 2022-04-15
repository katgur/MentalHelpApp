package com.example.mainscreenlayout.ui.favorites

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mainscreenlayout.R
import com.example.mainscreenlayout.adapter.MarkedItemAdapter
import com.example.mainscreenlayout.databinding.FavoritesFragmentBinding
import com.example.mainscreenlayout.databinding.FragmentHistoryBinding
import com.example.mainscreenlayout.ui.chat.ChatFragment
import com.example.mainscreenlayout.ui.record.RecordActivity
import com.example.mainscreenlayout.ui.record.RecordFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class FavoritesFragment : Fragment() {

    companion object {
        fun newInstance() = FavoritesFragment()
    }

    private lateinit var viewModel: FavoritesViewModel
    private lateinit var _binding: FavoritesFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FavoritesFragmentBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FavoritesViewModel::class.java)

        val favourites = viewModel.getFavourites(requireContext())
        val markedItemAdapter = MarkedItemAdapter(favourites.asReversed())
        markedItemAdapter.onItemClick = {
            if (it.record_id != null) {
                val startRecordActivityIntent = Intent(requireContext(), RecordActivity::class.java)
                startRecordActivityIntent.putExtra("id", it.record_id)
                startActivity(startRecordActivityIntent)
            } else if (it.exercise_id != null) {
                val navView: BottomNavigationView = requireActivity().findViewById(R.id.nav_view)
                navView.selectedItemId = R.id.navigation_chat
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.nav_host_fragment, ChatFragment.newInstance(it.exercise_id))
                    .disallowAddToBackStack()
                    .commit()
            }
        }
        _binding.favouritesRecycler.layoutManager = LinearLayoutManager(context)
        _binding.favouritesRecycler.adapter = markedItemAdapter
    }

}