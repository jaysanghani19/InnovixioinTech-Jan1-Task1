package com.jay.quiz.roomdatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "question_table")
data class QuestionTable(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    val questionNumber : Int,
    @ColumnInfo(name = QUIZ_NAME)
    val quizName : String,
    @ColumnInfo(name = QUESTION)
    val question : String,
    @ColumnInfo(name = OPTION1)
    val option1:String,
    @ColumnInfo(name = OPTION2)
    val option2 : String,
    @ColumnInfo(name = OPTION3)
    val option3: String,
    @ColumnInfo(name = OPTION4)
    val option4 : String,
    @ColumnInfo(name = ANSWER)
    val answer : Int
){
    companion object{
        const val QUESTION_TABLE = "question_table"
        const val QUIZ_NAME = "quizName"
        const val QUESTION = "question"
        const val OPTION1="option1"
        const val OPTION2 = "option2"
        const val OPTION3 = "option3"
        const val OPTION4 = "option4"
        const val ANSWER = "answer"
    }
}
