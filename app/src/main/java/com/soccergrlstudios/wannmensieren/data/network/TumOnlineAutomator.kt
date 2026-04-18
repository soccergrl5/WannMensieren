package com.soccergrlstudios.wannmensieren.data.network

import com.soccergrlstudios.wannmensieren.data.api.TumNatApi
import com.soccergrlstudios.wannmensieren.data.api.model.CourseApiDto

class TumOnlineAutomator(
    private val api: TumNatApi = TumNatService.api
) {

    suspend fun getAllAvailableCourses(): List<CourseApiDto> {
        return try {
            // Fetch courses from the NAT API and return the hits list
            api.getCourses(emptyMap()).hits
        } catch (e: Exception) {
            emptyList()
        }
    }
}
