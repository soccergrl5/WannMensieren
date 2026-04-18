package com.gossipgirls.wannmensieren.data.model

data class Course(
    val id: String,
    val title: String,
    val moduleCode: String,
    // Add other fields based on the OpenAPI spec
)

data class CourseGroup(
    val groupId: String,
    val courseId: String,
    val type: String, // "Lecture", "Exercise", etc.
    val sessions: List<Session>
)

data class Session(
    val dayOfWeek: Int, // 1 = Monday, etc.
    val startTime: String, // "10:00"
    val endTime: String    // "11:30"
)