package com.example.db

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.example.db.model.Handbooks

@Dao
interface HandbooksDAO {

    @Query("SELECT * from Handbooks  WHERE methodName = :methodName")
    suspend fun json(methodName: String): Handbooks

    @Query("SELECT * from Handbooks")
    suspend fun all(): List<Handbooks>

    @Insert(onConflict = REPLACE)
    suspend fun insert(entity: Handbooks)

    @Update
    suspend fun update(entity: Handbooks)

    @Delete
    suspend fun delete(entity: Handbooks) {

    }
}