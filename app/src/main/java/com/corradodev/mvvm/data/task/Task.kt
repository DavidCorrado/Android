package com.corradodev.mvvm.data.task

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true) var id: Long,
    var name: String,
    var detail: String
)