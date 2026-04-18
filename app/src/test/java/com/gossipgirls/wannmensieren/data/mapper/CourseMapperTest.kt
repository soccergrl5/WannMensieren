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
}
