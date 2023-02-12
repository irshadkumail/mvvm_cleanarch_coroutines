package com.example.demo.data

import com.example.demo.data.db.DomainDao
import com.example.demo.data.model.DomainResponseModel
import com.example.demo.data.network.NetworkHelper
import com.example.demo.data.network.RetrofitApiInterface
import com.example.demo.domain.DomainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Response
import javax.inject.Inject

class DomainRepositoryImpl @Inject constructor(
    private val apiInterface: RetrofitApiInterface,
    private val networkHelper: NetworkHelper,
    private val domainDao: DomainDao
) :
    DomainRepository {

    override suspend fun getDomains(): List<DomainResponseModel> = withContext(Dispatchers.IO) {
        apiInterface.getDomains()
    }

    override suspend fun pingDomain(url: String): Response = withContext(Dispatchers.IO) {
        networkHelper.pingUrl(url)
    }

    override suspend fun getLastSavedDomains(): List<DomainResponseModel> {
        return domainDao.getAllDomains().toList()
    }

    override suspend fun saveDomainsToCache(response: List<DomainResponseModel>) {
        domainDao.insertAll(response)
    }
}