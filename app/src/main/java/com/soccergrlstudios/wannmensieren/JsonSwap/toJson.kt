package com.soccergrlstudios.wannmensieren.JsonSwap

import com.soccergrlstudios.wannmensieren.datamodel.CourseModel
import kotlinx.serialization.json.Json

fun toJson(table: MutableList<CourseModel>): String {
    return Json.encodeToString(table)
}