package com.example.mainscreenlayout.ui.market

import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
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
        viewModel = ViewModelProvider(this, MarketViewModelFactory(requireActivity().applicationContext, viewLifecycleOwner)).get(MarketViewModel::class.java)

        val adapter = MarketAdapter()
        binding.marketRv.adapter = adapter
        binding.marketRv.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        adapter.onItemClick = { item ->
            viewModel.setSelected(item)
            Log.d("aaa", "setSelected f")
        }

        adapter.onDeleteItemButtonClick = { item ->
            viewModel.removeCurrent(item.first, requireContext())
            Log.d("aaa", "removeCurrent f")
        }

        viewModel.observePoints(viewLifecycleOwner, {
            binding.marketPointsText.text = it.first.toString()
            binding.marketLevelText.text = it.second.toString()
        }, requireContext())

        viewModel.observeItems(viewLifecycleOwner, {
            adapter.addItems(it)
            Log.d("aaa", "add items f")
        })

        viewModel.observeSelected(viewLifecycleOwner, {
            Log.d("aaa", "selected changed: " + it.first + " " + it.second)
            viewModel.addCurrent(it.first, requireContext())
        })

        viewModel.observeCurrent(viewLifecycleOwner, {
            Log.d("aaa", "current changed: " + it.joinToString(" "))
            val layers = arrayListOf<Drawable>()
            for (id in it) {
                AppCompatResources.getDrawable(requireContext(), id)?.let { it1 -> layers.add(it1) }
            }
            val layerDrawable = LayerDrawable(layers.toTypedArray())
            binding.marketIv.setImageDrawable(layerDrawable)
        })

        binding.marketSelectBtn.setOnClickListener {
            Log.d("aaa", "on select btn click f")
            viewModel.save(requireContext())
            requireActivity().finish()
        }
    }
}