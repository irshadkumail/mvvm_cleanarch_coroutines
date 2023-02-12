package com.example.demo.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.demo.data.model.DomainResponseModel

@Database(entities = [DomainResponseModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getDomainDao(): DomainDao
}