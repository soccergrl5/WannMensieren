package com.soccergrlstudios.wannmensieren.data.api

import com.soccergrlstudios.wannmensieren.data.network.TumNatService
import com.soccergrlstudios.wannmensieren.data.repository.CourseRepository
import com.soccergrlstudios.wannmensieren.datamodel.CourseModel
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Test

class CourseSpecificTest {

    @Test
    fun `extract day and time from course details`() = runBlocking {
        val api = TumNatService.api
        val repository = CourseRepository(api)
        
        var foundCourse: CourseModel? = null
        val semesters = listOf("current", "previous", "lecture")

        println("Searching for a course with valid lectures in details...")
        
        searchLoop@for (semester in semesters) {
            val response = try {
                api.getCourses(mapOf("semester_key" to semester, "limit" to "20"))
            } catch (e: Exception) {
                continue
            }

            for (hit in response.hits) {
                val id = hit.courseId ?: continue
                try {
                    val detailedModel = repository.fetchCourseDetails(id)
                    if (detailedModel.lectures.isNotEmpty()) {
                        foundCourse = detailedModel
                        println("Found suitable course: ${detailedModel.name} (ID: $id) in semester $semester")
                        break@searchLoop
                    }
                } catch (e: Exception) {
                    // Skip failed detail fetches
                }
            }
        }

        assertNotNull("Should have found at least one course with lectures in detail API", foundCourse)
        
        val course = foundCourse!!
        println("\n--- Analysis for Course: ${course.name} ---")
        println("Lectures extracted: ${course.lectures.size}")

        course.lectures.forEach { lecture ->
            println("  - Extracted: ${lecture.day} | Start: ${lecture.startTime} | End: ${lecture.endTime}")
            
            assertNotNull("Day must be present", lecture.day)
            assertNotNull("StartTime must be present", lecture.startTime)
            assertNotNull("EndTime must be present", lecture.endTime)
            
            // Basic format check for HH:mm
            assertTrue("StartTime should match HH:mm format: ${lecture.startTime}", 
                lecture.startTime.matches(Regex("\\d{2}:\\d{2}")))
            assertTrue("EndTime should match HH:mm format: ${lecture.endTime}", 
                lecture.endTime.matches(Regex("\\d{2}:\\d{2}")))
        }
    }

    private fun assertTrue(message: String, condition: Boolean) {
        org.junit.Assert.assertTrue(message, condition)
    }
}
