package com.example.roomdatabase

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PersonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(person: Person)

    @Delete
    suspend fun delete(person: Person)

    @Update
    suspend fun update(person: Person)

    @Query("SELECT * FROM Person")
    suspend fun getAllPerson(): List<Person>

}