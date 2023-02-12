package com.example.demo.di

import android.content.Context
import androidx.room.Room
import com.example.demo.data.db.AppDatabase
import com.example.demo.data.db.DomainDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun getDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java, "database-app"
        ).build()
    }

    @Provides
    @Singleton
    fun getDomainDao(appDatabase: AppDatabase): DomainDao {
        return appDatabase.getDomainDao()
    }


}