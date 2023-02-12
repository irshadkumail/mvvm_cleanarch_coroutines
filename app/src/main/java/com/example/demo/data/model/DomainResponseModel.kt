package com.example.demo.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class DomainResponseModel(
    @PrimaryKey val name: String,
    @SerializedName("url") val domain: String,
    @SerializedName("icon") val image: String
)

