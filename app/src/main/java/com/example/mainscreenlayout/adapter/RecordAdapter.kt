package com.example.mainscreenlayout.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mainscreenlayout.R
import com.example.mainscreenlayout.domain.Record

class RecordAdapter(private val record : Record) : RecyclerView.Adapter<RecordAdapter.RecordViewHolder>() {

    var onLongItemClick: ((RecordViewHolder, MutableMap.MutableEntry<String, String>) -> Unit)? = null
    var onColumnContentEdited: ((MutableMap.MutableEntry<String, String>) -> Unit) = {
        record.columns[it.key] = it.value
    }

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
                onLongItemClick?.invoke(this, entry)
                return@setOnLongClickListener true
            }
            description.setOnFocusChangeListener { view, hasFocus ->
                if (!hasFocus) {
                    onColumnContentEdited?.invoke(entry)
                    description.isEnabled = false
                }
            }
        }

        fun makeEditable() {
            description.isEnabled = true
        }
    }
}