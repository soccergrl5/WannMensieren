package com.gossipgirls.wannmensieren.data.api.model

import com.google.gson.annotations.SerializedName

data class CourseApiDto(
    @SerializedName("id") val id: String? = null,
    @SerializedName("course_id") val courseId: String? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("module_code") val moduleCode: String? = null,
    @SerializedName("groups") val groups: List<CourseGroupApiDto>? = null,
    @SerializedName("sessions") val sessions: List<CourseSessionApiDto>? = null
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
