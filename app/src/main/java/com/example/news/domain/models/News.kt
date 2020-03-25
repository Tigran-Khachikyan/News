package com.example.news.domain.models

data class News(
    val currentPage: Int,
    val orderBy: String,
    val pageSize: Int,
    val pages: Int,
    val results: List<ModelApi>,
    val startIndex: Int,
    val status: String,
    val total: Int,
    val userTier: String,
    val content: ModelApi
)