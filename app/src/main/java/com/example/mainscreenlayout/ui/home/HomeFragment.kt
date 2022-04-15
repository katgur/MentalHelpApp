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
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mainscreenlayout.R
import com.example.mainscreenlayout.adapter.RoundedRectangleItemAdapter
import com.example.mainscreenlayout.databinding.FragmentHomeBinding
import com.example.mainscreenlayout.model.MarkableItem
import com.example.mainscreenlayout.ui.market.MarketActivity
import com.example.mainscreenlayout.ui.chat.ChatFragment
import com.example.mainscreenlayout.ui.exercise.ExerciseListFragment
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
            openExercisesList(viewModel.getExercises(requireContext()))
        }

        binding.homeMarketBtn.setOnClickListener {
            val intent = Intent(requireContext(), MarketActivity::class.java)
            startActivity(intent)
        }

        viewModel.observePoints(viewLifecycleOwner, {
            binding.homePointsText.text = "Баллы " + it.first.toString()
            binding.homeLevelText.text = "Уровень " + it.second.toString()
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
        val recommendedAdapter = RoundedRectangleItemAdapter()
        recommendedAdapter.onItemClick = {
            it.id?.let { it1 -> goToExercise(it1) }
        }
        viewModel.observeRecommended(viewLifecycleOwner, {
            if (it != null) {
                recommendedAdapter.addItems(it)
            }
        })

        binding.recyclerRecommended.adapter = recommendedAdapter
        binding.recyclerRecommended.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }

    private fun setupPackList() {
        val packsAdapter = RoundedRectangleItemAdapter()
        packsAdapter.onItemClick = {
            openExercisesList(viewModel.getExercises(requireContext(), it.id))
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
            it.id?.let { it1 -> goToExercise(it1) }
        }
        viewModel.observeExercises(viewLifecycleOwner, {
            exerciseAdapter.addItems(it.subList(0, 5))
        })

        binding.recyclerExercises.adapter = exerciseAdapter
        binding.recyclerExercises.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }

    private fun goToExercise(id: String) {
        val navView: BottomNavigationView = requireActivity().findViewById(R.id.nav_view)
        navView.selectedItemId = R.id.navigation_chat
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment, ChatFragment.newInstance(id))
            .disallowAddToBackStack()
            .commit()
    }

    private fun openExercisesList(exercises: ArrayList<MarkableItem>) {
        val extras = Bundle()
        extras.putParcelableArrayList("exercises", exercises)
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment, ExerciseListFragment.newInstance(extras))
            .disallowAddToBackStack()
            .commit()
    }
}