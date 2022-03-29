package com.example.mainscreenlayout.ui.market

import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mainscreenlayout.adapter.MarketAdapter
import com.example.mainscreenlayout.databinding.MarketFragmentBinding

class MarketFragment : Fragment() {

    companion object {

        fun newInstance() : MarketFragment {
            val instance = MarketFragment()
//            val args = Bundle()
//            args.putInt("points", points)
//            args.putInt("level", level)
//            instance.arguments = args
            return instance
        }
    }

    private lateinit var viewModel: MarketViewModel
    private lateinit var binding: MarketFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = MarketFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, MarketViewModelFactory(requireActivity().applicationContext)).get(MarketViewModel::class.java)

//        val points = requireArguments().getInt("points")
//        val level = requireArguments().getInt("level")

        val adapter = MarketAdapter()
        binding.marketRv.adapter = adapter
        binding.marketRv.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        adapter.onItemClick = { item ->
            viewModel.setSelected(item)
        }

        adapter.onDeleteItemButtonClick = { item ->
            viewModel.removeCurrent(item.first)
        }

        viewModel.observePoints(viewLifecycleOwner, {
            binding.marketPointsText.text = it.first.toString()
            binding.marketLevelText.text = it.second.toString()
        }, requireContext())

        viewModel.observeItems(viewLifecycleOwner, {
            adapter.addItems(it)
        })

        viewModel.observeSelected(viewLifecycleOwner, {
            viewModel.addCurrent(it.first)
            adapter.setSelected(it.second)
        })

        viewModel.observeCurrent(viewLifecycleOwner, {
            val layers = arrayListOf<Drawable>()
            for (id in it) {
                AppCompatResources.getDrawable(requireContext(), id)?.let { it1 -> layers.add(it1) }
            }
            val layerDrawable = LayerDrawable(layers.toTypedArray())
            binding.marketIv.setImageDrawable(layerDrawable)
        })

        binding.marketSelectBtn.setOnClickListener {
            viewModel.save()
            requireActivity().finish()
        }
    }
}