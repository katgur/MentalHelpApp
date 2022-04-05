package com.example.mainscreenlayout.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mainscreenlayout.R
import com.example.mainscreenlayout.model.MarkableItem

class ExerciseAdapter(private val exercises : List<MarkableItem>) : RecyclerView.Adapter<ExerciseAdapter.ExerciseItemHolder>() {

    var onFavouriteExerciseChoose : ((String) -> Unit)? = null
    var onExerciseClick : ((MarkableItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseItemHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_exercise, parent, false)
        return ExerciseItemHolder(itemView)
    }

    override fun onBindViewHolder(holder: ExerciseItemHolder, position: Int) {
        val item = exercises[position]
        holder.bind(item)
    }

    override fun getItemCount() : Int = exercises.size

    inner class ExerciseItemHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        private val name = itemView.findViewById<TextView>(R.id.recycler_exercise_name)
        private val favButton = itemView.findViewById<ImageButton>(R.id.recycler_exercise_fav_btn)

        fun bind(item : MarkableItem) {
            name.text = item.name
            if (item.isMarked) {
                favButton.setColorFilter(R.color.primaryDarkColor)
            }
            favButton.setOnClickListener {
                if (!item.isMarked) {
                    item.id?.let { it1 -> onFavouriteExerciseChoose?.invoke(it1) }
                    favButton.setColorFilter(R.color.primaryDarkColor)
                }
            }
            itemView.setOnClickListener {
                onExerciseClick?.invoke(item)
            }
        }
    }
}