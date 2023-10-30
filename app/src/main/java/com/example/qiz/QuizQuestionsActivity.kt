package com.example.qiz

import android.content.Intent
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
    private var mCurrentPosition: Int = 1 // Default and the first question position
    private var mQuestionsList: ArrayList<Question> = Constants.getQuestions()
    private var mSelectedOptionPosition: Int = 0
    private var mCorrectAnswers: Int = 0
    private var mUserName: String? = null //Create a variable for getting the name from intent.

    override fun onCreate(savedInstanceState: Bundle?) { //This function is auto created by Android when the Activity Class is created.
        super.onCreate(savedInstanceState) //This call the parent constructor
        setContentView(R.layout.activity_quiz_questions) // This is used to align the xml view to this class

//        Log.e("Questions Size", "${questionsList.size}") // To print the no. of questions in logcat.
//        for (i in questionsList) {
//            Log.e("Questions", i.question) // To print all the questions in logcat.
//        }

        //Get the NAME from intent and assign it the variable.
        mUserName = intent.getStringExtra(Constants.USER_NAME)
        // Make the questions list and the current position variable global.
        setQuestion()

        var tv_option_one = findViewById<TextView>(R.id.tv_option_one)
        var tv_option_two = findViewById<TextView>(R.id.tv_option_two)
        var tv_option_three = findViewById<TextView>(R.id.tv_option_three)
        var tv_option_four = findViewById<TextView>(R.id.tv_option_four)
        var btn_submit = findViewById<Button>(R.id.btn_submit)

        // Set all the click events for Options using the interface onClick listener.
        tv_option_one.setOnClickListener(this)
        tv_option_two.setOnClickListener(this)
        tv_option_three.setOnClickListener(this)
        tv_option_four.setOnClickListener(this)
        btn_submit.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        var tv_option_one = findViewById<TextView>(R.id.tv_option_one)
        var tv_option_two = findViewById<TextView>(R.id.tv_option_two)
        var tv_option_three = findViewById<TextView>(R.id.tv_option_three)
        var tv_option_four = findViewById<TextView>(R.id.tv_option_four)
        var btn_submit = findViewById<Button>(R.id.btn_submit)

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
//                Toast.makeText(this, "Button clicked", Toast.LENGTH_SHORT).show()

                if (mSelectedOptionPosition == 0) {

                    mCurrentPosition++

                    when {
                        mCurrentPosition <= mQuestionsList!!.size -> {
                            setQuestion()
                        }
                        else -> {
                            //launch the result screen and also pass the user name and score details to it.
                            val intent = Intent(this, ResultActivity::class.java)
                            intent.putExtra(Constants.USER_NAME, mUserName)
                            intent.putExtra(Constants.CORRECT_ANSWERS, mCorrectAnswers)
                            intent.putExtra(Constants.TOTAL_QUESTIONS, mQuestionsList!!.size)
                            startActivity(intent)
                            finish()
                            //Toast.makeText(this, "You have successfully completed the quiz. Your Score is : $mCorrectAnswers", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                else {
                    val question = mQuestionsList?.get(mCurrentPosition - 1)

                    // This is to check if the answer is wrong
                    if (question!!.correctAnswer != mSelectedOptionPosition) {
                        answerView(mSelectedOptionPosition, R.drawable.wrong_option_border_bg)
                    }
                    // Increase the count of correct answer by 1 if the answer is right.
                    else {
                        mCorrectAnswers++
                    }

                    // This is for correct answer
                    answerView(question.correctAnswer, R.drawable.correct_option_border_bg)

                    if (mCurrentPosition == mQuestionsList!!.size) {
                        btn_submit.text = "FINISH"
                    }
                    else {
                        btn_submit.text = "GO TO NEXT QUESTION"
                    }

                    mSelectedOptionPosition = 0
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

        val question = mQuestionsList!!.get(mCurrentPosition - 1) // Getting the question from the list with the help of current position.
        var btn_submit = findViewById<Button>(R.id.btn_submit)

        defaultOptionsView()

        if (mCurrentPosition == mQuestionsList!!.size) {
            btn_submit.text = "FINISH"
        }
        else {
            btn_submit.text = "SUBMIT"
        }

        val tv_progress = findViewById<TextView>(R.id.tv_progress)
        val tv_question = findViewById<TextView>(R.id.tv_question)
        var iv_image = findViewById<ImageView>(R.id.iv_image)

        progressBar.progress = mCurrentPosition // Setting the current progress in the progressbar using the position of question
        tv_progress.text = "$mCurrentPosition" + "/" + progressBar.getMax() // Setting up the progress text

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
        mSelectedOptionPosition = selectedOptionNum

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
                tv_option_one.background = ContextCompat.getDrawable(this, drawableView)
            }
            2 -> {
                tv_option_two.background = ContextCompat.getDrawable(this, drawableView)
            }
            3 -> {
                tv_option_three.background = ContextCompat.getDrawable(this, drawableView)
            }
            4 -> {
                tv_option_four.background = ContextCompat.getDrawable(this, drawableView)
            }
        }
    }
}


