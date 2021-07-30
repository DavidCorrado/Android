package com.corradodev.mvvm.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.corradodev.mvvm.data.task.Task
import com.corradodev.mvvm.databinding.ItemTaskBinding

class TaskAdapter(private val tasks: List<Task>, private val listener: (Task) -> Unit) : RecyclerView.Adapter<TaskAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding =
            ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(tasks[position], listener)
    }

    override fun getItemCount() = tasks.size

    class ViewHolder(private val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(task: Task, listener: (Task) -> Unit) {
            with(task) {
                itemView.setOnClickListener({ listener(task) })
                binding.tvTitle.text = name
                binding.tvDescription.text = detail
            }
        }
    }
}