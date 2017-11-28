package com.corradodev.mvvm.data

import android.arch.lifecycle.LiveData
import javax.inject.Inject

/**
 * Created by davidcorrado on 11/15/17.
 */

class TaskDatabaseRepository @Inject constructor(val taskDAO: TaskDAO) : RepositoryInterface<Task> {
    override fun find(id: Long): LiveData<Task> {
        return taskDAO.find(id)
    }

    override fun findAll(): LiveData<List<Task>> {
        return taskDAO.findAll()
    }

    override fun save(t: Task) {
        taskDAO.save(t)
    }

    override fun delete(t: Task) {
        taskDAO.delete(t)
    }
}