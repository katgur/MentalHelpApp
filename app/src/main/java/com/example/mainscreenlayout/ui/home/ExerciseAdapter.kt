package com.example.mainscreenlayout.ui.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mainscreenlayout.R

class ExerciseAdapter(private var exercises: List<String> = mutableListOf()) : RecyclerView.Adapter<ExerciseAdapter.ExerciseItemHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ExerciseAdapter.ExerciseItemHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_exercise, parent, false)
        return ExerciseAdapter.ExerciseItemHolder(itemView)
    }

    override fun onBindViewHolder(holder: ExerciseItemHolder, position: Int) {
        val exercise = exercises[position]
        holder.bind(exercise)
    }

    override fun getItemCount(): Int = exercises.size

    @SuppressLint("NotifyDataSetChanged")
    fun addItems(items: List<String>) {
        exercises += items
        notifyDataSetChanged()
    }

    class ExerciseItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val text: TextView = itemView.findViewById(R.id.exercise_text)

        fun bind(name: String) {
            text.text = name
        }
    }
}