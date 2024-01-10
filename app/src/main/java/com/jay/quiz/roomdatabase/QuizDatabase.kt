package com.jay.quiz.roomdatabase

import android.content.Context
import android.provider.CalendarContract.Instances
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [QuestionTable::class,QuizDataTable::class], version = 1, exportSchema = false)
abstract class QuizDatabase : RoomDatabase() {
    abstract fun getQuizDao() :QuizDataDao

    abstract fun getQuestionDao() : QuestionTableDao
    companion object{
        @Volatile
        private var INSTANCE:QuizDatabase? = null

        fun getInstance(context: Context) : QuizDatabase?{
            var instance = INSTANCE
            if (instance==null){
                instance=Room.databaseBuilder(context,QuizDatabase::class.java,"quiz_database")
                    .build()
            }
            return instance
        }
    }
}