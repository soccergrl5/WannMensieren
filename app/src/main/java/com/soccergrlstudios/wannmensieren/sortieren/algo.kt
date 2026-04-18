var MondayTimesToSort = listOf()
var TuesdayTimesToSort =  listOf()
var WednesdayTimesToSort = setOf()
var ThursdayTimesToSort = setOf()
var FridayTimesToSort = setOf()

fun getTimes(val courses: List<CourseModel>) {
    for (course in courses) {
        for (times in course.lectures)
            when (times.day.toString()) {
                "MONDAY" ->
            }
    }
}

