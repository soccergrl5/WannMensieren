package com.soccergrlstudios.wannmensieren.datamodel
import android.annotation.SuppressLint
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class LectureModel(val day: Weekdays, val startTime: String, val endTime: String){

}
