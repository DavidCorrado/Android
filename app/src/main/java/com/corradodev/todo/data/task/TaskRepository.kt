package com.corradodev.todo.data.task

import com.corradodev.todo.data.DataError
import com.corradodev.todo.data.DataState
import com.corradodev.todo.data.Repository
import com.corradodev.todo.data.api.APIService
import com.corradodev.todo.data.db.AppDatabase
import com.corradodev.todo.extension.toResult
import com.dropbox.android.external.store4.Fetcher
import com.dropbox.android.external.store4.SourceOfTruth
import com.dropbox.android.external.store4.StoreBuilder
import com.dropbox.android.external.store4.StoreRequest
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TaskRepository @Inject constructor(
    private val db: AppDatabase,
    private val apiService: APIService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : Repository<Task> {
    private val storeList = StoreBuilder.from(fetcher = Fetcher.of { apiService.findTasks() }, sourceOfTruth = SourceOfTruth.of(
                reader = { db.taskDAO().findAll() },
                writer = { _: Any, input: List<Task> -> db.taskDAO().saveAll(input) },
            )
        ).build()

    private val storeItem = StoreBuilder
        .from(
            fetcher = Fetcher.of { key: Long -> apiService.findTask(key) },
            sourceOfTruth = SourceOfTruth.of(
                reader = db.taskDAO()::find,
                writer = { _: Any, input: Task -> db.taskDAO().save(input) }
            )
        ).build()

    override fun find(id: Long): Flow<DataState<Task>> {
        return storeItem.stream(StoreRequest.cached(id, true)).toResult()
    }

    override fun findAll(): Flow<DataState<List<Task>>> {
        return storeList.stream(StoreRequest.cached("", true)).toResult()
    }

    override suspend fun save(data: Task) = withContext(ioDispatcher) {
        kotlin.runCatching {
            val task = apiService.saveTask(data)
            db.taskDAO().save(task)
            DataState.Success(Unit)
        }.getOrElse { t ->
            DataState.Error(DataError(t.message))
        }
    }

    override suspend fun delete(data: Task) = withContext(ioDispatcher) {
        kotlin.runCatching {
            apiService.deleteTask(data.id)
            db.taskDAO().delete(data)
            DataState.Success(Unit)
        }.getOrElse { t ->
            DataState.Error(DataError(t.message))
        }
    }
}
