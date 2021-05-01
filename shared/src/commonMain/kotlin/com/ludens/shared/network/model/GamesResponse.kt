package com.ludens.shared.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class GameResponse(
    @SerialName("id")
    val id: Long,
    @SerialName("name")
    val name: String,
    @SerialName("first_release_date")
    val releaseDate: Long? = null,
    @SerialName("summary")
    val summary: String? = null,
    @SerialName("genres")
    val genreIds: List<Long>? = null,
    @SerialName("platforms")
    val platformsIds: List<Long>? = null,
    @SerialName("aggregated_rating")
    val aggregatedRating: Double? = null,
    @SerialName("total_rating")
    val totalRating: Double? = null,
    @SerialName("cover")
    val coverId: Long? = null,
    @SerialName("artworks")
    val artworkIds: List<Long>? = null,
    @SerialName("screenshots")
    val screenshotIds: List<Long>? = null,
    @SerialName("collection")
    val collection: Long? = null,
    @SerialName("franchise")
    val franchiseId: Long? = null,
    @SerialName("franchises")
    val franchiseIds: List<Long>? = null,
    @SerialName("similar_games")
    val similarGamesIds: List<Long>? = null
)
