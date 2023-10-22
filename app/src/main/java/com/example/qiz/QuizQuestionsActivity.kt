package com.example.qiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView

class QuizQuestionsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_questions)

        val questionsList = Constants().getQuestions()
        Log.e("Questions Size", "${questionsList.size}") // To print the no. of questions in logcat.
        for (i in questionsList) {
            Log.e("Questions", i.question) // To print all the questions in logcat.
        }

        val currentPosition = 1 // Default and the first question position
        val question: Question? = questionsList[currentPosition - 1] // Getting the question from the list with the help of current position.
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val tv_progress = findViewById<TextView>(R.id.tv_progress)
        val tv_question = findViewById<TextView>(R.id.tv_question)
        val iv_image = findViewById<ImageView>(R.id.iv_image)
        val tv_option_one = findViewById<TextView>(R.id.tv_option_one)
        val tv_option_two = findViewById<TextView>(R.id.tv_option_two)
        val tv_option_three = findViewById<TextView>(R.id.tv_option_three)
        val tv_option_four = findViewById<TextView>(R.id.tv_option_four)

        progressBar.progress = currentPosition // Setting the current progress in the progressbar using the position of question
        tv_progress.text = "$currentPosition" + "/" + progressBar.getMax() // Setting up the progress text

        // Now set the current question and the options in the UI
        tv_question.text = question!!.question
        iv_image.setImageResource(question.image)
        tv_option_one.text = question.optionOne
        tv_option_two.text = question.optionTwo
        tv_option_three.text = question.optionThree
        tv_option_four.text = question.optionFour

    }
}