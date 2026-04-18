package com.soccergrlstudios.wannmensieren.data.api.model

import com.google.gson.annotations.SerializedName

data class CourseApiDto(
    @SerializedName("course_id") val courseId: Long? = null,
    @SerializedName("course_code") val courseCode: String? = null,
    @SerializedName("course_name") val courseName: String? = null,
    @SerializedName("course_name_en") val courseNameEn: String? = null,
    @SerializedName("activity") val activity: ActivityApiDto? = null,
    @SerializedName("groups") val groups: List<CourseGroupApiDto>? = null,
    @SerializedName("sessions") val sessions: List<CourseSessionApiDto>? = null
)

data class ActivityApiDto(
    @SerializedName("activity_id") val activityId: String? = null,
    @SerializedName("activity_name") val activityName: String? = null
)

data class CourseGroupApiDto(
    @SerializedName("id") val id: String? = null,
    @SerializedName("group_id") val groupId: String? = null,
    @SerializedName("type") val type: String? = null,
    @SerializedName("sessions") val sessions: List<CourseSessionApiDto>? = null
)

data class CourseSessionApiDto(
    @SerializedName("day") val day: String? = null,
    @SerializedName("day_of_week") val dayOfWeek: Int? = null,
    @SerializedName("start") val start: String? = null,
    @SerializedName("start_time") val startTime: String? = null,
    @SerializedName("end") val end: String? = null,
    @SerializedName("end_time") val endTime: String? = null,
    @SerializedName("room") val room: String? = null,
    @SerializedName("location") val location: String? = null
)
