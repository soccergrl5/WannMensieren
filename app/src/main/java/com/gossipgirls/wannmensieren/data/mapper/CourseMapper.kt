package com.gossipgirls.wannmensieren.data.mapper

import com.gossipgirls.wannmensieren.data.api.model.CourseApiDto
import com.gossipgirls.wannmensieren.data.api.model.CourseGroupApiDto
import com.gossipgirls.wannmensieren.data.api.model.CourseSessionApiDto
import com.gossipgirls.wannmensieren.data.model.Course
import com.gossipgirls.wannmensieren.data.model.CourseGroup
import com.gossipgirls.wannmensieren.data.model.Session

object CourseMapper {
    fun toDomain(dto: CourseApiDto): Course {
        val courseId = dto.id ?: dto.courseId ?: ""
        val directSessions = dto.sessions.orEmpty().mapNotNull(::toSession)
        val mappedGroups = dto.groups.orEmpty().map { group ->
            toCourseGroup(group, courseId)
        }
        val groupedSessions = mappedGroups.flatMap { it.sessions }

        return Course(
            id = courseId,
            title = dto.title ?: dto.name ?: "",
            moduleCode = dto.moduleCode,
            groups = mappedGroups,
            weeklySessions = (directSessions + groupedSessions)
                .distinctBy { Triple(it.dayOfWeek, it.startTime, it.endTime) }
                .sortedWith(compareBy(Session::dayOfWeek, Session::startTime))
        )
    }

    private fun toCourseGroup(dto: CourseGroupApiDto, courseId: String): CourseGroup {
        val groupId = dto.groupId ?: dto.id ?: ""
        return CourseGroup(
            groupId = groupId,
            courseId = courseId,
            type = dto.type ?: "Unknown",
            sessions = dto.sessions.orEmpty().mapNotNull(::toSession)
        )
    }

    private fun toSession(dto: CourseSessionApiDto): Session? {
        val start = dto.startTime ?: dto.start ?: return null
        val end = dto.endTime ?: dto.end ?: return null
        val day = dto.dayOfWeek ?: parseDayOfWeek(dto.day) ?: return null

        return Session(
            dayOfWeek = day,
            startTime = start,
            endTime = end,
            location = dto.location ?: dto.room
        )
    }

    private fun parseDayOfWeek(day: String?): Int? {
        return when (day?.trim()?.lowercase()) {
            "1", "mon", "monday", "montag" -> 1
            "2", "tue", "tuesday", "dienstag" -> 2
            "3", "wed", "wednesday", "mittwoch" -> 3
            "4", "thu", "thursday", "donnerstag" -> 4
            "5", "fri", "friday", "freitag" -> 5
            "6", "sat", "saturday", "samstag" -> 6
            "7", "sun", "sunday", "sonntag" -> 7
            else -> null
        }
    }
}
