package com.jay.quiz.roomdatabase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface QuestionTableDao {
    @Insert
    abstract fun insertQuestion(questionTable: QuestionTable)

    @Update
    abstract fun updateQuestion(questionTable: QuestionTable) : Int

    @Delete
    abstract fun deleteQuestion(questionTable: QuestionTable) : Int

//    @Query("SELECT * FROM question_table")
//    fun getAllQuestion():List<QuestionTable>

    @Query("SELECT * FROM question_table WHERE quizName= :quizName")
    fun getQuestionOfTheQuiz(quizName : String): LiveData<List<QuestionTable>>

}