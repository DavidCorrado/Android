package com.corradodev.mvvm.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by davidcorrado on 11/15/17.
 */

@Entity(tableName = "tasks")
data class Task(@PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") var id: Long,
                @ColumnInfo(name = "title") var title: String,
                @ColumnInfo(name = "description") var description: String) {
}
