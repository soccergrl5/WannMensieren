package com.soccergrlstudios.wannmensieren

import android.os.Bundle
import android.transition.Scene
import android.transition.TransitionManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.soccergrlstudios.wannmensieren.data.network.TumNatService
import com.soccergrlstudios.wannmensieren.datamodel.CourseModel
import com.soccergrlstudios.wannmensieren.datamodel.LectureModel
import com.soccergrlstudios.wannmensieren.datamodel.Weekdays
import com.soccergrlstudios.wannmensieren.something.Trying

//import com.soccergrlstudios.wannmensieren.JsonSwap.toJson

class MainActivity : AppCompatActivity() {

    //Course Selection
    private lateinit var courseListDisplay: ListView
    private lateinit var courseNameList: ArrayList<String>
    private lateinit var selectedCoursesIndexList: ArrayList<Int>
    private lateinit var selectedCoursesList: MutableList<CourseModel>
    private lateinit var itemAdapter: ArrayAdapter<String>

    private lateinit var okButton: Button

    //Result Display
    private lateinit var resultListDisplay: ListView
    private lateinit var resultInfoList: ArrayList<String>
    private lateinit var resultAdapter: ArrayAdapter<String>

    private lateinit var resultOption1btn: Button
    private lateinit var resultOption2btn: Button
    private lateinit var resultOption3btn: Button
    private lateinit var resultOption4btn: Button
    private var currentResultOption: Int = 0

    //Scenes
    private lateinit var sceneCourses: Scene
    private lateinit var sceneResults: Scene

    private lateinit var sceneResultsOption1: Scene
    private lateinit var sceneResultsOption2: Scene
    private lateinit var sceneResultsOption3: Scene
    private lateinit var sceneResultsOption4: Scene

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        sceneCourses = Scene.getSceneForLayout(findViewById(R.id.rootContainer), R.layout.scene_courses, this)
        sceneResults = Scene.getSceneForLayout(findViewById(R.id.rootContainer), R.layout.scene_results, this)

        sceneCourses.enter()

        selectCourseOverview()
    }

    fun selectCourseOverview() {
        /*val searchParams = mapOf(
            "semester_key" to "current",
            "order_by" to "title"
        )
        val courses = TumNatService.fetchCoursesBlocking(searchParams);*/
        val courses = listOf(
            CourseModel("Fach1", "001", 1,
                listOf(
                    LectureModel(Weekdays.MONDAY, "09:45", "11:15"),
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

        itemAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, courseNameList)
        courseListDisplay.adapter = itemAdapter

        courseListDisplay.setOnItemClickListener{arg1, arg2, pos, _ ->
            val test = itemAdapter.getView(pos, arg2, arg1)

            if(selectedCoursesIndexList.contains(pos)){
                selectedCoursesIndexList.remove(pos)
                test.setBackgroundResource(R.color.none)
            }
            else{
                selectedCoursesIndexList.add(pos)
                test.setBackgroundResource(R.color.blue)
            }
        }

        courseListDisplay.setOnItemLongClickListener(AdapterView.OnItemLongClickListener{ _, _, pos, _ ->

            var builder = AlertDialog.Builder(this)

            val course = courses[pos]

            builder.setTitle(course.name)

            var infoString = "ID: ${course.courseId}\n\nLectures:\n"
            for(lecture in course.lectures){
                infoString += "${lecture.day}: ${lecture.startTime} - ${lecture.endTime}\n"
            }

            builder.setMessage(infoString)

            builder.show()

            true
        })

        okButton = findViewById(R.id.ok)
        okButton.setOnClickListener {
            selectedCoursesList = mutableListOf()

            for (course in selectedCoursesIndexList){
                selectedCoursesList.add(courses[course])
            }

            TransitionManager.go(sceneResults)

            //toJson(selectedCoursesList)

            //val results: List<List<CourseModel>> = listOf(selectedCoursesList, selectedCoursesList, selectedCoursesList, courses)

            val results = Trying().maybeAlgo(selectedCoursesList)

            courseResults(results)
        }
    }

    fun courseResults(results: List<List<CourseModel>>) {
        sceneResultsOption1 = Scene.getSceneForLayout(findViewById(R.id.resultContainer), R.layout.scene_results_option1, this)
        sceneResultsOption2 = Scene.getSceneForLayout(findViewById(R.id.resultContainer), R.layout.scene_results_option2, this)
        sceneResultsOption3 = Scene.getSceneForLayout(findViewById(R.id.resultContainer), R.layout.scene_results_option3, this)
        sceneResultsOption4 = Scene.getSceneForLayout(findViewById(R.id.resultContainer), R.layout.scene_results_option4, this)

        currentResultOption = 1

        TransitionManager.go(sceneResultsOption1)

        resultInfoList = ArrayList()

        specificResults(1, results)

        resultOption1btn = findViewById(R.id.option1)
        resultOption1btn.setOnClickListener {
            if(currentResultOption != 1){
                currentResultOption = 1
                TransitionManager.go(sceneResultsOption1)
                specificResults(1, results)
            }
        }

        resultOption2btn = findViewById(R.id.option2)
        resultOption2btn.setOnClickListener {
            if(currentResultOption != 2){
                currentResultOption = 2
                TransitionManager.go(sceneResultsOption2)
                specificResults(2, results)
            }
        }

        resultOption3btn = findViewById(R.id.option3)
        resultOption3btn.setOnClickListener {
            if(currentResultOption != 3){
                currentResultOption = 3
                TransitionManager.go(sceneResultsOption3)
                specificResults(3, results)
            }
        }

        resultOption4btn = findViewById(R.id.option4)
        resultOption4btn.setOnClickListener {
            if(currentResultOption != 4){
                currentResultOption = 4
                TransitionManager.go(sceneResultsOption4)
                specificResults(4, results)
            }
        }
    }

    fun specificResults(number: Int, results: List<List<CourseModel>>){
        resultInfoList.clear()

        for(result in results[number - 1]){
            var resultString = result.name + "\n"

            for(lecture in result.lectures){
                resultString += "${lecture.day}: ${lecture.startTime} - ${lecture.endTime}\n"
            }

            resultInfoList.add(resultString)
        }

        when(number) {
            1 -> {
                resultListDisplay = findViewById(R.id.resultList1)
            }

            2 -> {
                resultListDisplay = findViewById(R.id.resultList2)
            }

            3 -> {
                resultListDisplay = findViewById(R.id.resultList3)
            }

            4 -> {
                resultListDisplay = findViewById(R.id.resultList4)
            }
        }

        resultAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, resultInfoList)
        resultListDisplay.adapter = resultAdapter
    }
}