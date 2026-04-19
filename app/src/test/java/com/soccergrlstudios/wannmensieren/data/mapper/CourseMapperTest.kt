package com.soccergrlstudios.wannmensieren.data.mapper


import com.soccergrlstudios.wannmensieren.data.api.model.CourseApiDto
import com.soccergrlstudios.wannmensieren.data.api.model.CourseGroupApiDto
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

    @Test
    fun `prefers Standardgruppe over other groups`() {
        val dto = CourseApiDto(
            courseId = 1,
            groups = listOf(
                CourseGroupApiDto(
                    groupId = "Other",
                    sessions = listOf(CourseSessionApiDto(dayOfWeek = 1, startTime = "08:00", endTime = "10:00"))
                ),
                CourseGroupApiDto(
                    groupId = "Standardgruppe",
                    sessions = listOf(CourseSessionApiDto(dayOfWeek = 2, startTime = "10:00", endTime = "12:00"))
                )
            )
        )

        val result = CourseMapper.toDomain(dto)

        assertEquals(1, result.lectures.size)
        assertEquals(Weekdays.TUESDAY, result.lectures.first().day)
    }

    @Test
    fun `uses single group if it is the only one`() {
        val dto = CourseApiDto(
            courseId = 1,
            groups = listOf(
                CourseGroupApiDto(
                    groupId = "OnlyGroup",
                    sessions = listOf(CourseSessionApiDto(dayOfWeek = 3, startTime = "14:00", endTime = "16:00"))
                )
            )
        )

        val result = CourseMapper.toDomain(dto)

        assertEquals(1, result.lectures.size)
        assertEquals(Weekdays.WEDNESDAY, result.lectures.first().day)
    }

    @Test
    fun `ignores multiple groups if none is Standardgruppe`() {
        val dto = CourseApiDto(
            courseId = 1,
            groups = listOf(
                CourseGroupApiDto(
                    groupId = "Group A",
                    sessions = listOf(CourseSessionApiDto(dayOfWeek = 1, startTime = "08:00", endTime = "10:00"))
                ),
                CourseGroupApiDto(
                    groupId = "Group B",
                    sessions = listOf(CourseSessionApiDto(dayOfWeek = 2, startTime = "10:00", endTime = "12:00"))
                )
            )
        )

        val result = CourseMapper.toDomain(dto)

        assertTrue(result.lectures.isEmpty())
    }

    @Test
    fun `merges top-level sessions with selected group sessions`() {
        val dto = CourseApiDto(
            courseId = 1,
            sessions = listOf(
                CourseSessionApiDto(dayOfWeek = 1, startTime = "10:00", endTime = "12:00") // Top-level
            ),
            groups = listOf(
                CourseGroupApiDto(
                    groupId = "Standardgruppe",
                    sessions = listOf(
                        CourseSessionApiDto(dayOfWeek = 2, startTime = "14:00", endTime = "16:00") // Group
                    )
                )
            )
        )

        val result = CourseMapper.toDomain(dto)

        assertEquals(2, result.lectures.size)
        assertEquals(Weekdays.MONDAY, result.lectures[0].day)
        assertEquals(Weekdays.TUESDAY, result.lectures[1].day)
    }
}
