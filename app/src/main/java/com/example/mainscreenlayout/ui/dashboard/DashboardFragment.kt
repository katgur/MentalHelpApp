package com.example.mainscreenlayout.ui.dashboard

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mainscreenlayout.adapter.MonthCalendar
import com.example.mainscreenlayout.databinding.FragmentDashboardBinding
import com.example.mainscreenlayout.graph.MoodGraph
import java.time.format.DateTimeFormatter

class DashboardFragment : Fragment() {

    private lateinit var viewModel: DashboardViewModel
    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel =
            ViewModelProvider(this)[DashboardViewModel::class.java]
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return _binding!!.root
    }

    val adapter = MonthCalendar()
    private val graph = MoodGraph()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.calendarRv.layoutManager = GridLayoutManager(requireContext(), 7)
        binding.calendarRv.adapter = adapter

        binding.calendarBackBtn.setOnClickListener {
            viewModel.setPreviousMonth()
        }
        binding.calendarNextBtn.setOnClickListener {
            viewModel.setNextMonth()
        }
        viewModel.observeDate(viewLifecycleOwner, { date ->
            binding.calendarDateText.text = date.format(DateTimeFormatter.ISO_LOCAL_DATE)
            val data = viewModel.getData(requireContext(), date)
            adapter.setItems(data, date)
            binding.lineChart.data = graph.getLineData(requireContext(), data, date)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}