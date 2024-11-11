package com.example.geoquiz

import android.app.Activity
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.core.content.ContextCompat
import android.graphics.Color
import android.widget.Button
import android.widget.Toast

class QuizQuestionsActivity : ComponentActivity(),View.OnClickListener {

    private var mCurrentPosition: Int = 1
    //private var mQuestionsList: ArrayList<Question>? = null
    private var mSelectedOptionPosition: Int = 0
    private var mCorrectAnswers: Int = 0
    private var mUserName: String? = null
    private var mCheatChances: Int = 3
    private var mQuestionCheated: Boolean = false
    private var totalCheatAttempts = 0
    private var totalQuestionsAnswered = 0
    private var totalScore = 0
    private var mQuestionsList: ArrayList<Question> = Constants.getQuestions()
    private var mUserAnswers: MutableList<Int> = MutableList(mQuestionsList.size) { 0 }


    private lateinit var tv_question: TextView
    private lateinit var iv_image: ImageView
    private lateinit var progressBar: ProgressBar
    private lateinit var tv_progress: TextView
    private lateinit var tv_option_one: TextView
    private lateinit var tv_option_two: TextView
    private lateinit var tv_option_three: TextView
    private lateinit var tv_option_four: TextView
    private lateinit var btn_submit: Button
    private lateinit var btn_previous: Button
    private lateinit var btn_reset: Button
    private lateinit var btn_cheat: Button
    private lateinit var tv_cheat_remaining: TextView
    private lateinit var btn_result_summary: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_questions)

        val btn_result_summary: Button = findViewById(R.id.btn_result_summary)

        mUserName = intent.getStringExtra(Constants.USER_NAME)

