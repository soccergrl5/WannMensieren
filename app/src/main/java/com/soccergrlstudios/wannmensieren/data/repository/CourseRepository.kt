package com.soccergrlstudios.wannmensieren.data.repository

import com.soccergrlstudios.wannmensieren.data.api.TumNatApi
import com.soccergrlstudios.wannmensieren.data.mapper.CourseMapper
import com.soccergrlstudios.wannmensieren.datamodel.CourseModel

class CourseRepository(
    private val api: TumNatApi
) {

    /**
     * Fetches a list of courses from the TumNat API based on the provided search parameters.
     *
     * This method retrieves raw course data from the `getCourses` endpoint, extracts the hits,
     * maps them to domain-level [CourseModel]s, and filters out incomplete entries.
     *
     * @param params A map containing query parameters. Supported keys include:
     * - `semester_key`: Limit to a specific semester (e.g., "current", "next", "lecture", "exam").
     * - `org_id`: Integer ID of the organization offering the course.
     * - `catalog_tag`: Filter by tags of the course modules.
     * - `ghk`: Integer GHK of the course.
     * - `rule_id`: Filter by specific rule IDs.
     * - `limit`: Number of results to return (Min: 1, Max: 200, Default: 50).
     * - `offset`: Number of results to skip (for pagination).
     * - `order_by`: Sorting preference ("code", "code:desc", "title", "title:desc").
     * - `only_show_open_registration`: "true" or "false" to filter by registration status.
     *
     * @return A list of [CourseModel] objects that have a valid `courseId` and `name`.
     * @throws Exception if the network request fails or the API response is invalid.
     *
     * @sample
     * val searchParams = mapOf(
     *     "semester_key" to "current",
     *     "limit" to "20",
     *     "order_by" to "title"
     * )
     * val courses = repository.fetchCourses(searchParams)
     */
    suspend fun fetchCourses(params: Map<String, String>): List<CourseModel> {
        // Updated to handle TumApiResponse wrapper
        return api.getCourses(params).hits
            .map(CourseMapper::toDomain)
            .filter { it.courseId.isNotBlank() && it.name.isNotBlank() }
    }
}
