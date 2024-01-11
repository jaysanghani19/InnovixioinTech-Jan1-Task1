package com.jay.quiz.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jay.quiz.roomdatabase.QuestionTable
import com.jay.quiz.R
import org.w3c.dom.Text

class QuestionAdapter(val questionList: List<QuestionTable>) :
    RecyclerView.Adapter<QuestionViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val questionView = layoutInflater.inflate(R.layout.question_item_layout, parent, false)
        return QuestionViewHolder(questionView)
    }

    override fun getItemCount(): Int {
        return questionList.size
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        holder.bindData(questionList[position])
    }
}

class QuestionViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    private val questionTV: TextView = view.findViewById<TextView>(R.id.question_textview)
    private val option1TV = view.findViewById<TextView>(R.id.option1)
    private val option2TV = view.findViewById<TextView>(R.id.option2)
    private val option3TV = view.findViewById<TextView>(R.id.option3)
    private val option4TV = view.findViewById<TextView>(R.id.option4)

    fun bindData(question: QuestionTable) {
        questionTV.text = question.question
        option1TV.text = "A. ${question.option1}"
        option2TV.text = "B. ${question.option2}"
        option3TV.text = "C. ${question.option3}"
        option4TV.text = "D. ${question.option4}"
    }
}