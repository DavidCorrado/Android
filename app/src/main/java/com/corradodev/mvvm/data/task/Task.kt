package com.corradodev.mvvm.data.task

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(@PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") var id: Long,
                @ColumnInfo(name = "name") var name: String,
                @ColumnInfo(name = "detail") var detail: String)