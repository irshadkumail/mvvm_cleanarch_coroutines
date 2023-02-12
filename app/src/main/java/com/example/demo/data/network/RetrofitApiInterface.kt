package com.example.demo.data.network

import com.example.demo.data.model.DomainResponseModel
import retrofit2.http.GET


interface RetrofitApiInterface {

    @GET("/anonymous/290132e587b77155eebe44630fcd12cb/raw/777e85227d0c1c16e466475bb438e0807900155c/sk_hosts")
    suspend fun getDomains(): List<DomainResponseModel>

}