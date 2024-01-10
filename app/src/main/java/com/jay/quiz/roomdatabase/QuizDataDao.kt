package com.jay.quiz.roomdatabase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface QuizDataDao {
    @Insert
    abstract fun insertQuiz(quizDataTable: QuizDataTable)

    @Delete
    abstract fun deleteQuiz(quizDataTable: QuizDataTable) : Int

    @Update
    abstract fun updateQuiz(quizDataTable: QuizDataTable) : Int

    @Query("SELECT * FROM quiz_name")
    fun getAllQuiz() : LiveData<List<QuizDataTable>>

}