// Initialize views
        tv_question = findViewById(R.id.tv_question)
        iv_image = findViewById(R.id.iv_img)
        progressBar = findViewById(R.id.progressBar)
        tv_progress = findViewById(R.id.tv_progress)
        tv_option_one = findViewById(R.id.tv_option_one)
        tv_option_two = findViewById(R.id.tv_option_two)
        tv_option_three = findViewById(R.id.tv_option_three)
        tv_option_four = findViewById(R.id.tv_option_four)
        btn_submit = findViewById(R.id.btn_submit)
        btn_previous = findViewById(R.id.btn_previous)
        btn_reset = findViewById(R.id.btn_reset)
        btn_cheat = findViewById(R.id.btn_cheat)
        tv_cheat_remaining = findViewById(R.id.tv_cheat_remaining)

        totalQuestionsAnswered = 0

        mQuestionsList = Constants.getQuestions()

        if (savedInstanceState != null) {
            mCurrentPosition = savedInstanceState.getInt("CURRENT_POSITION", 1)
            mCorrectAnswers = savedInstanceState.getInt("CORRECT_ANSWERS", 0)

        }

        setQuestion()

        tv_option_one.setOnClickListener(this)
        tv_option_two.setOnClickListener(this)
        tv_option_three.setOnClickListener(this)
        tv_option_four.setOnClickListener(this)
        btn_submit.setOnClickListener(this)
        btn_previous.setOnClickListener(this)
        btn_reset.setOnClickListener(this)
        btn_cheat.setOnClickListener(this)
        btn_result_summary.setOnClickListener(this)



        btn_result_summary.setOnClickListener {
            // Pass data to ResultSummaryActivity
            val intent = Intent(this, ResultSummaryActivity::class.java)
            intent.putExtra("TOTAL_QUESTIONS_ANSWERED", totalQuestionsAnswered)
            intent.putExtra("TOTAL_SCORE", totalScore)
            intent.putExtra("TOTAL_CHEAT_ATTEMPTS", totalCheatAttempts)
            startActivity(intent)
        }
    }

    private fun setQuestion() {
        val question = mQuestionsList[mCurrentPosition - 1]

        defaultOptionsView()

        if (mCurrentPosition == mQuestionsList.size) {
            btn_submit.text = "FINISH"
        } else {
            btn_submit.text = "SUBMIT"
        }

        progressBar.progress = mCurrentPosition
        tv_progress.text = "$mCurrentPosition/${progressBar.max}"

        tv_question.text = question.question
        iv_image.setImageResource(question.image)
        tv_option_one.text = question.optionOne
        tv_option_two.text = question.optionTwo
        tv_option_three.text = question.optionThree
        tv_option_four.text = question.optionFour

        enableOptions(true)


        if (mCheatChances > 0) {
            btn_cheat.isEnabled = true
            btn_cheat.visibility = View.VISIBLE
        } else {
            btn_cheat.isEnabled = false
            btn_cheat.visibility = View.GONE
        }
        mQuestionCheated = false

        // Set the previously selected option
        if (mUserAnswers[mCurrentPosition - 1] != 0) {
            val selectedOption = mUserAnswers[mCurrentPosition - 1]
            answerView(selectedOption, when {
                selectedOption == question.correctAnswer -> R.drawable.correct_option_border_bg
                else -> R.drawable.wrong_option_border_bg
            })
            answerView(question.correctAnswer, R.drawable.correct_option_border_bg)

            // Disable options as question is already answered
            enableOptions(false)
        }
    }


    private fun defaultOptionsView() {
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


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_option_one -> selectedOptionView(tv_option_one, 1)
            R.id.tv_option_two -> selectedOptionView(tv_option_two, 2)
            R.id.tv_option_three -> selectedOptionView(tv_option_three, 3)
            R.id.tv_option_four -> selectedOptionView(tv_option_four, 4)
            R.id.btn_submit -> {
                if (mSelectedOptionPosition == 0) {
                    // No option selected, just move to the next question
                    mCurrentPosition++
                    if (mCurrentPosition <= mQuestionsList.size) {
                        (totalQuestionsAnswered++ /2) // Increment total answered questions
                        setQuestion()
                    } else {
                        showResult()
                    }
                } else {
                    // Option selected, process the answer
                    val question = mQuestionsList[mCurrentPosition - 1]
                    mUserAnswers[mCurrentPosition - 1] = mSelectedOptionPosition
                    (totalQuestionsAnswered++ /2)  // Increment total answered questions
                    if (question.correctAnswer == mSelectedOptionPosition) {
                        mCorrectAnswers++
                        totalScore++  // Increment total score if correct answer
                    } else {
                        answerView(mSelectedOptionPosition, R.drawable.wrong_option_border_bg)
                    }
                    answerView(question.correctAnswer, R.drawable.correct_option_border_bg)

                    if (mCurrentPosition == mQuestionsList.size) {
                        btn_submit.text = "FINISH"
                    } else {
                        btn_submit.text = "GO TO NEXT QUESTION"
                    }
                    mSelectedOptionPosition = 0
                    enableOptions(false)
                }
            }
            R.id.btn_previous -> {
                if (mCurrentPosition > 1) {
                    mCurrentPosition--
                    setQuestion()
                }
            }
            R.id.btn_cheat -> {
                if (!mQuestionCheated && mCheatChances > 0) {
                    cheat()
                } else {
                    Toast.makeText(this, "No cheat chance left for this question!", Toast.LENGTH_SHORT).show()
                }
            }
            R.id.btn_reset -> resetQuiz()
        }
    }

    private fun showResult() {
        val intent = Intent(this, ResultActivity::class.java)
        intent.putExtra(Constants.USER_NAME, mUserName)
        intent.putExtra(Constants.CORRECT_ANSWERS, mCorrectAnswers)
        intent.putExtra(Constants.TOTAL_QUESTIONS, mQuestionsList.size)
        val percentage = (mCorrectAnswers.toDouble() / mQuestionsList.size) * 100
        Toast.makeText(this, "You scored $percentage%", Toast.LENGTH_LONG).show()
        startActivity(intent)
        finish()
    }

    private fun cheat() {
        val question = mQuestionsList?.get(mCurrentPosition - 1)
        val correctAnswer = question!!.correctAnswer

        when (correctAnswer) {
            1 -> {
                tv_option_one.setTextColor(Color.BLUE)
                tv_option_one.setTypeface(tv_option_one.typeface, Typeface.BOLD)
            }
            2 -> {
                tv_option_two.setTextColor(Color.BLUE)
                tv_option_two.setTypeface(tv_option_two.typeface, Typeface.BOLD)
            }
            3 -> {
                tv_option_three.setTextColor(Color.BLUE)
                tv_option_three.setTypeface(tv_option_three.typeface, Typeface.BOLD)
            }
            4 -> {
                tv_option_four.setTextColor(Color.BLUE)
                tv_option_four.setTypeface(tv_option_four.typeface, Typeface.BOLD)
            }
        }

        // Update UI
        mCheatChances--
        mQuestionCheated = true
        totalCheatAttempts++
        updateCheatRemainingText()
    }

    private fun disableCheatButton() {
        btn_cheat.isEnabled = false
        btn_cheat.visibility = View.GONE
    }

    private fun updateCheatRemainingText() {
        tv_cheat_remaining.text = "Cheat Chances: $mCheatChances"
        if (mCheatChances == 0) {
            tv_cheat_remaining.visibility = View.GONE
            disableCheatButton()
        } else {
            tv_cheat_remaining.visibility = View.VISIBLE
            btn_cheat.isEnabled = true
            btn_cheat.visibility = View.VISIBLE
        }
    }

    private fun answerView(answer:Int, drawableView:Int) {
        when (answer) {
            1 -> {
                tv_option_one.background = ContextCompat.getDrawable(
                    this, drawableView
                )
            }

            2 -> {
                tv_option_two.background = ContextCompat.getDrawable(
                    this, drawableView
                )
            }

            3 -> {
                tv_option_three.background = ContextCompat.getDrawable(
                    this, drawableView
                )
            }

            4 -> {
                tv_option_four.background = ContextCompat.getDrawable(
                    this, drawableView
                )
            }
        }
    }

    private fun selectedOptionView(tv:TextView,selectedOptionNum:Int){
        defaultOptionsView()
        mSelectedOptionPosition = selectedOptionNum

        tv.setTextColor(Color.parseColor("#363A43"))
        tv.setTypeface (tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(this, R.drawable.selected_option_border_bg)

    }


    private fun enableOptions(enable: Boolean) {
        tv_option_one.isEnabled = enable
        tv_option_two.isEnabled = enable
        tv_option_three.isEnabled = enable
        tv_option_four.isEnabled = enable
    }

    private fun resetQuiz() {
        mCurrentPosition = 1
        mCorrectAnswers = 0
        mSelectedOptionPosition = 0
        mCheatChances = 3 // Reset cheat chances
        mQuestionCheated = false
        totalCheatAttempts = 0 // Reset total cheat attempts
        totalQuestionsAnswered = 0 // Reset total questions answered
        totalScore = 0 // Reset total score

        mUserAnswers.clear() // Clear all user answers

        // Ensure mUserAnswers has the same size as mQuestionsList
        mUserAnswers = MutableList(mQuestionsList.size) { 0 }

        setQuestion()
        updateCheatRemainingText()
        Toast.makeText(this, "Quiz reset!", Toast.LENGTH_SHORT).show()
    }

}





