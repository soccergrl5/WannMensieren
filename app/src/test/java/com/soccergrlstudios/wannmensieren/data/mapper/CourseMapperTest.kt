package com.soccergrlstudios.wannmensieren.data.mapper


import com.soccergrlstudios.wannmensieren.data.api.model.CourseApiDto
import com.soccergrlstudios.wannmensieren.data.api.model.CourseSessionApiDto
import com.soccergrlstudios.wannmensieren.datamodel.Weekdays
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class CourseMapperTest {

    @Test
    fun `maps numeric day string`() {
        val dto = CourseApiDto(
            courseId = 1,
            courseName = "Course",
            sessions = listOf(
                CourseSessionApiDto(day = "1", startTime = "10:00", endTime = "12:00")
            )
        )

        val result = CourseMapper.toDomain(dto)

        assertEquals(Weekdays.MONDAY, result.lectures.first().day)
    }

    @Test
    fun `maps english day string`() {
        val dto = CourseApiDto(
            courseId = 1,
            courseName = "Course",
            sessions = listOf(
                CourseSessionApiDto(day = "Wednesday", startTime = "10:00", endTime = "12:00")
            )
        )

        val result = CourseMapper.toDomain(dto)

        assertEquals(Weekdays.WEDNESDAY, result.lectures.first().day)
    }

    @Test
    fun `maps german day string`() {
        val dto = CourseApiDto(
            courseId = 1,
            courseName = "Course",
            sessions = listOf(
                CourseSessionApiDto(day = "Donnerstag", startTime = "10:00", endTime = "12:00")
            )
        )

        val result = CourseMapper.toDomain(dto)

        assertEquals(Weekdays.THURSDAY, result.lectures.first().day)
    }

    @Test
    fun `skips invalid day string`() {
        val dto = CourseApiDto(
            courseId = 1,
            courseName = "Course",
            sessions = listOf(
                CourseSessionApiDto(day = "Noday", startTime = "10:00", endTime = "12:00")
            )
        )

        val result = CourseMapper.toDomain(dto)

        assertTrue(result.lectures.isEmpty())
    }

    @Test
    fun `maps start and end`() {
        val dto = CourseApiDto(
            courseId = 1,
            courseName = "Course",
            sessions = listOf(
                CourseSessionApiDto(dayOfWeek = 2, startTime = "08:15", endTime = "09:45")
            )
        )

        val result = CourseMapper.toDomain(dto)

        val lecture = result.lectures.first()
        assertEquals("08:15", lecture.startTime)
        assertEquals("09:45", lecture.endTime)
    }

    @Test
    fun `deduplicates exact duplicate sessions`() {
        val dto = CourseApiDto(
            courseId = 1,
            courseName = "Course",
            sessions = listOf(
                CourseSessionApiDto(dayOfWeek = 1, startTime = "10:00", endTime = "12:00"),
                CourseSessionApiDto(dayOfWeek = 1, startTime = "10:00", endTime = "12:00")
            )
        )

        val result = CourseMapper.toDomain(dto)

        assertEquals(1, result.lectures.size)
    }
}
