package com.soccergrlstudios.wannmensieren.data.api

import com.soccergrlstudios.wannmensieren.data.api.model.CourseApiDto
import com.soccergrlstudios.wannmensieren.data.api.model.TumApiResponse
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface TumNatApi {
    @GET("api/v1/course")
    suspend fun getCourses(
        @QueryMap(encoded = true) params: Map<String, String>
    ): TumApiResponse<CourseApiDto>

    @GET("api/v1/course/{course_id}")
    suspend fun getCourseDetails(
        @retrofit2.http.Path("course_id") courseId: Long
    ): CourseApiDto
}
