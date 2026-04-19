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
                val time1 = lecture.startTime.split(':')
                val time2 = lecture.endTime.split(':')

                if(time1[0] == "11" || time1[0] == "12" || time1[0] == "13" || time2[0] == "12" || time2[0] == "13" || time2[0] == "14"){
                    duringLunch = true
                    break
                }

                if(time1[0].toInt() < 11 && time2[0].toInt() > 14){
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

    fun ahhhhh(courses: List<CourseModel>): List<CourseModel>{
        var pening = mutableListOf<CourseModel>()

        for(course in courses){
            var approved = true

            for (lecture in course.lectures){
                val time1 = lecture.startTime.split(':')
                val time2 = lecture.endTime.split(':')

                if(time1[0].toInt() < 11 && time2[0].toInt() > 14){
                    approved = false
                    break
                }


            }
        }

        val result = courses

        return result
    }
}