package com.jay.quiz.roomdatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quiz_name")
data class QuizDataTable(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = QUIZ_NUMBER)
    val quizNumber : Int,
    @ColumnInfo(name = QUIZ_NAME)
    val quizName : String ,
    @ColumnInfo(name = TOTAL_QUESTION)
    val totalQuestion : Int
){
    companion object{
        const val QUIZ_TABLE = "quiz_name"
        const val QUIZ_NUMBER = "quizNumber"
        const val QUIZ_NAME = "quizName"
        const val TOTAL_QUESTION = "totalQuestion"
    }
}