package com.soccergrlstudios.wannmensieren.something

import com.soccergrlstudios.wannmensieren.datamodel.CourseModel

class Trying {
    fun maybeAlgo(courses: List<CourseModel>): List<List<CourseModel>>{
        val result1 = nothingAtLunch(courses)
        val result2 = courses
        val result3 = courses
        val result4 = courses

        val results: List<List<CourseModel>> = listOf(result1, result2, result3, result4)

        return results
    }

    fun nothingAtLunch(courses: List<CourseModel>): List<CourseModel>{
        val pending = mutableListOf<CourseModel>()

        for(course in courses){
            var duringLunch = false

            for(lecture in course.lectures){
                val time = lecture.startTime.split(':')

                if(time[0] == "11" || time[0] == "12" || time[0] == "13" || time[1] == "12" || time[1] == "13" || time[1] == "14"){
                    duringLunch = true
                    break
                }

                if(time[0].toInt() < 11 && time[1].toInt() > 14){
                    duringLunch = true
                    break
                }
            }

            if(!duringLunch){
                pending.add(course)
            }
        }

        val result = pending

        return result
    }
}