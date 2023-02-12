package com.example.demo.domain

import com.example.demo.data.model.DomainResponseModel
import okhttp3.Response

interface DomainRepository {

    suspend fun getDomains(): List<DomainResponseModel>

    suspend fun pingDomain(url: String): Response

    suspend fun getLastSavedDomains(): List<DomainResponseModel>

    suspend fun saveDomainsToCache(response: List<DomainResponseModel>)

}