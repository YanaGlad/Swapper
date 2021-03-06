package com.example.nomoneytrade.api.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class ProductDto(
    @SerialName("id") val id: Long,
    @SerialName("title") val title: String,
    @SerialName("user_id") val userId: Long,
    @SerialName("description") val description: String,
    @SerialName("imagePath") val imagePath: String,
    @SerialName("tags") val tags: List<TagDto>,
    @SerialName("tags_exchange") val tagsExchange: List<TagExchangeDto>,
    @SerialName("city") val city: String?,
    @SerialName("time") val time: String?,
)