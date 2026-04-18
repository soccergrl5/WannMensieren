package com.soccergrlstudios.wannmensieren.data.api

import com.soccergrlstudios.wannmensieren.data.network.TumNatService
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertNotNull
import org.junit.Test

/**
 * This is an integration test that hits the real NAT server.
 */
class TumNatIntegrationTest {

    @Test
    fun `test real api call to nat srv tum`() = runBlocking {
        val api = TumNatService.api
        
        try {
            val response = api.getCourses(emptyMap())
            
            assertNotNull("API response should not be null", response)
            val courses = response.hits
            
            println("Successfully fetched ${courses.size} courses (Total: ${response.totalCount}) from NAT server.")
            
            if (courses.isNotEmpty()) {
                val firstCourse = courses[0]
                // Corrected: assertNotNull expectation and log
                assertNotNull("Course ID should not be null", firstCourse.courseId)
                println("First course: ${firstCourse.courseName} (ID: ${firstCourse.courseId})")
            }
        } catch (e: Exception) {
            throw AssertionError("API call failed with exception: ${e.message}. Ensure you have internet access and the server is up.", e)
        }
    }
}
