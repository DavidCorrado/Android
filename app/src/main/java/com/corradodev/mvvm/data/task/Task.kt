package com.corradodev.mvvm.data.task

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(@PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") var id: Long,
                @ColumnInfo(name = "name") var name: String,
                @ColumnInfo(name = "detail") var detail: String)