package com.gossipgirls.wannmensieren.data.model

data class Course(
    val id: String,
    val title: String,
    val moduleCode: String?,
    val groups: List<CourseGroup> = emptyList(),
    val weeklySessions: List<Session> = emptyList()
) {
    val occupiedHoursByDay: Map<Int, List<Session>>
        get() = weeklySessions
            .groupBy { it.dayOfWeek }
            .mapValues { (_, sessions) -> sessions.sortedBy { it.startTime } }
}

data class CourseGroup(
    val groupId: String,
    val courseId: String,
    val type: String,
    val sessions: List<Session>
)

data class Session(
    val dayOfWeek: Int,
    val startTime: String,
    val endTime: String,
    val location: String? = null
)
