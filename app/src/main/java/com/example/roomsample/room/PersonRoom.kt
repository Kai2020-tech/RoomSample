package com.example.roomsample.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "person_table")
data class Person(
    @ColumnInfo(name = "person_name")
    var name: String,

    @ColumnInfo(name = "person_phone")
    var phone: String,

    @ColumnInfo(name = "person_address")
    var address: String,

    @PrimaryKey(autoGenerate = true)
    var personId: Long = 0L
)