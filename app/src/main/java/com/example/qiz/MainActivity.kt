package com.example.qiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.view.WindowCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        //This call the parent constructor
        super.onCreate(savedInstanceState)
        // This is used to align the xml view to this class
        setContentView(R.layout.activity_main)

        // To display the activity in full screen and also make the status bar and notification bar transparent in themes.xml
        WindowCompat.setDecorFitsSystemWindows(window, false)

        val btn_start = findViewById<Button>(R.id.btn_start)
        val et_name = findViewById<AppCompatEditText>(R.id.et_name)

        btn_start.setOnClickListener {

            // here the isBlank() function is used in the condition to check if the string is empty or contains only spaces.
            if (et_name.text.toString().isBlank()) {
                // if yes then don't proceed further and show a toast message.
                Toast.makeText(this@MainActivity, "Please enter your name", Toast.LENGTH_SHORT).show()
            }
            else {
                // If the string is valid continue to the next activity.
                Intent(this, QuizQuestionsActivity::class.java).apply { startActivity(this) }
                finish()
            }
        }
    }
}