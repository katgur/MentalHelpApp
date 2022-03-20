package com.example.mainscreenlayout.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mainscreenlayout.R
import com.example.mainscreenlayout.adapter.HomeScreenAdapter
import com.example.mainscreenlayout.adapter.RoundItemAdapter
import com.example.mainscreenlayout.adapter.RoundedRectangleItemAdapter
import com.example.mainscreenlayout.databinding.FragmentHomeBinding
import com.example.mainscreenlayout.ui.exercise.ExerciseListActivity
import com.example.mainscreenlayout.ui.chat.ChatFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding

    private lateinit var homeScreenAdapter: HomeScreenAdapter
    private var viewManager = LinearLayoutManager(context)

    private var argId : String = "default"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, HomeViewModelFactory()).get(HomeViewModel::class.java)

        // set headers recycler view
        val headings: List<String> = listOf(resources.getString(R.string.self_care),
            resources.getString(R.string.hello),
            resources.getString(R.string.packs),
            resources.getString(R.string.techniques))

        val recommendedAdapter = RoundItemAdapter()
        val exerciseAdapter = RoundedRectangleItemAdapter()
        val packsAdapter = RoundedRectangleItemAdapter()

        recommendedAdapter.onItemClick = {
            it.name?.let { it1 -> viewModel.onRecommendedClick(it1) }
        }

        exerciseAdapter.onItemClick = {
            val navView: BottomNavigationView = requireActivity().findViewById(R.id.nav_view)
            navView.selectedItemId = R.id.navigation_chat

            it.id?.let { it1 -> ChatFragment.newInstance(it1) }?.let { it2 ->
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.nav_host_fragment,
                        it2
                    )
                    .commit()
            }
        }
        packsAdapter.onItemClick = {
            it.name?.let { it1 -> viewModel.onPackClick(it1) }
        }

        homeScreenAdapter = HomeScreenAdapter(headings, listOf(recommendedAdapter, exerciseAdapter, packsAdapter))
        homeScreenAdapter.onAllButtonClick = {
            val startExerciseActivityIntent = Intent(requireContext(), ExerciseListActivity::class.java)
            startExerciseActivityIntent.putExtra("exercises", viewModel.getExercises(requireContext()))
            startActivity(startExerciseActivityIntent)
        }

        binding.recyclerHome.layoutManager = viewManager
        binding.recyclerHome.adapter = homeScreenAdapter
        viewModel.observeExercises(viewLifecycleOwner, {
            exerciseAdapter.addItems(it.subList(0, 5))
        })
        viewModel.observePacks(viewLifecycleOwner, {
            packsAdapter.addItems(it)
        })
        viewModel.observeRecommended(viewLifecycleOwner, {
            recommendedAdapter.addItems(it)
        })
    }
}