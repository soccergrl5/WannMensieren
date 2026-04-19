package com.soccergrlstudios.wannmensieren.datamodel

import android.annotation.SuppressLint
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class CourseModel(val name: String, val courseId: String, val priority: Int, val lectures: List<LectureModel>){

}
