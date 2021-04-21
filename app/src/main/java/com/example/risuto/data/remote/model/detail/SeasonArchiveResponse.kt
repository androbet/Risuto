package com.example.risuto.data.remote.model.detail

data class SeasonArchiveResponse(
    val request_hash: String,
    val request_cached: Boolean,
    val request_cache_expiry: Int,
    val archive: List<Archive>
)

data class Archive(
    val year: Int? = null,
    val seasons: List<String?> = emptyList()
)