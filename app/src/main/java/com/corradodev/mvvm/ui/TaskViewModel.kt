package com.corradodev.mvvm.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.corradodev.mvvm.data.Result
import com.corradodev.mvvm.data.task.Task
import com.corradodev.mvvm.data.task.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(private val taskRepository: TaskRepository) : ViewModel() {
    private val _uiState = MutableStateFlow<Result<Task>>(Result.Loading)
    val uiState = _uiState.asStateFlow()

    fun loadTask(id: Long) {
        viewModelScope.launch {
            taskRepository.find(id).collect { result ->
                _uiState.value = result
            }
        }
    }

    suspend fun saveTask(task: Task): Result<Unit> {
        return taskRepository.save(task)
    }

    suspend fun deleteTask(task: Task): Result<Unit> {
        return taskRepository.delete(task)
    }
}