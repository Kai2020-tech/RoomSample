package com.example.roomsample.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface PersonDataDao {
    @Insert
    fun insert(person: Person)

    @Update
    fun update(person: Person)

    @Query("SELECT * FROM person_table WHERE person_phone = :key")
    fun get(key: Int): Person?

    @Query("DELETE FROM person_table")
    fun clear()

    @Query("SELECT * FROM person_table ORDER BY personId DESC")
    fun getAll(): List<Person>
}