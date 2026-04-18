package com.soccergrlstudios.wannmensieren.data.api.model

import com.google.gson.Gson
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

class CourseApiDtoTest {

    private val gson = Gson()

    @Test
    fun `test parsing full course JSON`() {
        val json = """
            {
              "course_id": 123,
              "course_code": "IN0001",
              "course_name": "Advanced Programming",
              "course_name_en": "AP 101",
              "groups": [
                {
                  "id": "G1",
                  "group_id": "GRP_A",
                  "type": "Lecture",
                  "sessions": [
                    {
                      "day": "Monday",
                      "day_of_week": 1,
                      "start_time": "10:00",
                      "end_time": "12:00",
                      "room": "Interim I",
                      "location": "Garching"
                    }
                  ]
                }
              ],
              "sessions": [
                {
                  "day": "Wednesday",
                  "day_of_week": 3,
                  "start_time": "14:00",
                  "end_time": "16:00"
                }
              ]
            }
        """.trimIndent()

        val dto = gson.fromJson(json, CourseApiDto::class.java)

        assertNotNull(dto)
        assertEquals(123L, dto.courseId)
        assertEquals("IN0001", dto.courseCode)
        assertEquals("Advanced Programming", dto.courseName)

        // Groups
        assertNotNull(dto.groups)
        assertEquals(1, dto.groups?.size)
        val group = dto.groups!![0]
        assertEquals("G1", group.id)
        
        // Group Sessions
        assertNotNull(group.sessions)
        assertEquals(1, group.sessions?.size)
        val groupSession = group.sessions!![0]
        assertEquals("Monday", groupSession.day)
        assertEquals(1, groupSession.dayOfWeek)
        assertEquals("10:00", groupSession.startTime)

        // Direct Sessions
        assertNotNull(dto.sessions)
        assertEquals(1, dto.sessions?.size)
        val session = dto.sessions!![0]
        assertEquals("Wednesday", session.day)
        assertEquals(3, session.dayOfWeek)
        assertEquals("14:00", session.startTime)
    }

    @Test
    fun `test parsing partial JSON with nulls`() {
        val json = """
            {
              "course_id": 123,
              "course_name": "Simple Course"
            }
        """.trimIndent()

        val dto = gson.fromJson(json, CourseApiDto::class.java)

        assertNotNull(dto)
        assertEquals(123L, dto.courseId)
        assertEquals("Simple Course", dto.courseName)
        assertEquals(null, dto.courseCode)
        assertEquals(null, dto.groups)
        assertEquals(null, dto.sessions)
    }
}
