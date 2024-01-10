package com.jay.quiz.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.jay.quiz.roomdatabase.QuizDataTable
import com.jay.quiz.R

@Suppress("UNREACHABLE_CODE", "UNREACHABLE_CODE", "UNREACHABLE_CODE")
class QuizAdapter(
    private val quizList: List<QuizDataTable>,
    private val viewClickListener: (QuizDataTable) -> Unit,
) : RecyclerView.Adapter<QuizHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemsView = layoutInflater.inflate(R.layout.quiz_item_layout,parent,false)
        return QuizHolder(itemsView)
    }

    override fun getItemCount(): Int {
        return quizList.size
    }

    override fun onBindViewHolder(holder: QuizHolder, position: Int) {
        holder.bind(quizList[position] , viewClickListener)
    }
}

class QuizHolder(val view: View) : RecyclerView.ViewHolder(view) {
    val quizTV:TextView? = view.findViewById(R.id.quiz_name_textview)
    val questionNumberTV:TextView? = view.findViewById(R.id.total_question_textview)

    fun bind(quiz : QuizDataTable , clickListener : (QuizDataTable) -> Unit){
        quizTV?.text = quiz.quizName.toString()
        questionNumberTV?.text = view.context.getString(R.string.total_question,quiz.totalQuestion.toString())

        Log.i("Tag", "${quiz.quizName} and ${quiz.totalQuestion} and ${quiz.quizNumber}")
        view.setOnClickListener {
            clickListener(quiz)
        }
    }
}