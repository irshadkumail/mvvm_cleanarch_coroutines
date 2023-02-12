package com.example.demo.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.demo.data.model.DomainResponseModel

@Dao
interface DomainDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(domains: List<DomainResponseModel>)

    @Query("SELECT * FROM domainresponsemodel")
    suspend fun getAllDomains(): Array<DomainResponseModel>
}