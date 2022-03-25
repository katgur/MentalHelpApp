package com.example.mainscreenlayout.ui.dashboard

import android.graphics.Color
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
import com.example.mainscreenlayout.domain.Answer
import com.example.mainscreenlayout.model.PersonalDatabase
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import kotlin.random.Random

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
            ViewModelProvider(this).get(DashboardViewModel::class.java)
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return _binding!!.root
    }

    val adapter = MonthCalendar()

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
        })

        val date = LocalDate.now()

        val start = LocalDateTime.of(date.year, date.month, 1, 0, 0, 0).toEpochSecond(ZoneOffset.UTC)
        val end = LocalDateTime.of(date.year, date.month, date.month.length(date.isLeapYear), 23, 59, 59).toEpochSecond(
            ZoneOffset.UTC)
        val answers = PersonalDatabase.getInstance(requireContext()).dao().getAnswersBetween(start, end)
        val data : Map<LocalDate, Answer> = answers.entries.associate { LocalDateTime.ofEpochSecond(it.key.date, 0, ZoneOffset.UTC).toLocalDate() to it.value[0] }

        val entries = arrayListOf<Entry>()
        var i = 1f
        var dateI = LocalDate.of(date.year, date.month, 1)
        val length = date.month.length(date.isLeapYear)
        while (i <= length) {
            val x = i
            val answer = data[dateI]
            if (answer == null) {
                val y = 0f
                entries.add(Entry(x, y))
            }
            else {
                val y = data[dateI]?.depressed?.toFloat()
                y?.let { Entry(x, it) }?.let { entries.add(it) }
            }
            i += 1
            dateI = dateI.plusDays(1)
        }
        val depressed = LineDataSet(entries, "Депрессивность")
        depressed.colors = listOf(Color.CYAN)
        val entries1 = arrayListOf<Entry>()
        dateI = LocalDate.of(date.year, date.month, 1)
        while (i <= length) {
            val x = i
            val answer = data[dateI]
            if (answer == null) {
                val y = 0f
                entries1.add(Entry(x, y))
            }
            else {
                val y = data[dateI]?.anxious?.toFloat()
                y?.let { Entry(x, it) }?.let { entries1.add(it) }
            }
            i += 1
            dateI = dateI.plusDays(1)
        }
        val anxious = LineDataSet(entries, "Тревожность")
        anxious.colors = listOf(Color.MAGENTA)
        dateI = LocalDate.of(date.year, date.month, 1)
        val entries2 = arrayListOf<Entry>()
        while (i <= length) {
            val x = i
            val answer = data[dateI]
            if (answer == null) {
                val y = 0f
                entries2.add(Entry(x, y))
            }
            else {
                val y = data[dateI]?.stress?.toFloat()
                y?.let { Entry(x, it) }?.let { entries2.add(it) }
            }
            i += 1
            dateI = dateI.plusDays(1)
        }
        val stressed = LineDataSet(entries, "Стресс")
        stressed.colors = listOf(Color.BLUE)

        binding.lineChart.data = LineData(depressed, anxious, stressed)
        binding.lineChart.invalidate()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}