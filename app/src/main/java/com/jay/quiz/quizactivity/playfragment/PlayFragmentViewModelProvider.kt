package com.jay.quiz.quizactivity.playfragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jay.quiz.roomdatabase.QuestionTableDao

class PlayFragmentViewModelProvider(val dao : QuestionTableDao) :ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PlayFragmentViewModel::class.java)){
            return PlayFragmentViewModel(dao) as T
        }
        throw IllegalArgumentException("Illegal Argument")
    }
}