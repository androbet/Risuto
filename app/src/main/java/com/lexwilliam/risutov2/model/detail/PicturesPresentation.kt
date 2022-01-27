package com.lexwilliam.risutov2.model.detail

data class PicturesPresentation(
    val pictures: List<PicturePresentation>
)

data class PicturePresentation(
    val large: String,
    val small: String
)