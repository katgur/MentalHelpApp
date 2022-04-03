package com.example.mainscreenlayout.ui.exercise

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mainscreenlayout.R
import com.example.mainscreenlayout.adapter.ExerciseAdapter
import com.example.mainscreenlayout.databinding.ExerciseListFragmentBinding
import com.example.mainscreenlayout.domain.MarkableItem
import com.example.mainscreenlayout.ui.chat.ChatFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.ArrayList

class ExerciseListFragment : Fragment() {

    companion object {
        fun newInstance(extras : Bundle) : ExerciseListFragment {
            val instance = ExerciseListFragment()
            instance.arguments = extras
            return instance
        }
    }

    private lateinit var viewModel: ExerciseListViewModel
    private lateinit var binding: ExerciseListFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = ExerciseListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(ExerciseListViewModel::class.java)

        //todo
        val args = requireArguments()
        val exercises : ArrayList<MarkableItem>? = args.getParcelableArrayList("exercises")
        binding.exerciseListRv.layoutManager = GridLayoutManager(requireActivity(), 2)
        val adapter : ExerciseAdapter
        if (exercises != null) {
            adapter = ExerciseAdapter(exercises)
            adapter.onFavouriteExerciseChoose = {
                viewModel.markExercise(requireContext(), it)
            }
            adapter.onExerciseClick = {
                val navView: BottomNavigationView = requireActivity().findViewById(R.id.nav_view)
                navView.selectedItemId = R.id.navigation_chat
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.nav_host_fragment, ChatFragment.newInstance(it.id!!))
                    .commit()
            }
            binding.exerciseListRv.adapter = adapter
        } else {
            binding.exerciseListRv.adapter = ExerciseAdapter(emptyList())
        }

    }
}