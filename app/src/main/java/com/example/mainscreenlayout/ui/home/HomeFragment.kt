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
import com.example.mainscreenlayout.ui.chat.ChatFragment
import com.example.mainscreenlayout.ui.nick.NicknameFragment
import com.example.mainscreenlayout.utils.QueryUtils
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding

    private lateinit var homeScreenAdapter: HomeScreenAdapter
    private var viewManager = LinearLayoutManager(context)

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
            viewModel.onRecommendedClick(it)
        }
        exerciseAdapter.onItemClick = {
            var id = QueryUtils.nameToId[it]
            if (id == null) {
                //todo
                id = "null"
            }
            val navView: BottomNavigationView = requireActivity().findViewById(R.id.nav_view)
            navView.selectedItemId = R.id.navigation_chat

            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment, ChatFragment.newInstance(id))
                .commit()
        }
        packsAdapter.onItemClick = {
            viewModel.onPackClick(it)
        }

        homeScreenAdapter = HomeScreenAdapter(headings, listOf(recommendedAdapter, exerciseAdapter, packsAdapter))
        binding.recyclerHome.layoutManager = viewManager
        binding.recyclerHome.adapter = homeScreenAdapter
        viewModel.observeExercises(viewLifecycleOwner, {
            exerciseAdapter.addItems(it)
        })
        viewModel.observePacks(viewLifecycleOwner, {
            packsAdapter.addItems(it)
        })
        viewModel.observeRecommended(viewLifecycleOwner, {
            recommendedAdapter.addItems(it)
        })
    }
}