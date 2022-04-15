package com.example.mainscreenlayout.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.example.mainscreenlayout.R
import com.example.mainscreenlayout.model.entities.Record

class RecordAdapter(private val record : Record) : RecyclerView.Adapter<RecordAdapter.RecordViewHolder>() {

    var onLongItemClick: (() -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_record, parent, false)
        return RecordViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecordViewHolder, position: Int) {
        val keys = record.columns.entries.toList()
        holder.bind(keys[position])
    }

    override fun getItemCount(): Int = record.columns.size

    fun getRecord() : Record {
        return record
    }

    inner class RecordViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        private val name = itemView.findViewById<TextView>(R.id.record_column_name)
        private val description = itemView.findViewById<EditText>(R.id.record_column_content)

        fun bind(entry : MutableMap.MutableEntry<String, String>) {
            name.text = entry.key
            description.setText(entry.value)

            itemView.setOnLongClickListener {
                description.isEnabled = true
                onLongItemClick?.invoke()
                return@setOnLongClickListener true
            }

            description.doAfterTextChanged {
                record.columns[entry.key] = it.toString()
            }
        }
    }
}