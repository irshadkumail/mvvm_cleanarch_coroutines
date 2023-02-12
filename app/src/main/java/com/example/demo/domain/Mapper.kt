package com.example.demo.domain

import com.example.demo.data.model.DomainResponseModel
import com.example.demo.domain.models.DomainModel

fun DomainResponseModel.toBusinessModel() = DomainModel(
    name = name,
    domainUrl = this.domain,
    domainIcon = this.image
)