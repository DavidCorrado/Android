package com.corradodev.todo.ui.task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.corradodev.todo.data.ViewState
import com.corradodev.todo.data.task.Task
import com.corradodev.todo.data.task.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(private val taskRepository: TaskRepository) : ViewModel() {
    private val _state = MutableStateFlow<ViewState<Task>>(ViewState.Loading)
    val state = _state.asStateFlow()

    fun loadTask(id: Long) {
        viewModelScope.launch {
            if (id == 0L) {
                _state.value = ViewState.Success(Task(0, "", ""))
            } else {
                taskRepository.find(id).collect { result ->
                    _state.value = result
                }
            }
        }
    }

    fun taskModelUpdate(task: Task) {
        _state.value = ViewState.Success(task)
    }

    suspend fun saveTask(task: Task): ViewState<Unit> {
        return taskRepository.save(task)
    }

    suspend fun deleteTask(task: Task): ViewState<Unit> {
        return taskRepository.delete(task)
    }
}
