package com.example.mainscreenlayout.ui.home

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mainscreenlayout.R
import com.example.mainscreenlayout.adapter.RoundItemAdapter
import com.example.mainscreenlayout.adapter.RoundedRectangleItemAdapter
import com.example.mainscreenlayout.databinding.FragmentHomeBinding
import com.example.mainscreenlayout.ui.market.MarketActivity
import com.example.mainscreenlayout.ui.exercise.ExerciseListActivity
import com.example.mainscreenlayout.ui.chat.ChatFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, HomeViewModelFactory(requireContext())).get(HomeViewModel::class.java)

        setupRecommendedList()
        setupPackList()
        setupExerciseList()

        binding.homeExerciseBtn.setOnClickListener {
            val startExerciseActivityIntent = Intent(requireContext(), ExerciseListActivity::class.java)
            startExerciseActivityIntent.putExtra("exercises", viewModel.getExercises(requireContext()))
            startActivity(startExerciseActivityIntent)
        }

        binding.homeMarketBtn.setOnClickListener {
            val intent = Intent(requireContext(), MarketActivity::class.java)
            intent.putExtra("points", Integer.parseInt(binding.homePointsText.text as String))
            intent.putExtra("level", Integer.parseInt(binding.homeLevelText.text as String))
            startActivity(intent)
        }

        viewModel.observePoints(viewLifecycleOwner, {
            binding.homePointsText.text = it.first.toString()
            binding.homeLevelText.text = it.second.toString()
        }, requireContext())
    }

    override fun onResume() {
        super.onResume()
        setupPicture(requireContext())
    }

    private fun setupPicture(context: Context) {
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
        val current = sharedPref.getStringSet("current", emptySet())
        val layers = arrayListOf<Drawable>()
        AppCompatResources.getDrawable(context, R.drawable.bear)?.let { it1 -> layers.add(it1) }
        if (current != null) {
            for (item in current) {
                val id = Integer.parseInt(item)
                AppCompatResources.getDrawable(context, id)?.let { it1 -> layers.add(it1) }
            }
        }
        val layerDrawable = LayerDrawable(layers.toTypedArray())
        binding.homeBearIv.setImageDrawable(layerDrawable)
    }

    private fun setupRecommendedList() {
        val recommendedAdapter = RoundItemAdapter()
        recommendedAdapter.onItemClick = {
            it.name?.let { it1 -> viewModel.onRecommendedClick(it1) }
        }
        viewModel.observeRecommended(viewLifecycleOwner, {
            recommendedAdapter.addItems(it)
        }, requireContext())

        binding.recyclerRecommended.adapter = recommendedAdapter
        binding.recyclerRecommended.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }

    private fun setupPackList() {
        val packsAdapter = RoundedRectangleItemAdapter()
        //todo on pack item click
        packsAdapter.onItemClick = {
            it.name?.let { it1 -> viewModel.onPackClick(it1) }
        }
        viewModel.observePacks(viewLifecycleOwner, {
            packsAdapter.addItems(it)
        })

        binding.recyclerPacks.adapter = packsAdapter
        binding.recyclerPacks.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }

    private fun setupExerciseList() {
        val exerciseAdapter = RoundedRectangleItemAdapter()
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
        viewModel.observeExercises(viewLifecycleOwner, {
            exerciseAdapter.addItems(it.subList(0, 5))
        })

        binding.recyclerExercises.adapter = exerciseAdapter
        binding.recyclerExercises.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }
}