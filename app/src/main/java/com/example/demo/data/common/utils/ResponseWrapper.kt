package com.example.demo.data.common.utils

import com.google.gson.annotations.SerializedName

data class WrappedListResponse<T> (
    var code: Int,
    @SerializedName("status") var status : Boolean,
    @SerializedName("message") var message : String,
    @SerializedName("pagination") val pagination: Pagination,
    @SerializedName("results") var results : List<T>? = null
)

data class Pagination(
    @SerializedName("total_count") val totalCount: Int,
    @SerializedName("current_page") val currentPage: Int,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("_links") val links: Links
)

data class Links(
    @SerializedName("previous") val previous: String?,
    @SerializedName("next") val next: String?
)

data class WrappedResponse<T>(
    val data: List<T>? = null,
    val message: String? = null,
    val code: Int? = null
)

data class WrappedResponseProductMessage (
    @SerializedName("success") var success : Boolean,
    @SerializedName("message") var message : String? = null
)