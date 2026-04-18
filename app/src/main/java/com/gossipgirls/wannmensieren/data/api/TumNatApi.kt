package com.gossipgirls.wannmensieren.data.api
import retrofit2.http.GET
import retrofit2.http.Query


interface TumNatApi {
    @GET("api/courses")
    suspend fun getCourses(
        @Query("semester") semester: String,
        @Query("major") major: String
    ): List<Course>

    @GET("api/courses/{courseId}/groups")
    suspend fun getCourseGroups(
        @retrofit2.http.Path("courseId") courseId: String
    ): List<CourseGroup>
}