package com.corradodev.todo.ui.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.corradodev.todo.data.DataState
import com.corradodev.todo.data.task.Task
import com.corradodev.todo.data.task.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(private val taskRepository: TaskRepository) : ViewModel() {
    private val _state = MutableStateFlow<DataState<List<Task>>>(DataState.Loading)
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            taskRepository.findAll().collect { result ->
                _state.value = result
            }
        }
    }
}
