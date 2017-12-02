package com.corradodev.mvvm.data

import android.arch.lifecycle.LiveData
import kotlinx.coroutines.experimental.async
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

    override fun save(data: Task) {
        async {
            taskDAO.save(data)
        }
    }

    override fun delete(data: Task) {
        async {
            taskDAO.delete(data)
        }
    }
}