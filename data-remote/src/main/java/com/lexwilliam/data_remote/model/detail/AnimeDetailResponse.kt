package com.lexwilliam.data_remote.model.detail

import com.lexwilliam.data_remote.model.common.*


data class AnimeDetailResponse(
    val aired: AiredResponse?,
    val airing: Boolean?,
    val background: Any?,
    val broadcast: String?,
    val duration: String?,
    val ending_themes: List<String>?,
    val episodes: Int?,
    val favorites: Int?,
    val genres: List<GenreResponse>?,
    val image_url: String?,
    val licensors: List<LicensorResponse>?,
    val mal_id: Int?,
    val members: Int?,
    val opening_themes: List<String>?,
    val popularity: Int?,
    val premiered: String?,
    val producers: List<ProducerResponse>?,
    val rank: Int?,
    val rating: String?,
    val related: RelatedResponse?,
    val request_cache_expiry: Int?,
    val request_cached: Boolean?,
    val request_hash: String?,
    val score: Double?,
    val scored_by: Int?,
    val source: String?,
    val status: String?,
    val studios: List<StudioResponse>?,
    val synopsis: String?,
    val title: String?,
    val title_english: Any?,
    val title_japanese: String?,
    val title_synonyms: List<Any>?,
    val trailer_url: String?,
    val type: String?,
    val url: String?
)