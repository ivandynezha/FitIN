package com.fitin.services

import com.fitin.ui.login.LoginResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @FieldMap params: HashMap<String?, String?>
    ) : Response<LoginResponse>

//    @GET("api/course")
//    suspend fun course(
//        @Query("keyword") keyword: String,
//    ) : Response<CourseResponse>
//
//    @GET("api/category")
//    suspend fun category() : Response<CategoryResponse>
//
//    @POST("api/course-by-category")
//    suspend fun courseByCategory(
//        @Query("category_id") category_id: Int
//    ) : Response<CourseResponse>
//
//    @POST("api/course-by-id")
//    suspend fun courseById(
//        @Query("id") id: Int
//    ) : Response<ModuleResponse>
//
//    @POST("api/module-by-id")
//    suspend fun moduleById(
//        @Query("id") id: Int
//    ) : Response<DetailResponse>
//
//    @GET("api/home")
//    suspend fun home() : Response<HomeResponse>
//
//    @Multipart
//    @POST("api/avatar")
//    suspend fun avatar(
//        @Part avatar: MultipartBody.Part,
//        @Query("id") id: Int
//    ) : Response<AvatarResponse>
}