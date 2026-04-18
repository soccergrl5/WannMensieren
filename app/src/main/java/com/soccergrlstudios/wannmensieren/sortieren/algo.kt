var MondayTimesToSort = listOf("Mensa?",)
var TuesdayTimesToSort =  listOf("Mensa?",)
var WednesdayTimesToSort = listOf("Mensa?",)
var ThursdayTimesToSort = listOf("Mensa?",)
var FridayTimesToSort = listOf("Mensa?",)

fun assignTimeInt(time: str) {
    when (time) {
        "11:"
    }
}

fun getTimes(val courses: List<CourseModel>) {
    for (course in courses) {
        for (times in course.lectures)
            when (times.day.toString()) {
                "MONDAY" -> MondayTimesToSort.add(course.name)
                "TUESDAY" -> TuesdayTimesToSort.add(course.name)
                "WEDNESDAY" -> WednesdayTimesToSort.add(course.name)
                "THURSDAY" -> ThursdayTimesToSort.add(course.name)
                "FRIDAY" -> FridayTimesToSort.add(course.name)
            }
    }
}

