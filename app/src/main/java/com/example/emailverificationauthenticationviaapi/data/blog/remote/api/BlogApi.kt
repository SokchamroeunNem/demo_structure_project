package com.example.emailverificationauthenticationviaapi.data.blog.remote.api

import com.example.emailverificationauthenticationviaapi.data.blog.remote.dto.BlogResponse
import com.example.emailverificationauthenticationviaapi.data.common.utils.WrappedListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface BlogApi {

    @Headers("Accept: application/json")
    @GET("blog/list")
    suspend fun getAllMyBlogs() : Response<WrappedListResponse<BlogResponse>>

    /*@GET("products/{id}")
    suspend fun getBlogById(@Path("id") id: Int) : Response<WrappedResponse<ProductResponse>>

    @PUT("products/{id}")
    suspend fun updateBlog(@Body productUpdateRequest: ProductUpdateRequest, @Path("id") id: Int) :  Response<WrappedResponseProductMessage>

    @Headers("Accept: application/json")
    @POST("products")
    suspend fun createBlog(@Body productCreateRequest: ProductCreateRequest) : Response<WrappedResponse<ProductResponse>>*/

}