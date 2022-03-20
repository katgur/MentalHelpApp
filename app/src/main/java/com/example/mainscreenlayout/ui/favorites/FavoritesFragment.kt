package com.example.mainscreenlayout.ui.favorites

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

        //todo catch exception
        val favourites = viewModel.getFavourites(requireContext())
        val markedItemAdapter = MarkedItemAdapter(favourites)
        markedItemAdapter.onItemClick = {
            viewModel.onMarkedItemClick(it)
        }
        _binding.favouritesRecycler.layoutManager = LinearLayoutManager(context)
        _binding.favouritesRecycler.adapter = markedItemAdapter
    }

}