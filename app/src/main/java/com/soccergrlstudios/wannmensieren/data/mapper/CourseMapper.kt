package com.soccergrlstudios.wannmensieren.data.mapper

import com.soccergrlstudios.wannmensieren.data.api.model.CourseApiDto
import com.soccergrlstudios.wannmensieren.data.api.model.CourseGroupApiDto
import com.soccergrlstudios.wannmensieren.data.api.model.CourseSessionApiDto
import com.soccergrlstudios.wannmensieren.datamodel.Weekdays
import com.soccergrlstudios.wannmensieren.datamodel.LectureModel
import com.soccergrlstudios.wannmensieren.datamodel.CourseModel

object CourseMapper {

    fun toDomain(dto: CourseApiDto): CourseModel {
        val courseId = dto.courseId?.toString() ?: ""
        val directLectures = dto.sessions.orEmpty().mapNotNull(::toLecture)
        val groupLectures = dto.groups.orEmpty().flatMap { group ->
            group.sessions.orEmpty().mapNotNull(::toLecture)
        }

        return CourseModel(
            courseId = courseId,
            name = dto.courseName ?: dto.courseNameEn ?: "",
            priority = 0,
            lectures = (directLectures + groupLectures)
                .distinctBy {
                    listOf(it.day, it.startTime, it.endTime)
                }
                .sortedWith(compareBy({ it.day.ordinal }, { it.startTime }))
        )
    }

    private fun toLecture(dto: CourseSessionApiDto): LectureModel? {
        val start = dto.startTime ?: dto.start ?: return null
        val end = dto.endTime ?: dto.end ?: return null
        val day = parseWeekday(dto.dayOfWeek ?: parseDayToInt(dto.day)) ?: return null

        return LectureModel(
            day = day,
            startTime = start,
            endTime = end
        )
    }

    private fun parseDayToInt(day: String?): Int? {
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

    private fun parseWeekday(dayInt: Int?): Weekdays? {
        return when (dayInt) {
            1 -> Weekdays.MONDAY
            2 -> Weekdays.TUESDAY
            3 -> Weekdays.WEDNESDAY
            4 -> Weekdays.THURSDAY
            5 -> Weekdays.FRIDAY
            else -> null
        }
    }
}
