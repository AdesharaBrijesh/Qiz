package com.example.qiz

import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat

class QuizQuestionsActivity : AppCompatActivity(), View.OnClickListener {

    // Global variables for current position and questions list.
    private var CurrentPosition: Int = 1 // Default and the first question position
    private var QuestionsList: ArrayList<Question>? = null
    private var SelectedOptionPosition: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_questions)

//        Log.e("Questions Size", "${questionsList.size}") // To print the no. of questions in logcat.
//        for (i in questionsList) {
//            Log.e("Questions", i.question) // To print all the questions in logcat.
//        }

        // Make the questions list and the current position variable global and remove the logs here.
        QuestionsList = Constants.getQuestions()
        setQuestion()

        var tv_option_one = findViewById<TextView>(R.id.tv_option_one)
        var tv_option_two = findViewById<TextView>(R.id.tv_option_two)
        var tv_option_three = findViewById<TextView>(R.id.tv_option_three)
        var tv_option_four = findViewById<TextView>(R.id.tv_option_four)

        // Set all the click events for Options using the interface onClick listener.
        tv_option_one.setOnClickListener(this)
        tv_option_two.setOnClickListener(this)
        tv_option_three.setOnClickListener(this)
        tv_option_four.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        var tv_option_one = findViewById<TextView>(R.id.tv_option_one)
        var tv_option_two = findViewById<TextView>(R.id.tv_option_two)
        var tv_option_three = findViewById<TextView>(R.id.tv_option_three)
        var tv_option_four = findViewById<TextView>(R.id.tv_option_four)

        when (v?.id) {
            R.id.tv_option_one -> {
                selectedOptionView(tv_option_one, 1)
            }

            R.id.tv_option_two -> {
                selectedOptionView(tv_option_two, 2)
            }

            R.id.tv_option_three -> {
                selectedOptionView(tv_option_three, 3)
            }

            R.id.tv_option_four -> {
                selectedOptionView(tv_option_four, 4)
            }

            R.id.btn_submit -> {
                var CorrectAnswers: Int = 0

                if (SelectedOptionPosition == 0) {

                    CurrentPosition++

                    when {
                        CurrentPosition <= QuestionsList!!.size -> {
                            setQuestion()
                        }
                        else -> {
                            Toast.makeText(this, "You have successfully completed the quiz. Your Score is : $CorrectAnswers", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                else {
                    val question = QuestionsList?.get(CurrentPosition - 1)
                    var btn_submit = findViewById<Button>(R.id.btn_submit)

                    // This is to check if the answer is wrong
                    if (question!!.correctAnswer != SelectedOptionPosition) {
                        answerView(SelectedOptionPosition, R.drawable.wrong_option_border_bg)
                    }
                    // Increase the count of correct answer by 1 if the answer is right.
                    else {
                        CorrectAnswers++
                    }

                    // This is for correct answer
                    answerView(question.correctAnswer, R.drawable.correct_option_border_bg)

                    if (CurrentPosition == QuestionsList!!.size) {
                        btn_submit.text = "FINISH"
                    } else {
                        btn_submit.text = "GO TO NEXT QUESTION"
                    }

                    SelectedOptionPosition = 0
                }
            }
        }
    }

    // Create a function to set the question in the UI components.
    private fun setQuestion() {
        var tv_option_one = findViewById<TextView>(R.id.tv_option_one)
        var tv_option_two = findViewById<TextView>(R.id.tv_option_two)
        var tv_option_three = findViewById<TextView>(R.id.tv_option_three)
        var tv_option_four = findViewById<TextView>(R.id.tv_option_four)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val question = QuestionsList!!.get(CurrentPosition - 1) // Getting the question from the list with the help of current position.
        var btn_submit = findViewById<Button>(R.id.btn_submit)

        defaultOptionsView()

        if (CurrentPosition == QuestionsList!!.size) {
            btn_submit.text = "FINISH"
        }
        else {
            btn_submit.text = "SUBMIT"
        }

        val tv_progress = findViewById<TextView>(R.id.tv_progress)
        val tv_question = findViewById<TextView>(R.id.tv_question)
        var iv_image = findViewById<ImageView>(R.id.iv_image)

        progressBar.progress = CurrentPosition // Setting the current progress in the progressbar using the position of question
        tv_progress.text = "$CurrentPosition" + "/" + progressBar.getMax() // Setting up the progress text

        tv_question.text = question.question
        iv_image.setImageResource(question.image)
        tv_option_one.text = question.optionOne
        tv_option_two.text = question.optionTwo
        tv_option_three.text = question.optionThree
        tv_option_four.text = question.optionFour
    }

    // A function for view for highlighting the selected option.
    private fun selectedOptionView(tv: TextView, selectedOptionNum: Int) {
        defaultOptionsView()
        SelectedOptionPosition = selectedOptionNum

        tv.setTextColor(Color.parseColor("#363A43"))
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(this, R.drawable.selected_option_border_bg)
    }

    // A function to set default options view when the new question is loaded or when the answer is reselected.
    private fun defaultOptionsView() {
        var tv_option_one = findViewById<TextView>(R.id.tv_option_one)
        var tv_option_two = findViewById<TextView>(R.id.tv_option_two)
        var tv_option_three = findViewById<TextView>(R.id.tv_option_three)
        var tv_option_four = findViewById<TextView>(R.id.tv_option_four)

        val options = ArrayList<TextView>()
        options.add(0, tv_option_one)
        options.add(1, tv_option_two)
        options.add(2, tv_option_three)
        options.add(3, tv_option_four)

        for (option in options) {
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(this, R.drawable.default_option_border_bg)
        }
    }

    // A function for answer view which is used to highlight the answer is wrong or right.
    private fun answerView(answer: Int, drawableView: Int) {
        var tv_option_one = findViewById<TextView>(R.id.tv_option_one)
        var tv_option_two = findViewById<TextView>(R.id.tv_option_two)
        var tv_option_three = findViewById<TextView>(R.id.tv_option_three)
        var tv_option_four = findViewById<TextView>(R.id.tv_option_four)

        when (answer) {
            1 -> {
                tv_option_one.background = ContextCompat.getDrawable(this@QuizQuestionsActivity, drawableView)
            }
            2 -> {
                tv_option_two.background = ContextCompat.getDrawable(this@QuizQuestionsActivity, drawableView)
            }
            3 -> {
                tv_option_three.background = ContextCompat.getDrawable(this@QuizQuestionsActivity, drawableView)
            }
            4 -> {
                tv_option_four.background = ContextCompat.getDrawable(this@QuizQuestionsActivity, drawableView)
            }
        }
    }
}


