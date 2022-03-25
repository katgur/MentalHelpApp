package com.example.mainscreenlayout.ui.record

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mainscreenlayout.adapter.RecordAdapter
import com.example.mainscreenlayout.databinding.RecordFragmentBinding
import com.example.mainscreenlayout.domain.Record

class RecordFragment : Fragment() {

    companion object {
        fun newInstance(args : Bundle) : RecordFragment {
            val instance = RecordFragment()
            instance.arguments = args
            return instance
        }
    }

    private lateinit var viewModel: RecordViewModel
    private lateinit var binding: RecordFragmentBinding
    private val layoutManager = LinearLayoutManager(context)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = RecordFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //todo catch args
        val id = requireArguments().getString("id")
        if (id != null) {
            viewModel = ViewModelProvider(this, RecordViewModelFactory(id)).get(RecordViewModel::class.java)
        } else {
            requireActivity().finish()
        }

        //todo catch exc
        binding.recordDescText.text = viewModel.getDescription(requireContext())

        val record = viewModel.getRecord(requireContext())
        if (record == null) {
            requireActivity().finish()
        }
        val adapter = RecordAdapter(record)
        binding.recordRecycler.layoutManager = layoutManager
        binding.recordRecycler.adapter = adapter

        adapter.onLongItemClick = { holder, _ ->
            holder.makeEditable()
            binding.recordSaveBtn.isEnabled = true
        }

        //todo record is not replaced
        binding.recordSaveBtn.setOnClickListener {
            viewModel.replaceRecord(requireContext(), adapter.getRecord())
        }

        //todo refresh history after deleting
        binding.recordDeleteBtn.setOnClickListener {
            viewModel.deleteRecord(requireContext(), adapter.getRecord())
            requireActivity().finish()
        }
    }
}