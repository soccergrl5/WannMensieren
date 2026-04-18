package com.soccergrlstudios.wannmensieren.data.network

import com.soccergrlstudios.wannmensieren.data.api.TumNatApi
import com.soccergrlstudios.wannmensieren.data.api.model.CourseApiDto
import com.soccergrlstudios.wannmensieren.data.api.model.TumApiResponse
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class TumOnlineAutomatorTest {

    private lateinit var api: TumNatApi
    private lateinit var automator: TumOnlineAutomator

    @Before
    fun setup() {
        api = mock(TumNatApi::class.java)
        automator = TumOnlineAutomator(api)
    }

    @Test
    fun `getAllAvailableCourses returns courses from api`() = runBlocking {
        // Arrange
        val expectedCourses = listOf(
            CourseApiDto(courseId = 1L, courseName = "Course 1"),
            CourseApiDto(courseId = 2L, courseName = "Course 2")
        )
        val response = TumApiResponse(
            totalCount = 2,
            count = 2,
            offset = 0,
            hits = expectedCourses
        )
        `when`(api.getCourses(emptyMap())).thenReturn(response)

        // Act
        val result = automator.getAllAvailableCourses()

        // Assert
        assertEquals(2, result.size)
        assertEquals("Course 1", result[0].courseName)
    }

    @Test
    fun `getAllAvailableCourses returns empty list on error`() = runBlocking {
        // Arrange
        `when`(api.getCourses(emptyMap())).thenThrow(RuntimeException("Network error"))

        // Act
        val result = automator.getAllAvailableCourses()

        // Assert
        assertTrue(result.isEmpty())
    }
}
