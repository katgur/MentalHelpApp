package com.example.mainscreenlayout.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mainscreenlayout.R
import com.example.mainscreenlayout.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding

    private lateinit var homeScreenAdapter: HomeScreenAdapter
    private var viewManager = LinearLayoutManager(context)

    private lateinit var exerciseAdapter: ExerciseAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, HomeViewModelFactory(activity)).get(HomeViewModel::class.java)

        // set headers recycler view
        val headings: List<String> = listOf(resources.getString(R.string.self_care),
            resources.getString(R.string.hello),
            resources.getString(R.string.packs),
            resources.getString(R.string.techniques))

        exerciseAdapter = ExerciseAdapter()

        homeScreenAdapter = HomeScreenAdapter(headings, exerciseAdapter)
        binding.recyclerHome.layoutManager = viewManager
        binding.recyclerHome.adapter = homeScreenAdapter
        viewModel.observe(viewLifecycleOwner, {
            exerciseAdapter.addItems(it)
        })
    }
}