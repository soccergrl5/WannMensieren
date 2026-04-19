package com.soccergrlstudios.wannmensieren.data.mapper

import com.soccergrlstudios.wannmensieren.data.api.model.CourseApiDto
import com.soccergrlstudios.wannmensieren.data.api.model.CourseGroupApiDto
import com.soccergrlstudios.wannmensieren.data.api.model.CourseSessionApiDto
import com.soccergrlstudios.wannmensieren.datamodel.Weekdays
import com.soccergrlstudios.wannmensieren.datamodel.LectureModel
import com.soccergrlstudios.wannmensieren.datamodel.CourseModel
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

object CourseMapper {

    fun toDomain(dto: CourseApiDto): CourseModel {
        val courseId = dto.courseId?.toString() ?: ""

        val groups = dto.groups.orEmpty()
        val selectedGroup = groups.find { 
            val gid = it.groupId?.toString() ?: ""
            gid == "Standardgruppe" || it.name == "Standardgruppe" || it.groupName == "Standardgruppe"
        } ?: groups.singleOrNull()

        // Combine sessions and events (events is used in detail API)
        val groupSessions = (selectedGroup?.sessions.orEmpty() + selectedGroup?.events.orEmpty())
        val allSessions = dto.sessions.orEmpty() + groupSessions

        val lectures = allSessions
            .mapNotNull(::toLecture)
            .distinctBy { listOf(it.day, it.startTime, it.endTime) }
            .sortedWith(compareBy({ it.day.ordinal }, { it.startTime }))

        return CourseModel(
            courseId = courseId,
            name = dto.courseName ?: dto.courseNameEn ?: "",
            priority = 0,
            lectures = lectures
        )
    }

    private fun toLecture(dto: CourseSessionApiDto): LectureModel? {
        // Handle ISO-8601 strings from Detail API (e.g. "2026-04-14T10:00:00+02:00")
        val (startTime, dayFromIso) = if (dto.start?.contains("T") == true) {
            parseIsoDateTime(dto.start)
        } else {
            null to null
        }

        val (endTime, _) = if (dto.end?.contains("T") == true) {
            parseIsoDateTime(dto.end)
        } else {
            null to null
        }

        val start = startTime ?: dto.startTime ?: dto.start ?: return null
        val end = endTime ?: dto.endTime ?: dto.end ?: return null
        
        val dayInt = dto.dayOfWeek ?: dayFromIso ?: parseDayToInt(dto.day)
        val day = parseWeekday(dayInt) ?: return null

        return LectureModel(
            day = day,
            startTime = formatTime(start),
            endTime = formatTime(end)
        )
    }

    private fun parseIsoDateTime(isoStr: String): Pair<String, Int> {
        return try {
            val dt = ZonedDateTime.parse(isoStr)
            val time = dt.format(DateTimeFormatter.ofPattern("HH:mm"))
            val dayOfWeek = dt.dayOfWeek.value // 1 (Mon) to 7 (Sun)
            time to dayOfWeek
        } catch (e: Exception) {
            "" to 0
        }
    }

    private fun formatTime(time: String): String {
        // Ensure HH:mm format (sometimes API might return HH:mm:ss)
        return if (time.length > 5 && time.contains(":")) {
            time.substring(0, 5)
        } else {
            time
        }
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
