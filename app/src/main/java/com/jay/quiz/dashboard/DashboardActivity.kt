package com.jay.quiz.dashboard

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.jay.quiz.R
import com.jay.quiz.adapters.QuizAdapter
import com.jay.quiz.databinding.DashboardActivityBinding
import com.jay.quiz.quizactivity.QuizActivity
import com.jay.quiz.roomdatabase.QuizDataDao
import com.jay.quiz.roomdatabase.QuizDataTable
import com.jay.quiz.roomdatabase.QuizDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class DashboardActivity : AppCompatActivity(), CoroutineScope by MainScope() {
    private lateinit var binding: DashboardActivityBinding

    private lateinit var quizDataDao: QuizDataDao

    companion object{
        const val QUIZ_NAME_KEY = "Quiz Name"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.dashboard_activity)
        init()
    }

    private fun init() {
        quizDataDao = QuizDatabase.getInstance(this)?.getQuizDao()!!
        setQuizes()
        setClickListeners()
    }

    private fun setClickListeners() {
        binding.addButton.setOnClickListener {
            clickListenerOfAddButton()
        }
    }

    private fun clickListenerOfAddButton() {
        showCustomDialog()
    }

    private fun showCustomDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        val dialogView: View = layoutInflater.inflate(R.layout.dialog_quiz_name, null)
        builder.setView(dialogView)
        val editTextQuizName = dialogView.findViewById<EditText>(R.id.editTextQuizName)
        val btnCreateQuiz = dialogView.findViewById<Button>(R.id.btnCreateQuiz)
        val dialog: AlertDialog = builder.create()
        btnCreateQuiz.setOnClickListener {
//            val quizName = editTextQuizName.text.toString()
            if (TextUtils.isEmpty(editTextQuizName.text.toString())) {
                editTextQuizName.error = "Quiz Name can't be Empty"
            } else {
                val quiz = QuizDataTable(0, editTextQuizName.text.toString(), 0)
                launch {
                    withContext(Dispatchers.IO) {
                        quizDataDao.insertQuiz(quiz)
                        withContext(Dispatchers.Main) {
//                            toastMessage("Quiz Created Successfully")
                            dialog.dismiss()
                        }
                    }
                }
            }
        }
        dialog.show()
    }

    private fun toastMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun setQuizes() {
        launch {
            withContext(Dispatchers.IO) {
                val quizes = quizDataDao.getAllQuiz()
                withContext(Dispatchers.Main) {
                    quizes.observe(this@DashboardActivity) { it ->
                        it.forEach {
                            Log.i("Quiz Name", "Quizes Name: ${it.quizName}")
                        }
                        binding.quizesRecyclerView.layoutManager = LinearLayoutManager(this@DashboardActivity)
                        binding.quizesRecyclerView.adapter = QuizAdapter(it) { it1 ->
                            clickListenerOfRecyclerView(it1)
                        }
                    }
                }
            }

        }
    }

    private fun clickListenerOfRecyclerView(quiz: QuizDataTable) {
        val intent = Intent(this,QuizActivity::class.java)
        intent.putExtra(QUIZ_NAME_KEY,quiz.quizName)
        startActivity(intent)
    }

}