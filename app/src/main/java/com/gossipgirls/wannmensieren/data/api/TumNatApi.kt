package com.gossipgirls.wannmensieren.data.api

import com.gossipgirls.wannmensieren.data.api.model.CourseApiDto
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface TumNatApi {
    @GET("api/v1/course")
    suspend fun getCourses(
        @QueryMap(encoded = true) params: Map<String, String>
    ): List<CourseApiDto>
}
