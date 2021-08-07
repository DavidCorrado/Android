package com.corradodev.mvvm.data.task

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val name: String,
    val detail: String
)