package com.corradodev.mvvm.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.corradodev.mvvm.R
import com.corradodev.mvvm.data.task.Task
import kotlinx.android.synthetic.main.item_task.view.*

/**
 * Created by DCorrado@werner.com on 12/28/17.
 */

class TaskAdapter(private val tasks: List<Task>, private val listener: (Task) -> Unit) : RecyclerView.Adapter<TaskAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(tasks[position], listener)
    }

    override fun getItemCount() = tasks.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(task: Task, listener: (Task) -> Unit) {
            with(task) {
                itemView.setOnClickListener({ listener(task) })
                itemView.tv_title.text = name
                itemView.tv_description.text = detail
            }
        }
    }
}