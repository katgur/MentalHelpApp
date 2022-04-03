package com.example.mainscreenlayout.graph

import android.content.Context
import com.example.mainscreenlayout.R
import com.example.mainscreenlayout.domain.Answer
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import java.time.LocalDate

class MoodGraph {

    private var data: Map<LocalDate, Answer> = hashMapOf()

    fun getLineData(context: Context, data: Map<LocalDate, Answer>, date: LocalDate): LineData {
        this.data = data

        val depressed = LineDataSet(getDataById(date, 0), "Депрессивность")
        depressed.colors = listOf(context.getColor(R.color.depressed))
        depressed.setCircleColor(context.getColor(R.color.depressed))
        val anxious = LineDataSet(getDataById(date, 1), "Тревожность")
        anxious.colors = listOf(context.getColor(R.color.anxious))
        anxious.setCircleColor(context.getColor(R.color.anxious))
        val stressed = LineDataSet(getDataById(date, 2), "Стресс")
        stressed.colors = listOf(context.getColor(R.color.stress))
        stressed.setCircleColor(context.getColor(R.color.stress))

        return LineData(depressed, anxious, stressed)
    }

    private fun getDataById(date: LocalDate, id: Int): ArrayList<Entry> {
        val entries = arrayListOf<Entry>()
        var i = 1f
        val start = LocalDate.of(date.year, date.month, 1)
        val length = date.month.length(date.isLeapYear)
        while (i <= length) {
            val x = i
            val answer = data[start.plusDays(i.toLong() - 1)]
            if (answer == null) {
                val y = 0f
                entries.add(Entry(x, y))
            }
            else {
                when (id) {
                    0 -> {
                        val y = answer.depressed.toFloat()
                        entries.add(Entry(x, y))
                    }
                    1 -> {
                        val y = answer.anxious.toFloat()
                        entries.add(Entry(x, y))
                    }
                    2 -> {
                        val y = answer.stress.toFloat()
                        entries.add(Entry(x, y))
                    }
                }
            }
            i += 1
        }
        return entries
    }
}