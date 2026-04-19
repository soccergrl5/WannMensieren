package com.soccergrlstudios.wannmensieren.JsonSwap

import com.soccergrlstudios.wannmensieren.datamodel.CourseModel
import kotlinx.serialization.json.Json

fun fromJson(data: Json): String {
    return Json.encodeToString(data)
}