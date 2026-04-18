package com.soccergrlstudios.wannmensieren

import android.os.Bundle
import android.transition.Scene
import android.transition.TransitionManager
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.soccergrlstudios.wannmensieren.datamodel.CourseModel
import com.soccergrlstudios.wannmensieren.datamodel.LectureModel
import com.soccergrlstudios.wannmensieren.datamodel.Weekdays

class MainActivity : AppCompatActivity() {

    private lateinit var courseListDisplay: ListView
    private lateinit var courseNameList: ArrayList<String>
    private lateinit var selectedCoursesIndexList: ArrayList<Int>
    private lateinit var selectedCoursesList: MutableList<CourseModel>
    private lateinit var itemAdapter: ArrayAdapter<String>

    private lateinit var okButton: Button

    private lateinit var scene_courses: Scene
    private lateinit var scene_results: Scene

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        scene_courses = Scene.getSceneForLayout(findViewById(R.id.rootContainer), R.layout.scene_courses, this)
        scene_results = Scene.getSceneForLayout(findViewById(R.id.rootContainer), R.layout.scene_results, this)

        scene_courses.enter()

        selectCourseOverview()
    }

    fun selectCourseOverview() {
        val searchParams = mapOf(
            "semester_key" to "current",
            "order_by" to "title"
        )
        //val courses = repository.fetchCourses(searchParams)
        var courses: List<CourseModel>

        courses = listOf(
            CourseModel("Fach1", "001", 1,
                listOf(
                    LectureModel(Weekdays.MONDAY, "09:45", "11:115"),
                    LectureModel(Weekdays.THURSDAY, "13:15", "14:45")
                )
            ),
            CourseModel("Fach2", "002", 1,
                listOf(
                    LectureModel(Weekdays.WEDNESDAY, "08:00", "09:30"),
                    LectureModel(Weekdays.THURSDAY, "08:00", "09:30")
                )
            )
        )

        courseNameList = ArrayList()
        for (course in courses){
            courseNameList.add(course.name)
        }

        selectedCoursesIndexList = ArrayList()

        courseListDisplay = findViewById(R.id.courseList)

        itemAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, courseNameList)
        courseListDisplay.adapter = itemAdapter

        courseListDisplay.setOnItemClickListener{_, _, pos, _ ->
            selectedCoursesIndexList.add(pos)
        }

        okButton = findViewById(R.id.ok)
        okButton.setOnClickListener {
            selectedCoursesList = mutableListOf()

            for (course in selectedCoursesIndexList){
                selectedCoursesList.add(courses[course])
            }

            TransitionManager.go(scene_results)

            //Call Function for Results

            val results: List<List<CourseModel>> = listOf(selectedCoursesList)

            courseResults(results)
        }
    }

    fun courseResults(results: List<List<CourseModel>>) {
        for(result in results[0]){
            Toast.makeText(applicationContext, result.name, Toast.LENGTH_SHORT).show()
        }
    }
}