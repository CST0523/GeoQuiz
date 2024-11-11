package com.example.geoquiz

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.geoquiz.QuizQuestionsActivity
import com.example.geoquiz.R

class ResultSummaryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_summary)

        // Retrieve data from intent
        val totalQuestionsAnswered = intent.getIntExtra("TOTAL_QUESTIONS_ANSWERED", 0)
        val totalScore = intent.getIntExtra("TOTAL_SCORE", 0)
        val totalCheatAttempts = intent.getIntExtra("TOTAL_CHEAT_ATTEMPTS", 0)

        // Display received data in TextViews
        val tvTotalQuestionsAnswered: TextView = findViewById(R.id.tv_total_questions_answered)
        val tvTotalScore: TextView = findViewById(R.id.tv_total_score)
        val tvTotalCheatAttempts: TextView = findViewById(R.id.tv_total_cheat_attempts)

        tvTotalQuestionsAnswered.text = "Total Questions Answered: $totalQuestionsAnswered"
        tvTotalScore.text = "Total Score: $totalScore"
        tvTotalCheatAttempts.text = "Total Cheat Attempts: $totalCheatAttempts"

        // Back button functionality
        val btnBackToQuiz: Button = findViewById(R.id.btn_back_to_quiz)
        btnBackToQuiz.setOnClickListener {
            // Simply finish the activity to return to the previous QuizQuestionsActivity
            finish()
        }
    }
}