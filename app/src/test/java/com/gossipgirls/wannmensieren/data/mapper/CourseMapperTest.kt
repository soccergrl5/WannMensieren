package com.gossipgirls.wannmensieren.data.mapper

import com.gossipgirls.wannmensieren.data.api.model.CourseApiDto
import com.gossipgirls.wannmensieren.data.api.model.CourseSessionApiDto
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class CourseMapperTest {

    @Test
    fun `maps numeric day string`() {
        val dto = CourseApiDto(
            id = "C1",
            title = "Course",
            sessions = listOf(
                CourseSessionApiDto(day = "1", startTime = "10:00", endTime = "12:00")
            )
        )

        val result = CourseMapper.toDomain(dto)

        assertEquals(1, result.weeklySessions.first().dayOfWeek)
    }

    @Test
    fun `maps english day string`() {
        val dto = CourseApiDto(
            id = "C1",
            title = "Course",
            sessions = listOf(
                CourseSessionApiDto(day = "Wednesday", startTime = "10:00", endTime = "12:00")
            )
        )

        val result = CourseMapper.toDomain(dto)

        assertEquals(3, result.weeklySessions.first().dayOfWeek)
    }

    @Test
    fun `maps german day string`() {
        val dto = CourseApiDto(
            id = "C1",
            title = "Course",
            sessions = listOf(
                CourseSessionApiDto(day = "Donnerstag", startTime = "10:00", endTime = "12:00")
            )
        )

        val result = CourseMapper.toDomain(dto)

        assertEquals(4, result.weeklySessions.first().dayOfWeek)
    }

    @Test
    fun `skips invalid day string`() {
        val dto = CourseApiDto(
            id = "C1",
            title = "Course",
            sessions = listOf(
                CourseSessionApiDto(day = "Noday", startTime = "10:00", endTime = "12:00")
            )
        )

        val result = CourseMapper.toDomain(dto)

        assertTrue(result.weeklySessions.isEmpty())
    }

    @Test
    fun `maps start end and location`() {
        val dto = CourseApiDto(
            id = "C1",
            title = "Course",
            sessions = listOf(
                CourseSessionApiDto(dayOfWeek = 2, startTime = "08:15", endTime = "09:45", location = "HS 1")
            )
        )

        val result = CourseMapper.toDomain(dto)

        val session = result.weeklySessions.first()
        assertEquals("08:15", session.startTime)
        assertEquals("09:45", session.endTime)
        assertEquals("HS 1", session.location)
    }

    @Test
    fun `deduplicates exact duplicate sessions but keeps different locations`() {
        val dto = CourseApiDto(
            id = "C1",
            title = "Course",
            sessions = listOf(
                CourseSessionApiDto(dayOfWeek = 1, startTime = "10:00", endTime = "12:00", location = "A"),
                CourseSessionApiDto(dayOfWeek = 1, startTime = "10:00", endTime = "12:00", location = "A"),
                CourseSessionApiDto(dayOfWeek = 1, startTime = "10:00", endTime = "12:00", location = "B")
            )
        )

        val result = CourseMapper.toDomain(dto)

        assertEquals(2, result.weeklySessions.size)
    }
}
