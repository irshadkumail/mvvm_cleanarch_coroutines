package com.example.demo.di

import com.example.demo.data.DomainRepositoryImpl
import com.example.demo.data.db.DomainDao
import com.example.demo.data.network.NetworkHelper
import com.example.demo.data.network.RetrofitApiInterface
import com.example.demo.domain.DomainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
class RepositoryModule {

    @Provides
    fun getDomainRepo(
        retrofitApiInterface: RetrofitApiInterface,
        networkHelper: NetworkHelper,
        domainDao: DomainDao
    ): DomainRepository {
        return DomainRepositoryImpl(retrofitApiInterface, networkHelper, domainDao)
    }
}