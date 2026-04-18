package com.soccergrlstudios.wannmensieren

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var courseListDisplay: ListView
    private lateinit var courseNameList: ArrayList<String>
    private lateinit var selectedCourseNameList: ArrayList<String>
    private lateinit var itemAdapter: ArrayAdapter<String>

    private lateinit var okButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        selectCourseOverview()
    }

    fun selectCourseOverview() {
        courseNameList = ArrayList()
        courseNameList.add("Fach1")
        courseNameList.add("Fach2")

        selectedCourseNameList = ArrayList()

        courseListDisplay = findViewById(R.id.courseList)

        itemAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, courseNameList)
        courseListDisplay.adapter = itemAdapter

        courseListDisplay.setOnItemClickListener{_, _, pos, _ ->
            selectedCourseNameList.add(courseNameList.get(pos))
        }

        okButton = findViewById(R.id.ok)
        okButton.setOnClickListener {
            var message: String = ""

            for(name in selectedCourseNameList){
                message += name + ", "
            }

            Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
        }
    }
}