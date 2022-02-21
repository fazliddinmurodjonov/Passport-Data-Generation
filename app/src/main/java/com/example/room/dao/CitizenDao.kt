package com.example.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.room.entity.Citizen

@Dao
interface CitizenDao {
    @Query("select * from  citizen")
    fun getAllCitizen(): List<Citizen>

    @Query("select * from citizen where id=:id")
    fun getCitizenById(id: Int): Citizen

    @Insert
    fun addCitizen(citizen: Citizen)
}