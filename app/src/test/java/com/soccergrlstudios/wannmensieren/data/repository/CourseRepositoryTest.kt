package com.soccergrlstudios.wannmensieren.data.repository

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

class CourseRepositoryTest {

    private lateinit var api: TumNatApi
    private lateinit var repository: CourseRepository

    @Before
    fun setup() {
        api = mock(TumNatApi::class.java)
        repository = CourseRepository(api)
    }

    @Test
    fun `fetchCourses maps and filters results correctly`() = runBlocking {
        // Arrange
        val apiDtos = listOf(
            CourseApiDto(courseId = 1, courseName = "Course 1"),
            CourseApiDto(courseId = null, courseName = "Invalid"), // Should be filtered out
            CourseApiDto(courseId = 3, courseName = ""), // Should be filtered out
            CourseApiDto(courseId = 4, courseName = "Course 4")
        )
        val response = TumApiResponse(
            totalCount = 4,
            count = 4,
            offset = 0,
            hits = apiDtos
        )
        `when`(api.getCourses(emptyMap())).thenReturn(response)

        // Act
        val result = repository.fetchCourses(emptyMap())

        // Assert
        assertEquals(2, result.size)
        assertEquals("1", result[0].courseId)
        assertEquals("Course 1", result[0].name)
        assertEquals("4", result[1].courseId)
        assertEquals("Course 4", result[1].name)
    }

    @Test
    fun `fetchCourses returns empty list when api returns empty`() = runBlocking {
        val response = TumApiResponse<CourseApiDto>(
            totalCount = 0,
            count = 0,
            offset = 0,
            hits = emptyList()
        )
        `when`(api.getCourses(emptyMap())).thenReturn(response)

        val result = repository.fetchCourses(emptyMap())

        assertTrue(result.isEmpty())
    }
}
