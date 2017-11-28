package com.corradodev.mvvm.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by davidcorrado on 11/15/17.
 */

@Entity(tableName = "tasks")
data class Task(@ColumnInfo(name = "title") var title: String,
                @ColumnInfo(name = "description") var description: String){
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true) var id: Long = 0
}
