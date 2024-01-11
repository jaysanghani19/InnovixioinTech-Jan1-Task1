package com.jay.quiz.quizactivity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.jay.quiz.dashboard.DashboardActivity
import com.jay.quiz.R
import com.jay.quiz.databinding.AddQuestionFragmentBinding
import com.jay.quiz.quizactivity.addquestion.AddQuestionDirections
import com.jay.quiz.quizactivity.quizfragment.QuizFragment

class QuizActivity : AppCompatActivity() {
    companion object{
        const val QUIZ_NAME_KEY="Quiz Name"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.quiz_activity)

        val quizName = intent.getStringExtra(DashboardActivity.QUIZ_NAME_KEY)

        val quiz = intent.extras?.getString(DashboardActivity.QUIZ_NAME_KEY)
        val bundle = Bundle()
        bundle.apply {
            bundle.putString(QUIZ_NAME_KEY,quiz)
        }

        val quizFragment = QuizFragment()
        quizFragment.arguments=bundle
        supportFragmentManager.beginTransaction().replace(R.id.quiz_activity_fragment_frame,quizFragment).commit()

    }
}