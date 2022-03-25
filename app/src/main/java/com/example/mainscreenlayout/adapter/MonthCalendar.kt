package com.example.mainscreenlayout.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.mainscreenlayout.R
import com.example.mainscreenlayout.domain.Answer
import com.example.mainscreenlayout.domain.HistoryItem
import com.example.mainscreenlayout.model.PersonalDatabase
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset

@RequiresApi(Build.VERSION_CODES.O)
class MonthCalendar(private var data : HashMap<LocalDate, Answer> = hashMapOf(), private var date : LocalDate = LocalDate.now()) : RecyclerView.Adapter<MonthCalendar.DayHolder>() {

    private var firstDayInMonth = this.date.withDayOfMonth(1)
    private var offset = firstDayInMonth.dayOfWeek.value - 1
    private var firstDay = firstDayInMonth.minusDays(offset.toLong())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_calendar, parent, false)
        return DayHolder(itemView)
    }

    override fun onBindViewHolder(holder: DayHolder, position: Int) {
        val date = firstDay.plusDays(position.toLong())
        holder.bind(date, data[date])
    }

    override fun getItemCount(): Int = 42

    fun setItems(data : Map<LocalDate, Answer>, date : LocalDate) {
        this.data.clear()
        this.data.putAll(data)

        this.date = date
        firstDayInMonth = this.date.withDayOfMonth(1)
        offset = firstDayInMonth.dayOfWeek.value - 1
        firstDay = firstDayInMonth.minusDays(offset.toLong())

        notifyDataSetChanged()
    }

    inner class DayHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        private val day = itemView.findViewById<TextView>(R.id.calendar_day)
        private val status = itemView.findViewById<TextView>(R.id.calendar_status)

        fun bind(date : LocalDate, answer : Answer?) {
            day.text = date.dayOfMonth.toString()
            if (answer != null) {
                status.text = answer.problem
            }
        }
    }
}