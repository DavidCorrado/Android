package com.corradodev.mvvm.data

import kotlinx.coroutines.experimental.async
import javax.inject.Inject

/**
 * Created by davidcorrado on 11/15/17.
 */

class TaskDatabaseRepository @Inject constructor(val taskDAO: TaskDAO) : RepositoryDatasourceInterface<Task> {
    override fun find(id: Long, findResult: FindResult<Task>?) {
        taskDAO.find(id).subscribe {
            findResult?.findResult(RepositoryResponse(it))
        }
    }

    override fun findAll(findAllResult: FindAllResult<Task>?) {
        taskDAO.findAll().subscribe {
            findAllResult?.findAllResults(RepositoryResponse(it))
        }
    }

    override fun save(data: Task, saveResult: SaveResult<Task>?) {
        async {
            taskDAO.save(data)
            saveResult?.saveResult(RepositoryResponse(data))
        }
    }

    override fun delete(data: Task, deleteResult: DeleteResult?) {
        async {
            taskDAO.delete(data)
            deleteResult?.deleteResult(RepositoryResponse())
        }
    }

    override fun deleteAll(deleteAllResult: DeleteAllResult?) {
        async {
            taskDAO.deleteAll()
            deleteAllResult?.deleteAllResult(RepositoryResponse())
        }
    }
}