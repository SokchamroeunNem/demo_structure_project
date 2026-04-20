package com.example.emailverificationauthenticationviaapi.data.common.utils

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



data class WrappedResponse<T> (
    var code: Int,
//    @SerializedName("message") var message : String,
    @SerializedName("success") var status : Boolean,
//    @SerializedName("errors") var errors : List<String>? = null,
    @SerializedName("data") var data : T? = null
)

data class WrappedResponseProductMessage (
    @SerializedName("success") var success : Boolean,
    @SerializedName("message") var message : String? = null
)