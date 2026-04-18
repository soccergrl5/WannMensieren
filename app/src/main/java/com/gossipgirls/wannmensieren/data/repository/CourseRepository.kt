package com.gossipgirls.wannmensieren.data.repository

import com.gossipgirls.wannmensieren.data.api.TumNatApi
import com.gossipgirls.wannmensieren.data.mapper.CourseMapper
import com.gossipgirls.wannmensieren.data.model.Course

class CourseRepository(
    private val api: TumNatApi
) {
    suspend fun fetchCourses(params: Map<String, String>): List<Course> {
        return api.getCourses(params)
            .map(CourseMapper::toDomain)
            .filter { it.id.isNotBlank() && it.title.isNotBlank() }
    }
}
